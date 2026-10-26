package tn.pidev.entities;

import java.util.Objects;
import java.time.LocalDate;

public class Abonnement {
    private int idAbonnement;
    private Utilisateur utilisateur;
    private Offre offre;
    private String nomOffre;
    private String nomAbonnement;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String Status;
    private String nomSalle;
    private String NomClient;
    private int idOffre;
    private double prixOffre;
    public Abonnement() {
    }
    public Abonnement( int idAbonnement,Offre offre,Utilisateur user ,String nomAbonnement, LocalDate dateDebut, String status,double prixOffre ) {
       this.idAbonnement = idAbonnement;
        this.offre=offre;
        this.utilisateur=user;
        this.nomAbonnement = nomAbonnement;
        this.dateDebut = dateDebut;
        this.dateFin = dateDebut.plusDays(offre.getDuree());
        this.Status = status;
        this.prixOffre=prixOffre;
    }
    public Abonnement( Offre offre,Utilisateur user ,String nomAbonnement, LocalDate dateDebut, String status) {
        this.offre=offre;
        this.utilisateur=user;
        this.nomAbonnement = nomAbonnement;
        this.dateDebut = dateDebut;
        this.dateFin = dateDebut.plusDays(offre.getDuree());
        this.Status = status;
    }
    public Abonnement( int idAbonnement,String nomAbonnement, String nomOffre,int idOffre,String nomSalle,LocalDate dateDebut,LocalDate dateFin, String status) {
        this.idAbonnement = idAbonnement;
        this.nomAbonnement = nomAbonnement;
        this.nomOffre = nomOffre;
        this.idOffre = idOffre;
        this.nomSalle = nomSalle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.Status = status;
    }
    public Abonnement( int idAbonnement,String NomClient,String nomAbonnement, String nomOffre,int idOffre,LocalDate dateDebut,LocalDate dateFin, String status,Double prixOffre) {
        this.idAbonnement = idAbonnement;
        this.NomClient=NomClient;
        this.nomAbonnement = nomAbonnement;
        this.nomOffre = nomOffre;
        this.idOffre = idOffre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.Status = status;
        this.prixOffre=prixOffre;
    }

    public double getPrixOffre() {
        return prixOffre;
    }

    public void setPrixOffre(double prixOffre) {
        this.prixOffre = prixOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public int getIdOffre() {
        return idOffre;
    }
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut,int duree) {
        this.dateDebut = dateDebut;
        this.dateFin = dateDebut.plusDays(duree);
    }
    public void settDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public void setDateDebutUpdate(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }

    public int getIdAbonnement() {
        return idAbonnement;
    }
   public void setIdAbonnement(int idAbonnement) {
        this.idAbonnement = idAbonnement;
    }


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getNomOffre() {
        return nomOffre;
    }

    public void setNomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
    }

    public String getNomAbonnement() {
        return nomAbonnement;
    }

    public void setNomAbonnement(String nomAbonnement) {
        this.nomAbonnement = nomAbonnement;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNomClient() {
        return NomClient;
    }

    public void setNomClient(String nomClient) {
        NomClient = nomClient;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Abonnement{" +
                " nom offre=" + nomOffre +
                ", nomAbonnement='" + nomAbonnement + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", Status='" + Status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Abonnement that)) return false;
        return idAbonnement == that.idAbonnement;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idAbonnement);
    }
}
