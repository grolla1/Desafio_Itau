package com.itau.devItau.controller;

import com.itau.devItau.dto.TransacoesEstatisticsResponse;
import com.itau.devItau.service.TransacoesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransacoesController {
    private final TransacoesService service;

    public TransacoesController(TransacoesService service) {
        this.service = service;
    }

    @GetMapping("/estatistica")
    public TransacoesEstatisticsResponse getEstatistics(@RequestParam long seconds) {
        return service.getTransactionsStatistics(seconds);
    }
}
