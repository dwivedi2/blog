package com.blog.service.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }




    @Override
    public CommentDto saveComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + id)
        );

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

         return mapToDto(newComment);


    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post not found with id.."+postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("comment not found with id.."+commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
//        postId=2
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post not found with id.."+postId)
        );

//        comment.getpost().getpostId()  2
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("comment not found with id.."+commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updateComment = commentRepository.save(comment);
        return mapToDto(updateComment);

    }

    @Override
    public void deleteByCommentId(long postId, long commentId) {
        commentRepository.deleteById(commentId);
    }

    Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        return comment;

    }

    CommentDto mapToDto(Comment comment){
        CommentDto commentdto = new CommentDto();
        commentdto.setId(comment.getId());
        commentdto.setBody(comment.getBody());
        commentdto.setEmail(comment.getEmail());
        commentdto.setName(comment.getName());
        return commentdto;

    }
}
