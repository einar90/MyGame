package com.example.mygame;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import sheep.game.Sprite;
import sheep.graphics.Color;


public class Helicopter extends Sprite {
	
	private Bitmap bitmap;
	private int nrOfSpriteFrames; // Dumber of subframes in the sprite picture
	private int currentSpriteFrame; // The sprite frame currently being displayed
	private int frameFrequency; // Time to display each frame
	private float lastFrameSetTime; // Last frame was set at ...
	private int spriteWidth, spriteHeight;
	private int canvasWidth, canvasHeight;
	private int xPos, yPos;
	private int xSpeed, ySpeed;
	private Rect spriteBox;
	private Rect positionBox;
	
	
	public Helicopter(Bitmap bitmap, int xPos, int yPos) {
		nrOfSpriteFrames = 4;
		frameFrequency = 100;
		currentSpriteFrame = 0;
		lastFrameSetTime = 0;
		xSpeed = ySpeed = 5;
		
		spriteWidth = bitmap.getWidth() / nrOfSpriteFrames;
		spriteHeight = bitmap.getHeight();
		this.bitmap = bitmap;
		flipBitmap();
		this.xPos = xPos;
		this.yPos = yPos;
		spriteBox = positionBox = new Rect(0, 0, spriteWidth, spriteHeight);
		this.setShape(spriteWidth, spriteHeight);
		
	}
	
	public void draw(Canvas canvas, int helicopterCounter) {
		canvasWidth = canvas.getWidth();
		canvasHeight = canvas.getHeight();
		canvas.drawBitmap(bitmap, spriteBox, positionBox, null);
		canvas.drawText("x: " + xPos + "; y: " + yPos, 20, 30 + 20*helicopterCounter, Color.BLACK);
		
	}
	
	public void update(float sysTime, ArrayList<Helicopter> helicopters) {
		if (sysTime < lastFrameSetTime + frameFrequency) return;
		if (currentSpriteFrame + 1 == nrOfSpriteFrames) currentSpriteFrame = 0; 
		else currentSpriteFrame++;
		lastFrameSetTime = sysTime;
		moveSprite(helicopters);
		
	}


	public void moveSprite(ArrayList<Helicopter> helicopters) {
		xPos = xPos + xSpeed;
		yPos = yPos + ySpeed;
		positionBox = new Rect(xPos, yPos, xPos + spriteWidth, yPos + spriteHeight);
		spriteBox.left = spriteWidth*currentSpriteFrame;
		spriteBox.right = spriteBox.left + spriteWidth;
		collisionCheck(helicopters);
	}
	
	private void collisionCheck(ArrayList<Helicopter> helicopters) {
		// Checking right and left walls
		if (positionBox.right > canvasWidth || positionBox.left < 0) {
			xSpeed = - xSpeed;
			flipBitmap();
		}
		
		// Checking top and bottom walls
		if (positionBox.bottom > canvasHeight || positionBox.top < 0) {
			ySpeed = - ySpeed;
		}
		
		// Checking for collisions with other helicopters
		for (Helicopter helicopter : helicopters) {
			if (helicopter.equals(this)) continue;
			
			if (this.positionBox.intersect(helicopter.positionBox)) {
				xSpeed = - xSpeed;
				ySpeed = - ySpeed;
				flipBitmap();
			}
		}
	}
	
	private void flipBitmap() {
		Matrix matrix = new Matrix();
		matrix.preScale(-1, 1);
		Bitmap dst = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
		bitmap = new BitmapDrawable(dst).getBitmap();
	}

	public void move(float x, float y) {
		int xCenter = (positionBox.right + positionBox.left) / 2;
		int yCenter = (positionBox.bottom + positionBox.top) / 2;
		
		if ((x > xCenter && xSpeed < 0)
				|| (x < xCenter && xSpeed > 0)) {
			flipBitmap();
			xSpeed = - xSpeed;
		}
		
		if ((y > yCenter && ySpeed < 0)
				|| (y < yCenter && ySpeed > 0)) {
			ySpeed = - ySpeed;
		}
		
	}

	

}
