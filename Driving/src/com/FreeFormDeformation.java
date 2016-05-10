package com;

public class FreeFormDeformation {

	int l;
	int m;
	int n;
	
	double deltax;
	double deltay;
	double deltaz;

	//lattice coordinates in [0,1]
	Point3D[][][] normalizedControlPoints=null;
	
	Point3D origin=null;
	
	boolean debug=false;


	public FreeFormDeformation(int l, int m, int n,double deltax,double deltay,double deltaz, Point3D[][][] nonNormalizedControlPoints,Point3D origin) {
		super();
		this.l = l;
		this.m = m;
		this.n = n;		
		this.origin = origin;
		
		this.deltax = deltax;
		this.deltay = deltay;
		this.deltaz = deltaz;	
		
		
		this.normalizedControlPoints =  new Point3D[l+1][m+1][n+1];
		
		for (int i = 0; i <= l; i++) {
			for (int j = 0; j <= m; j++) {
				for (int k = 0; k <= n; k++) {
					
					Point3D nnp = nonNormalizedControlPoints[i][j][k];
					
					this.normalizedControlPoints[i][j][k]=
							new Point3D(
								(nnp.x-origin.x)/deltax,			
								(nnp.y-origin.y)/deltay,
								(nnp.z-origin.z)/deltaz
					);
				}

			}
		}
	}

	public FreeFormDeformation(int l, int m, int n,double deltax,double deltay,double deltaz,Point3D origin) {

		this.l = l;
		this.m = m;
		this.n = n;
		this.normalizedControlPoints = new Point3D[l+1][m+1][n+1];
		this.origin = origin;
		
		this.deltax = deltax;
		this.deltay = deltay;
		this.deltaz = deltaz;

		for (int i = 0; i <= l; i++) {
			for (int j = 0; j <= m; j++) {
				for (int k = 0; k <= n; k++) {
					this.normalizedControlPoints[i][j][k]=
							new Point3D(
								i*1.0/l,			
								j*1.0/m,
								k*1.0/n
					);
				}

			}
		}
	}
	
	public Point3D getDeformedPoint(Point3D p){
		
		return getDeformedPoint( p,new Point3D(0,0,0));
	}
	
	public Point3D getDeformedPoint(Point3D p,Point3D origin){

		Point3D pComponent=new Point3D(
				
				(p.x-origin.x)/deltax,
				(p.y-origin.y)/deltay,
				(p.z-origin.z)/deltaz
		);

		Point3D pd=getDeformedPointComponent(pComponent);

		Point3D pDeformed=new Point3D(
				deltax*pd.x+origin.x,
				deltay*pd.y+origin.y,
				deltaz*pd.z+origin.z
		);

		return pDeformed;
	}


	public Point3D getDeformedPointComponent(Point3D p){

		Point3D pd=new Point3D();

		pd.setX(bernstein(p,0));
		pd.setY(bernstein(p,1));
		pd.setZ(bernstein(p,2));

		return pd;
	}

	private double bernstein(Point3D p, int coo) {


		double pol0=0;

		double s=p.x;
		double t=p.y;
		double u=p.z;

		for(int i=0;i<=l;i++){

			for(int j=0;j<=m;j++){

				for(int k=0;k<=n;k++){

					double c=0;
					switch(coo){
					case 0:
						c=normalizedControlPoints[i][j][k].getX();
						break;
					case 1:
						c=normalizedControlPoints[i][j][k].getY();
						break;
					case 2:
						c=normalizedControlPoints[i][j][k].getZ();
						break;
					}
					pol0+=coefficient(i,l,s)*coefficient(j,m,t)*coefficient(k,n,u)*c;
					
					if(debug)
						System.out.println(coo+"="+"("+i+","+j+","+k+")"+pol0);
					

				}

			}
		}



		return pol0;
	}

	private static double coefficient(int k, int n, double u) {


		double value=Math.pow(1.0-u, n-k)*Math.pow(u, k);

		value=value*binomial(n,k);

		return value;
	}

	private static double binomial(int n, int k) {		

		return factorial(n)/(factorial(n-k)*factorial(k));
	}

	private static int factorial(int num) {

		int ret = 1;
		for (int i = 1; i <= num; ++i) ret *= i;
		return ret;
	}

	public static void main(String[] args) {

		FreeFormDeformation fd=new FreeFormDeformation(2,3,3,100,200,300,new Point3D(0,0,0)); 
		
		Point3D p=new Point3D(50,60,70);
		
		System.out.println(fd.getDeformedPoint(p));
		
		fd.normalizedControlPoints[0][0][0].x+=0.01;
		
		System.out.println(fd.getDeformedPoint(p));
	}

}
