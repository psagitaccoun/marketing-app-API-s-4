package blogapp.myblog4.payload;

import lombok.Data;

import java.util.List;
@Data
public class PostResponse {
    private List<PostDto> contents;
    private int totalNumberPages;
    private int totalElements;
    private int sizeOfPage;
    private boolean lastPage;

}
