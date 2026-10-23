package br.com.hoteis.service;
import br.com.hoteis.dto.Dados.ReservaInput;
import br.com.hoteis.model.*;
import br.com.hoteis.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.util.List;
@Service @Transactional public class ReservaService {
 private final ReservaRepository reservas;private final QuartoRepository quartos;private final HospedeRepository hospedes;
 public ReservaService(ReservaRepository r,QuartoRepository q,HospedeRepository h){reservas=r;quartos=q;hospedes=h;}
 public List<Reserva> listar(){return reservas.findAll(Sort.by(Sort.Direction.DESC,"id"));}
 public Reserva buscar(Long id){return reservas.findById(id).orElseThrow(()->new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,"Reserva não encontrada"));}
 public static void validarPeriodo(LocalDate entrada,LocalDate saida){if(entrada==null||saida==null||!saida.isAfter(entrada))throw new RegraException("A saída deve ser posterior à entrada.");if(entrada.isBefore(LocalDate.now()))throw new RegraException("A entrada não pode estar no passado.");}
 public Reserva salvar(Long id,ReservaInput d){
  validarPeriodo(d.entrada(),d.saida());
  Reserva r=id==null?new Reserva():reservas.bloquear(id).orElseThrow(()->new RegraException("Reserva não encontrada"));
  if(r.status!=StatusReserva.CONFIRMADA)throw new RegraException("Apenas reservas confirmadas podem ser editadas.");
  Quarto q=quartos.bloquear(d.quartoId()).orElseThrow(()->new RegraException("Quarto não encontrado"));
  if(!q.ativo)throw new RegraException("Quarto indisponível para reservas.");
  if(reservas.conflitos(q.id,d.entrada(),d.saida(),id)>0)throw new RegraException("O quarto já está reservado nesse período.");
  Hospede h=hospedes.findById(d.hospedeId()).orElseThrow(()->new RegraException("Hóspede não encontrado"));
  boolean manterDiaria=id!=null && r.quarto.id.equals(q.id);
  r.diaria=manterDiaria?r.diaria:q.diaria;r.quarto=q;r.hospede=h;r.entrada=d.entrada();r.saida=d.saida();r.valorTotal=r.diaria.multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(r.entrada,r.saida)));
  return reservas.saveAndFlush(r);
 }
 public Reserva transicao(Long id,String acao){Reserva r=reservas.bloquear(id).orElseThrow(()->new RegraException("Reserva não encontrada"));
  switch(acao){
   case "check-in" -> {if(r.status!=StatusReserva.CONFIRMADA||LocalDate.now().isBefore(r.entrada)||!LocalDate.now().isBefore(r.saida))throw new RegraException("Check-in permitido somente durante o período de uma reserva confirmada.");r.status=StatusReserva.HOSPEDADO;}
   case "check-out" -> {if(r.status!=StatusReserva.HOSPEDADO)throw new RegraException("Faça o check-in antes do check-out.");r.status=StatusReserva.FINALIZADA;}
   case "cancelar" -> {if(r.status!=StatusReserva.CONFIRMADA)throw new RegraException("Apenas reservas confirmadas podem ser canceladas.");r.status=StatusReserva.CANCELADA;}
   default -> throw new RegraException("Ação inválida.");
  }return reservas.saveAndFlush(r);
 }
 public void excluir(Long id){Reserva r=reservas.bloquear(id).orElseThrow(()->new RegraException("Reserva não encontrada"));if(r.status==StatusReserva.HOSPEDADO)throw new RegraException("Finalize a hospedagem antes de excluir.");reservas.delete(r);reservas.flush();}
}