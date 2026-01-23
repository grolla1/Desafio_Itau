package com.itau.devItau.repository;

import com.itau.devItau.model.TransactionsModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionsInterface {

    TransactionsModel save(TransactionsModel transacao);

    Optional<TransactionsModel> findById(UUID id);

    List<TransactionsModel> findTransactionsAfter(long cutOffMillis);

    void deleteAll();
}
