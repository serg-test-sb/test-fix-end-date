package com.tsystems.fixedline;
import java.util.List;

class Veertragsgegenstand {
    List<Kondition> konditionList;
    IstTermin istTermin;

    Veertragsgegenstand(List<Kondition> konditionList, IstTermin istTermin) {
        this.konditionList = konditionList;
        this.istTermin = istTermin;
    }
}
