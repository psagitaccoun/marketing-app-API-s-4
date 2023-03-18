package blogapp.myblog4.service.impl;

import blogapp.myblog4.entity.Comment;
import blogapp.myblog4.entity.Post;
import blogapp.myblog4.exception.BlogApiException;
import blogapp.myblog4.exception.ResourceNotFoundException;
import blogapp.myblog4.payload.CommentDto;
import blogapp.myblog4.repository.CommentRepository;
import blogapp.myblog4.repository.PostRepository;
import blogapp.myblog4.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository,ModelMapper mapper,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.mapper=mapper;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        CommentDto commentDto1 = mapToDto(newComment);
        return commentDto1;
    }

    @Override
    public List<CommentDto> getAllComments(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> listDto = comments.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return listDto;
    }

    @Override
    public CommentDto getByCommentId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId));

        if (comment.getPost().getId()!=post.getId()){
            throw  new BlogApiException("Comment is not Present for this post");
        };

        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public void deleteComment(long postId,long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId));

        if (comment.getPost().getId()!=post.getId()){
            throw  new BlogApiException("Comment is not Present for this post");
        };
        commentRepository.delete(comment);

    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId));

        if (comment.getPost().getId()!=post.getId()){
            throw  new BlogApiException("Comment is not Present for this post");
        };

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment newComment = commentRepository.save(comment);
        CommentDto commentDto1 = mapToDto(newComment);


        return commentDto1;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }
}
