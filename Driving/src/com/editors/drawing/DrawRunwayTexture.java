package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawRunwayTexture {
	
	private static final Color ROAD_COLOR0 = new Color(86,87,82);
	
	private static final Color STRIP_COLOR0 = new Color(231,184,18);
	
	private static final int ROAD_SPAN=450;
	
	private static final int STRIP_WIDTH=20;

	public static void main(String[] args) {
		
		DrawRunwayTexture drt=new DrawRunwayTexture();
		drt.drawTarmacLine();
		
	}

	
	
	private void drawTarmacLine() {
		
		int w=ROAD_SPAN;
		int h=ROAD_SPAN;

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("runway_texture.jpg");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			bufGraphics.setColor(ROAD_COLOR0);
			bufGraphics.fillRect(0,0,w,h);
			
			bufGraphics.setColor(STRIP_COLOR0);
			//vertical strips
			int x=0;
			int y=0;
			bufGraphics.fillRect(x,y,STRIP_WIDTH,h);
			
			x+=(ROAD_SPAN-STRIP_WIDTH)*0.5;
			bufGraphics.fillRect(x,y,STRIP_WIDTH,h);
			
			x=ROAD_SPAN-STRIP_WIDTH;
			bufGraphics.fillRect(x,y,STRIP_WIDTH,h);
			
			ImageIO.write(buf,"jpg",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
