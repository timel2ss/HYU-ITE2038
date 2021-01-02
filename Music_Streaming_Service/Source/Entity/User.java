package Entity;

public class User {
    private int index;
    private String ID;
    private String PW;
    private String name;
    private String SSN;
    private String address;
    private String phoneNumber;
    private String email;
    private boolean subscription;
    private String expirationDate;
    private boolean autoSubscription;
    private int listCount;

    public User(int index, String ID, String PW, String name, String SSN, String address, String phoneNumber, String email, boolean subscription, String expirationDate, boolean autoSubscription, int listCount) {
        this.index = index;
        this.ID = ID;
        this.PW = PW;
        this.name = name;
        this.SSN = SSN;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.subscription = subscription;
        this.expirationDate = expirationDate;
        this.autoSubscription = autoSubscription;
        this.listCount = listCount;
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

    public boolean isSubscription() {
        return subscription;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isAutoSubscription() {
        return autoSubscription;
    }

    public void setAutoSubscription(boolean autoSubscription) {
        this.autoSubscription = autoSubscription;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "index=" + index +
                ", ID='" + ID + '\'' +
                ", PW='" + PW + '\'' +
                ", name='" + name + '\'' +
                ", SSN='" + SSN + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", subscription=" + subscription +
                ", expirationDate='" + expirationDate + '\'' +
                ", autoSubscription=" + autoSubscription +
                ", listCount=" + listCount +
                '}' + "\n";
    }
}