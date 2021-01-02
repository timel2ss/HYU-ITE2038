package Entity;

public class Music {
    private int index;
    private String title;
    private String album;
    private String releaseDate;
    private String lyrics;
    private int playTime;
    private int playCount;
    private String Composer;
    private String lyricist;
    private String arranger;

    public Music(int index, String title, String album, String releaseDate, String lyrics, int playTime, int playCount, String composer, String lyricist, String arranger) {
        this.index = index;
        this.title = title;
        this.album = album;
        this.releaseDate = releaseDate;
        this.lyrics = lyrics;
        this.playTime = playTime;
        this.playCount = playCount;
        Composer = composer;
        this.lyricist = lyricist;
        this.arranger = arranger;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getComposer() {
        return Composer;
    }

    public void setComposer(String composer) {
        Composer = composer;
    }

    public String getLyricist() {
        return lyricist;
    }

    public void setLyricist(String lyricist) {
        this.lyricist = lyricist;
    }

    public String getArranger() {
        return arranger;
    }

    public void setArranger(String arranger) {
        this.arranger = arranger;
    }

    @Override
    public String toString() {
        return "Music{" +
                "index=" + index +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", playTime=" + playTime +
                ", playCount=" + playCount +
                ", Composer='" + Composer + '\'' +
                ", lyricist='" + lyricist + '\'' +
                ", arranger='" + arranger + '\'' +
                '}' + '\n';
    }
}
