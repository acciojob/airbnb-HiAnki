package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class HotelManagementRepository {

    private HashMap<String,Hotel> hotels;
    private HashMap<Integer,User> users;
    private HashMap<String,Booking> bookings;
    private HashMap<Integer,Integer> bookings_by_a_person;

    public HotelManagementRepository() {
        hotels = new HashMap<>();
        users = new HashMap<>();
        bookings = new HashMap<>();
        bookings_by_a_person = new HashMap<>();
    }

    public HotelManagementRepository(HashMap<String, Hotel> hotels, HashMap<Integer, User> users, HashMap<String, Booking> bookings, HashMap<Integer, Integer> booking_by_a_person) {
        this.hotels = hotels;
        this.users = users;
        this.bookings = bookings;
        this.bookings_by_a_person = booking_by_a_person;
    }

    public HashMap<Integer, Integer> getBookings_by_a_person() {
        return bookings_by_a_person;
    }

    public void setBookings_by_a_person(HashMap<Integer, Integer> bookings_by_a_person) {
        this.bookings_by_a_person = bookings_by_a_person;
    }

    public HashMap<String, Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(HashMap<String, Hotel> hotels) {
        this.hotels = hotels;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<Integer, User> users) {
        this.users = users;
    }

    public HashMap<String, Booking> getBookings() {
        return bookings;
    }

    public void setBookings(HashMap<String, Booking> bookings) {
        this.bookings = bookings;
    }

    public boolean addHotel(Hotel hotel) {
        if(hotels.containsKey(hotel.getHotelName())) return false;

        hotels.put(hotel.getHotelName(),hotel);
        return true;
    }

    public void addUser(User user) {
        users.put(user.getaadharCardNo(),user);
    }

    public void bookARoom(Booking booking, String uuidString) {
        bookings.put(uuidString,booking);
        bookings_by_a_person.put(booking.getBookingAadharCard(),bookings_by_a_person.getOrDefault(booking.getBookingAadharCard(),0)+1);

    }
}
