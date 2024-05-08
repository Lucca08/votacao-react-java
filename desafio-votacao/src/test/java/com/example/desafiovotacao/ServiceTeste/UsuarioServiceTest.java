package com.example.desafiovotacao.ServiceTeste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.desafiovotacao.Exception.UsuarioExistenteException;
import com.example.desafiovotacao.Exception.UsuarioNaoEncontradoException;
import com.example.desafiovotacao.dto.UsuarioDTO;
import com.example.desafiovotacao.model.Usuario;
import com.example.desafiovotacao.repository.UsuarioRepository;
import com.example.desafiovotacao.service.UsuarioService.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Teste de criação de usuário")
    public void testCriarUsuario(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setEmail("lucca@gmail.com");
        usuarioDTO.setNome("Usuário de teste");
        usuarioDTO.setAdmin(false);

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setUsuarioId(1L);
        usuarioSalvo.setCpf(usuarioDTO.getCpf());
        usuarioSalvo.setEmail(usuarioDTO.getEmail());
        usuarioSalvo.setNome(usuarioDTO.getNome());
        usuarioSalvo.setAdmin(usuarioDTO.isAdmin());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);
        
        Usuario resultado = usuarioService.criarUsuario(usuarioDTO);
        
        assertEquals(usuarioDTO.getNome(), resultado.getNome());
        assertEquals(usuarioDTO.getCpf(), resultado.getCpf());
        assertEquals(usuarioDTO.getEmail(), resultado.getEmail());
        assertEquals(usuarioDTO.isAdmin(), resultado.isAdmin());
        assertTrue(resultado.getUsuarioId() > 0);
    }

    @Test
    @DisplayName("Teste de criação de usuário com CPF existente")
    public void testCriarUsuarioComCpfExistente(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setEmail("lucca@gmail.com");
        usuarioDTO.setNome("Usuário de teste");
        usuarioDTO.setAdmin(false);

        when(usuarioRepository.existsByCpf(usuarioDTO.getCpf())).thenReturn(true);

        Throwable exception = assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.criarUsuario(usuarioDTO);
        });

        assertEquals("Já existe um usuário cadastrado com o CPF: " + usuarioDTO.getCpf(), exception.getMessage());
    }

    @Test
    @DisplayName("Teste de criação de usuário com e-mail existente")
    public void testCriarUsuarioComEmailExistente(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setEmail("lucca@gmail.com");
        usuarioDTO.setNome("Usuário de teste");
        usuarioDTO.setAdmin(false);

        when(usuarioRepository.existsByCpf(usuarioDTO.getCpf())).thenReturn(false);
        when(usuarioRepository.existsByEmail(usuarioDTO.getEmail())).thenReturn(true);

        Throwable exception = assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.criarUsuario(usuarioDTO);
        });

        assertEquals("Já existe um usuário cadastrado com o e-mail: " + usuarioDTO.getEmail(), exception.getMessage());
    }

    @Test
    @DisplayName("Teste de criação de usuário admin")
    public void testCriarUsuarioAdmin(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setEmail("lucca@gmail.com");
        usuarioDTO.setNome("Usuário de teste");
        usuarioDTO.setAdmin(true);

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setUsuarioId(1L);
        usuarioSalvo.setCpf(usuarioDTO.getCpf());
        usuarioSalvo.setEmail(usuarioDTO.getEmail());
        usuarioSalvo.setNome(usuarioDTO.getNome());
        usuarioSalvo.setAdmin(false);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);
        
        Usuario resultado = usuarioService.criarUsuario(usuarioDTO);

        assertEquals(usuarioDTO.getNome(), resultado.getNome());
        assertEquals(usuarioDTO.getCpf(), resultado.getCpf());
        assertEquals(usuarioDTO.getEmail(), resultado.getEmail());
        assertEquals(false, resultado.isAdmin());
        assertTrue(resultado.getUsuarioId() > 0);
    }

    @Test
    @DisplayName("Teste de busca de usuário por ID")
    public void testBuscarUsuarioPorId(){
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNome("Usuário de teste");
    
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarUsuarioPorId(1L);
        
        assertEquals(usuario.getNome(), resultado.getNome());
        assertEquals(usuario.getUsuarioId(), resultado.getUsuarioId());
    }

    @Test
    @DisplayName("Teste de busca por id de usuário não encontrado")
    public void testBuscarUsuarioPorIdNaoEncontrado(){
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
    
        Throwable exception = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.buscarUsuarioPorId(1L);
        });

        assertEquals("Usuário não encontrado com o ID: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de busca de usuário por CPF")
    public void testBuscarUsuarioporCpf(){
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNome("Usuário de teste");


        when(usuarioRepository.findByCpf("12345678900")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarUsuarioPorCpf("12345678900");

        assertEquals(usuario.getNome(), resultado.getNome());
        assertEquals(usuario.getUsuarioId(), resultado.getUsuarioId());
    }

    @Test
    @DisplayName("Teste de busca por CPF de usuário não encontrado")
    public void testBuscarUsuarioPorCpfNaoEncontrado(){
        when(usuarioRepository.findByCpf("12345678900")).thenReturn(Optional.empty());

        Throwable exception = assertThrows(UsuarioNaoEncontradoException.class, ()-> {
            usuarioService.buscarUsuarioPorCpf("12345678900");
        });
        
        assertEquals("Usuário não encontrado com o CPF: 12345678900", exception.getMessage());
    }


    @Test
    @DisplayName("Teste de busca de usuário por e-mail")
    public void testBuscarUsuarioPorEmail(){
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNome("Usuário de teste");

        when(usuarioRepository.findByEmail("lucca@gmail.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarUsuarioPorEmail("lucca@gmail.com");

        assertEquals(usuario.getNome(), resultado.getNome());
        assertEquals(usuario.getUsuarioId(), resultado.getUsuarioId());
    }

    @Test
    @DisplayName("Teste de busca por e-mail de usuário não encontrado")
    public void testBuscarUsuarioPorEmailNaoEncontrado(){
        when(usuarioRepository.findByEmail("lucca@gmail.com")).thenReturn(Optional.empty());

        Throwable exception = assertThrows(UsuarioNaoEncontradoException.class, ()-> {
            usuarioService.buscarUsuarioPorEmail("lucca@gmail.com");
        });

        assertEquals("Usuário não encontrado com o e-mail: lucca@gmail.com", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de atualização de usuário")
    public void testAtualizarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNome("Usuário de teste");
        usuario.setCpf("12345678900");
        usuario.setEmail("lucca@gmail.com");

        UsuarioDTO usuarioAtualizado = new UsuarioDTO();
        usuarioAtualizado.setNome("Usuário de teste atualizado");
        usuarioAtualizado.setCpf("12345678900");
        usuarioAtualizado.setEmail("lucca@gmail.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.atualizarUsuario(1L, usuarioAtualizado);

        assertEquals(usuarioAtualizado.getNome(), usuario.getNome());
        assertEquals(usuarioAtualizado.getCpf(), usuario.getCpf());
        assertEquals(usuarioAtualizado.getEmail(), usuario.getEmail());
        assertEquals(usuarioAtualizado.isAdmin(), usuario.isAdmin());

    }

    @Test
    @DisplayName("Teste de atualização de usuário com CPF existente")
    public void testAtualizarUsuarioComCpfExistente(){
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNome("Usuário de teste");
        usuario.setCpf("12345678900");
        usuario.setEmail("lucca@gmail.com");

        UsuarioDTO usuarioAtualizado = new UsuarioDTO();
        usuarioAtualizado.setNome("Usuário de teste atualizado");
        usuarioAtualizado.setCpf("12345678900");
        usuarioAtualizado.setEmail("lucca@gmail.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByCpfAndUsuarioId(usuarioAtualizado.getCpf(),1L)).thenReturn(true);

        Throwable exception = assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.atualizarUsuario(1L, usuarioAtualizado);
        });

        assertEquals("Já existe um usuário cadastrado com o CPF: " + usuarioAtualizado.getCpf(), exception.getMessage());

    }

    @Test
    @DisplayName("Teste de atualização de usuário com e-mail existente")
    public void testAtualizarUsuarioComEmailExistente() {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNome("Usuário de teste");
        usuario.setCpf("12345678900");
        usuario.setEmail("lucca@gmail.com");
    
        UsuarioDTO usuarioAtualizado = new UsuarioDTO();
        usuarioAtualizado.setNome("Usuário de teste atualizado");
        usuarioAtualizado.setCpf("12345678901");
        usuarioAtualizado.setEmail("lucca@gmail.com");
    
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailAndUsuarioId(usuarioAtualizado.getEmail(), 1L)).thenReturn(true);
    
        Throwable exception = assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.atualizarUsuario(1L, usuarioAtualizado);
        });
    
        assertEquals("Já existe um usuário cadastrado com o e-mail: " + usuarioAtualizado.getEmail(), exception.getMessage());
    }
    


    @Test
    @DisplayName("Teste de atualização de usuário não encontrado")
    public void testAtualizarUsuarioNaoEncontrado(){
        UsuarioDTO usuarioAtualizado = new UsuarioDTO();
        usuarioAtualizado.setNome("Usuário de teste atualizado");
        usuarioAtualizado.setCpf("12345678900");
        usuarioAtualizado.setEmail("lucca@gmail.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.atualizarUsuario(1L, usuarioAtualizado);
        });

        assertEquals("Usuário não encontrado com o ID: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de deletar usuário")
    public void testDeletarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNome("Usuário de teste");
        usuario.setCpf("12345678900");
        usuario.setEmail("luccabibianogarcia02novo@gmail.com");
    
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Usuário de teste");
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setEmail("luccabibianogarcia02novo@gmail.com");
    
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
    
        usuarioService.deletarUsuario(1L);
    
        verify(usuarioRepository, times(1)).delete(usuario);
    }
    
    @Test
    @DisplayName("Teste de deletar usuário com sucesso")
    public void testDeletarUsuarioComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNome("Usuário de teste");
        usuario.setCpf("12345678900");
        usuario.setEmail("luccabibianogarcia02novo@gmail.com");
    
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
    
        usuarioService.deletarUsuario(1L);
    
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    @DisplayName("Teste de deletar usuário quando o usuário não é encontrado")
    public void testDeletarUsuarioUsuarioNaoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
        usuarioService.deletarUsuario(1L);
        });
        verify(usuarioRepository, never()).delete(any());
}

    
}