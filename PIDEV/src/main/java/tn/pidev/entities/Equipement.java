package tn.pidev.entities;

import java.util.Objects;

public class Equipement {
    private int ref;
    private String nom;
    private int quantite;
    private String statut;
    private int idSalle;
    private String image;  // Added field for image path or URL

    // Constructor with image
    public Equipement(String nom, int quantite, String statut, int idSalle, String image) {
        this.nom = nom;
        this.quantite = quantite;
        this.statut = statut;
        this.idSalle = idSalle;
        this.image = image; // Initialize the image
    }

    // Default constructor
    public Equipement() {}

    // Getter and Setter for image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    // Optional: Update toString method to include image
    @Override
    public String toString() {
        return "Equipement{" +
                "ref=" + ref +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", statut='" + statut + '\'' +
                ", idSalle=" + idSalle +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Equipement that)) return false;
        return ref == that.ref;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ref);
    }
}
