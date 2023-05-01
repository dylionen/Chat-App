package pl.training.chat.login;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.training.chat.model.LoggedUser;

import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public UserView findUserViewByName(String name) {
        User user = userRepository.findByName(name);
        if (user != null) {
            return new UserView()
                    .setUserName(user.getName())
                    .setAvatar(user.getAvatar() != null ? new String(user.getAvatar(), StandardCharsets.UTF_8) : null);
        }
        return null;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        userDto.setActive(user.getActive());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void updateAvatar(String avatar, String name) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByName(name));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAvatar(avatar.getBytes());
            userRepository.save(user);
        }
    }
}
