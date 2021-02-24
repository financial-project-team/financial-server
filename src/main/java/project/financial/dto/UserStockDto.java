package project.financial.dto;


import lombok.Data;
import lombok.Getter;
import project.financial.domain.UserStock;

import java.time.LocalDateTime;

public class UserStockDto {

    @Getter
    public static class CreateUserStockRequest{
        private String name;
        //평단가
        private Double rating;
        //수량
        private Double quantity;
        private LocalDateTime buyDate;
    }

    @Getter
    public static class CreateUserStockResponse{
        //주식이름
        private String name;
        //손익금액
        private Double gainOrLoss;
        //손익퍼센트
        private Double yield;
        //현재가
        private Double currentPrice;
        //보유수량
        private Double quantity;
        //평가금액
        private Double evalAmount;
        //매수금액
        private Double perchaseAmount;
        //평단가
        private Double rating;
        //등락률
        private Double rate;

        public CreateUserStockResponse(UserStock userStock){
            this.name = userStock.getStock().getName();
            this.gainOrLoss = userStock.getGainOrLoss();
            this.yield = userStock.getYield();
            this.currentPrice = userStock.getStock().getCurrentPrice();
            this.quantity = userStock.getQuantity();
            this.evalAmount = userStock.getEvalAmount();
            this.perchaseAmount = userStock.getPerchaseAmount();
            this.rating = userStock.getRating();
            this.rate = userStock.getStock().getRate();
        }
    }
    @Data
    public static class StockRequest{
        private String name;
        private Double quantity;
        private Double rating;
    }

}
