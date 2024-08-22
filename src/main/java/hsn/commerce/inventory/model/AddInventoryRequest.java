package hsn.commerce.inventory.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddInventoryRequest {
    @NotNull
    private Integer productId;

    @NotBlank
    @Size(max = 200)
    private String productName;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;
}
