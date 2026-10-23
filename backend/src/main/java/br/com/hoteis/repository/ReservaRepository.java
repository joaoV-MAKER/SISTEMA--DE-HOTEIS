package br.com.hoteis.repository;
import br.com.hoteis.model.*;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.*;
public interface ReservaRepository extends JpaRepository<Reserva,Long> {
 boolean existsByQuartoId(Long id);
 boolean existsByHospedeId(Long id);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select r from Reserva r where r.id=:id") Optional<Reserva> bloquear(Long id);
 @Query("select count(r) from Reserva r where r.quarto.id=:quartoId and r.status <> br.com.hoteis.model.StatusReserva.CANCELADA and r.entrada < :saida and r.saida > :entrada and (:ignorar is null or r.id <> :ignorar)") long conflitos(Long quartoId, LocalDate entrada, LocalDate saida, Long ignorar);
}