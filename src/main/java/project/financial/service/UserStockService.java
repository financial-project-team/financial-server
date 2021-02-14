package project.financial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.financial.domain.UserStock;
import project.financial.dto.UserStockDto;
import project.financial.repository.UserStockRepository;

@Service
@Transactional()
@RequiredArgsConstructor
public class UserStockService {
    private final UserStockRepository userStockRepository;

    public UserStockDto.CreateUserStockResponse addStock(UserStock userStock) {
        userStockRepository.save(userStock);
        return new UserStockDto.CreateUserStockResponse(userStock);
    }


}
