package com;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class ZBuffer{

		int rgbColor;
		double z=0;
		
		public double p_x=0;
		public double p_y=0;
		public double p_z=0;
		
		public double[] pcf_values=null;

		
		public int getRgbColor() {
			return rgbColor;
		}
		public void setRgbColor(int rgbColor) {
			this.rgbColor = rgbColor;
		}
		public double getZ() {
			return z;
		}
		public void setZ(double z) {
			this.z = z;
		}
		
		public ZBuffer(int rgbColor, double z) {
			super();
			this.rgbColor = rgbColor;
			this.z = z;
		}
		public ZBuffer() {
			super();
		}
		
		public static Color  fromHexToColor(String col){



			int r=Integer.parseInt(col.substring(0,2),16);
			int g=Integer.parseInt(col.substring(2,4),16);
			int b=Integer.parseInt(col.substring(4,6),16);

			Color color=new Color(r,g,b);

			return color;
		}

		public static String fromColorToHex(Color col){

			String exe="";

			exe+=addZeros(Integer.toHexString(col.getRed()))
					+addZeros(Integer.toHexString(col.getGreen()))
					+addZeros(Integer.toHexString(col.getBlue()));

			return exe;

		}


		public static String addZeros(String hexString) {
			
			if(hexString.length()==1)
				return "0"+hexString;
			else 
				return hexString;
		}
		
		public static int  pickRGBColorFromTexture(Texture texture,double px,double py,double pz,Point3D xDirection,Point3D yDirection, Point3D origin,int deltaX,int deltaY){
			
			
			int x=0;
			int y=0;
			
			if(origin!=null){
				

				 x=(int) Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,xDirection)+deltaX;
				 y=(int) Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,yDirection)+deltaY;
				 
				 
			}
			else
			{
				
				  x=(int) Point3D.calculateDotProduct(px,py, pz,xDirection)+deltaX;
				  y=(int) Point3D.calculateDotProduct(px,py, pz,yDirection)+deltaY;
	
			}	
			
			
			int w=texture.getWidth();
			int h=texture.getHeight();
			
			//border fixed condition
			/*if(x<0) x=0;
			if(x>=w) x=w-1;
			if(y<0) y=0;
			if(y>=h) y=h-1;*/
			
			//border periodic condition
			if(x<0) x=w+x%w;
			if(x>=w) x=x%w;
			if(y<0) y=h+y%h;
			if(y>=h) y=y%h;
			
			int argb= texture.getRGB(x, h-y-1);
			
			return argb;
			
		}
		
		public static Point3D  pickTexturePositionPCoordinates(Texture texture,double px,double py,double pz,Point3D xDirection,Point3D yDirection, Point3D origin,int deltaX,int deltaY){
			

			 
			double x=0;
			double y=0;
			
			if(origin!=null){
				

				 x= Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,xDirection)+deltaX;
				 y= Point3D.calculateDotProduct(px-origin.x,py-origin.y, pz-origin.z,yDirection)+deltaY;


			}
			else
			{
				  x= Point3D.calculateDotProduct(px,py,pz,xDirection)+deltaX;
				  y= Point3D.calculateDotProduct(px,py,pz,yDirection)+deltaY;
	
			}	
			
			
			
			int w=texture.getWidth();
			int h=texture.getHeight();
			
			//border fixed conditions
			/*if(x<0) x=0;
			if(x>=w)x=w-1;
			if(y<0) y=0;
			if(y>=h) y=h-1;*/

			//border periodic condition
			if(x<0) x=w+x%w;
			if(x>=w) x=x%w;
			if(y<0)	y=h+y%h;
			if(y>=h) y=y%h;

			
			Point3D point=new Point3D();
			point.x=x;
			point.y=h-y-1;//?
			return point;
			
			
		}
		
		public void update(double xs,double ys,double zs, int rgbColor) {
			
			
			if(getZ()==0 ||  getZ()>ys ){

				setZ(ys);
				setRgbColor(rgbColor);
				
				p_x=xs;
				p_y=ys;
				p_z=zs;

			}

		}

		public boolean isToUpdate(double ys){


			return getZ()==0 ||  getZ()>ys;
		}	

		public void set(double xs,double ys,double zs, int rgbColor) {

			setZ(ys);
			setRgbColor(rgbColor);

			p_x=xs;
			p_y=ys;
			p_z=zs;
		}

}
