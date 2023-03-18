package blogapp.myblog4.service.impl;

import blogapp.myblog4.entity.Post;
import blogapp.myblog4.exception.ResourceNotFoundException;
import blogapp.myblog4.payload.PostDto;
import blogapp.myblog4.payload.PostResponse;
import blogapp.myblog4.repository.PostRepository;
import blogapp.myblog4.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
private ModelMapper mapper;
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post post1 = postRepository.save(post);
        PostDto postDto1 = mapToDto(post1);
        return postDto1;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);
      //  List<Post> posts = postRepository.findAll();
        // List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
      //  return dtos;
        Page<Post> post = postRepository.findAll(pageable);
        List<Post> posts = post.getContent();
        List<PostDto> dto = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContents( dto);
        postResponse.setTotalNumberPages(post.getTotalPages());
        postResponse.setTotalElements((int) post.getTotalElements());
        postResponse.setSizeOfPage(post.getSize());
        postResponse.setLastPage(post.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "PostId", id)
        );
        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(long posId, PostDto postDto) {
        Post post = postRepository.findById(posId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "PostId", posId)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(post.getContent());
        Post post1 = postRepository.save(post);
        PostDto postDto1 = mapToDto(post1);
        return postDto1;
    }

    @Override
    public void deletePost(long posId) {
        Post post = postRepository.findById(posId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "PostId", posId)
        );
        postRepository.delete(post);
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }

    private PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }
}
