    package com.example.desafiovotacao.ControllerTeste;

    import static io.restassured.RestAssured.given;
    import static org.hamcrest.Matchers.equalTo;

    import com.example.desafiovotacao.dto.UsuarioDTO;
    import com.example.desafiovotacao.model.Usuario;
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
    public void testAtualizarUsuario() throws Exception {
        // Criar um usuário para usar no teste
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Teste");
        usuarioDTO.setSenha("123456");
        usuarioDTO.setCpf("12345678901");
        usuarioDTO.setEmail("teste@example.com");
        usuarioDTO.setAdmin(true);

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

        // Testar o login do usuário
        given()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(usuarioDTO))
        .when()
            .post("/usuario/login")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(usuarioDTO.getNome()))
            .body("cpf", equalTo(usuarioDTO.getCpf()))
            .body("email", equalTo(usuarioDTO.getEmail()))
            .body("admin", equalTo(usuarioDTO.isAdmin()));

        // Atualizar as informações do usuário
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Novo Nome");
        novoUsuario.setEmail("novoemail@example.com");
        novoUsuario.setSenha("654321");
        novoUsuario.setCpf("10987654321");
        novoUsuario.setAdmin(true);

        given()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(novoUsuario))
        .when()
            .put("/usuario/1")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(novoUsuario.getNome()))
            .body("email", equalTo(novoUsuario.getEmail()));

        // Deletar o usuário
        given()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(usuarioDTO))

        .when()
            .delete("/usuario/1")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
        }
    }