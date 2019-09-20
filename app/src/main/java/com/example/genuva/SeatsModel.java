package com.example.genuva;

public class SeatsModel {
    private String id, ticketPrice;
    private Boolean seat_state;

    public SeatsModel() {
    }

    public SeatsModel(String id, String ticketPrice, Boolean seat_state) {
        this.id = id;
        this.ticketPrice = ticketPrice;
        this.seat_state = seat_state;
    }

    public String getId() {
        return id;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public Boolean getSeat_state() {
        return seat_state;
    }
}
