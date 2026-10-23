package br.com.hoteis.controller;
import br.com.hoteis.model.Quarto;
import br.com.hoteis.service.QuartoService;
import br.com.hoteis.dto.Dados.QuartoInput;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;
import java.time.LocalDate;
@RestController @RequestMapping("/api/quartos") public class QuartoController {
 private final QuartoService service; public QuartoController(QuartoService service){this.service=service;}
 @GetMapping public List<Quarto> listar(){return service.listar();}
 @GetMapping("/{id}") public Quarto buscar(@PathVariable Long id){return service.buscar(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Quarto criar(@Valid @RequestBody QuartoInput dados){return service.salvar(null,dados);}
 @PutMapping("/{id}") public Quarto atualizar(@PathVariable Long id,@Valid @RequestBody QuartoInput dados){return service.salvar(id,dados);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id){service.excluir(id);}
 @GetMapping("/disponiveis") public List<Quarto> disponiveis(@RequestParam LocalDate entrada,@RequestParam LocalDate saida,@RequestParam(required=false) Long reservaId){return service.disponiveis(entrada,saida,reservaId);}
 
}