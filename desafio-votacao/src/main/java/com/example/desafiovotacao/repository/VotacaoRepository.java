package com.example.desafiovotacao.repository;

import com.example.desafiovotacao.model.Usuario;
import com.example.desafiovotacao.model.Votacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
    List<Votacao> findByVoto(boolean voto);
    List<Votacao> findByUsuario(Usuario usuario);
    List<Votacao> findByVotoAndUsuario(boolean voto, Usuario usuario);
    

}
