package com;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;
import java.util.Vector;

public class LineData implements Cloneable{

		public Vector lineDatas=new Vector();
		public boolean isSelected=false;
		
		public double shadowCosin=1;
		
		public String data=null;
		
		public int texture_index=0;
		public String hexColor=GREEN_HEX;	
		
		public static String GREEN_HEX="00FF00";
				
		public int face=0;

		public int size(){

			return lineDatas.size();
		}

		public void addIndex(int n){
			lineDatas.add(new Integer(n));
		}

		public int getIndex(int i){
			return ((Integer)lineDatas.elementAt(i)).intValue();
		}
		
		
		public void setIndex(int i,int n){
			lineDatas.setElementAt(new Integer(n),i);
		}
		
		public String toString() {
			
			return decomposeLineData(this);
		}
		
		public String getHexColor() {
			return hexColor;
		}

		public void setHexColor(String hexColor) {
			this.hexColor = hexColor;
		}

		public int getTexture_index() {
			return texture_index;
		}

		public void setTexture_index(int texture_index) {
			this.texture_index = texture_index;
		}
		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public LineData(){}
		
		public LineData (int i, int j, int k, int l) {
			
			this.addIndex(i);
			this.addIndex(j);
			this.addIndex(k);
			this.addIndex(l);

			
		}
		
		public LineData (int i, int j, int k) {
			
			this.addIndex(i);
			this.addIndex(j);
			this.addIndex(k);
		
			
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		} 
		

		public LineData clone() {
		
			LineData ldnew=new LineData();
			
			for(int i=0;i<size();i++){
				
				ldnew.addIndex(getIndex(i));
			}
			ldnew.texture_index=texture_index;
			ldnew.shadowCosin=shadowCosin;
			
			return ldnew;
		}
		
		private String decomposeLineData(LineData ld) {

			String str="";
			
			if(data!=null)
				str+="["+data+"]";
			
			int size=ld.size();

			for(int j=0;j<size;j++){
				


				if(j>0)
					str+=",";
				str+=ld.getIndex(j);

			}

			return str;
		}

		public int positionOf(int i) {
			
			for(int j=0;j<size();j++){
				
				if(i==getIndex(j))
					return j;
			}
			
			return -1;
		}

		public double getShadowCosin() {
			return shadowCosin;
		}

		public void setShadowCosin(double shadowCosin) {
			this.shadowCosin = shadowCosin;
		}
		
		public static Polygon3D buildPolygon(LineData ld,Vector points) {



			int size=ld.size();

			int[] cxr=new int[size];
			int[] cyr=new int[size];
			int[] czr=new int[size];


			for(int i=0;i<size;i++){


				int num=ld.getIndex(i);

				Point3D p=(Point3D) points.elementAt(num);


				

				//real coordinates
				cxr[i]=(int)(p.x);
				cyr[i]=(int)(p.y);
				czr[i]=(int)(p.z);
				

				


			}


			Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

	        return p3dr;

		}
		
		public static Polygon3D buildPolygon(LineData ld,Point3D[] points) {



			int size=ld.size();

			int[] cxr=new int[size];
			int[] cyr=new int[size];
			int[] czr=new int[size];


			for(int i=0;i<size;i++){


				int num=ld.getIndex(i);

				Point3D p=points[num];


				

				//real coordinates
				cxr[i]=(int)(p.x);
				cyr[i]=(int)(p.y);
				czr[i]=(int)(p.z);
				

				


			}


			Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

	        return p3dr;

		}
		
		public static LineData[] divideIntoTriangles(LineData ld){
			
			if(ld.size()<3)
				return new LineData[0];
			
			LineData[] triangles=new LineData[ld.size()-2];
			
			if(ld.size()==3){
				triangles[0]=ld;
				return triangles;
				
			}
			
			for(int i=1;i<ld.size()-1;i++){
				
				LineData ldt=new LineData();
				
				
				ldt.addIndex(ld.getIndex(0));
				ldt.addIndex(ld.getIndex(i));
				ldt.addIndex(ld.getIndex(i+1));

				
				triangles[i-1]=ldt;
				
			}
			
			return triangles;
			
		}


		

		
	}