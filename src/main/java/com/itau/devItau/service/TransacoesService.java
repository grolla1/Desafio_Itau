package com.itau.devItau.service;

import com.itau.devItau.dto.TransacoesEstatisticsResponse;
import com.itau.devItau.exception.TransactionBusinessException;
import com.itau.devItau.exception.TransactionNotFoundException;
import com.itau.devItau.model.TransacoesModel;
import org.springframework.stereotype.Service;
import com.itau.devItau.repository.TransacoesRep;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
public class TransacoesService {
    private final TransacoesRep repository;

    public TransacoesService(TransacoesRep repository) {
        this.repository = repository;
    }

    public TransacoesEstatisticsResponse getTransactionsStatistics(long seconds) {
        if (seconds <= 0) {
            throw new TransactionBusinessException("O parametro 'seconds' deve ser maior que zero.");
        }

        List<TransacoesModel> lastSecondsTransactions;

        Instant cutOff = Instant.now().minusSeconds(seconds);

        lastSecondsTransactions = repository.findTransactionsAfter(cutOff);

        if (lastSecondsTransactions.isEmpty()) {
            throw new TransactionNotFoundException("Nenhuma transação foi encontrada no período informado.");
        }

        TransacoesEstatisticsResponse response = new TransacoesEstatisticsResponse();

        response.setCount(lastSecondsTransactions.size());
        response.setMax(findMaxValue(lastSecondsTransactions));
        response.setMin(findMinValue(lastSecondsTransactions));
        response.setAvg(defineAvg(lastSecondsTransactions));
        response.setSum(sumValues(lastSecondsTransactions));

        return response;
    }

    private BigDecimal findMaxValue(List<TransacoesModel> transacoes) {
        return transacoes.stream()
                .map(TransacoesModel::getValor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal findMinValue(List<TransacoesModel> transacoes) {
        return transacoes.stream()
                .map(TransacoesModel::getValor)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal defineAvg(List<TransacoesModel> transacoes) {
        if (transacoes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal soma = BigDecimal.ZERO;
        for (TransacoesModel transacao : transacoes) {
            soma = soma.add(transacao.getValor());
        }

        return soma.divide(BigDecimal.valueOf(transacoes.size()), 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal sumValues(List<TransacoesModel> transacoes) {
        if (transacoes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal soma = BigDecimal.ZERO;
        for (TransacoesModel transacao : transacoes) {
            soma = soma.add(transacao.getValor());
        }

        return soma;
    }
}
