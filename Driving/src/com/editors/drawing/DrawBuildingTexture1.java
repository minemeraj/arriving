package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.Polygon3D;

public class DrawBuildingTexture1 {	
	
	int num_len_windows=2;		
	int num_wid_windows=1;
	
	int window_width=28;
	int window_height=44;		
	int window_lower_base=27;	
	
	int min_tile_l=10;
	int min_tile_w=13;
	
	int bx=10;
	int by=10;
	
	Color BACKGROUND_COLOR=new Color(0, 255, 0);	
		
	Color[] WINDOW_COLORS={new Color(56, 89, 124),new Color(162, 162, 190),new Color(19, 24, 17)};
	Color WINDOW_COLOR=WINDOW_COLORS[0];
	
	Color[] SHUTTER_COLORS={new Color(17, 119, 54),new Color(129, 70, 36),new Color(113, 58, 51)};
	Color SHUTTER_COLOR=SHUTTER_COLORS[0];
		
	Color[] WALL_COLORS={new Color(253, 227, 170),new Color(250, 206, 229),new Color(200, 228, 188),new Color(207, 75, 54)};
	Color FRONT_WALL_COLOR=WALL_COLORS[0];
	Color BACK_WALL_COLOR=WALL_COLORS[0];
	Color LEFT_WALL_COLOR=WALL_COLORS[0];
	Color RIGHT_WAll_COLOR=WALL_COLORS[0];
	
	Color[] ROOF_COLORS={new Color(205, 153, 51),new Color(145, 11, 36),new Color(160, 81, 77),new Color(29, 78, 48)};
	Color ROOF_COLORO=ROOF_COLORS[0];
	Color ROOF_COLOR1=ROOF_COLORS[1];
	Color BOTTOM_COLOR=Color.YELLOW;


	public static void main(String[] args) {
		
		int house_w=208;	
		int house_l=286;
		int house_h=164;
		int num_floors=2;
		int roof_heigth=47;

		DrawBuildingTexture1 db=new DrawBuildingTexture1();
		db.draw(house_w,house_l,house_h,num_floors,roof_heigth);

	}

	private void draw(int house_w,int house_l,int house_h,int num_floors,int roof_heigth) {
		
		//typical floor_height=82
		int floor_height=house_h/num_floors;
		
		int windows_wid_interspace=(house_w-num_wid_windows*window_width)/(num_wid_windows+1);
		int windows_len_interspace=(house_l-num_len_windows*window_width)/(num_len_windows+1);

		int w=2*(house_l+house_w)+2*bx;
		int h=house_l+house_w+house_h+roof_heigth+2*by;
		
		int nWTile=2*(house_w/(2*min_tile_w));
		int nLTile=house_l/min_tile_l;
		double tile_w=house_w*1.0/(nWTile);
		double tile_l=house_l*1.0/(nLTile);
		
		System.out.println("house_w="+house_w+"\nhouse_l="+house_l+"\nhouse_h="+house_h+"\nw="+w+"\nh="+h);

		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);

		File file=new File("texture.jpg");

		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			bufGraphics.setColor(BACKGROUND_COLOR);
			bufGraphics.fillRect(0,0,w,h);
			
			//BOTTOM FACE
			bufGraphics.setColor(BOTTOM_COLOR);
			bufGraphics.drawRect(bx,h-(by+house_l),house_w,house_l);

			//TOP FACE
			bufGraphics.setColor(ROOF_COLORO);
			bufGraphics.fillRect(bx+house_w,h-(by+house_h+house_w+roof_heigth+house_l),house_l,house_w);
			bufGraphics.setColor(ROOF_COLOR1);
			bufGraphics.drawLine(
					bx+house_w, h-(by+house_h+house_w/2+roof_heigth+house_l),
					bx+house_w+house_l, h-(by+house_h+house_w/2+roof_heigth+house_l));
			for (int i = 0; i < nLTile; i++) {
				
				int x=(int) (bx+house_w+tile_l*i);
				
				for (int j = 0; j < nWTile; j++) {
					
					int y=h-(by+house_h+ (int)(tile_w*(j+1))+roof_heigth+house_l);
					
					//bufGraphics.drawRect(x, y, (int) tile_l,  (int)tile_w);
					if(j>=nWTile/2){
						bufGraphics.fillRect(x, y, (int) tile_l-2, 2);
						bufGraphics.fillRect(x, y, 2,  (int)tile_w-2);
					}else{
						bufGraphics.fillRect(x, y+(int)tile_w-2, (int) tile_l-2, 2);
						bufGraphics.fillRect(x, y+2, 2,  (int)tile_w-2);
					}
				}
			}
	
			

			////BACK FACE
			bufGraphics.setColor(BACK_WALL_COLOR);
			bufGraphics.fillRect(bx,h-(by+house_h+house_l),house_w,house_h);
			
			Polygon3D pb=new Polygon3D();
			pb.addPoint(bx, h-(by+house_h+house_l));
			pb.addPoint(bx+house_w/2, h-(by+house_h+roof_heigth+house_l));
			pb.addPoint(bx+house_w, h-(by+house_h+house_l));
			
			bufGraphics.fillPolygon(pb);
		
			for (int j = 0; j < num_floors; j++) {
				
				int x0=bx;
				int y0=by+house_l+floor_height*j;
		

				for (int i = 0; i < num_wid_windows; i++) {

					int x=x0+i*windows_wid_interspace+i*window_width+windows_wid_interspace;

					int yf0= y0+window_height+window_lower_base;

					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x-window_width/2,h-yf0,window_width/2,window_height);

					bufGraphics.setColor(WINDOW_COLOR);
					bufGraphics.fillRect(x,h-yf0,window_width,window_height);

					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x+window_width,h-yf0,window_width/2,window_height);

				}

			}

			////RIGHT FACE

			
			bufGraphics.setColor(RIGHT_WAll_COLOR);
			bufGraphics.fillRect(bx+house_w,h-(by+house_h+house_l),house_l,house_h);

			for (int j = 0; j < num_floors; j++) {

				int x0=bx+house_w;
				int y0=by+house_l+floor_height*j;

				for (int i = 0; i < num_len_windows; i++) {

					int x=x0+i*windows_len_interspace+i*window_width+windows_len_interspace;
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
			bufGraphics.fillRect(bx+house_w+house_l,h-(by+house_h+house_l),house_l,house_h);

			Polygon3D pf=new Polygon3D();
			pf.addPoint(bx+house_w+house_l, h-(by+house_h+house_l));
			pf.addPoint(bx+house_w+house_l+house_w/2, h-(by+house_h+roof_heigth+house_l));
			pf.addPoint(bx+house_w+house_l+house_w, h-(by+house_h+house_l));
			bufGraphics.fillPolygon(pf);

			for (int j = 0; j < num_floors; j++) {

				int x0=bx+house_l+house_w;
				int y0=by+house_l+floor_height*j;

				for (int i = 0; i < num_wid_windows; i++) {

					int x=x0+i*windows_wid_interspace+i*window_width+windows_wid_interspace;
					int y=y0+window_height+window_lower_base;

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
			bufGraphics.fillRect(bx+house_w+house_l+house_w,h-(by+house_h+house_l),house_l,house_h);

			for (int j = 0; j < num_floors; j++) {

				int x0=bx+house_w+house_l+house_w;
				int y0=by+house_l+floor_height*j;
				

				for (int i = 0; i < num_len_windows; i++) {

					int x=x0+i*windows_len_interspace+i*window_width+windows_len_interspace;
					int y=y0+window_height+window_lower_base;
					

					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x-window_width/2,h-y,window_width/2,window_height);

					bufGraphics.setColor(WINDOW_COLOR);
					bufGraphics.fillRect(x,h-y,window_width,window_height);

					bufGraphics.setColor(SHUTTER_COLOR);
					bufGraphics.fillRect(x+window_width,h-y,window_width/2,window_height);

				}

			}


			//////

			ImageIO.write(buf,"gif",file);

		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println("DRAWN");

	}

	public void setWINDOW_COLOR(Color wINDOW_COLOR) {
		WINDOW_COLOR = wINDOW_COLOR;
	}

	public void setSHUTTER_COLOR(Color sHUTTER_COLOR) {
		SHUTTER_COLOR = sHUTTER_COLOR;
	}

	public void setFRONT_WALL_COLOR(Color fRONT_WALL_COLOR) {
		FRONT_WALL_COLOR = fRONT_WALL_COLOR;
	}

	public void setLEFT_WALL_COLOR(Color lEFT_WALL_COLOR) {
		LEFT_WALL_COLOR = lEFT_WALL_COLOR;
	}

	public void setRIGHT_WAll_COLOR(Color rIGHT_WAll_COLOR) {
		RIGHT_WAll_COLOR = rIGHT_WAll_COLOR;
	}
	

	public void setBACK_WALL_COLOR(Color bACK_WALL_COLOR) {
		BACK_WALL_COLOR = bACK_WALL_COLOR;
	}

	public void setROOF_COLORO(Color rOOF_COLORO) {
		ROOF_COLORO = rOOF_COLORO;
	}

	public void setROOF_COLOR1(Color rOOF_COLOR1) {
		ROOF_COLOR1 = rOOF_COLOR1;
	}



}
