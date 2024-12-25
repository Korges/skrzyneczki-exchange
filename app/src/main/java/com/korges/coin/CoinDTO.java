package com.korges.coin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record CoinDTO(
        String id,
        String symbol,
        String name,
        String image,
        BigDecimal currentPrice,
        BigDecimal marketCap,
        Integer marketCapRank,
        BigDecimal circulatingSupply,
        BigDecimal totalSupply,
        BigDecimal maxSupply,
        BigDecimal ath,
        LocalDateTime athDate,
        LocalDateTime lastUpdated
) {
    public CoinDTO {
    }

    public CoinDTO(String id, BigDecimal currentPrice) {
        this(id, null, null, null, currentPrice, null, null, null, null, null, null, null, null);
    }
}