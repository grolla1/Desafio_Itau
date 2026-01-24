package com.itau.devItau.controller;

import com.itau.devItau.dto.TransactionsEstatisticsResponse;
import com.itau.devItau.dto.TransactionsInsertRequest;
import com.itau.devItau.service.TransactionsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionsController {
    private final TransactionsService service;

    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    public TransactionsController(TransactionsService service) {
        this.service = service;
    }

    @GetMapping("/estatistica")
    public TransactionsEstatisticsResponse getEstatistics(@RequestParam long seconds) {
        logger.info("======= CONTROLLER: GET /estatistica =======");
        return service.getTransactionsStatistics(seconds);
    }

    @PostMapping("/transacao")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransactions(@Valid @RequestBody TransactionsInsertRequest trasactions) {
        logger.info("======= CONTROLLER: POST /transacao =======");
        service.transactionsSave(trasactions);
    }

    @DeleteMapping("/transacao")
    public void deleteTransactions() {
        logger.info("======= CONTROLLER: DELETE /transacao =======");
        service.deleteAll();
    }
}
