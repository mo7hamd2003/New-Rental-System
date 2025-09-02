package vehiclerentalsystem.Services;

import vehiclerentalsystem.DAO.BookingDAO;
import vehiclerentalsystem.Models.Booking;

import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }

    public boolean addBooking(Booking booking) {
        return bookingDAO.addBooking(booking);
    }

    public boolean updateBooking(Booking booking) {
        return bookingDAO.updateBooking(booking);
    }

    public boolean deleteBooking(int id) {
        return bookingDAO.deleteBooking(id);
    }

    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }
}
