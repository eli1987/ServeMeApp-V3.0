package com.example.oryossipof.alphahotal;


import java.io.Serializable;

public class Dish  implements Serializable{

    public int dishId;
    public String dishName ;
    public String dishImg ;


    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public Dish(int dishId, String dishName, String dishImg) {
        this.dishId =dishId;

        this.dishName = dishName;
        this.dishImg = dishImg;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishImg() {
        return dishImg;
    }

    public void setDishImg(String dishImg) {
        this.dishImg = dishImg;
    }
}
