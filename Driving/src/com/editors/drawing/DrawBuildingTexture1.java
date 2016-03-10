package com.editors.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.Polygon3D;

public class DrawBuildingTexture1 {

	public static void main(String[] args) {

		DrawBuildingTexture1 db=new DrawBuildingTexture1();
		db.draw();

	}

	private void draw() {

		int floor_height=82;
		int num_floors=2;


		int num_len_windows=2;		
		int num_wid_windows=1;

		int window_width=28;
		int window_height=44;		
		int window_lower_base=27;	
		

		int house_w=208;//num_wid_windows*window_width+(num_wid_windows+1)*windows_len_interspace;//	
		int house_l=286;//num_len_windows*window_width+(num_len_windows+1)*windows_len_interspace;//	
		int house_h=floor_height*num_floors;
		
		int windows_len_interspace=(house_w-num_wid_windows*window_width)/(num_wid_windows+1);
		int windows_wid_interspace=(house_l-num_len_windows*window_width)/(num_len_windows+1);
		
		int roof_heigth=47;


		int bx=10;
		int by=10;

		int w=2*(house_l+house_w)+2*bx;
		int h=house_l+house_w+house_h+roof_heigth+2*by;
		
		System.out.println("house_w="+house_w+"\nhouse_l="+house_l+"\nhouse_h="+house_h+"\nw="+w+"\nh="+h);

		BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED);

		File file=new File("texture.jpg");

		try {

			Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();

			bufGraphics.setColor(Color.GREEN);
			bufGraphics.fillRect(0,0,w,h);
			
			//BOTTOM FACE
			bufGraphics.setColor(Color.YELLOW);
			bufGraphics.drawRect(bx,h-(by+house_l),house_w,house_l);

			//TOP FACE
			bufGraphics.setColor(Color.YELLOW);
			bufGraphics.fillRect(bx+house_w,h-(by+house_h+house_w+roof_heigth+house_l),house_l,house_w/2);
			bufGraphics.setColor(Color.ORANGE);
			bufGraphics.fillRect(bx+house_w,h-(by+house_h+house_w/2+roof_heigth+house_l),house_l,house_w/2);	
			

			////BACK FACE
			bufGraphics.setColor(Color.CYAN);
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

					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x-window_width/2,h-yf0,window_width/2,window_height);

					bufGraphics.setColor(Color.BLACK);
					bufGraphics.fillRect(x,h-yf0,window_width,window_height);

					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x+window_width,h-yf0,window_width/2,window_height);

				}

			}

			////RIGHT FACE

			
			bufGraphics.setColor(Color.WHITE);
			bufGraphics.fillRect(bx+house_w,h-(by+house_h+house_l),house_l,house_h);

			for (int j = 0; j < num_floors; j++) {

				int x0=bx+house_w;
				int y0=by+house_l+floor_height*j;

				for (int i = 0; i < num_len_windows; i++) {

					int x=x0+i*windows_len_interspace+i*window_width+windows_len_interspace;
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


			bufGraphics.setColor(Color.LIGHT_GRAY);
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
			bufGraphics.fillRect(bx+house_w+house_l+house_w,h-(by+house_h+house_l),house_l,house_h);

			for (int j = 0; j < num_floors; j++) {

				int x0=bx+house_w+house_l+house_w;
				int y0=by+house_l+floor_height*j;
				

				for (int i = 0; i < num_len_windows; i++) {

					int x=x0+i*windows_len_interspace+i*window_width+windows_len_interspace;
					int y=y0+window_height+window_lower_base;
					

					bufGraphics.setColor(Color.RED);
					bufGraphics.fillRect(x-window_width/2,h-y,window_width/2,window_height);

					bufGraphics.setColor(Color.BLACK);
					bufGraphics.fillRect(x,h-y,window_width,window_height);

					bufGraphics.setColor(Color.RED);
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

}
