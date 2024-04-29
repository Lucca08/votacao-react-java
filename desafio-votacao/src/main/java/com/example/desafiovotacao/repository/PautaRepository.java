package com.example.desafiovotacao.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.desafiovotacao.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>{
    Optional<Pauta> findByNome(String nome);
    Optional<Pauta> findById(Long id);

    
    
}
