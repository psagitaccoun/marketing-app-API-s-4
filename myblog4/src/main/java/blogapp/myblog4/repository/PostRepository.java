package blogapp.myblog4.repository;

import blogapp.myblog4.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
