package com;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Vector;

import com.main.Renderer3D;

public class CustomData {
	
	public Vector points=null;
	public Vector polyData=null;
	public Vector texture_points=null;
	
	public int n=0;
	public static double pi=Math.PI;
	
	public int numTexturePoints=0;
	
	public CustomData specificData=null;
	
	public void buildBox(double x , double y, double z,double x_side,double y_side, double z_side) {
		
		BPoint pLeg000=addBPoint(x,y,z);
		BPoint pLeg100=addBPoint(x+x_side,y,z);
		BPoint pLeg110=addBPoint(x+x_side,y+y_side,z);
		BPoint pLeg010=addBPoint(x,y+y_side,z);
		
	
		LineData bottom=buildLine(pLeg000,pLeg010,pLeg110,pLeg100,Renderer3D.CAR_BOTTOM);
		polyData.add(bottom);
		
		
		BPoint pLeg001=addBPoint(x,y,z+z_side);
		BPoint pLeg101=addBPoint(x+x_side,y,z+z_side);
		BPoint pLeg111=addBPoint(x+x_side,y+y_side,z+z_side);
		BPoint pLeg011=addBPoint(x,y+y_side,z+z_side);
	
		
		addLine(pLeg001,pLeg101,pLeg111,pLeg011,Renderer3D.CAR_TOP);
		
		addLine(pLeg000,pLeg001,pLeg011,pLeg010,Renderer3D.CAR_LEFT);

		addLine(pLeg010,pLeg011,pLeg111,pLeg110,Renderer3D.CAR_FRONT);

		addLine(pLeg110,pLeg111,pLeg101,pLeg100,Renderer3D.CAR_RIGHT);

		addLine(pLeg100,pLeg101,pLeg001,pLeg000,Renderer3D.CAR_BACK);


	}
	
	public PolygonMesh getMesh(){
		
		PolygonMesh pm=new PolygonMesh(points,polyData);
        pm.setTexturePoints(getTexture_points()); 
		
		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;
	}
	

	public void translatePoints(Vector points, double dx, double dy) { 

		for (int i = 0; i < points.size(); i++) {
			BPoint point = (BPoint) points.elementAt(i);
			if(point==null)
				continue;
			point.translate(dx,dy,0);
		}

	}
	
	
	public void translatePoints(Point3D[] points, double dx, double dy) { 

		for (int i = 0; i < points.length; i++) {
			Point3D point = points[i];
			if(point==null)
				continue;
			point.translate(dx,dy,0);
		}

	}
	
	public void rotateYZ(BPoint[][][] element, double y0, double z0, double teta) {
		
		double st=Math.sin(teta);
		double ct=Math.cos(teta);
		
		for (int i = 0; i < element.length; i++) {
			
			BPoint[][] ele1 = element[i];
			 
			for (int j = 0; j < ele1.length; j++) {
				
				BPoint[] ele2 = ele1[j];
				
				for (int k = 0; k < ele2.length; k++) {
					
					BPoint bp=ele2[k];
					
					double xx=bp.x;
					double yy=bp.y;
					double zz=bp.z;
					
					bp.x=xx;
					bp.y=(zz-z0)*st+(yy-y0)*ct+y0;
					bp.z=-(yy-y0)*st+(zz-z0)*ct+z0;
					
					
				}
				
			}
			
		}
		
		
	}
	
	public void rotateYZ(BPoint[] element, double y0, double z0, double teta) {

		double st=Math.sin(teta);
		double ct=Math.cos(teta);

		for (int i = 0; i < element.length; i++) {

			BPoint bp = element[i];

			double xx=bp.x;
			double yy=bp.y;
			double zz=bp.z;

			bp.x=xx;
			bp.y=(zz-z0)*st+(yy-y0)*ct+y0;
			bp.z=-(yy-y0)*st+(zz-z0)*ct+z0;


		}


	}
	
	public BPoint addBPoint(double x, double y, double z){
		
		BPoint point=new BPoint(x, y, z, n++);
		points.setElementAt(point,point.getIndex());
		return point;
		
	}

	public BPoint addBPoint( double x, double y, double z,Segments s){
		
		BPoint point=new BPoint(s.x(x), s.y(y), s.z(z), n++);
		points.setElementAt(point,point.getIndex());
		return point;
		
	}
	
	public LineData  addLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex());
		ld.addIndex(p1.getIndex());					
		ld.addIndex(p2.getIndex());
		if(p3!=null)
			ld.addIndex(p3.getIndex());	
		ld.setData(""+face);

		polyData.add(ld);
		
		return ld;
	}
	
	public LineData  addLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int txt0,int txt1,int txt2,int txt3,int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex(),txt0,0,0);
		ld.addIndex(p1.getIndex(),txt1,0,0);					
		ld.addIndex(p2.getIndex(),txt2,0,0);
		if(p3!=null)
			ld.addIndex(p3.getIndex(),txt3,0,0);	
		ld.setData(""+face);

		polyData.add(ld);
		
		return ld;
	}
	
	public LineData  addLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int[] txt,int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex(),txt[0],0,0);
		ld.addIndex(p1.getIndex(),txt[1],0,0);					
		ld.addIndex(p2.getIndex(),txt[2],0,0);
		if(p3!=null)
			ld.addIndex(p3.getIndex(),txt[3],0,0);	
		ld.setData(""+face);

		polyData.add(ld);
		
		return ld;
	}
	
	public LineData  addLine(BPoint[][][]p,int i,int j,int k, int[] txt,int face) {


		if(face==Renderer3D.CAR_LEFT){

			return addLine(p[i][j][k],p[i][j][k+1],p[i][j+1][k+1],p[i][j+1][k],txt[0],txt[1],txt[2],txt[3],face);
		}
		else if(face==Renderer3D.CAR_BOTTOM){


			return addLine(p[i][j][k],p[i][j+1][k],p[i+1][j+1][k],p[i+1][j][k],txt[0],txt[1],txt[2],txt[3],face);

		}
		else if(face==Renderer3D.CAR_TOP){


			return addLine(p[i][j][k+1],p[i+1][j][k+1],p[i+1][j+1][k+1],p[i][j+1][k+1],txt[0],txt[1],txt[2],txt[3],face);
		}
		else if(face==Renderer3D.CAR_BACK){


			return addLine(p[i][j][k],p[i+1][j][k],p[i+1][j][k+1],p[i][j][k+1],txt[0],txt[1],txt[2],txt[3],face);
		}
		else if(face==Renderer3D.CAR_FRONT){


			return addLine(p[i][j+1][k],p[i][j+1][k+1],p[i+1][j+1][k+1],p[i+1][j+1][k],txt[0],txt[1],txt[2],txt[3],face);
		}

		else if(face==Renderer3D.CAR_RIGHT){

			return addLine(p[i+1][j][k],p[i+1][j+1][k],p[i+1][j+1][k+1],p[i+1][j][k+1],txt[0],txt[1],txt[2],txt[3],face);

		}
		
		return null;

	}
	
	
	public LineData  addLine(BPoint[][][]p,int i,int j,int k, int face) {


		if(face==Renderer3D.CAR_LEFT){

			return addLine(p[i][j][k],p[i][j][k+1],p[i][j+1][k+1],p[i][j+1][k],face);
		}
		else if(face==Renderer3D.CAR_BOTTOM){


			return addLine(p[i][j][k],p[i][j+1][k],p[i+1][j+1][k],p[i+1][j][k],face);

		}
		else if(face==Renderer3D.CAR_TOP){


			return addLine(p[i][j][k+1],p[i+1][j][k+1],p[i+1][j+1][k+1],p[i][j+1][k+1],face);
		}
		else if(face==Renderer3D.CAR_BACK){


			return addLine(p[i][j][k],p[i+1][j][k],p[i+1][j][k+1],p[i][j][k+1],face);
		}
		else if(face==Renderer3D.CAR_FRONT){


			return addLine(p[i][j+1][k],p[i][j+1][k+1],p[i+1][j+1][k+1],p[i+1][j+1][k],face);
		}

		else if(face==Renderer3D.CAR_RIGHT){

			return addLine(p[i+1][j][k],p[i+1][j+1][k],p[i+1][j+1][k+1],p[i+1][j][k+1],face);

		}
		
		return null;

	}
	
	public LineData buildLine(BPoint p0, BPoint p1, BPoint p2,
			BPoint p3, int face) {

		LineData ld=new LineData();

		ld.addIndex(p0.getIndex());
		ld.addIndex(p1.getIndex());					
		ld.addIndex(p2.getIndex());
		if(p3!=null)
			ld.addIndex(p3.getIndex());	
		ld.setData(""+face);

		return ld;
	}
	


	
	public void addYCylinder(double cyx0, double cyy0,double cyz0,
			double cylinder_radius,double cylinder_lenght,int barrel_meridians){
	
			BPoint[] uTrunkpoints=new BPoint[barrel_meridians];
			BPoint[] bTrunkpoints=new BPoint[barrel_meridians];
			
			for (int i = 0; i < barrel_meridians; i++) {
			
				
				double x=cyx0+cylinder_radius*Math.cos(2*Math.PI/barrel_meridians*i);
				double z=cyz0+cylinder_radius*Math.sin(2*Math.PI/barrel_meridians*i);
				
				uTrunkpoints[i]=addBPoint(x,cyy0+cylinder_lenght,z);
				
				
			}


			LineData topLD=new LineData();
			
			
				
				for (int i = barrel_meridians-1; i >=0; i--) {
				
				topLD.addIndex(uTrunkpoints[i].getIndex());
				
			}
			topLD.setData(""+Renderer3D.CAR_TOP);
			polyData.add(topLD);
			
			for (int i = 0; i < barrel_meridians; i++) {
				
				double x=cyx0+cylinder_radius*Math.cos(2*Math.PI/barrel_meridians*i);
				double z=cyz0+cylinder_radius*Math.sin(2*Math.PI/barrel_meridians*i);
				
				bTrunkpoints[i]=addBPoint(x,cyy0,z);
				
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
				sideLD.setData(""+Renderer3D.getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
			
	}
	
	public void addZCylinder(double cyx0, double cyy0,double cyz0,
			double cylinder_radius,double cylinder_lenght,int barrel_meridians){
	
			int trunk_parallels=2;
			int trunk_meridians=10;
			
		    BPoint[][] trunkpoints=new BPoint[trunk_parallels][trunk_meridians];
			
			for (int k = 0; k < trunk_parallels; k++) {
				
				
				for (int i = 0; i < trunk_meridians; i++) {
					
					double x=cyx0+cylinder_radius*Math.cos(2*Math.PI/trunk_meridians*i);
					double y=cyy0+cylinder_radius*Math.sin(2*Math.PI/trunk_meridians*i);
					double z=cyz0+cylinder_lenght/(trunk_parallels-1.0)*k;
					
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
	}
	
	public void addPrism(Prism prism){
		
	
		int size=prism.getSize();
		


			LineData topLD=new LineData();
			
			
			for (int i = 0; i < size; i++) {	
			
				
				topLD.addIndex(((BPoint) prism.upperBase[i]).getIndex());
				
			}
			topLD.setData(""+Renderer3D.CAR_TOP);
			polyData.add(topLD);
			
			LineData bottomLD=new LineData();
			
			
				
			for (int i = size-1; i >=0; i--) {	
				
				bottomLD.addIndex(((BPoint) prism.lowerBase[i]).getIndex());
				
			}
			bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
			polyData.add(bottomLD);
			
			
			
			for (int i = 0; i < size; i++) {
				
				LineData sideLD=new LineData();
				
				
				
				sideLD.addIndex(((BPoint) prism.lowerBase[i]).getIndex());
				sideLD.addIndex(((BPoint) prism.lowerBase[(i+1)%size]).getIndex());
				sideLD.addIndex(((BPoint) prism.upperBase[(i+1)%size]).getIndex());
				sideLD.addIndex(((BPoint) prism.upperBase[i]).getIndex());				
				sideLD.setData(""+Renderer3D.getFace(sideLD,points));
				polyData.add(sideLD);
				
			}
			
	}
	
	public PolygonMesh buildMesh(double scale){
		
		PolygonMesh pm=buildMesh();
		if(pm!=null)
			rescaleMesh(pm,scale);
		
		return pm;
	}


	public PolygonMesh buildMesh() {
		return null;
	}
	
	public static void rescaleMesh(PolygonMesh mesh, double scale) {
		
		if(scale==1.0)
			return;
		
		Point3D[] points = mesh.points;
		
		for (int i = 0; i < points.length; i++) {
			
			points[i].x=Math.round(points[i].x *scale);
			points[i].y=Math.round(points[i].y *scale);
			points[i].z=Math.round(points[i].z *scale);
		}
		
	}
	
	
	
	public static int f(int i,int j,int nx,int ny){
		
		return i+j*nx;
	}
	

	
	public CustomData getSpecificData() {
		return specificData;
	}


	public void setSpecificData(CustomData specificData) {
		this.specificData = specificData;
	}

	public void saveBaseCubicTexture(PolygonMesh pm, File file) {
		
	}

	public Vector getTexture_points() {
		return texture_points;
	}

	public void setTexture_points(Vector texture_points) {
		this.texture_points = texture_points;
	}
	
	public Point3D buildScaledPoint(double x,double y,double z,double alfa){
		
		return new Point3D(x*alfa,y*alfa,z*alfa);
		
	}
	
	public void drawLine(Graphics2D bufGraphics, Point3D point3d, Point3D point3d2) {
		
		bufGraphics.drawLine(
				(int)calX(point3d.x),
				(int)calY(point3d.y),
				(int)calX(point3d2.x),
				(int)calY(point3d2.y)
			);
	
	}
	
	public double calX(double x){
		
		return x;
	}

	public double calY(double y){
			return y;
	}
	
	public void drawTextureBlock(
			Graphics2D bufGraphics,
			TextureBlock t,
			Color upperColor,
			Color lowerColor,
			Color sidelColor
		){
		
		//draw lines for reference
		
		int numx=t.numx;
		int numy=t.numy;
		int numz=t.numz;		
		int N_FACES = t.N_FACES;
		
		bufGraphics.setStroke(new BasicStroke(0.1f));

		if(t.isDrawUpperBase()){

			bufGraphics.setColor(upperColor);

			for (int i = 0; i <numx; i++) {

				for (int j = 0; j < numy; j++) {

					double x0= calX(t.upperBase[i][j].x);
					double y0= calY(t.upperBase[i][j].y);

					double x1= calX(t.upperBase[(i+1)%numx][j].x);
					double y1= calY(t.upperBase[(i+1)%numx][j].y);

					double x2= calX(t.upperBase[(i+1)%numx][(j+1)%numy].x);
					double y2= calY(t.upperBase[(i+1)%numx][(j+1)%numy].y);

					double x3= calX(t.upperBase[i][(j+1)%numy].x);
					double y3= calY(t.upperBase[i][(j+1)%numy].y);

					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);	 
					bufGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);	 
					bufGraphics.drawLine((int)x2,(int)y2,(int)x3,(int)y3);	 
					bufGraphics.drawLine((int)x3,(int)y3,(int)x0,(int)y0);	 

				}

			}
		}
		
		if(t.isDrawLowerBase()){
		
		//lowerbase

		bufGraphics.setColor(lowerColor);		

			for (int i = 0; i <numx; i++) {
				
				for (int j = 0; j < numy; j++) {
	
					double x0= calX(t.lowerBase[i][j].x);
					double y0= calY(t.lowerBase[i][j].y);
					
					double x1= calX(t.lowerBase[(i+1)%numx][j].x);
					double y1= calY(t.lowerBase[(i+1)%numx][j].y);
					
					double x2= calX(t.lowerBase[(i+1)%numx][(j+1)%numy].x);
					double y2= calY(t.lowerBase[(i+1)%numx][(j+1)%numy].y);
					
					double x3= calX(t.lowerBase[i][(j+1)%numy].x);
					double y3= calY(t.lowerBase[i][(j+1)%numy].y);
	
					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);	 
					bufGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);	 
					bufGraphics.drawLine((int)x2,(int)y2,(int)x3,(int)y3);	 
					bufGraphics.drawLine((int)x3,(int)y3,(int)x0,(int)y0);	 		
				}
			}

		}

		//lateral surface
		bufGraphics.setColor(sidelColor);


		for(int j=0;j<numz-1;j++){

			//texture is open and periodical:

			for (int i = 0; i<N_FACES; i++) { 

				double x0=calX(t.lateralFaces[i][j].x);
				double x1=calX(t.lateralFaces[i+1][j].x);
				double y0=calY(t.lateralFaces[i][j].y);
				double y1=calY(t.lateralFaces[i][j+1].y);
				
				bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y0);
				bufGraphics.drawLine((int)x1,(int)y0,(int)x1,(int)y1);
				bufGraphics.drawLine((int)x1,(int)y1,(int)x0,(int)y1);
				bufGraphics.drawLine((int)x0,(int)y1,(int)x0,(int)y0);
			}

		}	
		
		
	}
	
	
	
	public void addTexturePoints(Vector texture_points,TextureBlock tb) {
		

		

		int numx=tb.numx;
		int numy=tb.numy;
		int numz=tb.numz;

		//upperbase
		
		if(tb.isDrawUpperBase()){

			for (int i = 0; i <numx; i++) {
				
				for (int j = 0; j < numy; j++) {
				
					
					double x= calX(tb.upperBase[i][j].x);
					double y= calY(tb.upperBase[i][j].y);
					
					int index=((Integer)tb.upperBase[i][j].getData()).intValue();
		
					Point3D p=new Point3D(x,y,0);			
					texture_points.setElementAt(p,index);
				}
	
			}
		}
		//lateral surface


		for(int j=0;j<numz;j++){

			//texture is open and periodical:

			for (int i = 0; i <=tb.N_FACES; i++) {

				double x=calX(tb.lateralFaces[i][j].x);
				double y=calY(tb.lateralFaces[i][j].y);

				Point3D p=new Point3D(x,y,0);

				int index=((Integer)tb.lateralFaces[i][j].getData()).intValue();
				//System.out.print(texIndex+"\t");
				texture_points.setElementAt(p,index);
			}
			
		}	
		
		if(tb.isDrawLowerBase()){
		
			for (int i = 0; i <numx; i++) {
				
				for (int j = 0; j < numy; j++) {
	
					double x= calX(tb.lowerBase[i][j].x);
					double y= calY(tb.lowerBase[i][j].y);
					
					int index=((Integer)tb.lowerBase[i][j].getData()).intValue();
		
					Point3D p=new Point3D(x,y,0);			
					texture_points.setElementAt(p,index);
				}
			}
		
		}

	}
	
	public void drawTextureCylinder(
			Graphics2D bufGraphics,
			TextureCylinder t,
			Color upperColor,
			Color lowerColor,
			Color sidelColor
		){
		
		//draw lines for reference
		

		bufGraphics.setStroke(new BasicStroke(0.1f));

		if(t.isDrawUpperBase()){

			bufGraphics.setColor(upperColor);

			for (int j = 0; j <t.upperBase.length; j++) {

					double x0= calX(t.upperBase[j].x);
					double y0= calY(t.upperBase[j].y);
					
					double x1= calX(t.upperBase[(j+1)%t.upperBase.length].x);
					double y1= calY(t.upperBase[(j+1)%t.upperBase.length].y);

					bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);	 

				}
		}
		
		if(t.isDrawLowerBase()){
		
		//lowerbase

		bufGraphics.setColor(lowerColor);		

			for (int j = 0; j <t.lowerBase.length; j++) {
	
				double x0= calX(t.lowerBase[j].x);
				double y0= calY(t.lowerBase[j].y);
				
				double x1= calX(t.lowerBase[(j+1)%t.lowerBase.length].x);
				double y1= calY(t.lowerBase[(j+1)%t.lowerBase.length].y);
	
				bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y1);			
	
			}


		}

		//lateral surface
		bufGraphics.setColor(sidelColor);

		
		for(int i=0;i<t.N_PARALLELS-1;i++){

			//texture is open and periodical:

			for (int j = 0; j <t.N_MERIDIANS; j++) { 

				double x0=calX(t.lateralFaces[i][j].x);		
				double y0=calY(t.lateralFaces[i][j].y);
				double x1=calX(t.lateralFaces[i][j+1].x);				
				double y1=calY(t.lateralFaces[i+1][j].y);
				
				
				
				bufGraphics.drawLine((int)x0,(int)y0,(int)x1,(int)y0);
				bufGraphics.drawLine((int)x1,(int)y0,(int)x1,(int)y1);
				bufGraphics.drawLine((int)x1,(int)y1,(int)x0,(int)y1);
				bufGraphics.drawLine((int)x0,(int)y1,(int)x0,(int)y0);
			}

		}	
		
	}
	
	
	public void addTexturePoints(Vector texture_points,TextureCylinder tb) {
		

	

		//upperbase
		
		if(tb.isDrawUpperBase()){

			for (int j = 0; j <tb.upperBase.length; j++) {
	
						
					double x= calX(tb.upperBase[j].x);
					double y= calY(tb.upperBase[j].y);
					
					int index=((Integer)tb.upperBase[j].getData()).intValue();
		
					Point3D p=new Point3D(x,y,0);			
					texture_points.setElementAt(p,index);
		
	
			}
		}
		//lateral surface
		

		for(int j=0;j<tb.N_PARALLELS;j++){

			//texture is open and periodical:

			for (int i = 0; i <=tb.N_MERIDIANS; i++) {

				double x=calX(tb.lateralFaces[j][i].x);
				double y=calY(tb.lateralFaces[j][i].y);

				Point3D p=new Point3D(x,y,0);

				int index=((Integer)tb.lateralFaces[j][i].getData()).intValue();
				texture_points.setElementAt(p,index);
			}
			
		}	

		
		if(tb.isDrawLowerBase()){
		
			for (int j = 0; j <tb.lowerBase.length; j++) {
	
					double x= calX(tb.lowerBase[j].x);
					double y= calY(tb.lowerBase[j].y);
					
					int index=((Integer)tb.lowerBase[j].getData()).intValue();
		
					Point3D p=new Point3D(x,y,0);			
					texture_points.setElementAt(p,index);
				}
		}
		
		
		 
	}

}
