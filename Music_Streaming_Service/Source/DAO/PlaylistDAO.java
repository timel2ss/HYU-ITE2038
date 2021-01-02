package DAO;

import Entity.Music;
import Entity.Playlist;
import Entity.User;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class PlaylistDAO {
    private final Properties properties = new Properties();

    private Connection getConnection() {
        try {
            String resource = "src/config/DB.properties";
            FileInputStream fi = new FileInputStream(resource);
            properties.load(fi);
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPW = properties.getProperty("password");
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(url, dbID, dbPW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(pstmt != null) {
                pstmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int showPlaylists(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT P_Name, MusicCount, TotalLength FROM playlist WHERE User_Idx = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, user.getIndex());
            rs = pstmt.executeQuery();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%10s %20s %20s\n", "Entity.Playlist", "MusicCount", "TotalLength");
            System.out.println("--------------------------------------------------------------------------------");
            while(rs.next()) {
                String name = rs.getString(1);
                int count = rs.getInt(2);
                int totalLength = rs.getInt(3);
                System.out.printf("%10s %20s %20s\n", name, count, totalLength);
            }
            System.out.println("--------------------------------------------------------------------------------\n");
            return 1;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int createPlaylist(User user, String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "INSERT INTO playlist(P_Name, User_Idx, MusicCount, TotalLength) VALUES(?, ?, ?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, user.getIndex());
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int increaseListCount(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "UPDATE user SET ListCount = ListCount + 1";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            int result = pstmt.executeUpdate();
            user.setListCount(user.getListCount() + 1);
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int decreaseListCount(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "UPDATE user SET ListCount = ListCount - 1";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            int result = pstmt.executeUpdate();
            user.setListCount(user.getListCount() - 1);
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int deletePlayList(User user, String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "DELETE FROM playlist WHERE P_name = ? AND User_Idx= ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, user.getIndex());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public Playlist selectPlaylist(User user, String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT * FROM playlist WHERE P_Name = ? AND User_Idx = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, user.getIndex());
            rs = pstmt.executeQuery();
            if(rs.next()) {
                int index = rs.getInt(1);
                String playListName = rs.getString(2);
                int userIdx = rs.getInt(3);
                int count = rs.getInt(4);
                int totalLength = rs.getInt(5);
                return new Playlist(index, playListName, userIdx, count, totalLength);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    public int showMusicInPlaylist(User user, Playlist playlist) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT Title FROM music, contains WHERE PlayList_Idx = ? AND Music_Idx = M_Index";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, playlist.getIndex());
            rs = pstmt.executeQuery();
            System.out.println("--------------------");
            System.out.printf("%15s\n", "Title");
            System.out.println("--------------------");
            while(rs.next()) {
                String title = rs.getString(1);
                System.out.printf("%15s\n", title);
            }
            System.out.println("--------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public Music selectMusicInPlaylist(User user, Playlist playlist, String title) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT M_Index, Title,  Album, ReleaseDate, Lyrics, PlayTime, PlayCount, Composer, Lyricist, Arranger FROM music, playlist, contains WHERE PlayList_Idx = P_Index AND Music_Idx = M_Index AND P_Index = ? AND Title = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, playlist.getIndex());
            pstmt.setString(2, title);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int index = rs.getInt(1);
                String name = rs.getString(2);
                String album = rs.getString(3);
                String date = rs.getString(4);
                String lyrics = rs.getString(5);
                int playTime = rs.getInt(6);
                int playCount = rs.getInt(7);
                String composer = rs.getString(8);
                String lyricist = rs.getString(9);
                String arranger = rs.getString(10);
                return new Music(index, name, album, date, lyrics, playTime, playCount, composer, lyricist, arranger);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    public int insertMusicIntoPlaylist(User user, Playlist playlist, Music music) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "INSERT INTO contains VALUES(?, ?)";
        String update = "UPDATE playlist SET MusicCount = MusicCount + 1, TotalLength = ? WHERE P_Index= ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, music.getIndex());
            pstmt.setInt(2, playlist.getIndex());
            int result1 = pstmt.executeUpdate();
            pstmt = conn.prepareStatement(update);
            pstmt.setInt(1, playlist.getTotalLength() + music.getPlayTime());
            playlist.setTotalLength(playlist.getTotalLength() + music.getPlayTime());
            pstmt.setInt(2, playlist.getIndex());
            playlist.setMusicCount(playlist.getMusicCount() + 1);

            int result2 = pstmt.executeUpdate();
            if(result1 > 0 && result2 > 0) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int deleteMusicFromPlaylist(User user, Playlist playlist, Music music) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "DELETE FROM contains WHERE Music_Idx = ? AND PlayList_Idx = ?";
        String update = "UPDATE playlist SET MusicCount = MusicCount - 1, TotalLength = ? WHERE P_Index= ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, music.getIndex());
            pstmt.setInt(2, playlist.getIndex());
            int result1 =  pstmt.executeUpdate();
            pstmt = conn.prepareStatement(update);
            pstmt.setInt(1, playlist.getTotalLength() - music.getPlayTime());
            playlist.setMusicCount(playlist.getMusicCount() - 1);
            pstmt.setInt(2, playlist.getIndex());
            playlist.setTotalLength(playlist.getTotalLength() - music.getPlayTime());
            int result2 = pstmt.executeUpdate();
            if(result1 > 0 && result2 > 0) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }
}
