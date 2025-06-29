package com.giacomo.potenza.InnovationFintech.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TipoProdotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdotto;
    private String nomeProdotto;
    private String descrizione;
    private Double tassoInteresse;
    private Integer durataMesi;
    private String tipoCalcolo;

    @OneToMany(mappedBy = "tipoProdotto", cascade = CascadeType.ALL)
    private List<RichiestaFinanziamento> richiesteFinanziamenti;

    // Costruttori
    public TipoProdotto() {}

    public TipoProdotto(String nomeProdotto, String descrizione, Double tassoInteresse, Integer durataMesi, String tipoCalcolo) {
        this.nomeProdotto = nomeProdotto;
        this.descrizione = descrizione;
        this.tassoInteresse = tassoInteresse;
        this.durataMesi = durataMesi;
        this.tipoCalcolo = tipoCalcolo;
    }

    // Getter e Setter
    // (Aggiungi qui i metodi getter e setter)
}
