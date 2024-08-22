package hsn.commerce.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hsn.commerce.inventory.entity.Inventory;
import hsn.commerce.inventory.model.*;
import hsn.commerce.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addInventorySuccess() throws Exception {
        // Create request
        AddInventoryRequest request = new AddInventoryRequest();
        request.setProductId(100);
        request.setProductName("Kipas Angin Bladeless");
        request.setPrice(1200000);
        request.setQuantity(250);

        mockMvc.perform(
                post("/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<InventoryResponse> response = objectMapper.readValue(result.getResponse()
                    .getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());

            assertEquals(request.getProductId(), response.getData().getProductId());
            assertEquals(request.getProductName(), response.getData().getProductName());
            assertEquals(request.getPrice(), response.getData().getPrice());
            assertEquals(request.getQuantity(), response.getData().getQuantity());

            Inventory userDb = inventoryRepository.findById(1).orElse(null);
            assertNotNull(userDb);
        });
    }

    @Test
    void addInventoryBadRequest() throws Exception {
        // Create request with blank product name
        AddInventoryRequest request = new AddInventoryRequest();
        request.setProductId(100);
        request.setProductName("");
        request.setPrice(1200000);
        request.setQuantity(250);

        mockMvc.perform(
                post("/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse()
                    .getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateInventorySuccess() throws Exception {
        // Insert data
        Inventory inventory = new Inventory();
        inventory.setProductId(100);
        inventory.setProductName("Kipas Angin Bladeless");
        inventory.setPrice(1200000);
        inventory.setQuantity(250);
        inventoryRepository.save(inventory);

        // Create request
        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(100);
        request.setProductName("Kipas Angin Bladeless - New");
        request.setPrice(1250000);
        request.setQuantity(300);

        mockMvc.perform(
                put("/update/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<InventoryResponse> response = objectMapper.readValue(result.getResponse()
                    .getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());

            assertEquals(request.getProductId(), response.getData().getProductId());
            assertEquals(request.getProductName(), response.getData().getProductName());
            assertEquals(request.getPrice(), response.getData().getPrice());
            assertEquals(request.getQuantity(), response.getData().getQuantity());

            Inventory userDb = inventoryRepository.findById(1).orElse(null);
            assertNotNull(userDb);
        });
    }

    @Test
    void updateInventoryNotFound() throws Exception {
        // Create request
        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(100);
        request.setProductName("Kipas Angin Bladeless");
        request.setPrice(1200000);
        request.setQuantity(250);

        // Using 999 value as non-existent inventory ID
        mockMvc.perform(
                put("/update/999")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateInventoryBadRequest() throws Exception {
        // Create request with blank product name
        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setProductId(100);
        request.setProductName("");
        request.setPrice(1200000);
        request.setQuantity(250);

        mockMvc.perform(
                put("/update/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void restockInventorySuccess() throws Exception {
        // Insert data
        Inventory inventory = new Inventory();
        inventory.setProductId(100);
        inventory.setProductName("Kipas Angin Bladeless");
        inventory.setPrice(1200000);
        inventory.setQuantity(250);
        inventoryRepository.save(inventory);

        // Create request
        RestockRequest request = new RestockRequest();
        request.setProductId(100);
        request.setQuantity(10); // This value should be added to the previous quantity

        mockMvc.perform(
                patch("/restock/" + request.getProductId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<InventoryResponse> response = objectMapper.readValue(result.getResponse()
                    .getContentAsString(), new TypeReference<>() {
            });

            Inventory userDb = inventoryRepository.findById(1).orElse(null);

            assertNull(response.getErrors());

            assertEquals(userDb.getId(), response.getData().getId());
            assertEquals(request.getProductId(), response.getData().getProductId());
            assertEquals(userDb.getProductName(), response.getData().getProductName());
            assertEquals(userDb.getPrice(), response.getData().getPrice());
            assertEquals(260, response.getData().getQuantity()); // 250 + 10 = 160
        });
    }

    @Test
    void restockInventoryNotFound() throws Exception {
        // Create request
        RestockRequest request = new RestockRequest();
        request.setProductId(100);
        request.setQuantity(10);

        // Using 999 value as non-existent inventory ID
        mockMvc.perform(
                patch("/restock/999")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void restockInventoryBadRequest() throws Exception {
        // Create request without quantity, which means quantity = null
        RestockRequest request = new RestockRequest();
        request.setProductId(100);

        mockMvc.perform(
                patch("/restock/" + request.getProductId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }
}
