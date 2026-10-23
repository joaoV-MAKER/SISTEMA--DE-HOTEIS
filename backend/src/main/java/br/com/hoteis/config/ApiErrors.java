package br.com.hoteis.config;
import br.com.hoteis.service.RegraException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import java.util.Map;
@RestControllerAdvice public class ApiErrors {
 @ExceptionHandler(RegraException.class) public ResponseEntity<?> regra(RegraException e){return ResponseEntity.status(409).body(Map.of("message",e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<?> validacao(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("message","Revise os campos informados.","fields",e.getBindingResult().getFieldErrors().stream().map(f->f.getField()+": "+f.getDefaultMessage()).toList()));}
 @ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<?> integridade(DataIntegrityViolationException e){return ResponseEntity.status(409).body(Map.of("message","Número/documento já cadastrado ou registro vinculado a uma reserva."));}
}