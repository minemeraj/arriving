package com.editors.road.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.beans.PropertyChangeEvent;
import java.util.Vector;

import com.BarycentricCoordinates;
import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.LineDataVertex;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.ValuePair;
import com.editors.road.RoadEditor;
import com.main.Renderer3D;
import com.main.Road;



public class RoadEditorIsoPanel extends RoadEditorPanel{

	
	int MOVX=-50;
	int MOVY=100;

	int dx=2;
	int dy=2;

	
	public double deltay=1;
	public double deltax=1;
	
	public int y0=400;
	public int x0=100;
	
	public int POSX=0;
	public int POSY=0;
	public int MOVZ=0;
	
	public double alfa=Math.PI/3;
	public double cosAlfa=Math.cos(alfa);
	public double sinAlfa=Math.sin(alfa);
	
	Point3D projectionNormal=new Point3D(-1/Math.sqrt(3),-1/Math.sqrt(3),1/Math.sqrt(3));
	
	public double viewDirection=0;	
	public double viewDirectionCos=1.0;
	public double viewDirectionSin=0.0;	

	public RoadEditorIsoPanel(RoadEditor editor, int cENTER_WIDTH,int cENTER_HEIGHT) {
		
		super(editor, cENTER_WIDTH,cENTER_HEIGHT);
		initialize();
	}
	

	public void drawRoad(PolygonMesh[] meshes, Vector drawObjects,ZBuffer landscapeZbuffer,Graphics2D graph) {
		
		drawRoad(meshes,landscapeZbuffer);
		drawObjects(drawObjects,null,landscapeZbuffer);
	}
	

	public void initialize() {

		deltax=4;
		deltay=4;

		POSX=0;
		POSY=1000;
		
		selectionColor=new Color(255,0,0,127);

	}


	private void drawRoad(PolygonMesh[] meshes,ZBuffer roadZbuffer) {
		
		
		
		for(int index=0;index<2;index++){
		
			PolygonMesh mesh=meshes[index];
			
			int sizel=mesh.polygonData.size();
	
			for(int j=0;j<sizel;j++){
	
	
				LineData ld=(LineData) mesh.polygonData.elementAt(j);
	
				Polygon3D p3D=buildTranslatedPolygon3D(ld,mesh.points,index);
				
				Color selected=null;
				
				if(ld.isSelected()){

					if(index<0 || index==editor.ACTIVE_PANEL){
						selected=selectionColor;
					}
				}
				
	
				decomposeClippedPolygonIntoZBuffer(p3D,selected,RoadEditor.worldTextures[p3D.getIndex()],roadZbuffer);
				
			    if(index==1){
			    	
			    	//build road polgyons
			    	
			    	Polygon3D[] polygons=Road.buildAdditionalRoadPolygons(p3D);
			    	
			    	if(selected==null)
			    		selected=Color.DARK_GRAY;
			    	
			    	for (int i = 0; i < polygons.length; i++) {
							decomposeClippedPolygonIntoZBuffer(polygons[i],selected,null,roadZbuffer);
					}
			    	
			    }
	
	
			}
		
		}
		
		

		//buildScreen(buf); 
	}

	
	public void drawObjects(Vector drawObjects,Area totalVisibleField,ZBuffer zbuffer){


        Rectangle rect = null;//totalVisibleField.getBounds();
		for(int i=0;i<drawObjects.size();i++){

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);
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
		
	    
		decomposeCubicMesh((CubicMesh) mesh,RoadEditor.objectTextures[dro.getIndex()],zbuffer,selected);
		
	}
	
	public void decomposeCubicMesh(CubicMesh cm, Texture texture,ZBuffer zBuffer,Color selected){
		
		
		
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

			LineData ld=cm.polygonData.elementAt(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld);
			polRotate.setShadowCosin(ld.getShadowCosin());


			int face=cm.boxFaces[i];

			buildTransformedPolygon(polRotate);


			decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,selected,texture,zBuffer);



		}


}


	public void decomposeCubiMeshPolygon(
			
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
			
			
			
			decomposeClippedPolygonIntoZBuffer(polRotate,selected,texture,zBuffer,xDirection,yDirection,rotateOrigin,deltaTexture+deltaWidth,deltaHeight);
	}
	
	 public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color selected,Texture texture,ZBuffer zbuffer){
	   	 
	    	Point3D origin=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
	    	decomposeClippedPolygonIntoZBuffer(p3d, selected, texture,zbuffer,null,null,origin,0,0);

	 }

	

    public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color selected,Texture texture,ZBuffer zbuffer,
			Point3D xDirection,Point3D yDirection,Point3D origin,int deltaX,int deltaY){		

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
						
				
				decomposeTriangleIntoZBufferEdgeWalking( clippedTriangles[j],rgb, texture,zbuffer, xDirection,yDirection,origin, deltaX, deltaY,bc);
				
			}

		

		}

	}

	public Point3D buildTransformedPoint(Point3D point) {
	
		Point3D newPoint=new Point3D();
	
	
	
		double x=point.x-POSX;
		double y=point.y-POSY;
	
		newPoint.x=(int) (viewDirectionCos*x+viewDirectionSin*y);
		newPoint.y=(int) (viewDirectionCos*y-viewDirectionSin*x);	
		newPoint.z=point.z+MOVZ;
	
		return newPoint;
	}

	public Point3D buildTransformedVersor(Point3D point) {

		Point3D newPoint=new Point3D();

	
			
		double x=point.x;
		double y=point.y;
		double z=point.z;
			
		newPoint.x= (viewDirectionCos*x+viewDirectionSin*y);
		newPoint.y= (viewDirectionCos*y-viewDirectionSin*x);	
		newPoint.z=z;

		return newPoint;
	}

	public void buildTransformedPolygon(Polygon3D base) {
	
	
	
		for(int i=0;i<base.npoints;i++){
	
	
			double x=base.xpoints[i]-POSX;
			double y=base.ypoints[i]-POSY;
	
			base.xpoints[i]=(int) (viewDirectionCos*x+viewDirectionSin*y);
			base.ypoints[i]=(int) (viewDirectionCos*y-viewDirectionSin*x);	
	
			base.zpoints[i]=base.zpoints[i]+MOVZ;
	
		}
	
	
	}

	public void keyPressed(KeyEvent arg0) {

		

		int code =arg0.getKeyCode();

		/*if(code==KeyEvent.VK_LEFT  )
		{ 
			POSX-=2*deltax;
			draw();
		}
		else if(code==KeyEvent.VK_RIGHT  )
		{ 
			POSX+=2*deltax;
			draw();
		}
		else if(code==KeyEvent.VK_UP  )
		{ 
			POSY+=2*deltax;
			draw();
		}
		else if(code==KeyEvent.VK_DOWN  )
		{ 
			POSY-=2*deltax;
			draw();
			
		}else if(code==KeyEvent.VK_Q  )
		{  
			
		}
		else if(code==KeyEvent.VK_W  )
		{  
		
			
		}else{
			
			super.keyPressed(arg0);
		}*/
	}

    public boolean selectPolygons(int x, int y, PolygonMesh mesh) {
    	
    	Vector vec=selectPolygons(x,y,mesh,true);
    	
    	return vec!=null && vec.size()>0;
    	
    }

    public Vector selectPolygons(int x, int y, PolygonMesh mesh,
    		boolean toSelect) {

    	Vector ret=new Vector();

    	if(mesh==null)
    		return ret;



    	Vector selectablePolygons=new Vector();

    	int sizel=mesh.polygonData.size();

    	for(int i=0;i<sizel;i++){


    		LineData ld=(LineData) mesh.polygonData.elementAt(i);
    		
    		
    		Polygon3D polReal= buildTranslatedPolygon3D(ld,mesh.points,0);

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
    		PolygonToOrder pot = (PolygonToOrder) selectablePolygons.elementAt(i);

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


    		LineData ld=(LineData) mesh.polygonData.elementAt(i);



    		if(selectedPolygonOrder!=null && i==selectedPolygonOrder.getIndex()){

    			if(toSelect){
    				//editor.setCellPanelData(ld);							
    				ld.setSelected(true);
    				
    				for(int k=0;k<editor.chooseTexture[editor.ACTIVE_PANEL].getItemCount();k++){

    					ValuePair vp=(ValuePair) editor.chooseTexture[editor.ACTIVE_PANEL].getItemAt(k);
    					if(vp.getId().equals(""+ld.getTexture_index())) 
    						editor.chooseTexture[editor.ACTIVE_PANEL].setSelectedItem(vp);
    				}
    			}				

    			ret.add(ld);
    			
    			
    			

    		}else if(!editor.checkMultiplePointsSelection[editor.ACTIVE_PANEL].isSelected()){

    			if(toSelect){
    				ld.setSelected(false);

    			}	
    		}


    	}

    	return ret;


    }
    

    public void selectObjects(int x, int y, Vector drawObjects) {
 
    	
    	for (int i = 0; i < drawObjects.size(); i++) {

			DrawObject dro=(DrawObject) drawObjects.elementAt(i);

			boolean selected=selectObject(x,y,dro);

			if(selected){

				dro.setSelected(true);
				editor.setObjectData(dro);


			}
			else if(!editor.checkMultipleObjectsSelection.isSelected())
				dro.setSelected(false);

		}
    }
    

	public boolean selectObject(int x, int y, DrawObject dro) {
		for (int j = 0; j < dro.polygons.size(); j++) {
			Polygon3D pol3D = ((Polygon3D) dro.polygons.elementAt(j)).clone();
           
			buildTransformedPolygon(pol3D);
			Polygon3D poly = builProjectedPolygon(pol3D);
			
	
			if(poly.contains(x,y)){
				
				return true;
				
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
			
			//p.rotate(POSX,POSY,cosf,sinf);
			
			int xx=convertX(p.x,p.y,p.z);
			int yy=convertY(p.x,p.y,p.z);	
		
			
			pol.addPoint(xx,yy);
			

		}
		
		return pol;
	}

	private Polygon3D buildTranslatedPolygon3D(LineData ld,Point3D[] points,int index) {



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
			
			if(index==1)
				czr[i]+=Road.ROAD_THICKNESS;

		}



		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr,cxtr,cytr);
		p3dr.setHexColor(ld.getHexColor());
		p3dr.setIndex(ld.getTexture_index());

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

	

	public void left() {
		POSX-=2*deltax;
	}
	

	public void right() {
		POSX+=2*deltax;
	}

	public void down() {
		POSY-=2*deltax;
		
	}


	public void up() {
		POSY+=2*deltax;
		
	}
	
	public void zoom(int i) {
		if(i<0){
			deltax=deltax*2;
			deltay=deltay*2;
			
		}	
		else if(i>0){
			deltax=deltax/2;
			deltay=deltay/2;
			
		}
	}
	
	public void mouseDown() {
		y0+=5;
		
	}


	public void mouseUp() {
		y0-=5;
		
	}
	
	public class PolygonToOrder{
		
		LineData polygon=null;
		Point3D centroid=null;
		int index=-1;
		int indexZ=-1;
		
		public PolygonToOrder(LineData polygon, Point3D centroid, int index) {

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
}
