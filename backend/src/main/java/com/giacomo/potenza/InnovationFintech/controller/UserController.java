package com.giacomo.potenza.InnovationFintech.controller;


import com.giacomo.potenza.InnovationFintech.entity.User;
import com.giacomo.potenza.InnovationFintech.service.UserService;
//import com.giacomo.potenza.InnovationFintech.dto.LoginRequest;
//import com.giacomo.potenza.InnovationFintech.dto.PasswordUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*import javax.validation.Valid;*/
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * GET /api/users - Recupera tutti gli utenti
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/{matricola} - Recupera utente per matricola
     */
    @GetMapping("/{matricola}")
    public ResponseEntity<User> getUserByMatricola(@PathVariable String matricola) {
        User user = userService.getUserByMatricola(matricola);
        return ResponseEntity.ok(user);
    }

    /**
     * GET /api/users/email/{email} - Recupera utente per email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/users - Crea nuovo utente
     * @PostMapping
     *     public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
     *         User createdUser = userService.createUser(user);
     *         return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
     *     }
     */


    /**
     * PUT /api/users/{matricola} - Aggiorna utente esistente
     * @PutMapping("/{matricola}")
     *     public ResponseEntity<User> updateUser(@PathVariable String matricola,
     *                                            @Valid @RequestBody User userDetails) {
     *         User updatedUser = userService.updateUser(matricola, userDetails);
     *         return ResponseEntity.ok(updatedUser);
     *     }
     */


    /**
     * PATCH /api/users/{matricola}/password - Aggiorna password
     * @PatchMapping("/{matricola}/password")
     *     public ResponseEntity<Map<String, String>> updatePassword(
     *             @PathVariable String matricola,
     *             @Valid @RequestBody PasswordUpdateRequest passwordRequest) {
     *
     *         userService.updatePassword(matricola, passwordRequest.getNewPassword());
     *
     *         Map<String, String> response = new HashMap<>();
     *         response.put("message", "Password aggiornata con successo");
     *         return ResponseEntity.ok(response);
     *     }
     */


    /**
     * POST /api/users/login - Verifica credenziali di login
     * @PostMapping("/login")
     *     public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
     *         boolean isValid = userService.verifyLogin(loginRequest.getEmail(), loginRequest.getPassword());
     *
     *         Map<String, Object> response = new HashMap<>();
     *         response.put("success", isValid);
     *
     *         if (isValid) {
     *             User user = userService.getUserByEmail(loginRequest.getEmail()).orElse(null);
     *             response.put("message", "Login effettuato con successo");
     *             response.put("user", user);
     *             return ResponseEntity.ok(response);
     *         } else {
     *             response.put("message", "Credenziali non valide");
     *             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
     *         }
     *     }
     */


    /**
     * DELETE /api/users/{matricola} - Elimina utente
     */
    @DeleteMapping("/{matricola}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String matricola) {
        userService.deleteUser(matricola);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Utente eliminato con successo");
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/users/exists/{matricola} - Verifica se utente esiste
     */
    @GetMapping("/exists/{matricola}")
    public ResponseEntity<Map<String, Boolean>> userExists(@PathVariable String matricola) {
        boolean exists = userService.userExists(matricola);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/users/role/{ruolo} - Recupera utenti per ruolo
     */
    @GetMapping("/role/{ruolo}")
    public ResponseEntity<List<User>> getUsersByRuolo(@PathVariable String ruolo) {
        List<User> users = userService.getUsersByRuolo(ruolo);
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/search - Ricerca utenti per nome e/o cognome
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome) {

        List<User> users = userService.searchUsersByName(nome, cognome);
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/count - Conta numero totale di utenti
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countUsers() {
        long count = userService.countUsers();

        Map<String, Long> response = new HashMap<>();
        response.put("totalUsers", count);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/users/{matricola}/toggle-status - Attiva/disattiva utente
     */
    @PatchMapping("/{matricola}/toggle-status")
    public ResponseEntity<User> toggleUserStatus(@PathVariable String matricola) {
        User user = userService.toggleUserStatus(matricola);
        return ResponseEntity.ok(user);
    }

    /**
     * GET /api/users/stats - Statistiche utenti
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getUserStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.countUsers());
        // Aggiungi altre statistiche se necessario

        return ResponseEntity.ok(stats);
    }
}