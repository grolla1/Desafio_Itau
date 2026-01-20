package com.itau.devItau.model;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Transacoes {
    //apesar de não persistir as informações no banco, anotação para garantir a completude da classe
    @NotNull
    private UUID id;
    @NotNull
    private Float valor;
    @NotNull
    private OffsetDateTime dataHora;

    public UUID getId() {
        return this.id;
    }

    public Float getValor() {
        return this.valor;
    }

    public OffsetDateTime getDataHora() {
        return this.dataHora;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public void setDataHora(OffsetDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
