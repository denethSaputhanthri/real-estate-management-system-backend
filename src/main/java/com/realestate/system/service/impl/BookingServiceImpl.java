package com.realestate.system.service.impl;

import com.realestate.system.entity.User;
import com.realestate.system.enums.UserRole;
import com.realestate.system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.realestate.system.entity.Booking;
import com.realestate.system.entity.Inquiry;
import com.realestate.system.enums.BookingStatus;
import com.realestate.system.exception.BadRequestException;
import com.realestate.system.exception.ResourceNotFoundException;
import com.realestate.system.model.dto.request.CreateBookingRequest;
import com.realestate.system.model.dto.response.BookingResponse;
import com.realestate.system.repository.BookingRepository;
import com.realestate.system.repository.InquiryRepository;
import com.realestate.system.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final InquiryRepository inquiryRepository;

    @Override
    public BookingResponse createBooking(CreateBookingRequest request) {
        Inquiry inquiry = inquiryRepository.findById(request.getInquiryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inquiry not found with id: " + request.getInquiryId()
                        ));

        if (bookingRepository.existsByInquiryId(request.getInquiryId())) {
            throw new BadRequestException(
                    "Booking already exists for this inquiry"
            );
        }

        User authenticatedUser = getAuthenticatedUser();

        if (authenticatedUser.getRole() == UserRole.CUSTOMER) {

            if (!inquiry.getCustomer().getId()
                    .equals(authenticatedUser.getId())) {

                throw new BadRequestException(
                        "You can only create a booking for your own inquiry"
                );
            }
        }

        if (authenticatedUser.getRole() == UserRole.AGENT) {

            if (inquiry.getAgent() == null ||
                    !inquiry.getAgent().getId()
                            .equals(authenticatedUser.getId())) {

                throw new BadRequestException(
                        "You can only create a booking for an inquiry assigned to you"
                );
            }
        }

        Booking booking = new Booking();

        booking.setInquiry(inquiry);
        booking.setVisitDate(request.getVisitDate());
        booking.setStatus(BookingStatus.SCHEDULED);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found (SEARCH BY ID)" + id));

        return mapToResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookingResponse updateBooking(Long id, CreateBookingRequest request) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found" + id));

        existingBooking.setVisitDate(request.getVisitDate());
        Booking updatedBooking = bookingRepository.save(existingBooking);
        return mapToResponse(updatedBooking);
    }

    @Override
    public void deleteBooking(Long id) {
       Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found (DELETE BY ID)" + id));
        bookingRepository.delete(booking);
    }

    private User getAuthenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Authenticated user not found"));
    }

    private BookingResponse mapToResponse(Booking booking) {

        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());

        if (booking.getInquiry() != null) {

            Inquiry inquiry = booking.getInquiry();
            response.setInquiryId(inquiry.getId());

            if (inquiry.getProperty() != null) {
                response.setPropertyId(inquiry.getProperty().getId());
            }

            if (inquiry.getCustomer() != null) {
                response.setCustomerId(inquiry.getCustomer().getId());
            }

            if (inquiry.getAgent() != null) {
                response.setAgentId(inquiry.getAgent().getId());
            }
        }

        response.setVisitDate(booking.getVisitDate());
        response.setStatus(booking.getStatus());
        response.setCreatedAt(booking.getCreatedAt());
        response.setUpdatedAt(booking.getUpdatedAt());

        return response;
    }
}
