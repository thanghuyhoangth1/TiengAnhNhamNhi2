package hoandeptraicompany.com.tienganhnhamnhi.ObjectClass;

import java.io.Serializable;

/**
 * Created by Hoang on 8/14/2017.
 */

public class EnglishClass implements Serializable {
    private  int id;
    private String english;
    private String englishComplete;
    private String vietnamese;
    private String explian;

    public EnglishClass(int id, String english, String englishComplete, String vietnamese, String explian) {
        this.id = id;
        this.english = english;
        this.englishComplete = englishComplete;
        this.vietnamese = vietnamese;
        this.explian = explian;
    }

    public EnglishClass() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getEnglishComplete() {
        return englishComplete;
    }

    public void setEnglishComplete(String englishComplete) {
        this.englishComplete = englishComplete;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public String getExplian() {
        return explian;
    }

    public void setExplian(String explian) {
        this.explian = explian;
    }
}
