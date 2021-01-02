package Entity;

public class Playlist {
    private int index;
    private String name;
    private int userIdx;
    private int musicCount;
    private int totalLength;

    public Playlist(int index, String name, int userIdx, int musicCount, int totalLength) {
        this.index = index;
        this.name = name;
        this.userIdx = userIdx;
        this.musicCount = musicCount;
        this.totalLength = totalLength;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public int getMusicCount() {
        return musicCount;
    }

    public void setMusicCount(int musicCount) {
        this.musicCount = musicCount;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }
}
