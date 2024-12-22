package com.korges.coin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Service
class CoinRedisCacheService {
    private static final String COIN_LIST_KEY = "coin:list";
    private static final String COIN_PRICE_KEY = "coin:price";
    private final CoinRepository coinRepository;
    private final CoinHttpService httpService;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    CoinRedisCacheService(CoinRepository coinRepository, CoinHttpService httpService, RedisTemplate<String, Object> redisTemplate) {
        this.coinRepository = coinRepository;
        this.httpService = httpService;
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        initializeCoinTable();
    }

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    void refreshAllPrices() {
        Object result = this.redisTemplate.opsForValue().get(COIN_LIST_KEY);
        List<String> idList = result != null ? (List<String>) result : List.of();
        try {
            List<AbstractMap.SimpleEntry<Object, Object>> priceList = fetchPriceFromAPI(idList);
            priceList.forEach(this::updatePriceInCache);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Map<String, Number> getPriceFromCache() {
        Object result = this.redisTemplate.opsForHash().entries(COIN_PRICE_KEY);
        return result != null ? (Map<String, Number>) result : Map.of();
    }

    Number getPriceFromCacheById(String id) {
        return (Number) this.redisTemplate.opsForHash().get(COIN_PRICE_KEY, id);
    }

    void updatePriceInCache(AbstractMap.SimpleEntry<Object, Object> simpleEntry) {
        this.redisTemplate.opsForHash().put(COIN_PRICE_KEY, simpleEntry.getKey(), simpleEntry.getValue());
    }

    private void initializeCoinTable() {
        try {
            HttpResponse<String> response = httpService.getCoinsWithMarketData();
            List<Map<String, Object>> prices = objectMapper.readValue(response.body(), List.class);
            List<Coin> list = prices.stream().map(x -> objectMapper.convertValue(x, Coin.class)).toList();
            this.redisTemplate.opsForValue().set(COIN_LIST_KEY, list.stream().map(Coin::getId).toList());
            this.coinRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private List<AbstractMap.SimpleEntry<Object, Object>> fetchPriceFromAPI(List<String> idList) {
        try {
            HttpResponse<String> response = httpService.getCoinPriceByIds(idList);
            List<Map<String, Object>> prices = objectMapper.readValue(response.body(), List.class);
            return prices.stream().map(x -> new AbstractMap.SimpleEntry<>(x.get("id"), x.get("current_price"))).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
