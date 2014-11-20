package ch.crut.taxi.utils;

import android.widget.CheckBox;

/**
 * Created by Alex on 18.11.2014.
 */
public class WishObjects {

    private int ImageViewResurs;
    private int wishNameResurse;
    private boolean isAddWish = false;

    public int getImageViewResurs() {

        return ImageViewResurs;
    }

    public void setImageViewResurs(int imageViewResurs) {

        ImageViewResurs = imageViewResurs;
    }

    public int getWishNameResurse() {

        return wishNameResurse;
    }

    public void setWishNameResurse(int wishNameResurse) {

        this.wishNameResurse = wishNameResurse;
    }

    public WishObjects(int imageViewResurse, int wishName){
    this.ImageViewResurs = imageViewResurse;
    this.wishNameResurse = wishName;
}


    public boolean isAddWish() {
        return isAddWish;
    }

    public void setAddWish(boolean isAddWish) {
        this.isAddWish = isAddWish;
    }
}
