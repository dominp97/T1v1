package com.pmdm.t1v1;

import java.util.Date;

public class Datos {
    private boolean escuchaRock;
    private int discos;
    private int grupoFavorito;
    private String cancion;
    private String festival;
    private Date fechaConcierto;

    public Datos(boolean escuchaRock, int discos, int grupoFavorito, String cancion, String festival, Date fechaConcierto) {
        this.escuchaRock = escuchaRock;
        this.discos = discos;
        this.grupoFavorito = grupoFavorito;
        this.cancion = cancion;
        this.festival = festival;
        this.fechaConcierto = fechaConcierto;
    }

    @Override
    public String toString() {
        return "Datos{" +
                "escuchaRock=" + escuchaRock +
                ", discos=" + discos +
                ", grupoFavorito=" + grupoFavorito +
                ", cancion='" + cancion + '\'' +
                ", festival='" + festival + '\'' +
                ", fechaConcierto=" + fechaConcierto +
                '}';
    }
}
