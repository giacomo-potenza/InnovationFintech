package com.giacomo.potenza.InnovationFintech.entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    private String telefono;
    private Date dataNascita;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<RichiestaFinanziamento> richiesteFinanziamenti;

    // Costruttori
    public Cliente() {}

    public Cliente(String nome, String cognome, String codiceFiscale, String email, String telefono, Date dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.telefono = telefono;
        this.dataNascita = dataNascita;
    }

    // Getter e Setter
    // (Aggiungi qui i metodi getter e setter)
}
