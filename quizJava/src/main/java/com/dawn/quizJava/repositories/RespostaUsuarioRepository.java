package com.dawn.quizJava.repositories;

import com.dawn.quizJava.models.RespostaUsuario;
import com.dawn.quizJava.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaUsuarioRepository extends JpaRepository<RespostaUsuario, Integer> {
    List<RespostaUsuario> findByUsuario(Usuario usuario);
    List<RespostaUsuario> findByUsuarioAndCorretaTrue(Usuario usuario);
}