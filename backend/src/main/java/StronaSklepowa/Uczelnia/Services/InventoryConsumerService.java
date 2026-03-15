package StronaSklepowa.Uczelnia.Services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import StronaSklepowa.Uczelnia.DTOs.OrderItemDTO;
import StronaSklepowa.Uczelnia.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryConsumerService {
    private final ProductRepository productRepository;
    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    @Transactional
    public void processInventory(OrderDTO orderDto) {
        for (OrderItemDTO item : orderDto.getItems()) {
            productRepository.findById(item.getProductId()).ifPresent(product -> {
                int newStock = product.getStockQuantity() - item.getQuantity();
                product.setStockQuantity(Math.max(0, newStock));
                productRepository.save(product);
                System.out.println("Zaktualizowano: " + product.getName() + " pozostało: " + product.getStockQuantity());
            });
        }
    }
}