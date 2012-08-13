/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccgtowerdefense;

/**
 *
 * @author Shinngelbach
 */
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private SurfaceHolder mHolder = null;  // Holder reference
    boolean running = false;               // exits the thread when set false
    private long mLastTime = 0;            // used for maintaining FPS
    private int width, height, fps, ifps;  // store width and height of canvas, also store
    // how many frames are rendered per second
    Sprite ball;
    Sprite bar;
    // declare paint object &amp; rect
    private Paint paint = new Paint();
    private Rect rect = new Rect(0, 0, width, height);

    public GameThread(SurfaceHolder surfaceholder, Context context, Handler handler) {
        this.mHolder = surfaceholder; // only store holder for now
        ifps = fps = 0;
        // add these lines in constructor
        ball = new Sprite(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ball));
        ball.x = 100;
        ball.y = 100;

        ball.vx = 12;
        ball.vy = 8;

        // set paint color to black
        paint.setColor(Color.BLACK);
        bar = new Sprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.bar));
        bar.x = 100;
    }

    // init vars here
    public void doStart() {
        synchronized (mHolder) {
            mLastTime = System.currentTimeMillis() + 100;
        }
    }

    @Override
    public void run() {
        while (running) {
            Canvas c = null;
            try {
                c = mHolder.lockCanvas(); // grab canvas from the holder
                if (width == 0) { // store screen width and height
                    width = c.getWidth();
                    height = c.getHeight();
                    // put this line in run() method after height assignment statement
                    bar.y = height - 100;
                }

                synchronized (mHolder) {
                    long now = System.currentTimeMillis();

                    update(); // this method updates game logic every frame
                    draw(c);  // this method renders the current frame

                    // logic for maintaining FPS
                    ifps++;
                    if (now > (mLastTime + 1000)) {
                        mLastTime = now;
                        fps = ifps;
                        ifps = 0;
                    }
                }
            } finally {
                if (c != null) {
                    // finally whatever the scenario must release the canvas
                    mHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    private void update() {
        // add these lines in update() method
        ball.x += ball.vx;
        ball.y -= ball.vy;

        // invert vx if ball crosses x-axis boundaries
        if (ball.x < 0 || ball.x + ball.getWidth() > width) {
            ball.vx = -ball.vx;
        }

        // invert vy if ball crosses y-axis boundaries
        if (ball.y < 0 || ball.y + ball.getHeight() > height) {
            ball.vy = -ball.vy;
        }

        // add these lines in update() method
        if (ball.y + ball.getHeight() >= bar.y) {
            if (ball.x + ball.getWidth() > bar.x && ball.x < bar.x + bar.getWidth()) {
                ball.vy = -ball.vy;
            }
        }
    }

    private void draw(Canvas c) {
        // finally add this line in draw() method
        c.drawColor(Color.BLACK);
        c.drawRect(rect, paint);
        ball.draw(c);
        bar.draw(c);
    }

    public void onTouch(MotionEvent event) {
        // set the bar's position to where the user has touched
        if (bar != null) {
            bar.x = (int) (event.getX() - bar.getWidth() / 2);
        }
    }
}
