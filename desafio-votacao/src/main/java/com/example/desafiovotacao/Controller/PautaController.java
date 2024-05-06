package com.example.desafiovotacao.Controller;

import com.example.desafiovotacao.dto.PautaDTO;
import com.example.desafiovotacao.dto.UsuarioDTO;
import com.example.desafiovotacao.model.Pauta;
import com.example.desafiovotacao.service.PautaService.PautaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    private final PautaService pautaService;

    @Autowired
    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody PautaDTO pautaDTO, @RequestHeader("Usuario-CPF") String usuarioCPF) {
        UsuarioDTO usuarioLogado = new UsuarioDTO();
        usuarioLogado.setCpf(usuarioCPF); 

        Pauta pauta = pautaService.criarPauta(pautaDTO, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(pauta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPautaPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPautaPorId(id);
        return ResponseEntity.ok(pauta);
    }

    @GetMapping
    public ResponseEntity<List<Pauta>> buscarTodasPautas() {
        List<Pauta> pautas = pautaService.buscarTodasPautas();
        return ResponseEntity.ok(pautas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPauta(@PathVariable Long id, @RequestHeader("Usuario-CPF") String usuarioCPF) {
        UsuarioDTO usuarioLogado = new UsuarioDTO();
        usuarioLogado.setCpf(usuarioCPF); 

        pautaService.deletarPauta(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pauta> atualizarPauta(@PathVariable Long id, @Valid @RequestBody PautaDTO pautaDTO, @RequestHeader("Usuario-CPF") String usuarioCPF) {
        UsuarioDTO usuarioLogado = new UsuarioDTO();
        usuarioLogado.setCpf(usuarioCPF); 

        Pauta pautaAtualizada = pautaService.atualizarPauta(id, pautaDTO, usuarioLogado);
        return ResponseEntity.ok(pautaAtualizada);
    }
}
