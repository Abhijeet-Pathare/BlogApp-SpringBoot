package com.codesoft.blog.services;

import com.codesoft.blog.entities.Post;
import com.codesoft.blog.payloads.PostDto;

import java.util.List;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    //update
    Post updatePost(PostDto postDto,Integer postId);

    //delete
    void deletePost(Integer postId);

    //gat all posts
    List<Post> getAllPost();

    //get single post by id
    Post getPostById(Integer postId);

    //get all posts by category
    List<Post> getPostsByCategory(Integer categoryId);

    //get all posts by user
    List<Post> getPostsByUser(Integer userId);

    //search posts
    List<Post> searchPosts(String keyword);

}
