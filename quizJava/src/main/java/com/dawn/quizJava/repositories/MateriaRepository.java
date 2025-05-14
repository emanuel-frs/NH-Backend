package com.dawn.quizJava.repositories;

import com.dawn.quizJava.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
}
