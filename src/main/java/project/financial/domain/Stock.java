package project.financial.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id @GeneratedValue
    @Column(name = "stock_id")
    private Long id;

    @OneToMany(mappedBy = "stock")
    private List<UserStock> userStocks = new ArrayList<>();

    //주식이름

    private String name;
    //현재가
    private Double currentPrice;
    //전일비
    private Double preContrastPrice;
    //등락률
    private Double rate;
    //액면가
    private Double faceValue;
    //시가총액
    private Double capitalization;
    //상장주식수
    private Double listedShares;
    //외국인비율
    private Double foreignerRate;
    //거래량
    private Double volume;
    //per
    private Double per;
    //roe
    private Double roe;

}
