package tn.pidev.entities;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class Offre {
    private int idOffre;
    private String nomOffre;
    private String description;
    private double prix;
    private int duree;
    private SalleDeSport salle;
    private Statut statut;
    private List<String> images;
    private Categorie categorie;  // Nouveau champ pour la catégorie

    public Offre() {
    }
    public enum Statut {
        Active,
        Inactive
    }
    public enum Categorie {
        Fitness,
        Yoga,
        Cardio,
        Musculation,
        Natation,
        Boxe,
        Zumba,
        Running,
        Danse
    }

    public Offre(int idOffre, String nomOffre, Statut statut, String description, double prix, int duree,List<String> images,Categorie categorie) {
        this.idOffre = idOffre;
        this.nomOffre = nomOffre;
        this.statut = statut;
        this.description = description;
        this.prix = prix;
        this.duree = duree;
        this.images = images;
        this.categorie=categorie;
    }
    public Offre( String nomOffre, Statut statut, String description, double prix, int duree,List<String> images,Categorie categorie) {
        this.nomOffre = nomOffre;
        this.statut = statut;
        this.description = description;
        this.prix = prix;
        this.duree = duree;
        this.images = images;
        this.categorie=categorie;

    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getIdOffre() {
        return idOffre;
    }
    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public SalleDeSport getSalle() {
        return salle;
    }
    public void setSalle(SalleDeSport salle) {
        this.salle = salle;
    }

    public String getNomOffre() {
        return nomOffre;
    }

    public void setNomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    @Override
    public String toString() {
        return "Offre{" +
                " nomOffre='" + nomOffre + '\'' +
                ", categorie=" + categorie +'\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", duree=" + duree +
                ", statut='" + statut + '\'' +
                ", images=" + images + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Offre offre)) return false;
        return idOffre == offre.idOffre;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idOffre);
    }
}
