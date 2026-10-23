package br.com.hoteis.service;
import br.com.hoteis.dto.Dados.HospedeInput;
import br.com.hoteis.model.Hospede;
import br.com.hoteis.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.util.List;
@Service @Transactional public class HospedeService {
 private final HospedeRepository hospedes;private final ReservaRepository reservas;
 public HospedeService(HospedeRepository h,ReservaRepository r){hospedes=h;reservas=r;}
 public List<Hospede> listar(){return hospedes.findAll(Sort.by("nome"));}
 public Hospede buscar(Long id){return hospedes.findById(id).orElseThrow(()->new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,"Hóspede não encontrado"));}
 public Hospede salvar(Long id,HospedeInput d){Hospede h=id==null?new Hospede():buscar(id);h.nome=d.nome().trim();h.documento=d.documento().trim();h.email=d.email().trim();h.telefone=d.telefone().trim();return hospedes.saveAndFlush(h);}
 public void excluir(Long id){Hospede h=buscar(id);if(reservas.existsByHospedeId(id))throw new RegraException("Hóspede vinculado a reservas. Exclua as reservas antes de remover o cadastro.");hospedes.delete(h);hospedes.flush();}
}