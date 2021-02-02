package project.financial.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserStock {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    //평단가
    private Double rating;
    //손익퍼센트
    private Double yield;
    //손익금액
    private Double gainOrLoss;
    //매수날짜
    private LocalDateTime buyDate;
    //매도날짜
    private LocalDateTime sellDate;
    //수량
    private Long quantity;
    //평가금액
    private Double evalAmount;
    //상태
    private BuySellStatus status;
    //매수금액
    private Long perchaseAmount;
}
