package tn.pidev.entities;
import java.util.Date;
import java.util.Objects;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Reclamation {
    private int id_reclamation;
    private int id_user;
    private String Description;
    private LocalDateTime date;
    private String statut;

    public Reclamation() {
    }

    public Reclamation(int id_user, String description, String statut) {
        this.id_user = id_user;
        Description = description;
        this.date = LocalDateTime.now();
        this.statut = statut;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDate(LocalDateTime date) { this.date = date; }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    public int getId_user() {
        return id_user;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    public int getId_reclamation() {
        return id_reclamation;
    }


    @Override
    public String toString() {
        return "Reclamation{" +
                ", id_user=" + id_user +
                ", Description='" + Description + '\'' +
                ", date=" + date +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reclamation that)) return false;
        return id_reclamation == that.id_reclamation;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_reclamation);
    }
}
