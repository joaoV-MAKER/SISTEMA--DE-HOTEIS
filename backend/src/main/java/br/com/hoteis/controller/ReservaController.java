package br.com.hoteis.controller;
import br.com.hoteis.model.Reserva;
import br.com.hoteis.service.ReservaService;
import br.com.hoteis.dto.Dados.ReservaInput;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;
import java.time.LocalDate;
@RestController @RequestMapping("/api/reservas") public class ReservaController {
 private final ReservaService service; public ReservaController(ReservaService service){this.service=service;}
 @GetMapping public List<Reserva> listar(){return service.listar();}
 @GetMapping("/{id}") public Reserva buscar(@PathVariable Long id){return service.buscar(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Reserva criar(@Valid @RequestBody ReservaInput dados){return service.salvar(null,dados);}
 @PutMapping("/{id}") public Reserva atualizar(@PathVariable Long id,@Valid @RequestBody ReservaInput dados){return service.salvar(id,dados);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id){service.excluir(id);}
 
 @PatchMapping("/{id}/{acao}") public Reserva transicao(@PathVariable Long id,@PathVariable String acao){return service.transicao(id,acao);}
}