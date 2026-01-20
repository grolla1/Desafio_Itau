package com.itau.devItau.repository;

import com.itau.devItau.model.Transacoes;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransacoesInterface {

    Transacoes save(Transacoes transacao);

    Optional<Transacoes> findById(UUID id);

    List<Transacoes> findTransactionsAfter(Instant instant);

    void deleteAll();
}
