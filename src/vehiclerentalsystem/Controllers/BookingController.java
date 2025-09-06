package vehiclerentalsystem.Controllers;

import vehiclerentalsystem.Services.BookingService;
import vehiclerentalsystem.Models.Booking;
import java.util.List;

public class BookingController {
    private BookingService bookingService;

    public BookingController() {
        this.bookingService = new BookingService();
    }

    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    public boolean addBooking(Booking booking) {
        return bookingService.addBooking(booking);
    }

    public boolean updateBooking(Booking booking) {
        return bookingService.updateBooking(booking);
    }

    public boolean deleteBooking(int id) {
        return bookingService.deleteBooking(id);
    }
}