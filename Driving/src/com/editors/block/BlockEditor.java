package com.editors.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.editors.Editor;
import com.editors.EditorPanel;
import com.editors.object.ObjectEditorPreviewPanel;
import com.main.HelpPanel;

public class BlockEditor extends Editor implements EditorPanel,KeyListener, ActionListener, MouseWheelListener{
	
	private static Color BACKGROUND_COLOR=new Color(0,0,0);

	private int CENTER_WIDTH=1000;
	private int CENTER_HEIGHT=500;
	private int BOTTOM_BORDER=300;

	private BlockEditorPanel centerPanel=null;

	private JMenuBar jmb;
	private JMenu jm1;
	private JMenuItem jmt11;
	private JMenuItem jmtSaveBlocks;
	private JMenu jm2;
	private JMenuItem jmtStartBlock;

	private JMenu jm3;

	private JCheckBoxMenuItem jmt31;

	private JMenuItem jmt32;

	private JMenuItem jmt21;

	private JMenuItem jmtSaveSurface;	
	
	BlockData blockData=null;
	
	private JCheckBox checkMultipleSelection;
	
	private Stack oldBlockData=null;

	private JComboBox xBlock;

	private JComboBox yBlock;

	private JComboBox zBlock;

	public JCheckBox checkDiplaySelectedBlock;

	private Vector selectedBlocks=null;

	private boolean isWaitForCompletion=false;

	private JButton xPlus;

	private JButton yPlus;

	private JButton zPlus;

	private JButton xMinus;

	private JButton yMinus;

	private JButton zMinus;

	private JButton deselectAll;

	private JButton addBlockBack;

	private JButton addBlockFront;

	private JButton addBlockLeft;

	private JButton addBlockRight;

	private JButton addBlockTop;

	private JButton addBlockBottom;

	private JButton deleteBlock;

	private JMenu help_jm; 
	
	private static int BOTTOM=0;
	private static int TOP=1;
	private static int FRONT=2;
	private static int BACK=3;
	private static int LEFT=4;
	private static int RIGHT=5;
	

	public static void main(String[] args) {
		BlockEditor ce=new BlockEditor();
		ce.setVisible(true);
	}
	
	public BlockEditor() {
		
		
		 
		setTitle("Block editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocation(50,50);
		setSize(CENTER_WIDTH,CENTER_HEIGHT+BOTTOM_BORDER);
		
		centerPanel=new BlockEditorPanel(CENTER_WIDTH,CENTER_HEIGHT,this);
		centerPanel.setBounds(0,0,CENTER_WIDTH,CENTER_HEIGHT);
		centerPanel.setBackground(BACKGROUND_COLOR);
		add(centerPanel);	
		
		
		buildBottomPanel();
		
		
		addKeyListener(this);
		addMouseWheelListener(this);
		
		addMenuBar();
		
		initialize();
	}
	
	private JPanel buildAddBlockPanel(int i, int r) {

		JPanel move=new JPanel();
		move.setBounds(i,r,100,100);
		move.setLayout(null);
		
	
		Border border = BorderFactory.createEtchedBorder();
		move.setBorder(border);
		
		addBlockBack=new JButton(new ImageIcon("lib/trianglen.jpg"));
		addBlockBack.setBounds(40,10,20,20);
		addBlockBack.addActionListener(this);
		addBlockBack.setFocusable(false);
		move.add(addBlockBack);
		
		addBlockFront=new JButton(new ImageIcon("lib/triangles.jpg"));
		addBlockFront.setBounds(40,70,20,20);
		addBlockFront.addActionListener(this);
		addBlockFront.setFocusable(false);
		move.add(addBlockFront);
		
		addBlockLeft=new JButton(new ImageIcon("lib/triangleo.jpg"));
		addBlockLeft.setBounds(5,40,20,20);
		addBlockLeft.addActionListener(this);
		addBlockLeft.setFocusable(false);
		move.add(addBlockLeft);
		
		addBlockRight=new JButton(new ImageIcon("lib/trianglee.jpg"));
		addBlockRight.setBounds(75,40,20,20);
		addBlockRight.addActionListener(this);
		addBlockRight.setFocusable(false);
		move.add(addBlockRight);
		
		addBlockTop=new JButton(new ImageIcon("lib/up.jpg"));
		addBlockTop.setBounds(5,70,20,20);
		addBlockTop.addActionListener(this);
		addBlockTop.setFocusable(false);
		move.add(addBlockTop);
		
		addBlockBottom=new JButton(new ImageIcon("lib/down.jpg"));
		addBlockBottom.setBounds(75,70,20,20);
		addBlockBottom.addActionListener(this);
		addBlockBottom.setFocusable(false);
		move.add(addBlockBottom);

		return move;

	}

	private void buildBottomPanel() {

		JPanel bottom=new JPanel(null);
		bottom.setBounds(0,CENTER_HEIGHT,CENTER_WIDTH,BOTTOM_BORDER);

		add(bottom);

		int c=20;

		int r=5;
		
		JLabel jlb=new JLabel("Add block");
		jlb.setBounds(c,r,100,20);
		bottom.add(jlb);
		
		r+=25;
		
		bottom.add(buildAddBlockPanel(c,r));
		
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
					selectedBlocks=new Vector();
                    displayAll();
			}
		});

		r+=30;



		lmul=new JLabel("Display selected:");
		lmul.setBounds(c,r,130,20);
		bottom.add(lmul);

		checkDiplaySelectedBlock=new JCheckBox();
		checkDiplaySelectedBlock.setSelected(true);
		checkDiplaySelectedBlock.addKeyListener(this);
		checkDiplaySelectedBlock.setBounds(c+150,r,20,20);
		bottom.add(checkDiplaySelectedBlock);

		checkDiplaySelectedBlock.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				displayAll();

			}
		});
		
		r+=25;
		
		


		r=5;
		r+=25;
		c=c+200;


		jlb=new JLabel("x");
		jlb.setBounds(c,r,30,20);
		bottom.add(jlb);
		xBlock=new JComboBox();
		xBlock.setFocusable(false);
		xBlock.setBounds(c+40,r,100,20);
		bottom.add(xBlock);
		xBlock.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent arg0) {
			
				updateBlockSelectedBlockUnit();
			}
		});


		r+=30;


		jlb=new JLabel("y");
		jlb.setBounds(c,r,30,20);
		bottom.add(jlb);
		yBlock=new JComboBox();
		yBlock.setFocusable(false);
		yBlock.setBounds(c+40,r,100,20);
		bottom.add(yBlock);
		yBlock.addItemListener(new ItemListener() {
			
		
			public void itemStateChanged(ItemEvent arg0) {
				
				updateBlockSelectedBlockUnit();
			}


		});

		r+=30;

		jlb=new JLabel("z");
		jlb.setBounds(c,r,30,20);
		bottom.add(jlb);
		zBlock=new JComboBox();
		zBlock.setFocusable(false);
		zBlock.setBounds(c+40,r,100,20);
		bottom.add(zBlock);
		zBlock.addItemListener(new ItemListener() {
			
		
			public void itemStateChanged(ItemEvent arg0) {
				
				updateBlockSelectedBlockUnit();
			}
		});
		
		
		r+=30;
		jlb=new JLabel("Visibility");
		jlb.setBounds(c,r,70,20);
		bottom.add(jlb);
		
		
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

		deleteBlock=new JButton("Delete block"); 
		deleteBlock.setFocusable(false);
		deleteBlock.setBounds(c,r,120,20);
		deleteBlock.addActionListener(this);
		bottom.add(deleteBlock);
		
		r+=30;
		
		deselectAll=new JButton("Deselect all"); 
		deselectAll.setFocusable(false);
		deselectAll.setBounds(c,r,120,20);
		deselectAll.addActionListener(this);
		bottom.add(deselectAll);
	}
	
	private void updateBlockSelectedBlockUnit() {
		
		
		try{
			
			if(isWaitForCompletion)
				return;
			
			int i=(Integer)xBlock.getSelectedItem();
			int j=(Integer)yBlock.getSelectedItem();
			int k=(Integer)zBlock.getSelectedItem();
			
			updateBlockSelectedBlockUnit( i, j,  k);
		}
		catch (Exception e) {
		
		}
	}
        
	public void updateBlockSelectedBlockUnit(int i,int j, int k) {
		
		try{

			if(!checkMultipleSelection.isSelected())
				selectedBlocks.removeAllElements();
			selectedBlocks.add(new BlockListItem(i,j,k,0));
			
			boolean isVisible=blockData.selectionMask[i][j][k]!=0;
						
			displayAll();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void updateComoboBoxes(int i,int j, int k) {

		isWaitForCompletion=true;

		xBlock.setSelectedIndex(i);
		yBlock.setSelectedIndex(j);
		zBlock.setSelectedIndex(k);

		isWaitForCompletion=false;

		

	}

	private void addMenuBar() {
		
		jmb=new JMenuBar();
		
		jm1=new JMenu("Block");
		jm1.addMenuListener(this);
		jmb.add(jm1);

		jmt11 = new JMenuItem("Load Blocks");
		jmt11.addActionListener(this);
		jm1.add(jmt11);

		jmtSaveBlocks = new JMenuItem("Save Blocks");
		jmtSaveBlocks.addActionListener(this);
		jm1.add(jmtSaveBlocks);
		
		jm1.addSeparator();
		
		jmtSaveSurface = new JMenuItem("Save surface");
		jmtSaveSurface.addActionListener(this);
		jm1.add(jmtSaveSurface);
		
		
		
		jm2=new JMenu("Edit");
		jm2.addMenuListener(this);
		jmb.add(jm2);
		
		
		jmt21 = new JMenuItem("Undo");
		jmt21.addActionListener(this);
		jm2.add(jmt21);
		jmt21.setEnabled(false);
		
		jmtStartBlock = new JMenuItem("Start Block");
		jmtStartBlock.addActionListener(this);
		jm2.add(jmtStartBlock);
		
		
		jm3=new JMenu("View");
		jm3.addMenuListener(this);
		jmb.add(jm3);
		
		jmt31 = new JCheckBoxMenuItem("Show normals");
		jmt31.setState(false);
		jmt31.addActionListener(this);
		jm3.add(jmt31);
		
		
		jmt32 = new JMenuItem("Preview");
		jmt32.addActionListener(this);
		jm3.add(jmt32);
		
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
			zoom(+1);
			displayAll(); 
		}
		else if(code==KeyEvent.VK_MINUS)
		{	
			zoom(-1);
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
			loadBlocksFromFile();
			resetLists();
		}
		else if(o==jmtSaveBlocks) {
			saveBlocks();
			
		}
		else if(o==jmtSaveSurface) {
			saveSurface();
			
		}
		else if(o==jmt21) {
			undo();
			displayAll();
		}		
		else if(o==jmtStartBlock) {
			addBlocks();
			displayAll();
			
		}
		else if(o==jmt31) {
			displayAll();
		}
		else if(o==jmt32) {
			preview();
		}
		
		if(blockData==null)
			return;
		
		if(o==xPlus) {
			
			moveSelection(1,0,0);
		
		}
		else if(o==yPlus) {
			
			moveSelection(0,1,0);

		}
		else if(o==zPlus) {
			
			moveSelection(0,0,1);
			

		}
		else if(o==xMinus) {
			
			
			moveSelection(-1,0,0);
			
		}
		else if(o==yMinus) {
			
			moveSelection(0,-1,0);
			
	
		}
		else if(o==zMinus) {
			
			moveSelection(0,0,-1);
			
		}
		else if(o==deselectAll) {
		
			deselectAll();
			displayAll();
		}
		else if(o==deleteBlock) {
			
			deleteBlock();
			displayAll();
		}		
		else if(o==addBlockBottom){
			
			addBlock(BOTTOM);
			
		}
		else if(o==addBlockLeft){
			
			addBlock(LEFT);
			
		}
		else if(o==addBlockRight){
			
			addBlock(RIGHT);
			
		}
		else if(o==addBlockTop){
			
			addBlock(TOP);
			
		}
		else if(o==addBlockBack){
			
			addBlock(BACK);
			
		}
		else if(o==addBlockFront){
			
			addBlock(FRONT);
			
		}

		
	}
	
	private void moveSelection(int dx, int dy, int dz) {
		
		if(selectedBlocks.size()==0)
			return;
		
		BlockListItem bli=(BlockListItem) selectedBlocks.lastElement();
		
		int i=bli.i;
		int j=bli.j;
		int k=bli.k;
		
	
			
		if(i+dx<0 || j+dy<0 || k+dz<0)
			return;
		
		if(i+dx>=blockData.NX-1 || j+dy>=blockData.NY-1 || k+dz>=blockData.NZ-1)
			return;
		
		//if(blockData.selectionMask[i+dx][j+dy][k+dz]==0)
		//	return;
	
		
		if(dx>0){
			
			
				xBlock.setSelectedIndex(xBlock.getSelectedIndex()+1);
				
		}
		else if(dx<0){
			
		
				xBlock.setSelectedIndex(xBlock.getSelectedIndex()-1);
				
		}
		else if(dy>0){
			
	
				yBlock.setSelectedIndex(yBlock.getSelectedIndex()+1);
			
		}
		else if(dy<0){
			
		
				yBlock.setSelectedIndex(yBlock.getSelectedIndex()-1);
				
		}
		else if(dz>0){
			
		
				zBlock.setSelectedIndex(zBlock.getSelectedIndex()+1);
				
		}
		else if(dz<0){
			
		
				zBlock.setSelectedIndex(zBlock.getSelectedIndex()-1);
				
		}
	}

	private void addBlock(int position) {
		
		
		if(selectedBlocks.size()==0)
			return;
		
		BlockListItem bli=(BlockListItem) selectedBlocks.lastElement();
		
		int i=bli.i;
		int j=bli.j;
		int k=bli.k;
	
		prepareUndo();
		
		if(position==TOP){
			
			if(k+1>=blockData.NZ-1)
			   expandCubeLattice(0,0,1);
	
			addBlock(i,j,k+1);
			displayAll();
		}
		else if(position==BOTTOM){
			
			if(k-1>=0)
				addBlock(i,j,k-1);
			displayAll();
		}
		else if(position==LEFT){
			
			if(j-1>=0)
				addBlock(i,j-1,k);
			displayAll();
		}
		else if(position==RIGHT){
			
			if(j+1>=blockData.NY-1)
			   expandCubeLattice(0,1,0);
			
			addBlock(i,j+1,k);
			displayAll();
		}
		else if(position==FRONT){
			
			if(i+1>=blockData.NX-1)
				expandCubeLattice(1,0,0);
				
			addBlock(i+1,j,k);
			
			displayAll();
		}
		else if(position==BACK){
			
			
			if(i-1>=0)
				addBlock(i-1,j,k);
			displayAll();
		}
		
	}
	
	private void expandCubeLattice(int dx, int dy, int dz) {


		BlockData newBlockData=new BlockData(blockData.NX+dx,blockData.NY+dy,blockData.NZ+dz,
				blockData.LX*(blockData.NX+dx)*1.0/(blockData.NX),
				blockData.LY*(blockData.NY+dy)*1.0/(blockData.NY),
				blockData.LZ*(blockData.NZ+dz)*1.0/(blockData.NZ)
				);
  



		for(int i=0;i<blockData.NX;i++)
			for(int j=0;j<blockData.NY;j++){

				for(int k=0;k<blockData.NZ;k++){

					newBlockData.selectionMask[i][j][k]=blockData.selectionMask[i][j][k];

				}
			}

		this.blockData=newBlockData;
		
		      
		addBlocks(this.blockData);
	}

	public void addBlock(int i,int j,int k) {
		

		
		
		blockData.selectionMask[i][j][k]=1;
		selectedBlocks.removeAllElements();
		selectedBlocks.add(new BlockListItem(i,j,k,0));
		
		isWaitForCompletion=true;
		
		xBlock.setSelectedIndex(i);
		yBlock.setSelectedIndex(j);
		zBlock.setSelectedIndex(k);
		
		isWaitForCompletion=false;
	}
	
	
	private void deleteBlock(){
		
		if(selectedBlocks.size()==0)
			return;
		
		BlockListItem bli=(BlockListItem) selectedBlocks.lastElement();
		
		int i=bli.i;
		int j=bli.j;
		int k=bli.k;
		
		deleteBlock(i,j,k);
	}
	
	private void deleteBlock(int i,int j,int k) {
		
		blockData.selectionMask[i][j][k]=0;
		selectedBlocks.removeAllElements();
	}

	public void preview() {
		
		BlockEditor ce=new BlockEditor();
		PolygonMesh pm = buildSurface();
		
		ce.meshes[ACTIVE_PANEL]=new PolygonMesh(pm.points,pm.polygonData);
		
		
		ObjectEditorPreviewPanel oepp=new ObjectEditorPreviewPanel(ce);
	}

	private void maskBlockUnit(int mask) {
				
		
		try{

			
			if(selectedBlocks!=null && selectedBlocks.size()>0 && checkDiplaySelectedBlock.isSelected()){
				
			
				
				for (int s = 0; s < selectedBlocks.size(); s++) {
					BlockListItem cli = (BlockListItem) selectedBlocks.elementAt(s);
					
								
					blockData.selectionMask[cli.i][cli.j][cli.k]=(short) mask;
					
				}
				selectedBlocks=new Vector();
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
			

		deselectAll();
		
	}
	
	public Vector getSelectedBlockUnit(){

		
		
		return selectedBlocks;
		
	}
	
	private void saveBlocks() {

		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save lines");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);
		
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			try{
				PrintWriter pr = new PrintWriter(new FileOutputStream(file));
				saveBlocks(pr,false);
	            pr.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		} 


	}

	private void saveBlocks(PrintWriter pr,boolean isCustom) {


        if(blockData==null)
        	return;
        
    	PolygonMesh mesh=meshes[ACTIVE_PANEL];
		
		
		try {
	
			pr.print("\nD=");

		
			pr.print(blockData.print());
	
			pr.print("\nM=");

			for(int i=0;i<mesh.points.length;i++){

				Point3D p0=mesh.points[i];
				int i0=(int)p0.p_x;
				int j0=(int)p0.p_y;
				int k0=(int)p0.p_z;	
				
				pr.print(blockData.selectionMask[i0][j0][k0]);
				if(i<mesh.points.length-1)
					pr.print(" ");
			}


		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private void loadBlocksFromFile() {

		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save lines");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		if(currentFile!=null)
			fc.setSelectedFile(currentFile);
		
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			
			currentDirectory=fc.getCurrentDirectory();
			currentFile=fc.getSelectedFile();
			try{
				
				loadBlocksFromFile(file);

			}catch (Exception e) {
				e.printStackTrace();
			}
		} 


	}
	
	private void loadBlocksFromFile(File file){

		meshes[ACTIVE_PANEL]=new PolygonMesh();
		oldMeshes[ACTIVE_PANEL]=new Stack();


		try {
			BufferedReader br=new BufferedReader(new FileReader(file));


			String str=null;
			int rows=0;
			while((str=br.readLine())!=null){
				if(str.indexOf("#")>=0 || str.length()==0)
					continue;

				if(str.startsWith("D=")){

					String dimensions=str.substring(2);
					String[] vals = dimensions.split(" ");
					int NX=Integer.parseInt(vals[0]);
					int NY=Integer.parseInt(vals[1]);
					int NZ=Integer.parseInt(vals[2]);
					double LX=Double.parseDouble(vals[3]);
					double LY=Double.parseDouble(vals[4]);
					double LZ=Double.parseDouble(vals[5]);

					blockData=new BlockData(NX,NY,NZ,LX,LY,LZ);
				}
				else if(str.startsWith("M=")){

					Vector values=new Vector();
					
					String line=str.substring(2);
					StringTokenizer stk=new StringTokenizer(line," ");
					while(stk.hasMoreElements()){

						values.add(stk.nextElement());
					}
					
				
					for(int i=0;i<blockData.NX;i++)
						for(int j=0;j<blockData.NY;j++){

							for(int k=0;k<blockData.NZ;k++){
								
								int pos=pos(i,j,k,blockData.NX,blockData.NY,blockData.NZ);
								blockData.selectionMask[i][j][k]=Short.parseShort((String) values.elementAt(pos));
								
							}
						}	

				}
			}

			br.close();
			
			addBlocks(blockData);
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
	
	private void saveLines(File file,PolygonMesh pm) {


		PrintWriter pr;
		try {
			pr = new PrintWriter(new FileOutputStream(file));
			

			for(int i=0;i<pm.points.length;i++){

				Point3D p=pm.points[i];
				pr.print("\nv=");
				pr.print(decomposePoint(p));
				if(i<pm.points.length-1)
					pr.print(" ");
			}	

			decomposeObjVertices(pr,pm,false);
			

			for(int i=0;i<pm.polygonData.size();i++){

				LineData ld=(LineData) pm.polygonData.get(i);
				pr.print("\nf=");
				pr.print(decomposeLineData(ld));
				if(i<pm.polygonData.size()-1)
					pr.print(" ");
			}	
			

			

			pr.close(); 	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
	
public void decomposeObjVertices(PrintWriter pr,PolygonMesh mesh,boolean isCustom) {
		
		if(isCustom){
			
			for (int i = 0; i < mesh.texturePoints.size(); i++) {
				Point3D pt = (Point3D)  mesh.texturePoints.get(i);
				pr.print("\nvt=");
				pr.print(pt.x+" "+pt.y);
			}
			
			return;
		}
		
		int DX=0;

		
		int deltaX=0;
		int deltaY=0;
		int deltaX2=0;

		double minx=0;
		double miny=0;
		double minz=0;
		
		double maxx=0;
		double maxy=0;
		double maxz=0;
		
		
	      //find maxs
		for(int j=0;j<mesh.points.length;j++){
			
			Point3D point=mesh.points[j];
			
			if(j==0){
				
				minx=point.x;
				miny=point.y;
				minz=point.z;
				
				maxx=point.x;
				maxy=point.y;
				maxz=point.z;
			}
			else{
				
				maxx=(int)Math.max(point.x,maxx);
				maxz=(int)Math.max(point.z,maxz);
				maxy=(int)Math.max(point.y,maxy);
				
				
				minx=(int)Math.min(point.x,minx);
				minz=(int)Math.min(point.z,minz);
				miny=(int)Math.min(point.y,miny);
			}
			
	
		}
		
		deltaX2=(int)(maxx-minx)+1;
		deltaX=(int)(maxz-minz)+1; 
		deltaY=(int)(maxy-miny)+1;

		for(int i=0;i<mesh.points.length;i++){

			Point3D p=mesh.points[i];

			/*public static final int CAR_BACK=0;
			public static final int CAR_TOP=1;
			public static final int CAR_LEFT=2;
			public static final int CAR_RIGHT=3;
			public static final int CAR_FRONT=4;
			public static final int CAR_BOTTOM=5;*/
		
			//back
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(p.z-minz));					
			//top
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(p.y-miny+deltaX));
			//left
			pr.print("\nvt=");
			pr.print(DX+(int)(p.z-minz)+" "+(int)(p.y-miny+deltaX));			
			//right
			pr.print("\nvt=");
			pr.print(DX+(int)(-p.z+maxz+deltaX2+deltaX)+" "+(int)(p.y-miny+deltaX));		
			//front
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(-p.z+maxz+deltaY+deltaX));	
			//bottom
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+2*deltaX+deltaX2)+" "+(int)(p.y-miny+deltaX));

		}	
		
		
		
	}

	private PolygonMesh buildSurface() {
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];

		PolygonMesh newPolygonMesh=new PolygonMesh();


		int NX=blockData.NX;
		int NY=blockData.NY;
		int NZ=blockData.NZ;


	

		//create new line data

		ArrayList<LineData> newPolygonData =new ArrayList();

		for(int i=0;i<NX-1;i++)
			for(int j=0;j<NY-1;j++){

				for(int k=0;k<NZ-1;k++){

					
					if(blockData.selectionMask[i][j][k]==0)
						continue;
					
					Vector BlockLines=buildBlockLines(i,j,k,blockData); 
					
					for (int l = 0; l < BlockLines.size(); l++) {
						LineData newLd = (LineData) BlockLines.elementAt(l);
						newPolygonData.add(newLd);
					}
				}
			}

		

		newPolygonMesh.points=mesh.points;
		newPolygonMesh.polygonData=newPolygonData;
		
		newPolygonMesh=PolygonMesh.simplifyMesh(newPolygonMesh);

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

	private void addBlocks() {
		
		BlockDataChooser cdcChooser=new BlockDataChooser();
		BlockData cd=cdcChooser.getBlockData();
			
		
		if(cd!=null){
			
			selectedBlocks=new Vector();
			
			addBlocks(cd); 
			this.blockData=cd;
			
			for (int i = 0; i < cd.NX-1; i++) {
				
				for (int j = 0; j < cd.NY-1; j++) {
					
					for (int k = 0; k < cd.NZ-1; k++) {
						
						
						addBlock(i,j,k);
						
					}
				}
			}

		}
		
	}

	private void addBlocks(BlockData cd) {

		
		int NX=cd.NX;
		int NY=cd.NY;
		int NZ=cd.NZ;
		double LX=cd.LX;
		double LY=cd.LY;
		double LZ=cd.LZ;
		
		double dx=LX/NX;
		double dy=LY/NY;
		double dz=LZ/NZ;
		
		PolygonMesh mesh=meshes[ACTIVE_PANEL];

		meshes[ACTIVE_PANEL]=new PolygonMesh();
		

		meshes[ACTIVE_PANEL].points=new Point3D[NX*NY*NZ];

		for(int i=0;i<NX;i++)
			for(int j=0;j<NY;j++){

				for(int k=0;k<NZ;k++){

					double x=dx*i;
					double y=dy*j;
					double z=dz*k;

					int pos=pos(i,j,k,NX,NY,NZ);
					meshes[ACTIVE_PANEL].points[pos]=new Point3D(x,y,z,i,j,k);
				}
			}
		
		
		setcomboData(xBlock,NX);
		setcomboData(yBlock,NY);
		setcomboData(zBlock,NZ);
		
		

	}
	
	/*public void createSurfaceLines(){
		
		int NX=BlockData.NX;
		int NY=BlockData.NY;
		int NZ=BlockData.NZ;
		
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
	
	private int[] p000={0,0,0};
	private int[] p100={1,0,0};
	private int[] p110={1,1,0};
	private int[] p010={0,1,0};
	private int[] p001={0,0,1};
	private int[] p011={0,1,1};
	private int[] p111={1,1,1};
	private int[] p101={1,0,1};
	
	private Vector buildBlockLines(int i,int j,int k,BlockData cd){
		
		Vector BlockLines=new Vector();
		

		//bottom
		if(hasSide(BOTTOM,i,j,k,cd))
			BlockLines.add(buildBlockLine(p000,p010,p110,p100,i,j,k,cd));			
		//top
		if(hasSide(TOP,i,j,k,cd))
			BlockLines.add(buildBlockLine(p001,p101,p111,p011,i,j,k,cd));
		//front
		if(hasSide(FRONT,i,j,k,cd))
			BlockLines.add(buildBlockLine(p100,p110,p111,p101,i,j,k,cd));
		//back
		if(hasSide(BACK,i,j,k,cd))
			BlockLines.add(buildBlockLine(p000,p001,p011,p010,i,j,k,cd));
		//left
		if(hasSide(LEFT,i,j,k,cd))
			BlockLines.add(buildBlockLine(p000,p100,p101,p001,i,j,k,cd));
		//right
		if(hasSide(RIGHT,i,j,k,cd))
			BlockLines.add(buildBlockLine(p010,p011,p111,p110,i,j,k,cd));
		
		
		return BlockLines;
	}

	private boolean hasSide(int SIDE, int i, int j, int k, BlockData cd) {
		
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

	private LineData buildBlockLine(int[] p0, int[] p1, int[] p2,
			int[] p3, int i, int j, int k, BlockData cd) {
		
		LineData ld=new LineData();
		
		ld.addIndex(pos(i+p0[0],j+p0[1],k+p0[2],cd.NX,cd.NY,cd.NZ));
		ld.addIndex(pos(i+p1[0],j+p1[1],k+p1[2],cd.NX,cd.NY,cd.NZ));
		ld.addIndex(pos(i+p2[0],j+p2[1],k+p2[2],cd.NX,cd.NY,cd.NZ));
		ld.addIndex(pos(i+p3[0],j+p3[1],k+p3[2],cd.NX,cd.NY,cd.NZ));
		
		return ld;
	}

	private void setcomboData(JComboBox uBlock, int n) {
		
		uBlock.setModel(new DefaultComboBoxModel());
		
		for (int i = 0; i < n-1; i++) {
			((DefaultComboBoxModel)uBlock.getModel()).addElement(new Integer(i));
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
		selectedBlocks.removeAllElements();
				
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
		
		selectedBlocks=new Vector();
		oldBlockData=new Stack();
		
		/*BlockData bd=new BlockData(2,2,2,50,50,50);
		addBlocks( bd);
		this.blockData=bd;
		addBlock(0,0,0);*/
		
	}

	@Override
	public void joinSelectedPoints() {
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


	public void zoom(int n) {
		
		centerPanel.zoom(n);
	
		
	}
	
	public void prepareUndo() {
		jmt21.setEnabled(true);
		
		super.prepareUndo();
		
		try {
			
			if(oldBlockData.size()==MAX_STACK_SIZE){
				
				oldBlockData.removeElementAt(0);
			
			}
			oldBlockData.push((BlockData) blockData.clone());
			
			
						
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	

	public void undo() {
		super.undo();
		
		if(oldBlockData.size()>0)
			blockData=(BlockData) oldBlockData.pop();
	}
	
	public static class BlockData{
		
		public BlockData(int nX, int nY, int nZ, double lX, double lY, double lZ) {
		
			NX = nX;
			NY = nY;
			NZ = nZ;
			LX = lX;
			LY = lY;
			LZ = lZ;
			
			
			initSelectionMask();
		}
		private String print() {
			
			return NX+" "+ NY+" "+ NZ+" "+ LX+" "+ LY+" "+ LZ;			
		}
		public BlockData() {
			
				
			initSelectionMask();
			
		}
		public void initSelectionMask() {
			
		selectionMask=new short[NX][NY][NZ];
			
			for(int i=0;i<NX;i++)
				for(int j=0;j<NY;j++){

					for(int k=0;k<NZ;k++){
						
						selectionMask[i][j][k]=0;
						
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
		
			
			BlockData cd=new BlockData(NX,NY,NZ,LX,LY,LZ);
			
			for(int i=0;i<NX;i++)
				for(int j=0;j<NY;j++){

					for(int k=0;k<NZ;k++){
						
						cd.selectionMask[i][j][k]=selectionMask[i][j][k];
						
					}
				}
			
					
			return cd;
			
		}
	}
	
	public class BlockListItem{
		
		public DecimalFormat df=new DecimalFormat("000");

		private int index=-1;
		
		private short selectMask=1;

		int i;
		int j;
		int k;

		
		
		private BlockListItem(int i,int j,int k, int index) {	
			
			this.index=index;
			this.i=i;
			this.j=j;
			this.k=k;
			
		}
		
	
		
		
		public String toString() {
			
			String visible=blockData.selectionMask[i][j][k]>0?"V":"M";
			
			return i+","+j+","+k+" ("+visible+")";
		}
		
		public void setSelectMask(short selectMask) {
			this.selectMask = selectMask;
		}
		
	}
	
    public void menuSelected(MenuEvent arg0) {
    	
    	super.menuSelected(arg0);
    	
    	Object o = arg0.getSource();
    	
		if(o==help_jm){
			
			help();
			
		}
    }
    
	private void help() {
	
		
		HelpPanel hp=new HelpPanel(300,200,this.getX()+100,this.getY(),HelpPanel.BLOCK_EDITOR_HELP_TEXT,this);
		
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

	@Override
	public void moveSelectedPointWithMouse(Point3D p3d, int type) {
		// TODO Auto-generated method stub
		
	}

}
