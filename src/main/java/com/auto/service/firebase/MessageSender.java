package com.auto.service.firebase;

import com.auto.service.entity.FirebaseToken;
import com.auto.service.entity.User;
import com.auto.service.repository.FirebaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@AllArgsConstructor
public class MessageSender implements Notification{

    @Override
    public void notification(String token,String title,String message) {
            NotificationTest bodyNode = new NotificationTest();
            bodyNode.setTo("/"+token);
            bodyNode.setData(new Data(title, message));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "key=AAAAClHADw0:APA91bGGz7vBseKFtAX7gfHsoQ0RguaHfOSS7cYdMIZMFe97PxEKnVup0O6jAFV8RPsaIrpbw9Wz3tIMpykIkNVmh8NyZr8KUq1mwmbCkxW6_RKXpq4uJ13C_n92h-WAmoOAMuo7gsq0");
            String url = "https://fcm.googleapis.com/fcm/send";
            RestTemplate template = new RestTemplate();
            HttpEntity<NotificationTest> entity = new HttpEntity<>(bodyNode, headers);
            template.postForLocation(url, entity);
            System.out.println("send mew message");
    }
}
