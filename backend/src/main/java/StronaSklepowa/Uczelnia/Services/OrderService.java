package StronaSklepowa.Uczelnia.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import StronaSklepowa.Uczelnia.Entities.Order;
import StronaSklepowa.Uczelnia.Entities.OrderItem;
import StronaSklepowa.Uczelnia.Entities.OrderStatus;
import StronaSklepowa.Uczelnia.Entities.Product;
import StronaSklepowa.Uczelnia.Entities.User;
import StronaSklepowa.Uczelnia.Repositories.OrderRepository;
import StronaSklepowa.Uczelnia.Repositories.ProductRepository;
import StronaSklepowa.Uczelnia.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService; 

    @Transactional
    public String placeOrderAndGetPaymentUrl(Long userId, List<OrderItem> items) throws Exception {
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Koszyk nie może być pusty");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
        
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setItems(new ArrayList<>());
            
        int totalSum = 0;
        for (OrderItem itemRequest : items) {
            Product product = productRepository.findById(itemRequest.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Produkt nie istnieje"));
            int currentStock = (product.getStockQuantity() != null) ? product.getStockQuantity() : 0;
            if (currentStock < itemRequest.getQuantity()) {
                throw new RuntimeException("Brak towaru: " + product.getName());
            }
            product.setStockQuantity(currentStock - itemRequest.getQuantity());
            productRepository.save(product); 
            OrderItem newItem = new OrderItem();
            newItem.setProduct(product);
            newItem.setQuantity(itemRequest.getQuantity());
            Integer unitPrice = product.getPriceInGrosze();
            if (unitPrice == null) unitPrice = 0;
            newItem.setPriceAtPurchaseInGrosze(unitPrice);
            newItem.setOrder(order);
            order.getItems().add(newItem); 
            totalSum += unitPrice * newItem.getQuantity();
        }
        order.setTotalAmountInGrosze(totalSum);
        Order savedOrder = orderRepository.save(order);
        return paymentService.createCheckoutSession(
            savedOrder.getId(), 
            (double) savedOrder.getTotalAmountInGrosze() / 100 
        );
    }
}