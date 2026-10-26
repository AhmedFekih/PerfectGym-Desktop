package tn.pidev.entities;

import java.util.Objects;

public class Evenement {
    private int idEvenement;
    private String nom;
    private String dateEvenement;
    private String statut;

    public Evenement() {
    }

    public Evenement( String nom, String dateEvenement, String statut) {
        this.nom = nom;
        this.dateEvenement = dateEvenement;
        this.statut = statut;
    }

    public int getIdEvenement() {
        return idEvenement;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(String dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                ", nom='" + nom + '\'' +
                ", dateEvenement=" + dateEvenement +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Evenement evenement)) return false;
        return idEvenement == evenement.idEvenement;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idEvenement);
    }
}
