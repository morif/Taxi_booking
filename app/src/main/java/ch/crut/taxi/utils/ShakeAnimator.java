package ch.crut.taxi.utils;


import android.view.View;


public class ShakeAnimator extends com.daimajia.androidanimations.library.attention.ShakeAnimator {

    public void animate(View... targets) {
        for (View target : targets) {
            super.animate(target);
        }
    }
}
