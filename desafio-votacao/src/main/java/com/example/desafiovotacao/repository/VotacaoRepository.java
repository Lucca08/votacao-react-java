package com.example.desafiovotacao.repository;

import com.example.desafiovotacao.model.Votacao;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
    List<Votacao> findByPautaPautaId(Long pautaId);
    Optional<Votacao> findByPautaPautaIdAndUsuarioUsuarioId(Long pautaId, Long usuarioId); 


}
