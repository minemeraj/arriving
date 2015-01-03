package com;

import com.main.Renderer3D;

public class TextureBlock {
	
	public Point3D[][] upperBase=null;
	public Point3D[][] lowerBase=null;
	public Point3D[][] lateralFaces=null; 
	
	public int N_FACES=0;
	public double texture_side_dy;
	public double texture_side_dx;
	public int numx;
	public int numy;
	public int numz;
	
	boolean isDrawUpperBase=true;
	boolean isDrawLowerBase=true;
	public double len;
	public int vlen;
	
	public int entryIndex=0;
	public int exitIndex=0;
	
	public TextureBlock(int numx,int numy,int numz,
			double DX,double DY,double DZ,double x0,double y0,int entryIndex){
		
		this.numx=numx;
		this.numy=numy;
		this.numz=numz;
		
		this.entryIndex=entryIndex;
		
		N_FACES=numx*2+(numy-2)*2;
		
		len=2*(DX+DY);
		vlen=(int) (DZ+DY*2);
		
		texture_side_dy=(DY/(numy-1));
		texture_side_dx=(DX/(numx-1));
		
		upperBase=new Point3D[numx][numy];
		
		double baseY=DZ+DY;
		
		int count=entryIndex;
		
		
		for (int i = 0; i <numx; i++) {
			
			for (int j = 0; j < numy; j++) {
				
				double x=x0+texture_side_dx*i;
				double y=y0+texture_side_dy*j+baseY;
				
				upperBase[i][j]=new Point3D(x,y,0);
				upperBase[i][j].setData(new Integer(count++));
			}
			
		}
	

		
		double texture_side_dz= (DZ/(numz-1));
			
		lateralFaces=new Point3D[N_FACES+1][numz];

		for(int j=0;j<numz;j++){
			
			double y=y0+DY+texture_side_dz*j;

			//texture is open and periodical:
			
			double x=x0; 

			for (int i = 0; i <=N_FACES; i++) {

				lateralFaces[i][j]=new Point3D(x,y,0);
				lateralFaces[i][j].setData(new Integer(count++));
				
				double dx=texture_side_dx;
				
				if(i%(numx+numy-2)>=numx-1)
					dx=texture_side_dy;
				
				x+=dx;

			}
			
		}
		
		
		lowerBase=new Point3D[numx][numy];
		
		for (int i = 0; i <numx; i++) {
			
			for (int j = 0; j < numy; j++) {
				
				double x=x0+texture_side_dx*i;
				double y=y0+texture_side_dy*j;
				
				lowerBase[i][j]=new Point3D(x,y,0);
				lowerBase[i][j].setData(new Integer(count++));
			}
			
		}
		
		exitIndex=count;

	}
	
	
	public int[] lf(int i,int j,int k,int type){
		
		int n0=0;
		int n1=1;
		int n2=2;
		int n3=3;
		

		if(type==Renderer3D.CAR_LEFT || type==Renderer3D.CAR_FRONT){
	
			n0=1;
			n1=2;
			n2=3;
			n3=0;

		}

		
		int[] vals=new int [4];
		vals[0]=lf(i,j,k,n0,type);
		vals[1]=lf(i,j,k,n1,type);
		vals[2]=lf(i,j,k,n2,type);
		vals[3]=lf(i,j,k,n3,type);

		
		return vals;
		
	}
	
	public int lf(int i,int j,int k,int n,int type){
		
		int nx=0;
		int ny=0;
		
		
		if(type==Renderer3D.CAR_BACK){
			
			nx=i;
			ny=k;
			
			
		}else if(type==Renderer3D.CAR_RIGHT){
			
			nx=j;
			ny=k;
			
			
		}else if(type==Renderer3D.CAR_FRONT){
			
			nx=i;
			ny=k;
			
			nx=numx-2-i;
			
		}else if(type==Renderer3D.CAR_LEFT){
			
			nx=j;
			ny=k;
			nx=numy-2-nx;
			
		}else if(type==Renderer3D.CAR_TOP ||type==Renderer3D.CAR_BOTTOM  ){
			
			nx=i;
			ny=j;
			
		}
		
		
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
		
		if(type==Renderer3D.CAR_BACK){
			
			
		}else if(type==Renderer3D.CAR_RIGHT){
			
			deltax+=numx-1;
			
		}else if(type==Renderer3D.CAR_FRONT){
			
			deltax+=numx-1+numy-1;
			
		}else if(type==Renderer3D.CAR_LEFT){
			
			deltax+=2*(numx-1)+numy-1;
		}
		
		if(type==Renderer3D.CAR_TOP){
			
			Integer value=(Integer) upperBase[nx+deltax][ny+deltay].getData();
			return value.intValue();
			
		}else if(type==Renderer3D.CAR_BOTTOM){
			
			Integer value=(Integer) lowerBase[nx+deltax][ny+deltay].getData();
			return value.intValue();
		}
		else{
			Integer value=(Integer) lateralFaces[nx+deltax][ny+deltay].getData();
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
	
	
	/*int[][] upperFaceIndexes={
			
			{0,1,2,3,4},
			{5,6,7,8,9},
			{10,11,12,13,14},
			{15,16,17,18,19},
			{20,21,22,23,24},
			{25,26,27,28,29},
			{30,31,32,33,34},
			{35,36,37,38,39},
			{40,41,42,43,44}
			
	};

	int[][] lateralFaceIndexes={
			
			{45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69},
			{70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94},
			{95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119},
			{120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144},
			{145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169},
			{170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194},
			{195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219},
			{220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244},
			{245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269},
			
			
	};
	
	int[][] bottomFaceIndexes={
			
			{270,271,272,273,274},
			{275,276,277,278,279},
			{280,281,282,283,284},
			{285,286,287,288,289},
			{290,291,292,293,294},
			{295,296,297,298,299},
			{300,301,302,303,304},
			{305,306,307,308,309},
			{310,311,312,313,314},
			
	};*/

}
