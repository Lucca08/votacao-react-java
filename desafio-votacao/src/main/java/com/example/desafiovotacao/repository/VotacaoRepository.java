package com.example.desafiovotacao.repository;

import com.example.desafiovotacao.model.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<Votacao, Long>{
        Votacao findById(long id);
   
}
