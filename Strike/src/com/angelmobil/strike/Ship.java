package com.angelmobil.strike;

import java.util.concurrent.CopyOnWriteArrayList;

import com.angelmobil.strike.Meteors.Meteor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Style;

public class Ship extends Thread {

		class Shot {
			int x, y;
			int direction, speed;

			Paint paint;
			public Shot() {
				paint = new Paint();
				paint.setARGB(255, (int) (Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
				paint.setStyle(Style.FILL);
			}
			
			public void move() {
				x = (int) (x - speed * Math.cos(Math.toRadians(direction+90)));
				y = (int) (y - speed * Math.sin(Math.toRadians(direction+90)));

				testAndDestroy();

				if (x > width || y > height || x < 0 || y < 0) {
					shots.remove(this);
				}
				

			}

			
			public void testAndDestroy() {
				for (Meteor meteor : Meteors.stack) {
					if (	meteor.x + meteor.size > x 
						&&  meteor.x - meteor.size < x 
						&&  meteor.y + meteor.size > y
						&&  meteor.y - meteor.size < y
						) {
						
						Meteors.stack.remove(meteor);
						
					}
				}
			}
			
			public void draw(Canvas canvas) {
				canvas.drawRect(x-5, y-5, x+5, y+5, paint);		
			}
			
		}
	
	
		int x, y;
		int direction, speed;
		int size;
		Paint paint;
		
		Bitmap bmpJet;
		Rect recJet;

		int width, height;
		Path path;
				
		public Ship(Context context) {
			
			x = width / 2;
			y = height / 2;
			
			size = (int) (Math.random() * 10) + 10;
			speed = (int) (Math.random() * 5);
			direction = (int) (Math.random() * 360);
			
			paint = new Paint();
			paint.setARGB(255, (int) (Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			paint.setStyle(Style.FILL);
			
		    //bmpJet = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		}
		
		public void move() {
			x = (int) (x - speed * Math.cos(Math.toRadians(direction+90)));
			y = (int) (y - speed * Math.sin(Math.toRadians(direction+90)));
			if (x > width) x = 0;
			if (y > height) y = 0;
			if (x < 0) x = width;
			if (y < 0) y = height;
		}
		
		public void draw(Canvas canvas) {
			// erase
			//if (recJet!=null) canvas.drawRect(recJet, getPaintBack());
		        
			//recJet = new Rect(x-10, y-10, x+10, y+10);
			//canvas.drawBitmap(bmpJet, null, recJet, null);	
			path = new Path();
			path.moveTo(x, y - 10);
			path.lineTo(x + 5, y + 10);
			path.lineTo(x - 5, y + 10);
			path.lineTo(x, y -10);

			Matrix matrix = new Matrix();
			matrix.reset();
			matrix.setRotate(direction, x, y);
			canvas.setMatrix(matrix);               
			canvas.drawPath(path, paint);
			canvas.setMatrix(null);

			
			for (Shot shot : shots) {
				shot.draw(canvas);
			}
		}
		
	
		CopyOnWriteArrayList<Shot> shots = new CopyOnWriteArrayList<Shot>();
		public void shot() {
			Shot shot = new Shot();
			shot.x = x;
			shot.y = y;
			shot.direction = direction;
			shot.speed = 10;
			shots.add(shot);
		}
		
		int counter = 0;
		boolean running;
		@Override
		public void run() {
			running = true;
			while(running) {
				
				if (counter % 10 == 0) move();
				
				for (Shot shot : shots) {
					shot.move();
				}
				
				try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		}
	
}
