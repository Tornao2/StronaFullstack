package StronaSklepowa.Uczelnia.Services;

import StronaSklepowa.Uczelnia.Entities.Order;
import StronaSklepowa.Uczelnia.Entities.OrderItem;
import StronaSklepowa.Uczelnia.Entities.Product;
import StronaSklepowa.Uczelnia.Repositories.OrderItemRepository;
import StronaSklepowa.Uczelnia.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderItem createOrderItem(Order order, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt o ID " + productId + " nie istnieje"));
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Niewystarczająca ilość produktu: " + product.getName());
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity); 
        item.setPriceAtPurchaseInGrosze(product.getPriceInGrosze());
        return orderItemRepository.save(item);
    }
}