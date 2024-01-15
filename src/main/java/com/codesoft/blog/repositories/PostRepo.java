package com.codesoft.blog.repositories;

import com.codesoft.blog.entities.Category;
import com.codesoft.blog.entities.Post;
import com.codesoft.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    //we want posts of a user
    //derived query methods
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);



}
