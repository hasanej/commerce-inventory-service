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

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;
}
