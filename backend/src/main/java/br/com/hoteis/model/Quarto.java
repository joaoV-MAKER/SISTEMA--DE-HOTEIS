package br.com.hoteis.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="quartos") public class Quarto {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false,unique=true) public String numero;
 @Column(nullable=false) public String tipo;
 @Column(nullable=false) public Integer capacidade;
 @Column(nullable=false,precision=12,scale=2) public BigDecimal diaria;
 @Column(nullable=false) public boolean ativo=true;
 public Quarto() {}
}