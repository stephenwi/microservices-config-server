package cm.iroko.inventoryservice.controller;

import cm.iroko.inventoryservice.dto.InventoryResponse;
import cm.iroko.inventoryservice.model.Inventory;
import cm.iroko.inventoryservice.repository.InventoryRepository;
import cm.iroko.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ss/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository inventoryRepository;
    private final InventoryService inventoryService;

    //@GetMapping("/{skuCode}")

    @GetMapping("/getSkuCode")
    @ResponseStatus(HttpStatus.OK)
    List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
    /*       Inventory inventory =  inventoryRepository.findBySkuCode(skuCode)
                 .orElseThrow(() -> new RuntimeException("Cannot find product by sku code " + skuCode));
                 return inventory.getStock() > 0;
    */

        return inventoryService.isInStock(skuCode);
    }
}
