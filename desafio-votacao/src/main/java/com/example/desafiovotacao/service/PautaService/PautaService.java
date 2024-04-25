package com.example.desafiovotacao.service.PautaService;

import java.util.List;
import java.util.logging.Logger;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.desafiovotacao.Exception.PautaNaoCriadaException;
import com.example.desafiovotacao.Exception.PautaNaoEncontradaException;
import com.example.desafiovotacao.dto.PautaDTO;
import com.example.desafiovotacao.model.Pauta;
import com.example.desafiovotacao.repository.PautaRepository;

@Service
public class PautaService {

    private static final Logger LOGGER = Logger.getLogger(PautaService.class.getName());

    private PautaRepository pautaRepository;

    @Autowired
    public PautaService(PautaRepository pautaRepository){
        this.pautaRepository = pautaRepository;
    }
  
    @Transactional
    public Pauta criarPauta(PautaDTO pautaDTO) {
        LOGGER.info("Criando nova pauta: " + pautaDTO.getNome());
        Pauta pauta = new Pauta();
        pauta.setNome(pautaDTO.getNome());
        Pauta novaPauta = pautaRepository.save(pauta);
        
        if (novaPauta == null) {
            LOGGER.warning("Pauta não criada");
            throw new PautaNaoCriadaException("Pauta não criada");
        }
        
        LOGGER.info("Pauta criada com sucesso: " + novaPauta.getPautaId());
        return novaPauta;
    }
    
    @Transactional
    public Pauta buscarPautaPorId(Long id) {
        return pautaRepository.findById(id)
            .orElseThrow(() -> {
                LOGGER.warning("Pauta não encontrada para o ID: " + id);
                return new PautaNaoEncontradaException("Pauta não encontrada");
            });
    }

    @Transactional
    public Pauta buscarPautaPorNome(String nome) {
        LOGGER.info("Buscando pauta por nome: " + nome);
        return pautaRepository.findByNome(nome)
            .orElseThrow(() -> {
                LOGGER.warning("Pauta não encontrada para o nome: " + nome);
                return new PautaNaoEncontradaException("Pauta não encontrada");
            });
    }

    @Transactional
    public void deletarPauta(Long pautaId) {
        LOGGER.info("Deletando pauta com ID: " + pautaId);
        Pauta pauta = buscarPautaPorId(pautaId);
        
        if (pauta == null) {
            LOGGER.warning("Pauta não encontrada para o ID: " + pautaId);
            throw new PautaNaoEncontradaException("Pauta não encontrada");
        }

        pautaRepository.delete(pauta);
        LOGGER.info("Pauta deletada com sucesso");

    }

    @Transactional
    public Pauta atualizarPauta(Long pautaId, PautaDTO pautaDTO) {
        LOGGER.info("Atualizando pauta com ID: " + pautaId);
        Pauta pauta = buscarPautaPorId(pautaId);
        pauta.setNome(pautaDTO.getNome());
        LOGGER.info("Pauta atualizada com sucesso");
        return pautaRepository.save(pauta);
    }

    @Transactional
    public List<Pauta> buscarTodasPautas() {
        LOGGER.info("Buscando todas as pautas");
        return pautaRepository.findAll();
    }
}
