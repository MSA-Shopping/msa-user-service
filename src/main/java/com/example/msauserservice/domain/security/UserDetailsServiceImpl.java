package com.example.msauserservice.domain.security;


import com.example.msauserservice.domain.users.User;
import com.example.msauserservice.domain.users.UserRepository;
import com.example.msauserservice.global.exception.CustomException;
import com.example.msauserservice.global.exception.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        return new UserDetailsImpl(user);
    }
}
