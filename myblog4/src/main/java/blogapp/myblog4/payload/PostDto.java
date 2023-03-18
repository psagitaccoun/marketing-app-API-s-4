package blogapp.myblog4.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotNull
    @Size(min=2)
    private String title;
    private String description;
    private String content;
}
