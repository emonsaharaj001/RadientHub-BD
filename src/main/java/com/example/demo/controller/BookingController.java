package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @GetMapping
    public String getBookings() {
        return "All Bookings";
    }

    @PostMapping
    public String createBooking() {
        return "Booking Created";
    }

    @PutMapping("/{id}")
    public String updateBooking(@PathVariable Long id) {
        return "Booking Updated " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        return "Booking Deleted " + id;
    }
}