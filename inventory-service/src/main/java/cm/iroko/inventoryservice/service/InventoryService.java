package cm.iroko.inventoryservice.service;

import cm.iroko.inventoryservice.dto.InventoryResponse;
import cm.iroko.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(String skuCode) {
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("ended");
       return inventoryRepository.findBySkuCode(skuCode).stream()
               .map(inventory ->
                   InventoryResponse.builder()
                           .skuCode(inventory.getSkuCode())
                           .isInStock(inventory.getStock() > 0)
                           .build()
               ).toList();
    }
}
