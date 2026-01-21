package com.itau.devItau.repository;

import com.itau.devItau.model.TransacoesModel;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class TransacoesRep implements TransacoesInterface{

    private final Map<UUID, TransacoesModel> memoStorage = new HashMap<>();

    @Override
    public TransacoesModel save(TransacoesModel transacao) {
        if (transacao.getId() == null) {
            transacao.setId(UUID.randomUUID());
        }
        memoStorage.put(transacao.getId(), transacao);
        return transacao;
    }

    @Override
    public Optional<TransacoesModel> findById(UUID id) {
        return Optional.ofNullable(memoStorage.get(id));
    }

    @Override
    // recebe o tempo passado limite em que deve ser feito o filtro de transações
    public List<TransacoesModel> findTransactionsAfter(Instant cutOff) {
        long cutOffMillis = cutOff.toEpochMilli(); // pega o tempo até x segundos atrás em milissegundos

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
