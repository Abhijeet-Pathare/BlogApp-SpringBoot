package com.codesoft.blog.controllers;

import com.codesoft.blog.entities.Post;
import com.codesoft.blog.payloads.ApiResponse;
import com.codesoft.blog.payloads.PostDto;
import com.codesoft.blog.payloads.PostResponse;
import com.codesoft.blog.services.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

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
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                                    @RequestParam(value="pageSize",defaultValue = "10",required = false) Integer pageSize,
                                                    @RequestParam(value="sortBy", defaultValue="postId",required = false) String sortBy){
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
}
