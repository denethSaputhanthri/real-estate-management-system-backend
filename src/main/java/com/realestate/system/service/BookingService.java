package com.realestate.system.service;

import com.realestate.system.model.dto.request.CreateBookingRequest;
import com.realestate.system.model.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);

    BookingResponse getBookingById(Long id);

    List<BookingResponse> getAllBookings();

    BookingResponse updateBooking(Long id, CreateBookingRequest request);

    void deleteBooking(Long id);
}
