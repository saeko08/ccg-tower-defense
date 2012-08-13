package com.ccgtowerdefense;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {
    GameView mGameView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onFindButtonClick(View view) {
    }

    public void onCreateButtonClick(View view) {
        mGameView = new GameView(this);
        setContentView(mGameView);
        mGameView.mThread.doStart();
    }

    public void onDeckButtonClick(View view) {
    }

    public void onCardsButtonClick(View view) {
    }

    // add this method in MainActivity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            // Pass over the control to our thread class
            mGameView.mThread.onTouch(event);
        } catch (Exception e) {
        }

        return true;
    }
}
