package com;

public class TextureBlock {
	
	public Point3D[][] upperBase=null;
	public Point3D[][] lowerBase=null;
	public Point3D[][] lateralFaces=null; 
	
	public int N_FACES=0;
	public int texture_side_dy;
	public int texture_side_dx;
	
	public TextureBlock(int numx,int numy,int numz,
			double DX,double DY,double DZ,double x0,double y0){
		
		N_FACES=numx*2+(numy-2)*2;
		
		texture_side_dy=(int) (DY/(numy-1));
		texture_side_dx=(int) (DX/(numx-1));
		
		upperBase=new Point3D[numx][numy];
		
		double baseY=DZ+DY;
		
		for (int i = 0; i <numx; i++) {
			
			for (int j = 0; j < numy; j++) {
				
				double x=x0+texture_side_dx*i;
				double y=y0+texture_side_dy*j+baseY;
				
				upperBase[i][j]=new Point3D(x,y,0);
			}
			
		}
	
		
		lowerBase=new Point3D[numx][numy];
		
		for (int i = 0; i <numx; i++) {
			
			for (int j = 0; j < numy; j++) {
				
				double x=x0+texture_side_dx*i;
				double y=y0+texture_side_dy*j;
				
				lowerBase[i][j]=new Point3D(x,y,0);
			}
			
		}
		
		double texture_side_dz=(int) (DZ/(numz-1));
			
		lateralFaces=new Point3D[N_FACES+1][numz];

		for(int j=0;j<numz;j++){

			//texture is open and periodical:
			
			double x=x0; 

			for (int i = 0; i <=N_FACES; i++) {

			
				double y=y0+DY+texture_side_dz*j;

				lateralFaces[i][j]=new Point3D(x,y,0);
				
				double dx=texture_side_dx;
				
				if(i%(numx+numy-2)>=numx-1)
					dx=texture_side_dy;
				
				x+=dx;

			}
			
		}	
	}

}
