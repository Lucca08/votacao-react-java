package com.example.desafiovotacao.ControllerTeste;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.example.desafiovotacao.dto.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCriarUsuarioELogin() throws Exception {
        // Criar um usuário para usar no teste
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Teste");
        usuarioDTO.setCpf("12345678901");
        usuarioDTO.setEmail("teste@example.com");
        usuarioDTO.setAdmin(false);

        // Testar o registro de usuário
        given()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(usuarioDTO))
        .when()
            .post("/usuario")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("nome", equalTo(usuarioDTO.getNome()))
            .body("cpf", equalTo(usuarioDTO.getCpf()))
            .body("email", equalTo(usuarioDTO.getEmail()))
            .body("admin", equalTo(usuarioDTO.isAdmin()));

        // Testar o login com as credenciais do usuário
        given()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(usuarioDTO))
        .when()
            .post("/login")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(usuarioDTO.getNome()))
            .body("senha", equalTo(usuarioDTO.getSenha()))
            .body("cpf", equalTo(usuarioDTO.getCpf()))
            .body("email", equalTo(usuarioDTO.getEmail()))
            .body("admin", equalTo(usuarioDTO.isAdmin()));
    }
}
