package com.giacomo.potenza.InnovationFintech.service;


import com.giacomo.potenza.InnovationFintech.entity.User;
import com.giacomo.potenza.InnovationFintech.repository.UserRepository;
import com.giacomo.potenza.InnovationFintech.exception.UserNotFoundException;
import com.giacomo.potenza.InnovationFintech.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*@Autowired
    private BCryptPasswordEncoder passwordEncoder;*/

    /**
     * Recupera tutti gli utenti
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Recupera un utente per matricola
     */
    public User getUserByMatricola(String matricola) {
        return userRepository.findById(matricola)
                .orElseThrow(() -> new UserNotFoundException("Utente con matricola " + matricola + " non trovato"));
    }

    /**
     * Recupera un utente per email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Crea un nuovo utente
     */
    public User createUser(User user) {
        // Verifica se l'utente esiste già
        if (userRepository.existsById(user.matricola)) {
            throw new UserAlreadyExistsException("Utente con matricola " + user.matricola + " già esistente");
        }

        // Verifica se l'email è già utilizzata
        if (user.email != null && userRepository.findByEmail(user.email).isPresent()) {
            throw new UserAlreadyExistsException("Email " + user.email + " già utilizzata");
        }

        // Cripta la password se presente
        if (user.password != null && !user.password.isEmpty()) {
            //user.password = passwordEncoder.encode(user.password);
        }

        return userRepository.save(user);
    }

    /**
     * Aggiorna un utente esistente
     */
    public User updateUser(String matricola, User userDetails) {
        User existingUser = getUserByMatricola(matricola);

        // Aggiorna i campi solo se non sono null
        if (userDetails.nome != null) {
            existingUser.nome = userDetails.nome;
        }
        if (userDetails.cognome != null) {
            existingUser.cognome = userDetails.cognome;
        }
        if (userDetails.email != null) {
            // Verifica che l'email non sia già utilizzata da un altro utente
            Optional<User> userWithEmail = userRepository.findByEmail(userDetails.email);
            if (userWithEmail.isPresent() && !userWithEmail.get().matricola.equals(matricola)) {
                throw new UserAlreadyExistsException("Email " + userDetails.email + " già utilizzata");
            }
            existingUser.email = userDetails.email;
        }
        if (userDetails.ruolo != null) {
            existingUser.ruolo = userDetails.ruolo;
        }

        return userRepository.save(existingUser);
    }

    /**
     * Aggiorna la password di un utente
     */
    public void updatePassword(String matricola, String newPassword) {
        User user = getUserByMatricola(matricola);
        //user.password = passwordEncoder.encode(newPassword);
        userRepository.save(user);
    }

    /**
     * Verifica le credenziali di login
     */
    public boolean verifyLogin(String email, String password) {
        Optional<User> user = getUserByEmail(email);
        if (user.isPresent() && user.get().password != null) {
            //return passwordEncoder.matches(password, user.get().password);
        }
        return false;
    }

    /**
     * Elimina un utente
     */
    public void deleteUser(String matricola) {
        if (!userRepository.existsById(matricola)) {
            throw new UserNotFoundException("Utente con matricola " + matricola + " non trovato");
        }
        userRepository.deleteById(matricola);
    }

    /**
     * Verifica se un utente esiste
     */
    public boolean userExists(String matricola) {
        return userRepository.existsById(matricola);
    }

    /**
     * Recupera utenti per ruolo
     */
    public List<User> getUsersByRuolo(String ruolo) {
        return userRepository.findByRuolo(ruolo);
    }

    /**
     * Conta il numero totale di utenti
     */
    public long countUsers() {
        return userRepository.count();
    }

    /**
     * Recupera utenti per nome e cognome (ricerca parziale)
     */
    public List<User> searchUsersByName(String nome, String cognome) {
        if (nome != null && cognome != null) {
            return userRepository.findByNomeContainingIgnoreCaseAndCognomeContainingIgnoreCase(nome, cognome);
        } else if (nome != null) {
            return userRepository.findByNomeContainingIgnoreCase(nome);
        } else if (cognome != null) {
            return userRepository.findByCognomeContainingIgnoreCase(cognome);
        }
        return getAllUsers();
    }

    /**
     * Attiva/disattiva un utente (se implementi un campo status)
     */
    public User toggleUserStatus(String matricola) {
        User user = getUserByMatricola(matricola);
        // Implementa la logica di attivazione/disattivazione se hai un campo status
        return userRepository.save(user);
    }
}