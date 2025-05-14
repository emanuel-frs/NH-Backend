package com.dawn.quizJava.services;

import com.dawn.quizJava.models.RespostaUsuario;
import com.dawn.quizJava.repositories.RespostaUsuarioRepository;
import com.dawn.quizJava.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RespostaUsuarioService {

    private static final Logger log = LoggerFactory.getLogger(RespostaUsuarioService.class);

    private final RespostaUsuarioRepository respostaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    public RespostaUsuarioService(RespostaUsuarioRepository respostaUsuarioRepository,
                                  UsuarioRepository usuarioRepository) {
        this.respostaUsuarioRepository = respostaUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RespostaUsuario salvarResposta(RespostaUsuario resposta) {
        return respostaUsuarioRepository.save(resposta);
    }

    public List<RespostaUsuario> buscarRespostasPorUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(respostaUsuarioRepository::findByUsuario)
                .orElse(Collections.emptyList());
    }

    public List<RespostaUsuario> buscarRespostasCorretasPorUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(respostaUsuarioRepository::findByUsuarioAndCorretaTrue)
                .orElse(Collections.emptyList());
    }
}
