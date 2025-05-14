package com.dawn.quizJava.controllers;

import com.dawn.quizJava.models.Materia;
import com.dawn.quizJava.services.MateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    MateriaService materiaService;

    @GetMapping
    public ResponseEntity<List<Materia>> findAllMateria(){
        return new ResponseEntity<>(materiaService.findAll(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Materia> findvByID(@PathVariable Integer id){
        Materia materia = materiaService.findById(id);
        if (materia == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(materia, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Materia> createMateria(@Valid @RequestBody Materia materia){
        return new ResponseEntity<>(materiaService.create(materia), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Materia> updateMateria(@Valid @RequestBody Materia materia){
        return new ResponseEntity<>(materiaService.update(materia), HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Materia> deleteMateria(@PathVariable Integer id){
        Materia materia = materiaService.delete(id);
        if (materia == null){
            return new ResponseEntity<>(materia, HttpStatus.NOT_FOUND);
        } else {
            materiaService.delete(id);
            return new ResponseEntity<>(materia, HttpStatus.OK);
        }
    }

}
