package com.giacomo.potenza.InnovationFintech.entity;


import jakarta.persistence.*;

@Entity
public class RichiestaFinanziamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFinanziamento;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_prodotto")
    private TipoProdotto tipoProdotto;

    private String matricola;
    private Double importo;
    private Date dataRichiesta;
    private String stato;

    // Costruttori
    public RichiestaFinanziamento() {}

    public RichiestaFinanziamento(Cliente cliente, TipoProdotto tipoProdotto, String matricola, Double importo, Date dataRichiesta, String stato) {
        this.cliente = cliente;
        this.tipoProdotto = tipoProdotto;
        this.matricola = matricola;
        this.importo = importo;
        this.dataRichiesta = dataRichiesta;
        this.stato = stato;
    }

    // Getter e Setter
    // (Aggiungi qui i metodi getter e setter)
}
