package com.itau.devItau.service;

import com.itau.devItau.dto.TransactionsEstatisticsResponse;
import com.itau.devItau.dto.TransactionsInsertRequest;
import com.itau.devItau.exception.TransactionBusinessException;
import com.itau.devItau.model.TransactionsModel;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.itau.devItau.repository.TransactionsRep;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionsService {
    private final TransactionsRep repository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionsService.class);

    public TransactionsService(TransactionsRep repository) {
        this.repository = repository;
    }

    public TransactionsEstatisticsResponse getTransactionsStatistics(long seconds) {
        logger.info("======= SERVICE: getTransactionsStatistics =======");
        if (seconds <= 0) {
            logger.error("Erro: O parametro 'seconds' deve ser maior que zero.");
            throw new TransactionBusinessException("O parametro 'seconds' deve ser maior que zero.");
        }

        List<TransactionsModel> lastSecondsTransactions =
                repository.findTransactionsAfter(defineCutOffMillis(seconds));

        logger.debug("Lista de Transações: {}", lastSecondsTransactions);

        BigDecimalSummaryStatistics stats = calculateStatistics(lastSecondsTransactions);

        TransactionsEstatisticsResponse response = new TransactionsEstatisticsResponse();

        response.setCount(stats.getCount());
        response.setMax(stats.getMax() == null ? BigDecimal.ZERO : stats.getMax());
        response.setMin(stats.getMin() == null ? BigDecimal.ZERO : stats.getMin());
        response.setAvg(stats.getAverage());
        response.setSum(stats.getSum());

        return response;
    }

    public void transactionsSave(TransactionsInsertRequest transaction) {
        logger.info("======= SERVICE: transactionsSave =======");
        Instant agora = Instant.now();

        if (transaction.getDataHora().toInstant().toEpochMilli() > agora.toEpochMilli()) {
            logger.error("Erro: A transação não pode ser registrada, pois a data informada é futura.");
            throw new TransactionBusinessException("A transação não pode ser registrada, pois a data informada é futura.");
        }

        TransactionsModel transactionsModel = new TransactionsModel();

        transactionsModel.setId(UUID.randomUUID());
        transactionsModel.setValor(transaction.getValor());
        transactionsModel.setDataHora(transaction.getDataHora());

        repository.save(transactionsModel);
    }

    public void deleteAll() {
        logger.info("======= SERVICE: deleteAll =======");
        repository.deleteAll();
    }

    private long defineCutOffMillis(long seconds) {
        Instant cutOff = Instant.now().minusSeconds(seconds);

        return cutOff.toEpochMilli(); // pega o tempo até x segundos atrás em milissegundos
    }

    private BigDecimalSummaryStatistics calculateStatistics(List<TransactionsModel> lastSecondsTransactions) {
        BigDecimalSummaryStatistics stats = new BigDecimalSummaryStatistics();

         lastSecondsTransactions.stream()
                .map(TransactionsModel::getValor)
                .forEach(stats::accept);

        return stats;
    }
}
