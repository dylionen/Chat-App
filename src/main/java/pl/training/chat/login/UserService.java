package pl.training.chat.login;
import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

    User findUserByName(String name);

    void updateAvatar(String avatar, String name);
}