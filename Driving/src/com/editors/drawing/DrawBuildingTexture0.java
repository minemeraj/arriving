package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawBuildingTexture0 {
	
	public static void main(String[] args) {
		
		DrawBuildingTexture0 db=new DrawBuildingTexture0();
		db.draw();
		
	}

	private void draw() {
		
		int floor_height=120;
		int num_floors=2;
		
		
		int num_len_windows=2;		
		int num_lat_windows=2;
		
		int window_width=40;
		int window_height=50;		
		int window_lower_base=30;	
		int windows_interspace=60;
		
		int house_w=num_lat_windows*window_width+(num_lat_windows+1)*windows_interspace;	
		int house_l=num_len_windows*window_width+(num_len_windows+1)*windows_interspace;	
	
		int house_h=floor_height*num_floors;	
		
		
		int bx=10;
		int by=10;
		
		int w=house_h*2+house_l+2*bx;
		int h=house_w+2*house_h+2*by;
		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);

		File file=new File("texture.jpg");

		try {
			
			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			bufGraphics.setColor(Color.GREEN);
			bufGraphics.fillRect(0,0,w,h);
			
			//TOP FACE
			bufGraphics.setColor(Color.YELLOW);
			bufGraphics.fillRect(bx+house_h,h-(by+house_h+house_w),house_l,house_w/2);
			bufGraphics.setColor(Color.ORANGE);
			bufGraphics.fillRect(bx+house_h,h-(by+house_h+house_w/2),house_l,house_w/2);		
			
			////BACK FACE
			
			int x0=bx+house_h;
			bufGraphics.setColor(Color.WHITE);
			bufGraphics.fillRect(x0,h-(by+house_h),house_l,house_h);
			
			for (int j = 0; j < num_floors; j++) {
				
				
				int y0=by+floor_height*j;

				for (int i = 0; i < num_len_windows; i++) {

					int x=x0+(i+1)*windows_interspace+i*window_width;
					int y=y0+window_height+window_lower_base;

					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x-window_width/2,h-y,window_width/2,window_height);

					bufGraphics.setColor(Color.BLACK);
					bufGraphics.fillRect(x,h-y,window_width,window_height);

					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x+window_width,h-y,window_width/2,window_height);



				}

			}
			
			////FRONT FACE
			
		
			bufGraphics.setColor(Color.WHITE);
			bufGraphics.fillRect(x0,h-(by+house_h+house_w+house_h),house_l,house_h);
			
			for (int j = 0; j < num_floors; j++) {
				
				
				int y0=by+house_h+house_w+floor_height*(num_floors-j);

				for (int i = 0; i < num_len_windows; i++) {

					int x=x0+(i+1)*windows_interspace+i*window_width;
					int y=y0-window_lower_base;

					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x-window_width/2,h-y,window_width/2,window_height);

					bufGraphics.setColor(Color.BLACK);
					bufGraphics.fillRect(x,h-y,window_width,window_height);

					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x+window_width,h-y,window_width/2,window_height);



				}

			}
			
			////LEFT FACE
			bufGraphics.setColor(Color.MAGENTA);
			bufGraphics.fillRect(bx,h-(by+house_h+house_w),house_h,house_w);
			
			for (int j = 0; j < num_floors; j++) {
				
				int xl0=bx+j*(floor_height)+window_lower_base;
				
				
				for (int i = 0; i < num_len_windows; i++) {
					
					int yl0=by+house_h+house_w-((i+1)*windows_interspace+i*window_width);
					
					
					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(xl0,h-(yl0-window_width),window_height,window_width/2);
					
					bufGraphics.setColor(Color.BLACK);
					bufGraphics.fillRect(xl0,h-yl0,window_height,window_width);
					
					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(xl0,h-(yl0+window_width/2),window_height,window_width/2);
					
				}
				
			}
			
			////RIGHT FACE
			bufGraphics.setColor(Color.CYAN);
			bufGraphics.fillRect(bx+house_h+house_l,h-(by+house_h+house_w),house_h,house_w);
			
			for (int j = 0; j < num_floors; j++) {
				
				int xl0=bx+house_h+house_l+(j+1)*floor_height;				
				
				for (int i = 0; i < num_len_windows; i++) {
					
					int x=xl0-window_lower_base-window_height;
					
					int yl0=by+house_h+house_w-((i+1)*windows_interspace+i*window_width);
					
					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x,h-(yl0-window_width),window_height,window_width/2);
					
					bufGraphics.setColor(Color.BLACK);
					bufGraphics.fillRect(x,h-yl0,window_height,window_width);
					
					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x,h-(yl0+window_width/2),window_height,window_width/2);
					
				}
				
			}
			//////
			
			ImageIO.write(buf,"gif",file);

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		System.out.println("DRAWN");
		
	}

}
