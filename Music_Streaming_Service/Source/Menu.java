import Action.AdminAction;
import Action.MusicAction;
import Action.PlaylistAction;
import Action.UserAction;
import Entity.Admin;
import Entity.Music;
import Entity.Playlist;
import Entity.User;

import java.util.Scanner;

public class Menu {
    Scanner keyboard = new Scanner(System.in);
    AdminAction adminAction = new AdminAction();
    UserAction userAction = new UserAction();
    MusicAction musicAction = new MusicAction();
    PlaylistAction playlistAction = new PlaylistAction();

    public void startPage() {
        int choice;
        while(true) {
            System.out.println("0. Exit");
            System.out.println("1. Admin Menu");
            System.out.println("2. User Menu");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    loginPage(choice);
                    break;
                case 2:
                    loginPage(choice);
                    break;
            }
        }
    }

    public void loginPage(int info) {
        int choice;
        while(true) {
            System.out.println("0. Return to previous menu");
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    if (info == 1) {
                        Admin admin = adminAction.login();
                        if(admin != null) {
                            adminPage(admin);
                        }
                    } else {
                        User user = userAction.login();
                        if (user != null) {
                            userPage(user);
                        }
                    }
                    break;
                case 2:
                    if (info == 1) {
                        adminAction.register();
                    } else {
                        userAction.register();
                    }
                    break;
            }
        }
    }

    public void adminPage(Admin admin) {
        int choice;
        while(true) {
            System.out.println("0. Return to previous menu");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Music");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    manageUserPage(admin);
                    break;
                case 2:
                    manageMusicPage(admin);
                    break;
            }
        }
    }

    public void manageUserPage(Admin admin) {
        int choice;
        while(true) {
            System.out.println("0. Return to previous menu");
            System.out.println("1. Show all Users");
            System.out.println("2. Show Deleted Users");
            System.out.println("3. Restore Deleted User");
            System.out.println("4. Delete User");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    adminAction.showUserList();
                    break;
                case 2:
                    adminAction.showDeletedUserList();
                    break;
                case 3:
                    adminAction.showDeletedUserList();
                    adminAction.restoreUser(admin);
                    break;
                case 4:
                    adminAction.deleteUser(admin);
                    break;

            }
        }
    }

    public void manageMusicPage(Admin admin) {
        int choice;
        while(true) {
            System.out.println("0. Return to previous menu");
            System.out.println("1. Show all Music");
            System.out.println("2. Add Music");
            System.out.println("3. Delete Music");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    musicAction.searchMusicList();
                    break;
                case 2:
                    adminAction.addMusic(admin);
                    break;
                case 3:
                    adminAction.deleteMusic(admin);
                    break;
            }
        }
    }

    public void userPage(User user) {
        int choice;
        while(true) {
            System.out.println("0. Return to previous menu");
            System.out.println("1. Music menu");
            System.out.println("2. PlayList Menu");
            System.out.println("3. User info");
            System.out.println("4. Subscribe");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    musicPage(user);
                    break;
                case 2:
                    playListMainPage(user);
                    break;
                case 3:
                    System.out.println(user.toString());
                    break;
                case 4:
                    userAction.subscribe(user);
                    break;
            }
        }
    }

    public void musicPage(User user) {
        int choice;
        while(true) {
            System.out.println("0. Return to previous menu");
            System.out.println("1. Show All Music");
            System.out.println("2. Search Music by title");
            System.out.println("3. Search Music by singer");
            System.out.println("4. Search Music by genre");
            System.out.println("5. Music Chart");
            System.out.println("6. Music Streaming");
            System.out.println("7. Music Details");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    musicAction.searchMusicList();
                    break;
                case 2:
                    musicAction.searchMusicByTitle();
                    break;
                case 3:
                    musicAction.searchMusicBySinger();
                    break;
                case 4:
                    musicAction.searchMusicByGenre();
                    break;
                case 5:
                    musicAction.showStatistics();
                    break;
                case 6:
                    userAction.musicStreaming(user);
                    break;
                case 7:
                    Music music = musicAction.selectMusic();
                    System.out.println(music.toString());
                    break;
            }
        }
    }

    public void playListMainPage(User user) {
        int choice;
        while(true) {
            System.out.println("0. Return to previous menu");
            System.out.println("1. Show all Playlists");
            System.out.println("2. Make a new Playlist");
            System.out.println("3. Select a Playlist");
            System.out.println("4. Delete a Playlist");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    playlistAction.showPlaylists(user);
                    break;
                case 2:
                    playlistAction.createPlaylist(user);
                    break;
                case 3:
                    Playlist playlist = playlistAction.selectPlaylist(user);
                    if(playlist == null) {
                        break;
                    }
                    playListPage(user, playlist);
                    break;
                case 4:
                    playlistAction.deletePlaylist(user);
                    break;
            }
        }
    }

    public void playListPage(User user, Playlist playlist) {
        int choice;
        while (true) {
            System.out.println("0. Return to previous menu");
            System.out.println("1. Show all Entity.Music in Playlist");
            System.out.println("2. Add Music");
            System.out.println("3. Delete Music");
            choice = keyboard.nextInt();
            System.out.println();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    playlistAction.showMusicInPlaylist(user, playlist);
                    break;
                case 2:
                    musicAction.searchMusicList();
                    Music music1 = musicAction.selectMusic();
                    if(music1 == null) {
                        break;
                    }
                    playlistAction.insertMusicIntoPlaylist(user, playlist, music1);
                    break;
                case 3:
                    Music music2 = playlistAction.selectMusicInPlaylist(user, playlist);
                    if(music2 == null) {
                        break;
                    }
                    playlistAction.deleteMusicFromPlaylist(user, playlist, music2);
                    break;
            }
        }
    }
}