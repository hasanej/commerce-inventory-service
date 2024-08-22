package hsn.commerce.inventory.controller;

import hsn.commerce.inventory.model.AddInventoryRequest;
import hsn.commerce.inventory.model.InventoryResponse;
import hsn.commerce.inventory.model.UpdateInventoryRequest;
import hsn.commerce.inventory.model.WebResponse;
import hsn.commerce.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @PostMapping(
            path = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<InventoryResponse> add(@RequestBody AddInventoryRequest request) {
        InventoryResponse inventoryResponse = inventoryService.add(request);
        return WebResponse.<InventoryResponse>builder().data(inventoryResponse).build();
    }

    @PutMapping(
            path = "/update/{inventoryId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<InventoryResponse> update(@RequestBody UpdateInventoryRequest request,
                                                 @PathVariable("inventoryId") Integer inventoryId
    ) {
        request.setId(inventoryId);

        InventoryResponse inventoryResponse = inventoryService.update(request, inventoryId);
        return WebResponse.<InventoryResponse>builder().data(inventoryResponse).build();
    }
}
