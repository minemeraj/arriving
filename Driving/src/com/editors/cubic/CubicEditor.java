package com.editors.cubic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.editors.Editor;
import com.editors.EditorPanel;
import com.editors.block.BlockEditor.BlockData;
import com.editors.object.ObjectEditorPreviewPanel;
import com.main.HelpPanel;

public class CubicEditor extends Editor implements EditorPanel,KeyListener, ActionListener, MouseWheelListener{
	
	public static Color BACKGROUND_COLOR=new Color(0,0,0);

	int CENTER_WIDTH=1000;
	int CENTER_HEIGHT=500;
	int BOTTOM_BORDER=300;

	private CubicEditorPanel centerPanel=null;

	private JMenuBar jmb;
	private JMenu jm1;
	private JMenuItem jmt11;
	private JMenuItem jmt12;
	private JMenu jm2;
	private JMenuItem jmt22;

	private JMenu jm3;

	JCheckBoxMenuItem jmt31;

	private JMenuItem jmt_preview;

	private JMenuItem jmt21;

	private JMenuItem jmt13;	
	
	CubeData cubeData=null;

	private JButton maskCubeUnit;

	private JButton deMaskCubeUnit;
	
	public JCheckBox checkMultipleSelection;
	
	Stack oldCubeData=null;

	private JComboBox xCUbe;

	private JComboBox yCUbe;

	private JComboBox zCUbe;

	public JCheckBox checkDiplaySelectedCube;

	private Vector selectedCubes=null;

	private boolean isWaitForCompletion=false;

	private JButton xPlus;

	private JButton yPlus;

	private JButton zPlus;

	private JButton xMinus;

	private JButton yMinus;

	private JButton zMinus;

	private JTextField visibility;

	private JButton selectXRow;

	private JButton selectZRow;

	private JButton selectYRow;

	private JButton deselectAll;

	private JButton selectAll;

	private JButton moveToCubeBack;

	private JButton moveToCubeFront;

	private JButton moveToCubeLeft;

	private JButton moveToCubeRight;

	private JButton moveToCubeTop;

	private JButton moveToCubeBottom;

	private JMenu help_jm; 
	
	public static int BOTTOM=0;
	public static int TOP=1;
	public static int FRONT=2;
	public static int BACK=3;
	public static int LEFT=4;
	public static int RIGHT=5;
	

	public static void main(String[] args) {
		CubicEditor ce=new CubicEditor();
		ce.setVisible(true);
	}
	
	public CubicEditor() {
		
		initialize();
		 
		setTitle("Cubic editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocation(50,50);
		setSize(CENTER_WIDTH,CENTER_HEIGHT+BOTTOM_BORDER);
		
		centerPanel=new CubicEditorPanel(CENTER_WIDTH,CENTER_HEIGHT,this);
		centerPanel.setBounds(0,0,CENTER_WIDTH,CENTER_HEIGHT);
		centerPanel.setBackground(BACKGROUND_COLOR);
		add(centerPanel);
		
		buildBottomPanel();

		addKeyListener(this);
		addMouseWheelListener(this);
		
		addMenuBar();
	}

	private void buildBottomPanel() {

		JPanel bottom=new JPanel(null);
		bottom.setBounds(0,CENTER_HEIGHT,CENTER_WIDTH,BOTTOM_BORDER);

		add(bottom);



		int c=20;

		int r=5;
		
		JLabel jlb=new JLabel("Move to cube");
		jlb.setBounds(c,r,120,20);
		bottom.add(jlb);
		
		r+=25;
		
		bottom.add(buildMoveCubicUnitPanel(c,r));


		r=5;
		r+=25;
		c=c+200;

		JLabel lmul=new JLabel("Multiple selection:");
		lmul.setBounds(c,r,130,20);
		bottom.add(lmul);

		checkMultipleSelection=new JCheckBox();
		checkMultipleSelection.setSelected(false);
		checkMultipleSelection.addKeyListener(this);
		checkMultipleSelection.setBounds(c+150,r,20,20);
		bottom.add(checkMultipleSelection);
		checkMultipleSelection.addChangeListener(new ChangeListener() {

			
			public void stateChanged(ChangeEvent arg0) {
				if(!checkMultipleSelection.isSelected())
					selectedCubes=new Vector();
                    displayAll();
			}
		});

		r+=30;

		maskCubeUnit=new JButton("Mask cube");
		maskCubeUnit.setBounds(c,r,130,20);
		maskCubeUnit.addActionListener(this);
		maskCubeUnit.setFocusable(false);
		bottom.add(maskCubeUnit);

		r+=25;

		deMaskCubeUnit=new JButton("De-Mask cube");
		deMaskCubeUnit.setBounds(c,r,130,20);
		deMaskCubeUnit.addActionListener(this);
		deMaskCubeUnit.setFocusable(false);
		bottom.add(deMaskCubeUnit);

		r+=25;

		lmul=new JLabel("Display selected:");
		lmul.setBounds(c,r,130,20);
		bottom.add(lmul);

		checkDiplaySelectedCube=new JCheckBox();
		checkDiplaySelectedCube.setSelected(true);
		checkDiplaySelectedCube.addKeyListener(this);
		checkDiplaySelectedCube.setBounds(c+150,r,20,20);
		bottom.add(checkDiplaySelectedCube);

		checkDiplaySelectedCube.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				displayAll();

			}
		});


		r=5;
		r+=25;
		c=c+200;


		jlb=new JLabel("x");
		jlb.setBounds(c,r,30,20);
		bottom.add(jlb);
		xCUbe=new JComboBox();
		xCUbe.setFocusable(false);
		xCUbe.setBounds(c+40,r,100,20);
		bottom.add(xCUbe);
		xCUbe.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent arg0) {
			
				updateCubeSelectedCubeUnit();
			}
		});


		r+=30;


		jlb=new JLabel("y");
		jlb.setBounds(c,r,30,20);
		bottom.add(jlb);
		yCUbe=new JComboBox();
		yCUbe.setFocusable(false);
		yCUbe.setBounds(c+40,r,100,20);
		bottom.add(yCUbe);
		yCUbe.addItemListener(new ItemListener() {
			
		
			public void itemStateChanged(ItemEvent arg0) {
				
				updateCubeSelectedCubeUnit();
			}


		});

		r+=30;

		jlb=new JLabel("z");
		jlb.setBounds(c,r,30,20);
		bottom.add(jlb);
		zCUbe=new JComboBox();
		zCUbe.setFocusable(false);
		zCUbe.setBounds(c+40,r,100,20);
		bottom.add(zCUbe);
		zCUbe.addItemListener(new ItemListener() {
			
		
			public void itemStateChanged(ItemEvent arg0) {
				
				updateCubeSelectedCubeUnit();
			}
		});
		
		
		r+=30;
		jlb=new JLabel("Visibility");
		jlb.setBounds(c,r,70,20);
		bottom.add(jlb);
		
		visibility=new JTextField(3);
		visibility.setFocusable(false);
		visibility.setEditable(false);
		visibility.setBounds(c+80,r,40,20);
		bottom.add(visibility);
		
		r=5;
		r+=25;
		c=c+150;
		
		
		xMinus=new JButton("B");
		xMinus.setFocusable(false);
		xMinus.setBounds(c,r,50,20);
		xMinus.addActionListener(this);
		xMinus.setToolTipText("Back");
		bottom.add(xMinus);
		
		xPlus=new JButton("F");
		xPlus.setFocusable(false);
		xPlus.setBounds(c+50,r,50,20);
		xPlus.addActionListener(this);
		xPlus.setToolTipText("Forward");
		bottom.add(xPlus);
		
		r+=30;
		
		yMinus=new JButton("L");
		yMinus.setFocusable(false);
		yMinus.setBounds(c,r,50,20);
		yMinus.addActionListener(this);
		yMinus.setToolTipText("Left");
		bottom.add(yMinus);
		
		yPlus=new JButton("R");
		yPlus.setFocusable(false);
		yPlus.setBounds(c+50,r,50,20);
		yPlus.addActionListener(this);
		yPlus.setToolTipText("Right");
		bottom.add(yPlus);
		
		r+=30;
		
		zMinus=new JButton("D");
		zMinus.setFocusable(false);
		zMinus.setBounds(c,r,50,20);
		zMinus.addActionListener(this);
		zMinus.setToolTipText("Down");
		bottom.add(zMinus);
		
		zPlus=new JButton("U");
		zPlus.setFocusable(false);
		zPlus.setBounds(c+50,r,50,20);
		zPlus.addActionListener(this);
		zPlus.setToolTipText("Up");
		bottom.add(zPlus);
	
		r=5;
		r+=25;
		c=c+150;
		
		selectXRow=new JButton("Select x row"); 
		selectXRow.setFocusable(false);
		selectXRow.setBounds(c,r,120,20);
		selectXRow.addActionListener(this);
		bottom.add(selectXRow);
		
		r+=30;
		
		selectYRow=new JButton("Select y row"); 
		selectYRow.setFocusable(false);
		selectYRow.setBounds(c,r,120,20);
		selectYRow.addActionListener(this);
		bottom.add(selectYRow);
		
		r+=30;
		
		selectZRow=new JButton("Select z row"); 
		selectZRow.setFocusable(false);
		selectZRow.setBounds(c,r,120,20);
		selectZRow.addActionListener(this);
		bottom.add(selectZRow);
		
		
		r+=30;
		
		deselectAll=new JButton("Deselect all"); 
		deselectAll.setFocusable(false);
		deselectAll.setBounds(c,r,120,20);
		deselectAll.addActionListener(this);
		bottom.add(deselectAll);
		
		
		r+=30;
		
		selectAll=new JButton("Select all"); 
		selectAll.setFocusable(false);
		selectAll.setBounds(c,r,120,20);
		selectAll.addActionListener(this);
		bottom.add(selectAll);
	}
	
	private JPanel buildMoveCubicUnitPanel(int i, int r) {

		JPanel move=new JPanel();
		move.setBounds(i,r,100,100);
		move.setLayout(null);
		
	
		Border border = BorderFactory.createEtchedBorder();
		move.setBorder(border);
		
		moveToCubeBack=new JButton(new ImageIcon("lib/trianglen.jpg"));
		moveToCubeBack.setBounds(40,10,20,20);
		moveToCubeBack.addActionListener(this);
		moveToCubeBack.setFocusable(false);
		move.add(moveToCubeBack);
		
		moveToCubeFront=new JButton(new ImageIcon("lib/triangles.jpg"));
		moveToCubeFront.setBounds(40,70,20,20);
		moveToCubeFront.addActionListener(this);
		moveToCubeFront.setFocusable(false);
		move.add(moveToCubeFront);
		
		moveToCubeLeft=new JButton(new ImageIcon("lib/triangleo.jpg"));
		moveToCubeLeft.setBounds(5,40,20,20);
		moveToCubeLeft.addActionListener(this);
		moveToCubeLeft.setFocusable(false);
		move.add(moveToCubeLeft);
		
		moveToCubeRight=new JButton(new ImageIcon("lib/trianglee.jpg"));
		moveToCubeRight.setBounds(75,40,20,20);
		moveToCubeRight.addActionListener(this);
		moveToCubeRight.setFocusable(false);
		move.add(moveToCubeRight);
		
		moveToCubeTop=new JButton(new ImageIcon("lib/up.jpg"));
		moveToCubeTop.setBounds(5,70,20,20);
		moveToCubeTop.addActionListener(this);
		moveToCubeTop.setFocusable(false);
		move.add(moveToCubeTop);
		
		moveToCubeBottom=new JButton(new ImageIcon("lib/down.jpg"));
		moveToCubeBottom.setBounds(75,70,20,20);
		moveToCubeBottom.addActionListener(this);
		moveToCubeBottom.setFocusable(false);
		move.add(moveToCubeBottom);

		return move;

	}
	
	public void updateCubeSelectedCubeUnit() {
		
		
		try{
			
			if(isWaitForCompletion)
				return;
			
			int i=(Integer)xCUbe.getSelectedItem();
			int j=(Integer)yCUbe.getSelectedItem();
			int k=(Integer)zCUbe.getSelectedItem();
			
			updateCubeSelectedCubeUnit( i, j,  k);
		}
		catch (Exception e) {
		
		}
	}
        
	public void updateCubeSelectedCubeUnit(int i,int j, int k) {
		
		try{

			if(!checkMultipleSelection.isSelected())
				selectedCubes.removeAllElements();
			selectedCubes.add(new CubeListItem(i,j,k,0));
			
			boolean isVisible=cubeData.selectionMask[i][j][k]!=0;
			visibility.setText(isVisible?"V":"M");
			
			displayAll();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void updateComoboBoxes(int i,int j, int k) {

		isWaitForCompletion=true;

		xCUbe.setSelectedIndex(i);
		yCUbe.setSelectedIndex(j);
		zCUbe.setSelectedIndex(k);

		isWaitForCompletion=false;

		

	}

	private void addMenuBar() {
		
		jmb=new JMenuBar();
		
		jm1=new JMenu("Cube");
		jm1.addMenuListener(this);
		jmb.add(jm1);

		jmt11 = new JMenuItem("Load cube");
		jmt11.addActionListener(this);
		jm1.add(jmt11);

		jmt12 = new JMenuItem("Save cube");
		jmt12.addActionListener(this);
		jm1.add(jmt12);
		
		jm1.addSeparator();
		
		jmt13 = new JMenuItem("Save surface");
		jmt13.addActionListener(this);
		jm1.add(jmt13);
		
		
		
		jm2=new JMenu("Edit");
		jm2.addMenuListener(this);
		jmb.add(jm2);
		
		
		jmt21 = new JMenuItem("Undo");
		jmt21.addActionListener(this);
		jm2.add(jmt21);
		jmt21.setEnabled(false);
		
		jmt22 = new JMenuItem("Add cube");
		jmt22.addActionListener(this);
		jm2.add(jmt22);
		
		
		jm3=new JMenu("View");
		jm3.addMenuListener(this);
		jmb.add(jm3);
		
		jmt31 = new JCheckBoxMenuItem("Show normals");
		jmt31.setState(false);
		jmt31.addActionListener(this);
		jm3.add(jmt31);
		
		
		jmt_preview = new JMenuItem("Preview");
		jmt_preview.addActionListener(this);
		jm3.add(jmt_preview);
		
		
		help_jm=new JMenu("Help");
		help_jm.addMenuListener(this);		
		jmb.add(help_jm);
		
		
		setJMenuBar(jmb);
		
	}
	
	

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		int code =arg0.getKeyCode();
		
		if(code==KeyEvent.VK_RIGHT  )
		{	
			translate(+1,0);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_LEFT )
		{	
			translate(-1,0);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_DOWN   )
		{	
			translate(0,+1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_UP)
		{	
			translate(0,-1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_PLUS)
		{	
			zoomIn();
			displayAll(); 
		}
		else if(code==KeyEvent.VK_MINUS)
		{	
			zoomOut();
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
		else if(code==KeyEvent.VK_F3 )
		{	
			checkMultipleSelection.setSelected(true);  
		}
		else if(code==KeyEvent.VK_F4 )
		{	
			 
			checkMultipleSelection.setSelected(false);  
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		

		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object o=arg0.getSource();
		
		if(o==jmt11) {
			loadPointsFromFile();
			resetLists();
		}
		else if(o==jmt12) {
			saveLines();
			
		}
		else if(o==jmt13) {
			saveSurface();
			
		}
		else if(o==jmt21) {
			undo();
			displayAll();
		}		
		else if(o==jmt22) {
			addCube();
			displayAll();
			
		}
		else if(o==jmt31) {
			displayAll();
		}
		else if(o==jmt_preview) {
			preview();
		}
		else if(o==maskCubeUnit) {
			
			
			maskCubeUnit(0);
			displayAll();
		}
		else if(o==deMaskCubeUnit) {
			
			
			maskCubeUnit(1);
			displayAll();
		}
		
		if(cubeData==null)
			return;
		
		if(o==xPlus ||  o==moveToCubeFront) {
			
			if(xCUbe.getSelectedIndex()+1<cubeData.NX-1)
				xCUbe.setSelectedIndex(xCUbe.getSelectedIndex()+1);
		}
		else if(o==yPlus || o==moveToCubeRight) {
			
			if(yCUbe.getSelectedIndex()+1<cubeData.NY-1)
				yCUbe.setSelectedIndex(yCUbe.getSelectedIndex()+1);
		}
		else if(o==zPlus || o==moveToCubeTop) {
			
			if(zCUbe.getSelectedIndex()+1<cubeData.NZ-1)
				zCUbe.setSelectedIndex(zCUbe.getSelectedIndex()+1);
		}
		else if(o==xMinus || o==moveToCubeBack) {
			
			if(xCUbe.getSelectedIndex()>0)
				xCUbe.setSelectedIndex(xCUbe.getSelectedIndex()-1);
		}
		else if(o==yMinus || o==moveToCubeLeft) {
			
			if(yCUbe.getSelectedIndex()>0)
				yCUbe.setSelectedIndex(yCUbe.getSelectedIndex()-1);
		}
		else if(o==zMinus || o==moveToCubeBottom) {
			
			if(zCUbe.getSelectedIndex()>0)
				zCUbe.setSelectedIndex(zCUbe.getSelectedIndex()-1);
		}
		else if(o==selectXRow) {
			
			selectRow(0);
			displayAll();
		}
		else if(o==selectYRow) {
			
			selectRow(1);
			displayAll();
		}
		else if(o==selectZRow) {
			
			selectRow(2);
			displayAll();
		}
		else if(o==deselectAll) {
		
			deselectAll();
			displayAll();
		}
		else if(o==selectAll) {
			
			selectAll();
			displayAll();
		}
		
	}
	
	private void selectRow(int r) {

		if(cubeData==null)
			return;

		int ii=(Integer)xCUbe.getSelectedItem();
		int jj=(Integer)yCUbe.getSelectedItem();
		int kk=(Integer)zCUbe.getSelectedItem();

		int NX=cubeData.NX;
		int NY=cubeData.NY;
		int NZ=cubeData.NZ;

		int counter=0;

		checkMultipleSelection.setSelected(true);
		
		selectedCubes.removeAllElements();

		isWaitForCompletion=true;

		if(r==0){

			for(int i=0;i<NX-1;i++)
				updateCubeSelectedCubeUnit( i, jj,  kk);

		}
		else if(r==1){
			
			for(int j=0;j<NY-1;j++)
				updateCubeSelectedCubeUnit( ii, j,  kk);

		}
		else if(r==2){
			
			for(int k=0;k<NZ-1;k++)
				updateCubeSelectedCubeUnit( ii, jj,  k);

		}

		isWaitForCompletion=false;
	}

	public void preview() {
		
		CubicEditor ce=new CubicEditor();
		PolygonMesh pm = buildSurface();
		
		ce.points[0]=PolygonMesh.fromArrayToVector(pm.points);
		ce.lines[0]=pm.polygonData;
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(ce);
	}

	private void maskCubeUnit(int mask) {
				
		
		try{

			prepareUndo();
			
			if(selectedCubes!=null && selectedCubes.size()>0 && checkDiplaySelectedCube.isSelected()){
				
			
				
				for (int s = 0; s < selectedCubes.size(); s++) {
					CubeListItem cli = (CubeListItem) selectedCubes.elementAt(s);
					
								
					cubeData.selectionMask[cli.i][cli.j][cli.k]=(short) mask;
					visibility.setText(mask!=0?"V":"M");
				}
				
				deselectAll();
				
				int i=(Integer)xCUbe.getSelectedItem();
				int j=(Integer)yCUbe.getSelectedItem();
				int k=(Integer)zCUbe.getSelectedItem();
				
				updateCubeSelectedCubeUnit( i, j,  k);
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
			

		
	}
	
	public Vector getSelectedCubeUnit(){

		
		
		return selectedCubes;
		
	}

	public void saveLines(File file) {


        if(cubeData==null)
        	return;
		
		PrintWriter pr; 
		try {
			pr = new PrintWriter(new FileOutputStream(file));
			pr.print("\nD=");

		
			pr.print(cubeData.print());
	
			pr.print("\nM=");

			for(int i=0;i<points[0].size();i++){

				Point3D p0=(Point3D) points[0].elementAt(i);
				int i0=(int)p0.p_x;
				int j0=(int)p0.p_y;
				int k0=(int)p0.p_z;	
				
				pr.print(cubeData.selectionMask[i0][j0][k0]);
				if(i<points[0].size()-1)
					pr.print("_");
			}

			pr.close(); 	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
	
	public void loadPointsFromFile(File file){

		points[0]=new Vector();
		lines[0]=new Vector();

		oldPoints=new Stack();
		oldLines=new Stack();

		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("D=")){

					String dimensions=str.substring(2);
					String[] vals = dimensions.split("_");
					int NX=Integer.parseInt(vals[0]);
					int NY=Integer.parseInt(vals[1]);
					int NZ=Integer.parseInt(vals[2]);
					double LX=Double.parseDouble(vals[3]);
					double LY=Double.parseDouble(vals[4]);
					double LZ=Double.parseDouble(vals[5]);

					cubeData=new CubeData(NX,NY,NZ,LX,LY,LZ);
				}
				else if(str.startsWith("M=")){

					Vector values=new Vector();
					
					String line=str.substring(2);
					StringTokenizer stk=new StringTokenizer(line,"_");
					while(stk.hasMoreElements()){

						values.add(stk.nextElement());
					}
					
				
					for(int i=0;i<cubeData.NX;i++)
						for(int j=0;j<cubeData.NY;j++){

							for(int k=0;k<cubeData.NZ;k++){
								
								int pos=pos(i,j,k,cubeData.NX,cubeData.NY,cubeData.NZ);
								cubeData.selectionMask[i][j][k]=Short.parseShort((String) values.elementAt(pos));
								
							}
						}	

				}
			}

			br.close();
			
			addCube(cubeData);
			//checkNormals();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	

	private void saveSurface() {
	
		
		PolygonMesh pm=buildSurface();
		
		
		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save surface");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			currentDirectory=fc.getCurrentDirectory();
			saveLines(file,pm);

		} 
		
	}
	
	public void saveLines(File file,PolygonMesh pm) {


		

		PrintWriter pr;
		try {
			pr = new PrintWriter(new FileOutputStream(file));
			pr.print("P=");

			for(int i=0;i<pm.points.length;i++){

				Point3D p=pm.points[i];
				pr.print(decomposePoint(p));
				if(i<points[0].size()-1)
					pr.print("_");
			}	

			pr.print("\nL=");

			for(int i=0;i<pm.polygonData.size();i++){

				LineData ld=(LineData) pm.polygonData.elementAt(i);

				pr.print(decomposeLineData(ld));
				if(i<pm.polygonData.size()-1)
					pr.print("_");
			}	
			

			

			pr.close(); 	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	private PolygonMesh buildSurface() {

		PolygonMesh newPolygonMesh=new PolygonMesh();


		int NX=cubeData.NX;
		int NY=cubeData.NY;
		int NZ=cubeData.NZ;


		//create new points[0]
		Point3D[] newArrPoints = PolygonMesh.fromVectorToArray(points[0]);


		//create new line data

		Vector<LineData> newPolygonData =new Vector();

		for(int i=0;i<NX-1;i++)
			for(int j=0;j<NY-1;j++){

				for(int k=0;k<NZ-1;k++){

					
					if(cubeData.selectionMask[i][j][k]==0)
						continue;
					
					Vector cubeLines=buildCubeLines(i,j,k,cubeData); 
					
					for (int l = 0; l < cubeLines.size(); l++) {
						LineData newLd = (LineData) cubeLines.elementAt(l);
						newPolygonData.add(newLd);
					}
				}
			}

		

		newPolygonMesh.points=newArrPoints;
		newPolygonMesh.polygonData=newPolygonData;


		return newPolygonMesh;		
	}


	/*private boolean isInner(int i, int j, int k, int NX, int NY, int NZ, short[][][] selMask) {


		if(i==0 || i==NX-1 || j==0 || j==NY-1  || k==0 || k==NZ-1 )
			return false;

		for(int di=-1;di<2;di++)
			for(int dj=-1;dj<2;dj++)
				for(int dk=-1;dk<2;dk++){

					short pos=selMask[i+di][j+dj][k+dk];

					if(pos==0)
						return false;
				}

		return true;
	}*/

	private void addCube() {
		
		CubicDataChooser cdcChooser=new CubicDataChooser();
		CubeData cd=cdcChooser.getCubeData();
		
		if(cd!=null){
			
			addCube(cd); 
			this.cubeData=cd;
		}
		
	}

	private void addCube(CubeData cd) {

		
		int NX=cd.NX;
		int NY=cd.NY;
		int NZ=cd.NZ;
		double LX=cd.LX;
		double LY=cd.LY;
		double LZ=cd.LZ;
		
		double dx=LX/NX;
		double dy=LY/NY;
		double dz=LZ/NZ;

		points[0]=new Vector();
		lines[0]=new Vector();

		points[0].setSize(NX*NY*NZ);

		for(int i=0;i<NX;i++)
			for(int j=0;j<NY;j++){

				for(int k=0;k<NZ;k++){

					double x=dx*i;
					double y=dy*j;
					double z=dz*k;

					int pos=pos(i,j,k,NX,NY,NZ);
					points[0].setElementAt(new Point3D(x,y,z,i,j,k),pos);
				}
			}
		setcomboData(xCUbe,NX);
		setcomboData(yCUbe,NY);
		setcomboData(zCUbe,NZ);
		

		

	}
	
	/*public void createSurfaceLines(){
		
		int NX=cubeData.NX;
		int NY=cubeData.NY;
		int NZ=cubeData.NZ;
		
		//z sides
		for(int i=0;i<NX-1;i++)
			for(int j=0;j<NY-1;j++){

				for(int k=0;k<NZ;k++){

					LineData lds=new LineData();

					int pos0=pos(i,j,k,NX,NY,NZ);
					int pos1=pos(i+1,j,k,NX,NY,NZ);
					int pos2=pos(i+1,j+1,k,NX,NY,NZ);
					int pos3=pos(i,j+1,k,NX,NY,NZ);

					//upper z
					
					lds.addIndex(pos0);
					lds.addIndex(pos1);
					lds.addIndex(pos2);
					lds.addIndex(pos3);

					if(k!=0)
						lines.add(lds);
					
					//lower z
					
					LineData ldm=new LineData();
					ldm.addIndex(pos0);
					ldm.addIndex(pos3);
					ldm.addIndex(pos2);
					ldm.addIndex(pos1);					

					if(k!=NZ-1)
						lines.add(ldm);
				}
			}
		
		//x sides
		for(int i=0;i<NX;i++)
			for(int j=0;j<NY-1;j++){

				for(int k=0;k<NZ-1;k++){

					LineData lds=new LineData();

					int pos0=pos(i,j,k,NX,NY,NZ);
					int pos1=pos(i,j,k+1,NX,NY,NZ);
					int pos2=pos(i,j+1,k+1,NX,NY,NZ);
					int pos3=pos(i,j+1,k,NX,NY,NZ);
					
					//upper x

					lds.addIndex(pos0);
					lds.addIndex(pos3);					
					lds.addIndex(pos2);
					lds.addIndex(pos1);

					if(i!=0)
						lines.add(lds);
					
					//lower x
					
					LineData ldm=new LineData();
					ldm.addIndex(pos0);
					ldm.addIndex(pos1);	
					ldm.addIndex(pos2);
					ldm.addIndex(pos3);				
					
					if(i!=NX-1)
						lines.add(ldm);
				}
			}
		//y sides
		for(int i=0;i<NX-1;i++)
			for(int j=0;j<NY;j++){

				for(int k=0;k<NZ-1;k++){

					//upper y
					
					LineData lds=new LineData();

					int pos0=pos(i,j,k,NX,NY,NZ);
					int pos1=pos(i+1,j,k,NX,NY,NZ);
					int pos2=pos(i+1,j,k+1,NX,NY,NZ);
					int pos3=pos(i,j,k+1,NX,NY,NZ);

					lds.addIndex(pos0);
					lds.addIndex(pos3);					
					lds.addIndex(pos2);
					lds.addIndex(pos1);

					if(j!=0)
						lines.add(lds);
					
					//lower y
					
					LineData ldm=new LineData();
					ldm.addIndex(pos0);
					ldm.addIndex(pos1);	
					ldm.addIndex(pos2);
					ldm.addIndex(pos3);
					
					

					 if(j!=NY-1)
						 lines.add(ldm);
				}
			}
		
		
	}*/
	
	int[] p000={0,0,0};
	int[] p100={1,0,0};
	int[] p110={1,1,0};
	int[] p010={0,1,0};
	int[] p001={0,0,1};
	int[] p011={0,1,1};
	int[] p111={1,1,1};
	int[] p101={1,0,1};
	
	public Vector buildCubeLines(int i,int j,int k,CubeData cd){
		
		Vector cubeLines=new Vector();
		

		//bottom
		if(hasSide(BOTTOM,i,j,k,cd))
			cubeLines.add(buildCubeLine(p000,p010,p110,p100,i,j,k,cd));			
		//top
		if(hasSide(TOP,i,j,k,cd))
			cubeLines.add(buildCubeLine(p001,p101,p111,p011,i,j,k,cd));
		//front
		if(hasSide(FRONT,i,j,k,cd))
			cubeLines.add(buildCubeLine(p100,p110,p111,p101,i,j,k,cd));
		//back
		if(hasSide(BACK,i,j,k,cd))
			cubeLines.add(buildCubeLine(p000,p001,p011,p010,i,j,k,cd));
		//left
		if(hasSide(LEFT,i,j,k,cd))
			cubeLines.add(buildCubeLine(p000,p100,p101,p001,i,j,k,cd));
		//right
		if(hasSide(RIGHT,i,j,k,cd))
			cubeLines.add(buildCubeLine(p010,p011,p111,p110,i,j,k,cd));
		
		
		return cubeLines;
	}

	private boolean hasSide(int SIDE, int i, int j, int k, CubeData cd) {
		
		int NX=cd.NX;
		int NY=cd.NY;
		int NZ=cd.NZ;
		
		if(SIDE==BOTTOM){
			
			if(k==0)
				return true;
			else if(cd.selectionMask[i][j][k-1]==0)
				return true;
		}
		else if(SIDE==TOP){
			
			if(k==NZ-2)
				return true;
			else if(cd.selectionMask[i][j][k+1]==0)
				return true;
			
		}
		else if(SIDE==FRONT){
			
			if(i==NX-2)
				return true;
			else if(cd.selectionMask[i+1][j][k]==0)
				return true;
		}
		else if(SIDE==BACK){
			
			if(i==0)
				return true;
			else if(cd.selectionMask[i-1][j][k]==0)
				return true;
		}
		else if(SIDE==LEFT){
			
			if(j==0)
				return true;
			else if(cd.selectionMask[i][j-1][k]==0)
				return true;
		}
		else if(SIDE==RIGHT){
			
			if(j==NY-2)
				return true;
			else if(cd.selectionMask[i][j+1][k]==0)
				return true;
		}

		return false;
	}

	private LineData buildCubeLine(int[] p0, int[] p1, int[] p2,
			int[] p3, int i, int j, int k, CubeData cd) {
		
		LineData ld=new LineData();
		
		ld.addIndex(pos(i+p0[0],j+p0[1],k+p0[2],cd.NX,cd.NY,cd.NZ));
		ld.addIndex(pos(i+p1[0],j+p1[1],k+p1[2],cd.NX,cd.NY,cd.NZ));
		ld.addIndex(pos(i+p2[0],j+p2[1],k+p2[2],cd.NX,cd.NY,cd.NZ));
		ld.addIndex(pos(i+p3[0],j+p3[1],k+p3[2],cd.NX,cd.NY,cd.NZ));
		
		return ld;
	}

	private void setcomboData(JComboBox uCUbe, int n) {
		
		uCUbe.setModel(new DefaultComboBoxModel());
		
		for (int i = 0; i < n-1; i++) {
			((DefaultComboBoxModel)uCUbe.getModel()).addElement(new Integer(i));
		}
		repaint();
		
	}

	private int pos(int i, int j,int k, int nX, int nY,int nZ) {
		
		return (i+j*nX)*nZ+k;
	}
	

	public void paint(Graphics g) {
		super.paint(g);
		draw();
	}

	@Override
	public void addPoint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPoint(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildPolygon() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeSelectedPoint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectAll() {
		
		clean();
		selectedCubes.removeAllElements();
				
	}

	public void selectAll() {
		
		clean();
		selectedCubes.removeAllElements();
		
		for(int i=0;i<cubeData.NX-1;i++)
			for(int j=0;j<cubeData.NY-1;j++){

				for(int k=0;k<cubeData.NZ-1;k++){
					
					int pos=pos(i,j,k,cubeData.NX,cubeData.NY,cubeData.NZ);
					selectedCubes.add(new CubeListItem(i,j,k,pos));
				}
			}	
				
	}


	@Override
	public void deselectAllLines() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectAllPoints() {
	}

	@Override
	public void displayAll() {
		
		draw();
		resetLists();
	}


	public void draw() {
		
        centerPanel.draw();
    	
		
	}

	@Override
	public void initialize() {
		
		oldCubeData=new Stack();
		selectedCubes=new Vector();
		
	}

	@Override
	public void joinSelectedPoints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveSelectedPointWithMouse(Point3D p3d, int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveSelectedPoints(int dx, int dy, int dz) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rescaleAllPoints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetLists() {
		
	}


	public void rotate(double df) {
		
		 centerPanel.rotate(df);
		
	}

	@Override
	public void selectAllPoints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectPoint(Point3D p) {
		
		p.setSelected(true);
		
	}

	@Override
	public void selectPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}


	public void translate(int i, int j) {
			
		centerPanel.translate(i,j);
	
	}


	public void zoomIn() {
		
		centerPanel.zoomIn();
		
		
	}

	public void zoomOut() {
		
		centerPanel.zoomOut();
	
		
	}
	
	public void prepareUndo() {
		jmt21.setEnabled(true);
		
		super.prepareUndo();
		
		try {
			
			if(oldPoints.size()==MAX_STACK_SIZE){
				
				oldCubeData.removeElementAt(0);
			
			}
			oldCubeData.push((CubeData) cubeData.clone());
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	

	public void undo() {
		super.undo();
		
		if(oldCubeData.size()>0)
			cubeData=(CubeData) oldCubeData.pop();
	}
	
	public static class CubeData{
		
		public CubeData(int nX, int nY, int nZ, double lX, double lY, double lZ) {
		
			NX = nX;
			NY = nY;
			NZ = nZ;
			LX = lX;
			LY = lY;
			LZ = lZ;
			
			
			initSelectionMask();
		}
		public String print() {
			
			return NX+"_"+ NY+"_"+ NZ+"_"+ LX+"_"+ LY+"_"+ LZ;			
		}
		public CubeData() {
			
				
			initSelectionMask();
			
		}
		public void initSelectionMask() {
			
		selectionMask=new short[NX][NY][NZ];
			
			for(int i=0;i<NX;i++)
				for(int j=0;j<NY;j++){

					for(int k=0;k<NZ;k++){
						
						selectionMask[i][j][k]=1;
						
					}
				}
			
		}
		int NX=0;
		int NY=0;
		int NZ=0;
		
		double LX=0;
		double LY=0;
		double LZ=0;
		
		public short[][][] selectionMask;
		
		public int getNX() {
			return NX;
		}
		public void setNX(int nX) {
			NX = nX;
		}
		public int getNY() {
			return NY;
		}
		public void setNY(int nY) {
			NY = nY;
		}
		public int getNZ() {
			return NZ;
		}
		public void setNZ(int nZ) {
			NZ = nZ;
		}
		public double getLX() {
			return LX;
		}
		public void setLX(double lX) {
			LX = lX;
		}
		public double getLY() {
			return LY;
		}
		public void setLY(double lY) {
			LY = lY;
		}
		public double getLZ() {
			return LZ;
		}
		public void setLZ(double lZ) {
			LZ = lZ;
		}

		
		protected Object clone() throws CloneNotSupportedException {
		
			
			CubeData cd=new CubeData(NX,NY,NZ,LX,LY,LZ);
			
			for(int i=0;i<NX;i++)
				for(int j=0;j<NY;j++){

					for(int k=0;k<NZ;k++){
						
						cd.selectionMask[i][j][k]=selectionMask[i][j][k];
						
					}
				}
			
					
			return cd;
			
		}
	}
	
	public class CubeListItem{
		
		public DecimalFormat df=new DecimalFormat("000");

		int index=-1;
		
		short selectMask=1;

		int i;
		int j;
		int k;

		
		
		public CubeListItem(int i,int j,int k, int index) {	
			
			this.index=index;
			this.i=i;
			this.j=j;
			this.k=k;
			
		}
		
	
		
		
		public String toString() {
			
			String visible=cubeData.selectionMask[i][j][k]>0?"V":"M";
			
			return i+","+j+","+k+" ("+visible+")";
		}
		
		public void setSelectMask(short selectMask) {
			this.selectMask = selectMask;
		}
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
	
		int units=arg0.getUnitsToScroll();
		
		if(units>0)
			translate(0,+1);
		else 
			translate(0,-1);
		
		displayAll(); 
	}
	
	private void help() {
	
		
		HelpPanel hp=new HelpPanel(300,200,this.getX()+100,this.getY(),HelpPanel.CUBIC_EDITOR_HELP_TEXT,this);
		
	}
	
    public void menuSelected(MenuEvent arg0) {
    	
    	super.menuSelected(arg0);
    	
    	Object o = arg0.getSource();
    	
		if(o==help_jm){
			
			help();
			
		}
    }

}
