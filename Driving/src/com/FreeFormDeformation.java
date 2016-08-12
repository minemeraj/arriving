package com;

public class FreeFormDeformation {

	/** Lattice x units**/
	private int l;
	/** Lattice y units**/
	private int m;
	/** Lattice z units**/
	private int n;
	
	/** Lattice x size**/
	private double deltax;
	/** Lattice y size**/
	private double deltay;
	/** Lattice z size**/
	private double deltaz;
	
	Point3D xVersor=null;
	Point3D yVersor=null;
	Point3D zVersor=null;

	/**lattice coordinates in [0,1]**/
	private Point3D[][][] normalizedControlPoints=null;
	
	private Point3D origin=null;
	
	private boolean debug=false;

	/**
	 * 
	 * @param l
	 * @param m
	 * @param n
	 * @param deltax
	 * @param deltay
	 * @param deltaz
	 * @param nonNormalizedControlPoints
	 * @param origin
	 */
	FreeFormDeformation(int 
			l, int m, int n,
			double deltax,double deltay,double deltaz, 
			Point3D[][][] nonNormalizedControlPoints,
			Point3D xVersor,
			Point3D yVersor,
			Point3D zVersor,			
			Point3D origin) {
		super();
		this.l = l;
		this.m = m;
		this.n = n;		
		this.origin = origin;
		
		this.deltax = deltax;
		this.deltay = deltay;
		this.deltaz = deltaz;	
		
		this.xVersor = xVersor;
		this.yVersor = yVersor;
		this.zVersor = zVersor;
		
		this.origin = origin;
		
		
		this.normalizedControlPoints =  new Point3D[l+1][m+1][n+1];
		
		for (int i = 0; i <= l; i++) {
			for (int j = 0; j <= m; j++) {
				for (int k = 0; k <= n; k++) {
					
					Point3D onnp = nonNormalizedControlPoints[i][j][k];
					Point3D nnp =onnp.substract(origin);
					
					double px=Point3D.calculateDotProduct(nnp, xVersor);
					double py=Point3D.calculateDotProduct(nnp, yVersor);
					double pz=Point3D.calculateDotProduct(nnp, zVersor);
					
					this.normalizedControlPoints[i][j][k]=
							new Point3D(
								px/deltax,			
								py/deltay,
								pz/deltaz
					);
				}

			}
		}
	}

	/**
	 * Test function which builds a standard lattice
	 * 
	 * @param l
	 * @param m
	 * @param n
	 * @param deltax
	 * @param deltay
	 * @param deltaz
	 * @param origin
	 */
	public FreeFormDeformation(int l, int m, int n,double deltax,double deltay,double deltaz,Point3D origin) {

		this.l = l;
		this.m = m;
		this.n = n;
		this.normalizedControlPoints = new Point3D[l+1][m+1][n+1];
		this.origin = origin;
		
		this.deltax = deltax;
		this.deltay = deltay;
		this.deltaz = deltaz;
		
		xVersor=new Point3D(1.0,0.0,0.0);
		yVersor=new Point3D(0.0,1.0,0.0);
		zVersor=new Point3D(0.0,0.0,1.0);

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
	
	Point3D getDeformedPoint(double px,double py,double pz){
		
		return getDeformedPoint(new Point3D(px,py,pz));
	}
	
	Point3D getDeformedPoint(Point3D p){
		
		Point3D nnp =p.substract(origin);
		
		double px=Point3D.calculateDotProduct(nnp, xVersor);
		double py=Point3D.calculateDotProduct(nnp, yVersor);
		double pz=Point3D.calculateDotProduct(nnp, zVersor);

		Point3D pComponent=new Point3D(
				
				px/deltax,
				py/deltay,
				pz/deltaz
		);

		Point3D pd=getDeformedPointComponent(pComponent);

		Point3D pDeformed=new Point3D(
				deltax*pd.x*(xVersor.x)+deltay*pd.y*(yVersor.x)+deltaz*pd.z*(zVersor.x)+origin.x,
				deltax*pd.x*(xVersor.y)+deltay*pd.y*(yVersor.y)+deltaz*pd.z*(zVersor.y)+origin.y,
				deltax*pd.x*(xVersor.z)+deltay*pd.y*(yVersor.z)+deltaz*pd.z*(zVersor.z)+origin.z
		);

		return pDeformed;
	}


	private Point3D getDeformedPointComponent(Point3D p){

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

		FreeFormDeformation fd=new FreeFormDeformation(1,1,1,100,200,300,new Point3D(0,0,0)); 
		
		Point3D p=new Point3D(50,60,70);
		
		System.out.println(fd.getDeformedPoint(p));
		
		//stretch on x max
		fd.normalizedControlPoints[1][0][0].x+=0.5;
		fd.normalizedControlPoints[1][1][0].x+=0.5;
		fd.normalizedControlPoints[1][1][1].x+=0.5;
		fd.normalizedControlPoints[1][0][1].x+=0.5;
		
		//stretch on y max
		fd.normalizedControlPoints[0][1][0].y+=0.5;
		fd.normalizedControlPoints[1][1][0].y+=0.5;
		fd.normalizedControlPoints[0][1][1].y+=0.5;
		fd.normalizedControlPoints[1][1][1].y+=0.5;
		
		//stretch on z max
		fd.normalizedControlPoints[0][0][1].z+=0.5;
		fd.normalizedControlPoints[0][1][1].z+=0.5;
		fd.normalizedControlPoints[1][0][1].z+=0.5;
		fd.normalizedControlPoints[1][1][1].z+=0.5;
		
		System.out.println(fd.getDeformedPoint(p));
	}

}
