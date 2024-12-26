package com.korges.coin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CoinLiveUpdateDTO(
        String id,
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