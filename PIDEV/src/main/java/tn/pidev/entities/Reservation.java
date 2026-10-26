package tn.pidev.entities;

import java.util.Objects;

public class Reservation {
    private int idClient;
    private int nbPlaces;
    private String Statut;
    private int idReservation;
    public Reservation() {
    }

    public Reservation(int idClient, int nbPlaces, String Statut) {
        this.idClient = idClient;
        this.nbPlaces = nbPlaces;
        this.Statut = Statut;
    }
    public int getidReservation(){
        return idReservation;
    }
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idClient=" + idClient +
                ", nbPlaces=" + nbPlaces +
                ", Statut='" + Statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reservation that)) return false;
        return idReservation == that.idReservation;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idReservation);
    }
}
