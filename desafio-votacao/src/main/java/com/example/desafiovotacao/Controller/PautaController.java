package com.example.desafiovotacao.Controller;

import com.example.desafiovotacao.dto.PautaDTO;
import com.example.desafiovotacao.dto.UsuarioDTO;
import com.example.desafiovotacao.model.Pauta;
import com.example.desafiovotacao.model.Usuario;
import com.example.desafiovotacao.service.PautaService.PautaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.desafiovotacao.service.UsuarioService.UsuarioService;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    private final PautaService pautaService;
    private final UsuarioService usuarioService;

    @Autowired
    public PautaController(PautaService pautaService, UsuarioService usuarioService) {
        this.pautaService = pautaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody PautaDTO pautaDTO, @RequestHeader("Usuario-CPF") String usuarioCPF) {
        Usuario usuario = usuarioService.buscarUsuarioPorCpf(usuarioCPF);
        
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        UsuarioDTO usuarioDTO = mapToUsuarioDTO(usuario);

        Pauta pauta = pautaService.criarPauta(pautaDTO, usuarioDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(pauta);        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPauta(@PathVariable Long id, @RequestHeader("Usuario-CPF") String usuarioCPF) {
        Usuario usuario = usuarioService.buscarUsuarioPorCpf(usuarioCPF);
        
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        UsuarioDTO usuarioDTO = mapToUsuarioDTO(usuario);

        pautaService.deletarPauta(id, usuarioDTO);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPautaPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPautaPorId(id);
        
        if (pauta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pauta);
    }

    @GetMapping
    public ResponseEntity<List<Pauta>> buscarTodasPautas() {
    List<Pauta> pautas = pautaService.buscarTodasPautas();
    
    if (pautas.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(pautas);
}




    private UsuarioDTO mapToUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setAdmin(usuario.isAdmin());

        return usuarioDTO;
    }
}
