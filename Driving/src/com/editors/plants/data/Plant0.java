package com.editors.plants.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.BPoint;
import com.CustomData;
import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.TextureBlock;
import com.TextureCylinder;
import com.main.Renderer3D;

public class Plant0 extends CustomData{

	double trunk_lenght=0; 
	double trunk_upper_radius=0;
	double trunk_lower_radius=0;
	
	int trunk_parallels=0;
	int trunk_meridians=0;	
	
	double foliage_length=0;
	double foliage_radius=0;
	double foliage_barycenter=0;
		
	int foliage_meridians=0;
	int foliage_parallels=0;
	int foliage_lobes=0;
	
	double lobe_percentage_depth=1.0;
	
	public static int PLANT_TYPE_0=0;
	
	public int plant_type=PLANT_TYPE_0;

	public Plant0(){}
	
	TextureCylinder trunkCylinder=null;
	TextureCylinder foliageCylinder=null;

	public Plant0(
			int plant_type,
			double trunk_lenght, double trunk_upper_radius,double trunk_lower_radius,
			int trunk_meridians,int trunk_parallels,
			double foliage_length, double foliage_radius,double foliage_barycenter,			
			int foliage_meridians,int foliage_parallels,
			int foliage_lobes,
			double lobe_percentage_depth
			
			) {
		
		super();
		
		this.plant_type = plant_type; 
		this.trunk_lenght = trunk_lenght; 
		this.trunk_upper_radius = trunk_upper_radius;
		this.trunk_lower_radius = trunk_lower_radius;
		this.trunk_meridians = trunk_meridians;
		this.trunk_parallels = trunk_parallels;
		
		this.foliage_length = foliage_length;
		this.foliage_radius = foliage_radius;
		this.foliage_barycenter = foliage_barycenter;
		
		this.foliage_meridians = foliage_meridians;
		this.foliage_parallels = foliage_parallels;
		this.foliage_lobes = foliage_lobes;
		this.lobe_percentage_depth = lobe_percentage_depth;
		
		trunkCylinder=new TextureCylinder(trunk_meridians,trunk_parallels,trunk_lower_radius,
				trunk_lenght,0,0,0);
		foliageCylinder=new TextureCylinder(foliage_meridians,foliage_parallels,foliage_radius,
				foliage_length,0,0,trunkCylinder.exitIndex);
		
		initMesh();
	}

	
	public PolygonMesh initMesh(){


		points=new Vector();
		points.setSize(800);

		polyData=new Vector();
		
		
		n=0;


		//trunk:

	    BPoint[][] trunkpoints=new BPoint[trunk_parallels][trunk_meridians];
		
		for (int k = 0; k < trunk_parallels; k++) {
			
			
			for (int i = 0; i < trunk_meridians; i++) {
				
				double z=trunk_lenght/(trunk_parallels-1.0)*k;
				
				//linear
				//double r=z/trunk_lenght*(trunk_upper_radius-trunk_lower_radius)+trunk_lower_radius;
				//parabolic
				double r=(z-trunk_lenght)*(z-trunk_lenght)
						*(trunk_lower_radius-trunk_upper_radius)/(trunk_lenght*trunk_lenght)
						+trunk_upper_radius;
								
				double x=r*Math.cos(2*Math.PI/trunk_meridians*i);
				double y=r*Math.sin(2*Math.PI/trunk_meridians*i);
				
				
				trunkpoints[k][i]=addBPoint(x,y,z);
				
			}
			
		}
		


		LineData topLD=new LineData();
		
		for (int i = 0; i < trunk_meridians; i++) {
			
			topLD.addIndex(trunkpoints[trunk_parallels-1][i].getIndex());
			
		}
		topLD.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topLD);
		



		LineData bottomLD=new LineData();
		
		for (int i = trunk_meridians-1; i >=0; i--) {
			
			bottomLD.addIndex(trunkpoints[0][i].getIndex());
			
		}
		bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomLD);
		
		for (int k = 0; k < trunk_parallels-1; k++) {
		
			for (int i = 0; i < trunk_meridians; i++) {
				
				LineData sideLD=new LineData();
				
				sideLD.addIndex(trunkpoints[k][i].getIndex());
				sideLD.addIndex(trunkpoints[k][(i+1)%trunk_meridians].getIndex());
				sideLD.addIndex(trunkpoints[k+1][(i+1)%trunk_meridians].getIndex());
				sideLD.addIndex(trunkpoints[k+1][i].getIndex());	
				sideLD.setData(""+Renderer3D.getFace(sideLD,points));
				polyData.add(sideLD);
				
				
			}
		}
		//foliage:
		
	
		
		BPoint[][] foliagePoints=new BPoint[foliage_parallels][foliage_meridians];
		
		double dzf=foliage_length/(foliage_parallels-1);
		
		for (int k = 0; k < foliage_parallels; k++) {
			
			double zf=dzf*k;
			
			foliagePoints[k]=new BPoint[foliage_meridians];
			
			for (int i = 0; i < foliage_meridians; i++) {
				
				double teta=2*Math.PI/foliage_meridians*i;
				
				double r=ff(zf)*rr(teta);			
				
				double x=r*Math.cos(teta);
				double y=r*Math.sin(teta);
				
				foliagePoints[k][i]=addBPoint(x,y,trunk_lenght+zf);
				
			}
			
		}
			

		LineData topFoliage=new LineData();
		
		for (int i = 0; i < foliage_meridians; i++) {
			
			topFoliage.addIndex(foliagePoints[foliage_parallels-1][i].getIndex());
			
		}
		topFoliage.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topFoliage);
		
		LineData bottomFoliage=new LineData();
		
		for (int i = foliage_meridians-1; i>=0; i--) {
			
			bottomFoliage.addIndex(foliagePoints[0][i].getIndex());
			
			
		}
		bottomFoliage.setData(""+Renderer3D.getFace(bottomFoliage,points));
		polyData.add(bottomFoliage);
		
		for (int k = 0; k < foliage_parallels-1; k++) {
		
		
			for (int i = 0; i < foliage_meridians; i++) {
				
				LineData sideLD=new LineData();
				
				sideLD.addIndex(foliagePoints[k][i].getIndex());
				sideLD.addIndex(foliagePoints[k][(i+1)%foliage_meridians].getIndex());
				sideLD.addIndex(foliagePoints[k+1][(i+1)%foliage_meridians].getIndex());
				sideLD.addIndex(foliagePoints[k+1][i].getIndex());	
				sideLD.setData(""+Renderer3D.getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
	
		}
		/////////

		//translatePoints(points,nw_x,nw_y);

		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}
	
	
	


	public double getTrunk_lenght() {
		return trunk_lenght;
	}

	public void setTrunk_lenght(double trunk_lenght) {
		this.trunk_lenght = trunk_lenght; 
	}


	public double getFoliage_length() {
		return foliage_length;
	}

	public void setFoliage_length(double foliage_length) {
		this.foliage_length = foliage_length;
	}

	public double getFoliage_radius() {
		return foliage_radius;
	}

	public void setFoliage_radius(double foliage_radius) {
		this.foliage_radius = foliage_radius;
	}


	public int getFoliage_meridians() {
		return foliage_meridians;
	}

	public void setFoliage_meridians(int foliage_meridians) {
		this.foliage_meridians = foliage_meridians;
	}

	public int getFoliage_parallels() {
		return foliage_parallels;
	}

	public void setFoliage_parallels(int foliage_parallels) {
		this.foliage_parallels = foliage_parallels;
	}

	public int getFoliage_lobes() {
		return foliage_lobes;
	}

	public void setFoliage_lobes(int foliage_lobes) {
		this.foliage_lobes = foliage_lobes;
	}


	public double getTrunk_upper_radius() {
		return trunk_upper_radius;
	}

	public void setTrunk_upper_radius(double trunk_upper_radius) {
		this.trunk_upper_radius = trunk_upper_radius;
	}

	public double getTrunk_lower_radius() {
		return trunk_lower_radius;
	}

	public void setTrunk_lower_radius(double trunk_lower_radius) {
		this.trunk_lower_radius = trunk_lower_radius;
	}

	public int getTrunk_parallels() {
		return trunk_parallels;
	}

	public void setTrunk_parallels(int trunk_parallels) {
		this.trunk_parallels = trunk_parallels;
	}

	public int getTrunk_meridians() {
		return trunk_meridians;
	}

	public void setTrunk_meridians(int trunk_meridians) {
		this.trunk_meridians = trunk_meridians;
	}

	public double getLobe_percentage_depth() {
		return lobe_percentage_depth;
	}

	public void setLobe_percentage_depth(double lobe_percentage_depth) {
		this.lobe_percentage_depth = lobe_percentage_depth;
	}


	public double ff(double x){
		
		if(foliage_length==0)
			return 0;
			
		double xr=x/foliage_length;
		double xf=foliage_barycenter/foliage_length;

		
		if(xr>xf){
			
			double a=(trunk_upper_radius-foliage_radius)/(xf*xf-2*xf+1);
			double b=-2*xf*a;
			double c=trunk_upper_radius-b-a;			
			return a*xr*xr+b*xr+c;
			
		}else{

			
			double a=(trunk_upper_radius-foliage_radius)/(xf*xf);
			double b=-2*(trunk_upper_radius-foliage_radius)/xf;
			double c=trunk_upper_radius;
				
			return a*xr*xr+b*xr+c;
			
		}
		

		
	}
	
	public double rr(double teta){
		
		double alfa=lobe_percentage_depth/100.0;
		
		double rad=(alfa+(1.0-alfa)*Math.abs(Math.cos(foliage_lobes*teta/2.0)));
		
		return rad;
		
	}

	public int getPlant_type() {
		return plant_type;
	}

	public void setPlant_type(int plant_type) {
		this.plant_type = plant_type;
	}
	
	@Override
	public void saveBaseCubicTexture(PolygonMesh mesh, File file) {
			
		Color backgroundColor=Color.green;
		Color back_color=Color.BLUE;
		Color top_color=Color.RED;
		Color bottom_color=Color.MAGENTA;
		Color left_color=Color.YELLOW;
		Color right_color=Color.ORANGE;
		Color front_color=Color.CYAN;
		
		int IMG_WIDTH=0;
		int IMG_HEIGHT=0;

		int deltaX=0;
		int deltaY=0;
		int deltaX2=0;

		double minx=0;
		double miny=0;
		double minz=0;
		
		double maxx=0;
		double maxy=0;
		double maxz=0;
		
		
	      //find maxs
		for(int j=0;j<mesh.points.length;j++){
			
			Point3D point=mesh.points[j];
			
			if(j==0){
				
				minx=point.x;
				miny=point.y;
				minz=point.z;
				
				maxx=point.x;
				maxy=point.y;
				maxz=point.z;
			}
			else{
				
				maxx=(int)Math.max(point.x,maxx);
				maxz=(int)Math.max(point.z,maxz);
				maxy=(int)Math.max(point.y,maxy);
				
				
				minx=(int)Math.min(point.x,minx);
				minz=(int)Math.min(point.z,minz);
				miny=(int)Math.min(point.y,miny);
			}
			
	
		}
		
		deltaX2=(int)(maxx-minx)+1;
		deltaX=(int)(maxz-minz)+1; 
		deltaY=(int)(maxy-miny)+1;
		
		deltaX2=deltaX2+deltaX;
		
		IMG_HEIGHT=(int) deltaY+deltaX+deltaX;
		IMG_WIDTH=(int) (deltaX2+deltaX2);
		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {

	


				int DX=0;

				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(DX+0,0,IMG_WIDTH,IMG_HEIGHT);


				//draw lines for reference

				bufGraphics.setColor(new Color(0,0,0));
				bufGraphics.setStroke(new BasicStroke(0.1f));
				for(int j=0;j<mesh.polygonData.size();j++){ 

					LineData ld=(LineData) mesh.polygonData.elementAt(j);

					for (int k = 0; k < ld.size(); k++) {


						Point3D point0=  mesh.points[ld.getIndex(k)];
						Point3D point1=  mesh.points[ld.getIndex((k+1)%ld.size())];
						//top
						bufGraphics.setColor(top_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.x-minx+deltaX),(int)(-point1.y+maxy+deltaX));
						//front
						bufGraphics.setColor(front_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(point0.z-minz),DX+(int)(point1.x-minx+deltaX),(int)(point1.z-minz));
						//left
						bufGraphics.setColor(left_color);
						bufGraphics.drawLine(DX+(int)(point0.z-minz),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.z-minz),(int)(-point1.y+maxy+deltaX));
						//right
						bufGraphics.setColor(right_color);
						bufGraphics.drawLine(DX+(int)(-point0.z+maxz+deltaX2),(int)(-point0.y+maxy+deltaX),DX+(int)(-point1.z+maxz+deltaX2),(int)(-point1.y+maxy+deltaX));
						//back
						bufGraphics.setColor(back_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX),(int)(-point0.z+maxz+deltaY+deltaX),DX+(int)(point1.x-minx+deltaX),(int)(-point1.z+maxz+deltaY+deltaX));
						//bottom
						bufGraphics.setColor(bottom_color);
						bufGraphics.drawLine(DX+(int)(point0.x-minx+deltaX+deltaX2),(int)(-point0.y+maxy+deltaX),DX+(int)(point1.x-minx+deltaX+deltaX2),(int)(-point1.y+maxy+deltaX));
				
					}

				}	
			
			ImageIO.write(buf,"gif",file);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}


}	