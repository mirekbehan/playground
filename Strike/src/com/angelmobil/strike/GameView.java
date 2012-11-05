package com.angelmobil.strike;

import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.*;


public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback, OnTouchListener {
	
	Thread mThread;
	SurfaceHolder mSurfaceHolder;
	volatile boolean running = false;
	Context context;
	
	
	Meteors meteors;
	Ship ship;

	//Creates new surface view as well as a new surfaceholder, which allows access to the surface
	public GameView(Context context) {
		super(context);
		this.context = context;
	    mSurfaceHolder = getHolder();
	    mSurfaceHolder.addCallback(this);
	    	    
	    setOnTouchListener(this);
	    
	    //this.setZOrderOnTop(true);
	    //getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}
	
	

	public void resume(){
	    running = true;
	    mThread = new Thread(this);
	    mThread.start();

	    meteors = new Meteors(10);
	    meteors.start();
	    ship = new Ship(context);
	    ship.start();
	}

	public void pause(){
	       boolean retry = true;
	       running = false;
	       
	       meteors.running = false;
	       ship.running = false;
	       
	       while(retry){
	    	   try {
	    		   mThread.join();
	    		   retry = false;
	    	   } catch (InterruptedException e) {
	    		   // TODO Auto-generated catch block
	    		   e.printStackTrace();
	    	   }
	       }
	}

	
	int x,y=0;
	long timestamp;
	
	@Override
	public void run() {
		while(running){
	        if(mSurfaceHolder.getSurface().isValid()){
	            Canvas canvas = mSurfaceHolder.lockCanvas();
	            if (canvas!=null) {
	            drawBackground(canvas);
	            if (ship!=null) ship.draw(canvas);
	    		if (meteors!=null) meteors.draw(canvas);
	            
	            // draw control panel
	            canvas.drawCircle(50, height - 50, 50, getPaint());
	            canvas.drawCircle(width - 50, height - 50, 50, getPaint());
	            // draw touch point 
	            canvas.drawCircle(tx, ty, 10, getTextPaint());
	            canvas.drawCircle(tx1, ty1, 10, getTextPaint());
	            	            
	            // draw refresh rate and fps
	            long ms = (new Date().getTime() - timestamp);
	            long fps = 1000 / ms;
	            canvas.drawRect(0, 0, 100, 10, getPaint());
	            canvas.drawText(ms + " ms, " + fps + " fps" , 10, 10, getTextPaint());
	            timestamp = new Date().getTime();
	            
	            if (MainActivity.photo!=null) 
	            	canvas.drawBitmap(MainActivity.photo, null, new Rect(ship.x - 50, ship.y-110, ship.x + 50, ship.y-10), null);
	            }	            
	            mSurfaceHolder.unlockCanvasAndPost(canvas);
	        }
	    }
	}
	
	Rect controlMove,controlShot;
	
	public void drawBackground(Canvas canvas) {
        canvas.drawARGB(255, 0, 255, 80);
	}
	

	private Paint paintBack;
	public Paint getPaintBack() {
		if (paintBack==null) {
			paintBack = new Paint();
			paintBack.setColor(Color.BLACK);
			paintBack.setStyle(Style.FILL);
		}
		return paintBack;
	}

	
	private Paint paint;
	public Paint getPaint() {
		if (paint==null) {
			paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setStyle(Style.FILL_AND_STROKE);
		}
		return paint;
	}
	
	private Paint paintText;
	public Paint getTextPaint() {
		if (paintText==null) {
			paintText = new Paint();
			paintText.setColor(Color.RED);
			paintText.setStyle(Style.STROKE);
			paintText.setTextAlign(Align.LEFT);
		}
		return paintText;
	}

	
	int width, height;
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		this.width = width;
		this.height = height;
		controlMove = new Rect(0, height - 100, 100, height);
		controlShot = new Rect(width - 100, height - 100, width, height);
		meteors.setup(context, width,height);
		ship.setup(width,height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.resume();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.pause();
	}

	int tx,ty,tx1,ty1;
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//int firstIndex = event.getX(0) < event.getX(1) ? 0: 1;
		//int secondIndex = event.getX(0) < event.getX(1) ? 1: 0;

		if (event.getPointerCount() > 1 ) {
			tx1 = (int) event.getX(1);
			ty1 = (int) event.getY(1);
		} else {
			tx1 = 0;
			ty1 = 0;
		}
		tx = (int) event.getX();
		ty = (int) event.getY();
		// direction
		
		if (controlMove.contains(tx, ty) || controlMove.contains(tx1, ty1)) {
			float dx = tx - 50;
			float dy = ty - height + 50;
			double direction = dx / dy;
			direction = Math.atan(direction);
			direction = Math.toDegrees(direction);
			if (dx > 0 && dy < 0) direction = - direction;
			if (dx > 0 && dy > 0) direction = 180 - direction;
			if (dx < 0 && dy > 0) direction = 180 - direction;
			if (dx < 0 && dy < 0) direction = 360 - direction;
			
			ship.direction = (int) direction;
			ship.speed = (int) (Math.sqrt(dx*dx + dy*dy)/10);
		}

		
		
		//width - 50, height - 50, 50
		if (controlShot.contains(tx, ty) || controlShot.contains(tx1, ty1)) {
			ship.shot();
		}		
		
		return true;
	}

	

	
}
