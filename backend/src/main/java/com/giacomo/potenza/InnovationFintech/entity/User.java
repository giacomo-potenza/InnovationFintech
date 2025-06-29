package com.giacomo.potenza.InnovationFintech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "[User]", uniqueConstraints = {
        @UniqueConstraint(columnNames = "matricola")
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @Id
    @Column(length = 20, nullable = false)
    public String matricola;

    @Column(length = 255)
    public String nome;

    @Column(length = 255)
    public String cognome;

    @Column(nullable = false, length = 255)
    public String password;

    @Column(length = 255)
    public String email;

    @Column(length = 255)
    public String ruolo;

}
