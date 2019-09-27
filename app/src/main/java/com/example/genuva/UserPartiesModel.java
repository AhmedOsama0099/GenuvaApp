package com.example.genuva;

public class UserPartiesModel {


    public String getPartyId() {
        return partyId;
    }
    private String partyPlace;
    private String partyId;
    private String partyName;
    private String partyTime;
    private String totalPartyPrice;
    private String seatsNumbers;
    private int seatsCount;

    public String getPartyPlace() {
        return partyPlace;
    }

    public String getPartyName() {
        return partyName;
    }

    public String getPartyTime() {
        return partyTime;
    }

    public String getTotalPartyPrice() {
        return totalPartyPrice;
    }

    public String getSeatsNumbers() {
        return seatsNumbers;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public UserPartiesModel(String partyPlace, String partyId, String partyName, String partyTime, String totalPartyPrice, String seatsNumbers, int seatsCount) {
        this.partyPlace = partyPlace;
        this.partyId = partyId;
        this.partyName = partyName;
        this.partyTime = partyTime;
        this.totalPartyPrice = totalPartyPrice;
        this.seatsNumbers = seatsNumbers;
        this.seatsCount = seatsCount;
    }

    public UserPartiesModel() {
    }
}
