package Action;

import DAO.MusicDAO;
import Entity.Music;

import java.util.Scanner;

public class MusicAction {
    MusicDAO musicDAO = new MusicDAO();
    Scanner keyboard = new Scanner(System.in);

    public void searchMusicList() {
        musicDAO.searchMusicList();
    }

    public void searchMusicByTitle() {
        System.out.print("Title: ");
        String title = keyboard.next();
        musicDAO.searchMusicByTitle(title);
    }

    public void searchMusicBySinger() {
        System.out.print("Singer name: ");
        String name = keyboard.next();
        musicDAO.searchMusicBySinger(name);
    }

    public void searchMusicByGenre() {
        System.out.print("Genre name: ");
        String name = keyboard.next();
        name = name.toLowerCase();
        musicDAO.searchMusicByGenre(name);
    }

    public Music selectMusic() {
        System.out.print("Title: ");
        String title = keyboard.next();
        System.out.println();
        return musicDAO.selectMusic(title);
    }

    public void showStatistics() {
        musicDAO.showStatistics();
    }
}
