package tn.pidev.entities;

import java.util.Objects;

public class Utilisateur {
    private int cin;
    private String nom;
    private String prenom;
    private int numtel;
    private String Adresse;
    private String Sexe;
    private String motdepasse;
    private tn.pidev.entities.Categorie Categorie;
    private String statut;
    private SalleDeSport salle;
   // private List<Reclamation> reclamations;
    public Utilisateur() {
    }
    public Utilisateur(int cin, String nom, String prenom, int numtel, String Adresse, String Sexe, String motdepasse, Categorie Categorie, String statut) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.Adresse = Adresse;
        this.Sexe = Sexe;
        this.motdepasse = motdepasse;
        this.Categorie = Categorie;
        this.statut = statut;
    }



    public int getCin() {
        return cin;
    }


    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public Categorie getCategorie() {
        return Categorie;
    }

    public void setCategorie(Categorie categorie) {
        Categorie = categorie;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    //public List<Reclamation> getReclamations() { return reclamations; }

//    public void ajouterReclamation(Reclamation reclamation) {
//        reclamations.add(reclamation);
//    }
//
//    public void supprimerReclamation(Reclamation reclamation) {
//        reclamations.remove(reclamation);
//    }
    @Override
    public String toString() {
        return "Utilisateur{" +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numtel=" + numtel +
                ", Adresse='" + Adresse + '\'' +
                ", Sexe='" + Sexe + '\'' +
                ", motdepasse='" + motdepasse + '\'' +
                ", Categorie=" + Categorie +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Utilisateur that)) return false;
        return cin == that.cin;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cin);
    }
}
