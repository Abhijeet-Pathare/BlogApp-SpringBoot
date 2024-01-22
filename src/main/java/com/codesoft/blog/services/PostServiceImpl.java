package com.codesoft.blog.services;

import com.codesoft.blog.entities.Category;
import com.codesoft.blog.entities.Post;
import com.codesoft.blog.entities.User;
import com.codesoft.blog.exceptions.ResourceNotFoundException;
import com.codesoft.blog.payloads.PostDto;
import com.codesoft.blog.payloads.PostResponse;
import com.codesoft.blog.repositories.CategoryRepo;
import com.codesoft.blog.repositories.PostRepo;
import com.codesoft.blog.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId,Integer categoryId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ","userId",userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        Post post = modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost =  postRepo.save(post);
        return modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        post.setCategory(postDto.getCategory());
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
//        post.setUser(postDto.getUser());
//        post.setCategory(postDto.getCategory());
        Post updatedPost = postRepo.save(post);
        PostDto updatedPostDto = modelMapper.map(updatedPost, PostDto.class);
        return updatedPostDto;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
        postRepo.deleteById(postId);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
//        org.springframework.data.domain.Pageable p = PageRequest.of(pageNumber,pageSize);
            Page<Post> pagePost = postRepo.findAll(p);
            List<Post> allPosts = pagePost.getContent();

        List<Post> posts = postRepo.findAll();
        List<PostDto> postDtos = posts.stream().map(post->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category cat = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Post> posts = postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","UserId",userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        for(Post post : posts){
            PostDto postDto = this.modelMapper.map(post,PostDto.class);
            postDtos.add(postDto);
        }
        //List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

}
