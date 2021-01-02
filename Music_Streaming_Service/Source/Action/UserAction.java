package Action;

import DAO.UserDAO;
import Entity.User;

import java.security.MessageDigest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class UserAction {
    User user;
    UserDAO userDAO = new UserDAO();
    Scanner keyboard = new Scanner(System.in);

    public String SHA256(String PW) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(PW.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for(int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int register() {
        System.out.print("ID: ");
        String ID = keyboard.next();
        System.out.print("PW: ");
        String PW = keyboard.next();
        PW = SHA256(PW);
        System.out.print("Name: ");
        String name = keyboard.next();
        System.out.print("SSN: ");
        String SSN = keyboard.next();
        System.out.print("Address: ");
        String address = keyboard.next();
        System.out.print("PhoneNumber: ");
        String phoneNumber = keyboard.next();
        System.out.print("Email: ");
        String email = keyboard.next();

        int result = userDAO.register(ID, PW, name, SSN, address, phoneNumber, email);

        if(result >= 0) {
            System.out.println("Register Success\n");
            return 1;
        }
        return -1;
    }

    public User login() {
        String ID, PW;
        System.out.print("ID: ");
        ID = keyboard.next();
        System.out.print("PW: ");
        PW = keyboard.next();
        PW = SHA256(PW);
        int result = userDAO.login(ID, PW);
        if(result > 0) {
            user = userDAO.getInfo(result);
            if(user.getExpirationDate() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    java.util.Date deadline = simpleDateFormat.parse(user.getExpirationDate());
                    java.util.Date date = new Date(new java.util.Date().getTime());
                    if (date.after(deadline)) {
                        if (user.isAutoSubscription()) {
                            subscribe(user);
                        } else {
                            unsubscribe(user);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Login Success\n");
            return user;
        }
        return null;
    }

    public void musicStreaming(User user) {
        if(!user.isSubscription()) {
            System.out.println("Subscribe First\n");
            return;
        }
        System.out.print("Title (cancel c): ");
        String title = keyboard.next();
        if(title.equalsIgnoreCase("c")) {
            return;
        }
        int result = userDAO.musicStreaming(title);
        if(result > 0) {
            System.out.println("Streaming Success\n");
        }
    }

    public void subscribe(User user) {
        if(user.isSubscription()) {
            System.out.println("You're already subscribed\n");
            return;
        }

        System.out.print("AutoSubscribe (Y/N): ");
        String answer = keyboard.next();
        boolean flag;

        if(answer.equalsIgnoreCase("Y")) {
            flag = true;
        }
        else if(answer.equalsIgnoreCase("N")) {
            flag = false;
        }
        else {
            System.out.println("Wrong input\n");
            return;
        }
        int result = userDAO.subscribe(user.getIndex(), flag);
        if(result > 0) {
            user.setSubscription(true);
            Date date = new Date(new java.util.Date().getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            user.setExpirationDate(new java.sql.Date(cal.getTime().getTime()).toString());
            user.setAutoSubscription(true);
            System.out.println("Subscribe Success\n");
        }
    }

    public void unsubscribe(User user) {
        int result = userDAO.unsubscribe(user.getIndex());
        if(result > 0) {
            user.setSubscription(false);
            user.setExpirationDate(null);
            user.setAutoSubscription(false);
            System.out.println("Unsubscribed\n");
        }
    }
}
