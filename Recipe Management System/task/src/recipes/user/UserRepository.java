package recipes.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    public Optional<AppUser> findAppUserByUsername(String username);
}
