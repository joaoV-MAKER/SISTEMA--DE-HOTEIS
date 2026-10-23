package br.com.hoteis;
import br.com.hoteis.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@org.springframework.test.context.TestExecutionListeners(listeners=org.springframework.test.context.support.DependencyInjectionTestExecutionListener.class,mergeMode=org.springframework.test.context.TestExecutionListeners.MergeMode.REPLACE_DEFAULTS)
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test") class FluxoIntegradoTest {
 @Autowired MockMvc mvc;@Autowired ObjectMapper json;@Autowired ReservaRepository reservas;@Autowired QuartoRepository quartos;@Autowired HospedeRepository hospedes;
 long quarto,hospede;String entrada,saida;
 @BeforeEach void preparar() throws Exception {
  reservas.deleteAll();hospedes.deleteAll();quartos.deleteAll();entrada=LocalDate.now().toString();saida=LocalDate.now().plusDays(3).toString();
  quarto=criar("quartos","{\"numero\":\"101\",\"tipo\":\"Standard\",\"capacidade\":2,\"diaria\":180,\"ativo\":true}");
  hospede=criar("hospedes","{\"nome\":\"Ana Silva\",\"documento\":\"ABC12345\",\"email\":\"ana@example.com\",\"telefone\":\"11999999999\"}");
 }
 long criar(String recurso,String body)throws Exception {return json.readTree(mvc.perform(post("/api/"+recurso).contentType("application/json").content(body)).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()).get("id").asLong();}
 String dados(String e,String s){return "{\"quartoId\":"+quarto+",\"hospedeId\":"+hospede+",\"entrada\":\""+e+"\",\"saida\":\""+s+"\"}";}
 @Test void fluxoCompletoCalculaValorEControlaEstados()throws Exception {
  long id=criar("reservas",dados(entrada,saida));
  mvc.perform(get("/api/reservas/"+id)).andExpect(status().isOk()).andExpect(jsonPath("$.valorTotal").value(540)).andExpect(jsonPath("$.hospede.nome").value("Ana Silva"));
  mvc.perform(put("/api/reservas/"+id).contentType("application/json").content(dados(entrada,LocalDate.now().plusDays(4).toString()))).andExpect(status().isOk()).andExpect(jsonPath("$.valorTotal").value(720));
  mvc.perform(patch("/api/reservas/"+id+"/check-out")).andExpect(status().isConflict());
  mvc.perform(patch("/api/reservas/"+id+"/check-in")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("HOSPEDADO"));
  mvc.perform(delete("/api/reservas/"+id)).andExpect(status().isConflict());
  mvc.perform(patch("/api/reservas/"+id+"/check-out")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("FINALIZADA"));
  mvc.perform(delete("/api/reservas/"+id)).andExpect(status().isNoContent());
  mvc.perform(delete("/api/hospedes/"+hospede)).andExpect(status().isNoContent());
  mvc.perform(delete("/api/quartos/"+quarto)).andExpect(status().isNoContent());
 }
 @Test void sobreposicaoBloqueadaCancelamentoLiberaEPeriodosAdjacentesAceitos()throws Exception {
  long id=criar("reservas",dados(entrada,saida));
  mvc.perform(post("/api/reservas").contentType("application/json").content(dados(entrada,saida))).andExpect(status().isConflict());
  mvc.perform(get("/api/quartos/disponiveis").param("entrada",entrada).param("saida",saida)).andExpect(jsonPath("$.length()").value(0));
  mvc.perform(get("/api/quartos/disponiveis").param("entrada",entrada).param("saida",saida).param("reservaId",""+id)).andExpect(jsonPath("$.length()").value(1));
  criar("reservas",dados(saida,LocalDate.now().plusDays(5).toString()));
  mvc.perform(patch("/api/reservas/"+id+"/cancelar")).andExpect(status().isOk());
  criar("reservas",dados(entrada,saida));
 }
 @Test void validaDatasCamposUnicidadeEVinculos()throws Exception {
  mvc.perform(post("/api/reservas").contentType("application/json").content(dados(saida,entrada))).andExpect(status().isConflict());
  mvc.perform(post("/api/reservas").contentType("application/json").content(dados(LocalDate.now().minusDays(1).toString(),saida))).andExpect(status().isConflict());
  mvc.perform(post("/api/hospedes").contentType("application/json").content("{\"nome\":\"A\"}")).andExpect(status().isBadRequest());
  mvc.perform(post("/api/quartos").contentType("application/json").content("{\"numero\":\"101\",\"tipo\":\"Standard\",\"capacidade\":2,\"diaria\":180,\"ativo\":true}")).andExpect(status().isConflict());
  criar("reservas",dados(entrada,saida));
  mvc.perform(delete("/api/hospedes/"+hospede)).andExpect(status().isConflict());
  mvc.perform(delete("/api/quartos/"+quarto)).andExpect(status().isConflict());
 }
 @Test void crudDeQuartosEHospedes()throws Exception {
  mvc.perform(get("/api/quartos/"+quarto)).andExpect(status().isOk());
  mvc.perform(put("/api/quartos/"+quarto).contentType("application/json").content("{\"numero\":\"102\",\"tipo\":\"Luxo\",\"capacidade\":3,\"diaria\":290,\"ativo\":false}")).andExpect(status().isOk()).andExpect(jsonPath("$.numero").value("102"));
  mvc.perform(post("/api/reservas").contentType("application/json").content(dados(entrada,saida))).andExpect(status().isConflict());
  mvc.perform(put("/api/hospedes/"+hospede).contentType("application/json").content("{\"nome\":\"Ana Souza\",\"documento\":\"ABC12345\",\"email\":\"ana@example.com\",\"telefone\":\"11999999999\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Ana Souza"));
  mvc.perform(get("/api/hospedes/"+hospede)).andExpect(status().isOk());
  mvc.perform(get("/api/reservas/999999")).andExpect(status().isNotFound());
 }
}
