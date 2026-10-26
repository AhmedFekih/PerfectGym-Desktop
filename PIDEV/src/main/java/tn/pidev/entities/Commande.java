package tn.pidev.entities;

import java.util.Objects;

public class Commande {
    private int idCommande;
    private int idClient;
    private int quantite;
    private float prixTotal;
    private String statut;

    public Commande() {
    }
    public Commande( int idClient, int quantite, float prixTotal, String statut) {
        this.idClient = idClient;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
        this.statut = statut;

    }

    public int getIdCommande() {
        return idCommande;
    }


    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Commande{" +
                ", idClient=" + idClient +
                ", quantite=" + quantite +
                ", prixTotal=" + prixTotal +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Commande commande)) return false;
        return idCommande == commande.idCommande;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCommande);
    }
}
