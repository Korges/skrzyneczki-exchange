package com.korges.coin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Coin {

    @Id
    private String id;
    private String symbol;
    private String name;
    @Transient
    private BigDecimal currentPrice;
    private BigDecimal marketCap;
    private Integer marketCapRank;
    private BigDecimal circulatingSupply;
    private BigDecimal totalSupply;
    private BigDecimal maxSupply;
    private BigDecimal ath;
    private LocalDateTime athDate;
    private LocalDateTime lastUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public Integer getMarketCapRank() {
        return marketCapRank;
    }

    public void setMarketCapRank(Integer marketCapRank) {
        this.marketCapRank = marketCapRank;
    }

    public BigDecimal getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(BigDecimal circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public BigDecimal getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(BigDecimal totalSupply) {
        this.totalSupply = totalSupply;
    }

    public BigDecimal getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(BigDecimal maxSupply) {
        this.maxSupply = maxSupply;
    }

    public BigDecimal getAth() {
        return ath;
    }

    public void setAth(BigDecimal ath) {
        this.ath = ath;
    }

    public LocalDateTime getAthDate() {
        return athDate;
    }

    public void setAthDate(LocalDateTime athDate) {
        this.athDate = athDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
