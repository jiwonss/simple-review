package com.example.simple.user.service;

import com.example.simple.user.dto.UserDto;
import com.example.simple.user.entity.UserEntity;
import com.example.simple.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public UserEntity save(UserDto userDto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        return userRepository.save(UserEntity.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .auth(userDto.getAuth())
                .build());
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void updatePassword(UserEntity userEntity, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userEntity.setPassword(encoder.encode(password));
        userRepository.save(userEntity);
    }

    @Transactional
    public int deleteUser(String email) {
        if (existsByEmail(email)) {
            userRepository.deleteByEmail(email);
            return 1;
        }
        return 0;
    }
}
