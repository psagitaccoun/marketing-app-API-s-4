package blogapp.myblog4.controller;

import blogapp.myblog4.payload.PostDto;
import blogapp.myblog4.payload.PostResponse;
import blogapp.myblog4.service.PostService;
import blogapp.myblog4.util.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/posts/")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("createPost")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        ResponseEntity<PostDto> postDtoResponseEntity = new ResponseEntity<>(dto, HttpStatus.CREATED);
        return postDtoResponseEntity;
    }

    @GetMapping("getAllPosts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = Default.pageNo,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = Default.pageSize,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue =Default.sortBy,required = false ) String sortBy,
            @RequestParam(value="sortDir",defaultValue =Default.sortDir,required = false ) String sortDir

    ){
        PostResponse allPosts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return  allPosts;
    }

    @GetMapping("getPost/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto postById = postService.getPostById(id);
        return new ResponseEntity<>(postById,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("updatePost/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable("id") long posId,
            @RequestBody PostDto postDto
    ){
        PostDto postDto1 = postService.updatePost(posId, postDto);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("deletePost/{id}")
    public ResponseEntity<String> deletePost( @PathVariable("id") long posId){
        postService.deletePost(posId);
        return new ResponseEntity<>("Post is deleted!!!",HttpStatus.OK);
    }
}
