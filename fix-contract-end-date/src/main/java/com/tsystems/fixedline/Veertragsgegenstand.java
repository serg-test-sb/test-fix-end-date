package com.tsystems.fixedline;
import java.util.List;

public class Veertragsgegenstand {
    List<Kondition> konditionList;
    IstTermin istTermin;

    public Veertragsgegenstand(List<Kondition> konditionList, IstTermin istTermin) {
        this.konditionList = konditionList;
        this.istTermin = istTermin;
    }
}
