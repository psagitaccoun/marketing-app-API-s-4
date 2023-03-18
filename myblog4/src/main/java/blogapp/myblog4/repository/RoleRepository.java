package blogapp.myblog4.repository;

import blogapp.myblog4.entity.Role;
import blogapp.myblog4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
