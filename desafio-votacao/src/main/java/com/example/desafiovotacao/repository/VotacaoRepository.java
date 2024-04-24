package com.example.desafiovotacao.repository;

import com.example.desafiovotacao.model.Votacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
    List<Votacao> findByVoto(boolean voto);
    List<Votacao> findByIdPauta(long idPauta);
    List<Votacao> findByIdUsuario(long idUsuario);
}



