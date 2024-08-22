package hsn.commerce.inventory.repository;

import hsn.commerce.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findFirstByProductId(Integer id);

    @Query(
            nativeQuery = true,
            value = "SELECT inventories.id, inventories.quantity FROM order_service.orders orders" +
                    " JOIN inventory_service.inventories inventories on inventories.id = orders.inventory_id" +
                    " WHERE orders.inventory_id = :id")
    Optional<Inventory> findInventoryById(@Param("inventoryId") Integer id);
}
