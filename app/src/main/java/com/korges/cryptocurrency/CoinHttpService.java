package com.korges.cryptocurrency;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
class CoinHttpService {
    private static final String API_KEY = "x-cg-demo-api-key";
    private static final String API_COINGECKO_V3 = "https://api.coingecko.com/api/v3";
    private static final String COINS_MARKETS = "/coins/markets";
    private static final String VS_CURRENCY = "vs_currency=";
    private static final String PER_PAGE = "per_page=";
    private static final String IDS = "ids=";
    private final CoinConfiguration configuration;
    private final HttpClient httpClient;

    public CoinHttpService(CoinConfiguration configuration) {
        this.configuration = configuration;
        this.httpClient = HttpClient.newHttpClient();
    }

    HttpResponse<String> getCoinsWithMarketData() {
        String apiUrl = API_COINGECKO_V3 + COINS_MARKETS +
                "?" +
                VS_CURRENCY + configuration.getVsCurrency() +
                "&" +
                PER_PAGE + configuration.getPerPage();
        return getHttpRequest(apiUrl);
    }

    HttpResponse<String> getCoinPriceByIds(List<String> idList) {
        String ids = idList.stream().reduce((a, b) -> a + "%2C" + b).orElse("bitcoin");
        String apiUrl = API_COINGECKO_V3 + COINS_MARKETS +
                "?" +
                VS_CURRENCY + configuration.getVsCurrency() +
                "&" +
                IDS + ids;
        return getHttpRequest(apiUrl);
    }

    private HttpResponse<String> getHttpRequest(String apiUrl) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header(API_KEY, configuration.getApiKey())
                    .build();

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
