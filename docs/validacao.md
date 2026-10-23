# Validação realizada

- Compilação de produção Angular: passou (TypeScript e templates; bundle inicial de aproximadamente 590 kB).
- Backend Spring Boot: compilou e gerou JAR executável.
- Quatro testes HTTP integrados: 4 passaram, 0 falhas, 0 erros. Banco H2 em modo PostgreSQL, com a migração Flyway real do projeto.
- Cobertura: CRUD, valor de diárias, edição, sobreposição, datas inválidas, cancelamento, disponibilidade, check-in/check-out, unicidade e exclusão de registros vinculados.
- A execução completa em Docker/PostgreSQL e a verificação visual no navegador ainda precisam ser realizadas no computador com Docker Desktop. O ambiente de validação recusou conexões locais entre os processos, inclusive à prévia Angular; isso impediu concluir essa etapa.

Os testes foram executados em uma cópia dos mesmos fontes, em uma pasta de validação, devido a limitações de acesso do ambiente à instalação de dependências em C:\SYSTEMA-HOTEIS.

## Conferência manual após iniciar o Docker

1. Abrir http://localhost:4200 e conferir as três telas em computador e celular.
2. Cadastrar um hóspede e uma reserva de três noites no quarto 101: total de R$ 540,00 com a diária inicial de R$ 180,00.
3. Repetir o período e conferir bloqueio de disponibilidade.
4. Cancelar, recriar, executar check-in e check-out.
5. Parar e reiniciar com docker compose down / docker compose up e conferir persistência.
