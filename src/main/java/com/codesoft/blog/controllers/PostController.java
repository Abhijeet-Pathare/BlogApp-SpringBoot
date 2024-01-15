package com.codesoft.blog.controllers;

import com.codesoft.blog.entities.Post;
import com.codesoft.blog.payloads.PostDto;
import com.codesoft.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}