package dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String address;
    private String role;

    public CustomerDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id){ this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName){ this.lastName = lastName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role;}

}
