package com.itau.devItau.dto;

import java.math.BigDecimal;

public class TransacoesEstatisticsResponse {
    private long count;

    private BigDecimal sum;

    private BigDecimal avg;

    private BigDecimal min;

    private BigDecimal max;

    //Getters
    public BigDecimal getAvg() {
        return avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public long getCount() {
        return count;
    }

    //Setters
    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}

