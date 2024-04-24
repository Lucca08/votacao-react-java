package com.example.desafiovotacao.service.PautaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafiovotacao.Exception.PautaNaoEncontradaException;
import com.example.desafiovotacao.dto.PautaDTO;
import com.example.desafiovotacao.model.Pauta;
import com.example.desafiovotacao.repository.PautaRepository;

import jakarta.transaction.Transactional;

@Service
public class PautaService {
   
    private PautaRepository pautaRepository;

    @Autowired
    public PautaService(PautaRepository pautaRepository){
        this.pautaRepository = pautaRepository;
    }
  
    @Transactional
    public Pauta criarPauta(PautaDTO pautaDTO) {
        Pauta pauta = new Pauta();
        pauta.setNome(pautaDTO.getNome());
        return pautaRepository.save(pauta);
    }

    @Transactional
    public Pauta buscarPautaPorId(Long pautaId) {
        return pautaRepository.findById(pautaId)
            .orElseThrow(() -> new PautaNaoEncontradaException("Pauta não encontrada"));
        
    }

    @Transactional
    public Pauta buscarPautaPorNome(String nome) {
        return pautaRepository.findByNome(nome)
            .orElseThrow(() -> new PautaNaoEncontradaException("Pauta não encontrada"));
    }

    @Transactional
    public void deletarPauta(Long pautaId) {
        Pauta pauta = buscarPautaPorId(pautaId);
        pautaRepository.delete(pauta);
    }


}
