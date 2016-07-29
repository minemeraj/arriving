package com;

import com.main.CarFrame;

/**
 * 
 * MAIN PROGRAM ENGINE, TRY TO SET A CONSTANT FRAME RATE OF 20
 * @author Piazza Francesco Giovanni ,Betacom s.r.l. http://www.betacom.it
 *
 */


public class Engine extends Thread{
	
	private CarFrame carFrame;
	
	public static double dtt=0.034;
	
	public Engine(CarFrame carFrame) {
		super();
		this.carFrame = carFrame;
	}

	
	 private long startTime;  
     private long endTime;  
     private long frameTimes = 0;  
     private int frames = 0;  
     
     private long MS_PER_FRAME=50;
     

     public void run() {

    	 startTime = System.currentTimeMillis(); 

    	 while(true){

    		 endTime =  System.currentTimeMillis();  

    		 carFrame.up();

    		 try {
    			 long delay=endTime + MS_PER_FRAME - System.currentTimeMillis();
    			 if(delay>0)
    				 sleep(delay);
    		 } catch (InterruptedException e) {
    		 }

    		 endTime =  System.currentTimeMillis();  

    		 frameTimes = frameTimes + endTime - startTime;  
    		 startTime=endTime;
    		 frames++;  

    		 if(frameTimes >= 1000)  
    		 {  
  
    			 System.out.println("FPS:"+Long.toString(frames));  
    			 frames = 0;  
    			 frameTimes = 0;  
    		 } 

    	



    	 }

     }

	

}
