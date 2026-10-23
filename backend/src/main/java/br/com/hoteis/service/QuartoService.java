package br.com.hoteis.service;
import br.com.hoteis.dto.Dados.QuartoInput;
import br.com.hoteis.model.Quarto;
import br.com.hoteis.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import java.util.List;
@Service @Transactional public class QuartoService {
 private final QuartoRepository quartos; private final ReservaRepository reservas;
 public QuartoService(QuartoRepository q,ReservaRepository r){quartos=q;reservas=r;}
 public List<Quarto> listar(){return quartos.findAll(Sort.by("numero"));}
 public Quarto buscar(Long id){return quartos.findById(id).orElseThrow(()->new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,"Quarto não encontrado"));}
 public List<Quarto> disponiveis(LocalDate entrada,LocalDate saida,Long ignorar){ReservaService.validarPeriodo(entrada,saida);return listar().stream().filter(q->q.ativo && reservas.conflitos(q.id,entrada,saida,ignorar)==0).toList();}
 public Quarto salvar(Long id,QuartoInput d){Quarto q=id==null?new Quarto():quartos.bloquear(id).orElseThrow(()->new RegraException("Quarto não encontrado"));q.numero=d.numero().trim();q.tipo=d.tipo().trim();q.capacidade=d.capacidade();q.diaria=d.diaria();q.ativo=d.ativo();return quartos.saveAndFlush(q);}
 public void excluir(Long id){Quarto q=quartos.bloquear(id).orElseThrow(()->new RegraException("Quarto não encontrado"));if(reservas.existsByQuartoId(id))throw new RegraException("Quarto vinculado a reservas. Desative-o para preservar o histórico.");quartos.delete(q);quartos.flush();}
}