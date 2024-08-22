package hsn.commerce.inventory.service;

import hsn.commerce.inventory.entity.Inventory;
import hsn.commerce.inventory.model.AddInventoryRequest;
import hsn.commerce.inventory.model.InventoryResponse;
import hsn.commerce.inventory.model.UpdateInventoryRequest;
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
        inventory.setName(request.getName());
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

        inventory.setName(request.getName());
        inventory.setPrice(request.getPrice());
        inventory.setQuantity(request.getQuantity());
        inventoryRepository.save(inventory);

        return toInventoryResponse(inventory);
    }

    private InventoryResponse toInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .name(inventory.getName())
                .price(inventory.getPrice())
                .quantity(inventory.getQuantity())
                .build();
    }
}
