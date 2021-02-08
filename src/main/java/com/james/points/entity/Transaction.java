package com.james.points.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Transaction.Builder.class)

public class Transaction {
    @JsonProperty("payerName")
    private String payerName;
    @JsonProperty("points")
    private Integer points;
    private Date transactionDate;

    private Transaction(Builder build) {
        this.payerName = build.payerName;
        this.points =  build.points;
        this.transactionDate = build.transactionDate;
    }
    public String getPayerName() {
        return payerName;
    }
    public Integer getPoints() {
        return points;
    }
    public Date getTransactionDate() {
        return transactionDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(builder = Transaction.Builder.class)
    public static class Builder {
        @JsonProperty("payerName")
        String payerName;
        @JsonProperty("points")
        Integer points;
        @JsonProperty("date")
        private Date transactionDate;

        public Builder payerName(String payerName) {
            this.payerName = payerName;
            return this;
        }

        public Builder points(Integer points) {
            this.points = points;
            return this;
        }

        public Builder transactionDate(Date transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
