package com.example.desafiovotacao.ServiceTeste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.desafiovotacao.Exception.FalhaAoAtualizarPautaException;
import com.example.desafiovotacao.Exception.PautaNaoCriadaException;
import com.example.desafiovotacao.Exception.PautaNaoEncontradaException;
import com.example.desafiovotacao.dto.PautaDTO;
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
    @DisplayName("Teste de criação de pauta")
    public void testCriarPauta(){
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setNome("Pauta de teste");

        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");

        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        Pauta resultado = pautaService.criarPauta(pautaDTO);

        assertEquals(pauta, resultado);
    }

    @Test
    @DisplayName("Teste de criação de pauta triste")
    public void testCriarPautaTriste(){
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setNome("");

        Throwable exception = assertThrows(PautaNaoCriadaException.class, () -> {
            pautaService.criarPauta(pautaDTO);
        });
        
        assertEquals("Pauta não criada", exception.getMessage());
    }


    @Test
    @DisplayName("Teste de busca de pauta por ID")
    public void testBuscarPautaPorId(){
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");
    
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
    
        Pauta resultado = pautaService.buscarPautaPorId(1L);
        
        assertNotNull(resultado);
        assertEquals(pauta.getNome(), resultado.getNome());
    }

    @Test
    @DisplayName("Teste de busca por id de pauta não encontrada")
    public void testBuscarPautaPorIdNaoEncontrada(){
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());
    
        Throwable exception = assertThrows(PautaNaoEncontradaException.class, () -> {
            pautaService.buscarPautaPorId(1L);
        });

        assertEquals("Pauta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de busca de pauta por nome")
    public void testBuscarPautaPorNome(){
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");
    
        when(pautaRepository.findByNome("Pauta de teste")).thenReturn(Optional.of(pauta));
    
        Pauta resultado = pautaService.buscarPautaPorNome("Pauta de teste");
        
        assertNotNull(resultado);
        assertEquals(pauta.getNome(), resultado.getNome());
    }

    @Test
    @DisplayName("Teste de busca por nome de pauta por nome não encontrada")
    public void testBuscarPautaPorNomeNaoEncontrada(){
        when(pautaRepository.findByNome("Pauta de teste")).thenReturn(Optional.empty());
    
        Throwable exception = assertThrows(PautaNaoEncontradaException.class, () -> {
            pautaService.buscarPautaPorNome("Pauta de teste");
        });

        assertEquals("Pauta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de deletar pauta")
    public void testDeletarPauta(){
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");
    
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
    
        pautaService.deletarPauta(1L);
    }

    @Test
    @DisplayName("Teste de deletar pauta não encontrada")
    public void testDeletarPautaNaoEncontrada(){
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());
    
        Throwable exception = assertThrows(PautaNaoEncontradaException.class, () -> {
            pautaService.deletarPauta(1L);
        });

        assertEquals("Pauta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de atualizar pauta")
    public void testAtualizarPauta(){
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");
    
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);
    
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setNome("Pauta de teste atualizada");

        Pauta resultado = pautaService.atualizarPauta(1L, pautaDTO);
        
        assertNotNull(resultado);
        assertEquals(pautaDTO.getNome(), resultado.getNome());
    }

    @Test
    @DisplayName("Teste de atualizar pauta não encontrada")
    public void testAtualizarPautaNaoEncontrada(){
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());
    
        Throwable exception = assertThrows(PautaNaoEncontradaException.class, () -> {
            pautaService.atualizarPauta(1L, new PautaDTO());
        });

        assertEquals("Pauta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de atualizar pauta triste")
    public void testAtualizarPautaTriste(){
        Pauta pauta = new Pauta();
        pauta.setNome("Pauta de teste");
    
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
    
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setNome("");

        Throwable exception = assertThrows(FalhaAoAtualizarPautaException.class, () -> {
            pautaService.atualizarPauta(1L, pautaDTO);
        });

        assertEquals("Falha ao atualizar pauta", exception.getMessage());
    }

    @Test
    @DisplayName("Teste buscar todas as pautas")
    public void testBuscarTodasPautas(){

        Pauta pauta1 = new Pauta();
        pauta1.setNome("Pauta de teste 1");

        Pauta pauta2 = new Pauta();
        pauta2.setNome("Pauta de teste 2");

        when(pautaRepository.findAll()).thenReturn(Arrays.asList(pauta1, pauta2));

        List<Pauta> resultado = pautaService.buscarTodasPautas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(pauta1), "Pauta de teste 1 não encontrada");
        assertTrue(resultado.contains(pauta2), "Pauta de teste 2 não encontrada");
    }

    @Test
    @DisplayName("Teste buscar todas as pautas triste")
    public void testBuscarTodasPautasTriste(){

        when(pautaRepository.findAll()).thenReturn(Arrays.asList());

        Throwable exception = assertThrows(PautaNaoEncontradaException.class, () -> {
            pautaService.buscarTodasPautas();
        });

        assertEquals("Nenhuma pauta encontrada", exception.getMessage());
    }

    

    
}
