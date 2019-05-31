package com.github.shyamking.android_gravity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageView> stones;
    RelativeLayout container;
    boolean grav_down = true;
    DisplayMetrics display;
    int mHeight, mWidth;
    int sHeight, sWidth;
    boolean canClick = true;
    String TAG = "SHYAMDEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get height and width
        display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        mHeight = display.heightPixels;
        mWidth = display.widthPixels;

        //root view
        container = findViewById(R.id.container);

        //create stones
        int[] stoneImages = {
                R.drawable.stone_blue,
                R.drawable.stone_green,
                R.drawable.stone_yellow,
                R.drawable.stone_purple,
                R.drawable.stone_red
        };
        stones = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            stones.add(new ImageView(this));
            stones.get(i).setImageDrawable(getResources().getDrawable(stoneImages[i]));
        }

        //set
        sHeight = sWidth = (int)pxFromDp(40);


        for (int i = 0; i < 5; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(sWidth,sHeight);

            int rWidth = (int)(Math.random()*(mWidth - sWidth));
            lp.leftMargin = rWidth;
            lp.topMargin = mHeight - sHeight;
            container.addView(stones.get(i), lp);
        }

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canClick) {
                    grav_down = !grav_down;
                    TranslateAnimation anim;

                    //changing background
                    if (grav_down) {
                        container.setBackground(getResources().getDrawable(R.drawable.gravity_down));
                        anim = new TranslateAnimation(
                                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                                Animation.ABSOLUTE, 0, Animation.ABSOLUTE, mHeight - sHeight
                        );
                        anim.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                for (int i = 0; i < 5; i++) {
                                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) stones.get(i).getLayoutParams();
                                    lp.topMargin = mHeight - sHeight;
                                    stones.get(i).setLayoutParams(lp);
                                }
                                canClick = true;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    } else {
                        container.setBackground(getResources().getDrawable(R.drawable.gravity_up));
                        anim = new TranslateAnimation(
                                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                                Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -(mHeight - sHeight)
                        );
                        anim.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                for (int i = 0; i < 5; i++) {
                                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) stones.get(i).getLayoutParams();
                                    lp.topMargin = 0;
                                    stones.get(i).setLayoutParams(lp);
                                }
                                canClick = true;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                    anim.setDuration(1000);
                    anim.setInterpolator(new AccelerateInterpolator());
                    for (int i = 0; i < 5; i++)
                        stones.get(i).startAnimation(anim);
                }
                canClick = false;
            }
        });
    }

    float pxFromDp(float dp) {
        return display.densityDpi * dp / 160.0f;
    }
}
