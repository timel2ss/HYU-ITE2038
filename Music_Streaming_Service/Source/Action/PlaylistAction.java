package Action;

import DAO.PlaylistDAO;
import Entity.Music;
import Entity.Playlist;
import Entity.User;

import java.util.Scanner;

public class PlaylistAction {
    PlaylistDAO playlistDAO = new PlaylistDAO();
    Scanner keyboard = new Scanner(System.in);

    public void showPlaylists(User user) {
        playlistDAO.showPlaylists(user);
    }

    public void createPlaylist(User user) {
        System.out.print("Playlist Name: ");
        String name = keyboard.next();
        int result = playlistDAO.createPlaylist(user, name);
        int result2 = playlistDAO.increaseListCount(user);
        if(result > 0 && result2 > 0) {
            System.out.println("Create Success\n");
        }
    }

    public void deletePlaylist(User user) {
        System.out.print("Playlist Name (Cancel c): ");
        String name = keyboard.next();
        if(name.equalsIgnoreCase("c")) {
            return;
        }
        int result = playlistDAO.deletePlayList(user, name);
        int result2 = playlistDAO.decreaseListCount(user);
        if(result > 0 && result2 > 0) {
            System.out.println("Delete Success\n");
        }
    }

    public Playlist selectPlaylist(User user) {
        System.out.print("Playlist Name (Cancel c): ");
        String name = keyboard.next();
        if(name.equalsIgnoreCase("c")) {
            return null;
        }
        Playlist playlist = playlistDAO.selectPlaylist(user, name);
        if(playlist != null) {
            return playlist;
        }
        System.out.println();
        return null;
    }

    public void showMusicInPlaylist(User user, Playlist playlist) {
        playlistDAO.showMusicInPlaylist(user, playlist);
    }

    public void insertMusicIntoPlaylist(User user, Playlist playlist, Music music) {
        playlistDAO.insertMusicIntoPlaylist(user, playlist, music);
    }

    public void deleteMusicFromPlaylist(User user, Playlist playlist, Music music) {
        playlistDAO.deleteMusicFromPlaylist(user, playlist, music);
    }

    public Music selectMusicInPlaylist(User user, Playlist playlist) {
        System.out.print("Playlist Name (Cancel c): ");
        String title = keyboard.next();
        if(title.equalsIgnoreCase("c")) {
            return null;
        }
        Music music = playlistDAO.selectMusicInPlaylist(user, playlist, title);
        if(music != null) {
            return music;
        }
        return null;
    }
}
