package com.dawn.quizJava.dtos;

import com.dawn.quizJava.enums.OpcaoCorreta;
import com.dawn.quizJava.models.Pergunta;
import lombok.Data;

@Data
public class PerguntaDTO {
    private Integer id;
    private String enunciado;
    private String opcaoA;
    private String opcaoB;
    private String opcaoC;
    private String opcaoD;
    private OpcaoCorreta respostaCorreta;
    private String nomeMateria;

    public PerguntaDTO(Pergunta pergunta) {
        this.id = pergunta.getId();
        this.enunciado = pergunta.getEnunciado();
        this.opcaoA = pergunta.getOpcaoA();
        this.opcaoB = pergunta.getOpcaoB();
        this.opcaoC = pergunta.getOpcaoC();
        this.opcaoD = pergunta.getOpcaoD();
        this.respostaCorreta = pergunta.getRespostaCorreta();
        this.nomeMateria = pergunta.getMateria().getNome();
    }
}

