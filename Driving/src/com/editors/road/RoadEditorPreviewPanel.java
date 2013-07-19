package com.editors.road;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.LineData;
import com.Point4D;
import com.Polygon3D;
import com.Texture;
import com.ZBuffer;
import com.editors.EditorPreviewPanel;
import com.main.Road;



/*
 * Created on 24/ott/09
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class RoadEditorPreviewPanel extends EditorPreviewPanel implements KeyListener, PropertyChangeListener{



	private RoadEditor roadEditor=null;
	
	public static final int NUM_WORLD_TEXTURES = 12;
	public static Texture[] worldTextures;	
	
	public int NX=2;
	public int NY=80;
	
	
	public int POSX=0;
	public int POSY=0;
	public int MOVZ=0;
	
	public Vector[] points=new Vector[2];
	public Vector[] lines=new Vector[2];
	
	
	public RoadEditorPreviewPanel( RoadEditor roadEditor) {
		
		super();
		this.roadEditor=roadEditor;
	
		this.lines=roadEditor.lines;
		this.points=roadEditor.points;
		
		this.roadEditor=roadEditor;
		roadEditor.addPropertyChangeListener(this);
		
		addKeyListener(this);
		setVisible(true);
		initialize();
	}
	
	
	public void initialize() {

		super.initialize();

		deltax=4;
		deltay=4;
		
		POSX=0;
		POSY=1000;

		try {

			File directoryImg=new File("lib");
			File[] files=directoryImg.listFiles();

			Vector vRoadTextures=new Vector();

			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("road_texture_")){

					vRoadTextures.add(files[i]);

				}		
			}

			worldTextures=new Texture[vRoadTextures.size()];


			if(isUseTextures){


				for(int i=0;i<vRoadTextures.size();i++){

					worldTextures[i]=new Texture(ImageIO.read(new File("lib/road_texture_"+i+".jpg")));
				}





			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}





	public void draw() {
		
				


		drawRoad(buf);
		super.draw();
	
		
		
	}


	private void drawRoad(BufferedImage buf) {
		
		for(int index=0;index<2;index++){
		
			int sizel=lines[index].size();
	
			for(int j=0;j<sizel;j++){
	
	
				LineData ld=(LineData) lines[index].elementAt(j);
	
				Polygon3D p3D=buildTranslatedPolygon3D(ld,points[index],index);
	
				decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),worldTextures[p3D.getIndex()],roadZbuffer);
				
			    if(index==1){
			    	
			    	//build road polgyons
			    	
			    	Polygon3D[] polygons=Road.buildAdditionalRoadPolygons(p3D);
			    	
			    	for (int i = 0; i < polygons.length; i++) {
							decomposeClippedPolygonIntoZBuffer(polygons[i],Color.DARK_GRAY,null,roadZbuffer);
					}
			    	
			    }
	
	
			}
		
		}

		buildScreen(buf); 
	}
	
	public void keyPressed(KeyEvent arg0) {
		
		super.keyPressed(arg0);
		
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
		}
	}
	




	private Polygon3D buildTranslatedPolygon3D(LineData ld,Vector points,int index) {



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];

		
		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);

			Point4D p=(Point4D) points.elementAt(num);

			//real coordinates

			cxr[i]=(int)(p.x)-POSX;
			cyr[i]=(int)(p.y)-POSY;
			czr[i]=(int)(p.z)+MOVZ;
			
			if(index==1)
				czr[i]+=Road.ROAD_THICKNESS;

		}



		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);
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
			this.lines=roadEditor.lines;
			this.points=roadEditor.points;
			draw();
		}
		
	

		
	}

}
