package com.codesoft.blog.services;

import com.codesoft.blog.payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId);

    void deleteComment(Integer commentId);

}
