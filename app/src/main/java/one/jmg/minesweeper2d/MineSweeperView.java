/*
 * Copyright (C) 2024 Joe George
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package one.jmg.minesweeper2d;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import one.jmg.minesweeper2d.engine.MineField;

public class MineSweeperView extends View {
	private final Paint mPaint;
	private MineField mf = null;
	private Target target = null;

	private int width;
	private int height;

	public MineSweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStrokeWidth(2);
		mPaint.setTextSize(30);

		mf = new MineField(9,9,10);
		target = new Target(0,0);
	}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mf.gameState() == MineField.GameState.LOST) {
            mPaint.setColor(Color.RED);
            mPaint.setTextAlign(Align.CENTER);
            mPaint.setTextSize(100);
            canvas.drawText("BOOM!", (int)(width/2), (int)(height/2), mPaint);
            mPaint.setTextAlign(Align.LEFT);
            mPaint.setTextSize(30);
        } else if (mf.gameState() == MineField.GameState.WON) {
            mPaint.setColor(Color.RED);
            mPaint.setTextAlign(Align.CENTER);
            mPaint.setTextSize(100);
            canvas.drawText("YOU WON!", (int)(width/2), (int)(height/2), mPaint);
            mPaint.setTextAlign(Align.LEFT);
            mPaint.setTextSize(30);

        } else {
            canvas.drawColor(Color.BLACK);
            width = getWidth();
            height = getHeight();

            mPaint.setColor(Color.WHITE);
            canvas.drawLine((int)(width*.75), 0, (int)(width*.75), height, mPaint);
            int w = (int)((width*.75)/9);
            int h = (int)(height/9);
            for (int i = 1; i < 9; i++) {
                canvas.drawLine((int)(w*i), 0, (int)(w*i), height, mPaint);
                canvas.drawLine(0, h*i, (int)(width*.75), h*i, mPaint);
            }

            target.draw(canvas);
            mf.draw(canvas, mPaint);
        }
        mPaint.setColor(Color.GREEN);

        canvas.drawText("Time:", (int)(width*.75 + 20), (int)(height/2), mPaint);
        canvas.drawText("" + mf.getTime(), (int)(width*.75 + 20), (int)(height/2 - 50), mPaint);
        canvas.drawText("Flagged:", (int)(width*.75 + 20), (int)(height/2 - 100), mPaint);
        canvas.drawText("" + mf.getFlagged(), (int)(width*.75 + 20), (int)(height/2 - 150), mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            target.setX((int) event.getX());
            target.setY((int) event.getY());
            reveal();
            invalidate();
        }
        return super.onTouchEvent(event);
    }

	public void reveal() {
		int col = (int)(target.getX() / ((width*.75) / 9)) + 1;
		int row = (int)(target.getY() / (height / 9) ) + 1;
		mf.reveal(col, row);
	}

	public void flag() {
		int col = (int)(target.getX() / ((width*.75) / 9)) + 1;
		int row = (int)(target.getY() / (height / 9) ) + 1;
		mf.flag(col, row);		
	}

}
