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
	
	TextureCylinder trunkCylinder=null;
	TextureCylinder foliageCylinder=null;
	
	private double len;
	private double vlen;
	
	public static int IMG_WIDTH;
	public static int IMG_HEIGHT;
	
	public static int texture_x0=10;
	public static int texture_y0=10;
	
	public static boolean isTextureDrawing=false;
	
	public Plant0(){}

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
		
		trunkCylinder=new TextureCylinder(trunk_meridians,trunk_parallels,trunk_lower_radius,trunk_lower_radius,
				trunk_lenght,0,0,0,false,true);
		foliageCylinder=new TextureCylinder(foliage_meridians,foliage_parallels,foliage_radius,trunk_lower_radius,
				foliage_length,0,trunkCylinder.getVlen(),trunkCylinder.exitIndex,true,true);
		
		len=foliageCylinder.getLen();
		vlen=foliageCylinder.getVlen()+trunkCylinder.getVlen();
		numTexturePoints=foliageCylinder.exitIndex;
		
		initMesh();
	}

	
	public void initMesh(){


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
		


		/*LineData topTrunk=new LineData();
		
		for (int i = 0; i < trunk_meridians; i++) {
			
			topTrunk.addIndex(trunkpoints[trunk_parallels-1][i].getIndex(),trunkCylinder.lf(i,0,0,Renderer3D.CAR_TOP),0,0);
			
		}
		topTrunk.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topTrunk);*/
		



		LineData bottomTrunk=new LineData();
		
		for (int i = trunk_meridians-1; i >=0; i--) {
			
			bottomTrunk.addIndex(trunkpoints[0][i].getIndex(),trunkCylinder.lf(i,0,0,Renderer3D.CAR_BOTTOM),0,0);
			
		}
		bottomTrunk.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomTrunk);
		
		for (int k = 0; k < trunk_parallels-1; k++) {
		
			for (int i = 0; i < trunk_meridians; i++) {
				
				addLine(trunkpoints[k][i],trunkpoints[k][(i+1)%trunk_meridians],trunkpoints[k+1][(i+1)%trunk_meridians],trunkpoints[k+1][i],
					   trunkCylinder.lf(i,k,Renderer3D.CAR_LEFT),Renderer3D.CAR_LEFT);
				
				
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
			
			topFoliage.addIndex(foliagePoints[foliage_parallels-1][i].getIndex(),foliageCylinder.lf(i,0,0,Renderer3D.CAR_TOP),0,0);
			
		}
		topFoliage.setData(""+Renderer3D.CAR_TOP);
		polyData.add(topFoliage);
		
		LineData bottomFoliage=new LineData();
		
		for (int i = foliage_meridians-1; i>=0; i--) {
			
			bottomFoliage.addIndex(foliagePoints[0][i].getIndex(),foliageCylinder.lf(i,0,0,Renderer3D.CAR_BOTTOM),0,0);
			
			
		}
		bottomFoliage.setData(""+Renderer3D.CAR_BOTTOM);
		polyData.add(bottomFoliage);
		
		for (int k = 0; k < foliage_parallels-1; k++) {
		
		
			for (int i = 0; i < foliage_meridians; i++) {
				
				
				addLine(foliagePoints[k][i],foliagePoints[k][(i+1)%foliage_meridians],foliagePoints[k+1][(i+1)%foliage_meridians],foliagePoints[k+1][i],
						   foliageCylinder.lf(i,k,Renderer3D.CAR_LEFT),Renderer3D.CAR_LEFT);

				
			}
	
		}

		//////// texture points		
		
		texture_points=buildTexturePoints();



	}
	
	public Vector buildTexturePoints() {
		
		isTextureDrawing=false;
		
		Vector texture_points=new Vector();
		texture_points.setSize(numTexturePoints);
		
				
		addTexturePoints(texture_points,trunkCylinder);
		addTexturePoints(texture_points,foliageCylinder);

		
		return texture_points;
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
			

		isTextureDrawing=true;

		

		Color backgroundColor=Color.green;

		
		IMG_WIDTH=(int) len+2*texture_x0;
		IMG_HEIGHT=(int) vlen+2*texture_y0;

		
		BufferedImage buf=new BufferedImage(IMG_WIDTH,IMG_HEIGHT,BufferedImage.TYPE_BYTE_INDEXED);
	
		try {


				Graphics2D bufGraphics=(Graphics2D)buf.getGraphics();
 
				bufGraphics.setColor(backgroundColor);
				bufGraphics.fillRect(0,0,IMG_WIDTH,IMG_HEIGHT);


				//draw lines for reference
				
				drawTextureCylinder(bufGraphics,trunkCylinder,Color.RED,Color.BLUE,Color.BLACK);
				drawTextureCylinder(bufGraphics,foliageCylinder,Color.RED,Color.BLUE,Color.BLACK);
			
				
			
				ImageIO.write(buf,"gif",file);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		
	}
	
	public double calX(double x){
		
		return texture_x0+x;
	}

	public double calY(double y){
		if(isTextureDrawing)
			return IMG_HEIGHT-(texture_y0+y);
		else
			return texture_y0+y;
	}


}	