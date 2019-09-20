package com.example.genuva;

public class Model_party {
    private int image;
    private String name_party,time_party;

    public Model_party(int image, String name_party, String time_party) {
        this.image = image;
        this.name_party = name_party;
        this.time_party = time_party;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName_party() {
        return name_party;
    }

    public void setName_party(String name_party) {
        this.name_party = name_party;
    }

    public String getTime_party() {
        return time_party;
    }

    public void setTime_party(String time_party) {
        this.time_party = time_party;
    }
}
