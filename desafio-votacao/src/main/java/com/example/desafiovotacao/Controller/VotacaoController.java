package com.example.desafiovotacao.Controller;


import com.example.desafiovotacao.dto.VotacaoDTO;
import com.example.desafiovotacao.model.Votacao;
import com.example.desafiovotacao.service.VotacaoService.VotacaoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votacao")
public class VotacaoController {

    private final VotacaoService votacaoService;

    @Autowired
    public VotacaoController(VotacaoService votacaoService) {
        this.votacaoService = votacaoService;
    }

    @PostMapping("/votar")
    public ResponseEntity<Void> votar(@RequestParam Long idPauta, @RequestParam Long usuarioId, @Valid @RequestBody VotacaoDTO votacaoDTO) {
        votacaoService.votar(idPauta, usuarioId, votacaoDTO.isVoto());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/contarVotos")
    public ResponseEntity<Integer> contarVotos(@RequestParam Long idPauta) {
        int totalVotos = votacaoService.contarVotos(idPauta);
        return ResponseEntity.ok(totalVotos);
    }

    @PutMapping("/atualizarVoto")
    public ResponseEntity<Void> atualizarVoto(@RequestParam Long idPauta, @RequestParam Long usuarioId, @Valid @RequestBody VotacaoDTO votacaoDTO) {
        votacaoService.atualizarVoto(idPauta, usuarioId, votacaoDTO.isVoto());
        return ResponseEntity.noContent().build();
    }

}
