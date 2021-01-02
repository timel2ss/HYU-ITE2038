package Action;

import DAO.AdminDAO;
import Entity.Admin;

import java.security.MessageDigest;
import java.util.Scanner;

public class AdminAction {
    Admin admin;
    AdminDAO adminDAO = new AdminDAO();
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

        int result = adminDAO.register(ID, PW, name, SSN, address, phoneNumber, email);

        if(result >= 0) {
            System.out.println("Register Success\n");
            return 1;
        }
        return -1;
    }

    public Admin login() {
        System.out.print("ID: ");
        String ID = keyboard.next();
        System.out.print("PW: ");
        String PW = keyboard.next();
        PW = SHA256(PW);
        int result = adminDAO.login(ID, PW);
        if(result > 0) {
            admin = adminDAO.getInfo(result);
            System.out.println("Login Success\n");
            return admin;
        }
        return null;
    }

    public void showUserList() {
        adminDAO.showUserList();
    }

    public void showDeletedUserList() {
        adminDAO.showDeletedUserList();
    }

    public void deleteUser(Admin admin) {
        System.out.print("User Index: ");
        int index = keyboard.nextInt();
        adminDAO.deleteUser(index);
        adminDAO.insertManagingUser(admin, index);
    }

    public void restoreUser(Admin admin) {
        System.out.print("User Index: ");
        int index = keyboard.nextInt();
        adminDAO.restore(index);
        adminDAO.deleteManagingUser(admin, index);
    }

    public int addMusic(Admin admin) {
        System.out.print("Title: ");
        String title = keyboard.next();

        keyboard.nextLine();
        System.out.print("Singer(if many artists participated, separate with spacebar): ");
        String singer = keyboard.nextLine();
        boolean contains = singer.contains(" ");
        String[] singers = singer.split(" ");

        System.out.print("Genre(if music has many genres, seperate with spacebar): ");
        String genre = keyboard.nextLine();
        genre = genre.toLowerCase();
        boolean contains2 = genre.contains(" ");
        String[] genres = genre.split(" ");

        System.out.print("Album: ");
        String album = keyboard.next();
        System.out.print("Lyrics: ");
        String lyrics = keyboard.next();
        System.out.print("Playtime (s): ");
        int playTime = keyboard.nextInt();
        System.out.print("Composer: ");
        String composer = keyboard.next();
        System.out.print("Lyricist: ");
        String lyricist = keyboard.next();
        System.out.print("Arranger: ");
        String arranger = keyboard.next();

        int result = adminDAO.musicInsert(title, album, lyrics, playTime, composer, lyricist, arranger);
        int idx = adminDAO.getMusicLastIdx();
        int result2 = adminDAO.insertManagingMusic(admin, idx);

        if(contains) {
            for (int i = 0; i < singers.length; i++) {
                adminDAO.singerInsert(idx, singers[i]);
            }
        }
        else {
            adminDAO.singerInsert(idx, singer);
        }

        if(contains2) {
            for(int i = 0; i < genres.length; i++) {
                adminDAO.genreInsert(idx, genres[i]);
            }
        }
        else {
            adminDAO.genreInsert(idx, genre);
        }

        if(result > 0 && result2 > 0) {
            System.out.println("Insert Success\n");
            return 1;
        }
        return -1;
    }

    public int deleteMusic(Admin admin) {
        System.out.print("Title: ");
        String title = keyboard.next();
        int deleteIdx = adminDAO.findIndexByTitle(title);
        int result = adminDAO.deleteMusic(deleteIdx);
        if(result > 0) {
            System.out.println("Delete Success\n");
            return 1;
        }
        return -1;
    }

}
