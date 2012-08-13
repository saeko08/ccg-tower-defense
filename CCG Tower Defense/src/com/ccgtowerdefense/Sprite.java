/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccgtowerdefense;

/**
 *
 * @author Shinngelbach
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {
    // x,y coordinates  x,y velocities

    int x, y, vx, vy;
    private Bitmap texture; // A bitmap texture

    public Sprite(Bitmap texture) {
        this.texture = texture;
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    public void draw(Canvas c) {
        // Simply draw the object onto the canvas, at specified position
        c.drawBitmap(texture, x, y, null);
    }
}
