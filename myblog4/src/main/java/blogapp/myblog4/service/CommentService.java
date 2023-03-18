package blogapp.myblog4.service;

import blogapp.myblog4.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getAllComments(long postId);

    CommentDto getByCommentId(long postId, long commentId);

    void deleteComment(long postId,long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
}
