package com.editors.object;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.ZBuffer;
import com.editors.Editor;
import com.editors.EditorPreviewPanel;



/*
 * Created on 24/ott/09
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class ObjectEditorPreviewPanel extends EditorPreviewPanel implements KeyListener, PropertyChangeListener,MouseWheelListener {


	
	private Editor editor=null;

	
	
	public ObjectEditorPreviewPanel(Editor editor) {
		
		
		super();
		
		editor.addPropertyChangeListener(this);
		this.editor=editor;
		
		this.mesh=editor.getMeshes()[editor.getACTIVE_PANEL()];
		
		deltay=1;
		deltax=1;
		
		clonedPoints=clonePoints(mesh.xpoints,mesh.ypoints,mesh.zpoints);
		PolygonMesh pm=new PolygonMesh(clonedPoints,mesh.polygonData);
		polygons = PolygonMesh.getBodyPolygons(pm);
	
		
		addMouseWheelListener(this);
		addKeyListener(this);
		setVisible(true);
		initialize();
	}
	
	@Override
	public void initialize() {

		super.initialize();
		
		x0=300;
		y0=300;
		
		
		isUseTextures=false;

		pAsso=new Point3D(Math.cos(alfa)/s2,Math.sin(alfa)/s2,1/s2);
		
		try {

			File directoryImg=new File("lib");
			File[] files=directoryImg.listFiles();

			ArrayList<File> vRoadTextures=new ArrayList<File>();

			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("road_texture_")){

					vRoadTextures.add(files[i]);

				}		
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}




	@Override
	public void draw() {
		
				


		drawObject(buf);
		super.draw();
		
		
		
	}


	private void drawObject(BufferedImage buf) {

        

		for(int j=0;j<polygons.size();j++)	{

			Polygon3D p3D=(Polygon3D) polygons.get(j);
			decomposeClippedPolygonIntoZBuffer(p3D,Color.GRAY,null,roadZbuffer,p3D.hashCode());

		}

		buildScreen(buf); 
	}
	

	@Override
   public void decomposeClippedPolygonIntoZBuffer(Polygon3D p3d,Color color,Texture texture,ZBuffer zbuffer,
    		Point3D xDirection,Point3D yDirection,Point3D origin,int deltaX,int deltaY,int hashCode){

    	//avoid clipping:
    	Polygon3D clippedPolygon = p3d;//Polygon3D.clipPolygon3DInY(p3d,0);

    	Polygon3D[] triangles = Polygon3D.divideIntoTriangles(clippedPolygon);
    	
    	double cosin=0;

    	if(texture!=null && xDirection==null && yDirection==null){

    		Point3D p0=new Point3D(p3d.xpoints[0],p3d.ypoints[0],p3d.zpoints[0]);
    		Point3D p1=new Point3D(p3d.xpoints[1],p3d.ypoints[1],p3d.zpoints[1]);

    		xDirection=(p1.substract(p0)).calculateVersor();
    		Point3D normal=Polygon3D.findNormal(p3d);
    		yDirection=Point3D.calculateCrossProduct(normal,xDirection).calculateVersor();

    	}
    	else{
    		
    		Point3D normal=Polygon3D.findNormal(p3d);
    		cosin=Point3D.calculateCosin(normal,pAsso);
    	}
    	
    	

    	for(int i=0;i<triangles.length;i++){

    		decomposeTriangleIntoZBufferEdgeWalking( triangles[i],calculateShadowColor(cosin,color.getRGB()), texture,zbuffer,
    				xDirection,yDirection,origin, deltaX, deltaY,
    				null,hashCode);

    	}

    }



	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if( "ObjectEditorUndo".equals(evt.getPropertyName()) ||
			 "ObjectEditorUpdate".equals(evt.getPropertyName())
		)
		{
			PolygonMesh pm=editor.getMeshes()[editor.getACTIVE_PANEL()];
			this.polygons = PolygonMesh.getBodyPolygons(pm);
			
			draw();
  
		}
		
	

		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		super.keyPressed(arg0);
		
		int code =arg0.getKeyCode();
		
		if(code==KeyEvent.VK_LEFT  )
		{ 
			x0-=5/deltax;
			draw();
		}
		else if(code==KeyEvent.VK_RIGHT  )
		{ 
			x0+=5/deltax;
			draw();
		}
		else if(code==KeyEvent.VK_UP  )
		{ 
			y0+=5/deltax;
			draw();
		}
		else if(code==KeyEvent.VK_DOWN  )
		{ 
			y0-=5/deltax;
			draw();
		}
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
	
		int pix=arg0.getUnitsToScroll();
		if(pix>0) 
			y0-=5/deltax;
		else 
			y0+=5/deltax;
			
		
		draw();
		
	}	

}
