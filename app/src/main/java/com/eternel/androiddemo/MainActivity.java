package com.eternel.androiddemo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private float startY;
    private Top top;
    private ImageView image;
    private FrameLayout frame;
    private boolean threeSlide = true;
    private SparseArray<Float> points = new SparseArray<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top = findViewById(R.id.twolevel);
        image = findViewById(R.id.image);
        frame = findViewById(R.id.frame);
        frame.bringChildToFront(top);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        points.append(event.getPointerId(event.getActionIndex()), startY);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        int actionIndex = event.getActionIndex();
                        points.append(event.getPointerId(actionIndex), event.getY(actionIndex));
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (event.getPointerCount() == 3) {
                            for (int i = 0; i < event.getPointerCount(); i++) {
                                float endY = event.getY(i);
                                if (endY == points.get(event.getPointerId(i))) {
                                    threeSlide = false;
                                    break;
                                }
                            }
                            if (threeSlide) {
                                top.scrollView((int) startY, (int) event.getY());
                                startY = event.getY(event.getPointerId(0));
                            }
                        }

                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        points.remove(event.getPointerId(event.getActionIndex()));
                        break;
                    case MotionEvent.ACTION_UP:
                        points = new SparseArray<>();
                        break;
                    default:

                }
                return true;
            }
        });
        StatusBarUtil.immersive(this);
    }
}
