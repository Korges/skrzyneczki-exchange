package com.korges.coin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.korges.coin.dto.CoinDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
class CoinFacade {
    private final ObjectMapper objectMapper;
    private final CoinRepository coinRepository;
    private final CoinRedisCacheService coinRedisCacheService;

    CoinFacade(CoinRepository coinRepository, CoinRedisCacheService coinRedisCacheService) {
        this.coinRepository = coinRepository;
        this.coinRedisCacheService = coinRedisCacheService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    List<CoinDTO> findAllCryptocurrency() {
        Map<String, Number> priceFromCache = coinRedisCacheService.getPriceFromCache();
        return StreamSupport.stream(coinRepository.findAll().spliterator(), false)
                .map(cryptocurrency -> {
                    String id = cryptocurrency.getId();
                    Number number = priceFromCache.get(id);
                    cryptocurrency.setCurrentPrice(getCurrentPrice(number));
                    return cryptocurrency;
                })
                .map(cryptocurrency -> objectMapper.convertValue(cryptocurrency, CoinDTO.class))
                .toList();
    }

    CoinDTO findCryptocurrencyById(String id) {
        Coin coin = coinRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Number currentPrice = coinRedisCacheService.getPriceFromCacheById(coin.getId());
        coin.setCurrentPrice(BigDecimal.valueOf(currentPrice.doubleValue()));
        return objectMapper.convertValue(coin, CoinDTO.class);
    }

    private BigDecimal getCurrentPrice(Number currentPrice) {
        return BigDecimal.valueOf(currentPrice.doubleValue());
    }

}
