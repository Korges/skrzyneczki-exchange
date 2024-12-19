package com.korges.cryptocurrency;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
class CoinConfiguration {
    private String apiKey;
    private String perPage;
    private String vsCurrency;

    String getApiKey() {
        return apiKey;
    }

    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    String getPerPage() {
        return perPage;
    }

    void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    String getVsCurrency() {
        return vsCurrency;
    }

    void setVsCurrency(String vsCurrency) {
        this.vsCurrency = vsCurrency;
    }
}

