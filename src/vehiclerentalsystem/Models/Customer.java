package vehiclerentalsystem.Models;

public class Customer {
    private int id;
    private String firstname;
    private String lastname;
    private int phoneNb;
    private String email;

    // Constructors
    public Customer() {
    }

    public Customer(int id, String firstname, String lastname, int phoneNb, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNb = phoneNb;
        this.email = email;
    }

    public Customer(String firstname, String lastname, int phoneNb, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNb = phoneNb;
        this.email = email;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPhoneNb() {
        return phoneNb;
    }

    public void setPhoneNb(int phoneNb) {
        this.phoneNb = phoneNb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname; // or any format you prefer
    }

}
