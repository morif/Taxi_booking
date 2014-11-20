package ch.crut.taxi.utils;


public class WishObjects {

    private int ImageViewResurs;
    private int wishNameResurse;
    private boolean isAddWish;


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


    public WishObjects(int imageViewResurse, int wishName) {
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
