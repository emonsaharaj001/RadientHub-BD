package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseDemoController {

    @GetMapping("/db/schema")
    public String schema() {
        return """
                RELATIONAL SCHEMA:

                users(id, name, email, password)
                services(id, service_name, category, price, duration)
                bookings(id, user_id, service_id, booking_date, status)
                payments(id, booking_id, amount, payment_method, payment_status)
                ratings(id, user_id, service_id, rating_value, review)
                chats(id, user_id, salon_id)
                messages(id, chat_id, sender, message_text)
                """;
    }

    @GetMapping("/db/join-query")
    public String joinQuery() {
        return """
                JOIN QUERY:

                SELECT b.id, u.name, s.service_name, b.booking_date, b.status
                FROM bookings b
                JOIN users u ON b.user_id = u.id
                JOIN services s ON b.service_id = s.id;
                """;
    }

    @GetMapping("/db/group-query")
    public String groupQuery() {
        return """
                GROUP BY / AGGREGATION QUERY:

                SELECT s.service_name, COUNT(b.id) AS total_bookings
                FROM bookings b
                JOIN services s ON b.service_id = s.id
                GROUP BY s.service_name;
                """;
    }

    @GetMapping("/db/subquery")
    public String subQuery() {
        return """
                SUBQUERY:

                SELECT service_name, price
                FROM services
                WHERE price > (
                    SELECT AVG(price)
                    FROM services
                );
                """;
    }

    @GetMapping("/db/advanced")
    public String advancedQuery() {
        return """
                ADVANCED DATABASE FEATURES:

                VIEW:
                CREATE VIEW booking_summary AS
                SELECT u.name, s.service_name, b.booking_date, b.status
                FROM bookings b
                JOIN users u ON b.user_id = u.id
                JOIN services s ON b.service_id = s.id;

                INDEX:
                CREATE INDEX idx_user_email ON users(email);
                """;
    }

    @GetMapping("/db/normalization")
    public String normalization() {
        return """
                NORMALIZATION:

                1NF:
                Each table has atomic values. No repeating groups are used.

                2NF:
                Every non-key attribute fully depends on the primary key.

                3NF:
                There is no transitive dependency.
                User information is stored in users table.
                Service information is stored in services table.
                Booking only stores foreign keys and booking details.
                """;
    }
}
