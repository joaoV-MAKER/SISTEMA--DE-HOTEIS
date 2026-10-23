# Estadia — SYSTEMA-HOTEIS

Aplicação acadêmica local: Angular 21 + Bootstrap 5 + CSS, Spring Boot 3.5 / Java 21 e PostgreSQL 17.

## Executar com Docker (recomendado)

Instale Docker Desktop e mantenha-o em execução. No PowerShell:

```powershell
cd C:\SYSTEMA-HOTEIS
docker compose up --build
```

Interface: http://localhost:4200 · API: http://localhost:8080/api/quartos.
O primeiro build baixa dependências. O banco persiste no volume hoteis_data. Para parar: docker compose down.
As credenciais de desenvolvimento ficam em compose.yml e podem ser alteradas por DB_PASSWORD em .env (copie .env.example antes da primeira inicialização).
Os quatro quartos iniciais são criados por uma migração Flyway. Nenhum hóspede real é incluído.

## Desenvolvimento sem Docker para a aplicação

Pré-requisitos: Java JDK 21, Maven 3.9+, Node 24 com npm, PostgreSQL rodando.
Crie o banco hoteis com usuário hoteis e senha hoteis_local, ou informe DB_URL, DB_USER e DB_PASSWORD no ambiente.
Também pode iniciar apenas PostgreSQL via docker compose up -d db.

Terminal 1:
```powershell
cd C:\SYSTEMA-HOTEIS\backend
mvn spring-boot:run
```
Terminal 2:
```powershell
cd C:\SYSTEMA-HOTEIS\frontend
npm install
npm start
```

O proxy Angular encaminha /api para localhost:8080. Em Docker, o Nginx faz esse encaminhamento.
O projeto atual usa Java; dotnet run executava somente o exemplo antigo, arquivado em legacy-dotnet.

## Fluxo de uso

1. Cadastre ou edite quartos (número único, categoria, capacidade, diária e situação ativa).
2. Cadastre um hóspede com documento único, e-mail e telefone.
3. Em Reservas, clique Nova reserva, selecione entrada e saída e consulte quartos disponíveis.
4. Escolha quarto e hóspede; o backend calcula noites × diária. Exemplo: 3 noites × R$ 180 = R$ 540.
5. Faça check-in durante o período reservado, depois check-out. Uma reserva confirmada também pode ser cancelada.

## Regras de negócio

- Reserva tem um quarto e um hóspede (muitos-para-um em ambas as relações).
- Datas usam dias civis; saída deve ser posterior à entrada, e a entrada de uma nova reserva não pode estar no passado.
- O intervalo é [entrada, saída): check-out de uma reserva pode coincidir com a entrada da próxima.
- Reservas não canceladas bloqueiam sobreposição. Bloqueio pessimista do quarto serializa gravações concorrentes na API.
- Cancelamento libera a disponibilidade. Quarto inativo não aceita novas reservas.
- Valor é calculado no servidor com BigDecimal. A diária é registrada na reserva e preservada ao editar mantendo o quarto; trocar o quarto aplica a diária atual.
- Só reserva confirmada pode ser editada/cancelada. Check-in exige confirmada e data dentro do período; check-out exige hospedado.
- Check-out antecipado não recalcula o valor nem reduz o período contratado.
- Quartos e hóspedes vinculados a reservas não são excluídos. Reserva em hospedagem não pode ser excluída.
- O total do painel é valor contratado não cancelado, sem simulação de pagamento.

## Validação

Backend: mvn test (na pasta backend). Testes HTTP integrados com H2 em modo PostgreSQL, sem alterar o banco real.
Frontend: npm run build (na pasta frontend), com checagem de TypeScript e templates Angular.
As migrações em backend/src/main/resources/db/migration são a fonte oficial do esquema PostgreSQL.

## Organização e apresentação

Consulte docs/apresentacao.md e docs/api/README.md. Há exatamente três entidades principais, três Controllers, três Services e três Repositories.
Aplicação destinada à demonstração local, sem módulo de autenticação.
