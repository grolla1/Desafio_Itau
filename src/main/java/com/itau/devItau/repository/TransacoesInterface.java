package com.itau.devItau.repository;

import com.itau.devItau.model.Transacoes;

import java.util.List;
import java.util.Optional;

public interface TransacoesRepository {

    Transacoes save(Transacoes transacao);

    Optional<Transacoes> findById(Long id);

    Optional<Transacoes> findByValue(Float valor);

    List<Transacoes> findLast60Sec();

    List<Transacoes> findInLastSeconds(Long seconds);

    Void deleteAll();
}
