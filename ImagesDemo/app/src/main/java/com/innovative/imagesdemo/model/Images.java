package com.innovative.imagesdemo.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by agicon06 on 11/10/17.
 */

public class Images implements Serializable {

    private String imagePath;

    public Images(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
