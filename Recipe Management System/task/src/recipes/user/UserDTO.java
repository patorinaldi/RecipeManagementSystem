package recipes.user;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @Email
    private String username;
    @Size(min = 8)
    private String password;
}
