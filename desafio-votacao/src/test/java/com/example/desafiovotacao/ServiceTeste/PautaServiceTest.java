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

    
    
}
