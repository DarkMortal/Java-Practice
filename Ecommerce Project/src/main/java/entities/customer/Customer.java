package entities.customer;

import java.io.Serializable;

public record Customer(String name, String email, String password) implements Serializable {
    public Customer {
        if(name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if(password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }
        if(email.isBlank() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
