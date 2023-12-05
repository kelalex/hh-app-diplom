package hhapp.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import hhapp.domain.jpa.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserID(Long userID);

    User findByUsername(String username);
}
