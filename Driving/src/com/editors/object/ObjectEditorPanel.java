package com.editors.object;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Polygon;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.border.Border;

import com.LineData;
import com.LineDataVertex;
import com.Point3D;
import com.PointListItem;
import com.Polygon3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;
import com.editors.EditorPanel;
import com.editors.ValuePair;
import com.editors.road.RoadEditorPolygonDetail;
import com.main.Renderer3D;

class ObjectEditorPanel extends JPanel implements EditorPanel,ActionListener,KeyListener, ItemListener{

	private final int VIEW_TYPE_3D=0;
	private final int VIEW_TYPE_TOP=1;
	private final int VIEW_TYPE_LEFT=2;
	private final int VIEW_TYPE_FRONT=3;
	private int VIEW_TYPE=VIEW_TYPE_3D;

	ObjectEditor objectEditor=null;


	protected int HEIGHT=ObjectEditor.HEIGHT;
	protected int WIDTH=ObjectEditor.WIDTH;


	private final int RIGHT_BORDER=320;
	private final int BOTTOM_BORDER=100;



	private JPanel right;
	private JTextField coordinatesx;
	private JTextField coordinatesy;
	private JTextField coordinatesz;
	private JTextField extraData=null;

	private JButton addPoint;
	private JButton deleteSelection;
	private JButton changePoint;
	private JButton deselectAll;;
	protected JCheckBox checkMultipleSelection;
	private JList pointList=null;


	//public JButton joinPoints;
	private JButton buildPolygon;


	private JPanel bottom;
	private JLabel screenPoint;
	private JCheckBox checkCoordinatesx;
	private JCheckBox checkCoordinatesz;
	private JCheckBox checkCoordinatesy;
	private JCheckBox checkExtraData;
	
	private JTextField meshDescription;


	private JList lineList;
	private JButton selectAll;
	LineData polygon=new LineData();


	private JButton rescale;
	private DoubleTextField rescaleValue;

	private DoubleTextField objMove;
	private JButton movePointUp;
	private JButton movePointDown;
	private JButton movePointLeft;
	private JButton movePointRight;
	private JButton movePointTop;
	private JButton movePointBottom;
	private JButton startBuildPolygon;
	private JButton mergeSelectedPoints;
	private JButton polygonDetail;

	private DecimalFormat dfc=null;



	JComboBox chooseFace;


	private ObjectEditor3DPanel center3D;
	private ObjectEditorTopBottomPanel centerTop;
	private ObjectEditorFrontBackPanel centerFront;
	private ObjectEditorLeftRightPanel centerLeft;
	

	private static Color BACKGROUND_COLOR=new Color(0,0,0);

	ObjectEditorPanel(ObjectEditor oe){


		this.objectEditor=oe;
		
		buildRightPanel();
		buildBottomPanel();

		setLayout(null);
		addKeyListener(this);


		center3D=new ObjectEditor3DPanel(this);
		center3D.setBackground(BACKGROUND_COLOR);
		center3D.setBounds(0,0,WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		center3D.setTransferHandler(new FileTransferhandler());
		center3D.addKeyListener(this);

		centerTop=new ObjectEditorTopBottomPanel(this);
		centerTop.setBackground(BACKGROUND_COLOR);
		centerTop.setBounds(0,0,WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		centerTop.setTransferHandler(new FileTransferhandler());
		centerTop.addKeyListener(this);

		centerLeft=new ObjectEditorLeftRightPanel(this);
		centerLeft.setBackground(BACKGROUND_COLOR);
		centerLeft.setBounds(0,0,WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		centerLeft.setTransferHandler(new FileTransferhandler());
		centerLeft.addKeyListener(this);

		centerFront=new ObjectEditorFrontBackPanel(this);
		centerFront.setBackground(BACKGROUND_COLOR);
		centerFront.setBounds(0,0,WIDTH+RIGHT_BORDER,HEIGHT+BOTTOM_BORDER);
		centerFront.setTransferHandler(new FileTransferhandler());
		centerFront.addKeyListener(this);
		
		initialize();

		add(center3D);


	}

	protected void buildRightPanel() {

		String header="<html><body>";
		String footer="</body></html>";

		right=new JPanel();
		right.setBounds(WIDTH,0,RIGHT_BORDER,HEIGHT);
		right.setLayout(null);

		int r=5;

		JLabel lx=new JLabel("x:");
		lx.setBounds(5,r,20,20);
		right.add(lx);
		coordinatesx=new DoubleTextField(8);
		coordinatesx.setBounds(30,r,150,20);
		coordinatesx.addKeyListener(this);
		right.add(coordinatesx);
		checkCoordinatesx=new JCheckBox();
		checkCoordinatesx.setBounds(200,r,30,20);
		checkCoordinatesx.addKeyListener(this);
		right.add(checkCoordinatesx);



		r+=30;

		JLabel ly=new JLabel("y:");
		ly.setBounds(5,r,20,20);
		right.add(ly);
		coordinatesy=new DoubleTextField(8);
		coordinatesy.setBounds(30,r,150,20);
		coordinatesy.addKeyListener(this);
		right.add(coordinatesy);
		checkCoordinatesy=new JCheckBox();
		checkCoordinatesy.setBounds(200,r,30,20);
		checkCoordinatesy.addKeyListener(this);
		right.add(checkCoordinatesy);


		r+=30;

		JLabel lz=new JLabel("z:");
		lz.setBounds(5,r,20,20);
		right.add(lz);
		coordinatesz=new DoubleTextField(8);
		coordinatesz.setBounds(30,r,150,20);
		coordinatesz.addKeyListener(this);
		right.add(coordinatesz);
		checkCoordinatesz=new JCheckBox();
		checkCoordinatesz.setBounds(200,r,30,20);
		checkCoordinatesz.addKeyListener(this);
		right.add(checkCoordinatesz);


		r+=30;

		JLabel ed=new JLabel("Ex:");
		ed.setBounds(5,r,20,20);
		right.add(ed);
		extraData=new JTextField();
		extraData.setBounds(30,r,150,20);
		extraData.addKeyListener(this);
		extraData.setToolTipText("Optional extra value");
		right.add(extraData);
		checkExtraData=new JCheckBox();
		checkExtraData.setBounds(200,r,30,20);
		checkExtraData.addKeyListener(this);		
		right.add(checkExtraData);

		r+=30;

		JLabel lmul=new JLabel("Multiple selection:");
		lmul.setBounds(5,r,150,20);
		right.add(lmul);		
		checkMultipleSelection=new JCheckBox();
		checkMultipleSelection.setSelected(false);
		checkMultipleSelection.addKeyListener(this);
		checkMultipleSelection.setBounds(160,r,20,20);
		right.add(checkMultipleSelection);




		r+=30;

		addPoint=new JButton(header+"<u>I</u>nsert point"+footer);
		addPoint.addKeyListener(this);
		addPoint.addActionListener(this);
		addPoint.setFocusable(false);
		addPoint.setBounds(5,r,150,20);
		right.add(addPoint);

		rescale=new JButton(header+"<u>R</u>escale all"+footer);
		rescale.addKeyListener(this);
		rescale.addActionListener(this);
		rescale.setFocusable(false);
		rescale.setBounds(160,r,150,20);
		right.add(rescale);

		rescaleValue=new DoubleTextField();
		rescaleValue.setBounds(200,r+30,70,20);
		rescaleValue.addKeyListener(this);
		rescaleValue.setToolTipText("Scale factor");
		right.add(rescaleValue);

		r+=30;

		startBuildPolygon=new JButton(header+"Start polygo<u>n</u> <br/> points sequence"+footer);
		startBuildPolygon.addActionListener(this);
		startBuildPolygon.addKeyListener(this);
		startBuildPolygon.setFocusable(false);
		startBuildPolygon.setBounds(5,r,150,35);
		right.add(startBuildPolygon);

		r+=45;

		buildPolygon=new JButton(header+"Build <u>p</u>olygon"+footer);
		buildPolygon.addActionListener(this);
		buildPolygon.addKeyListener(this);
		buildPolygon.setFocusable(false);
		buildPolygon.setBounds(5,r,150,20);
		right.add(buildPolygon);



		JLabel jlFace=new JLabel("Face:");
		jlFace.setBounds(160,r,50,20);
		right.add(jlFace);

		chooseFace=new JComboBox(); 
		chooseFace.setBounds(200,r,50,20); 
		for (int i =0; i< Renderer3D.faceIndexes.length; i++) {
			int val=Renderer3D.faceIndexes[i];
			String desc=Renderer3D.faceDesc[i];
			chooseFace.addItem(new ValuePair(""+val,""+desc));
		}
		chooseFace.addItemListener(this);
		right.add(chooseFace);

		r+=30;

		polygonDetail=new JButton(header+"Polygon detail"+footer);
		polygonDetail.addActionListener(this);
		polygonDetail.addKeyListener(this);
		polygonDetail.setFocusable(false);
		polygonDetail.setBounds(5,r,150,20);
		right.add(polygonDetail);

		JPanel movePanel=buildPointsMovePanel(185,r);
		right.add(movePanel);

		r+=30;

		deleteSelection=new JButton(header+"<u>D</u>elete selection"+footer);
		deleteSelection.addActionListener(this);
		deleteSelection.addKeyListener(this);
		deleteSelection.setFocusable(false);
		deleteSelection.setBounds(5,r,150,20);
		right.add(deleteSelection);



		r+=30;

		changePoint=new JButton(header+"<u>C</u>hange sel Point"+footer);
		changePoint.addActionListener(this);
		changePoint.addKeyListener(this);
		changePoint.setFocusable(false);
		changePoint.setBounds(5,r,150,20);
		right.add(changePoint);


		r+=30;

		deselectAll=new JButton(header+"D<u>e</u>select all"+footer);
		deselectAll.addActionListener(this);
		deselectAll.addKeyListener(this);
		deselectAll.setFocusable(false);
		deselectAll.setBounds(5,r,100,20);
		right.add(deselectAll);

		r+=30;

		selectAll=new JButton(header+"Selec<u>t</u> all"+footer);
		selectAll.addActionListener(this);
		selectAll.addKeyListener(this);
		selectAll.setFocusable(false);
		selectAll.setBounds(5,r,100,20);
		right.add(selectAll);


		mergeSelectedPoints=new JButton(header+"<u>M</u>erge selected<br/>points"+footer);
		mergeSelectedPoints.addActionListener(this);
		mergeSelectedPoints.addKeyListener(this);
		mergeSelectedPoints.setFocusable(false);
		mergeSelectedPoints.setBounds(160,r,150,35);
		right.add(mergeSelectedPoints);

		r+=40;
		
 		JLabel jLabel=new JLabel("Name:");
		jLabel.setBounds(10,r,60,20);
		right.add(jLabel);
		
		meshDescription=new JTextField();
		meshDescription.setBounds(70,r,240,20);
		meshDescription.addKeyListener(this);
		meshDescription.setToolTipText("Optional Object name");
		right.add(meshDescription);
		
		r+=30;

		right.add(buildLowerRightPanel(r));



		add(right);


	}

	void buildBottomPanel() {
		bottom=new JPanel();
		bottom.setBounds(0,HEIGHT,WIDTH+RIGHT_BORDER,BOTTOM_BORDER);
		bottom.setLayout(null);
		JLabel lscreenpoint = new JLabel();
		lscreenpoint.setText("Position x,y,z: ");
		lscreenpoint.setBounds(2,2,100,20);
		bottom.add(lscreenpoint);
		screenPoint=new JLabel();
		screenPoint.setText(",");
		screenPoint.setBounds(120,2,300,20);
		bottom.add(screenPoint);
		add(bottom);
	}

	private Component buildLowerRightPanel(int r) {
		pointList=new JList();
		JScrollPane jsp=new JScrollPane(pointList);
		JPanel lowpane=new JPanel();
		lowpane.setBounds(5,r,300,220);        
		right.add(lowpane);

		lowpane.setLayout(null);
		JLabel jlb=new JLabel("Points:");
		jlb.setBounds(20,0,100,20);
		lowpane.add(jlb);
		jsp.setBounds(0,25,140,180);
		lowpane.add(jsp);

		pointList.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e){

				PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

				if(!checkMultipleSelection.isSelected()) 
					polygon=new LineData();

				int index=pointList.getSelectedIndex();
				for(int i=0;mesh.xpoints!=null && i<mesh.xpoints.length;i++){

					Point3D p=new Point3D(mesh.xpoints[i],mesh.ypoints[i],mesh.zpoints[i]);
					if(index==i){
						mesh.selected[i]=true;
						selectPoint(p);
						polygon.addIndex(i);
					}
					else if(!checkMultipleSelection.isSelected())
						mesh.selected[i]=false;

				}

				displayAll();


			}


		}
				); 
		pointList.setFocusable(false);


		lineList=new JList();

		lineList.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e){

				PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

				int index=lineList.getSelectedIndex();
				for(int i=0;i<mesh.polygonData.size();i++){

					LineData ld=(LineData) mesh.polygonData.get(i);
					if(index==i)
						ld.setSelected(true);
					else 
						ld.setSelected(false);

				}
				deselectAllPoints();
				displayAll();


			}


		}
				); 
		lineList.setFocusable(false);

		JScrollPane jscp=new JScrollPane(lineList);
		jlb=new JLabel("Lines:");
		jlb.setBounds(170,0,100,20);
		lowpane.add(jlb);
		jscp.setBounds(150,25,150,180);
		lowpane.add(jscp);

		return lowpane;
	}

	private JPanel buildPointsMovePanel(int i, int r) {

		JPanel move=new JPanel();
		move.setBounds(i,r,100,100);
		move.setLayout(null);

		Border border = BorderFactory.createEtchedBorder();
		move.setBorder(border);

		objMove=new DoubleTextField();
		objMove.setBounds(30,40,40,20);
		objMove.setToolTipText("Position increment");
		move.add(objMove);
		objMove.addKeyListener(this);

		movePointUp=new JButton("+Z");
		movePointUp.setBorder(null);
		movePointUp.setBounds(40,10,20,20);
		movePointUp.addActionListener(this);
		movePointUp.setFocusable(false);
		move.add(movePointUp);

		movePointDown=new JButton("-Z");
		movePointDown.setBorder(null);
		movePointDown.setBounds(40,70,20,20);
		movePointDown.addActionListener(this);
		movePointDown.setFocusable(false);
		move.add(movePointDown);

		movePointLeft=new JButton("-Y");
		movePointLeft.setBorder(null);
		movePointLeft.setBounds(5,40,20,20);
		movePointLeft.addActionListener(this);
		movePointLeft.setFocusable(false);
		move.add(movePointLeft);

		movePointRight=new JButton("+Y");
		movePointRight.setBorder(null);
		movePointRight.setBounds(75,40,20,20);
		movePointRight.addActionListener(this);
		movePointRight.setFocusable(false);
		move.add(movePointRight);

		movePointTop=new JButton("+X");
		movePointTop.setBorder(null);
		movePointTop.setBounds(5,70,20,20);
		movePointTop.addActionListener(this);
		movePointTop.setFocusable(false);
		move.add(movePointTop);

		movePointBottom=new JButton("-X");
		movePointBottom.setBorder(null);
		movePointBottom.setBounds(75,70,20,20);
		movePointBottom.addActionListener(this);
		movePointBottom.setFocusable(false);
		move.add(movePointBottom);

		return move;

	}

	/**
	 * 
	 */
	@Override
	public void initialize() {


		

		DecimalFormatSymbols dfs=new DecimalFormatSymbols(Locale.UK);
		dfc=new DecimalFormat("###.##");
		dfc.setDecimalFormatSymbols(dfs);


		center3D.initialize();
		centerTop.initialize();

	}



	public Area clipPolygonToArea2D(Polygon p_in,Area area_out){


		Area area_in = new Area(p_in);

		Area new_area_out = (Area) area_out.clone();
		new_area_out.intersect(area_in);

		return new_area_out;

	}

	static Polygon3D buildPolygon(LineData ld,double[] xpoints,double[] ypoints,double[] zpoints) {



		int size=ld.size();

		int[] cxr=new int[size];
		int[] cyr=new int[size];
		int[] czr=new int[size];


		for(int i=0;i<size;i++){


			int num=ld.getIndex(i);


			//real coordinates
			cxr[i]=(int)(xpoints[num]);
			cyr[i]=(int)(ypoints[num]);
			czr[i]=(int)(zpoints[num]);
		}


		Polygon3D p3dr=new Polygon3D(size,cxr,cyr,czr);

		return p3dr;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();

		if(o==addPoint){ 
			objectEditor.prepareUndo();
			addPoint();
		}	
		else if(o==deleteSelection) {
			objectEditor.prepareUndo();
			delete();
		}	
		else if(o==changePoint){
			objectEditor.prepareUndo();
			changeSelectedPoint();
			displayAll();
		}
		else if(o==mergeSelectedPoints){

			objectEditor.prepareUndo();
			mergeSelectedPoints();

		}
		else if(o==startBuildPolygon){
			startBuildPolygon();
		}		
		else if(o==buildPolygon){
			objectEditor.prepareUndo();
			buildPolygon();
			displayAll();
		}
		else if(o==polygonDetail){
			polygonDetail();
			//displayAll();
		}
		else if(o==deselectAll){
			deselectAll();
			displayAll();
		}
		else if(o==selectAll){
			selectAllPoints();
			displayAll();
		}
		else if(o==rescale){
			objectEditor.prepareUndo();
			rescaleAllPoints();
			displayAll();
		}
		else if(o==movePointUp){
			objectEditor.prepareUndo();
			moveSelectedPoints(0,0,1);
			displayAll();
		}
		else if(o==movePointDown){
			objectEditor.prepareUndo();
			moveSelectedPoints(0,0,-1);
			displayAll();
		}
		else if(o==movePointLeft){
			objectEditor.prepareUndo();
			moveSelectedPoints(0,-1,0);
			displayAll();
		}
		else if(o==movePointRight){
			objectEditor.prepareUndo();
			moveSelectedPoints(0,+1,0);
			displayAll();
		}
		else if(o==movePointTop){
			objectEditor.prepareUndo();
			moveSelectedPoints(1,0,0);
			displayAll();
		}
		else if(o==movePointBottom){
			objectEditor.prepareUndo();
			moveSelectedPoints(-1,0,0);
			displayAll();
		}

	}

	@Override
	public void moveSelectedPoints(int dx, int dy, int dz) { 

		String sqty=objMove.getText();

		if(sqty==null || sqty.equals(""))
			return;

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		double qty=Double.parseDouble(sqty);

		for(int i=0;mesh.xpoints!=null && i<mesh.xpoints.length;i++){

			if(mesh.selected[i]){


				mesh.xpoints[i]=mesh.xpoints[i]+qty*dx;
				mesh.ypoints[i]=mesh.ypoints[i]+qty*dy;
				mesh.zpoints[i]=mesh.zpoints[i]+qty*dz;

				coordinatesx.setText(dfc.format(mesh.xpoints[i]));
				coordinatesy.setText(dfc.format(mesh.ypoints[i]));
				coordinatesz.setText(dfc.format(mesh.zpoints[i]));
			}

		}
		//deselectAll();


	}

	@Override
	public void rescaleAllPoints() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		String txt=rescaleValue.getText();

		if(txt==null || txt.equals("")){

			JOptionPane.showMessageDialog(this,"Please insert rescale factor under the button");
			return;
		}	
		double val=Double.parseDouble(txt);
		for(int i=0;mesh.xpoints!=null && i<mesh.xpoints.length;i++){


			mesh.xpoints[i]=Math.round(mesh.xpoints[i]*val);
			mesh.ypoints[i]=Math.round(mesh.ypoints[i] *val);
			mesh.zpoints[i]=Math.round(mesh.zpoints[i] *val);
		}

	}


	@Override
	public void selectAllPoints() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		for(int i=0;mesh.xpoints!=null && i<mesh.xpoints.length;i++){

			mesh.selected[i]=true;
	
		}

	}
	@Override
	public void buildPolygon() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		if(polygon.lineDatas.size()<3){
			deselectAll();
			return;
		}	



		int sz=mesh.polygonData.size();

		//check if a polygon is already present

		for (int i = 0; i < sz ; i++) {

			LineData ld = (LineData)  mesh.polygonData.get(i);

			if(polygon.size()==ld.size()){	

				boolean present=false;

				for (int j = 0; j < ld.size(); j++) {

					int n=ld.getIndex(j);

					boolean found=false;

					for (int k = 0; k < polygon.size(); k++) {
						int m = polygon.getIndex(k);

						if(m==n){

							found=true;
							break;
						}

					}

					if(found) {
						present=true;        

					}else{

						present=false;
						break;
					}	

				} 


				if(present){
					JOptionPane.showMessageDialog(this,"Polygon already present!");
					polygon=new LineData();
					deselectAll();
					return;
				}


			}

		}



		Point3D normal = PolygonMesh.getNormal(0,polygon,mesh.xpoints,mesh.ypoints,mesh.zpoints);	

		int boxFace=Renderer3D.findBoxFace(normal);

		polygon.setData(""+boxFace);

		mesh.polygonData.add(polygon);

		deselectAll();

	}

	private void polygonDetail() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];



		for(int i=0;mesh.polygonData!=null && i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(!ld.isSelected())
				continue;

			RoadEditorPolygonDetail repd=new RoadEditorPolygonDetail(objectEditor,ld);

			if(repd.getModifiedLineData()!=null){

				mesh.polygonData.set(i,repd.getModifiedLineData());

			}

			break;
		}
		displayAll();

	}



	@Override
	public void joinSelectedPoints() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		for(int i=0;mesh.xpoints!=null && i<mesh.xpoints.length;i++){

			Point3D p0=new Point3D(mesh.xpoints[i],mesh.ypoints[i],mesh.zpoints[i]);

			for(int j=0;j<mesh.xpoints.length;j++){

				Point3D p1=new Point3D(mesh.xpoints[j],mesh.ypoints[j],mesh.zpoints[j]);

				if(p0.isSelected() && p1.isSelected() && i<j)
				{
					LineData ld=new LineData();
					ld.addIndex(i);
					ld.addIndex(j);
					mesh.polygonData.add(ld);

				}

			}

		}
		deselectAll();

	}

	@Override
	public void changeSelectedPoint() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		if(mesh.xpoints==null)
			return; 

		for(int i=0;i<mesh.xpoints.length;i++){

	
			if(mesh.selected[i]){

				if(!"".equals(coordinatesx.getText()))
					mesh.xpoints[i]=Double.parseDouble(coordinatesx.getText());
				if(!"".equals(coordinatesy.getText()))
					mesh.xpoints[i]=Double.parseDouble(coordinatesy.getText());
				if(!"".equals(coordinatesz.getText()))
					mesh.xpoints[i]=Double.parseDouble(coordinatesz.getText());
				if(!"".equals(extraData.getText()))
					mesh.data[i]=extraData.getText();
				else
					mesh.data[i]=null;
			}

		}

		deselectAll();

	}

	@Override
	public void addPoint() {

		if("".equals(coordinatesx.getText()) ||
				"".equals(coordinatesy.getText()) ||
				"".equals(coordinatesz.getText())
				)
			return;
		double x=Double.parseDouble(coordinatesx.getText());
		double y=Double.parseDouble(coordinatesy.getText());
		double z=Double.parseDouble(coordinatesz.getText());


		addPoint(x,y,z);

	}
	@Override
	public void addPoint(double x, double y, double z) {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		if(!"".equals(coordinatesx.getText()))
			x=Double.parseDouble(coordinatesx.getText());
		if(!"".equals(coordinatesy.getText()))
			y=Double.parseDouble(coordinatesy.getText());
		if(!"".equals(coordinatesz.getText()))
			z=Double.parseDouble(coordinatesz.getText());

		Point3D p=new Point3D(x,y,z);

		if(!"".equals(extraData.getText()))
			p.setData(extraData.getText());

		int sz= 0;
		if(mesh.xpoints!=null)
			sz=mesh.xpoints.length;

		for (int i = 0; i <sz; i++) {
			Point3D old_p = new Point3D(mesh.xpoints[i],mesh.ypoints[i],mesh.zpoints[i]);

			if(old_p.x==p.x && old_p.y==p.y && old_p.z==p.z){

				JOptionPane.showMessageDialog(this,"Point already present!");
				return;


			}
		}

		mesh.addPoint(p);

		deselectAll();
		displayAll();

		pointList.ensureIndexIsVisible(pointList.getModel().getSize()-1); 

	}
	@Override
	public void delete() {

		ArrayList<Point3D> newPoints=new ArrayList<Point3D>();
		ArrayList<LineData> newLines=new ArrayList<LineData>();

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		for(int i=0;mesh.xpoints!=null && i<mesh.xpoints.length;i++){

			Point3D p=new Point3D(mesh.xpoints[i],mesh.ypoints[i],mesh.zpoints[i]);
			if(!mesh.selected[i]) 
				newPoints.add(p);

		}

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();

			boolean gotAllPoint=true;

			for(int j=0;j<ld.size();j++){

				LineDataVertex ldv= ld.getItem(j);
				
				Point3D p0=new Point3D(mesh.xpoints[ldv.getVertex_index()],mesh.ypoints[ldv.getVertex_index()],mesh.zpoints[ldv.getVertex_index()]);

				if(!mesh.selected[ldv.getVertex_index()]) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.get(k);
						if(np.equals(p0))
						{
							newLd.addIndex(k,ldv.getVertex_index(),ldv.getVertex_texture_x(),ldv.getVertex_texture_y());
							break;
						}

					}
				else
					gotAllPoint=false;

			}
			if(newLd.size()>1 && gotAllPoint)
				newLines.add(newLd);




		}

		mesh.setPoints(newPoints);
		mesh.polygonData=newLines;
		deselectAll();


		displayAll();

	}

	private void mergeSelectedPoints() {


		ArrayList<Point3D> newPoints=new ArrayList<Point3D>();
		ArrayList<LineData> newLines=new ArrayList<LineData>();

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];


		int firstPoint=-1;

		for(int i=0;mesh.xpoints!=null && i<mesh.xpoints.length;i++){

			Point3D p=new Point3D(mesh.xpoints[i],mesh.ypoints[i],mesh.zpoints[i]);
			if(!mesh.selected[i]) 
				newPoints.add(p);
			else if(firstPoint==-1){
				firstPoint=newPoints.size();
				newPoints.add(p);
			}
			else{



			}


		}


		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected())
				continue;
			LineData newLd = new LineData();

			boolean insertedFirst=false;
			for(int j=0;j<ld.size();j++){

				LineDataVertex ldv= ld.getItem(j);

				Point3D p0=new Point3D(mesh.xpoints[ldv.getVertex_index()],mesh.ypoints[ldv.getVertex_index()],mesh.zpoints[ldv.getVertex_index()]);
				if(!p0.isSelected()) 
					for(int k=0;k<newPoints.size();k++){

						Point3D np=(Point3D) newPoints.get(k);
						if(np.equals(p0))
						{
							newLd.addIndex(k,ldv.getVertex_index(),ldv.getVertex_texture_x(),ldv.getVertex_texture_y());
							break;
						}
					}
				else{
					//cause using convex net polygons a point can't appear more than one time
					if(insertedFirst)
						continue;

					newLd.addIndex(firstPoint,ldv.getVertex_index(),ldv.getVertex_texture_x(),ldv.getVertex_texture_y());

					insertedFirst=true;
				}

			}
			if(newLd.size()>1 )
				newLines.add(newLd);




		}

		mesh.setPoints(newPoints);
		mesh.polygonData=newLines;
		deselectAll();

		displayAll();
	}
	@Override
	public void deselectAll() {

		clean();
		deselectAllLines();
		deselectAllPoints();
		polygon=new LineData();




	}
	@Override
	public void deselectAllPoints() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		for(int i=0;mesh.xpoints!=null && i<mesh.xpoints.length;i++){

			mesh.selected[i]=false;

		}

	}
	@Override
	public void deselectAllLines() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			ld.setSelected(false);
		}

	}






	@Override
	public void clean(){

		if(!checkCoordinatesx.isSelected())	coordinatesx.setText("");
		if(!checkCoordinatesy.isSelected())coordinatesy.setText("");
		if(!checkCoordinatesz.isSelected())coordinatesz.setText("");
		if(!checkExtraData.isSelected())extraData.setText("");



	}
	@Override
	public void keyPressed(KeyEvent arg0) {

		int code =arg0.getKeyCode();
		
		Object obj = arg0.getSource();
		if(obj==meshDescription)
			return;

		if(code==KeyEvent.VK_DOWN )
			translate(0,-1);
		else if(code==KeyEvent.VK_UP  )
			translate(0,1);
		else if(code==KeyEvent.VK_LEFT )
		{	
			translate(-1,0);

		}
		else if(code==KeyEvent.VK_RIGHT  )
		{	
			translate(1,0);  

		}
		else if(code==KeyEvent.VK_C  )
		{	
			objectEditor.prepareUndo();
			changeSelectedPoint();   
			displayAll();   
		}
		else if(code==KeyEvent.VK_J )
		{	
			objectEditor.prepareUndo();
			joinSelectedPoints(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_I )
		{	
			objectEditor.prepareUndo();
			addPoint();
		}
		else if(code==KeyEvent.VK_P )
		{	
			objectEditor.prepareUndo();
			buildPolygon(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_E )
		{	
			deselectAll();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_T )
		{	
			selectAllPoints();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_D )
		{	
			objectEditor.prepareUndo();
			delete();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_Q )
		{	
			rotate(+0.1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_W )
		{	
			rotate(-0.1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_A){
			getCenter().rotateTeta(+1);
			displayAll();
		}	
		else if(code==KeyEvent.VK_S){
			getCenter().rotateTeta(-1);
			displayAll();
		}	
		else if(code==KeyEvent.VK_M )
		{	
			mergeSelectedPoints();
		}
		else if(code==KeyEvent.VK_F1 )
		{	
			zoom(+1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_F2 )
		{	
			zoom(-1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_N )
		{	
			startBuildPolygon();
		}
		else if(code==KeyEvent.VK_R )
		{	
			rescaleAllPoints(); 
			displayAll(); 
		}
		else if(code==KeyEvent.VK_F3 )
		{	
			checkMultipleSelection.setSelected(true);  
		}
		else if(code==KeyEvent.VK_F4 )
		{	

			checkMultipleSelection.setSelected(false);  
		}
		else if(code==KeyEvent.VK_PAGE_UP )
		{	
			moveSelectedLine(-1); 
		}
		else if(code==KeyEvent.VK_PAGE_DOWN )
		{	

			moveSelectedLine(+1); 
		}
		else if(code==KeyEvent.VK_LESS )
		{	

			invertSelectedLine(); 
		}

	}

	private void startBuildPolygon() {

		checkMultipleSelection.setSelected(true);
		deselectAll();
		displayAll();
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void moveSelectedLine(int direction) {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected() && direction>0 && i<mesh.polygonData.size()-1 ){
				swapLines(mesh.polygonData,i+1,i);	
				lineList.setSelectedIndex(lineList.getSelectedIndex()+1);
				break;
			}
			else if(ld.isSelected() && direction<0 && i>0){
				swapLines(mesh.polygonData,i,i-1);	
				lineList.setSelectedIndex(lineList.getSelectedIndex()-1);
				break;
			}
		}
		//System.out.println(lineList.getSelectedIndex());
		resetLists();
	}

	private void swapLines(ArrayList<LineData> lines, int i1, int i2) {
		LineData ld1=(LineData) lines.get(i1);
		LineData ld2=(LineData) lines.get(i2);
		lines.set(i2,ld1);
		lines.set(i1,ld2);
	}

	private void invertSelectedLine(){

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			if(ld.isSelected()){

				LineData invertedLd=new LineData();

				for (int j = ld.size()-1; j >=0; j--) {
					invertedLd.addIndex(ld.getIndex(j));
				}
				mesh.polygonData.set(i,invertedLd);
			}


		}
		resetLists();
		displayAll();
	}
	@Override
	public void resetLists() {

		PolygonMesh mesh=objectEditor.getMeshes()[objectEditor.getACTIVE_PANEL()];

		if(mesh.xpoints==null)
			return;

		DefaultListModel dlm=new DefaultListModel();
		int sel=pointList.getSelectedIndex();


		for(int i=0;i<mesh.xpoints.length;i++){

			Point3D p=new Point3D(mesh.xpoints[i],mesh.ypoints[i],mesh.zpoints[i]);
			dlm.addElement(new PointListItem(p,i)) ; 
		}

		pointList.setModel(dlm);

		if(sel>=0 && sel<pointList.getModel().getSize())
			pointList.setSelectedIndex(sel);

		///////////////////////

		DefaultListModel dflm=new DefaultListModel();
		int selec=lineList.getSelectedIndex();


		for(int i=0;i<mesh.polygonData.size();i++){

			LineData ld=(LineData) mesh.polygonData.get(i);
			dflm.addElement(ld) ; 
		}

		lineList.setModel(dflm);

		if(selec>=0 && selec<lineList.getModel().getSize())
			lineList.setSelectedIndex(selec);


	}

	

	public void selectPointsWithRectangle() {



	}
	


	public void translate(int i, int j){
		
		getCenter().translate(i, j);
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {

		Object obj=arg0.getSource();

		if(obj==chooseFace){


		}

	}
	@Override
	public void selectPoint(int x, int y){
		// TODO Auto-generated method stub
	}

	@Override
	public void selectPoint(Point3D p) {

		if(!checkCoordinatesx.isSelected())
			coordinatesx.setText(dfc.format(p.x));
		if(!checkCoordinatesy.isSelected())
			coordinatesy.setText(dfc.format(p.y));
		if(!checkCoordinatesz.isSelected())
			coordinatesz.setText(dfc.format(p.z));
		if(!checkExtraData.isSelected())
			extraData.setText(p.getData()!=null?p.getData().toString():null);


		deselectAllLines();

		p.setSelected(true);


	}
	@Override
	public void moveSelectedPointWithMouse(Point3D p3d, int type) {
		// TODO Auto-generated method stub

	}
	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}




	


	

	public double calculateScreenDistance(Polygon3D p3d, int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	


	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {

		center3D.addPropertyChangeListener(listener);
		centerTop.addPropertyChangeListener(listener);
		centerLeft.addPropertyChangeListener(listener);
		centerFront.addPropertyChangeListener(listener);
	}

	private class FileTransferhandler extends TransferHandler{


		@Override
		public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {

			for(int i=0;i<transferFlavors.length;i++){

				if (!transferFlavors[i].equals(DataFlavor.javaFileListFlavor))
					return false;
			}
			return true;
		}
		@Override
		public boolean importData(JComponent comp, Transferable t) {

			/*try {
				List list=(List) t.getTransferData(DataFlavor.javaFileListFlavor);
				Iterator itera = list.iterator();
				while(itera.hasNext()){

					Object o=itera.next();
					if(!(o instanceof File))
						continue;
					File file=(File) o;
					currentDirectory=file.getParentFile();
					currentFile=file;

					loadPointsFromFile(file,ACTIVE_PANEL,forceReading);
					getCenter().displayAll();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}*/
			return false;
		}

	}
	
	@Override
	public void displayAll() {
		getCenter().displayAll();
		
	}
	
	
	public ObjectEditorViewPanel getCenter(){
		
		if(VIEW_TYPE==VIEW_TYPE_3D)
			return center3D;
		else if(VIEW_TYPE==VIEW_TYPE_TOP)
		    return centerTop;
		else if(VIEW_TYPE==VIEW_TYPE_LEFT)
		    return centerLeft;
		else 
		    return centerFront;
		
	}

	void set3DView() {

		remove(getCenter()); 
		VIEW_TYPE=VIEW_TYPE_3D;
		add(center3D);		
		center3D.grabFocus();
		repaint();
	}

	void setTopView() {

		remove(getCenter()); 
		VIEW_TYPE=VIEW_TYPE_TOP;
		add(centerTop);		
		centerTop.grabFocus();
		repaint();
	}

	void setLeftView() {

		remove(getCenter()); 
		VIEW_TYPE=VIEW_TYPE_LEFT;
		add(centerLeft);		
		centerLeft.grabFocus();
		repaint();
	}

	void setFrontView() {

		remove(getCenter()); 
		VIEW_TYPE=VIEW_TYPE_FRONT;
		add(centerFront);		
		centerFront.grabFocus();
		repaint();
	}

	@Override
	public void zoom(int n) {
		getCenter().zoom(n);
		
	}

	@Override
	public void rotate(double df) {
		getCenter().rotate(df);
		
	}
	
	public String getMeshDescription(){
		
		return meshDescription.getText();
	}
	
	public void setMeshDescription(String description){
		
		meshDescription.setText(description);
	}

}
