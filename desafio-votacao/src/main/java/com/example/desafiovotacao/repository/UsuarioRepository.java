package com.example.desafiovotacao.repository;

import com.example.desafiovotacao.model.Usuario;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByNome(String nome);
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findBySenha(String senha);
    Optional<Usuario> findByAdmin(boolean admin);
    Optional<Usuario> findById(long id);

}
