package com.dawn.quizJava.services;

import com.dawn.quizJava.models.Materia;
import com.dawn.quizJava.repositories.MateriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    private static final Logger log = LoggerFactory.getLogger(MateriaService.class);

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public Materia findById(Integer id) {
        return materiaRepository.findById(id).orElse(null);
    }

    public Materia create(Materia materia) {
        return materiaRepository.save(materia);
    }

    public Materia update(Materia materia) {
        return materiaRepository.save(materia);
    }

    public Materia delete(Integer id) {
        if (materiaRepository.existsById(id)) {
            Materia excluir = materiaRepository.findById(id).orElse(null);
            try {
                materiaRepository.deleteById(id);
                return excluir;
            } catch (Exception e) {
                MateriaService.log.error("error: ", e);
                return null;
            }
        }
        return null;
    }

}
