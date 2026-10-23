# Roteiro de apresentação

Substituam Integrante 1/2/3 pelos nomes da equipe.

| Responsável | Entidade e camadas | Tela e demonstração |
|---|---|---|
| Integrante 1 | QuartoController, QuartoService, QuartoRepository, Quarto | CRUD de quartos, diária, capacidade, situação e consulta de disponibilidade |
| Integrante 2 | HospedeController, HospedeService, HospedeRepository, Hospede | CRUD de hóspedes, documento único e validação de contato |
| Integrante 3 | ReservaController, ReservaService, ReservaRepository, Reserva | Seleção de quarto/hóspede, cálculo, edição, cancelamento, check-in e check-out |

## Demonstração integrada (todos devem saber explicar)

1. Mostrar o relacionamento: um quarto possui várias reservas; um hóspede possui várias reservas.
2. Criar quarto e hóspede nas respectivas telas.
3. Reservar de hoje até três dias depois: explicar formulário → HttpClient → REST Controller → Service → Repository → PostgreSQL.
4. Mostrar cálculo do valor no servidor e persistência da diária contratada.
5. Tentar repetir a reserva: explicar intervalo e bloqueio de sobreposição.
6. Editar a reserva; cancelar para liberar o quarto; criar outra e executar check-in/check-out.
7. Demonstrar erro de e-mail, saída anterior à entrada e tentativa de excluir quarto vinculado.
8. Mostrar as três camadas no código e os testes FluxoIntegradoTest.

Tema proposto: sistema de gestão de hotel. A tabela de temas mencionada no enunciado não foi recebida; confirmar a disponibilidade do tema com o professor até 26/10/2026.
