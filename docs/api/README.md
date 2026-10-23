# API REST

Base: http://localhost:8080/api. Corpos JSON; IDs numéricos.

| Recurso | Operações |
|---|---|
| /quartos | GET lista, POST cria |
| /quartos/{id} | GET detalhe, PUT atualiza, DELETE exclui |
| /quartos/disponiveis?entrada=2026-11-01&saida=2026-11-04 | GET disponíveis; reservaId opcional ignora a própria reserva na edição |
| /hospedes | GET lista, POST cria |
| /hospedes/{id} | GET detalhe, PUT atualiza, DELETE exclui |
| /reservas | GET lista, POST cria |
| /reservas/{id} | GET detalhe, PUT atualiza, DELETE exclui |
| /reservas/{id}/check-in | PATCH sem corpo |
| /reservas/{id}/check-out | PATCH sem corpo |
| /reservas/{id}/cancelar | PATCH sem corpo |

Quarto: {"numero":"401","tipo":"Suíte","capacidade":2,"diaria":350.00,"ativo":true}

Hóspede: {"nome":"Ana Silva","documento":"PASS12345","email":"ana@example.com","telefone":"11999999999"}

Reserva: {"quartoId":1,"hospedeId":1,"entrada":"2026-11-01","saida":"2026-11-04"}

Reserva devolve quarto e hóspede relacionados, diária, valorTotal e status. IDs e preços não são aceitos do cliente para sobrescrever o cálculo.
Respostas: 200 leitura/atualização, 201 criação, 204 exclusão, 400 validação, 404 ID inexistente na consulta, 409 conflito de negócio/unicidade. Erros de validação usam message e fields; conflitos usam message.
