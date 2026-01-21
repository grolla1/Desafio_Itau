package com.itau.devItau.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransacoesModel {
    //apesar de não persistir as informações no banco, anotação para garantir a completude da classe
    @NotNull
    private UUID id;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private OffsetDateTime dataHora;

    public UUID getId() {
        return this.id;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public OffsetDateTime getDataHora() {
        return this.dataHora;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataHora(OffsetDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
