package br.com.hoteis.controller;
import br.com.hoteis.model.Hospede;
import br.com.hoteis.service.HospedeService;
import br.com.hoteis.dto.Dados.HospedeInput;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;
import java.time.LocalDate;
@RestController @RequestMapping("/api/hospedes") public class HospedeController {
 private final HospedeService service; public HospedeController(HospedeService service){this.service=service;}
 @GetMapping public List<Hospede> listar(){return service.listar();}
 @GetMapping("/{id}") public Hospede buscar(@PathVariable Long id){return service.buscar(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Hospede criar(@Valid @RequestBody HospedeInput dados){return service.salvar(null,dados);}
 @PutMapping("/{id}") public Hospede atualizar(@PathVariable Long id,@Valid @RequestBody HospedeInput dados){return service.salvar(id,dados);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id){service.excluir(id);}
 
 
}