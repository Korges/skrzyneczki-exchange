package com.korges.coin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.korges.coin.dto.CoinLiveUpdateDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
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
    private final SimpMessagingTemplate messagingTemplate;

    CoinRedisCacheService(CoinRepository coinRepository, CoinHttpService httpService, RedisTemplate<String, Object> redisTemplate, SimpMessagingTemplate messagingTemplate) {
        this.coinRepository = coinRepository;
        this.httpService = httpService;
        this.redisTemplate = redisTemplate;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        initializeCoinTable();
    }

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    void refreshAllPrices() {
        System.out.println("Refreshing token prices...");
        Object result = this.redisTemplate.opsForValue().get(COIN_LIST_KEY);
        List<String> idList = result != null ? (List<String>) result : List.of();
        try {
            List<CoinLiveUpdateDTO> priceList = fetchPriceFromAPI(idList);
            priceList.forEach(this::updatePriceInCache);
            messagingTemplate.convertAndSend("/topic/crypto-prices", priceList);
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

    void updatePriceInCache(CoinLiveUpdateDTO coinDTO) {
        this.redisTemplate.opsForHash().put(COIN_PRICE_KEY, coinDTO.id(), coinDTO.currentPrice());
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

    private List<CoinLiveUpdateDTO> fetchPriceFromAPI(List<String> idList) {
        try {
            HttpResponse<String> response = httpService.getCoinPriceByIds(idList);
            System.out.println(response.headers());
            List<Map<String, Object>> prices = objectMapper.readValue(response.body(), List.class);
            return prices.stream().map(x -> objectMapper.convertValue(x, CoinLiveUpdateDTO.class)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
