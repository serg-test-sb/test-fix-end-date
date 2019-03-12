package com.tsystems.fixedline;
class Kondition {
    VertragsLaufZeit vertragsLaufZeit;
    VerlaengerungsFrist verlaengerungsFrist;
    String art;

    Kondition(String art, VertragsLaufZeit vertragsLaufZeit, VerlaengerungsFrist verlaengerungsFrist) {
        this.art = art;
        this.vertragsLaufZeit = vertragsLaufZeit;
        this.verlaengerungsFrist = verlaengerungsFrist;
    }
}
