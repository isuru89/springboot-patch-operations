package io.github.isuru89.sbpatch.repository;

import io.github.isuru89.sbpatch.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDTO, Long> {
}
