package com.zzxkj.blog.service;

import com.zzxkj.blog.Entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
