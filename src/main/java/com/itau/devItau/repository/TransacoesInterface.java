package com.itau.devItau.repository;

import com.itau.devItau.model.TransacoesModel;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransacoesInterface {

    TransacoesModel save(TransacoesModel transacao);

    Optional<TransacoesModel> findById(UUID id);

    List<TransacoesModel> findTransactionsAfter(Instant instant);

    void deleteAll();
}
