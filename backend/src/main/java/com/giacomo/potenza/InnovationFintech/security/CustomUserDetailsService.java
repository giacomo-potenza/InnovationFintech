
package com.giacomo.potenza.InnovationFintech.security;

import com.giacomo.potenza.InnovationFintech.entity.User;
import com.giacomo.potenza.InnovationFintech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public CustomUserDetailsService(UserRepository repo) {
        this.userRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String matricola) throws UsernameNotFoundException {
        User user = userRepository.findById(matricola)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + matricola));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.matricola)
                .password(user.password) // Spring confronterà con questa
                .authorities(Collections.emptyList())
                .build();
    }

}

