package blogapp.myblog4.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;
    @NotNull
    @Size(min = 2,message = "name should be minimum 2 characters")
    private String name;
    @Email(message = "email format is invalid")
    private String email;
    @NotNull
    @Size(min=2,max=200)
    private String body;
}
