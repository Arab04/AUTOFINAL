package com.auto.service.sms;

import com.auto.service.payloads.SmsRequest;

public interface UniqueCenter {
    void smsSender(String number, String message);
}
