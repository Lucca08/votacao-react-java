package com.example.desafiovotacao.repository;

import com.example.desafiovotacao.model.Usuario;
import com.example.desafiovotacao.model.Votacao;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
    List<Votacao> findByVoto(boolean voto);
    List<Votacao> findByPautaPautaId(Long pautaId);
    List<Votacao> findByUsuarioUsuarioId(Long usuarioId);
    Optional<Votacao> findByPautaPautaIdAndUsuarioUsuarioId(Long pautaId, Long usuarioId); 
    Optional<Votacao> findByVotacaoId(Long votacaoId);


}
