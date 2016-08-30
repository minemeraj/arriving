package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawRailwayTexture {
	
	private static final Color RAIL_BALLAST_COLOR0 = new Color(86,87,82);
	private static final Color RAIL_COLOR0 = new Color(229,244,254);
	private static final int RAIL_GAUGE=180;
	private static final int RAIL_WIDTH=20;
	private static final int RAIL_BORDER_SPAN=100;
	
	private static final Color RAIL_TIE_COLOR0 = new Color(187,149,138);
	private static final int RAIL_TIE_WIDTH=300;

	public static void main(String[] args) {
		
		DrawRailwayTexture drt=new DrawRailwayTexture();
		drt.drawSingleRailRoad();
	}

	private void drawSingleRailRoad() {
		
		int w=RAIL_GAUGE+2*RAIL_WIDTH+2*RAIL_BORDER_SPAN;
		int h=RAIL_GAUGE+2*RAIL_WIDTH+2*RAIL_BORDER_SPAN;

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("rail_texture.jpg");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			bufGraphics.setColor(RAIL_BALLAST_COLOR0);
			bufGraphics.fillRect(0,0,w,h);
			
			int ties_number=4;
			int RAIL_TIE_INTERVAL=h/ties_number;
			int RAIL_TIE_LENGTH=(int) (RAIL_TIE_INTERVAL*0.25);
			
			for (int i = 0; i < ties_number; i++) {

				bufGraphics.setColor(RAIL_TIE_COLOR0);
				int x=(int) ((w-RAIL_TIE_WIDTH)*0.5);
				int y=h-RAIL_TIE_LENGTH-i*RAIL_TIE_INTERVAL;
				bufGraphics.fillRect(x,y,RAIL_TIE_WIDTH,RAIL_TIE_LENGTH);
			}
			
			
			bufGraphics.setColor(RAIL_COLOR0);
			//vertical strips
			int x=(int) ((w-RAIL_GAUGE)*0.5-RAIL_WIDTH);
			int y=0;
			bufGraphics.fillRect(x,y,RAIL_WIDTH,h);
			
			x=(int) ((w+RAIL_GAUGE)*0.5);
			bufGraphics.fillRect(x,y,RAIL_WIDTH,h);
			
			ImageIO.write(buf,"jpg",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
