package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DrawBuildingTexture0 {
	
	int window_width=28;
	int window_height=44;		
	int window_lower_base=27;	
	
	int num_len_windows=2;		
	int num_wid_windows=1;	

	int bx=10;
	int by=10;
	
	Color BACKGROUND_COLOR=new Color(0, 255, 0);	
	
	Color[] WINDOW_COLORS={new Color(162, 162, 190)};
	Color WINDOW_COLOR=WINDOW_COLORS[0];
	
	Color[] SHUTTER_COLORS={new Color(17, 119, 54)};
	Color SHUTTER_COLOR=SHUTTER_COLORS[0];
		
	Color[] WALL_COLORS={new Color(253, 227, 170)};
	Color FRONT_WALL_COLOR=WALL_COLORS[0];
	Color BACK_WALL_COLOR=WALL_COLORS[0];
	Color LEFT_WALL_COLOR=WALL_COLORS[0];
	Color RIGHT_WAll_COLOR=WALL_COLORS[0];
	
	Color[] ROOF_COLORS={new Color(205, 153, 51),new Color(145, 11, 36)};
	Color ROOF_COLORO=ROOF_COLORS[0];
	Color ROOF_COLOR1=ROOF_COLORS[1];
	Color BOTTOM_COLOR=Color.YELLOW;
	
	public static void main(String[] args) {
		
		DrawBuildingTexture0 db=new DrawBuildingTexture0();
		
		int house_w=208;	
		int house_l=286;
		int house_h=164;
		int num_floors=2;
		
		db.draw(house_w,house_l,house_h,num_floors);
		
	}

	private void draw(int house_w,int house_l,int house_h,int num_floors) {
		
		int floor_height=120;
		
		int windows_wid_interspace=(house_w-num_wid_windows*window_width)/(num_wid_windows+1);
		int windows_len_interspace=(house_l-num_len_windows*window_width)/(num_len_windows+1);	

		
		int w=house_h*2+house_l+2*bx;
		int h=house_w+2*house_h+2*by;
		
		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);

		File file=new File("texture.jpg");

		try {
			
			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			bufGraphics.setColor(BACKGROUND_COLOR);
			bufGraphics.fillRect(0,0,w,h);
			
			//TOP FACE
			bufGraphics.setColor(ROOF_COLORO);
			bufGraphics.fillRect(bx+house_h,h-(by+house_h+house_w),house_l,house_w/2);
			bufGraphics.setColor(ROOF_COLOR1);
			bufGraphics.fillRect(bx+house_h,h-(by+house_h+house_w/2),house_l,house_w/2);		
			
			////BACK FACE
			
			int x0=bx+house_h;
			bufGraphics.setColor(BACK_WALL_COLOR);
			bufGraphics.fillRect(x0,h-(by+house_h),house_l,house_h);
			
			for (int j = 0; j < num_floors; j++) {
				
				
				int y0=by+floor_height*j;

				for (int i = 0; i < num_len_windows; i++) {

					int x=x0+(i+1)*windows_len_interspace+i*window_width;
					int y=y0+window_height+window_lower_base;

					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x-window_width/2,h-y,window_width/2,window_height);

					bufGraphics.setColor(WINDOW_COLOR);
					bufGraphics.fillRect(x,h-y,window_width,window_height);

					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x+window_width,h-y,window_width/2,window_height);



				}

			}
			
			////FRONT FACE
			
		
			bufGraphics.setColor(FRONT_WALL_COLOR);
			bufGraphics.fillRect(x0,h-(by+house_h+house_w+house_h),house_l,house_h);
			
			for (int j = 0; j < num_floors; j++) {
				
				
				int y0=by+house_h+house_w+floor_height*(num_floors-j);

				for (int i = 0; i < num_len_windows; i++) {

					int x=x0+(i+1)*windows_len_interspace+i*window_width;
					int y=y0-window_lower_base;

					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x-window_width/2,h-y,window_width/2,window_height);

					bufGraphics.setColor(WINDOW_COLOR);
					bufGraphics.fillRect(x,h-y,window_width,window_height);

					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x+window_width,h-y,window_width/2,window_height);



				}

			}
			
			////LEFT FACE
			bufGraphics.setColor(LEFT_WALL_COLOR);
			bufGraphics.fillRect(bx,h-(by+house_h+house_w),house_h,house_w);
			
			for (int j = 0; j < num_floors; j++) {
				
				int xl0=bx+j*(floor_height)+window_lower_base;
				
				
				for (int i = 0; i < num_len_windows; i++) {
					
					int yl0=by+house_h+house_w-((i+1)*windows_wid_interspace+i*window_width);
					
					
					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(xl0,h-(yl0-window_width),window_height,window_width/2);
					
					bufGraphics.setColor(WINDOW_COLOR);
					bufGraphics.fillRect(xl0,h-yl0,window_height,window_width);
					
					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(xl0,h-(yl0+window_width/2),window_height,window_width/2);
					
				}
				
			}
			
			////RIGHT FACE
			bufGraphics.setColor(RIGHT_WAll_COLOR);
			bufGraphics.fillRect(bx+house_h+house_l,h-(by+house_h+house_w),house_h,house_w);
			
			for (int j = 0; j < num_floors; j++) {
				
				int xl0=bx+house_h+house_l+(j+1)*floor_height;				
				
				for (int i = 0; i < num_len_windows; i++) {
					
					int x=xl0-window_lower_base-window_height;
					
					int yl0=by+house_h+house_w-((i+1)*windows_wid_interspace+i*window_width);
					
					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x,h-(yl0-window_width),window_height,window_width/2);
					
					bufGraphics.setColor(WINDOW_COLOR);
					bufGraphics.fillRect(x,h-yl0,window_height,window_width);
					
					bufGraphics.setColor(SHUTTER_COLOR);
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
