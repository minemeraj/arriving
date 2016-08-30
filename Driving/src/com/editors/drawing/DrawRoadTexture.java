package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawRoadTexture {
	
	private static final Color ROAD_COLOR0 = new Color(67,67,67);
	
	private static final Color STRIP_COLOR0 = Color.WHITE;
	
	private static final Color BORDER_STRIP_COLOR0 = new Color(231,184,18);
	
	private static final int STRIP_WIDTH = 5;
	private static final int PARKING_WIDTH = 60;
	private static final int PARKING_LENGTH = 120;
	private static final int PARKING_LINE_WIDTH= 78;
	
	private static final int ROAD_LINE_SPAN=150;
	private static final int ROAD_BORDER=70;
	private static final int ROAD_STRIP_WIDTH=5;
	private static final int ROAD_STRIP_LENGTH=50;

	public static void main(String[] args) {
		
		DrawRoadTexture drt=new DrawRoadTexture();
		drt.drawParking();
		drt.drawOneLineRoad();
		drt.drawTwoLineRoad();
		drt.drawThreeLineRoad();
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
	
	private void drawOneLineRoad() {
		
		int w=2*(ROAD_LINE_SPAN+ROAD_BORDER);
		int h=2*(ROAD_LINE_SPAN+ROAD_BORDER);

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("oneline_texture.jpg");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			bufGraphics.setColor(ROAD_COLOR0);
			bufGraphics.fillRect(0,0,w,h);			
			
			//vertical strips
			int x=ROAD_BORDER;
			int y=0;
			bufGraphics.setColor(BORDER_STRIP_COLOR0);
			bufGraphics.fillRect(x,y,ROAD_STRIP_WIDTH,h);
			
			int dy=(int) ((h-ROAD_STRIP_LENGTH)*0.5);
			x=(int) ((w-ROAD_STRIP_WIDTH)*0.5);
			bufGraphics.setColor(STRIP_COLOR0);
			bufGraphics.fillRect(x,y+dy,ROAD_STRIP_WIDTH,ROAD_STRIP_LENGTH);
			
			
			x=w-ROAD_BORDER-ROAD_STRIP_WIDTH;
			bufGraphics.setColor(BORDER_STRIP_COLOR0);
			bufGraphics.fillRect(x,y,ROAD_STRIP_WIDTH,h);
			
			ImageIO.write(buf,"jpg",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	private void drawTwoLineRoad() {
		
		int w=4*ROAD_LINE_SPAN+2*ROAD_BORDER;
		int h=4*ROAD_LINE_SPAN+2*ROAD_BORDER;

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("twoline_texture.jpg");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			bufGraphics.setColor(ROAD_COLOR0);
			bufGraphics.fillRect(0,0,w,h);			
			
			//vertical strips
			int x=ROAD_BORDER;
			int y=0;
			bufGraphics.setColor(BORDER_STRIP_COLOR0);
			bufGraphics.fillRect(x,y,ROAD_STRIP_WIDTH,h);
			
			int dy=(int) ((h-ROAD_STRIP_LENGTH)*0.5);
			bufGraphics.setColor(STRIP_COLOR0);
			
			x=(int) (ROAD_BORDER+ROAD_LINE_SPAN-ROAD_STRIP_WIDTH*0.5);			
			bufGraphics.fillRect(x,y+dy,ROAD_STRIP_WIDTH,ROAD_STRIP_LENGTH);
			
			x=(int) ((w-ROAD_STRIP_WIDTH)*0.5);			
			bufGraphics.fillRect(x,0,ROAD_STRIP_WIDTH,h);
			
			x=(int) (w-ROAD_BORDER-ROAD_LINE_SPAN-ROAD_STRIP_WIDTH*0.5);			
			bufGraphics.fillRect(x,y+dy,ROAD_STRIP_WIDTH,ROAD_STRIP_LENGTH);			
			
			x=w-ROAD_BORDER-ROAD_STRIP_WIDTH;
			bufGraphics.setColor(BORDER_STRIP_COLOR0);
			bufGraphics.fillRect(x,y,ROAD_STRIP_WIDTH,h);
			
			ImageIO.write(buf,"jpg",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	private void drawThreeLineRoad() {
		
		int w=6*ROAD_LINE_SPAN+2*ROAD_BORDER;
		int h=6*ROAD_LINE_SPAN+2*ROAD_BORDER;

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("threeline_texture.jpg");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			bufGraphics.setColor(ROAD_COLOR0);
			bufGraphics.fillRect(0,0,w,h);			
			
			//vertical strips
			int x=ROAD_BORDER;
			int y=0;
			bufGraphics.setColor(BORDER_STRIP_COLOR0);
			bufGraphics.fillRect(x,y,ROAD_STRIP_WIDTH,h);
			
			int dy=(int) ((h-ROAD_STRIP_LENGTH)*0.5);
			bufGraphics.setColor(STRIP_COLOR0);
			
			x=(int) (ROAD_BORDER+ROAD_LINE_SPAN-ROAD_STRIP_WIDTH*0.5);			
			bufGraphics.fillRect(x,y+dy,ROAD_STRIP_WIDTH,ROAD_STRIP_LENGTH);
			
			x=(int) (ROAD_BORDER+2*ROAD_LINE_SPAN-ROAD_STRIP_WIDTH*0.5);			
			bufGraphics.fillRect(x,y+dy,ROAD_STRIP_WIDTH,ROAD_STRIP_LENGTH);
			
			x=(int) ((w-ROAD_STRIP_WIDTH)*0.5);			
			bufGraphics.fillRect(x,0,ROAD_STRIP_WIDTH,h);
			
			x=(int) (w-ROAD_BORDER-2*ROAD_LINE_SPAN-ROAD_STRIP_WIDTH*0.5);			
			bufGraphics.fillRect(x,y+dy,ROAD_STRIP_WIDTH,ROAD_STRIP_LENGTH);
			
			x=(int) (w-ROAD_BORDER-ROAD_LINE_SPAN-ROAD_STRIP_WIDTH*0.5);			
			bufGraphics.fillRect(x,y+dy,ROAD_STRIP_WIDTH,ROAD_STRIP_LENGTH);			
			
			x=w-ROAD_BORDER-ROAD_STRIP_WIDTH;
			bufGraphics.setColor(BORDER_STRIP_COLOR0);
			bufGraphics.fillRect(x,y,ROAD_STRIP_WIDTH,h);
			
			ImageIO.write(buf,"jpg",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
