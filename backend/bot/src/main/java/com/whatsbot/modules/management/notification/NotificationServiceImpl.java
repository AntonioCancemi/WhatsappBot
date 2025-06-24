package com.whatsbot.modules.management.notification;

import com.whatsbot.modules.management.model.BookingNotification;
import com.whatsbot.modules.management.repository.BookingNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final BookingNotificationRepository repository;

    @Override
    public void notify(BookingNotification notification) {
        repository.save(notification);
        log.info("New notification for tenant {}: {}", notification.getTenant().getId(), notification.getMessage());
    }
}
