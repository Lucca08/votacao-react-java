package com.example.desafiovotacao.repository;

import com.example.desafiovotacao.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpfAndUsuarioId(String cpf, Long id);
    boolean existsByEmailAndUsuarioId(String email, Long id); 

}
