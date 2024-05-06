package com.example.desafiovotacao.Controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.desafiovotacao.Exception.UnauthorizedAccessException;
import com.example.desafiovotacao.Exception.UsuarioExistenteException;
import com.example.desafiovotacao.Exception.UsuarioNaoEncontradoException;
import com.example.desafiovotacao.dto.UsuarioDTO;
import com.example.desafiovotacao.model.Usuario;
import com.example.desafiovotacao.service.UsuarioService.UsuarioService;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

   @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
    try {
        Usuario usuario = usuarioService.criarUsuario(usuarioDTO);
        UsuarioDTO usuarioCriadoDTO = mapToUsuarioDTO(usuario);
        return ResponseEntity.created(new URI("/usuario/" + usuario.getUsuarioId()))
                             .body(usuarioCriadoDTO);
    } catch (UsuarioExistenteException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (URISyntaxException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody UsuarioDTO usuarioDTO) {
    try {
        Usuario usuario = usuarioService.login(usuarioDTO);
        UsuarioDTO usuarioLogadoDTO = mapToUsuarioDTO(usuario);
        return ResponseEntity.ok(usuarioLogadoDTO);
    } catch (UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (UsuarioNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}



    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Usuario> buscarUsuarioPorCpf(@PathVariable String cpf) {
        Usuario usuario = usuarioService.buscarUsuarioPorCpf(cpf);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setUsuarioId(id);
        usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.noContent().build();
    }

    private UsuarioDTO mapToUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setAdmin(usuario.isAdmin());
        return usuarioDTO;
    }
}
