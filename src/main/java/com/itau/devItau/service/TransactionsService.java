package com.itau.devItau.service;

import com.itau.devItau.dto.TransactionsEstatisticsResponse;
import com.itau.devItau.dto.TransactionsInsertRequest;
import com.itau.devItau.exception.TransactionBusinessException;
import com.itau.devItau.model.TransactionsModel;
import org.springframework.stereotype.Service;
import com.itau.devItau.repository.TransactionsRep;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionsService {
    private final TransactionsRep repository;

    public TransactionsService(TransactionsRep repository) {
        this.repository = repository;
    }

    public TransactionsEstatisticsResponse getTransactionsStatistics(long seconds) {
        if (seconds <= 0) {
            throw new TransactionBusinessException("O parametro 'seconds' deve ser maior que zero.");
        }

        List<TransactionsModel> lastSecondsTransactions;

        Instant cutOff = Instant.now().minusSeconds(seconds);

        long cutOffMillis = cutOff.toEpochMilli(); // pega o tempo até x segundos atrás em milissegundos

        lastSecondsTransactions = repository.findTransactionsAfter(cutOffMillis);

        TransactionsEstatisticsResponse response = new TransactionsEstatisticsResponse();

        response.setCount(lastSecondsTransactions.size());
        response.setMax(findMaxValue(lastSecondsTransactions));
        response.setMin(findMinValue(lastSecondsTransactions));
        response.setAvg(defineAvg(lastSecondsTransactions));
        response.setSum(sumValues(lastSecondsTransactions));

        return response;
    }

    public void transactionsSave(TransactionsInsertRequest transaction) {
        Instant agora = Instant.now();

        if (transaction.getDataHora().toInstant().toEpochMilli() > agora.toEpochMilli()) {
            throw new TransactionBusinessException("A transação não pode ser registrada, pois a data informada é futura.");
        }

        TransactionsModel transactionsModel = new TransactionsModel();

        transactionsModel.setId(UUID.randomUUID());
        transactionsModel.setValor(transaction.getValor());
        transactionsModel.setDataHora(transaction.getDataHora());

        repository.save(transactionsModel);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private BigDecimal findMaxValue(List<TransactionsModel> transactions) {
        return transactions.stream()
                .map(TransactionsModel::getValor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal findMinValue(List<TransactionsModel> transactions) {
        return transactions.stream()
                .map(TransactionsModel::getValor)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal defineAvg(List<TransactionsModel> transactions) {
        if (transactions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal soma = BigDecimal.ZERO;
        for (TransactionsModel transaction : transactions) {
            soma = soma.add(transaction.getValor());
        }

        return soma.divide(BigDecimal.valueOf(transactions.size()), 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal sumValues(List<TransactionsModel> transactions) {
        if (transactions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal soma = BigDecimal.ZERO;
        for (TransactionsModel transaction : transactions) {
            soma = soma.add(transaction.getValor());
        }

        return soma;
    }
}
