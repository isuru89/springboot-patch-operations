package io.github.isuru89.sbpatch.service;

import io.github.isuru89.sbpatch.dto.UserDTO;
import io.github.isuru89.sbpatch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO addUser(UserDTO user) {
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(user.getCreatedAt());

        return this.userRepository.saveAndFlush(user);
    }

    public Optional<UserDTO> getUserById(Long userId) {
        return this.userRepository.findById(userId);
    }

    public UserDTO editUser(UserDTO userObj) {
        userObj.setUpdatedAt(System.currentTimeMillis());

        return this.userRepository.saveAndFlush(userObj);
    }
}
