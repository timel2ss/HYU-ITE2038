package Entity;

public class Admin {
    private int index;
    private String ID;
    private String PW;
    private String name;
    private String SSN;
    private String address;
    private String phoneNumber;
    private String email;

    public Admin(int index, String ID, String PW, String name, String SSN, String address, String phoneNumber, String email) {
        this.index = index;
        this.ID = ID;
        this.PW = PW;
        this.name = name;
        this.SSN = SSN;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
