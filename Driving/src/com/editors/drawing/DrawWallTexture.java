package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawWallTexture {
	
	private static final Color BRICK_COLOR=new Color(213,88,42);	
	private static final Color MORTAR_COLOR=new Color(204,204,204);


	/**

	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DrawWallTexture dwt=new DrawWallTexture();
		dwt.drawBrickWall(120,120,6);
	}

	/**
	 * 
	 * @param w must be even
	 * @param h must be divided by 4
	 * 
	 * @param MORTAR_THICKNESS must be even
	 */
	private void drawBrickWall(int w,int h,int MORTAR_THICKNESS) {
		
		
		/*
		 * int w=(int) 2*(BRICK_LENGTH+MORTAR_THICKNESS);
		 * int h=(int) 4*(BRICK_HEIGHT+MORTAR_THICKNESS);
		 */
		
		int BRICK_LENGTH=(int) (w*0.5-MORTAR_THICKNESS);
		int BRICK_HEIGHT=(int) (h*0.25-MORTAR_THICKNESS);
		

		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);
		
		File file=new File("wall_texture.gif");
		
		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
			
			int HALF_BRICK_LENGTH=(int) (BRICK_LENGTH*0.5);
			
			int x=0;
			int y=h;
			
			for (int i = 0; i < 4; i++) {
				
				if(i%2==0){
					
					y-=BRICK_HEIGHT;
					bufGraphics.setColor(BRICK_COLOR);
					bufGraphics.fillRect(x,y,BRICK_LENGTH,BRICK_HEIGHT);			
					bufGraphics.setColor(MORTAR_COLOR);
					bufGraphics.fillRect(x+BRICK_LENGTH,y,MORTAR_THICKNESS,BRICK_HEIGHT);			
					bufGraphics.setColor(BRICK_COLOR);
					bufGraphics.fillRect(x+BRICK_LENGTH+MORTAR_THICKNESS,y,BRICK_LENGTH,BRICK_HEIGHT);
					
					bufGraphics.setColor(MORTAR_COLOR);
					bufGraphics.fillRect(x+2*BRICK_LENGTH+MORTAR_THICKNESS,y,MORTAR_THICKNESS,BRICK_HEIGHT);
					
					y=y-MORTAR_THICKNESS;
					bufGraphics.setColor(MORTAR_COLOR);
					bufGraphics.fillRect(x,y,w,MORTAR_THICKNESS);
					
				}else if(i%2==1){
				
					y=y-BRICK_HEIGHT;
					bufGraphics.setColor(BRICK_COLOR);
					bufGraphics.fillRect(x,y,HALF_BRICK_LENGTH,BRICK_HEIGHT);
					bufGraphics.setColor(MORTAR_COLOR);
					bufGraphics.fillRect(x+HALF_BRICK_LENGTH,y,MORTAR_THICKNESS,BRICK_HEIGHT);
					bufGraphics.setColor(BRICK_COLOR);
					bufGraphics.fillRect(x+HALF_BRICK_LENGTH+MORTAR_THICKNESS,y,BRICK_LENGTH,BRICK_HEIGHT);
					
					bufGraphics.setColor(MORTAR_COLOR);
					bufGraphics.fillRect(x+HALF_BRICK_LENGTH+BRICK_LENGTH+MORTAR_THICKNESS,y,MORTAR_THICKNESS,BRICK_HEIGHT);
					
					bufGraphics.setColor(BRICK_COLOR);
					bufGraphics.fillRect(x+HALF_BRICK_LENGTH+BRICK_LENGTH+2*MORTAR_THICKNESS,y,HALF_BRICK_LENGTH,BRICK_HEIGHT);
					
					y=y-MORTAR_THICKNESS;
					bufGraphics.setColor(MORTAR_COLOR);
					bufGraphics.fillRect(x,y,w,MORTAR_THICKNESS);
				}
				
			}
			
	
	

			
			ImageIO.write(buf,"gif",file);
			
			System.out.println("DONE");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
}
