package tn.pidev.entities;

import java.util.Objects;

public class Inscription {
    private int idClient;
    private int idPlanning;
    private String statut;
    private int idInscription;
    public Inscription() {
    }

    public Inscription(int idClient, int idPlanning, String statut) {
        this.idClient = idClient;
        this.idPlanning = idPlanning;
        this.statut = statut;
    }
    public int getIdInscription(){
        return idInscription;
    }
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(int idPlanning) {
        this.idPlanning = idPlanning;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "idClient=" + idClient +
                ", idPlanning=" + idPlanning +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Inscription that)) return false;
        return idInscription == that.idInscription;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idInscription);
    }
}
