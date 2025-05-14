package com.dawn.quizJava.models;

import com.dawn.quizJava.enums.OpcaoCorreta;
import com.dawn.quizJava.enums.Serie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "perguntas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(name = "opcao_a", nullable = false)
    private String opcaoA;

    @Column(name = "opcao_b", nullable = false)
    private String opcaoB;

    @Column(name = "opcao_c", nullable = false)
    private String opcaoC;

    @Column(name = "opcao_d", nullable = false)
    private String opcaoD;

    @Enumerated(EnumType.STRING)
    @Column(name = "resposta_correta", nullable = false, length = 1)
    private OpcaoCorreta respostaCorreta;

    @Column(nullable = false)
    private Serie serie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

}
