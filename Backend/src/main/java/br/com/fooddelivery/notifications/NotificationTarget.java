package br.com.fooddelivery.notifications;

/** Internal navigation targets. Never use arbitrary URLs from notification payloads. */
public enum NotificationTarget {
    ORDER, RESERVATION, DELIVERY, SUPPORT, STORE, FINANCE, ADMIN_REQUESTS
}
