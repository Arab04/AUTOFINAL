package com.auto.service.sms;

import com.auto.service.payloads.SmsRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("firstService")
@AllArgsConstructor
public class SmsSender implements UniqueCenter{

    private final SetProperties setProperties;

    @Override
    public void smsSender(String number,String code) {
        PhoneNumber to = new PhoneNumber(number);
        PhoneNumber from = new PhoneNumber(setProperties.getTrailNumber());
        String message = code;

        MessageCreator creator = Message.creator(to,from,message);
        creator.create();
    }

}
