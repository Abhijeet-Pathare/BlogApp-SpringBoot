package com.codesoft.blog.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

}
