package com.example.desafiovotacao.service.VotacaoService;

import com.example.desafiovotacao.Exception.UsuarioJaVotouNaPautaException;
import com.example.desafiovotacao.Exception.UsuarioNaoEncontradoException;
import com.example.desafiovotacao.model.Pauta;
import com.example.desafiovotacao.model.Votacao;
import com.example.desafiovotacao.repository.VotacaoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class VotacaoService {

    private final VotacaoRepository votacaoRepository;

    public VotacaoService(VotacaoRepository votacaoRepository) {
        this.votacaoRepository = votacaoRepository;
    }

    @Transactional
    public void votar(Long pautaId, Long usuarioId, boolean voto) {
        Optional<Votacao> votacaoOptional = votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(pautaId, usuarioId);
        if (votacaoOptional.isPresent()) {
            throw new UsuarioJaVotouNaPautaException("Usuário já votou na pauta");
        }
        Pauta pauta = new Pauta(pautaId, null, null); 
        Votacao votacao = new Votacao();
        votacao.setVotacaoId(usuarioId);
        votacao.setPauta(pauta); 
        votacao.setVoto(voto);
        votacaoRepository.save(votacao);
    }


    @Transactional
    public int contarVotos(Long pautaId) {
        List<Votacao> votos = votacaoRepository.findByPautaPautaId(pautaId);
        int totalVotos = 0;
    for (Votacao voto : votos) {
        if (voto.isVoto()) {
            totalVotos++;
        } else {
            totalVotos--;
        }
    }
    return totalVotos;
    }

    @Transactional
    public void atualizarVoto(Long pautaId, Long usuarioId, boolean novoVoto) {
        Optional<Votacao> votacaoOptional = votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(pautaId, usuarioId);
        if (votacaoOptional.isPresent()) {
            Votacao votacao = votacaoOptional.get();
            votacao.setVoto(novoVoto);
            votacaoRepository.save(votacao);
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }
    }

    @Transactional
    public boolean usuarioPodeVotar(Long pautaId, Long usuarioId) {
        Optional<Votacao> votacaoOptional = votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(pautaId, usuarioId);
        return votacaoOptional.isEmpty(); 
    }
}




