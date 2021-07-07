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
            headers.set("Authorization", "key=AAAAClHADw0:APA91bFiYkpRhbXpoYlZsxHwDlKcPEFG3CI1w7BSkCZ9i-UQKFgmrguTcJtF6H9OwAmLHqrKygvEXVz4-p1A5eZtzBtHoVwRZPRc30fKJuUtp6pp0NJBDJ-HWEqhwViXjCcZ1POj-tk5");
            String url = "https://fcm.googleapis.com/fcm/send";
            RestTemplate template = new RestTemplate();
            HttpEntity<NotificationTest> entity = new HttpEntity<>(bodyNode, headers);
            template.postForLocation(url, entity);
            System.out.println("send mew message");
    }
}
