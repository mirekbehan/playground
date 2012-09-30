package com.angelmobil.strike;

import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Meteors extends Thread {

	
	
	class Meteor {
		int x, y;
		int direction, speed;
		int size;
		Paint paint;
		
		public Meteor() {
			x = (int) (Math.random() * width);
			y = (int) (Math.random() * height);
			size = (int) (Math.random() * 10) + 10;
			speed = (int) (Math.random() * 5);
			direction = (int) (Math.random() * 360);
			
			paint = new Paint();
			paint.setARGB(255, (int) (Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			paint.setStyle(Style.FILL);
		}
		
		public void move(int width, int height) {
			 x = (int) (x - speed * Math.cos(Math.toRadians(direction+90)));
			 y = (int) (y - speed * Math.sin(Math.toRadians(direction+90)));
			 if (x > width) x = 0;
		     if (y > height) y = 0;
		     if (x < 0) x = width;
		     if (y < 0) y = height;
		}
		
		public void draw(Canvas canvas) {
			canvas.drawCircle(x, y, size, paint);
		}
	}
	
	
	
	int width, height, count;
	public Meteors(int count) {
		this.count = count;
	}

	public void setup(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	static public CopyOnWriteArrayList<Meteor> stack = new CopyOnWriteArrayList<Meteor>();
	
	public void moveAll() {
		for (Meteor meteor: stack) {
			meteor.move(width, height);
		}
	}
	
	int counter = 0;
	boolean running;
	@Override
	public void run() {

		running = true;
		
		while(running) {
			
			counter++;
			
			if (stack.size() < count && counter % 100 == 0) {
				Meteor meteor = new Meteor();
				stack.add(meteor);
			}

			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			moveAll();
		}
	}

	
	public void draw(Canvas canvas) {
		for (Meteor meteor : stack) {
			meteor.draw(canvas);
		}
	}
	
}
