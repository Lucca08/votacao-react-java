package com.example.desafiovotacao.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiovotacao.model.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long>{
    Optional<Pauta> findById(long id);
    Optional<Pauta> findByNome(String nome);
    
    
}
