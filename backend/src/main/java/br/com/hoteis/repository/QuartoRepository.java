package br.com.hoteis.repository;
import br.com.hoteis.model.Quarto;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.LockModeType;
import java.util.Optional;
public interface QuartoRepository extends JpaRepository<Quarto,Long> {
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select q from Quarto q where q.id=:id") Optional<Quarto> bloquear(Long id);
}