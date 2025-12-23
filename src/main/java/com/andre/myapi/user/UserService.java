package com.andre.myapi.user;

import com.andre.myapi.exception.InvalidPasswordException;
import com.andre.myapi.exception.UserNotFoundException;
import com.andre.myapi.security.PasswordHasher;
import com.andre.myapi.user.dto.PasswordUpdateDTO;
import com.andre.myapi.user.dto.UserRequestDTO;
import com.andre.myapi.user.dto.UserResponseDTO;
import com.andre.myapi.user.dto.UserUpdateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        User user = toEntity(dto);
        User saved = repository.create(user);
        return toResponseDTO(saved);
    }

    public UserResponseDTO getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return toResponseDTO(user);
    }

    public List<UserResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setActive(dto.getActive());

        User updated = repository.update(existing);
        return toResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    @Transactional
    public void updatePassword(Long id, PasswordUpdateDTO dto) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // 1️⃣ Check current password
        if (!BCrypt.checkpw(dto.getCurrentPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException();
        }

        // 2️⃣ Hash and store new password
        user.setPasswordHash(BCrypt.hashpw(dto.getNewPassword(), BCrypt.gensalt()));

        repository.update(user);
    }

    private UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    private User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setActive(dto.getActive() != null ? dto.getActive() : true);
        user.setPasswordHash(PasswordHasher.hash(dto.getPassword()));

        return user;
    }
}
