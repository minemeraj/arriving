package com;

import com.main.Renderer3D;

public class TextureCylinder {
	
	public Point3D[] upperBase=null;
	public Point3D[][] lateralFaces=null;
	public Point3D[] lowerBase=null;
	; 
	
	public int N_FACES=0;
	public double texture_side_dy;
	public double texture_side_dx;
	
	int N_MERIDIANS=0;
	int N_PARALLELS=0;
	
	boolean isDrawUpperBase=true;
	boolean isDrawLowerBase=true;
	public double len;
	public int vlen;
	
	public int entryIndex=0;
	public int exitIndex=0;
	
	private double radius;
	private double zHeight;
	
	
	public TextureCylinder(int N_MERIDIANS,int N_PARALLELS,
			double radius,double base_radius,double zHeight,double x0,double y0,int entryIndex
			
			
			){
		
		this( N_MERIDIANS,N_PARALLELS,
				radius,base_radius, zHeight, x0, y0, entryIndex,true,true);
		
		
	}
	
	public TextureCylinder(int N_MERIDIANS,int N_PARALLELS,
			double radius,double base_radius,double zHeight,double x0,double y0,int entryIndex,
			boolean isDrawUpperBase,boolean isDrawLowerBase
			
			){
		
		this.N_MERIDIANS=N_MERIDIANS;
		this.N_PARALLELS=N_PARALLELS;
		
		this.entryIndex=entryIndex;
		
		this.isDrawUpperBase=isDrawUpperBase;
		this.isDrawLowerBase=isDrawLowerBase;
		
		this.radius=radius;
		this.zHeight=zHeight;
		
		int count=entryIndex;
				
		len=2*Math.PI*this.radius;		

		vlen=(int) (zHeight+(isDrawUpperBase?2*base_radius:0)+(isDrawLowerBase?2*base_radius:0));
		
		texture_side_dy=(zHeight/(N_PARALLELS-1));
		texture_side_dx=(len/N_MERIDIANS);
		
		double xc=x0+base_radius;
		double yc=y0+base_radius+zHeight+(isDrawLowerBase?2*base_radius:0);
		
		double teta=(2*Math.PI)/(N_MERIDIANS);
		
		if(isDrawUpperBase){
		
			upperBase=new Point3D[N_MERIDIANS];
			
			for (int j = 0; j <N_MERIDIANS; j++) {
				
	
				double x= xc+(base_radius*Math.cos(j*teta));
				double y= yc+(base_radius*Math.sin(j*teta));
	
				upperBase[j]=new Point3D(x,y,0);
				upperBase[j].setData(new Integer(count++));
	
			}
		
		}
		

		double dx=texture_side_dx;
		double dy=texture_side_dy;
		
		lateralFaces=new Point3D[N_PARALLELS][N_MERIDIANS+1];

		for(int j=0;j<N_PARALLELS;j++){

			//texture is open and periodical:

			for (int i = 0; i <=N_MERIDIANS; i++) {

				double x=x0+dx*i;
				double y=y0+(isDrawLowerBase?2*base_radius:0)+dy*j;

				lateralFaces[j][i]=new Point3D(x,y,0);
				lateralFaces[j][i].setData(new Integer(count++));
			}
			
		}	
		
		
		xc=x0+base_radius;
		yc=y0+base_radius;
		
		if(isDrawLowerBase){
			
			lowerBase=new Point3D[N_MERIDIANS];
		
			for (int j = N_MERIDIANS-1; j >=0; j--) {
				
				int jj=N_MERIDIANS-1-j;
	
				double x= xc+(base_radius*Math.cos(j*teta));
				double y= yc+(base_radius*Math.sin(j*teta));
	
				lowerBase[jj]=new Point3D(x,y,0);		
				lowerBase[jj].setData(new Integer(count++));
	
			}
		}
		
		exitIndex=count;

	}
	
	
	public int[] lf(int i,int k,int type){
		
		int n0=0;
		int n1=1;
		int n2=2;
		int n3=3;

		
		int[] vals=new int [4];
		vals[0]=lf(i,k,n0,type);
		vals[1]=lf(i,k,n1,type);
		vals[2]=lf(i,k,n2,type);
		vals[3]=lf(i,k,n3,type);

		
		return vals;
		
	}
	
	public int lf(int i,int k,int n,int type){
		
		int nx=i;
		int nz=k;
		
		int deltax=0;
		int deltay=0;
		
		if(n==1){
			deltax=1;
		} else if(n==2){
			deltax=1;
			deltay=1;
		}	
		else if(n==3){
			
			deltay=1;
		}

		
		if(type==Renderer3D.CAR_TOP){
			
			Integer value=(Integer) upperBase[nx].getData();
			return value.intValue();
			
		}else if(type==Renderer3D.CAR_BOTTOM){
			
			Integer value=(Integer) lowerBase[nx].getData();
			return value.intValue();
		}
		else{
			Integer value=(Integer) lateralFaces[nz+deltay][nx+deltax].getData();
			return value.intValue();
		}	
	}

	public boolean isDrawUpperBase() {
		return isDrawUpperBase;
	}

	public void setDrawUpperBase(boolean isDrawUpperBase) {
		this.isDrawUpperBase = isDrawUpperBase;
	}

	public boolean isDrawLowerBase() {
		return isDrawLowerBase;
	}

	public void setDrawLowerBase(boolean isDrawLowerBase) {
		this.isDrawLowerBase = isDrawLowerBase;
	}

	public double getLen() {
		return len;
	}

	public void setLen(double len) {
		this.len = len;
	}

	public int getVlen() {
		return vlen;
	}

	public void setVlen(int vlen) {
		this.vlen = vlen;
	}


}
