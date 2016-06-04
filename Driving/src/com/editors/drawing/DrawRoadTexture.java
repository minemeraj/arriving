package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawRoadTexture {
	
	private static final Color ROAD_COLOR0 = new Color(67,67,67);
	
	private static final Color STRIP_COLOR0 = Color.WHITE;
	
	private static final int STRIP_WIDTH = 5;
	private static final int PARKING_WIDTH = 60;
	private static final int PARKING_LENGTH = 120;
	private static final int PARKING_LINE_WIDTH= 78;

	public static void main(String[] args) {
		
		DrawRoadTexture drt=new DrawRoadTexture();
		drt.drawParking();
		
	}

	
	
	private void drawParking() {
		
		int w=2*PARKING_LINE_WIDTH+3*STRIP_WIDTH+2*PARKING_LENGTH;
		int h=3*PARKING_WIDTH+3*STRIP_WIDTH;

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("road_texture.jpg");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			bufGraphics.setColor(ROAD_COLOR0);
			bufGraphics.fillRect(0,0,w,h);
			
			bufGraphics.setColor(STRIP_COLOR0);
			//vertical strips
			int x=PARKING_LINE_WIDTH;
			int y=0;
			bufGraphics.fillRect(x,y,STRIP_WIDTH,h);
			x+=STRIP_WIDTH+PARKING_LENGTH;
			bufGraphics.fillRect(x,y,STRIP_WIDTH,h);
			x+=STRIP_WIDTH+PARKING_LENGTH;
			bufGraphics.fillRect(x,y,STRIP_WIDTH,h);
			
			//horizontal strips
			x=PARKING_LINE_WIDTH;
			y=0;
			bufGraphics.fillRect(x,y,3*STRIP_WIDTH+2*PARKING_LENGTH,STRIP_WIDTH);
			y+=STRIP_WIDTH+PARKING_WIDTH;
			bufGraphics.fillRect(x,y,3*STRIP_WIDTH+2*PARKING_LENGTH,STRIP_WIDTH);
			y+=STRIP_WIDTH+PARKING_WIDTH;
			bufGraphics.fillRect(x,y,3*STRIP_WIDTH+2*PARKING_LENGTH,STRIP_WIDTH);
			
			ImageIO.write(buf,"gif",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
