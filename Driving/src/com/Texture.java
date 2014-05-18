package com;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.main.CarFrame;


public class Texture {

	
	int WIDTH;
	int HEIGHT;

	
	public Color background= CarFrame.BACKGROUND_COLOR;
	
	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

	int[] rgb;
	
	static double pi_2=Math.PI/2;
	
	public Texture(){		
	}
	
	public Texture(BufferedImage bi){
		
		HEIGHT=bi.getHeight();
		WIDTH=bi.getWidth();
		rgb=new int[WIDTH*HEIGHT];
			
		for(int i=0;i<WIDTH;i++)
			for(int j=0;j<HEIGHT;j++){
				rgb [i+WIDTH*j]=filterBackground(background,bi.getRGB(i,j));
				
		
			}	
		//analyzeImageBasic(bi);

	}
	
	private static int filterBackground(Color back,int rgbColor) {
		
		Color col=new Color(rgbColor);
		
		if(Math.abs(back.getRed()-col.getRed())<5 &&
				Math.abs(back.getGreen()-col.getGreen())<5 &&
						Math.abs(back.getBlue()-col.getBlue())<5 
				
				
		)
			return back.getRGB();
		
		return rgbColor;
	}
	
	public static BufferedImage invertVerticalImage(BufferedImage bi){
		
		BufferedImage nbi=new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
		
		for(int i=0;i<bi.getWidth();i++)
		  for(int j=0;j<bi.getHeight();j++){
			  
			  nbi.setRGB(i,j,bi.getRGB(i,bi.getHeight()-1-j));
		  }
		
		return nbi;
	}
	
	
	public int getRGB(int i,int j){

		return rgb[i+WIDTH*j];
		
	}

	public int getWidth() {
		return WIDTH;
	}


	public int getHeight() {
		
		return HEIGHT;
	}
	
	public static void main(String[] args) throws IOException {
		String fileBackground=null;
		//fileBackground="C:\\Documents and Settings\\Compaq_Proprietario\\workspace\\Driving\\lib\\road_texture_2.jpg";
		//String fileOrig="C:\\Documents and Settings\\Compaq_Proprietario\\Desktop\\new textures\\background_.jpg";
		//transformTexture(fileOrig,fileBackground);
		//analyzeImage(fileOrig);
		scanImagePoints("C:\\Users\\francescopiazza\\Desktop\\spec.GIF");
		
		
	}
	
	public static void transformTexture(String fileOrig,String fileBackground){
		
		File fileO=new File(fileOrig);
		String fileOut=fileO.getParent()+"\\tran_"+fileO.getName();
		
		BufferedImage bout;
		try {
			bout = transform(fileOrig,fileBackground);
			saveImage(bout,fileOut,"jpg");
		
		System.out.println("END");						
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private static BufferedImage transform(String fileOrig,String fileBackground) throws IOException {
	
		BufferedImage imageOrig=ImageIO.read(new File(fileOrig));
			
		
		int h=imageOrig.getHeight();
		int w=imageOrig.getWidth();
		
		BufferedImage bout=null;
		
		if(fileBackground!=null)
			bout=ImageIO.read(new File(fileBackground));
		else
			bout=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		
		int lenght=w*h;
		int[] rgb=new int[lenght];
		

		
		for(int i=0;i<w;i++)
			for(int j=0;j<h;j++){
				
				
				int iNew=calculateNewX(i,j,w,h);
				int jNew=calculateNewY(i,j,w,h);
				
				int argbs = 0;
				
				if(iNew<0 || jNew<0)
					{
					   if(fileBackground!=null)
						   argbs = bout.getRGB(i, j);
					   else
					       argbs = Color.WHITE.getRGB();
					}
				else
					argbs = imageOrig.getRGB(iNew, jNew);
				
				int alphas=0xff & (argbs>>24);
				int rs = 0xff & (argbs>>16);
				int gs = 0xff & (argbs >>8);
				int bs = 0xff & argbs;
								
				Color color=new Color(rs,gs,bs,alphas);
				rgb[i+j*w]=color.getRGB();
				
		}		
		
		
		bout.setRGB(0,0,w,h,rgb,0,w);
		return bout;
		
	}


	private static int calculateNewY(int i, int j, int w, int h) {
	
		int newJ=j;
		//int dy=h-1;
		//int newJ=dy-(int) Math.round(h/pi_2*Math.atan((dy-j)*1.0/(w-i)));
		//System.out.println(Math.atan((dy-j)/(w-i)));
		return newJ;
	}


	private static int calculateNewX(int i, int j, int w, int h) {
		
		int offset=w/2;
		//int dy=h-1;
		//int newI=(int) Math.round(w-Math.sqrt((dy-j)*(dy-j)+(w-i)*(w-i)));
		int newI=(i+offset)%w;
		if(newI<0)
			return -1;
			//newI+=w;
		return newI;
	}

	public static void scanImagePoints(String name) throws IOException{
		
		BufferedImage bi=ImageIO.read(new File(name));
		scanImagePoints(bi,Color.RED);
	}
	
	private static void scanImagePoints(BufferedImage bi,Color filter) {
		
		int filterRGB=filter.getRGB();
		
		int HEIGHT=bi.getHeight();
		int WIDTH=bi.getWidth();
		
	

		for(int j=0;j<HEIGHT;j++){
			
			boolean found=false;
			
			for(int i=0;i<WIDTH;i++){ 
			
							
				int rgb=bi.getRGB(i,j);
              
				if(rgb==filterRGB){
					
					System.out.print(i+","+j+"\t");
				    found=true;
				}
				
			}
			if(found)
				System.out.println();
		}	
	}

	public static void analyzeImage(String name) throws IOException{
		
		BufferedImage bi=ImageIO.read(new File(name));
		analyzeImage(bi);
	}
	
	public static void analyzeImageBasic(BufferedImage bi){
		
		
		
		int HEIGHT=bi.getHeight();
		int WIDTH=bi.getWidth();
		
		long max=0;
		long min=0;

		
		for(int i=0;i<WIDTH;i++)
			for(int j=0;j<HEIGHT;j++){
							
				int rgb=filterBackground(CarFrame.BACKGROUND_COLOR,bi.getRGB(i,j));
				
				if(i==0 && j==0){
					max=rgb;
					min=rgb;
					
				}
				
				if(rgb>max)
					max=rgb;
				
				if(rgb<min)
					min=rgb;
				
			}
		System.out.println("min rgb:"+min);
		System.out.println("max rgb:"+max);
	}	
	
	public static void analyzeImage(BufferedImage bi) throws IOException{
		
		
		
		int HEIGHT=bi.getHeight();
		int WIDTH=bi.getWidth();
		
			
		Hashtable readColors=new Hashtable();
		Hashtable countColors=new Hashtable();
		
		
		int counter=0;
		int[] rgbs = new int[WIDTH*HEIGHT];
		
		for(int i=0;i<WIDTH;i++)
			for(int j=0;j<HEIGHT;j++){
							
				rgbs [i+WIDTH*j]=filterBackground(CarFrame.BACKGROUND_COLOR,bi.getRGB(i,j));
				
		        if(readColors.get(rgbs [i+WIDTH*j])==null){
		        	readColors.put(rgbs [i+WIDTH*j],counter++);
		        }	
		        
		        if(countColors.get(rgbs [i+WIDTH*j])==null){
		        	countColors.put(rgbs [i+WIDTH*j],new Integer(1));
		        }
		        else{
		        	
		        	int pval=(Integer)countColors.get(rgbs [i+WIDTH*j]);
		        	countColors.put(rgbs [i+WIDTH*j],pval+1);
		        	
		        }
		        
		        
			}	


		int[] colors = new int[readColors.size()];
		String[] orderColors = new String[readColors.size()];
		
		Enumeration keys = readColors.keys();
		while(keys.hasMoreElements()){
			
			int rgb=(Integer)keys.nextElement();
			int val=(Integer)readColors.get(rgb);
			
			colors[val]=rgb;
			
			int num=(Integer)countColors.get(rgb);
			orderColors[val]=num+"_"+rgb;
			
		}
		
	
		System.out.println("Avarage pixels/colors:"+WIDTH*HEIGHT/colors.length+" ("+WIDTH*HEIGHT+"/"+colors.length+")");
		
		 //order colors:
		for (int i = 0; i < orderColors.length; i++) {
			for (int j = i+1; j < orderColors.length; j++) {
				
				int val1=Integer.valueOf(orderColors[i].substring(0,orderColors[i].indexOf("_")));
				int val2=Integer.valueOf(orderColors[j].substring(0,orderColors[j].indexOf("_")));
				
				if(val1<val2){
					
					String nval=orderColors[j];
					orderColors[j]=orderColors[i];
					orderColors[i]=nval;
					
				}
			}
		}
		for(int i=0;i<10 && i<orderColors.length;i++){
			
			System.out.println("Highest colors frequences "+(i+1)+":"+orderColors[i]);
		}
		//colors dictionary:
		/*  
		for(int i=0;i<WIDTH;i++)
			for(int j=0;j<HEIGHT;j++){
				
				int val=(Integer)readColors.get(rgbs [i+WIDTH*j]);
				rgbs [i+WIDTH*j]=val;
			}*/
	  
		
		
	}

	private static void saveImage(BufferedImage buf,String fileName,String type) throws IOException {
		
		File file=new File(fileName);
	
	
	
		
			ImageIO.write(buf,type,file);
			
		
	}
}
