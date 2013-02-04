package com.example.mygame;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Color;
import sheep.input.TouchListener;

public class GameState extends State implements TouchListener {
	
	private ArrayList<Helicopter> helicopters= new ArrayList<Helicopter>();
	
	public GameState(Resources resources) {
		Helicopter helicopter = new Helicopter(BitmapFactory.decodeResource(resources, R.drawable.helicoptersheet), 10, 10);
		helicopters.add(helicopter);
		Helicopter helicopter2 = new Helicopter(BitmapFactory.decodeResource(resources, R.drawable.helicoptersheet), 10, 300);
		helicopters.add(helicopter2);
		Helicopter helicopter3 = new Helicopter(BitmapFactory.decodeResource(resources, R.drawable.helicoptersheet), 10, 600);
		helicopters.add(helicopter3);	
	}
	
	public void draw(Canvas canvas) {
		canvas.drawPaint(new Color(255, 0, 255));
		int helicopterCounter = 0;
		for (Helicopter helicopter : helicopters) {
			helicopter.draw(canvas, helicopterCounter++);			
		}
	}
	
	public void update(float dt) {
		for (Helicopter helicopter : helicopters) {
			helicopter.update(System.currentTimeMillis(), helicopters);			
		}
	}
	
	public boolean onTouchDown(MotionEvent event) {
		for (Helicopter helicopter : helicopters) {
			helicopter.move(event.getX(), event.getY());
		}
		return false;
	}

}
