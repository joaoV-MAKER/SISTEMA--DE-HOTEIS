package br.com.hoteis.repository;
import br.com.hoteis.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
public interface HospedeRepository extends JpaRepository<Hospede,Long> {}