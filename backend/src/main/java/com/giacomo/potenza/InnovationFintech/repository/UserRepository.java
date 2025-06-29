package com.giacomo.potenza.InnovationFintech.repository;


import com.giacomo.potenza.InnovationFintech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Trova un utente per email
     */
    Optional<User> findByEmail(String email);

    /**
     * Trova utenti per ruolo
     */
    List<User> findByRuolo(String ruolo);

    /**
     * Trova utenti per nome (case insensitive, ricerca parziale)
     */
    List<User> findByNomeContainingIgnoreCase(String nome);

    /**
     * Trova utenti per cognome (case insensitive, ricerca parziale)
     */
    List<User> findByCognomeContainingIgnoreCase(String cognome);

    /**
     * Trova utenti per nome E cognome (case insensitive, ricerca parziale)
     */
    List<User> findByNomeContainingIgnoreCaseAndCognomeContainingIgnoreCase(String nome, String cognome);

    /**
     * Verifica se esiste un utente con una specifica email
     */
    boolean existsByEmail(String email);

    /**
     * Trova utenti per ruolo ordinati per cognome e nome
     */
    List<User> findByRuoloOrderByCognomeAscNomeAsc(String ruolo);

    /**
     * Query personalizzata: trova utenti il cui nome o cognome contiene il termine di ricerca
     */
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.cognome) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> findByNomeOrCognomeContaining(@Param("searchTerm") String searchTerm);

    /**
     * Query personalizzata: trova utenti per ruolo e termine di ricerca
     */
    @Query("SELECT u FROM User u WHERE u.ruolo = :ruolo AND " +
            "(LOWER(u.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.cognome) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<User> findByRuoloAndNomeOrCognomeContaining(@Param("ruolo") String ruolo,
                                                     @Param("searchTerm") String searchTerm);

    /**
     * Conta utenti per ruolo
     */
    long countByRuolo(String ruolo);

    /**
     * Query nativa: trova utenti con email non null
     */
    @Query(value = "SELECT * FROM user WHERE email IS NOT NULL AND email != ''", nativeQuery = true)
    List<User> findUsersWithEmail();

    /**
     * Query personalizzata: ricerca completa nome utente (nome + cognome)
     */
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(CONCAT(u.nome, ' ', u.cognome)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    List<User> findByFullNameContaining(@Param("fullName") String fullName);

    /**
     * Trova utenti ordinati per cognome e nome
     */
    List<User> findAllByOrderByCognomeAscNomeAsc();

    /**
     * Query personalizzata per statistiche: conta utenti per ogni ruolo
     */
    @Query("SELECT u.ruolo, COUNT(u) FROM User u GROUP BY u.ruolo")
    List<Object[]> countUsersByRole();

    /**
     * Trova utenti con password non impostata (null o vuota)
     */
    @Query("SELECT u FROM User u WHERE u.password IS NULL OR u.password = ''")
    List<User> findUsersWithoutPassword();
}