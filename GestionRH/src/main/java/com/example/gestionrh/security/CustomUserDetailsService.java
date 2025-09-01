package com.example.gestionrh.security;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.repository.CollaborateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CollaborateurRepository collaborateurRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(CollaborateurRepository collaborateurRepository, PasswordEncoder passwordEncoder) {
        this.collaborateurRepository = collaborateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collaborateur c = collaborateurRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + c.getRole().name()));
        // For demo, use email as username and fixed password "password"
        return User.withUsername(c.getEmail())
                .password(passwordEncoder.encode("password"))
                .authorities(authorities)
                .build();
    }
}

