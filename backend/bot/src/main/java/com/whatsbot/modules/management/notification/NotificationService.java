package com.whatsbot.modules.management.notification;

import com.whatsbot.modules.management.model.BookingNotification;

public interface NotificationService {
    void notify(BookingNotification notification);
}
