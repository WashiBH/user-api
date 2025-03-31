package com.bci.proyecto.service.impl;

import com.bci.proyecto.configuration.JwtTokenProvider;
import com.bci.proyecto.dto.PhoneDto;
import com.bci.proyecto.dto.request.UserCreateRequest;
import com.bci.proyecto.dto.request.UserPartialUpdateRequest;
import com.bci.proyecto.dto.request.UserUpdateRequest;
import com.bci.proyecto.dto.response.UserResponse;
import com.bci.proyecto.exception.EmailAlreadyExistsException;
import com.bci.proyecto.exception.ResourceNotFoundException;
import com.bci.proyecto.model.PhoneModel;
import com.bci.proyecto.model.UserModel;
import com.bci.proyecto.repository.UserRepository;
import com.bci.proyecto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getCorreo())) {
            throw new EmailAlreadyExistsException("El correo ya está registrado");
        }

        UserModel user = new UserModel();
        user.setName(userRequest.getNombre());
        user.setEmail(userRequest.getCorreo());
        user.setPassword(passwordEncoder.encode(userRequest.getContrasena()));

        // Mapear teléfonos
        if (userRequest.getTelefonos() != null) {
            user.setPhones(userRequest.getTelefonos().stream()
                    .map(phoneDto -> {
                        PhoneModel phone = new PhoneModel();
                        phone.setUser(user);
                        phone.setNumber(phoneDto.getNumero());
                        phone.setCityCode(phoneDto.getCodigoCiudad());
                        phone.setCountryCode(phoneDto.getCodigoPais());
                        return phone;
                    })
                    .toList());
        }

        // Generar token JWT
        String token = tokenProvider.generateToken(user.getEmail());
        user.setToken(token);

        UserModel savedUser = userRepository.save(user);
        return mapUserResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(UUID id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return mapUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UserUpdateRequest userUpdateRequest) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        // Actualizar campos permitidos
        if (userUpdateRequest.getNombre() != null) {
            user.setName(userUpdateRequest.getNombre());
        }

        if (userUpdateRequest.getContrasena() != null) {
            user.setPassword(passwordEncoder.encode(userUpdateRequest.getContrasena()));
        }

        // Actualizar teléfonos si se proporcionan
        if (userUpdateRequest.getTelefonos() != null) {
            // Eliminar teléfonos existentes
            user.getPhones().clear();

            // Añadir nuevos teléfonos
            userUpdateRequest.getTelefonos().forEach(phoneDto -> {
                PhoneModel phone = new PhoneModel();
                phone.setUser(user);
                user.getPhones().add(phone);
            });
        }

        user.setAuditModificationDate(LocalDateTime.now());
        UserModel updatedUser = userRepository.save(user);
        return mapUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public UserResponse partialUpdateUser(UUID id, UserPartialUpdateRequest partialUpdateRequest) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        // Actualizar solo los campos proporcionados
        if (partialUpdateRequest.getNombre() != null) {
            user.setName(partialUpdateRequest.getNombre());
        }

        if (partialUpdateRequest.getContrasena() != null) {
            user.setPassword(passwordEncoder.encode(partialUpdateRequest.getContrasena()));
        }

        // Actualizar teléfonos si se proporcionan
        if (partialUpdateRequest.getTelefonos() != null && !partialUpdateRequest.getTelefonos().isEmpty()) {
            user.getPhones().clear();
            partialUpdateRequest.getTelefonos().forEach(phoneDto -> {
                PhoneModel phone = new PhoneModel();
                phone.setUser(user);
                user.getPhones().add(phone);
            });
        }

        user.setAuditModificationDate(LocalDateTime.now());
        UserModel updatedUser = userRepository.save(user);
        return mapUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        userRepository.delete(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapUserResponse)
                .toList();
    }

    private UserResponse mapUserResponse(UserModel userModel){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userModel.getUserId());
        userResponse.setNombre(userModel.getName());
        userResponse.setCorreo(userModel.getEmail());
        userResponse.setCreado(userModel.getAuditCreatedDate());
        userResponse.setActivo(userModel.getActive());
        userResponse.setModificado(userModel.getAuditModificationDate());
        userResponse.setUltimoLogin(userModel.getLastLogin());
        userResponse.setTelefonos(userModel.getPhones().stream()
                .map(phoneModel -> {
                    PhoneDto phone = new PhoneDto();
                    phone.setNumero(phoneModel.getNumber());
                    phone.setCodigoCiudad(phoneModel.getCityCode());
                    phone.setCodigoPais(phoneModel.getCountryCode());
                    return phone;
                }).toList());
        return userResponse;
    }
}
