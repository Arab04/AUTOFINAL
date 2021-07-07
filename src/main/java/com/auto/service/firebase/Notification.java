package com.auto.service.firebase;

import com.auto.service.entity.User;

public interface Notification {

    void notification(String token,String title, String message);
}
