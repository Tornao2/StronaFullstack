package StronaSklepowa.Uczelnia.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import StronaSklepowa.Uczelnia.DTOs.OrderItemDTO;
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
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public String placeOrderAndGetPaymentUrl(String identifier, List<Long> itemIds) throws Exception {
        if (itemIds == null || itemIds.isEmpty()) {
            throw new RuntimeException("Koszyk nie może być pusty");
        }
        User user = userRepository.findByEmail(identifier)
            .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(user.getAddress());
        order.setShippingCity(user.getCity());
        order.setShippingZipCode(user.getZipCode());
        order.setItems(new ArrayList<>());
        int totalSum = 0;
        for (Long itemId : itemIds) {
            Product product = productRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Produkt nie istnieje"));
            OrderItem newItem = new OrderItem();
            newItem.setProduct(product);
            newItem.setQuantity(1); 
            Integer unitPrice = product.getPriceInGrosze();
            newItem.setPriceAtPurchaseInGrosze(unitPrice != null ? unitPrice : 0);
            newItem.setOrder(order);
            order.getItems().add(newItem); 
            totalSum += newItem.getPriceAtPurchaseInGrosze();
        }
        order.setTotalAmountInGrosze(totalSum);
        Order savedOrder = orderRepository.save(order);
        OrderDTO orderDto = mapOrderToDTO(savedOrder);
        kafkaProducerService.sendOrderUpdate(orderDto);
        return paymentService.createCheckoutSession(
            savedOrder.getId(), 
            savedOrder.getTotalAmountInGrosze() 
        );
    }

    public List<OrderDTO> getOrdersByUserEmail(String email) {
    return orderRepository.findByUserIdOrderByOrderDateDesc(
            userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika")).getId()
        ).stream()
        .map(this::mapOrderToDTO)
        .collect(Collectors.toList());
    }

    public OrderDTO getOrderByIdForUser(Long orderId, String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zamówienia"));
        if (!order.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Nie masz uprawnień do podglądu tego zamówienia");
        }
        return mapOrderToDTO(order);
    }

    private OrderDTO mapOrderToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmountInGrosze(order.getTotalAmountInGrosze());
        dto.setStatus(order.getStatus());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setShippingCity(order.getShippingCity());
        dto.setShippingZipCode(order.getShippingZipCode());
        dto.setItems(order.getItems().stream().map(item -> {
            OrderItemDTO itemDto = new OrderItemDTO();
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPriceAtPurchaseInGrosze(item.getPriceAtPurchaseInGrosze());
            return itemDto;
        }).collect(Collectors.toList()));
        
        return dto;
    }
}