package tn.pidev.entities;

import java.util.Objects;

public class PlanAlimentaire {
    private int idMeal;
    private String description;
    private float protein;
    private float fat;
    private float carbohydrates;
    private String image;
    private String statut;

    public PlanAlimentaire() {
    }

    public PlanAlimentaire( String description, float protein, float fat, float carbohydrates, String image, String statut) {
        this.description = description;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.image = image;
        this.statut = statut;
    }

    public int getIdMeal() {
        return idMeal;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
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
        return "PlanAlimentaire{" +
                ", description='" + description + '\'' +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbohydrates=" + carbohydrates +
                ", image='" + image + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlanAlimentaire that)) return false;
        return idMeal == that.idMeal;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idMeal);
    }
}
