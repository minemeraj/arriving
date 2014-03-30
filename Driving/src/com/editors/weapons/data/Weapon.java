package com.editors.weapons.data;

import java.util.Vector;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.Segments;
import com.main.Renderer3D;

public class Weapon extends CustomData{

	double barrel_lenght=0; 
	double barrel_radius=0;		
	int barrel_meridians=0;
	
	public static int WEAPON_TYPE_GUN=0;	
	public static int WEAPON_TYPE_SHOTGUN=1;
	public static int WEAPON_TYPE_REVOLVER=2;
	public static int WEAPON_TYPE_CHAINGUN=3;
	public int weapon_type=WEAPON_TYPE_GUN;
	
	
	
	double breech_length=0;
	double breech_width=0;
	double breech_height=0;
	
	double butt_length=0;
	double butt_width=0;
	double butt_height=0;

	double butt_end_length=0;
	double butt_end_width=0;
	double butt_end_height=0;

	public Weapon(){}

	public Weapon(
			
			int weapon_type,
			
			double barrel_lenght, 
			double barrel_radius,
			int barrel_meridians,

			double breech_length,
			double breech_width,
			double breech_height,

			double butt_length,
			double butt_width,			
			double butt_height,
			
			double butt_end_length,
			double butt_end_width,
			double butt_end_height
			) 
	{

								
				this.weapon_type = weapon_type;
								
				this.barrel_lenght = barrel_lenght;
				this.barrel_radius = barrel_radius;
				this.barrel_meridians = barrel_meridians;
		
				this.breech_length = breech_length;
				this.breech_width = breech_width;
				this.breech_height = breech_height;
		
				this.butt_length = butt_length;
				this.butt_width = butt_width;				
				this.butt_height = butt_height;
				
				this.butt_end_length = butt_end_length;
				this.butt_end_width = butt_end_width;
				this.butt_end_height = butt_end_height;
	}


	public Object clone(){

		Weapon grid=new Weapon(
				weapon_type,
				barrel_lenght,barrel_radius,barrel_meridians,
				breech_length,breech_width,breech_height,
				butt_length,butt_width,butt_height,
				butt_end_length,butt_end_width,butt_end_height
		
				);
		return grid;

	}




	public String toString() {

		return "F="+weapon_type+","+
				barrel_lenght+","+barrel_radius+","+barrel_meridians+","+
				breech_length+","+breech_width+","+breech_height+","+
				butt_length+","+butt_width+","+butt_height+","+
				butt_end_length+","+butt_width+","+butt_end_height;
	}

	public static Weapon buildWeapon(String str) {

		String[] vals = str.split(",");

	
		int weaponType =Integer.parseInt(vals[0]);
		
		double barrel_lenght =Double.parseDouble(vals[1]);
		double barrel_radius = Double.parseDouble(vals[2]);
		int barrel_meridians=Integer.parseInt(vals[3]);
		
		double breech_length =Double.parseDouble(vals[4]);
		double breech_width =Double.parseDouble(vals[5]);
		double breech_height =Double.parseDouble(vals[6]);
		
		double butt_length =Double.parseDouble(vals[7]);
		double butt_width =Double.parseDouble(vals[8]); 		
		double butt_height =Double.parseDouble(vals[9]);
		
		double butt_end_length =Double.parseDouble(vals[10]);
		double butt_end_width =Double.parseDouble(vals[11]);
		double butt_end_height =Double.parseDouble(vals[12]);

		Weapon grid=new Weapon(
				weaponType,
				barrel_lenght,barrel_radius,barrel_meridians,
				breech_length,breech_width,breech_height,
				butt_length,butt_width,butt_height,
				butt_end_length,butt_end_width,butt_end_height
				);

		return grid;
	}

	public double getBarrel_lenght() {
		return barrel_lenght;
	}

	public void setBarrel_lenght(double trunk_lenght) {
		this.barrel_lenght = trunk_lenght; 
	}

	public double getBarrel_radius() {
		return barrel_radius;
	}

	public void setBarrel_radius(double trunk_radius) {
		this.barrel_radius = trunk_radius;
	}

	public double getBarrel_length() {
		return breech_length;
	}

	public void setBarrel_length(double foliage_length) {
		this.breech_length = foliage_length;
	}

	public int getBarrel_meridians() {
		return barrel_meridians;
	}

	public void setBarrel_meridians(int foliage_meridians) {
		this.barrel_meridians = foliage_meridians;
	}
	
	public double getBreech_length() {
		return breech_length;
	}

	public void setBreech_length(double breech_length) {
		this.breech_length = breech_length;
	}

	public double getBreech_width() {
		return breech_width;
	}

	public void setBreech_width(double breech_width) {
		this.breech_width = breech_width;
	}

	public double getBreech_height() {
		return breech_height;
	}

	public void setBreech_height(double breech_height) {
		this.breech_height = breech_height;
	}

	public double getButt_length() {
		return butt_length;
	}

	public void setButt_length(double butt_length) {
		this.butt_length = butt_length;
	}


	public double getButt_height() {
		return butt_height;
	}

	public void setButt_height(double butt_height) {
		this.butt_height = butt_height;
	}

	public int getWeapon_type() {
		return weapon_type;
	}

	public void setWeapon_type(int weapon_type) {
		this.weapon_type = weapon_type;
	}

	public double getButt_end_width() {
		return butt_end_width;
	}

	public void setButt_end_width(double butt_end_width) {
		this.butt_end_width = butt_end_width;
	}
	
	public double getButt_width() {
		return butt_width;
	}

	public void setButt_width(double butt_width) {
		this.butt_width = butt_width;
	}
	

	public double getButt_end_length() {
		return butt_end_length;
	}

	public void setButt_end_length(double butt_end_length) {
		this.butt_end_length = butt_end_length;
	}

	public double getButt_end_height() {
		return butt_end_height;
	}

	public void setButt_end_height(double butt_end_height) {
		this.butt_end_height = butt_end_height;
	}

	
	public PolygonMesh buildMesh(){

		if(weapon_type==WEAPON_TYPE_SHOTGUN)
			return buildShotGunMesh();
		if(weapon_type==WEAPON_TYPE_GUN)
			return buildGunMesh();
		if(weapon_type==WEAPON_TYPE_REVOLVER)
			return buildRevolverMesh();
		if(weapon_type==WEAPON_TYPE_CHAINGUN)
			return buildChaingunMesh();
		else
			return buildGunMesh();
		
		

	}
	



	private PolygonMesh buildGunMesh() {
		points=new Vector();
		points.setSize(200);

		polyData=new Vector();
		
		
		n=0;
		
		double bax0=0;
		double bay0=breech_length;
		double baz0=breech_height-2*barrel_radius;

		//barrel:
		
		int fnx=5;
		int fny=5;
		int fnz=5;
		
		BPoint[][][] p=new BPoint[fnx][fny][fnz];
		
		Segments f0=new Segments(bax0,barrel_radius*2,bay0,barrel_lenght,baz0,barrel_radius*2);

		p[0][0][0]=addBPoint(-0.5,0,0,f0);
		p[1][0][0]=addBPoint(-0.25,0.0,0,f0);
		p[2][0][0]=addBPoint(0.0,0.0,0,f0);
		p[3][0][0]=addBPoint(0.25,0.0,0,f0);
		p[4][0][0]=addBPoint(0.5,0.0,0,f0);
		p[0][1][0]=addBPoint(-0.5,0.25,0,f0);
		p[1][1][0]=addBPoint(-0.25,0.25,0,f0);
		p[2][1][0]=addBPoint(0.0,0.25,0,f0);
		p[3][1][0]=addBPoint(0.25,0.25,0,f0);
		p[4][1][0]=addBPoint(0.5,0.25,0,f0);
		p[0][2][0]=addBPoint(-0.5,0.5,0,f0);
		p[1][2][0]=addBPoint(-0.25,0.5,0,f0);
		p[2][2][0]=addBPoint(0.0,0.5,0,f0);
		p[3][2][0]=addBPoint(0.25,0.5,0,f0);
		p[4][2][0]=addBPoint(0.5,0.5,0,f0);
		p[0][3][0]=addBPoint(-0.5,0.75,0,f0);
		p[1][3][0]=addBPoint(-0.25,0.75,0,f0);
		p[2][3][0]=addBPoint(0.0,0.75,0,f0);
		p[3][3][0]=addBPoint(0.25,0.75,0,f0);
		p[4][3][0]=addBPoint(0.5,0.75,0,f0);
		p[0][4][0]=addBPoint(-0.5,1.0,0,f0);
		p[1][4][0]=addBPoint(-0.25,1.0,0,f0);
		p[2][4][0]=addBPoint(0.0,1.0,0,f0);
		p[3][4][0]=addBPoint(0.25,1.0,0,f0);
		p[4][4][0]=addBPoint(0.5,1.0,0,f0);
		
		
		p[0][0][1]=addBPoint(-0.5,0,0.25,f0);
		p[1][0][1]=addBPoint(-0.25,0.0,0.25,f0);
		p[2][0][1]=addBPoint(0.0,0.0,0.25,f0);
		p[3][0][1]=addBPoint(0.25,0.0,0.25,f0);
		p[4][0][1]=addBPoint(0.5,0.0,0.25,f0);
		p[0][1][1]=addBPoint(-0.5,0.25,0.25,f0);	
		p[4][1][1]=addBPoint(0.5,0.25,0.25,f0);
		p[0][2][1]=addBPoint(-0.5,0.5,0.25,f0);	
		p[4][2][1]=addBPoint(0.5,0.5,0.25,f0);
		p[0][3][1]=addBPoint(-0.5,0.75,0.25,f0);	
		p[4][3][1]=addBPoint(0.5,0.75,0.25,f0);
		p[0][4][1]=addBPoint(-0.5,1.0,0.25,f0);
		p[1][4][1]=addBPoint(-0.25,1.0,0.25,f0);
		p[2][4][1]=addBPoint(0.0,1.0,0.25,f0);
		p[3][4][1]=addBPoint(0.25,1.0,0.25,f0);
		p[4][4][1]=addBPoint(0.5,1.0,0.25,f0);
		
		p[0][0][2]=addBPoint(-0.5,0,0.5,f0);
		p[1][0][2]=addBPoint(-0.25,0.0,0.5,f0);
		p[2][0][2]=addBPoint(0.0,0.0,0.5,f0);
		p[3][0][2]=addBPoint(0.25,0.0,0.5,f0);
		p[4][0][2]=addBPoint(0.5,0.0,0.5,f0);
		p[0][1][2]=addBPoint(-0.5,0.25,0.5,f0);	
		p[4][1][2]=addBPoint(0.5,0.25,0.5,f0);
		p[0][2][2]=addBPoint(-0.5,0.5,0.5,f0);	
		p[4][2][2]=addBPoint(0.5,0.5,0.5,f0);
		p[0][3][2]=addBPoint(-0.5,0.75,0.5,f0);	
		p[4][3][2]=addBPoint(0.5,0.75,0.5,f0);
		p[0][4][2]=addBPoint(-0.5,1.0,0.5,f0);
		p[1][4][2]=addBPoint(-0.25,1.0,0.5,f0);
		p[2][4][2]=addBPoint(0.0,1.0,0.5,f0);
		p[3][4][2]=addBPoint(0.25,1.0,0.5,f0);
		p[4][4][2]=addBPoint(0.5,1.0,0.5,f0);
		
		p[0][0][3]=addBPoint(-0.5,0,0.75,f0);
		p[1][0][3]=addBPoint(-0.25,0.0,0.75,f0);
		p[2][0][3]=addBPoint(0.0,0.0,0.75,f0);
		p[3][0][3]=addBPoint(0.25,0.0,0.75,f0);
		p[4][0][3]=addBPoint(0.5,0.0,0.75,f0);
		p[0][1][3]=addBPoint(-0.5,0.25,0.75,f0);	
		p[4][1][3]=addBPoint(0.5,0.25,0.75,f0);
		p[0][2][3]=addBPoint(-0.5,0.5,0.75,f0);	
		p[4][2][3]=addBPoint(0.5,0.5,0.75,f0);
		p[0][3][3]=addBPoint(-0.5,0.75,0.75,f0);	
		p[4][3][3]=addBPoint(0.5,0.75,0.75,f0);
		p[0][4][3]=addBPoint(-0.5,1.0,0.75,f0);
		p[1][4][3]=addBPoint(-0.25,1.0,0.75,f0);
		p[2][4][3]=addBPoint(0.0,1.0,0.75,f0);
		p[3][4][3]=addBPoint(0.25,1.0,0.75,f0);
		p[4][4][3]=addBPoint(0.5,1.0,0.75,f0);
		
		p[0][0][4]=addBPoint(-0.5,0.0,1.0,f0);	
		p[1][0][4]=addBPoint(-0.25,0.0,1.0,f0);	
		p[2][0][4]=addBPoint(0.0,0.0,1.0,f0);
		p[3][0][4]=addBPoint(0.25,0.0,1.0,f0);	
		p[4][0][4]=addBPoint(0.5,0.0,1.0,f0);		
		p[0][1][4]=addBPoint(-0.5,0.25,1.0,f0);	
		p[1][1][4]=addBPoint(-0.25,0.25,1.0,f0);
		p[2][1][4]=addBPoint(0.0,0.25,1.0,f0);
		p[3][1][4]=addBPoint(0.25,0.25,1.0,f0);
		p[4][1][4]=addBPoint(0.5,0.25,1.0,f0);
		p[0][2][4]=addBPoint(-0.5,0.5,1.0,f0);
		p[1][2][4]=addBPoint(-0.25,0.5,1.0,f0);	
		p[2][2][4]=addBPoint(0.0,0.5,1.0,f0);
		p[3][2][4]=addBPoint(0.25,0.5,1.0,f0);
		p[4][2][4]=addBPoint(0.5,0.5,1.0,f0);
		p[0][3][4]=addBPoint(-0.5,0.75,1.0,f0);	
		p[1][3][4]=addBPoint(-0.25,0.75,1.0,f0);
		p[2][3][4]=addBPoint(0.0,0.75,1.0,f0);
		p[3][3][4]=addBPoint(0.25,0.75,1.0,f0);
		p[4][3][4]=addBPoint(0.5,0.75,1.0,f0);
		p[0][4][4]=addBPoint(-0.5,1.0,1.0,f0);
		p[1][4][4]=addBPoint(-0.25,1.0,1.0,f0);	
		p[2][4][4]=addBPoint(0.0,1.0,1.0,f0);
		p[3][4][4]=addBPoint(0.25,1.0,1.0,f0);
		p[4][4][4]=addBPoint(0.5,1.0,1.0,f0);
		
		for (int i = 0; i < fnx-1; i++) {


			for (int j = 0; j < fny-1; j++) {

				for (int k = 0; k < fnz-1; k++) {




					if(i==0){

						LineData leftLD=addLine(p[i][j][k],p[i][j][k+1],p[i][j+1][k+1],p[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						LineData bottomLD=addLine(p[i][j][k],p[i][j+1][k],p[i+1][j+1][k],p[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==fnz-1){
						LineData topLD=addLine(p[i][j][k+1],p[i+1][j][k+1],p[i+1][j+1][k+1],p[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						LineData backLD=addLine(p[i][j][k],p[i+1][j][k],p[i+1][j][k+1],p[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==fny-1){
						LineData frontLD=addLine(p[i][j+1][k],p[i][j+1][k+1],p[i+1][j+1][k+1],p[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==fnx-1){

						LineData rightLD=addLine(p[i+1][j][k],p[i+1][j+1][k],p[i+1][j+1][k+1],p[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		//breech
		
		//main body
		
		int bnx=5;
		int bny=5;
		int bnz=2;
		
		BPoint[][][] breech=new BPoint[bnx][bny][bnz];
		
		double xc=0.0;
		
		Segments b0=new Segments(xc,breech_width,0,breech_length,0,breech_height);

		breech[0][0][0]=addBPoint(-0.5,0,0,b0);
		breech[1][0][0]=addBPoint(-0.25,0.0,0,b0);
		breech[2][0][0]=addBPoint(0.0,0.0,0,b0);
		breech[3][0][0]=addBPoint(0.25,0.0,0,b0);
		breech[4][0][0]=addBPoint(0.5,0.0,0,b0);
		breech[0][1][0]=addBPoint(-0.5,0.25,0,b0);
		breech[1][1][0]=addBPoint(-0.25,0.25,0,b0);
		breech[2][1][0]=addBPoint(0.0,0.25,0,b0);
		breech[3][1][0]=addBPoint(0.25,0.25,0,b0);
		breech[4][1][0]=addBPoint(0.5,0.25,0,b0);
		breech[0][2][0]=addBPoint(-0.5,0.5,0,b0);
		breech[1][2][0]=addBPoint(-0.25,0.5,0,b0);
		breech[2][2][0]=addBPoint(0.0,0.5,0,b0);
		breech[3][2][0]=addBPoint(0.25,0.5,0,b0);
		breech[4][2][0]=addBPoint(0.5,0.5,0,b0);
		breech[0][3][0]=addBPoint(-0.5,0.75,0,b0);
		breech[1][3][0]=addBPoint(-0.25,0.75,0,b0);
		breech[2][3][0]=addBPoint(0.0,0.75,0,b0);
		breech[3][3][0]=addBPoint(0.25,0.75,0,b0);
		breech[4][3][0]=addBPoint(0.5,0.75,0,b0);
		breech[0][4][0]=addBPoint(-0.5,1.0,0,b0);
		breech[1][4][0]=addBPoint(-0.25,1.0,0,b0);
		breech[2][4][0]=addBPoint(0.0,1.0,0,b0);
		breech[3][4][0]=addBPoint(0.25,1.0,0,b0);
		breech[4][4][0]=addBPoint(0.5,1.0,0,b0);
		
		breech[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);	
		breech[1][0][1]=addBPoint(-0.25,0.0,1.0,b0);	
		breech[2][0][1]=addBPoint(0.0,0.0,1.0,b0);
		breech[3][0][1]=addBPoint(0.25,0.0,1.0,b0);	
		breech[4][0][1]=addBPoint(0.5,0.0,1.0,b0);		
		breech[0][1][1]=addBPoint(-0.5,0.25,1.0,b0);	
		breech[1][1][1]=addBPoint(-0.25,0.25,1.0,b0);
		breech[2][1][1]=addBPoint(0.0,0.25,1.0,b0);
		breech[3][1][1]=addBPoint(0.25,0.25,1.0,b0);
		breech[4][1][1]=addBPoint(0.5,0.25,1.0,b0);
		breech[0][2][1]=addBPoint(-0.5,0.5,1.0,b0);
		breech[1][2][1]=addBPoint(-0.25,0.5,1.0,b0);	
		breech[2][2][1]=addBPoint(0.0,0.5,1.0,b0);
		breech[3][2][1]=addBPoint(0.25,0.5,1.0,b0);
		breech[4][2][1]=addBPoint(0.5,0.5,1.0,b0);
		breech[0][3][1]=addBPoint(-0.5,0.75,1.0,b0);	
		breech[1][3][1]=addBPoint(-0.25,0.75,1.0,b0);
		breech[2][3][1]=addBPoint(0.0,0.75,1.0,b0);
		breech[3][3][1]=addBPoint(0.25,0.75,1.0,b0);
		breech[4][3][1]=addBPoint(0.5,0.75,1.0,b0);
		breech[0][4][1]=addBPoint(-0.5,1.0,1.0,b0);
		breech[1][4][1]=addBPoint(-0.25,1.0,1.0,b0);	
		breech[2][4][1]=addBPoint(0.0,1.0,1.0,b0);
		breech[3][4][1]=addBPoint(0.25,1.0,1.0,b0);
		breech[4][4][1]=addBPoint(0.5,1.0,1.0,b0);
		
		for (int i = 0; i < bnx-1; i++) {


			for (int j = 0; j < bny-1; j++) {

				for (int k = 0; k < bnz-1; k++) {




					if(i==0){

						addLine(breech[i][j][k],breech[i][j][k+1],breech[i][j+1][k+1],breech[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(breech[i][j][k],breech[i][j+1][k],breech[i+1][j+1][k],breech[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==bnz-1){
						addLine(breech[i][j][k+1],breech[i+1][j][k+1],breech[i+1][j+1][k+1],breech[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(breech[i][j][k],breech[i+1][j][k],breech[i+1][j][k+1],breech[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==bny-1){
						addLine(breech[i][j+1][k],breech[i][j+1][k+1],breech[i+1][j+1][k+1],breech[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==bnx-1){

						addLine(breech[i+1][j][k],breech[i+1][j+1][k],breech[i+1][j+1][k+1],breech[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		//butt
		
		int pnx=5;
		int pny=5;
		int pnz=2;
		
		BPoint[][][] butt=new BPoint[pnx][pny][pnz];
		
		
		
		Segments p0=new Segments(xc,butt_width,0,butt_end_length,-butt_height,butt_height);

		butt[0][0][0]=addBPoint(-0.5,0,0,p0);
		butt[1][0][0]=addBPoint(-0.25,0.0,0,p0);
		butt[2][0][0]=addBPoint(0.0,0.0,0,p0);
		butt[3][0][0]=addBPoint(0.25,0.0,0,p0);
		butt[4][0][0]=addBPoint(0.5,0.0,0,p0);
		butt[0][1][0]=addBPoint(-0.5,0.25,0,p0);
		butt[1][1][0]=addBPoint(-0.25,0.25,0,p0);
		butt[2][1][0]=addBPoint(0.0,0.25,0,p0);
		butt[3][1][0]=addBPoint(0.25,0.25,0,p0);
		butt[4][1][0]=addBPoint(0.5,0.25,0,p0);
		butt[0][2][0]=addBPoint(-0.5,0.5,0,p0);
		butt[1][2][0]=addBPoint(-0.25,0.5,0,p0);
		butt[2][2][0]=addBPoint(0.0,0.5,0,p0);
		butt[3][2][0]=addBPoint(0.25,0.5,0,p0);
		butt[4][2][0]=addBPoint(0.5,0.5,0,p0);
		butt[0][3][0]=addBPoint(-0.5,0.75,0,p0);
		butt[1][3][0]=addBPoint(-0.25,0.75,0,p0);
		butt[2][3][0]=addBPoint(0.0,0.75,0,p0);
		butt[3][3][0]=addBPoint(0.25,0.75,0,p0);
		butt[4][3][0]=addBPoint(0.5,0.75,0,p0);
		butt[0][4][0]=addBPoint(-0.5,1.0,0,p0);
		butt[1][4][0]=addBPoint(-0.25,1.0,0,p0);
		butt[2][4][0]=addBPoint(0.0,1.0,0,p0);
		butt[3][4][0]=addBPoint(0.25,1.0,0,p0);
		butt[4][4][0]=addBPoint(0.5,1.0,0,p0);
		
		double yButt=breech_length-butt_length;
		
		Segments p1=new Segments(xc,butt_width,yButt,butt_length,-butt_height,butt_height);
		
		butt[0][0][1]=addBPoint(-0.5,0.0,1.0,p1);	
		butt[1][0][1]=addBPoint(-0.25,0.0,1.0,p1);	
		butt[2][0][1]=addBPoint(0.0,0.0,1.0,p1);
		butt[3][0][1]=addBPoint(0.25,0.0,1.0,p1);	
		butt[4][0][1]=addBPoint(0.5,0.0,1.0,p1);		
		butt[0][1][1]=addBPoint(-0.5,0.25,1.0,p1);	
		butt[1][1][1]=addBPoint(-0.25,0.25,1.0,p1);
		butt[2][1][1]=addBPoint(0.0,0.25,1.0,p1);
		butt[3][1][1]=addBPoint(0.25,0.25,1.0,p1);
		butt[4][1][1]=addBPoint(0.5,0.25,1.0,p1);
		butt[0][2][1]=addBPoint(-0.5,0.5,1.0,p1);
		butt[1][2][1]=addBPoint(-0.25,0.5,1.0,p1);	
		butt[2][2][1]=addBPoint(0.0,0.5,1.0,p1);
		butt[3][2][1]=addBPoint(0.25,0.5,1.0,p1);
		butt[4][2][1]=addBPoint(0.5,0.5,1.0,p1);
		butt[0][3][1]=addBPoint(-0.5,0.75,1.0,p1);	
		butt[1][3][1]=addBPoint(-0.25,0.75,1.0,p1);
		butt[2][3][1]=addBPoint(0.0,0.75,1.0,p1);
		butt[3][3][1]=addBPoint(0.25,0.75,1.0,p1);
		butt[4][3][1]=addBPoint(0.5,0.75,1.0,p1);
		butt[0][4][1]=addBPoint(-0.5,1.0,1.0,p1);
		butt[1][4][1]=addBPoint(-0.25,1.0,1.0,p1);	
		butt[2][4][1]=addBPoint(0.0,1.0,1.0,p1);
		butt[3][4][1]=addBPoint(0.25,1.0,1.0,p1);
		butt[4][4][1]=addBPoint(0.5,1.0,1.0,p1);
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




					if(i==0){

						addLine(butt[i][j][k],butt[i][j][k+1],butt[i][j+1][k+1],butt[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(butt[i][j][k],butt[i][j+1][k],butt[i+1][j+1][k],butt[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						addLine(butt[i][j][k+1],butt[i+1][j][k+1],butt[i+1][j+1][k+1],butt[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(butt[i][j][k],butt[i+1][j][k],butt[i+1][j][k+1],butt[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						addLine(butt[i][j+1][k],butt[i][j+1][k+1],butt[i+1][j+1][k+1],butt[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						addLine(butt[i+1][j][k],butt[i+1][j+1][k],butt[i+1][j+1][k+1],butt[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;

	}

	private PolygonMesh buildShotGunMesh() {
		points=new Vector();
		points.setSize(200);

		polyData=new Vector();
		
		
		n=0;
		
		double bax0=0;
		double bay0=breech_length;
		double baz0=breech_height-barrel_radius;

		//barrel:
		
		BPoint[] uTrunkpoints=new BPoint[barrel_meridians];
		BPoint[] bTrunkpoints=new BPoint[barrel_meridians];
		
		for (int i = 0; i < barrel_meridians; i++) {
		
			
			double x=bax0+barrel_radius*Math.cos(2*Math.PI/barrel_meridians*i);
			double z=baz0+barrel_radius*Math.sin(2*Math.PI/barrel_meridians*i);
			
			uTrunkpoints[i]=addBPoint(x,bay0+barrel_lenght,z);
			
			
		}


		LineData topLD=new LineData();
		
		
			
			for (int i = barrel_meridians-1; i >=0; i--) {
			
			topLD.addIndex(uTrunkpoints[i].getIndex());
			
		}
		topLD.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topLD);
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			double x=bax0+barrel_radius*Math.cos(2*Math.PI/barrel_meridians*i);
			double z=baz0+barrel_radius*Math.sin(2*Math.PI/barrel_meridians*i);
			
			bTrunkpoints[i]=addBPoint(x,bay0,z);
			
		}


		LineData bottomLD=new LineData();
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			bottomLD.addIndex(bTrunkpoints[i].getIndex());
			
		}
		bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);
		
		
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			LineData sideLD=new LineData();
			
			
			sideLD.addIndex(bTrunkpoints[(i+1)%barrel_meridians].getIndex());
			sideLD.addIndex(bTrunkpoints[i].getIndex());
			sideLD.addIndex(uTrunkpoints[i].getIndex());
			sideLD.addIndex(uTrunkpoints[(i+1)%barrel_meridians].getIndex());
			sideLD.setData(""+getFace(sideLD,points));
			polyData.add(sideLD);
			
		}
		
		//breech
		
		//main body
		
		int bnx=5;
		int bny=5;
		int bnz=2;
		
		BPoint[][][] breech=new BPoint[bnx][bny][bnz];
		
		double xc=0.0;
		
		Segments b0=new Segments(xc,breech_width,0,breech_length,0,breech_height);

		breech[0][0][0]=addBPoint(-0.5,0,0,b0);
		breech[1][0][0]=addBPoint(-0.25,0.0,0,b0);
		breech[2][0][0]=addBPoint(0.0,0.0,0,b0);
		breech[3][0][0]=addBPoint(0.25,0.0,0,b0);
		breech[4][0][0]=addBPoint(0.5,0.0,0,b0);
		breech[0][1][0]=addBPoint(-0.5,0.25,0,b0);
		breech[1][1][0]=addBPoint(-0.25,0.25,0,b0);
		breech[2][1][0]=addBPoint(0.0,0.25,0,b0);
		breech[3][1][0]=addBPoint(0.25,0.25,0,b0);
		breech[4][1][0]=addBPoint(0.5,0.25,0,b0);
		breech[0][2][0]=addBPoint(-0.5,0.5,0,b0);
		breech[1][2][0]=addBPoint(-0.25,0.5,0,b0);
		breech[2][2][0]=addBPoint(0.0,0.5,0,b0);
		breech[3][2][0]=addBPoint(0.25,0.5,0,b0);
		breech[4][2][0]=addBPoint(0.5,0.5,0,b0);
		breech[0][3][0]=addBPoint(-0.5,0.75,0,b0);
		breech[1][3][0]=addBPoint(-0.25,0.75,0,b0);
		breech[2][3][0]=addBPoint(0.0,0.75,0,b0);
		breech[3][3][0]=addBPoint(0.25,0.75,0,b0);
		breech[4][3][0]=addBPoint(0.5,0.75,0,b0);
		breech[0][4][0]=addBPoint(-0.5,1.0,0,b0);
		breech[1][4][0]=addBPoint(-0.25,1.0,0,b0);
		breech[2][4][0]=addBPoint(0.0,1.0,0,b0);
		breech[3][4][0]=addBPoint(0.25,1.0,0,b0);
		breech[4][4][0]=addBPoint(0.5,1.0,0,b0);
		
		breech[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);	
		breech[1][0][1]=addBPoint(-0.25,0.0,1.0,b0);	
		breech[2][0][1]=addBPoint(0.0,0.0,1.0,b0);
		breech[3][0][1]=addBPoint(0.25,0.0,1.0,b0);	
		breech[4][0][1]=addBPoint(0.5,0.0,1.0,b0);		
		breech[0][1][1]=addBPoint(-0.5,0.25,1.0,b0);	
		breech[1][1][1]=addBPoint(-0.25,0.25,1.0,b0);
		breech[2][1][1]=addBPoint(0.0,0.25,1.0,b0);
		breech[3][1][1]=addBPoint(0.25,0.25,1.0,b0);
		breech[4][1][1]=addBPoint(0.5,0.25,1.0,b0);
		breech[0][2][1]=addBPoint(-0.5,0.5,1.0,b0);
		breech[1][2][1]=addBPoint(-0.25,0.5,1.0,b0);	
		breech[2][2][1]=addBPoint(0.0,0.5,1.0,b0);
		breech[3][2][1]=addBPoint(0.25,0.5,1.0,b0);
		breech[4][2][1]=addBPoint(0.5,0.5,1.0,b0);
		breech[0][3][1]=addBPoint(-0.5,0.75,1.0,b0);	
		breech[1][3][1]=addBPoint(-0.25,0.75,1.0,b0);
		breech[2][3][1]=addBPoint(0.0,0.75,1.0,b0);
		breech[3][3][1]=addBPoint(0.25,0.75,1.0,b0);
		breech[4][3][1]=addBPoint(0.5,0.75,1.0,b0);
		breech[0][4][1]=addBPoint(-0.5,1.0,1.0,b0);
		breech[1][4][1]=addBPoint(-0.25,1.0,1.0,b0);	
		breech[2][4][1]=addBPoint(0.0,1.0,1.0,b0);
		breech[3][4][1]=addBPoint(0.25,1.0,1.0,b0);
		breech[4][4][1]=addBPoint(0.5,1.0,1.0,b0);
		
		for (int i = 0; i < bnx-1; i++) {


			for (int j = 0; j < bny-1; j++) {

				for (int k = 0; k < bnz-1; k++) {




					if(i==0){

						addLine(breech[i][j][k],breech[i][j][k+1],breech[i][j+1][k+1],breech[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(breech[i][j][k],breech[i][j+1][k],breech[i+1][j+1][k],breech[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==bnz-1){
						addLine(breech[i][j][k+1],breech[i+1][j][k+1],breech[i+1][j+1][k+1],breech[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(breech[i][j][k],breech[i+1][j][k],breech[i+1][j][k+1],breech[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==bny-1){
						addLine(breech[i][j+1][k],breech[i][j+1][k+1],breech[i+1][j+1][k+1],breech[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==bnx-1){

						addLine(breech[i+1][j][k],breech[i+1][j+1][k],breech[i+1][j+1][k+1],breech[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		//butt
		
		int pnx=5;
		int pny=2;
		int pnz=2;
		
		BPoint[][][] butt=new BPoint[pnx][pny][pnz];
		
		
		double zEnd=(butt_height-butt_end_height);
				
		Segments p0=new Segments(xc,butt_width,-butt_length,butt_length,zEnd,butt_end_height);	
		
		butt[0][0][0]=addBPoint(-0.5,0,0,p0);
		butt[1][0][0]=addBPoint(-0.25,0.0,0,p0);
		butt[2][0][0]=addBPoint(0.0,0.0,0,p0);
		butt[3][0][0]=addBPoint(0.25,0.0,0,p0);
		butt[4][0][0]=addBPoint(0.5,0.0,0,p0);
		butt[0][0][1]=addBPoint(-0.5,0.0,1.0,p0);	
		butt[1][0][1]=addBPoint(-0.25,0.0,1.0,p0);	
		butt[2][0][1]=addBPoint(0.0,0.0,1.0,p0);
		butt[3][0][1]=addBPoint(0.25,0.0,1.0,p0);	
		butt[4][0][1]=addBPoint(0.5,0.0,1.0,p0);	
		
		Segments p1=new Segments(xc,butt_width,-butt_length,butt_length,0,butt_height);


		butt[0][1][0]=addBPoint(-0.5,1.0,0,p1);
		butt[1][1][0]=addBPoint(-0.25,1.0,0,p1);
		butt[2][1][0]=addBPoint(0.0,1.0,0,p1);
		butt[3][1][0]=addBPoint(0.25,1.0,0,p1);
		butt[4][1][0]=addBPoint(0.5,1.0,0,p1);
		butt[0][1][1]=addBPoint(-0.5,1.0,1.0,p1);
		butt[1][1][1]=addBPoint(-0.25,1.0,1.0,p1);	
		butt[2][1][1]=addBPoint(0.0,1.0,1.0,p1);
		butt[3][1][1]=addBPoint(0.25,1.0,1.0,p1);
		butt[4][1][1]=addBPoint(0.5,1.0,1.0,p1);
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




					if(i==0){

						addLine(butt[i][j][k],butt[i][j][k+1],butt[i][j+1][k+1],butt[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(butt[i][j][k],butt[i][j+1][k],butt[i+1][j+1][k],butt[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						addLine(butt[i][j][k+1],butt[i+1][j][k+1],butt[i+1][j+1][k+1],butt[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(butt[i][j][k],butt[i+1][j][k],butt[i+1][j][k+1],butt[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						addLine(butt[i][j+1][k],butt[i][j+1][k+1],butt[i+1][j+1][k+1],butt[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						addLine(butt[i+1][j][k],butt[i+1][j+1][k],butt[i+1][j+1][k+1],butt[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;

	}

	

	private PolygonMesh buildChaingunMesh() {
		points=new Vector();
		points.setSize(200);

		polyData=new Vector();
		
		
		n=0;
		
		double bax0=0;
		double bay0=breech_length;
		double baz0=breech_height-barrel_radius;

		//barrel:
		
		BPoint[] uTrunkpoints=new BPoint[barrel_meridians];
		BPoint[] bTrunkpoints=new BPoint[barrel_meridians];
		
		for (int i = 0; i < barrel_meridians; i++) {
		
			
			double x=bax0+barrel_radius*Math.cos(2*Math.PI/barrel_meridians*i);
			double z=baz0+barrel_radius*Math.sin(2*Math.PI/barrel_meridians*i);
			
			uTrunkpoints[i]=addBPoint(x,bay0+barrel_lenght,z);
			
			
		}


		LineData topLD=new LineData();
		
		
			
			for (int i = barrel_meridians-1; i >=0; i--) {
			
			topLD.addIndex(uTrunkpoints[i].getIndex());
			
		}
		topLD.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topLD);
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			double x=bax0+barrel_radius*Math.cos(2*Math.PI/barrel_meridians*i);
			double z=baz0+barrel_radius*Math.sin(2*Math.PI/barrel_meridians*i);
			
			bTrunkpoints[i]=addBPoint(x,bay0,z);
			
		}


		LineData bottomLD=new LineData();
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			bottomLD.addIndex(bTrunkpoints[i].getIndex());
			
		}
		bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);
		
		
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			LineData sideLD=new LineData();
			
			
			sideLD.addIndex(bTrunkpoints[(i+1)%barrel_meridians].getIndex());
			sideLD.addIndex(bTrunkpoints[i].getIndex());
			sideLD.addIndex(uTrunkpoints[i].getIndex());
			sideLD.addIndex(uTrunkpoints[(i+1)%barrel_meridians].getIndex());
			sideLD.setData(""+getFace(sideLD,points));
			polyData.add(sideLD);
			
		}
		
		//breech
		
		//main body
		
		int bnx=5;
		int bny=5;
		int bnz=2;
		
		BPoint[][][] breech=new BPoint[bnx][bny][bnz];
		
		double xc=0.0;
		
		Segments b0=new Segments(xc,breech_width,0,breech_length,0,breech_height);

		breech[0][0][0]=addBPoint(-0.5,0,0,b0);
		breech[1][0][0]=addBPoint(-0.25,0.0,0,b0);
		breech[2][0][0]=addBPoint(0.0,0.0,0,b0);
		breech[3][0][0]=addBPoint(0.25,0.0,0,b0);
		breech[4][0][0]=addBPoint(0.5,0.0,0,b0);
		breech[0][1][0]=addBPoint(-0.5,0.25,0,b0);
		breech[1][1][0]=addBPoint(-0.25,0.25,0,b0);
		breech[2][1][0]=addBPoint(0.0,0.25,0,b0);
		breech[3][1][0]=addBPoint(0.25,0.25,0,b0);
		breech[4][1][0]=addBPoint(0.5,0.25,0,b0);
		breech[0][2][0]=addBPoint(-0.5,0.5,0,b0);
		breech[1][2][0]=addBPoint(-0.25,0.5,0,b0);
		breech[2][2][0]=addBPoint(0.0,0.5,0,b0);
		breech[3][2][0]=addBPoint(0.25,0.5,0,b0);
		breech[4][2][0]=addBPoint(0.5,0.5,0,b0);
		breech[0][3][0]=addBPoint(-0.5,0.75,0,b0);
		breech[1][3][0]=addBPoint(-0.25,0.75,0,b0);
		breech[2][3][0]=addBPoint(0.0,0.75,0,b0);
		breech[3][3][0]=addBPoint(0.25,0.75,0,b0);
		breech[4][3][0]=addBPoint(0.5,0.75,0,b0);
		breech[0][4][0]=addBPoint(-0.5,1.0,0,b0);
		breech[1][4][0]=addBPoint(-0.25,1.0,0,b0);
		breech[2][4][0]=addBPoint(0.0,1.0,0,b0);
		breech[3][4][0]=addBPoint(0.25,1.0,0,b0);
		breech[4][4][0]=addBPoint(0.5,1.0,0,b0);
		
		breech[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);	
		breech[1][0][1]=addBPoint(-0.25,0.0,1.0,b0);	
		breech[2][0][1]=addBPoint(0.0,0.0,1.0,b0);
		breech[3][0][1]=addBPoint(0.25,0.0,1.0,b0);	
		breech[4][0][1]=addBPoint(0.5,0.0,1.0,b0);		
		breech[0][1][1]=addBPoint(-0.5,0.25,1.0,b0);	
		breech[1][1][1]=addBPoint(-0.25,0.25,1.0,b0);
		breech[2][1][1]=addBPoint(0.0,0.25,1.0,b0);
		breech[3][1][1]=addBPoint(0.25,0.25,1.0,b0);
		breech[4][1][1]=addBPoint(0.5,0.25,1.0,b0);
		breech[0][2][1]=addBPoint(-0.5,0.5,1.0,b0);
		breech[1][2][1]=addBPoint(-0.25,0.5,1.0,b0);	
		breech[2][2][1]=addBPoint(0.0,0.5,1.0,b0);
		breech[3][2][1]=addBPoint(0.25,0.5,1.0,b0);
		breech[4][2][1]=addBPoint(0.5,0.5,1.0,b0);
		breech[0][3][1]=addBPoint(-0.5,0.75,1.0,b0);	
		breech[1][3][1]=addBPoint(-0.25,0.75,1.0,b0);
		breech[2][3][1]=addBPoint(0.0,0.75,1.0,b0);
		breech[3][3][1]=addBPoint(0.25,0.75,1.0,b0);
		breech[4][3][1]=addBPoint(0.5,0.75,1.0,b0);
		breech[0][4][1]=addBPoint(-0.5,1.0,1.0,b0);
		breech[1][4][1]=addBPoint(-0.25,1.0,1.0,b0);	
		breech[2][4][1]=addBPoint(0.0,1.0,1.0,b0);
		breech[3][4][1]=addBPoint(0.25,1.0,1.0,b0);
		breech[4][4][1]=addBPoint(0.5,1.0,1.0,b0);
		
		for (int i = 0; i < bnx-1; i++) {


			for (int j = 0; j < bny-1; j++) {

				for (int k = 0; k < bnz-1; k++) {




					if(i==0){

						addLine(breech[i][j][k],breech[i][j][k+1],breech[i][j+1][k+1],breech[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(breech[i][j][k],breech[i][j+1][k],breech[i+1][j+1][k],breech[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==bnz-1){
						addLine(breech[i][j][k+1],breech[i+1][j][k+1],breech[i+1][j+1][k+1],breech[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(breech[i][j][k],breech[i+1][j][k],breech[i+1][j][k+1],breech[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==bny-1){
						addLine(breech[i][j+1][k],breech[i][j+1][k+1],breech[i+1][j+1][k+1],breech[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==bnx-1){

						addLine(breech[i+1][j][k],breech[i+1][j+1][k],breech[i+1][j+1][k+1],breech[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		//butt
		
		int pnx=5;
		int pny=2;
		int pnz=2;
		
		BPoint[][][] butt=new BPoint[pnx][pny][pnz];
		
		
		double zEnd=(butt_height-butt_end_height);
				
		Segments p0=new Segments(xc,butt_width,-butt_length,butt_length,zEnd,butt_end_height);	
		
		butt[0][0][0]=addBPoint(-0.5,0,0,p0);
		butt[1][0][0]=addBPoint(-0.25,0.0,0,p0);
		butt[2][0][0]=addBPoint(0.0,0.0,0,p0);
		butt[3][0][0]=addBPoint(0.25,0.0,0,p0);
		butt[4][0][0]=addBPoint(0.5,0.0,0,p0);
		butt[0][0][1]=addBPoint(-0.5,0.0,1.0,p0);	
		butt[1][0][1]=addBPoint(-0.25,0.0,1.0,p0);	
		butt[2][0][1]=addBPoint(0.0,0.0,1.0,p0);
		butt[3][0][1]=addBPoint(0.25,0.0,1.0,p0);	
		butt[4][0][1]=addBPoint(0.5,0.0,1.0,p0);	
		
		Segments p1=new Segments(xc,butt_width,-butt_length,butt_length,0,butt_height);


		butt[0][1][0]=addBPoint(-0.5,1.0,0,p1);
		butt[1][1][0]=addBPoint(-0.25,1.0,0,p1);
		butt[2][1][0]=addBPoint(0.0,1.0,0,p1);
		butt[3][1][0]=addBPoint(0.25,1.0,0,p1);
		butt[4][1][0]=addBPoint(0.5,1.0,0,p1);
		butt[0][1][1]=addBPoint(-0.5,1.0,1.0,p1);
		butt[1][1][1]=addBPoint(-0.25,1.0,1.0,p1);	
		butt[2][1][1]=addBPoint(0.0,1.0,1.0,p1);
		butt[3][1][1]=addBPoint(0.25,1.0,1.0,p1);
		butt[4][1][1]=addBPoint(0.5,1.0,1.0,p1);
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




					if(i==0){

						addLine(butt[i][j][k],butt[i][j][k+1],butt[i][j+1][k+1],butt[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(butt[i][j][k],butt[i][j+1][k],butt[i+1][j+1][k],butt[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						addLine(butt[i][j][k+1],butt[i+1][j][k+1],butt[i+1][j+1][k+1],butt[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(butt[i][j][k],butt[i+1][j][k],butt[i+1][j][k+1],butt[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						addLine(butt[i][j+1][k],butt[i][j+1][k+1],butt[i+1][j+1][k+1],butt[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						addLine(butt[i+1][j][k],butt[i+1][j+1][k],butt[i+1][j+1][k+1],butt[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;

	}

	private PolygonMesh buildRevolverMesh() {
		points=new Vector();
		points.setSize(200);

		polyData=new Vector();
		
		
		n=0;
		
		double bax0=0;
		double bay0=breech_length;
		double baz0=breech_height-barrel_radius;

		//barrel:
		
		BPoint[] uTrunkpoints=new BPoint[barrel_meridians];
		BPoint[] bTrunkpoints=new BPoint[barrel_meridians];
		
		for (int i = 0; i < barrel_meridians; i++) {
		
			
			double x=bax0+barrel_radius*Math.cos(2*Math.PI/barrel_meridians*i);
			double z=baz0+barrel_radius*Math.sin(2*Math.PI/barrel_meridians*i);
			
			uTrunkpoints[i]=addBPoint(x,bay0+barrel_lenght,z);
			
			
		}


		LineData topLD=new LineData();
		
		
			
			for (int i = barrel_meridians-1; i >=0; i--) {
			
			topLD.addIndex(uTrunkpoints[i].getIndex());
			
		}
		topLD.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topLD);
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			double x=bax0+barrel_radius*Math.cos(2*Math.PI/barrel_meridians*i);
			double z=baz0+barrel_radius*Math.sin(2*Math.PI/barrel_meridians*i);
			
			bTrunkpoints[i]=addBPoint(x,bay0,z);
			
		}


		LineData bottomLD=new LineData();
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			bottomLD.addIndex(bTrunkpoints[i].getIndex());
			
		}
		bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);
		
		
		
		for (int i = 0; i < barrel_meridians; i++) {
			
			LineData sideLD=new LineData();
			
			
			sideLD.addIndex(bTrunkpoints[(i+1)%barrel_meridians].getIndex());
			sideLD.addIndex(bTrunkpoints[i].getIndex());
			sideLD.addIndex(uTrunkpoints[i].getIndex());
			sideLD.addIndex(uTrunkpoints[(i+1)%barrel_meridians].getIndex());
			sideLD.setData(""+getFace(sideLD,points));
			polyData.add(sideLD);
			
		}
		
		//breech
		
		//main body
		
		int bnx=5;
		int bny=5;
		int bnz=2;
		
		BPoint[][][] breech=new BPoint[bnx][bny][bnz];
		
		double xc=0.0;

		
		Segments b0=new Segments(xc,breech_width,0,breech_length,0,breech_height);

		breech[0][0][0]=addBPoint(-0.5,0,0,b0);
		breech[1][0][0]=addBPoint(-0.25,0.0,0,b0);
		breech[2][0][0]=addBPoint(0.0,0.0,0,b0);
		breech[3][0][0]=addBPoint(0.25,0.0,0,b0);
		breech[4][0][0]=addBPoint(0.5,0.0,0,b0);
		breech[0][1][0]=addBPoint(-0.5,0.25,0,b0);
		breech[1][1][0]=addBPoint(-0.25,0.25,0,b0);
		breech[2][1][0]=addBPoint(0.0,0.25,0,b0);
		breech[3][1][0]=addBPoint(0.25,0.25,0,b0);
		breech[4][1][0]=addBPoint(0.5,0.25,0,b0);
		breech[0][2][0]=addBPoint(-0.5,0.5,0,b0);
		breech[1][2][0]=addBPoint(-0.25,0.5,0,b0);
		breech[2][2][0]=addBPoint(0.0,0.5,0,b0);
		breech[3][2][0]=addBPoint(0.25,0.5,0,b0);
		breech[4][2][0]=addBPoint(0.5,0.5,0,b0);
		breech[0][3][0]=addBPoint(-0.5,0.75,0,b0);
		breech[1][3][0]=addBPoint(-0.25,0.75,0,b0);
		breech[2][3][0]=addBPoint(0.0,0.75,0,b0);
		breech[3][3][0]=addBPoint(0.25,0.75,0,b0);
		breech[4][3][0]=addBPoint(0.5,0.75,0,b0);
		breech[0][4][0]=addBPoint(-0.5,1.0,0,b0);
		breech[1][4][0]=addBPoint(-0.25,1.0,0,b0);
		breech[2][4][0]=addBPoint(0.0,1.0,0,b0);
		breech[3][4][0]=addBPoint(0.25,1.0,0,b0);
		breech[4][4][0]=addBPoint(0.5,1.0,0,b0);
		
		breech[0][0][1]=addBPoint(-0.5,0.0,1.0,b0);	
		breech[1][0][1]=addBPoint(-0.25,0.0,1.0,b0);	
		breech[2][0][1]=addBPoint(0.0,0.0,1.0,b0);
		breech[3][0][1]=addBPoint(0.25,0.0,1.0,b0);	
		breech[4][0][1]=addBPoint(0.5,0.0,1.0,b0);		
		breech[0][1][1]=addBPoint(-0.5,0.25,1.0,b0);	
		breech[1][1][1]=addBPoint(-0.25,0.25,1.0,b0);
		breech[2][1][1]=addBPoint(0.0,0.25,1.0,b0);
		breech[3][1][1]=addBPoint(0.25,0.25,1.0,b0);
		breech[4][1][1]=addBPoint(0.5,0.25,1.0,b0);
		breech[0][2][1]=addBPoint(-0.5,0.5,1.0,b0);
		breech[1][2][1]=addBPoint(-0.25,0.5,1.0,b0);	
		breech[2][2][1]=addBPoint(0.0,0.5,1.0,b0);
		breech[3][2][1]=addBPoint(0.25,0.5,1.0,b0);
		breech[4][2][1]=addBPoint(0.5,0.5,1.0,b0);
		breech[0][3][1]=addBPoint(-0.5,0.75,1.0,b0);	
		breech[1][3][1]=addBPoint(-0.25,0.75,1.0,b0);
		breech[2][3][1]=addBPoint(0.0,0.75,1.0,b0);
		breech[3][3][1]=addBPoint(0.25,0.75,1.0,b0);
		breech[4][3][1]=addBPoint(0.5,0.75,1.0,b0);
		breech[0][4][1]=addBPoint(-0.5,1.0,1.0,b0);
		breech[1][4][1]=addBPoint(-0.25,1.0,1.0,b0);	
		breech[2][4][1]=addBPoint(0.0,1.0,1.0,b0);
		breech[3][4][1]=addBPoint(0.25,1.0,1.0,b0);
		breech[4][4][1]=addBPoint(0.5,1.0,1.0,b0);
		
		for (int i = 0; i < bnx-1; i++) {


			for (int j = 0; j < bny-1; j++) {

				for (int k = 0; k < bnz-1; k++) {




					if(i==0){

						addLine(breech[i][j][k],breech[i][j][k+1],breech[i][j+1][k+1],breech[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(breech[i][j][k],breech[i][j+1][k],breech[i+1][j+1][k],breech[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==bnz-1){
						addLine(breech[i][j][k+1],breech[i+1][j][k+1],breech[i+1][j+1][k+1],breech[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(breech[i][j][k],breech[i+1][j][k],breech[i+1][j][k+1],breech[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==bny-1){
						addLine(breech[i][j+1][k],breech[i][j+1][k+1],breech[i+1][j+1][k+1],breech[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==bnx-1){

						addLine(breech[i+1][j][k],breech[i+1][j+1][k],breech[i+1][j+1][k+1],breech[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		//butt
		
		int pnx=5;
		int pny=2;
		int pnz=2;
		
		BPoint[][][] butt=new BPoint[pnx][pny][pnz];
		
		double zButt=-butt_height+breech_height*0.5;
		
		
		
		Segments p0=new Segments(xc,butt_width,-butt_end_length,butt_end_length,zButt,butt_height);

		butt[0][0][0]=addBPoint(-0.5,0,0,p0);
		butt[1][0][0]=addBPoint(-0.25,0.0,0,p0);
		butt[2][0][0]=addBPoint(0.0,0.0,0,p0);
		butt[3][0][0]=addBPoint(0.25,0.0,0,p0);
		butt[4][0][0]=addBPoint(0.5,0.0,0,p0);
		butt[0][1][0]=addBPoint(-0.5,1.0,0,p0);
		butt[1][1][0]=addBPoint(-0.25,1.0,0,p0);
		butt[2][1][0]=addBPoint(0.0,1.0,0,p0);
		butt[3][1][0]=addBPoint(0.25,1.0,0,p0);
		butt[4][1][0]=addBPoint(0.5,1.0,0,p0);
		
		Segments p1=new Segments(xc,butt_width,-butt_length,butt_length,zButt,butt_height);
		
		butt[0][0][1]=addBPoint(-0.5,0.0,1.0,p1);	
		butt[1][0][1]=addBPoint(-0.25,0.0,1.0,p1);	
		butt[2][0][1]=addBPoint(0.0,0.0,1.0,p1);
		butt[3][0][1]=addBPoint(0.25,0.0,1.0,p1);	
		butt[4][0][1]=addBPoint(0.5,0.0,1.0,p1);		
		butt[0][1][1]=addBPoint(-0.5,1.0,1.0,p1);
		butt[1][1][1]=addBPoint(-0.25,1.0,1.0,p1);	
		butt[2][1][1]=addBPoint(0.0,1.0,1.0,p1);
		butt[3][1][1]=addBPoint(0.25,1.0,1.0,p1);
		butt[4][1][1]=addBPoint(0.5,1.0,1.0,p1);
		
		for (int i = 0; i < pnx-1; i++) {


			for (int j = 0; j < pny-1; j++) {

				for (int k = 0; k < pnz-1; k++) {




					if(i==0){

						addLine(butt[i][j][k],butt[i][j][k+1],butt[i][j+1][k+1],butt[i][j+1][k],Renderer3D.CAR_LEFT);
					}

				
						
					if(k==0){

						addLine(butt[i][j][k],butt[i][j+1][k],butt[i+1][j+1][k],butt[i+1][j][k],Renderer3D.CAR_BOTTOM);
					
					}
					
					if(k+1==pnz-1){
						addLine(butt[i][j][k+1],butt[i+1][j][k+1],butt[i+1][j+1][k+1],butt[i][j+1][k+1],Renderer3D.CAR_TOP);
					}
					
					if(j==0){
						addLine(butt[i][j][k],butt[i+1][j][k],butt[i+1][j][k+1],butt[i][j][k+1],Renderer3D.CAR_BACK);
					}
					if(j+1==pny-1){
						addLine(butt[i][j+1][k],butt[i][j+1][k+1],butt[i+1][j+1][k+1],butt[i+1][j+1][k],Renderer3D.CAR_FRONT);	
					}
				

					if(i+1==pnx-1){

						addLine(butt[i+1][j][k],butt[i+1][j+1][k],butt[i+1][j+1][k+1],butt[i+1][j][k+1],Renderer3D.CAR_RIGHT);

					}
				}
			}

		}
		
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;

	}







}	