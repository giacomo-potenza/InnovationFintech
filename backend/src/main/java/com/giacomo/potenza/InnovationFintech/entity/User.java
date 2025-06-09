package com.giacomo.potenza.InnovationFintech.entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    private String matricola;
    private String nome;
    private String cognome;
    private String password;
    private String email;
    private String ruolo;

    // Costruttori
    public User() {}

    public User(String matricola, String nome, String cognome, String password, String email, String ruolo) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.email = email;
        this.ruolo = ruolo;
    }

    // Getter e Setter
    // (Aggiungi qui i metodi getter e setter)
}
