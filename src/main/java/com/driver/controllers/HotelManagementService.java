package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelManagementService {

    @Autowired
    HotelManagementRepository hotelManagementRepository;
    public boolean addHotel(Hotel hotel) {
        if(hotel==null) return false;
        if(hotel.getHotelName()==null) return false;

        return hotelManagementRepository.addHotel(hotel);
    }

    public void addUser(User user) {
        hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        HashMap<String,Hotel> hotels = hotelManagementRepository.getHotels();
        TreeSet<String> set = new TreeSet<>();
        String ans = "";
        for(String s: hotels.keySet()) set.add(s);
        int max_facility = 0;
        for(String s: set) {
            Hotel h = hotels.get(s);
            int curr_facility = h.getFacilities().size();
            if(curr_facility>max_facility) {
                max_facility = curr_facility;
                ans = s;
            }
        }
        return ans;
    }

    public int bookARoom(Booking booking) {

        String hotel_name = booking.getHotelName();
        int rooms = booking.getNoOfRooms();
        if(hotelManagementRepository.getHotels().containsKey(hotel_name)==false) return -1;


        int available_rooms = hotelManagementRepository.getHotels().get(hotel_name).getAvailableRooms();

        if(available_rooms<rooms) return -1;

        UUID uuid = UUID.randomUUID();

        // Convert the UUID to a string
        String uuidString = uuid.toString();
        hotelManagementRepository.bookARoom(booking,uuidString);
        booking.setBookingId(uuidString);

        // check if rooms available


        int total_amount = hotelManagementRepository.getHotels().get(hotel_name).getPricePerNight()*rooms;
        booking.setAmountToBePaid(total_amount);
        hotelManagementRepository.getHotels().get(hotel_name).setAvailableRooms(available_rooms-rooms);

        return total_amount;

    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepository.getBookings_by_a_person().get(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        if(hotelManagementRepository.getHotels().containsKey(hotelName)==false) return null;

        List<Facility> facilities = hotelManagementRepository.getHotels().get(hotelName).getFacilities();
        Set<Facility> set = new HashSet<>();
        for(Facility facility: facilities) set.add(facility);
        for(Facility facility: newFacilities) set.add(facility);
        facilities.clear();
        for(Facility facility: set) facilities.add(facility);
        hotelManagementRepository.getHotels().get(hotelName).setFacilities(facilities);
        return hotelManagementRepository.getHotels().get(hotelName);
    }
}
