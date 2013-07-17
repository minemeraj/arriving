package com.editors.road;
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
	
	public Vector points=new Vector();
	public Vector lines=new Vector();
	
	
	public RoadEditorPreviewPanel( RoadEditor roadEditor) {
		
		super();
		this.roadEditor=roadEditor;
	
		this.lines=roadEditor.lines[roadEditor.ACTIVE_PANEL];
		this.points=roadEditor.points[roadEditor.ACTIVE_PANEL];
		
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
		
		int sizel=lines.size();

		for(int j=0;j<sizel;j++){


			LineData ld=(LineData) lines.elementAt(j);

			Polygon3D p3D=buildTranslatedPolygon3D(ld,points);

			decomposeClippedPolygonIntoZBuffer(p3D,ZBuffer.fromHexToColor(p3D.getHexColor()),worldTextures[p3D.getIndex()],roadZbuffer);


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
	
	/*public Polygon3D buildTranslatedPolygon3D(int i,Point4D[] is,  Point4D[] is2){

		int[] cx=new int[4];
		int[] cy=new int[4];
		int[] cz=new int[4];	

		cx[0]=(int) (is[i].x-POSX);
		cy[0]=(int) (is[i].y-POSY);
		cz[0]=(int) (is[i].z+MOVZ);
		cx[1]=(int) (is[i+1].x-POSX);
		cy[1]=(int) (is[i+1].y-POSY);
		cz[1]=(int) (is[i+1].z+MOVZ);
		cx[2]=(int) (is2[i+1].x-POSX);
		cy[2]=(int) (is2[i+1].y-POSY);
		cz[2]=(int) (is2[i+1].z+MOVZ);
		cx[3]=(int) (is2[i].x-POSX);
		cy[3]=(int) (is2[i].y-POSY);
		cz[3]=(int) (is2[i].z+MOVZ);
		
		Polygon3D p3D=new Polygon3D(4,cx,cy,cz);

		p3D.setHexColor(is[i].getHexColor());
		p3D.setIndex(is[i].getIndex());
		
		return p3D;
	}*/



	private Polygon3D buildTranslatedPolygon3D(LineData ld,Vector points) {



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
			this.lines=roadEditor.lines[roadEditor.ACTIVE_PANEL];
			this.points=roadEditor.points[roadEditor.ACTIVE_PANEL];
			draw();
		}
		
	

		
	}

}
