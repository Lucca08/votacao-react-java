package com.example.desafiovotacao.service.UsuarioService;

import com.example.desafiovotacao.Exception.UnauthorizedAccessException;
import com.example.desafiovotacao.Exception.UsuarioExistenteException;
import com.example.desafiovotacao.model.Usuario;
import com.example.desafiovotacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.desafiovotacao.Exception.UsuarioNaoEncontradoException;
import com.example.desafiovotacao.dto.UsuarioDTO;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
   
    if (usuarioRepository.existsByCpf(usuarioDTO.getCpf())) {
        throw new UsuarioExistenteException("Já existe um usuário cadastrado com o CPF: " + usuarioDTO.getCpf());
    }
    
    if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
        throw new UsuarioExistenteException("Já existe um usuário cadastrado com o e-mail: " + usuarioDTO.getEmail());
    }

    if (usuarioDTO.isAdmin()) {
        usuarioDTO.setAdmin(true);
    }

    Usuario usuario = new Usuario();
    usuario.setNome(usuarioDTO.getNome());
    usuario.setSenha(usuarioDTO.getSenha());
    usuario.setCpf(usuarioDTO.getCpf());
    usuario.setEmail(usuarioDTO.getEmail());
    usuario.setAdmin(usuarioDTO.isAdmin());

    return usuarioRepository.save(usuario);
    }


    @Transactional(readOnly = true)
    public Usuario login(UsuarioDTO usuarioDTO) {
    Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail())
        .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o e-mail: " + usuarioDTO.getEmail()));

    if (!usuario.getCpf().equals(usuarioDTO.getCpf())) {
        throw new UnauthorizedAccessException("Credenciais inválidas");
    }

    return usuario;
}

@Transactional
public void deletarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    usuarioRepository.delete(usuario);
}


    
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorId(long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + usuarioId));
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o CPF: " + cpf));
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o e-mail: " + email));
    }

    @Transactional
    public void atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
    Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    
    if (usuarioRepository.existsByCpfAndUsuarioId(usuarioDTO.getCpf(), id)) {
        throw new UsuarioExistenteException("Já existe um usuário cadastrado com o CPF: " + usuarioDTO.getCpf());
    }
    
    if (usuarioRepository.existsByEmailAndUsuarioId(usuarioDTO.getEmail(), id)) {
        throw new UsuarioExistenteException("Já existe um usuário cadastrado com o e-mail: " + usuarioDTO.getEmail());
    }

    usuarioExistente.setNome(usuarioDTO.getNome());
    usuarioExistente.setEmail(usuarioDTO.getEmail());
    usuarioExistente.setCpf(usuarioDTO.getCpf());
    usuarioExistente.setAdmin(usuarioDTO.isAdmin());
    
    usuarioRepository.save(usuarioExistente);
    }

}