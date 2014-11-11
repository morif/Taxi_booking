package ch.crut.taxi.interfaces;


import ch.crut.taxi.utils.NavigationPoint;

public interface OnPlaceSelectedListener {


    /**
     * @param navigationPoint {@link ch.crut.taxi.utils.NavigationPoint}
     * @param selectedKey     {@link OnPlaceSelectedListener int value}
     */
    public void placeSelected(NavigationPoint navigationPoint, PlaceSelectedKeys selectedKey);

    public static enum PlaceSelectedKeys {
        ORIGINAL, DESTINATION
    }

    public static final String PLACE_SELECTED_KEY_SERIALIZABLE = "PLACE_SELECTED_KEY_SERIALIZABLE";
}
