package com.dawn.quizJava.services;

import com.dawn.quizJava.dtos.PerguntaDTO;
import com.dawn.quizJava.enums.Serie;
import com.dawn.quizJava.models.Materia;
import com.dawn.quizJava.models.Pergunta;
import com.dawn.quizJava.models.Usuario;
import com.dawn.quizJava.repositories.MateriaRepository;
import com.dawn.quizJava.repositories.PerguntaRepository;
import com.dawn.quizJava.repositories.RespostaUsuarioRepository;
import com.dawn.quizJava.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PerguntaService {

    private static final Logger log = LoggerFactory.getLogger(PerguntaService.class);

    private final PerguntaRepository perguntaRepository;
    private final RespostaUsuarioRepository respostaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final MateriaRepository materiaRepository;

    public PerguntaService(
            PerguntaRepository perguntaRepository,
            RespostaUsuarioRepository respostaUsuarioRepository,
            UsuarioRepository usuarioRepository,
            MateriaRepository materiaRepository
    ) {
        this.perguntaRepository = perguntaRepository;
        this.respostaUsuarioRepository = respostaUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<PerguntaDTO> findAll() {
        List<Pergunta> perguntas = perguntaRepository.findAll();
        return perguntas.stream()
                .map(PerguntaDTO::new)
                .collect(Collectors.toList());
    }

    public Pergunta findById(Integer id) {
        return perguntaRepository.findById(id).orElse(null);
    }

    public Pergunta create(Pergunta pergunta) {
        return perguntaRepository.save(pergunta);
    }

    public Pergunta update(Integer id, Pergunta perguntaAtualizada) {
        Pergunta existente = perguntaRepository.findById(id).orElse(null);
        if (existente == null) {
            log.warn("Pergunta para atualização não encontrada: ID {}", id);
            return null;
        }

        if (perguntaAtualizada.getEnunciado() != null) {
            existente.setEnunciado(perguntaAtualizada.getEnunciado());
        }
        if (perguntaAtualizada.getOpcaoA() != null) {
            existente.setOpcaoA(perguntaAtualizada.getOpcaoA());
        }
        if (perguntaAtualizada.getOpcaoB() != null) {
            existente.setOpcaoB(perguntaAtualizada.getOpcaoB());
        }
        if (perguntaAtualizada.getOpcaoC() != null) {
            existente.setOpcaoC(perguntaAtualizada.getOpcaoC());
        }
        if (perguntaAtualizada.getOpcaoD() != null) {
            existente.setOpcaoD(perguntaAtualizada.getOpcaoD());
        }
        if (perguntaAtualizada.getRespostaCorreta() != null) {
            existente.setRespostaCorreta(perguntaAtualizada.getRespostaCorreta());
        }
        if (perguntaAtualizada.getSerie() != null) {
            existente.setSerie(perguntaAtualizada.getSerie());
        }
        if (perguntaAtualizada.getMateria() != null) {
            existente.setMateria(perguntaAtualizada.getMateria());
        }
        try {
            return perguntaRepository.save(existente);
        } catch (Exception e) {
            log.error("Erro ao atualizar pergunta: ", e);
            return null;
        }
    }


    public Pergunta delete(Integer id) {
        if (perguntaRepository.existsById(id)) {
            Pergunta excluir = perguntaRepository.findById(id).orElse(null);
            try {
                perguntaRepository.deleteById(id);
                return excluir;
            } catch (Exception e) {
                log.error("Erro ao deletar pergunta: ", e);
                return null;
            }
        }
        return null;
    }

    public List<Pergunta> buscarPerguntasNaoAcertadas(Integer idMateria, Serie serie, Integer idUsuario) {
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if (materiaOpt.isEmpty() || usuarioOpt.isEmpty()) {
            log.warn("Matéria ou usuário não encontrado.");
            return Collections.emptyList();
        }

        Materia materia = materiaOpt.get();
        Usuario usuario = usuarioOpt.get();

        List<Pergunta> todasPerguntas = perguntaRepository.findByMateriaAndSerie(materia, serie);
        Set<Integer> perguntasAcertadasIds = respostaUsuarioRepository
                .findByUsuarioAndCorretaTrue(usuario)
                .stream()
                .map(resposta -> resposta.getPergunta().getId())
                .collect(Collectors.toSet());

        return todasPerguntas.stream()
                .filter(p -> !perguntasAcertadasIds.contains(p.getId()))
                .collect(Collectors.toList());
    }

    public List<Pergunta> buscarPerguntasAcertadas(Integer idMateria, Serie serie, Integer idUsuario) {
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if (materiaOpt.isEmpty() || usuarioOpt.isEmpty()) {
            log.warn("Matéria ou usuário não encontrado.");
            return Collections.emptyList();
        }

        Materia materia = materiaOpt.get();
        Usuario usuario = usuarioOpt.get();

        List<Pergunta> todasPerguntas = perguntaRepository.findByMateriaAndSerie(materia, serie);
        Set<Integer> perguntasAcertadasIds = respostaUsuarioRepository
                .findByUsuarioAndCorretaTrue(usuario)
                .stream()
                .map(resposta -> resposta.getPergunta().getId())
                .collect(Collectors.toSet());

        return todasPerguntas.stream()
                .filter(p -> perguntasAcertadasIds.contains(p.getId())) // Apenas perguntas acertadas
                .collect(Collectors.toList());
    }

    public List<Pergunta> createBatch(List<Pergunta> perguntas) {
        if (perguntas == null || perguntas.isEmpty()) {
            log.warn("Lista de perguntas está vazia ou nula.");
            return Collections.emptyList();
        }

        return perguntaRepository.saveAll(perguntas);
    }

    // No seu PerguntaService.java

    public List<PerguntaDTO> findByMateria(Integer idMateria) {
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);

        if (materiaOpt.isEmpty()) {
            log.warn("Matéria não encontrada com ID: {}", idMateria);
            return Collections.emptyList();
        }

        List<Pergunta> perguntas = perguntaRepository.findByMateria(materiaOpt.get());
        return perguntas.stream()
                .map(PerguntaDTO::new)
                .collect(Collectors.toList());
    }

    public List<PerguntaDTO> findBySerie(Serie serie) {
        List<Pergunta> perguntas = perguntaRepository.findBySerie(serie);
        return perguntas.stream()
                .map(PerguntaDTO::new)
                .collect(Collectors.toList());
    }

    public List<PerguntaDTO> findByMateriaAndSerie(Integer idMateria, Serie serie) {
        Optional<Materia> materiaOpt = materiaRepository.findById(idMateria);

        if (materiaOpt.isEmpty()) {
            log.warn("Matéria não encontrada com ID: {}", idMateria);
            return Collections.emptyList();
        }

        List<Pergunta> perguntas = perguntaRepository.findByMateriaAndSerie(materiaOpt.get(), serie);
        return perguntas.stream()
                .map(PerguntaDTO::new)
                .collect(Collectors.toList());
    }

}
