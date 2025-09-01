package com.example.gestionrh.security;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.repository.CollaborateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CollaborateurRepository collaborateurRepository;

    public CustomUserDetailsService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collaborateur c = collaborateurRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + c.getRole().name()));
        // For demo, use email as username and fixed password "password" encoded at runtime
        return User.withUsername(c.getEmail())
                .password("{noop}password")
                .authorities(authorities)
                .build();
    }
}

