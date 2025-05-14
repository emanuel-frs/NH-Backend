package com.dawn.quizJava.controllers;

import com.dawn.quizJava.dtos.PerguntaDTO;
import com.dawn.quizJava.enums.Serie;
import com.dawn.quizJava.models.Pergunta;
import com.dawn.quizJava.services.PerguntaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pergunta")
public class PerguntaController {

    @Autowired
    private PerguntaService perguntaService;

    @GetMapping
    public List<PerguntaDTO> getPerguntas() {
        return perguntaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> findById(@PathVariable Integer id) {
        Pergunta pergunta = perguntaService.findById(id);
        if (pergunta == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(pergunta, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Pergunta> createPergunta(@Valid @RequestBody Pergunta pergunta) {
        return new ResponseEntity<>(perguntaService.create(pergunta), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pergunta> updatePergunta(@PathVariable Integer id, @RequestBody Pergunta pergunta) {
        Pergunta atualizada = perguntaService.update(id, pergunta);
        return atualizada != null
                ? new ResponseEntity<>(atualizada, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pergunta> deletePergunta(@PathVariable Integer id) {
        Pergunta pergunta = perguntaService.delete(id);
        if (pergunta == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(pergunta, HttpStatus.OK);
        }
    }

    @GetMapping("/nao-respondidas/{idMateria}/{serie}/{idUsuario}")
    public ResponseEntity<List<Pergunta>> getPerguntasNaoAcertadas(
            @PathVariable(required = false) Integer idMateria,
            @PathVariable(required = false) Serie serie,
            @PathVariable(required = false) Integer idUsuario,
            @RequestParam(required = false) Integer materiaId,
            @RequestParam(required = false) Serie serieParam,
            @RequestParam(required = false) Integer usuarioId
    ) {
        Integer finalMateriaId = idMateria != null ? idMateria : materiaId;
        Serie finalSerie = serie != null ? serie : serieParam;
        Integer finalUsuarioId = idUsuario != null ? idUsuario : usuarioId;

        List<Pergunta> perguntas = perguntaService.buscarPerguntasNaoAcertadas(
                finalMateriaId, finalSerie, finalUsuarioId);

        if (perguntas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(perguntas, HttpStatus.OK);
    }

    @GetMapping("/acertadas/{idMateria}/{serie}/{idUsuario}")
    public ResponseEntity<List<Pergunta>> getPerguntasAcertadas(
            @PathVariable(required = false) Integer idMateria,
            @PathVariable(required = false) Serie serie,
            @PathVariable(required = false) Integer idUsuario,
            @RequestParam(required = false) Integer materiaId,
            @RequestParam(required = false) Serie serieParam,
            @RequestParam(required = false) Integer usuarioId
    ) {
        Integer finalMateriaId = idMateria != null ? idMateria : materiaId;
        Serie finalSerie = serie != null ? serie : serieParam;
        Integer finalUsuarioId = idUsuario != null ? idUsuario : usuarioId;

        List<Pergunta> perguntas = perguntaService.buscarPerguntasAcertadas(
                finalMateriaId, finalSerie, finalUsuarioId);

        if (perguntas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(perguntas, HttpStatus.OK);
    }

    @PostMapping("/lote")
    public ResponseEntity<List<Pergunta>> createBatch(@RequestBody List<Pergunta> perguntas) {
        List<Pergunta> salvas = perguntaService.createBatch(perguntas);
        return ResponseEntity.ok(salvas);
    }

    @GetMapping("/materia/{idMateria}")
    public ResponseEntity<List<PerguntaDTO>> getPerguntasPorMateria(@PathVariable Integer idMateria) {
        List<PerguntaDTO> perguntas = perguntaService.findByMateria(idMateria);
        if (perguntas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(perguntas, HttpStatus.OK);
    }

    @GetMapping("/serie/{serie}")
    public ResponseEntity<List<PerguntaDTO>> getPerguntasPorSerie(@PathVariable Serie serie) {
        List<PerguntaDTO> perguntas = perguntaService.findBySerie(serie);
        if (perguntas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(perguntas, HttpStatus.OK);
    }

    @GetMapping("/filtro/{idMateria}/{serie}")
    public ResponseEntity<List<PerguntaDTO>> getPerguntasPorMateriaESerie(
            @PathVariable Integer idMateria,
            @PathVariable Serie serie) {
        List<PerguntaDTO> perguntas = perguntaService.findByMateriaAndSerie(idMateria, serie);
        if (perguntas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(perguntas, HttpStatus.OK);
    }

    @GetMapping("/porcentagem-acertos/{idMateria}/{serie}/{idUsuario}")
    public ResponseEntity<String> getPorcentagemAcertos(
            @PathVariable Integer idMateria,
            @PathVariable Serie serie,
            @PathVariable Integer idUsuario
    ) {
        List<Pergunta> acertadas = perguntaService.buscarPerguntasAcertadas(idMateria, serie, idUsuario);
        List<Pergunta> naoAcertadas = perguntaService.buscarPerguntasNaoAcertadas(idMateria, serie, idUsuario);

        int totalAcertos = acertadas.size();
        int totalErros = naoAcertadas.size();
        int totalQuestoes = totalAcertos + totalErros;

        if (totalQuestoes == 0) {
            return new ResponseEntity<>("0%", HttpStatus.OK);
        }

        double razaoAcertos = (double) totalAcertos / totalQuestoes;
        int porcentagem = (int) Math.round(razaoAcertos * 100);
        String resultado = porcentagem + "%";

        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

}
