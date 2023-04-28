package edu.uoc.epcsd.notification.services;

import edu.uoc.epcsd.notification.kafka.ProductMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Component
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public void notifyProductAvailable(ProductMessage productMessage) {

        // TODO: query User service to get the users that have an alert for the specified product, then simulate the
        //  email notification for the alerted users by logging a line with INFO level

        String url = userServiceUrl + "/alert/" + productMessage.getBrand() + "/" + productMessage.getModel();
        List<String> userEmails = restTemplate.getForObject(url, List.class);

        if (userEmails != null) {
            for (String email : userEmails) {
                log.info("Sending email notification to: " + email + " for product: " + productMessage.getBrand() + " " + productMessage.getModel());
            }
        }
    }

}
