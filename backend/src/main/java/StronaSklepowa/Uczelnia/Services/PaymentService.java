package StronaSklepowa.Uczelnia.Services;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class PaymentService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public String createCheckoutSession(Long orderId, Double amount) throws Exception {
        long amountInZlote = (long) (amount * 100);
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment-success?orderId=" + orderId)
                .setCancelUrl("http://localhost:5173/payment-failed")
                .putMetadata("orderId", orderId.toString())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("pln")
                                                .setUnitAmount(amountInZlote)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Zamówienie nr #" + orderId)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
        Session session = Session.create(params);
        return session.getUrl(); 
    }
}