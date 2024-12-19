package com.korges.cryptocurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record CoinDTO(
        String id,
        String symbol,
        String name,
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
}