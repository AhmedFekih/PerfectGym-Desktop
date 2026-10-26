package tn.pidev.entities;

import java.util.Objects;

public class Planning {
    private int idPlanning;
    private String nomPlanning;
    private String description;
    private String date;
    private String image;
    private String video;
    private String statut;

    public Planning() {
    }

    public Planning(String nomPlanning, String description, String date, String image, String video, String statut) {

        this.nomPlanning = nomPlanning;
        this.description = description;
        this.date = date;
        this.image = image;
        this.video = video;
        this.statut = statut;
    }

    public int getIdPlanning() {
        return idPlanning;
    }


    public String getNomPlanning() {
        return nomPlanning;
    }

    public void setNomPlanning(String nomPlanning) {
        this.nomPlanning = nomPlanning;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Planning{" +
                ", nomPlanning='" + nomPlanning + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Planning planning)) return false;
        return idPlanning == planning.idPlanning;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPlanning);
    }
}
