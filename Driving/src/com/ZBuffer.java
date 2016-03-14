package com;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

public class ZBuffer{

		public int[] rgbColor;
		public double[] z;
		public int size=0;
		
		public int[] level;
		
		/*public double p_x=0;
		public double p_y=0;
		public double p_z=0;
		
		public double[] pcf_values=null;*/
		
		public ZBuffer(int size){
			
			this.size=size;
			rgbColor=new int[size];
			z=new double[size];
			level=new int[size];
		}

		
		public int getRgbColor(int index) {
			return rgbColor[index];
		}
		public void setRgbColor(int rgbColor,int index) {
			this.rgbColor[index] = rgbColor;
		}
		public double getZ(int index) {
			return z[index];
		}
		public void setZ(double z,int index) {
			this.z[index] = z;
			level[index]=0;
		}
		

		
		public static Color  fromHexToColor(String col){


			if(col==null || col.equals(""))
				return Color.WHITE;

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
		
		public static int  pickRGBColorFromTexture(
				Texture texture,double px,double py,double pz,
				Point3D xDirection,Point3D yDirection, Point3D origin,int deltaX,int deltaY,
				BarycentricCoordinates bc
				){
			
			
			int x=0;
			int y=0;
			
		
			
			if(bc!=null){
				
				
				
				Point3D point=new Point3D(px,py,pz);
				
				Point3D p=bc.getBarycentricCoordinates(point);
				
				Point3D p0=bc.pt0;
				Point3D p1=bc.pt1;
				Point3D p2=bc.pt2;
				
				x=(int) (p.x*(p0.x)+p.y*p1.x+(1-p.x-p.y)*p2.x);
				y=(int) (p.x*(p0.y)+p.y*p1.y+(1-p.x-p.y)*p2.y);
				
			}
			
			
			int w=texture.getWidth();
			int h=texture.getHeight();
			
			//border fixed condition
			/*if(x<0) x=0;
			if(x>=w) x=w-1;
			if(y<0) y=0;
			if(y>=h) y=h-1;*/
			
			//border periodic condition
			if(x<0) x=w-1+x%w;
			if(x>=w) x=x%w;
			if(y<0) y=h-1+y%h;
			if(y>=h) y=y%h;
			
		
			
			int argb= texture.getRGB(x, h-y-1);
			
			return argb;
			
		}
		
		/*public static Point3D  pickTexturePositionPCoordinates(Texture texture,double px,double py,double pz,Point3D xDirection,Point3D yDirection, Point3D origin,int deltaX,int deltaY){
			

			 
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
			//if(x<0) x=0;
			//if(x>=w)x=w-1;
			//if(y<0) y=0;
			//if(y>=h) y=h-1;

			//border periodic condition
			if(x<0) x=w-1+x%w;
			if(x>=w) x=x%w;
			if(y<0)	y=h-1+y%h;
			if(y>=h) y=y%h;

			
			Point3D point=new Point3D();
			point.x=x;
			point.y=h-y-1;//?
			return point;
			
			
		}*/
		
		public void update(double xs,double ys,double zs, int rgbColor,int index) {
			
			
			if(getZ(index)==0  ||  getZ(index)>ys ){

				setZ(ys,index);
				setRgbColor(rgbColor,index);
				
				/*p_x=xs;
				p_y=ys;
				p_z=zs;*/
	

			}

		}

		public boolean isToUpdate(double ys,int index){

          
			return getZ(index)==0 ||  getZ(index)>ys;
		}	

		public void set(double xs,double ys,double zs,double z, int rgbColor,int level,int index) {

			setZ(z,index);
			setRgbColor(rgbColor,index);
			setLevel(level,index);

			/*p_x=xs;
			p_y=ys;
			p_z=zs;*/

		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}

		public boolean isEmpty(int index){
			
			return level[index]==-1;
		}
		
		public void setLevel(int value,int index){
			
			level[index]=value;
		}

}
