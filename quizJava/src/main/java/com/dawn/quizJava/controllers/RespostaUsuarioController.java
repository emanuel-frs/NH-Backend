package com.dawn.quizJava.controllers;

import com.dawn.quizJava.dtos.RespostaUsuarioDTO;
import com.dawn.quizJava.models.RespostaUsuario;
import com.dawn.quizJava.services.RespostaUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resposta")
public class RespostaUsuarioController {

    @Autowired
    private RespostaUsuarioService respostaUsuarioService;

    @PostMapping
    public ResponseEntity<RespostaUsuario> salvarResposta(@Valid @RequestBody RespostaUsuario respostaUsuario) {
        RespostaUsuario respostaSalva = respostaUsuarioService.salvarResposta(respostaUsuario);
        return ResponseEntity.ok(respostaSalva);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<RespostaUsuarioDTO>> getRespostasPorUsuario(@PathVariable Integer id) {
        List<RespostaUsuario> respostas = respostaUsuarioService.buscarRespostasPorUsuario(id);

        List<RespostaUsuarioDTO> dtos = respostas.stream()
                .map(RespostaUsuarioDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{id}/acertos")
    public ResponseEntity<List<RespostaUsuarioDTO>> getAcertosPorUsuario(@PathVariable Integer id) {
        List<RespostaUsuario> acertos = respostaUsuarioService.buscarRespostasCorretasPorUsuario(id);

        List<RespostaUsuarioDTO> dtos = acertos.stream()
                .map(RespostaUsuarioDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}
