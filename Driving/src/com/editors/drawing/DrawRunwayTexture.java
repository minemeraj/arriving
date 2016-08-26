package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawRunwayTexture {
	
	private static final Color TARMAC_ROAD_COLOR0 = new Color(86,87,82);
	private static final Color TARMAC_STRIP_COLOR0 = new Color(231,184,18);
	private static final int TARMAC_ROAD_SPAN=450;
	private static final int TARMAC_STRIP_WIDTH=20;
	
	private static final Color RUNWAY_ROAD_COLOR0 = new Color(60,59,64);
	private static final Color RUNWAY_STRIP_COLOR0 = new Color(225,233,236);
	private static final int RUNWAY_ROAD_SPAN=2000;
	private static final int RUNWAY_ROAD_BORDER=270;
	private static final int RUNWAY_STRIP_WIDTH=30;
	private static final int RUNWAY_STRIP_LENGTH=748;


	public static void main(String[] args) {
		
		DrawRunwayTexture drt=new DrawRunwayTexture();
		drt.drawTarmacRoad();
		drt.drawRunwayRoad();
	}

	private void drawTarmacRoad() {
		
		int w=TARMAC_ROAD_SPAN;
		int h=TARMAC_ROAD_SPAN;

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("tarmac_texture.jpg");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			bufGraphics.setColor(TARMAC_ROAD_COLOR0);
			bufGraphics.fillRect(0,0,w,h);
			
			bufGraphics.setColor(TARMAC_STRIP_COLOR0);
			//vertical strips
			int x=0;
			int y=0;
			bufGraphics.fillRect(x,y,TARMAC_STRIP_WIDTH,h);
			
			x=(int) ((TARMAC_ROAD_SPAN-TARMAC_STRIP_WIDTH)*0.5);
			bufGraphics.fillRect(x,y,TARMAC_STRIP_WIDTH,h);
			
			x=TARMAC_ROAD_SPAN-TARMAC_STRIP_WIDTH;
			bufGraphics.fillRect(x,y,TARMAC_STRIP_WIDTH,h);
			
			ImageIO.write(buf,"jpg",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	private void drawRunwayRoad() {
		
		int w=RUNWAY_ROAD_SPAN;
		int h=RUNWAY_ROAD_SPAN;

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("runway_texture.jpg");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			bufGraphics.setColor(RUNWAY_ROAD_COLOR0);
			bufGraphics.fillRect(0,0,w,h);
			
			bufGraphics.setColor(RUNWAY_STRIP_COLOR0);
			//vertical strips
			int x=RUNWAY_ROAD_BORDER;
			int y=0;
			bufGraphics.fillRect(x,y,RUNWAY_STRIP_WIDTH,h);
			
			int dy=(int) ((RUNWAY_ROAD_SPAN-RUNWAY_STRIP_LENGTH)*0.5);
			x=(int) ((RUNWAY_ROAD_SPAN-RUNWAY_STRIP_WIDTH)*0.5);
			bufGraphics.fillRect(x,y+dy,RUNWAY_STRIP_WIDTH,RUNWAY_STRIP_LENGTH);
			
			x=RUNWAY_ROAD_SPAN-RUNWAY_ROAD_BORDER-RUNWAY_STRIP_WIDTH;
			bufGraphics.fillRect(x,y,RUNWAY_STRIP_WIDTH,h);
			
			ImageIO.write(buf,"jpg",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
