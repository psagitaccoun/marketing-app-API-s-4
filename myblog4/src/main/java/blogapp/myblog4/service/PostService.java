package blogapp.myblog4.service;

import blogapp.myblog4.payload.PostDto;
import blogapp.myblog4.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(long posId, PostDto postDto);

    void deletePost(long posId);
}
