package com.dawn.quizJava.enums;

public enum Serie {
    SEXTO(6, "6º ano"),
    SETIMO(7, "7º ano"),
    OITAVO(8, "8º ano"),
    NONO(9, "9º ano");

    private final int valor;
    private final String nomeFormatado;

    Serie(int valor, String nomeFormatado) {
        this.valor = valor;
        this.nomeFormatado = nomeFormatado;
    }

    public int getValor() {
        return valor;
    }

    public String getNomeFormatado() {
        return nomeFormatado;
    }

    public static Serie fromValor(int valor) {
        for (Serie s : Serie.values()) {
            if (s.valor == valor) {
                return s;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Série: " + valor);
    }
}



