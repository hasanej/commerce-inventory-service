package hsn.commerce.inventory.service;

import hsn.commerce.inventory.entity.Inventory;
import hsn.commerce.inventory.model.*;
import hsn.commerce.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ValidationService validationService;

    @Transactional
    public InventoryResponse add(AddInventoryRequest request) {
        validationService.validate(request);

        Inventory inventory = new Inventory();
        inventory.setProductId(request.getProductId());
        inventory.setProductName(request.getProductName());
        inventory.setPrice(request.getPrice());
        inventory.setQuantity(request.getQuantity());
        inventoryRepository.save(inventory);

        return toInventoryResponse(inventory);
    }

    @Transactional
    public InventoryResponse update(UpdateInventoryRequest request, Integer inventoryId) {
        validationService.validate(request);

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found."));

        inventory.setProductId(request.getProductId());
        inventory.setProductName(request.getProductName());
        inventory.setPrice(request.getPrice());
        inventory.setQuantity(request.getQuantity());
        inventoryRepository.save(inventory);

        return toInventoryResponse(inventory);
    }

    @Transactional
    public InventoryResponse restock(RestockRequest request, Integer productId) {
        validationService.validate(request);

        Inventory inventory = inventoryRepository.findFirstByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found."));

        Integer currentInventory = inventory.getQuantity();

        inventory.setQuantity(currentInventory + request.getQuantity()); // Add inputted quantity to existing quantity
        inventoryRepository.save(inventory);

        return toInventoryResponse(inventory);
    }

    @Transactional
    public InventoryResponse findInventoryById(Integer inventoryId, PurchaseRequest request) {
        validationService.validate(request);

        Inventory inventory = inventoryRepository.findInventoryById(inventoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found."));

        Integer currentInventory = inventory.getQuantity();

        inventory.setQuantity(currentInventory - request.getQuantity()); // Add inputted quantity to existing quantity
        inventoryRepository.save(inventory);

        return toInventoryResponse(inventory);
    }

    private InventoryResponse toInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .productName(inventory.getProductName())
                .price(inventory.getPrice())
                .quantity(inventory.getQuantity())
                .build();
    }
}
