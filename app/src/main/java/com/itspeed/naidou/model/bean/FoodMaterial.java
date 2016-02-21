package com.itspeed.naidou.model.bean;

import java.io.Serializable;

/**
 * Created by jafir on 15/9/14.
 */
public class FoodMaterial implements Serializable{

    private String food;
    private String weight;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {

        this.weight = weight;
    }

    @Override
    public String toString() {
        return "FoodMaterial{" +
                "food='" + food + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
