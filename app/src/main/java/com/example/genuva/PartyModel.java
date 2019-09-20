package com.example.genuva;

public class PartyModel {
    private String partyCoverImage, partyFirstClass, PartySecondClass, partyThirdClass, partyName, partyTime, partyKey;

    public PartyModel() {
    }

    public PartyModel(String partyCoverImage, String partyFirstClass, String partySecondClass, String partyThirdClass, String partyName, String partyTime, String partyKey) {
        this.partyCoverImage = partyCoverImage;
        this.partyFirstClass = partyFirstClass;
        this.PartySecondClass = partySecondClass;
        this.partyThirdClass = partyThirdClass;
        this.partyName = partyName;
        this.partyTime = partyTime;
        this.partyKey = partyKey;

    }

    public String getPartyKey() {
        return partyKey;
    }

    public String getPartyCoverImage() {
        return partyCoverImage;
    }

    public String getPartyFirstClass() {
        return partyFirstClass;
    }

    public String getPartySecondClass() {
        return PartySecondClass;
    }

    public String getPartyThirdClass() {
        return partyThirdClass;
    }

    public String getPartyName() {
        return partyName;
    }

    public String getPartyTime() {
        return partyTime;
    }

}
