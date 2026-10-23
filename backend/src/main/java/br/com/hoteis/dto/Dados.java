package br.com.hoteis.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
public final class Dados {
 public record QuartoInput(@NotBlank @Size(max=10) String numero, @NotBlank @Size(max=40) String tipo, @NotNull @Min(1) @Max(10) Integer capacidade, @NotNull @DecimalMin("0.01") @Digits(integer=10,fraction=2) BigDecimal diaria, @NotNull Boolean ativo) {}
 public record HospedeInput(@NotBlank @Size(min=3,max=120) String nome, @NotBlank @Size(min=5,max=30) String documento, @NotBlank @Email @Size(max=120) String email, @NotBlank @Pattern(regexp="[+0-9 ()-]{8,25}",message="Telefone inválido") String telefone) {}
 public record ReservaInput(@NotNull @Positive Long quartoId, @NotNull @Positive Long hospedeId, @NotNull LocalDate entrada, @NotNull LocalDate saida) {}
}