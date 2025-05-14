package com.dawn.quizJava.dtos;

import com.dawn.quizJava.models.RespostaUsuario;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RespostaUsuarioDTO {
    private String nomeUsuario;
    private String enunciadoPergunta;
    private Boolean correta;
    private LocalDateTime dataResposta;

    // Método estático para conversão de Entidade para DTO
    public static RespostaUsuarioDTO fromEntity(RespostaUsuario respostaUsuario) {
        RespostaUsuarioDTO dto = new RespostaUsuarioDTO();
        dto.setNomeUsuario(respostaUsuario.getUsuario().getNome());
        dto.setEnunciadoPergunta(respostaUsuario.getPergunta().getEnunciado());
        dto.setCorreta(respostaUsuario.getCorreta());
        dto.setDataResposta(respostaUsuario.getDataResposta());
        return dto;
    }
}