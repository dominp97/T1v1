package com.pmdm.t1v1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Datos {
    private boolean escuchaRock;
    private int discos;
    private String grupoFavorito;
    private ArrayList<String> cancion;
    private String festival;
    private GregorianCalendar fechaConcierto;

    public Datos(boolean escuchaRock, int discos, String grupoFavorito, ArrayList<String> cancion,
                 String festival, GregorianCalendar fechaConcierto) {
        this.escuchaRock = escuchaRock;
        this.discos = discos;
        this.grupoFavorito = grupoFavorito;
        this.cancion = cancion;
        this.festival = festival;
        this.fechaConcierto = fechaConcierto;
    }

    @Override
    public String toString() {
        String salida = "";
        salida = "Datos{" +
                "escuchaRock=" + escuchaRock +
                ", discos=" + discos +
                ", grupoFavorito=" + grupoFavorito ;
                for(String can : cancion){
                    salida += ", cancion='" + can + '\'' ;
                }
                salida += ", festival='" + festival + '\'' +
                ", fechaConcierto=" + fechaConcierto.get(Calendar.DAY_OF_MONTH)+"/"+
                        (fechaConcierto.get(Calendar.MONTH)+1)+"/"+
                        fechaConcierto.get(Calendar.YEAR)+
                '}';

        return salida;
    }
}
