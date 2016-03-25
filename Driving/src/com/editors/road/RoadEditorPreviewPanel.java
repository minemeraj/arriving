package com.editors.road;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

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
import com.editors.EditorData;
import com.editors.EditorPreviewPanel;
import com.main.Renderer3D;
import com.main.Road;



/*
 * Created on 24/ott/09
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class RoadEditorPreviewPanel extends EditorPreviewPanel implements KeyListener, PropertyChangeListener,MouseWheelListener{



	private RoadEditor roadEditor=null;
	
	public static final int NUM_WORLD_TEXTURES = 12;
	public static Texture[] worldTextures;	
	
	protected static CubicMesh[] objectMeshes=null;
	protected static Texture[] objectTextures=null;
	
	public int NX=2;
	public int NY=80;
	
	
	public int POSX=0;
	public int POSY=0;
	public int MOVZ=0;
	
	public PolygonMesh[] meshes=new PolygonMesh[2];
	Vector drawObjects=null;
	
	public double viewDirection=0;	
	public double viewDirectionCos=1.0;
	public double viewDirectionSin=0.0;	
	
	
	public RoadEditorPreviewPanel( RoadEditor roadEditor) {
		
		super();
		this.roadEditor=roadEditor;
	
		this.meshes=roadEditor.meshes;		
		this.drawObjects=roadEditor.getDrawObjects();
			
		this.roadEditor=roadEditor;
		roadEditor.addPropertyChangeListener(this);
		
		addKeyListener(this);
		addMouseWheelListener(this);
		setVisible(true);
		initialize();
	}
	
	@Override
	public void initialize() {

		super.initialize();

		deltax=4;
		deltay=4;

		POSX=0;
		POSY=1000;

		try {


			worldTextures=EditorData.worldTextures;
			objectMeshes=EditorData.objectMeshes;
			objectTextures=EditorData.objectTextures;



		} catch (Exception e) {
			e.printStackTrace();
		}

	}




	@Override
	public void draw() {
		
				


		drawRoad(buf);
		super.draw();
	
		
		
	}


	private void drawRoad(BufferedImage buf) {
		
		
		
		for(int index=0;index<2;index++){
		
			PolygonMesh mesh=meshes[index];
			
			int hashCode=mesh.hashCode();
			
			int sizel=mesh.polygonData.size();
	
			for(int j=0;j<sizel;j++){
	
	
				LineData ld=(LineData) mesh.polygonData.elementAt(j);
	
				Polygon3D p3D=buildTranslatedPolygon3D(ld,mesh.points,index);
	
				decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),worldTextures[p3D.getIndex()],roadZbuffer,hashCode);
				
			    if(index==1){
			    	
			    	//build road polgyons
			    	
			    	Polygon3D[] polygons=Road.buildAdditionalRoadPolygons(p3D);
			    	
			    	for (int i = 0; i < polygons.length; i++) {
							decomposeClippedPolygonIntoZBuffer(polygons[i],Color.DARK_GRAY,null,roadZbuffer,hashCode);
					}
			    	
			    }
	
	
			}
		
		}
		
		drawObjects(drawObjects,null,roadZbuffer);

		buildScreen(buf); 
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
	    
		decomposeCubicMesh((CubicMesh) mesh,objectTextures[dro.getIndex()],zbuffer);
		
	}
	
	public void decomposeCubicMesh(CubicMesh cm, Texture texture,ZBuffer zBuffer){
		
		
		
		Point3D point000=buildTransformedPoint(cm.point000);				

		Point3D point011=buildTransformedPoint(cm.point011);

		Point3D point001=buildTransformedPoint(cm.point001);
			
		Point3D xVersor=buildTransformedVersor(cm.getXAxis());
		Point3D yVersor=buildTransformedVersor(cm.getYAxis());
		
		Point3D zVersor=buildTransformedVersor(cm.getZAxis());
		Point3D zMinusVersor=new Point3D(-zVersor.x,-zVersor.y,-zVersor.z);


		
		int polSize=cm.polygonData.size();	
		for(int i=0;i<polSize;i++){



			int due=(int)(255-i%15);			
			Color col=new Color(due,0,0);

			LineData ld=cm.polygonData.elementAt(i);
			Polygon3D polRotate=PolygonMesh.getBodyPolygon(cm.points,ld,cm.getLevel());
			polRotate.setShadowCosin(ld.getShadowCosin());


			int face=cm.boxFaces[i];

			buildTransformedPolygon(polRotate);

			decomposeCubiMeshPolygon(polRotate,xVersor,yVersor,zVersor,zMinusVersor,cm,point000,point011,point001,face,col,texture,zBuffer);



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
			Color col, 
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
			
			
			
			decomposeClippedPolygonIntoZBuffer(polRotate,col,texture,zBuffer,xDirection,yDirection,rotateOrigin,deltaTexture+deltaWidth,deltaHeight,hashCode());
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
	@Override
	public void keyPressed(KeyEvent arg0) {

		

		int code =arg0.getKeyCode();

		if(code==KeyEvent.VK_LEFT  )
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
		}
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
			
			//old road translation
			//if(index==1)
			//	czr[i]+=Road.ROAD_THICKNESS;

		}



		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr,cxtr,cytr);
		p3dr.setHexColor(ld.getHexColor());
		p3dr.setIndex(ld.getTexture_index());
		p3dr.setIsFilledWithWater(ld.isFilledWithWater());
		return p3dr;

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		//System.out.println(evt.getPropertyName());
		if("RoadEditorUndo".equals(evt.getPropertyName()) 
				|| "RoadAltimetryUndo".equals(evt.getPropertyName()) 
				|| "RoadEditorUpdate".equals(evt.getPropertyName())
		)
		{
			this.meshes=roadEditor.meshes;
		
			draw();
		}
		
	

		
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
		int pix=arg0.getUnitsToScroll();
		if(pix>0) up();
		else down();
		
		draw();
	}


	private void down() {
		y0+=5;
		
	}


	private void up() {
		y0-=5;
		
	}


}
