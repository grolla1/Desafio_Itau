package com.itau.devItau.repository;

import com.itau.devItau.model.TransacoesModel;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransacoesInterface {

    TransacoesModel save(TransacoesModel transacao);

    Optional<TransacoesModel> findById(UUID id);

    List<TransacoesModel> findTransactionsAfter(Instant instant);

    void deleteAll();
}
