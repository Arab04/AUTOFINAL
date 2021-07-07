package com.auto.service.sms;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Initializer.class);
    private final SetProperties setProperties;

    @Autowired
    public Initializer(SetProperties setProperties) {
        this.setProperties = setProperties;
        Twilio.init(setProperties.getAccountSid(),setProperties.getAuthToken());
        LOGGER.info("Twillio started initializing");
    }

}
