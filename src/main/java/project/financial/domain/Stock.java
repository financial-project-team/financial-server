package project.financial.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

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
    @Column(name = "STOCK_ID")
    private Long id;

    @OneToMany(mappedBy = "stock")
    private List<UserStock> userStocks = new ArrayList<>();

    private String name;
    private Long presentPrice;
}
