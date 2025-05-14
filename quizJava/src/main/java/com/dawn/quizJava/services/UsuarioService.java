package com.dawn.quizJava.services;

import com.dawn.quizJava.models.RespostaUsuario;
import com.dawn.quizJava.models.Usuario;
import com.dawn.quizJava.repositories.RespostaUsuarioRepository;
import com.dawn.quizJava.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final RespostaUsuarioRepository respostaUsuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RespostaUsuarioRepository respostaUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.respostaUsuarioRepository = respostaUsuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario create(Usuario usuario) {
        Usuario existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente != null) {
            throw new IllegalArgumentException("Já existe um usuário com este email.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Integer id, Usuario usuarioAtualizado) {
        Usuario existente = usuarioRepository.findById(id).orElse(null);
        if (existente == null) {
            log.warn("Usuário para atualização não encontrado: ID {}", id);
            return null;
        }
        if (usuarioAtualizado.getNome() != null) {
            existente.setNome(usuarioAtualizado.getNome());
        }
        if (usuarioAtualizado.getEmail() != null) {
            existente.setEmail(usuarioAtualizado.getEmail());
        }
        if (usuarioAtualizado.getSenha() != null) {
            existente.setSenha(usuarioAtualizado.getSenha());
        }
        if (usuarioAtualizado.getDataRegistro() != null) {
            existente.setDataRegistro(usuarioAtualizado.getDataRegistro());
        }
        try {
            return usuarioRepository.save(existente);
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário: ", e);
            return null;
        }
    }


    public Usuario delete(Integer id) {
        if (usuarioRepository.existsById(id)) {
            Usuario excluir = usuarioRepository.findById(id).orElse(null);
            try {
                usuarioRepository.deleteById(id);
                return excluir;
            } catch (Exception e) {
                log.error("Erro ao deletar usuário: ", e);
                return null;
            }
        }
        return null;
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        } else {
            log.warn("Tentativa de login falhou para o e-mail: {}", email);
            return null;
        }
    }

    public List<RespostaUsuario> getRespostasCertasDoUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            log.warn("Usuário não encontrado com ID: {}", idUsuario);
            return List.of();
        }
        return respostaUsuarioRepository.findByUsuarioAndCorretaTrue(usuario);
    }
}
