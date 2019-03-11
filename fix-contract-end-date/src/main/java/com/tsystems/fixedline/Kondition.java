package com.tsystems.fixedline;
public class Kondition {
    VertragsLaufZeit vertragsLaufZeit;
    VerlaengerungsFrist verlaengerungsFrist;
    public String art;

    public Kondition(String art, VertragsLaufZeit vertragsLaufZeit, VerlaengerungsFrist verlaengerungsFrist) {
        this.art = art;
        this.vertragsLaufZeit = vertragsLaufZeit;
        this.verlaengerungsFrist = verlaengerungsFrist;
    }
}
