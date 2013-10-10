package com;

import com.main.CarFrame;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class Engine extends Thread{
	
	CarFrame carFrame;
	
	public static double dtt=0.034;
	
	public Engine(CarFrame carFrame) {
		super();
		this.carFrame = carFrame;
	}

	
	 private int startTime;  
     private int endTime;  
     private int frameTimes = 0;  
     private short frames = 0;  
	    
     public void run() {

    	 startTime = (int) System.currentTimeMillis(); 

    	 while(true){



    		 carFrame.up();

    		 endTime = (int) System.currentTimeMillis();  

    		 frameTimes = frameTimes + endTime - startTime;  
    		 startTime=endTime;
    		 frames++;  

    		 if(frameTimes >= 1000)  
    		 {  
  
    			 System.out.println("FPS:"+Long.toString(frames));  
    			 frames = 0;  
    			 frameTimes = 0;  
    		 } 

    		 try {
    			 //if(CarFrame.CAR_SPEED==0)
    			 //	break;
    			 //pause used to slow down the drawing process
    			 //Thread.sleep(25);
    		 } catch (Exception e) {

    			 e.printStackTrace();
    		 }



    	 }

     }

	

}
