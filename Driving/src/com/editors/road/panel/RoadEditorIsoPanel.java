package com.editors.road.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import com.BarycentricCoordinates;
import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.LineDataVertex;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.SPLine;
import com.SPNode;
import com.Texture;
import com.ZBuffer;
import com.editors.EditorData;
import com.editors.ValuePair;
import com.editors.road.RoadEditor;
import com.main.DrivingFrame;
import com.main.Renderer3D;
import com.main.Road;



public class RoadEditorIsoPanel extends RoadEditorPanel{
	
	private double deltay=1;
	private double deltax=1;
	
	private int y0=400;
	private int x0=100;
	

	
	private double alfa=Math.PI/3;
	private double cosAlfa=Math.cos(alfa);
	private double sinAlfa=Math.sin(alfa);
	
	private Point3D projectionNormal=new Point3D(-1/Math.sqrt(3),-1/Math.sqrt(3),1/Math.sqrt(3));
	
	private double viewDirectionCos=1.0;
	private double viewDirectionSin=0.0;	
	
	private int greenRgb= Color.GREEN.getRGB();
	
	private Point3D lightDirection=new Point3D(-Math.sqrt(2)/2,-Math.sqrt(2)/2,0);
	
	protected int POSX=0;
	protected int POSY=0;
	
	private int minMovement=5;

	public RoadEditorIsoPanel(RoadEditor editor, int WIDTH,int HEIGHT) {
		
		super(editor, WIDTH,HEIGHT);
		initialize();
	}
	
	@Override
	public void drawRoad(PolygonMesh[] meshes, ArrayList<DrawObject> drawObjects,ArrayList<SPLine> splines,Point3D startPosition,ZBuffer landscapeZbuffer,Graphics2D graph) {

		displayTerrain(landscapeZbuffer,meshes);
		if(!isHide_objects())
			displayObjects(drawObjects,null,landscapeZbuffer);
		if(!isHide_splines())
			displaySPLines(landscapeZbuffer,splines);
		
		displayStartPosition(landscapeZbuffer, startPosition);
	}
	
	@Override
	public void initialize() {

		deltax=4;
		deltay=4;

		POSX=0;
		POSY=1000;
		
		selectionColor=new Color(255,0,0,127);
		
		xMovement=2*minMovement;
		yMovement=2*minMovement;

	}


	public void displayTerrain(ZBuffer roadZbuffer,PolygonMesh[] meshes) {
		
		int index=0;
		
		//for(int index=0;index<2;index++){
		
			PolygonMesh mesh=meshes[index];
			
			int sizel=mesh.polygonData.size();
	
			for(int j=0;j<sizel;j++){
	
	
				LineData ld=(LineData) mesh.polygonData.get(j);
	
				Polygon3D p3D=buildTranslatedPolygon3D(ld,mesh.points,index,mesh.getLevel());
				
				Color selected=null;
				
				if(ld.isSelected()){

					if(index<0 || index==editor.getACTIVE_PANEL()){
						selected=selectionColor;
					}
				}
				
	
				decomposeClippedPolygonIntoZBuffer(p3D,selected,EditorData.worldTextures[p3D.getIndex()],roadZbuffer,mesh.hashCode());
				
			    if(index==1){
			    	
			    	//build road polgyons
			    	
			    	Polygon3D[] polygons=Road.buildAdditionalRoadPolygons(p3D);
			    	
			    	if(selected==null)
			    		selected=Color.DARK_GRAY;
			    	
			    	for (int i = 0; i < polygons.length; i++) {
							decomposeClippedPolygonIntoZBuffer(polygons[i],selected,null,roadZbuffer,mesh.hashCode());
					}
			    	
			    }
	
	
			}
		
		//}
		
		

		//buildScreen(buf); 
	}
	
	public void displaySPLines(ZBuffer landscapeZbuffer, ArrayList<SPLine> splines) {
	
		

		for (int i = 0; i < splines.size(); i++) {
			SPLine spline = (SPLine) splines.get(i);
			spline.calculate3DMeshes();
			
			int hashCode=spline.hashCode();
			
			ArrayList<PolygonMesh> meshes = spline.getMeshes3D();
			
			for (int j = 0; j < meshes.size(); j++) {
				
				PolygonMesh mesh = (PolygonMesh) meshes.get(j);
				
				
				ArrayList<LineData> polygonData=mesh.polygonData;
				
				int lsize=polygonData.size();
				

				for(int k=0;k<lsize;k++){
				
					Color selected=null;
					
					LineData ld=(LineData) polygonData.get(k);
					
					if(ld.isSelected()){

						/*if(index<0 || index==editor.ACTIVE_PANEL){
							selected=selectionColor;
						}*/
					}
					
					Polygon3D p3D=buildTranslatedPolygon3D(ld,mesh.points,DrivingFrame.ROAD_INDEX,mesh.getLevel());
					
					decomposeClippedPolygonIntoZBuffer(p3D,selected,EditorData.splinesTextures[ld.getTexture_index()],landscapeZbuffer,hashCode);
					
				
				}
				
			}
			
			ArrayList<SPNode> nodes = spline.getNodes();
			
			if(nodes==null)
				continue;
			
			int sz=nodes.size();

			for (int k = 0; k < sz; k++) {
				
				SPNode node = (SPNode) nodes.get(k);
				
				PolygonMesh pm=node.getRing();
				
				Texture texture=EditorData.whiteTexture;
				
				if(node.isSelected())
					texture=EditorData.redTexture;
				
				for(int l=0;l<pm.polygonData.size();l++){
					
					
					LineData ld=(LineData) pm.polygonData.get(l);
					
				
											
					Polygon3D p3D=buildTranslatedPolygon3D(ld,pm.points,DrivingFrame.ROAD_INDEX,pm.getLevel());
					
					decomposeClippedPolygonIntoZBuffer(p3D,null,texture,landscapeZbuffer,hashCode);

				} 
				
			
			}

			
		}
		
	}
	

	
	public void displayObjects(ArrayList<DrawObject> drawObjects,Area totalVisibleField,ZBuffer zbuffer){


        Rectangle rect = null;//totalVisibleField.getBounds();
		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.get(i);
     		drawPolygonMesh(dro, rect, zbuffer);
		}		

	}
	
	
	private void drawPolygonMesh(DrawObject dro,Rectangle rect,ZBuffer zbuffer) {
		
		//if(!totalVisibleField.contains(dro.x-POSX,VIEW_DIRECTION*(dro.y-POSY)))
	
		//if(rect.y+rect.height<dro.y-POSY)
		//	return;
		
		PolygonMesh mesh = dro.getMesh();
		
		Color selected=null;
		
		if(dro.isSelected()){

				selected=selectionColor;
			
		}
		
	    
		decomposeCubicMesh((CubicMesh) mesh,EditorData.objectTextures[dro.getIndex()],zbuffer,selected);
		
	}
	
	private void decomposeCubicMesh(CubicMesh cm, Texture texture,ZBuffer zBuffer,Color selected){
		
		
		
		Point3D point000=buildTransformedPoint(cm.point000);				

		Point3D point011=buildTransformedPoint(cm.point011);

		Point3D point001=buildTransformedPoint(cm.point001);
			
		Point3D xVersor=buildTransformedVersor(cm.getXAxis());
		Point3D yVersor=buildTransformedVersor(cm.getYAxis());
		
		Point3D zVersor=buildTransformedVersor(cm.getZAxis());
		Point3D zMinusVersor=new Point3D(-zVersor.x,-zVersor.y,-zVersor.z);
		
		


		
		int polSize=cm.polygonData.size();	
		for(int i=0;i<polSize;i++){



			//int due=(int)(255-i%15);			
			//Color col=new Color(due,0,0);

			LineData ld=cm.polygonData.get(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld,cm.getLevel());
			polRotate.setShadowCosin(ld.getShadowCosin());


			int face=cm.boxFaces[i];

			buildTransformedPolygon(polRotate);


			decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,selected,texture,zBuffer);



		}


}


	private void decomposeCubiMeshPolygon(
			
			Polygon3D polRotate, 
			Point3D xVersor, 
			Point3D yVersor, 
			Point3D zVersor, 
			Point3D zMinusVersor,
			CubicMesh cm, 
			Point3D point000, 
			Point3D point011, 
			Point3D point001,
			int face, 
			Color selected, 
			Texture texture, 
			ZBuffer zBuffer
			){
		
			Point3D xDirection=null;
			Point3D yDirection=null;
			
			Point3D rotateOrigin=point000;
			
			int deltaWidth=0;
			int deltaHeight=cm.getDeltaY();
			
			int deltaTexture=0;
			
		
			
		 	if(face==Renderer3D.CAR_BOTTOM){
		 		deltaWidth=cm.getDeltaX()+cm.getDeltaX2();
			 	xDirection=xVersor;
			 	yDirection=yVersor;
		 	} 
			if(face==Renderer3D.CAR_FRONT  ){
		
				
				 deltaWidth=cm.getDeltaX();
				 deltaHeight=cm.getDeltaY2();
				 xDirection=xVersor;
				 yDirection=zMinusVersor;
				 
				 rotateOrigin=point011;
		
		
			}
			else if(face==Renderer3D.CAR_BACK){
				 deltaWidth=cm.getDeltaX();
				 deltaHeight=0;
				 xDirection=xVersor;
				 yDirection=zVersor;
		
		
			}
			else if(face==Renderer3D.CAR_TOP){
				 deltaWidth=cm.getDeltaX();
				 xDirection=xVersor;
				 yDirection=yVersor;
		
		
			}
			else if(face==Renderer3D.CAR_LEFT) {
				
				xDirection=zVersor;
				yDirection=yVersor;
		
			}
			else if(face==Renderer3D.CAR_RIGHT) {
				
				xDirection=zMinusVersor;
				yDirection=yVersor;
		
				deltaWidth=cm.getDeltaX2();
				rotateOrigin=point001;
			}
			
			
			
			decomposeClippedPolygonIntoZBuffer(polRotate,selected,texture,zBuffer,xDirection,yDirection,rotateOrigin,deltaTexture+deltaWidth,deltaHeight,cm.hashCode());
	}
	
	private void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color selected,Texture texture,ZBuffer zbuffer,int hashCode){
	   	 
	    	Point3D origin=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
	    	decomposeClippedPolygonIntoZBuffer(p3d, selected, texture,zbuffer,null,null,origin,0,0,hashCode);

	 }

	

    private void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color selected,Texture texture,ZBuffer zbuffer,
			Point3D xDirection,Point3D yDirection,Point3D origin,int deltaX,int deltaY,int hashCode){		

		Point3D normal=Polygon3D.findNormal(p3d);

        int rgb=-1;
        if(selected!=null)
        	rgb=selected.getRGB();

		if(texture!=null && xDirection==null && yDirection==null){

			Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
			Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
			xDirection=(p1.substract(p0)).calculateVersor();

			yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();

			//yDirection=Point3D.calculateOrthogonal(xDirection);
		}

		Polygon3D[] triangles = Polygon3D.divideIntoTriangles(p3d);
		
		for(int i=0;i<triangles.length;i++){
			
			BarycentricCoordinates bc=new BarycentricCoordinates(triangles[i]);
			
			//Polygon3D clippedPolygon=Polygon3D.clipPolygon3DInY(triangles[i],(int) (Renderer3D.SCREEN_DISTANCE*2.0/3.0));

			//if(clippedPolygon.npoints==0)
			//	return ;
			
			Polygon3D[] clippedTriangles = Polygon3D.divideIntoTriangles(triangles[i]);
			
			for (int j = 0; j < clippedTriangles.length; j++) {
						
				
				decomposeTriangleIntoZBufferEdgeWalking( clippedTriangles[j],rgb, texture,zbuffer, xDirection,yDirection,origin, deltaX, deltaY,bc,hashCode);
				
			}

		

		}

	}
    
	/**
	 * 
	 * DECOMPOSE PROJECTED TRIANGLE USING EDGE WALKING AND
	 * PERSPECTIVE CORRECT MAPPING
	 * 
	 * @param p3d
	 * @param color
	 * @param texture
	 * @param useLowResolution
	 * @param xDirection
	 * @param yDirection
	 * @param origin 
	 */
	public void decomposeTriangleIntoZBufferEdgeWalking(Polygon3D p3d,int selected,Texture texture,ZBuffer zb,  
			Point3D xDirection, Point3D yDirection, Point3D origin,int deltaX,int deltaY,
			BarycentricCoordinates bc,int hashCode) {

		int rgbColor=selected;
		
		rr=a2*(selected>>16 & mask);
		gg=a2*(selected>>8 & mask);
		bb=a2*(selected & mask);

		Point3D normal=Polygon3D.findNormal(p3d).calculateVersor();
		
		//boolean isFacing=isFacing(p3d,normal,observerPoint);

		int level=p3d.getLevel();
	
		double cosin=calculateCosin(p3d);
		
		
		Point3D po0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D po1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D po2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);
	
		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);
		Point3D p2=new Point3D(p3d.xpoints[2],p3d.ypoints[2],p3d.zpoints[2]);

		p0.rotate(POSX,POSY,cosf,sinf);
		p1.rotate(POSX,POSY,cosf,sinf);
		p2.rotate(POSX,POSY,cosf,sinf);

		double x0=(int)convertX(p0.x,p0.y,p0.z);
		double y0=(int)convertY(p0.x,p0.y,p0.z);
		double z0=p0.y;

		double x1=(int)convertX(p1.x,p1.y,p1.z);
		double y1=(int)convertY(p1.x,p1.y,p1.z);
		double z1=p1.y;

		
		double x2=(int)convertX(p2.x,p2.y,p2.z);
		double y2=(int)convertY(p2.x,p2.y,p2.z);
		double z2=p2.y;

		
		//check if triangle is visible
		double maxX=Math.max(x0,x1);
		maxX=Math.max(x2,maxX);
		double maxY=Math.max(y0,y1);
		maxY=Math.max(y2,maxY);
		double minX=Math.min(x0,x1);
		minX=Math.min(x2,minX);
		double minY=Math.min(y0,y1);
		minY=Math.min(y2,minY);
		
		if(maxX<0 || minX>WIDTH || maxY<0 || minY>HEIGHT)
			return;

		Point3D[] points=new Point3D[3];

		points[0]=new Point3D(x0,y0,z0,p0.x,p0.y,p0.z);
		points[1]=new Point3D(x1,y1,z1,p1.x,p1.y,p1.z);
		points[2]=new Point3D(x2,y2,z2,p2.x,p2.y,p2.z);

		
		if(texture!=null){
			
			int w=texture.getWidth();
			int h=texture.getHeight();
			
			Point3D pt0=bc.pt0;
			Point3D pt1=bc.pt1;
			Point3D pt2=bc.pt2;
			
			Point3D p=bc.getBarycentricCoordinates(new Point3D(po0.x,po0.y,po0.z));
			double x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			double y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);
			points[0].setTexurePositions(x,texture.getHeight()-y);
			
			
			p=bc.getBarycentricCoordinates(new Point3D(po1.x,po1.y,po1.z));
			x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);	
			points[1].setTexurePositions(x,texture.getHeight()-y);
			
			
			p=bc.getBarycentricCoordinates(new Point3D(po2.x,po2.y,po2.z));
			x= (p.x*(pt0.x)+p.y*pt1.x+(1-p.x-p.y)*pt2.x);
			y= (p.x*(pt0.y)+p.y*pt1.y+(1-p.x-p.y)*pt2.y);
			points[2].setTexurePositions(x,texture.getHeight()-y);
				
		}
		
		int upper=0;
		int middle=1;
		int lower=2;

		for(int i=0;i<3;i++){

			if(points[i].y>points[upper].y)
				upper=i;

			if(points[i].y<points[lower].y)
				lower=i;

		}
		for(int i=0;i<3;i++){

			if(i!=upper && i!=lower)
				middle=i;
		}


		//double i_depth=1.0/zs;
		//UPPER TRIANGLE
		
		Point3D lowP=points[lower];
		Point3D upP=points[upper];
		Point3D midP=points[middle];
		
		int j0=midP.y>0?(int)midP.y:0;
		int j1=upP.y<HEIGHT?(int)upP.y:HEIGHT;

		for(int j=j0;j<j1;j++){

			double middlex=Point3D.foundXIntersection(upP,lowP,j);
			Point3D intersects = foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(upP,midP,j);
			Point3D intersecte = foundPX_PY_PZ_TEXTURE_Intersection(upP,midP,j);
	
			Point3D pstart=new Point3D(middlex,j,intersects.p_z,intersects.p_x,intersects.p_y,intersects.p_z,intersects.texture_x,intersects.texture_y);
			Point3D pend=new Point3D(middlex2,j,intersecte.p_z,intersecte.p_x,intersecte.p_y,intersecte.p_z,intersecte.texture_x,intersecte.texture_y);
			
			
			//pstart.p_y=pstart.p_x*projectionNormal.x+pstart.p_y*projectionNormal.y+pstart.p_z*projectionNormal.z;
			//pend.p_y=pend.p_x*projectionNormal.x+pend.p_y*projectionNormal.y+pend.p_z*projectionNormal.z;

			if(pstart.x>pend.x){

				Point3D swap= pend;
				pend= pstart;
				pstart=swap;

			}

			int start=(int)pstart.x;
			int end=(int)pend.x;



			double inverse=1.0/(end-start);


			int i0=start>0?start:0;

			for(int i=i0;i<end;i++){

				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i;

				double l=(i-start)*inverse;

				double yi=((1-l)*pstart.p_y+l*pend.p_y);
				double zi=((1-l)*pstart.p_z+l*pend.p_z);
		
				if(!zb.isToUpdate(yi,zi,tot,level,hashCode)){
				
					continue;
				}	

				
				double xi=((1-l)*pstart.p_x+l*pend.p_x);  
				
                double texture_x=(1-l)*pstart.texture_x+l*pend.texture_x;
                double texture_y=(1-l)*pstart.texture_y+l*pend.texture_y;

			

				if(texture!=null)
					rgbColor=texture.getRGB((int)texture_x,(int) texture_y);  
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin,deltaX, deltaY);
				if(rgbColor==greenRgb)
					continue;
				
				if(selected>-1){
					

					
				    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
				    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
				    int b=(int) (a1*(rgbColor & mask)+bb);
					
					rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
				}

				//System.out.println(x+" "+y+" "+tot);    	
				
				zb.set(xi,yi,zi,yi,calculateShadowColor(xi,yi,zi,cosin,rgbColor,p3d.isFilledWithWater()),level,tot,hashCode);
				
				
			}


		}
		//LOWER TRIANGLE
		j0=lowP.y>0?(int)lowP.y:0;
		j1=midP.y<HEIGHT?(int)midP.y:HEIGHT;
		
		for(int j=j0;j<j1;j++){
		
			double middlex=Point3D.foundXIntersection(upP,lowP,j);
			
			Point3D intersects = foundPX_PY_PZ_TEXTURE_Intersection(upP,lowP,j);

			double middlex2=Point3D.foundXIntersection(lowP,midP,j);
			
			Point3D insersecte = foundPX_PY_PZ_TEXTURE_Intersection(lowP,midP,j);
			
			Point3D pstart=new Point3D(middlex,j,intersects.p_z,intersects.p_x,intersects.p_y,intersects.p_z,intersects.texture_x,intersects.texture_y);
			Point3D pend=new Point3D(middlex2,j,insersecte.p_z,insersecte.p_x,insersecte.p_y,insersecte.p_z,insersecte.texture_x,insersecte.texture_y);


			//pstart.p_y=pstart.p_x*projectionNormal.x+pstart.p_y*projectionNormal.y+pstart.p_z*projectionNormal.z;
			//pend.p_y=pend.p_x*projectionNormal.x+pend.p_y*projectionNormal.y+pend.p_z*projectionNormal.z;

			
			if(pstart.x>pend.x){


				Point3D swap= pend;
				pend= pstart;
				pstart=swap;

			}

			int start=(int)pstart.x;
			int end=(int)pend.x;

			double inverse=1.0/(end-start);

			int i0=start>0?start:0;

			for(int i=i0;i<end;i++){
				
				if(i>=WIDTH)
					break;

				int tot=WIDTH*j+i; 

				double l=(i-start)*inverse;

				double yi=((1-l)*pstart.p_y+l*pend.p_y);
				double zi=((1-l)*pstart.p_z+l*pend.p_z);
			
				if(!zb.isToUpdate(yi,zi,tot,level,hashCode) ){
					
					continue;
				}	


				
				double xi=((1-l)*pstart.p_x+l*pend.p_x);  

                double texture_x=(1-l)*pstart.texture_x+l*pend.texture_x;
                double texture_y=(1-l)*pstart.texture_y+l*pend.texture_y;


				if(texture!=null)
					//rgbColor=ZBuffer.pickRGBColorFromTexture(texture,xi,yi,zi,xDirection,yDirection,origin, deltaX,deltaY);
					rgbColor=texture.getRGB((int)texture_x,(int) texture_y);   
				if(rgbColor==greenRgb)
					continue;
				
				if(selected>-1){
					

					
				    int r=(int) (a1*(rgbColor>>16 & mask)+rr);
				    int g=(int) (a1*(rgbColor>>8 & mask)+gg);
				    int b=(int) (a1*(rgbColor & mask)+bb);
					
					rgbColor= (255 << 32) + (r << 16) + (g << 8) + b;
				}

				//System.out.println(x+" "+y+" "+tot);


			
				zb.set(xi,yi,zi,yi,calculateShadowColor(xi,yi,zi,cosin,rgbColor,p3d.isFilledWithWater()),level,tot,p3d.hashCode());
				
			}


		}	




	}
	

	
	public double calculateCosin(Polygon3D polReal) {
		
		
		Point3D normal = Polygon3D.findNormal(polReal);
		
		double cosin=Point3D.calculateCosin(normal,lightDirection);
		
        return cosin; 

		
	
	}
	
	public int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs,boolean hasWater) {

		
		
		double factor=(1*(0.75+0.25*cosin));
		
		int alphas=0xff & (argbs>>24);
		int rs = 0xff & (argbs>>16);
		int gs = 0xff & (argbs >>8);
		int bs = 0xff & argbs;
		
		//water effect
		if(hasWater && zi<Road.WATER_LEVEL+MOVZ){		
			//factor=factor*0.7;	
		    rs=gs=0;
		}

		rs=(int) (factor*rs);
		gs=(int) (factor*gs);
		bs=(int) (factor*bs);
		
		return alphas <<24 | rs <<16 | gs <<8 | bs;
	
	}

	private Point3D buildTransformedPoint(Point3D point) {
	
		Point3D newPoint=new Point3D();
	
	
	
		double x=point.x-POSX;
		double y=point.y-POSY;
	
		newPoint.x=(int) (viewDirectionCos*x+viewDirectionSin*y);
		newPoint.y=(int) (viewDirectionCos*y-viewDirectionSin*x);	
		newPoint.z=point.z+MOVZ;
	
		return newPoint;
	}

	private Point3D buildTransformedVersor(Point3D point) {

		Point3D newPoint=new Point3D();

	
			
		double x=point.x;
		double y=point.y;
		double z=point.z;
			
		newPoint.x= (viewDirectionCos*x+viewDirectionSin*y);
		newPoint.y= (viewDirectionCos*y-viewDirectionSin*x);	
		newPoint.z=z;

		return newPoint;
	}

	private void buildTransformedPolygon(Polygon3D base) {
	
	
	
		for(int i=0;i<base.npoints;i++){
	
	
			double x=base.xpoints[i]-POSX;
			double y=base.ypoints[i]-POSY;
	
			base.xpoints[i]=(int) (viewDirectionCos*x+viewDirectionSin*y);
			base.ypoints[i]=(int) (viewDirectionCos*y-viewDirectionSin*x);	
	
			base.zpoints[i]=base.zpoints[i]+MOVZ;
	
		}
	
	
	}
	
    public ArrayList<LineData> getClickedPolygons(int x, int y, PolygonMesh mesh) {
    	
    	return selectPolygons(x,y,mesh,false);
    }

    public boolean selectPolygons(int x, int y, PolygonMesh mesh) {
    	
    	ArrayList<LineData> vec=selectPolygons(x,y,mesh,true);
    	
    	return vec!=null && vec.size()>0;
    	
    }

    public ArrayList<LineData> selectPolygons(int x, int y, PolygonMesh mesh,
    		boolean toSelect) {

    	ArrayList<LineData> ret=new ArrayList<LineData>();

    	if(mesh==null)
    		return ret;



    	ArrayList<PolygonToOrder> selectablePolygons=new ArrayList<PolygonToOrder>();

    	int sizel=mesh.polygonData.size();

    	for(int i=0;i<sizel;i++){


    		LineData ld=(LineData) mesh.polygonData.get(i);
    		
    		
    		Polygon3D polReal= buildTranslatedPolygon3D(ld,mesh.points,0,mesh.getLevel());

    		Polygon3D polProjectd=builProjectedPolygon(polReal);
           
    		if(polProjectd.contains(x,y)){

    			PolygonToOrder pot=new PolygonToOrder(ld,Polygon3D.findCentroid(polReal),i);
    			selectablePolygons.add(pot);

    		}


    	}	



    	PolygonToOrder selectedPolygonOrder=null;
    	double maxDistance=0;

    	/////here calculate the nearest block.

    	for (int i = 0; i < selectablePolygons.size(); i++) {
    		PolygonToOrder pot = (PolygonToOrder) selectablePolygons.get(i);

    		if(i==0){
    			selectedPolygonOrder=pot;
    			maxDistance=Point3D.calculateDotProduct(pot.getCentroid(),projectionNormal);

    		}else{

    			double distance=Point3D.calculateDotProduct(pot.getCentroid(),projectionNormal);
    			if(distance>maxDistance){

    				selectedPolygonOrder=pot;
    				maxDistance=distance;

    			}

    		}
    	}

    	/////


    	for(int i=0;i<sizel;i++){ 


    		LineData ld=(LineData) mesh.polygonData.get(i);



    		if(selectedPolygonOrder!=null && i==selectedPolygonOrder.getIndex()){

    			if(toSelect){
    				//editor.setCellPanelData(ld);							
    				ld.setSelected(true);
    				
    				for(int k=0;k<editor.chooseTexture[editor.getACTIVE_PANEL()].getItemCount();k++){

    					ValuePair vp=(ValuePair) editor.chooseTexture[editor.getACTIVE_PANEL()].getItemAt(k);
    					if(vp.getId().equals(""+ld.getTexture_index())) 
    						editor.chooseTexture[editor.getACTIVE_PANEL()].setSelectedItem(vp);
    				}
    				
    				editor.fillWithWater[editor.getACTIVE_PANEL()].setSelected(ld.isFilledWithWater());
    				
    			}				

    			ret.add(ld);
    			
    			
    			

    		}else if(!editor.checkMultiplePointsSelection[editor.getACTIVE_PANEL()].isSelected()){

    			if(toSelect){
    				ld.setSelected(false);

    			}	
    		}


    	}

    	return ret;


    }
    

    public void selectObjects(int x, int y, ArrayList<DrawObject> drawObjects) {
 
    	
    	for (int i = 0; i < drawObjects.size(); i++) {

			DrawObject dro=(DrawObject) drawObjects.get(i);

			boolean selected=selectObject(x,y,dro);

			if(selected){

				dro.setSelected(true);
				editor.setObjectData(dro);


			}
			else if(!editor.checkMultipleObjectsSelection.isSelected())
				dro.setSelected(false);

		}
    }
    

    private boolean selectObject(int x, int y, DrawObject dro) {
		
		
		CubicMesh cm = (CubicMesh) dro.getMesh();
		
		int polSize=cm.polygonData.size();	
		for(int i=0;i<polSize;i++){


			LineData ld=cm.polygonData.get(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld,cm.getLevel());
			polRotate.setShadowCosin(ld.getShadowCosin());

			buildTransformedPolygon(polRotate);			
			
			Polygon3D poly = builProjectedPolygon(polRotate);			
			
			if(poly.contains(x,y)){
				
				return true;
				
			}
			
		}	
		
		return false;
	}
	

	public boolean selectSPNodes(int x, int y, ArrayList<SPLine> splines) {
	
		boolean isToselect=true;
		
		for (int i = 0; i < splines.size(); i++) {
			
			SPLine spline = (SPLine) splines.get(i);
			
			ArrayList<SPNode> nodes = spline.getNodes();
			
			if(nodes==null)
				continue;
			
			int sz=nodes.size();
			
			for(int j=0;j<sz;j++){
			
				SPNode spnode = (SPNode) nodes.get(j);
				PolygonMesh circle = spnode.getCircle();
				
				for (int k = 0; k < circle.polygonData.size(); k++) {
					
					LineData ld=(LineData) circle.polygonData.get(k);
				    Polygon3D polReal= buildTranslatedPolygon3D(ld,circle.points,0,0);
				    Polygon3D polProjectd=builProjectedPolygon(polReal);
				   // System.out.println(k+" "+pol);
				    if(polProjectd.contains(x,y)){
				    	
				    	if(isToselect){
				    		
				    		spnode.setSelected(true);
				    		editor.setSPLineData(spline,spnode);
				    	
				    		
				    		break;
				    	}
				    	
				    }else{
				    	
				    	if(!editor.checkMultiplePointsSelection[editor.getACTIVE_PANEL()].isSelected())
						   	spnode.setSelected(false);
				    	
				    
				    }	
				}
				
			
		    
			}
	
		}
		
		return false;
	}


	public Polygon3D builProjectedPolygon(Polygon3D p3d) {

		
		Polygon3D pol=new Polygon3D();
		
		int size=p3d.npoints;


		for(int i=0;i<size;i++){


	
			double x=p3d.xpoints[i];
			double y=p3d.ypoints[i];
			double z=p3d.zpoints[i];
			
			Point3D p=new Point3D(x,y,z);
			
			p.rotate(POSX,POSY,cosf,sinf);
			
			int xx=convertX(p.x,p.y,p.z);
			int yy=convertY(p.x,p.y,p.z);	
		
			
			pol.addPoint(xx,yy);
			

		}
		
		return pol;
	}

	private Polygon3D buildTranslatedPolygon3D(LineData ld,Point3D[] points,int index,int level) {



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];
		int[] cxtr=new int[size];
		int[] cytr=new int[size];
		
		for(int i=0;i<size;i++){


			LineDataVertex ldv=(LineDataVertex) ld.getItem(i);
			int num=ldv.getVertex_index();

			Point4D p=(Point4D) points[num];

			//real coordinates

			cxr[i]=(int)(p.x)-POSX;
			cyr[i]=(int)(p.y)-POSY;
			czr[i]=(int)(p.z)+MOVZ;
			cxtr[i]=(int) ldv.getVertex_texture_x();
			cytr[i]=(int) ldv.getVertex_texture_y();
			
			//if(index==1)
			//	czr[i]+=Road.ROAD_THICKNESS;

		}



		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr,cxtr,cytr);
		p3dr.setHexColor(ld.getHexColor());
		p3dr.setIndex(ld.getTexture_index());
		p3dr.setLevel(level);
		p3dr.setIsFilledWithWater(ld.isFilledWithWater());
		p3dr.setWaterPolygon(ld.isWaterPolygon());
		return p3dr;

	}


	public void propertyChange(PropertyChangeEvent evt) {
		//System.out.println(evt.getPropertyName());
		if("RoadEditorUndo".equals(evt.getPropertyName()) 
				|| "RoadAltimetryUndo".equals(evt.getPropertyName()) 
				|| "RoadEditorUpdate".equals(evt.getPropertyName())
		)
		{
			/*this.meshes=roadEditor.meshes;
		
			draw();*/
		}
		
	

		
	}

	
	
	public int convertX(double sx,double sy,double sz){

		
		//return x0+(int) (deltax*(sy-sx*sinAlfa));//axonometric formula
		return x0+(int) ((sx*sinAlfa-sy*sinAlfa)/deltay);
	}

	public int convertY(double sx,double sy,double sz){

		
		//return y0+(int) (deltay*(sz-sx*cosAlfa));
		return y0-(int) ((sz+sy*cosAlfa+sx*cosAlfa)/deltay);
	}

	@Override
	public void translate(int i, int j) {
		
		POSX+=i*xMovement*deltax;
		POSY-=j*yMovement*deltax;
	}
	

	public void gotoPosition(int goPOSX, int goPOSY) {
		POSX=goPOSX;
		POSY=goPOSY;
		editor.draw();
		
	}
	
	public void zoom(int i) {
		
		double alfa=1.0;
		
		if(i<0){
			
			alfa=2.0;
			
			deltax=deltax*alfa;
			deltay=deltay*alfa;
			
		}	
		else if(i>0){
			
			alfa=0.5;
			
			deltax=deltax*alfa;
			deltay=deltay*alfa;
			
		}
		
		x0+=(WIDTH*0.5-x0)*(1.0-1.0/alfa);
		y0+=(HEIGHT*0.5-y0)*(1.0-1.0/alfa);
	}
	
	public void mouseDown() {
		y0+=xMovement;
		
	}


	public void mouseUp() {
		y0-=yMovement;
		
	}
	
	private class PolygonToOrder{
		
		private LineData polygon=null;
		private Point3D centroid=null;
		private int index=-1;
		private int indexZ=-1;
		
		private PolygonToOrder(LineData polygon, Point3D centroid, int index) {

			this.polygon = polygon;
			this.centroid = centroid;
			this.index = index;

		}

		public Point3D getCentroid() {
			return centroid;
		}

		public void setCentroid(Point3D centroid) {
			this.centroid = centroid;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndexZ() {
			return indexZ;
		}

		public void setIndexZ(int indexZ) {
			this.indexZ = indexZ;
		}



		public LineData getPolygon() {
			return polygon;
		}



		public void setPolygon(LineData polygon) {
			this.polygon = polygon;
		}
	
		
		
	}	

	public void rotate(int signum) {

		double df=0.1;
		fi+=df*signum;
		sinf=Math.sin(fi);
		cosf=Math.cos(fi);

	}


	@Override
	public void displayStartPosition(ZBuffer landscapeZbuffer, Point3D startPosition) {
		
		if(startPosition==null)
			return;

		PolygonMesh ring = EditorData.getRing(startPosition.getX(),startPosition.getY(),25,40,50);	
		

		int lsize=ring.polygonData.size();
		

		for(int j=0;j<lsize;j++){
			
			
			LineData ld=(LineData) ring.polygonData.get(j);

	
			Polygon3D p3D=buildTranslatedPolygon3D(ld,ring.points,DrivingFrame.ROAD_INDEX,ring.getLevel());
			
			decomposeClippedPolygonIntoZBuffer(p3D,Color.CYAN,EditorData.cyanTexture,landscapeZbuffer,ZBuffer.EMPTY_HASH_CODE);
	

		} 
	
		
	}

	@Override
	public int getPOSX() {
		return POSX;
	}

	@Override
	public int getPOSY() {
		return POSY;
	}

	@Override
	double getDeltaY() {
		return deltay;
	}

	@Override
	double getDeltaX() {
		return deltax;
	}

	@Override
	public void changeMotionIncrement(int i) {
		if(i>0){
			
			xMovement=2*xMovement;
			yMovement=2*yMovement;
			
		}else{
			
			if(xMovement==minMovement)
				return;
			
			xMovement=xMovement/2;
			yMovement=yMovement/2;
			
		}
		
	}

}
