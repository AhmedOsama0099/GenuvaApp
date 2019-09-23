package com.example.genuva;

public class UserPartiesModel {
    private String partyName,partyTime,totalPartyPrice,seatsNumbers;
    private int seatsCount;

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

    public UserPartiesModel(String partyName, String partyTime, String totalPartyPrice, String seatsNumbers, int seatsCount) {
        this.partyName = partyName;
        this.partyTime = partyTime;
        this.totalPartyPrice = totalPartyPrice;
        this.seatsNumbers = seatsNumbers;
        this.seatsCount = seatsCount;
    }

    public UserPartiesModel() {
    }
}
