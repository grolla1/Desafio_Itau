package com.itau.devItau.controller;

import com.itau.devItau.dto.TransactionsEstatisticsResponse;
import com.itau.devItau.dto.TransactionsInsertRequest;
import com.itau.devItau.service.TransactionsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionsController {
    private final TransactionsService service;

    public TransactionsController(TransactionsService service) {
        this.service = service;
    }

    @GetMapping("/estatistica")
    public TransactionsEstatisticsResponse getEstatistics(@RequestParam long seconds) {
        return service.getTransactionsStatistics(seconds);
    }

    @PostMapping("/transacao")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransactions(@Valid @RequestBody TransactionsInsertRequest trasactions) {
        service.transactionsSave(trasactions);
    }
}
