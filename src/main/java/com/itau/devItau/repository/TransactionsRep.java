package com.itau.devItau.repository;

import com.itau.devItau.model.TransactionsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class TransactionsRep implements TransactionsInterface {

    private final Map<UUID, TransactionsModel> memoStorage = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(TransactionsRep.class);

    @Override
    public TransactionsModel save(TransactionsModel transacao) {
        logger.info("======= REPOSITORY: save =======");
        memoStorage.put(transacao.getId(), transacao);
        return transacao;
    }

    @Override
    public Optional<TransactionsModel> findById(UUID id) {
        logger.info("======= REPOSITORY: findById =======");
        return Optional.ofNullable(memoStorage.get(id));
    }

    @Override
    // recebe o tempo passado limite em que deve ser feito o filtro de transações
    public List<TransactionsModel> findTransactionsAfter(long cutOffMillis) {
        logger.info("======= REPOSITORY: findTransactionsAfter =======");
        return memoStorage.values()
                .stream()
                .filter(
                        t -> toMilliSeconds(t.getDataHora()) >= cutOffMillis
                ).toList();
    }

    @Override
    public void deleteAll() {
        logger.info("======= REPOSITORY: deleteAll =======");
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
