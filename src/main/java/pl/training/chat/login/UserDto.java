package pl.training.chat.login;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String login;

    @NotEmpty(message = "Email nie może być pusty")
    @Email
    private String email;

    @NotEmpty(message = "Hasło nie możde być puste")
    private String password;

    private String repeatPassword;
    private List<String> roles;
    private Boolean active;
}