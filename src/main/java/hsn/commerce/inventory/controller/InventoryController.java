package hsn.commerce.inventory.controller;

import hsn.commerce.inventory.model.AddInventoryRequest;
import hsn.commerce.inventory.model.InventoryResponse;
import hsn.commerce.inventory.model.WebResponse;
import hsn.commerce.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @PostMapping(
            path = "/api/addInventory",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<InventoryResponse> add(@RequestBody AddInventoryRequest request) {
        InventoryResponse inventoryResponse = inventoryService.add(request);
        return WebResponse.<InventoryResponse>builder().data(inventoryResponse).build();
    }
}
