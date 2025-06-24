package com.whatsbot.booking.management.notification;

import com.whatsbot.booking.management.model.BookingNotification;

public interface NotificationService {
    void notify(BookingNotification notification);
}
