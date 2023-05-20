package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {


   private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("postId") long postId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.saveComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsById(@PathVariable("postId") long postId){
        List<CommentDto> commentDto = commentService.getCommentByPostId(postId);
        return commentDto;

    }

    //http://localhost:8080/api/posts/1/comments/commentId
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,@PathVariable("commentId") long commentId){
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }


    //http://localhost:8080/api/posts/1/comments/commentId
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteByCommentId(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId
    ){
        commentService.deleteByCommentId(postId,commentId);
        return new ResponseEntity<>("your data delete",HttpStatus.OK);

    }


}
