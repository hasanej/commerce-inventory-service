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
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;
}
