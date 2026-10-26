package tn.pidev.entities;

import tn.pidev.entities.Utilisateur;

import java.util.Objects;

public class SalleDeSport {
    private int idSalle;
    private String nom;
    private String localisation;
    private String image;
    private String statut;
    private tn.pidev.entities.Utilisateur utilisateur;

    public SalleDeSport() {
    }

    public SalleDeSport(String nom, String localisation, String image, String statut) {
        this.localisation = localisation;
        this.nom = nom;
        this.image = image;
        this.statut = statut;
    }
    public SalleDeSport(int idSalle,String nom, String localisation, String statut) {
        this.idSalle = idSalle;
        this.nom = nom;
        this.localisation = localisation;
        this.statut = statut;
    }

    public tn.pidev.entities.Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {}

    public int getIdSalle() {
        return idSalle;
    }
    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image){}
    @Override
    public String toString() {
        return "SalleDeSport{" +
                ", nom='" + nom + '\'' +
                ", localisation='" + localisation + '\'' +
                ", image='" + image + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SalleDeSport that)) return false;
        return idSalle == that.idSalle;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idSalle);
    }
}
