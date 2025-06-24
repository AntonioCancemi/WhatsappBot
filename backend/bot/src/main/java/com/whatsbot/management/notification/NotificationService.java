package com.whatsbot.management.notification;

import com.whatsbot.management.model.BookingNotification;

public interface NotificationService {
    void notify(BookingNotification notification);
}
