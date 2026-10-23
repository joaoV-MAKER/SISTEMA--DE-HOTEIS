package br.com.hoteis.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
@Entity @Table(name="reservas") public class Reserva {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) @JoinColumn(name="quarto_id") public Quarto quarto;
 @ManyToOne(optional=false) @JoinColumn(name="hospede_id") public Hospede hospede;
 @Column(nullable=false) public LocalDate entrada;
 @Column(nullable=false) public LocalDate saida;
 @Column(nullable=false,precision=12,scale=2) public BigDecimal diaria;
 @Column(nullable=false,precision=12,scale=2) public BigDecimal valorTotal;
 @Enumerated(EnumType.STRING) @Column(nullable=false) public StatusReserva status=StatusReserva.CONFIRMADA;
 public Reserva() {}
}