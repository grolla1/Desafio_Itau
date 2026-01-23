package com.itau.devItau.repository;

import com.itau.devItau.model.TransactionsModel;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class TransactionsRep implements TransactionsInterface {

    private final Map<UUID, TransactionsModel> memoStorage = new HashMap<>();

    @Override
    public TransactionsModel save(TransactionsModel transacao) {
        if (transacao.getId() == null) {
            transacao.setId(UUID.randomUUID());
        }
        memoStorage.put(transacao.getId(), transacao);
        return transacao;
    }

    @Override
    public Optional<TransactionsModel> findById(UUID id) {
        return Optional.ofNullable(memoStorage.get(id));
    }

    @Override
    // recebe o tempo passado limite em que deve ser feito o filtro de transações
    public List<TransactionsModel> findTransactionsAfter(long cutOffMillis) {
        return memoStorage.values()
                .stream()
                .filter(
                        t -> toMilliSeconds(t.getDataHora()) >= cutOffMillis
                ).toList();
    }

    @Override
    public void deleteAll() {
        memoStorage.clear();
    }

    /**
     * @param dataHora Data hora no formato OffsetDateTime
     * @return retorna o tempo em milissegundos
     */
    private long toMilliSeconds(OffsetDateTime dataHora) {
        return dataHora.toInstant().toEpochMilli();
    }
}
