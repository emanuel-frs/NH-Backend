package com.dawn.quizJava.repositories;

import com.dawn.quizJava.enums.Serie;
import com.dawn.quizJava.models.Materia;
import com.dawn.quizJava.models.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
    List<Pergunta> findByMateriaAndSerie(Materia materia, Serie serie);
    List<Pergunta> findByMateria(Materia materia);
    List<Pergunta> findBySerie(Serie serie);
}
