package studio.dex.tools;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/** on touch and move sprite sample.
 * Created by dexter on 2015/2/1.
 */
public class MoveSprite {
    ImageView view;

    private void touchMoveSample(){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float centerX = v.getWidth() / 2;
                float centerY = v.getHeight() / 2;
                float moveX = event.getX() - centerX;
                float moveY = event.getY() - centerY;
                float toX = v.getX() + moveX;
                float toY = v.getY() + moveY;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.setX(toX);
                        view.setY(toY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setX(toX);
                        view.setY(toY);
                        break;
                }

                return true;
            }
        });
    }
}
