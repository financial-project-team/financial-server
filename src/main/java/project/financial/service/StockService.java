package project.financial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.financial.domain.Stock;
import project.financial.repository.StockRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockRepository stockRepository;

    public Stock findByName(String name){
        return stockRepository.findByName(name);
    }
}
