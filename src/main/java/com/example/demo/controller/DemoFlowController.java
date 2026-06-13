package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoFlowController {

    static class BookingRequest {
        public int id;
        public String name;
        public String email;
        public String service;
        public String date;
        public String status;

        public BookingRequest(int id, String name, String email, String service, String date, String status) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.service = service;
            this.date = date;
            this.status = status;
        }
    }

    private static final List<BookingRequest> bookings = new ArrayList<>();
    private static int nextId = 1;

    @PostMapping("/submit-booking")
    public String submitBooking(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String service,
            @RequestParam String date
    ) {
        BookingRequest booking = new BookingRequest(nextId++, name, email, service, date, "Pending");
        bookings.add(booking);

        return """
                <html>
                <body style='font-family:Arial;text-align:center;padding:60px;background:#111;color:white;'>
                    <h1 style='color:#ff7eb3;'>Booking Request Submitted!</h1>
                    <h3>Your booking is now pending for admin approval.</h3>
                    <p><b>Name:</b> %s</p>
                    <p><b>Email:</b> %s</p>
                    <p><b>Service:</b> %s</p>
                    <p><b>Date:</b> %s</p>
                    <p><b>Status:</b> Pending</p>
                    <br>
                    <a href='/index.html' style='color:white;background:#ff4081;padding:12px 25px;border-radius:20px;text-decoration:none;'>Back to User Page</a>
                    <a href='/admin.html' style='color:white;background:#6a0572;padding:12px 25px;border-radius:20px;text-decoration:none;'>Go to Admin</a>
                </body>
                </html>
                """.formatted(name, email, service, date);
    }

    @GetMapping("/admin-bookings")
    public String adminBookings() {
        StringBuilder html = new StringBuilder();

        html.append("""
                <html>
                <head>
                    <title>Admin Bookings</title>
                    <style>
                        body{font-family:Arial;background:linear-gradient(135deg,#111,#6a0572);color:white;padding:40px;}
                        h1{text-align:center;color:#ff7eb3;}
                        table{width:100%;border-collapse:collapse;background:rgba(255,255,255,.12);margin-top:30px;}
                        th,td{border:1px solid rgba(255,255,255,.3);padding:14px;text-align:center;}
                        a{background:#ff4081;color:white;padding:8px 18px;border-radius:20px;text-decoration:none;}
                        .approved{color:#7CFC00;font-weight:bold;}
                        .pending{color:#FFD700;font-weight:bold;}
                    </style>
                </head>
                <body>
                <h1>Admin Booking Requests</h1>
                <table>
                <tr>
                    <th>ID</th>
                    <th>Customer</th>
                    <th>Email</th>
                    <th>Service</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                """);

        if (bookings.isEmpty()) {
            html.append("""
                    <tr>
                        <td colspan='7'>No booking request found.</td>
                    </tr>
                    """);
        } else {
            for (BookingRequest b : bookings) {
                html.append("""
                        <tr>
                            <td>%d</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td class='%s'>%s</td>
                            <td>%s</td>
                        </tr>
                        """.formatted(
                        b.id,
                        b.name,
                        b.email,
                        b.service,
                        b.date,
                        b.status.equals("Approved") ? "approved" : "pending",
                        b.status,
                        b.status.equals("Pending")
                                ? "<a href='/approve-booking/" + b.id + "'>Approve</a>"
                                : "Approved"
                ));
            }
        }

        html.append("""
                </table>
                <br><br>
                <center>
                    <a href='/admin.html'>Back to Admin Dashboard</a>
                    <a href='/index.html'>User Page</a>
                </center>
                </body>
                </html>
                """);

        return html.toString();
    }

    @GetMapping("/approve-booking/{id}")
    public String approveBooking(@PathVariable int id) {
        for (BookingRequest b : bookings) {
            if (b.id == id) {
                b.status = "Approved";
                break;
            }
        }

        return """
                <html>
                <body style='font-family:Arial;text-align:center;padding:60px;background:#111;color:white;'>
                    <h1 style='color:#7CFC00;'>Booking Approved!</h1>
                    <p>The booking request has been approved by admin.</p>
                    <a href='/admin-bookings' style='color:white;background:#ff4081;padding:12px 25px;border-radius:20px;text-decoration:none;'>Back to Booking List</a>
                </body>
                </html>
                """;
    }

    @GetMapping("/admin-summary")
    public String adminSummary() {
        return """
                ADMIN DASHBOARD SUMMARY

                Total Users: 3
                Total Services: 3
                Total Booking Requests: %d

                Current booking requests can be viewed from:
                /admin-bookings
                """.formatted(bookings.size());
    }
}
