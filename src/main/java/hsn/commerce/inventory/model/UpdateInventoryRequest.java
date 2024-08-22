package hsn.commerce.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateInventoryRequest {
    @NotNull
    @JsonIgnore
    private Integer id;

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
