package proj.auctionhousebackend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import proj.auctionhousebackend.exception.ExceptionCode;
import proj.auctionhousebackend.model.CredentialEntity;
import proj.auctionhousebackend.repository.CredentialRepository;

@RequiredArgsConstructor
public class UserDetailsServiceBean implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return credentialRepository
                .findByEmail(email)
                .map(this::getUserDetails)
                .orElseThrow(() -> new BadCredentialsException(ExceptionCode.ERR099_INVALID_CREDENTIALS.getMessage()));
    }

    private UserDetails getUserDetails(CredentialEntity user) {
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
