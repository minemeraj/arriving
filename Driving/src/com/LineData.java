package com;

import java.util.ArrayList;

public class LineData implements Cloneable{

		public ArrayList lineDatas=new ArrayList();
		public boolean isSelected=false;
		
		private double shadowCosin=1;
		
		public String data=null;
		
		public int texture_index=0;
		public String hexColor=GREEN_HEX;	
		
		public static final String GREEN_HEX="00FF00";
		
		private boolean fillWithWater=false;

		public int size(){

			return lineDatas.size();
		}

		public void addIndex(int n){
			
			LineDataVertex ldv=new LineDataVertex();
			ldv.setVertex_index(n);
			
			lineDatas.add(ldv);
		}
		

		public void addIndex(int n, int tn, double ptx,double pty) {
			
			LineDataVertex ldv=new LineDataVertex();
			ldv.setVertex_index(n);
			ldv.setVertex_texture_index(tn);
			ldv.setVertex_texture_x(ptx);
			ldv.setVertex_texture_y(pty);
			
			lineDatas.add(ldv);
			
		}
		
		Point3D getVertexTexturePoint(int i){
			
			LineDataVertex ldv=getItem(i);
			
			return new Point3D(ldv.getVertex_texture_x(),ldv.getVertex_texture_y(),0);
		}

		public int getIndex(int i){
			
			LineDataVertex ldv=getItem(i);
			return ldv.getVertex_index();
		}
		
		public int getVertex_texture_index(int i){
			
			LineDataVertex ldv=getItem(i);
			return ldv.getVertex_texture_index();
		}
		
		public LineDataVertex getItem(int i){
			
			LineDataVertex ldv=(LineDataVertex) lineDatas.get(i);
			return ldv;
		}
		
		@Override
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
		
		@Override
		public LineData clone() {
		
			LineData ldnew=new LineData();
			
			for(int i=0;i<size();i++){
				
				Point3D pt=getVertexTexturePoint(i);
				
				ldnew.addIndex(getIndex(i),getVertex_texture_index(i),pt.x,pt.y);
			}
			ldnew.texture_index=texture_index;
			ldnew.shadowCosin=shadowCosin;
			ldnew.data=data;
			
			return ldnew;
		}
		
		private String decomposeLineData(LineData ld) {

			String str="";
			
			if(data!=null)
				str+="["+data+"]";
			
			int size=ld.size();

			for(int j=0;j<size;j++){
				


				if(j>0)
					str+=" ";
				
				LineDataVertex ldv=(LineDataVertex) ld.getItem(j);
				
				str+=ldv.getVertex_index()+"/"+ldv.getVertex_texture_index();

			}

			return str;
		}

		public double getShadowCosin() {
			return shadowCosin;
		}

		public void setShadowCosin(double shadowCosin) {
			this.shadowCosin = shadowCosin;
		}
		
		
		public static Polygon3D buildPolygon(LineData ld,Point3D[] points) {



			int size=ld.size();

			int[] cxr=new int[size];
			int[] cyr=new int[size];
			int[] czr=new int[size];
			
			int[] cxtr=new int[size];
			int[] cytr=new int[size];


			for(int i=0;i<size;i++){

				LineDataVertex ldv=(LineDataVertex) ld.getItem(i);
				int num=ldv.getVertex_index();
				int numt=ldv.getVertex_texture_index();
	
				Point3D p=points[num];

				//real coordinates
				cxr[i]=(int)(p.x);
				cyr[i]=(int)(p.y);
				czr[i]=(int)(p.z);
				
				cxtr[i]=(int) ldv.getVertex_texture_x();
				cytr[i]=(int) ldv.getVertex_texture_y();

			}


			Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr,cxtr,cytr);

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

		public boolean isFilledWithWater() {
			return fillWithWater;
		}

		public void setFilledWithWater(boolean hasWater) {
			this.fillWithWater = hasWater;
		}


		
	}