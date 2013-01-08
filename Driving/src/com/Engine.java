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

	public void run() {
	
		while(true){
			
			carFrame.up();
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
