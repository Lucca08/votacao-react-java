package com.example.desafiovotacao.ServiceTeste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.desafiovotacao.Exception.FalhaAoAtualizarPautaException;
import com.example.desafiovotacao.Exception.PautaNaoCriadaException;
import com.example.desafiovotacao.Exception.PautaNaoEncontradaException;
import com.example.desafiovotacao.Exception.UnauthorizedAccessException;
import com.example.desafiovotacao.dto.PautaDTO;
import com.example.desafiovotacao.dto.UsuarioDTO;
import com.example.desafiovotacao.model.Pauta;
import com.example.desafiovotacao.repository.PautaRepository;
import com.example.desafiovotacao.service.PautaService.PautaService;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @Test
    @DisplayName("Teste de criação de pauta por admin")
    public void testCriarPautaPorAdmin() {
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setNome("Pauta de teste");

        UsuarioDTO usuarioAdmin = new UsuarioDTO();
        usuarioAdmin.setAdmin(true);

        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");

        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        Pauta resultado = pautaService.criarPauta(pautaDTO, usuarioAdmin);

        assertEquals(pauta, resultado);
    }

    @Test
    @DisplayName("Teste de criação de pauta por usuário não admin")
    public void testCriarPautaPorNaoAdmin() {
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setNome("Pauta de teste");

        UsuarioDTO usuarioNaoAdmin = new UsuarioDTO();
        usuarioNaoAdmin.setAdmin(false);

        Throwable exception = assertThrows(UnauthorizedAccessException.class, () -> {
            pautaService.criarPauta(pautaDTO, usuarioNaoAdmin);
        });

        assertEquals("Usuário não tem permissão para criar pauta", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de buscar pauta por ID")
    public void testBuscarPautaPorId() {
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        Pauta resultado = pautaService.buscarPautaPorId(1L);

        assertNotNull(resultado);
        assertEquals(pauta, resultado);
    }

    @Test
    @DisplayName("Teste de buscar pauta por ID inexistente")
    public void testBuscarPautaPorIdInexistente() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(PautaNaoEncontradaException.class, () -> {
            pautaService.buscarPautaPorId(1L);
        });

        assertEquals("Pauta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de buscar pauta por nome")
    public void testBuscarPautaPorNome() {
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");

        when(pautaRepository.findByNome("Pauta de teste")).thenReturn(Optional.of(pauta));

        Pauta resultado = pautaService.buscarPautaPorNome("Pauta de teste");

        assertNotNull(resultado);
        assertEquals(pauta, resultado);
    }

    @Test
    @DisplayName("Teste de buscar pauta por nome inexistente")
    public void testBuscarPautaPorNomeInexistente() {
        when(pautaRepository.findByNome("Pauta de teste")).thenReturn(Optional.empty());

        Throwable exception = assertThrows(PautaNaoEncontradaException.class, () -> {
            pautaService.buscarPautaPorNome("Pauta de teste");
        });

        assertEquals("Pauta não encontrada", exception.getMessage());
    }


    @Test
    @DisplayName("Teste de deletar pauta por admin")
    public void testDeletarPautaPorAdmin() {
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");

        UsuarioDTO usuarioAdmin = new UsuarioDTO();
        usuarioAdmin.setAdmin(true);

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        pautaService.deletarPauta(1L, usuarioAdmin);
    }

    @Test
    @DisplayName("Teste de deletar pauta por usuário não admin")
    public void testDeletarPautaPorNaoAdmin() {
        UsuarioDTO usuarioNaoAdmin = new UsuarioDTO();
        usuarioNaoAdmin.setAdmin(false);

        Throwable exception = assertThrows(UnauthorizedAccessException.class, () -> {
            pautaService.deletarPauta(1L, usuarioNaoAdmin);
        });

        assertEquals("Usuário não tem permissão para deletar pauta", exception.getMessage());
    }


    @Test
    @DisplayName("Teste de atualizar pauta por admin")
    public void testAtualizarPautaPorAdmin() {
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");

        UsuarioDTO usuarioAdmin = new UsuarioDTO();
        usuarioAdmin.setAdmin(true);

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setNome("Pauta de teste atualizada");

        Pauta resultado = pautaService.atualizarPauta(1L, pautaDTO, usuarioAdmin);

        assertNotNull(resultado);
        assertEquals(pautaDTO.getNome(), resultado.getNome());
    }

    @Test
    @DisplayName("Teste de atualizar pauta por usuário não admin")
    public void testAtualizarPautaPorNaoAdmin() {
        UsuarioDTO usuarioNaoAdmin = new UsuarioDTO();
        usuarioNaoAdmin.setAdmin(false);

        Throwable exception = assertThrows(UnauthorizedAccessException.class, () -> {
            pautaService.atualizarPauta(1L, new PautaDTO(), usuarioNaoAdmin);
        });

        assertEquals("Usuário não tem permissão para atualizar pauta", exception.getMessage());
    }

    


}
