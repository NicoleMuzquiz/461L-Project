package com.example.system;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.StackView;

/**
 * Created by einwo on 4/4/2017.
 */

public class CustomStackView extends StackView {

    float x1, x2, y1, y2, dx, dy;
    public CustomStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public CustomStackView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.onTouchEvent(event);

        switch(event.getAction()) {
            case(MotionEvent.ACTION_DOWN):
                x1 = event.getX();
                y1 = event.getY();
                break;
            case(MotionEvent.ACTION_MOVE):
            case(MotionEvent.ACTION_UP) :{
                x2 = event.getX();
                y2 = event.getY();
                dx = x2 - x1;
                dy = y2 - y1;

                // Use dx and dy to determine the direction
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {// direction = "right";
                        showNext();
                    } else {

                        showPrevious();


                    }

                }
            }
            // Log.v("hiiiiiiiiiii", direction+re);
        }
        return true;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stubLog.v("hiiiiiiiiiii","touched");
        Log.v("hiiiiiiiiiii","toucheddddddddd");
        //boolean re =false;


        return false;
    }
}
