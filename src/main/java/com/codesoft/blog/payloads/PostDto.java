package com.codesoft.blog.payloads;

import com.codesoft.blog.entities.Category;
import com.codesoft.blog.entities.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonSerialize//(using = PostDtoSerializer.class)//--> added dependency jacksn databind for serialization purpose
public class PostDto {

    private String title;
    private String content;
    private String imageName;
    private Date addedDate;

    private Category category;
    private User user;

}
