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
    public void votar(Long idPauta, Long idUsuario, boolean voto) {
        Optional<Votacao> votacaoOptional = votacaoRepository.findByPautaIdAndUsuarioId(idPauta, idUsuario);
        if (votacaoOptional.isPresent()) {
            throw new UsuarioJaVotouNaPautaException("Usuário já votou na pauta");
        }
        Pauta pauta = new Pauta(idPauta, null, null); 
        Votacao votacao = new Votacao();
        votacao.setVotacaoId(idUsuario);
        votacao.setPauta(pauta); 
        votacao.setVoto(voto);
        votacaoRepository.save(votacao);
    }


    @Transactional
    public int contarVotos(Long idPauta) {
        List<Votacao> votos = votacaoRepository.findByPautaId(idPauta);
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
    public void atualizarVoto(Long idPauta, Long idUsuario, boolean novoVoto) {
        Optional<Votacao> votacaoOptional = votacaoRepository.findByPautaIdAndUsuarioId(idPauta, idUsuario);
        if (votacaoOptional.isPresent()) {
            Votacao votacao = votacaoOptional.get();
            votacao.setVoto(novoVoto);
            votacaoRepository.save(votacao);
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }
    }

    @Transactional
    public boolean usuarioPodeVotar(Long idPauta, Long idUsuario) {
        Optional<Votacao> votacaoOptional = votacaoRepository.findByPautaIdAndUsuarioId(idPauta, idUsuario);
        return votacaoOptional.isEmpty(); 
    }
}




