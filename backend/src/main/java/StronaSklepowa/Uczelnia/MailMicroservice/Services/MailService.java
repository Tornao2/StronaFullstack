package StronaSklepowa.Uczelnia.MailMicroservice.Services;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import StronaSklepowa.Uczelnia.UserMicroservice.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    private UserRepository userRepository;

    @KafkaListener(topics = "mail-events", groupId = "mail-group")
    public void sendMail(OrderDTO order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@cherry-kom.com");
            message.setTo(order.getCustomerEmail());
            message.setSubject("Informacja o transakcji: " + order.getId());
            message.setText(
                    "Dzień Dobry.Informujemy o nowej transakcji w sklepie cherry-kom. Koszt transakcji: " + changeGroszeToZlote(order.getTotalAmountInGrosze())
            );
            mailSender.send(message);
        } catch (Exception ez) {
            log.error("Mail error: ", ez);
        }
    }

    private float changeGroszeToZlote(long pln) {
        return (float) pln /100;
    }
}
