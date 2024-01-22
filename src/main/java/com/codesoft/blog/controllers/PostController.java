package com.codesoft.blog.controllers;

import com.codesoft.blog.config.AppConstants;
import com.codesoft.blog.entities.Post;
import com.codesoft.blog.payloads.ApiResponse;
import com.codesoft.blog.payloads.PostDto;
import com.codesoft.blog.payloads.PostResponse;
import com.codesoft.blog.services.FileService;
import com.codesoft.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.codesoft.blog.config.AppConstants.PAGE_SIZE;
import static com.codesoft.blog.config.AppConstants.SORT_BY_POST_ID;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                                @PathVariable Integer categoryId){
    PostDto createdPost = postService.createPost(postDto,userId,categoryId);
    return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //get posts by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> postDtos = postService.getPostsByUser(userId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    //get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                    @RequestParam(value="pageSize",defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(value="sortBy", defaultValue=SORT_BY_POST_ID,required = false) String sortBy){
       PostResponse postResponse = postService.getAllPost(pageNumber,pageSize, sortBy);

        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //get a post by its id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(postService.getPostById(postId),HttpStatus.OK);
    }

    //delete post by postId
//    @DeleteMapping("/post/{postId}")
//    public String deletePostById(@PathVariable Integer postId){
//        postService.deletePost(postId);
//        return "Post with id "+postId+" deleted succesfully !";
//    }

    @DeleteMapping("/post/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        String message =  "Post with id "+postId+" deleted succesfully !";
        return new ApiResponse(message,true);
    }

    //update post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto udpatedPost = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(udpatedPost,HttpStatus.OK);
    }

    //search operation using title
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String keyword){
        List<PostDto> postDtoList = postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }
    //below method is used to seach posts by content using custom query method in repo
    @GetMapping("/posts/search/content/{keyword}")
    public ResponseEntity<List<PostDto>> searchByContent(@PathVariable String keyword){
        List<PostDto> postDtoList = postService.searchByContent(keyword);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatedPost = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }


    //method to serve files
    @GetMapping(value = "/profiles/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException{
        InputStream resource = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
