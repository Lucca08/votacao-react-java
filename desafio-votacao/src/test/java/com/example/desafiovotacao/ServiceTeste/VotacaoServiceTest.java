package com.example.desafiovotacao.ServiceTeste;

import com.example.desafiovotacao.Exception.UsuarioJaVotouNaPautaException;
import com.example.desafiovotacao.Exception.UsuarioNaoEncontradoException;
import com.example.desafiovotacao.model.Pauta;
import com.example.desafiovotacao.model.Votacao;
import com.example.desafiovotacao.repository.VotacaoRepository;
import com.example.desafiovotacao.service.VotacaoService.VotacaoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotacaoServiceTest {

    @Mock
    private VotacaoRepository votacaoRepository;

    @InjectMocks
    private VotacaoService votacaoService;

    @Test
    public void votar_UsuarioNaoVotouNaPauta_SalvaVotacao() {
        Votacao voto = new Votacao();
        voto.setVoto(true);
        voto.setPauta(new Pauta());
        voto.setVotacaoId(1L);
    
        when(votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(1L, 1L)).thenReturn(Optional.empty());
    
        votacaoService.votar(1L, 1L, true); 
    
        assertEquals(1L, voto.getVotacaoId());
    }
    
    @Test
    public void votar_UsuarioJaVotouNaPauta_LancaExcecao() {
        Votacao voto = new Votacao();
        voto.setVoto(true);
        voto.setPauta(new Pauta());
        voto.setVotacaoId(1L);
    
        when(votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(1L, 1L)).thenReturn(Optional.of(voto));
    
        UsuarioJaVotouNaPautaException exception = assertThrows(UsuarioJaVotouNaPautaException.class, () -> {
            votacaoService.votar(1L, 1L, true);
        });
    
        assertEquals("Usuário já votou na pauta", exception.getMessage());
    }
    

    @Test
    public void testContarVotos() {
        Long idPauta = 1L;

        Votacao voto1 = new Votacao();
        voto1.setVoto(true);

        Votacao voto2 = new Votacao();
        voto2.setVoto(true);

        Votacao voto3 = new Votacao();
        voto3.setVoto(false);

        List<Votacao> votos = List.of(voto1, voto2, voto3);
    
        when(votacaoRepository.findByPautaPautaId(idPauta)).thenReturn(votos);
    
        int totalVotos = votacaoService.contarVotos(idPauta);
    
        assertEquals(1, totalVotos); 
    }

    @Test
    public void testVotar_UsuarioJaVotou() {
        Votacao votacao = new Votacao();
        votacao.setVoto(true);
        votacao.setPauta(new Pauta());
        votacao.setVotacaoId(1L);
    
        when(votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(1L, 1L)).thenReturn(Optional.of(votacao));

        UsuarioJaVotouNaPautaException exception = assertThrows(UsuarioJaVotouNaPautaException.class, () -> {
            votacaoService.votar(1L, 1L, true);
        });

        assertEquals("Usuário já votou na pauta", exception.getMessage());
    }

   

    @Test
    public void testAtualizarVoto_UsuarioNaoEncontrado() {
        Votacao voto = new Votacao();
        voto.setVoto(true);
        voto.setPauta(new Pauta());
        voto.setVotacaoId(1L);
    
        when(votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(1L, 1L)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception =assertThrows(UsuarioNaoEncontradoException.class, () -> {
            votacaoService.atualizarVoto(1L, 1L, true);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    public void testUsuarioPodeVotar_UsuarioJaVotou() {
        Votacao voto = new Votacao();
        voto.setVoto(true);
        voto.setPauta(new Pauta());
        voto.setVotacaoId(1L);
    
        when(votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(1L, 1L)).thenReturn(Optional.of(voto));
        
        assertFalse(votacaoService.usuarioPodeVotar(1L, 1L));
    }

    @Test
    public void testAtualizarVoto_UsuarioEncontrado_AtualizaVoto() {
        Votacao voto = new Votacao();
        voto.setVoto(true);
        voto.setPauta(new Pauta());
        voto.setVotacaoId(1L);

        when(votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(1L, 1L)).thenReturn(Optional.of(voto));

        votacaoService.atualizarVoto(1L, 1L, false);

        assertFalse(voto.isVoto());
        verify(votacaoRepository).save(voto);

    }

    @Test
    public void testAtualizarVoto_UsuarioNaoEncontrado_LancaExcecao() {
        Votacao voto = new Votacao();
        voto.setVoto(true);
        voto.setPauta(new Pauta());
        voto.setVotacaoId(1L);
        
        when(votacaoRepository.findByPautaPautaIdAndUsuarioUsuarioId(1L, 1L)).thenReturn(Optional.empty());
        
        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            votacaoService.atualizarVoto(1L, 1L, false);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(votacaoRepository, never()).save(any(Votacao.class));
    }

    

}
