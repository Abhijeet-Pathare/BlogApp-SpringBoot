package com.codesoft.blog.services;

import com.codesoft.blog.entities.Post;
import com.codesoft.blog.payloads.PostDto;
import com.codesoft.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto,Integer postId);

    //delete
    void deletePost(Integer postId);

    //gat all posts
    //List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);
    PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy);

    //get single post by id
    PostDto getPostById(Integer postId);

    //get all posts by category
    List<PostDto> getPostsByCategory(Integer categoryId);

    //get all posts by user
    List<PostDto> getPostsByUser(Integer userId);

    //search posts
    List<PostDto> searchPosts(String keyword);

    List<PostDto> searchByContent(String keyword);

}
