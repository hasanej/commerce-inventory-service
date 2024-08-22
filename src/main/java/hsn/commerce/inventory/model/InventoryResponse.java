package hsn.commerce.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private Integer id;

    private Integer productId;

    private String productName;

    private Integer price;

    private Integer quantity;
}
