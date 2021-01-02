package DAO;

import Entity.Music;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class MusicDAO {
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

    public int searchMusicList() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int oldIdx = 0;
        boolean flag = false;
        String title;
        String SQL = "SELECT M_Index, Title, Name FROM music, singer WHERE M_Index = Music_Idx";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%15s %20s\n", "Title", "Artists");
            System.out.print("--------------------------------------------------------------------------------");
            while(rs.next()) {
                int index = rs.getInt(1);
                if(oldIdx != index) {
                    oldIdx = index;
                    flag = true;
                    title = rs.getString(2);
                    System.out.printf("\n%15s", title);
                }
                String artist = rs.getString(3);

                if(flag) {
                    System.out.printf(" %20s", artist);
                }
                else {
                    System.out.print(" " + artist);
                }
                flag = false;
            }
            System.out.println("\n--------------------------------------------------------------------------------\n");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int searchMusicByTitle(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int oldIdx = 0;
        boolean flag = false;
        String title;
        String SQL = "SELECT M_Index, Title, Name FROM music, singer WHERE M_Index = Music_Idx and M_Index in (SELECT M_Index FROM music WHERE Title LIKE concat('%', ?, '%'))";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%15s %20s\n", "Title", "Artists");
            System.out.print("--------------------------------------------------------------------------------");
            while(rs.next()) {
                int index = rs.getInt(1);
                if(oldIdx != index) {
                    oldIdx = index;
                    flag = true;
                    title = rs.getString(2);
                    System.out.printf("\n%15s", title);
                }
                String artist = rs.getString(3);

                if(flag) {
                    System.out.printf(" %20s", artist);
                }
                else {
                    System.out.print(" " + artist);
                }
                flag = false;
            }
            System.out.println("\n--------------------------------------------------------------------------------\n");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int searchMusicBySinger(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int oldIdx = 0;
        boolean flag = false;
        String title;
        String SQL = "SELECT M_Index, Title, Name FROM music, singer WHERE M_Index = Music_Idx and M_Index in (SELECT Music_Idx FROM singer WHERE Name LIKE concat('%', ?, '%'))";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%15s %20s\n", "Title", "Artists");
            System.out.print("--------------------------------------------------------------------------------");
            while(rs.next()) {
                int index = rs.getInt(1);
                if(oldIdx != index) {
                    oldIdx = index;
                    flag = true;
                    title = rs.getString(2);
                    System.out.printf("\n%15s", title);
                }
                String artist = rs.getString(3);

                if(flag) {
                    System.out.printf(" %20s", artist);
                }
                else {
                    System.out.print(" " + artist);
                }
                flag = false;
            }
            System.out.println("\n--------------------------------------------------------------------------------\n");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public int searchMusicByGenre(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int oldIdx = 0;
        boolean flag = false;
        String title;
        String SQL = "SELECT M_Index, Title, NAME FROM music, singer WHERE M_Index = Music_Idx and M_Index in (SELECT Music_Idx FROM genre WHERE MusicGenre LIKE concat('%', ?, '%'))";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%15s %20s\n", "Title", "Artists");
            System.out.print("--------------------------------------------------------------------------------");
            while(rs.next()) {
                int index = rs.getInt(1);
                if(oldIdx != index) {
                    oldIdx = index;
                    flag = true;
                    title = rs.getString(2);
                    System.out.printf("\n%15s", title);
                }
                String artist = rs.getString(3);

                if(flag) {
                    System.out.printf(" %20s", artist);
                }
                else {
                    System.out.print(" " + artist);
                }
                flag = false;
            }
            System.out.println("\n--------------------------------------------------------------------------------\n");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }

    public Music selectMusic(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String SQL = "SELECT * FROM music WHERE Title = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                int index = rs.getInt(1);
                String title = rs.getString(2);
                String album = rs.getString(3);
                String date = rs.getString(4);
                String lyrics = rs.getString(5);
                int playTime = rs.getInt(6);
                int playCount = rs.getInt(7);
                String composer = rs.getString(8);
                String lyricist = rs.getString(9);
                String arranger = rs.getString(10);
                return new Music(index, title, album, date, lyrics, playTime, playCount, composer, lyricist, arranger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    public int showStatistics() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int oldIdx = 0;
        boolean flag = false;
        String title;
        String SQL = "SELECT M_Index, Title, Name, PlayCount FROM music, singer WHERE M_Index = Music_Idx ORDER BY PlayCount desc";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%15s %20s %20s\n", "Title", "Play Count", "Artists");
            System.out.print("--------------------------------------------------------------------------------");
            while(rs.next()) {
                int index = rs.getInt(1);
                int playCount = rs.getInt(4);
                if(oldIdx != index) {
                    oldIdx = index;
                    flag = true;
                    title = rs.getString(2);
                    System.out.printf("\n%15s", title);
                    System.out.printf(" %20d", playCount);
                }
                String artist = rs.getString(3);


                if(flag) {
                    System.out.printf(" %20s", artist);
                }
                else {
                    System.out.print(" " + artist);
                }
                flag = false;
            }
            System.out.println("\n--------------------------------------------------------------------------------\n");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return -1;
    }
}
