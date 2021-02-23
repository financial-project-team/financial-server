package project.financial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.financial.domain.Stock;
import project.financial.domain.User;
import project.financial.domain.UserStock;
import project.financial.dto.UserStockDto;
import project.financial.security.JwtTokenProvider;
import project.financial.service.StockService;
import project.financial.service.UserService;
import project.financial.service.UserStockService;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stock")
public class StockController {

    private final UserService userService;
    private final StockService stockService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserStockService userStockService;

    //주식 추가
    @PostMapping(value = "/add")
    public UserStockDto.CreateUserStockResponse addStock(@RequestHeader("authToken") String token,
                                                         @RequestBody UserStockDto.CreateUserStockRequest request){
        User user = userService.findById(jwtTokenProvider.getUserId(token));
        Stock stock = stockService.findByName(request.getName());
        UserStock userStock = UserStock.builder()
                .user(user)
                .stock(stock)
                .gainOrLoss((stock.getCurrentPrice() - request.getRating()) * request.getQuantity())
                .evalAmount((request.getRating() * request.getQuantity()) + ((stock.getCurrentPrice() - request.getRating()) * request.getQuantity()))
                .quantity(request.getQuantity())
                .rating(request.getRating())
                .yield((stock.getCurrentPrice() - request.getRating()) / request.getRating() * 100)
                .perchaseAmount(request.getQuantity() * request.getRating())
                .build();
        return userStockService.addStock(userStock);
    }

}
