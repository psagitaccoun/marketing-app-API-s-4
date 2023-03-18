package blogapp.myblog4.controller;

import blogapp.myblog4.payload.CommentDto;
import blogapp.myblog4.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts/comments/")
public class CommentController {

    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{postId}/createComment")
    public ResponseEntity<Object> createComment(
            @PathVariable("postId") long postId,
            @Valid @RequestBody CommentDto commentDto,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return  new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @GetMapping("{postId}/getAllComments")
    public List<CommentDto> getAllComments(@PathVariable("postId") long postId){
        List<CommentDto> allComments = commentService.getAllComments(postId);
        return allComments;
    }
    @GetMapping("{postId}/{commentId}/getCommentById")
    public ResponseEntity<CommentDto> getCommentById(
        @PathVariable("postId") long postId,
        @PathVariable("commentId") long commentId
    ){
        CommentDto byCommentId = commentService.getByCommentId(postId, commentId);
        return new ResponseEntity<>(byCommentId,HttpStatus.OK);
    }

    @DeleteMapping("{postId}/{commentId}/deleteComment")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId
    ){
    commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("comment deleted!!!",HttpStatus.OK);
    }

    @PutMapping("{postId}/{commentId}/updateComment")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto commentDto1 = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(commentDto1,HttpStatus.OK);
    }
}
