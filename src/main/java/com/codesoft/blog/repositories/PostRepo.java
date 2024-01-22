package com.codesoft.blog.repositories;

import com.codesoft.blog.entities.Category;
import com.codesoft.blog.entities.Post;
import com.codesoft.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    //we want posts of a user
    //derived query methods
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
//generates a like query using the below method
    List<Post> findByTitleContaining(String title);
    //List<Post> findByContentContaining(String title);

    @Query("select p from Post p where p.content like :key")
    List<Post> searchByContent(@Param("key") String keyword);


}
