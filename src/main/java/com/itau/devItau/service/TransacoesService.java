package com.itau.devItau.service;

import com.itau.devItau.dto.TransacoesResponse;
import com.itau.devItau.model.TransacoesModel;
import org.springframework.stereotype.Service;
import com.itau.devItau.repository.TransacoesRep;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacoesService {
    private final TransacoesRep repository;

    public TransacoesService(TransacoesRep repository) {
        this.repository = repository;
    }

    public TransacoesResponse getTransactionsStatistics(long seconds) {
        List<TransacoesModel> lastSecondsTransactions = new ArrayList<>();

        Instant cutOff = Instant.now().minusSeconds(seconds);

        lastSecondsTransactions = repository.findTransactionsAfter(cutOff);

        TransacoesResponse response = new TransacoesResponse();

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
