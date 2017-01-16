package by.training.Task2_Burets.bean;

public class User {
    private String email;
    private String password;
    private String name;

    private RentUnit rentedEquipments;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.rentedEquipments = new RentUnit();
    }

    public User(String email, String password, String name) {
        this(email, password);
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public RentUnit getRentedEquipments() {
        return this.rentedEquipments;
    }

    public void setRentedEquipments(RentUnit rentedEquipments) {
        this.rentedEquipments = rentedEquipments;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
