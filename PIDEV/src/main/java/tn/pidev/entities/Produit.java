package tn.pidev.entities;

import java.util.Objects;

public class Produit {
    private int idProduit;
    private String nomProduit;
    private String description;
    private int quantite;
    private float prix;
    private String image;
    private String statut;

    public Produit() {
    }

    public Produit(String nomProduit, String description, int quantite, float prix, String image, String statut) {
        this.nomProduit = nomProduit;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
        this.image = image;
        this.statut = statut;
    }

    public int getIdProduit() {
        return idProduit;
    }


    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Produit{" +
                ", nomProduit='" + nomProduit + '\'' +
                ", description='" + description + '\'' +
                ", quantite=" + quantite +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Produit produit)) return false;
        return idProduit == produit.idProduit;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idProduit);
    }
}
