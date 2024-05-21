
package com.example.desafiovotacao.ControllerTeste;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.example.desafiovotacao.dto.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class UsuarioControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        System.out.println("Test server running on port: " + port);
    }

    @Test
    public void testCrudUsuario() throws Exception {
        verificarDadosIniciais();
        atualizarUsuario();
        deletarUsuario();
        verificarUsuarioDeletado();
    }

    private void verificarDadosIniciais() {
        given()
            .port(port)
        .when()
            .get("/usuario/1")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Teste"))
            .body("cpf", equalTo("12345678901"))
            .body("email", equalTo("teste@example.com"))
            .body("admin", equalTo(true));
    }

    private void atualizarUsuario() throws Exception {
        UsuarioDTO novoUsuarioDTO = new UsuarioDTO();
        novoUsuarioDTO.setNome("Novo Nome");
        novoUsuarioDTO.setEmail("novoemail@example.com");
        novoUsuarioDTO.setSenha("654321");
        novoUsuarioDTO.setCpf("10987654321");
        novoUsuarioDTO.setAdmin(true);

        given()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(novoUsuarioDTO))
        .when()
            .put("/usuario/1")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(novoUsuarioDTO.getNome()))
            .body("email", equalTo(novoUsuarioDTO.getEmail()));
    }

    private void deletarUsuario() {
        given()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .delete("/usuario/1")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void verificarUsuarioDeletado() {
        given()
            .port(port)
        .when()
            .get("/usuario/1")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
