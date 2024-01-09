package com.codesoft.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesoft.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
