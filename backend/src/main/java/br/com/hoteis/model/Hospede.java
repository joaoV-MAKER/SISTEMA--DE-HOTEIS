package br.com.hoteis.model;
import jakarta.persistence.*;
@Entity @Table(name="hospedes") public class Hospede {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false) public String nome;
 @Column(nullable=false,unique=true) public String documento;
 @Column(nullable=false) public String email;
 @Column(nullable=false) public String telefone;
 public Hospede() {}
}