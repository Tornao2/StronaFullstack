package StronaSklepowa.Uczelnia.OrderMicroservice.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import StronaSklepowa.Uczelnia.PaymentMicroservice.Services.PaymentService;
import org.springframework.stereotype.Service;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import StronaSklepowa.Uczelnia.DTOs.OrderItemDTO;
import StronaSklepowa.Uczelnia.OrderMicroservice.Entities.Order;
import StronaSklepowa.Uczelnia.OrderMicroservice.Entities.OrderItem;
import StronaSklepowa.Uczelnia.OrderMicroservice.Entities.OrderStatus;
import StronaSklepowa.Uczelnia.ProductMicroservice.Entities.Product;
import StronaSklepowa.Uczelnia.UserMicroservice.Entities.User;
import StronaSklepowa.Uczelnia.OrderMicroservice.Repositories.OrderRepository;
import StronaSklepowa.Uczelnia.ProductMicroservice.Repositories.ProductRepository;
import StronaSklepowa.Uczelnia.UserMicroservice.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final KafkaOrderService kafkaOrderService;

    @Transactional
    public Long placeOrder(String identifier, List<Long> itemIds) throws Exception {
        if (itemIds == null || itemIds.isEmpty()) {
            throw new RuntimeException("Koszyk nie może być pusty");
        }
        User user = userRepository.findByEmail(identifier).orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(user.getAddress());
        order.setShippingCity(user.getCity());
        order.setShippingZipCode(user.getZipCode());
//        order.setShippingAddress(user.getAddress() != null ? user.getAddress() : "Brak adresu");
//        order.setShippingCity(user.getCity() != null ? user.getCity() : "Brak miasta");
//        order.setShippingZipCode(user.getZipCode() != null ? user.getZipCode() : "00-000");
        order.setItems(new ArrayList<>());
        int totalSum = 0;
        for (Long itemId : itemIds) {
            Product product = productRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Produkt nie istnieje"));
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

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                kafkaOrderService.sendOrderEvent(orderDto);
            }
        });

        return savedOrder.getId();
    }

    public List<OrderDTO> getOrdersByUserEmail(String email) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika")).getId()).stream().map(this::mapOrderToDTO).collect(Collectors.toList());
    }

    public OrderDTO getOrderByIdForUser(Long orderId, String email) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Nie znaleziono zamówienia"));
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
        dto.setPaymentUrl(order.getPaymentUrl());
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