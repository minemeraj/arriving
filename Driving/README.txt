	**********************
	*  READ ME CARDRIVING VERSION   9.0.5
	*
	*  by Piazza Francesco Giovanni 
	*  Tecnes Milano ,Italy http://www.betacom.it
	*	
	**********************
		
	Java version:1.7.0_02
	Edited using Eclipse Platform Version 3.7.0
	
	************
	*
	* REFERENCE BOOK :
	* Developing Games In Java - Book
	* By David Brackeen, Bret Barker, Laurence Vanhelsuwé 
	* 
	* SEE FOR TEXTURE MAPPING ALSO THE ARTICLES BY 
	* Rosalee Wolfe
	* 
	*********************
	*
	* to launch from command line extract the lib directory from the jar 
	* in the same directory and write :
	*
	* java -Xms128m -Xmx256m  -classpath Driving.jar   com.main.CarFrame
	*
	* to launch with shading, which increases the load, use command:
	*
	* java -Xms128m -Xmx256m  -classpath Driving.jar   com.main.CarFrame +s
	*
	* to launch the road editor use the command:
	*
	* java -classpath Driving.jar    com.editors.road.RoadEditor
	*
	*** to launch the object editor use the command:
	*
	* java -classpath Driving.jar    com.editors.object.ObjectEditor
    *
	*
	* to launch the object iperview editor use the command:
	*
	* java -classpath Driving.jar    com.editors.iperview.IperviewEditor
	
	* to launch the autocar editor use the command:
	*
	* java -classpath Driving2D.jar    com.editors.autocars.AutocarEditor
	*
	*
	***** To launch custom editors:
	*
	*
	* java -classpath Driving.jar    com.editors.buildings.BuildingsEditor
	*
	* java -classpath Driving.jar    com.editors.animals.AnimalsEditor
	*
	* java -classpath Driving.jar    com.editors.forniture.FornitureEditor
	*
	* java -classpath Driving.jar    com.editors.plants.PlantsEditor
	*
	* java -classpath Driving.jar    com.editors.cars.CarsEditor
	*
	* java -classpath Driving.jar    com.editors.weapons.WeaponsEditor
	*
	*
	**** To launch other experimental editors:
	*
	* to launch the cubic editor use the command:
	*
	* java -classpath Driving.jar    com.editors.cubic.CubicEditor
	*	
	* to launch the bloc editor use the command:
	*
	* java -classpath Driving.jar    com.editors.block.BlockEditor
	*
	* to launch the Mould editor use the command:
	*
	* java -classpath Driving.jar    com.editors.others.Mould
	*
	*********************
	
	Main Game short keys:
	
	To change car: c key
	To change gear: up arrow key,down arrow key.
	To brake: F key
	To steer left,right: left arrow key,right arrow key or wasd sequence.
	To change the background: b key
	To restart: press the button "Reset car".
	Horn :press H key.
	Rear view: keep pressing Z key.
	External camera: 1 key
	Driver camera: 2 key
	To change engine rotation : N and M keys
	
	Press Esc to exit program.

    Note that in the lib folder you will find couples of files of the type:
    
	car_texture_t0.gif
	cardefault3D_0 (see section OBJECTS 3D AND CARS FORMAT)
		
	which are respectively the texture and the 3D data to draw the car. 
	The pure green in textures is the totally transparent color.	  
	
	You can also add your personal background going to the lib directory and 
	adding an image file named background_NUMBER_OF_IMAGE.gif, where NUMBER_OF_IMAGE
	is an integer starting from 1.
	The height should be 500 pixels, the width at least 1000 pixels.
	The blue is the transparent color.
	
	To add your personal object open the lib directory and create three files for each object: 
	
	a) object_NUMBER_OF_OBJECT.gif : image displayed in the RoadEditor
	b) object3D_NUMBER_OF_OBJECT.gif: file of the 3D object mesh (see section OBJECTS 3D AND CARS FORMAT)
	c) object3D_texture_NUMBER_OF_OBJECT.jpg: file of the object cubic texture.
	
	Both b) and 3) files can be generated via the ObjectEditor.
	
	*************************
	---ABOUT THE POLYGONS :
	
	A polygon with its vertices appearing in counterclockwise order is the "front" of the polygon.
	REFERENCE BOOK,chapter 7. Use only convex polygons!
	
	---TERRAIN FORMAT:
	
	Is a succession of line sequences :
	
	1-Points coordinates (values separated by " ")
	
	v=x0 y0 z0
	
	2) vt= vertex texture coordinate  (values separated by " ")
    
    vt=x0 y0
	
	3-Polygons (values separated by " ")
		
	f=T<NUMTEXTURE> C<HEXCOLOR> W<HASWATER> POINT0_INDEX/POINT0_TEXTURE_INDEX POINT1_INDEX/POINT1_TEXTURE_INDEX
	
	where HEXCOLOR is a string rgb color in the hex representation, e.g. for white :FFFFFF.
	and NUMTEXTURE is the index number of the texture associated, as found in the RoadEditor.
	HEXCOLOR is used if you don't want to sue textures.
	
	HASWATER=0,1 show if the polygon area is filled (=1) or not (=0) with water.
	
	POINT0_INDEX is the vertex index
	POINT0_TEXTURE_INDEX is vertex texture coordinate index
	

    -- SPLINE FORMAT
    
    Splines are in a sequence of coupled tags:
    <spline>
	...
	</spline>
	<spline>
	...
	</spline>
	
	Each spline si determined by its (sp)nodes:
	
	<spline>
	v=TN x y z
    v=TN x y z
    ...
	</spline>
	
	where N is a 0,1,2... spline index, and x,y,z are the node coordinates. 
	
	 Note that in the lib folder you will find groups of files of the type:
	 
	 spline_editor_N.jpg
	 spline_mesh_N
	 spline_texture_N.jpg
	 
	 
	 where 
	 
	 spline_editor_N.jpg is a texture used in the RoadEditor and AutocarEditor for the Nth psline.
	 spline_mesh_N is the mesh used in the main program for the Nth psline.
	 spline_texture_N is a texture used in the main program for the Nth psline.
	
	-- OBJECT LIST FORMAT:
	
	X Y Z OBJECT-INDEX[ROTATION_ANGLE HEXCOLOR]
	
	where X,Y,Z are the object location,
	OBJECT-INDEX is the index identifier of the type of object,ROTATION_ANGLE is the rotation angle around
	the center with respect to the standard position, HEXCOLOR is a string rgb color in the hex
	representation, e.g. for white :FFFFFF.
	
	if you don't want to use textures create a file driving.properties in the lib directory and
	write in: ISUSETEXTURE=false
	
	
	---OBJECTS 3D AND CARS FORMAT:
	
    every object is defined by two files:
    
    object3D_[num_object] which define the object mesh
    object3D_texture_[num_object].jpg which define the cubic texture for the object
    
    in the same way every car is composed by two files:
    
    cardefault3D_[num_car] which define the object mesh
  	car_texture_t[num_car].gif which define the cubic texture for the object
    
    every mesh files is composed by the lines:
    
    a) v=vertex in the format  (values separated by " ")
    
    v=x0 y0 z0
     
    b) vt= vertex texture coordinate  (values separated by " ")
    
    vt=x0 y0
     
    c) f=face (polygon) composing the object in the format  (values separated by " ")
    
    f=[DATA] I0/VT0 I1/VT1
    
    the [DATA] parts contains infos about the cubic face to use in texturing.
    
    Ii is the i vertex of the polygon, VTi is the i vertex texture coordinate index.
   
    
    
    ******** CUBIC TEXTURE
    
    Here the face numbers for the cubic textures:
    
    CUBE_BOTTOM=-1;
	CUBE_BACK=0;
	CUBE_TOP=1;
	CUBE_LEFT=2;
	CUBE_RIGHT=3;
	CUBE_FRONT=4; 
	
	Cubic Mesh schema (Bottom not used):
	
	
	-----------------------------
	|		|      		 | 		|
	|		|     4   	 |		| 
	|       |      		 | 		|	
	-----------------------------
	|       | 	         |      |  
	|   2   |	  1      |  3   | 
	|       | 	         |      | 
	|       | 	         |      | 
	-----------------------------    
    | 		|            |		|
	|		|     0      |	    |
	|		|            | 	    |
	-----------------------------	
	
	y
	^
	|
	|
	0--->x	
	
	4 parameters to navigate the texture, from left to right and bottom to top:
	
	deltaX,deltaX2
	deltaY,deltaY2
	
	TEXTURE_WIDTH  = deltaX+deltaX2
	TEXTURE_HEIGHT = deltaY+deltaY2
	
	deltaX=deltaY=OBJECT_HEIGHT
	deltaX2=OBJECT_WIDTH+OBJECT_HEIGHT
	deltaY2=OBJECT_LENGTH+OBJECT_HEIGHT
	
	so:
	
	OBJECT_LENGTH  = deltaY2-deltaY = maxY-minY
	OBJECT_WIDTH   = deltaX2-deltaX = maxX-minX
	OBJECT_HEIGHT  = deltaX = deltaY = maxZ-minZ
	
	CUBE'S VERTICES NUMERATION


	  7       6
	   ______
	  /     / 
	 /     /
	/_____/
	
	4     5 	
	
	  3       2
	   ______
	  /     / 
	 /     /
	/_____/
	
	0     1 
	
	************
	
	ROAD EDITOR

    
    The game uses for the maps the files in the lib directory with the name type: 
    landscape_<MAPNAME>
    
    Ex:landscape_default,landscape_city0.
    
    Autocars files are named following the map name:
    If map file is:
    	landscape_<MAPNAME>
    then the program associates the autocars file:
   		 autocars_<MAPNAME>
   	If no autocar file is defined, the program ignore it.	 
    
    Select tiles with left mouse button in the area, select object by clicking in the inner area.
   
    Select object and road part using left mouse button.
    Insert objects and road points with right mouse button.
    Change terrain using right mouse button.
    Rotate 3D view with keys q and w.
    
    Move objects with the arrows panel.

    You can also keep the mouse pressed and select a rectangular region o points/objects.
   
    To select a road polygon press in his inner space with the left mouse button.After selecting 
    a polygon press the "Polygon detail" button to see the ordered list of points composing it.

    Editor short keys:

        b: change selected object 
        p: change selected point
        n: start build polygon
        e: deselect all
        f1: zoom in
        f2  zoom out
        
    To insert the node of a spline press the right button of the mouse in the location where you want
    to insert it. 
    
    To insert an object press the button "insert object" to have it in a fixed starting point, or type the coordinates
    of the new object and then press the button "insert object".
    
   
    If you don't want to clean up some text boxes after every insertion (to reuse their values) check 
    the case at the right of them.
        
    To move objects and road points use also the panel with the arrows at the cardinal points,
    putting in the central text field the quantity by which you want to move them.
    
    The file object_[NUM_OBJECT].gif is the image to display in the panel to choose the object type to
    insert/modify in the road. 
    
    **************
    
    OBJECT EDITOR
    
    You can select a point or a line using the lists or the mouse (only for the points).
    After selecting a polygon press the "Polygon detail" button to see the ordered list of points composing it.
    
    Using the x,y,z fields you can also change the values for all the selected points.
   
    Object Editor short keys:
    
    Arrow keys left and right: move y0 origin.
    Arrow keys up and down : move z0 origin.
    Keys page up and page down : move x0 origin.
    Key c: change selected point.
    key j: join selected points
    key p: build polygon
    key i: insert point
    Key e: deselect all
    Key t: select all
    Key d: delete selected point or line
    Key F3-F4: enable or disable multiple selection. 
    Key F1 and F2 : zoom in and out
    Page up and down:move lines up and down the list
    Key less: invert line point order.
    
    key q,w,a,s : rotate axes
    
    
    You can save an object in the standard cube texture format via the menu Save->Save mesh 
    To keep custom texture coordinates save via menu  Save->Save custom mesh

    To load an object file go to menu Load->Load lines or drag in the file.
    
    Use menu Save->Save base texture to save a basic cubic trace for the object texture.
    
    You can also save in a format useful for the old versions of the 
    Driving program via Save->Save poly format (note that you can't load from
    this type of format).
    
    To create a new polygon press "Start polygon points sequence", select the sequence of points part of
    the polygon, then press "Build polygon".
    
    Change view mode choosing from the View menu:3D,Top,Left,Front view are available.
    
    Choose the menu View->Preview to see a 3D grey scale preview of the object.
    
    -----AUTOCARS FORMAT:
	
	ROAD_0=x0,y0,z0_x1,y1,z1_...
	ROAD_1=
	...
	DATA_0=car_type
	INIT_0=center_x,center_y,u,nu,fi,steering,autoline_index (all double, except the last one)
		
	DATA_1=...
	
	center_x,center_y: initail position of the auotcar center, better if very near
	to the road line
	
	********* AUTOCARS EDITOR
   
	Add an autoline by choosing menu Other->Add autoline,
	then add points by clicking with right mouse button.
	   
	Add autocar by choosing menu Other->Add autocar, then move your autocar near an already
	existing autoline.
	   
	Assign an autoline to the autocar, unless is parked.
	   
	Assign a speed to an autocar (U and Nu), or set it parked. 
	   
	Assign a direcion with the steering angle.
	   
	Remember to press "Change Car" button to apply car modifications.
	
    --- EXPERIMENTAL EDITOR:
    
    IPERVIEW EDITOR
    
    To move points with mouse,disable Multiple selection,select a point 
    with the left mouse button,keep CTRL pressed add drag it into a new position.
    
    MOULD EDITOR
    
    Rotator:
    
    Load Profile Image:
    Create a profile using an image of HALF your object with 
    WHITE background and RED outline.The radius is the y-coordinate of the image.
    
    "Only data" options use as parallels only the data read, no interpolation or equal 
    parallels.
    
    Load Profile Data
    Create a profile using a data file of x and y coordinates, the radius is the y-coordinate
    of the image. The format is data with space separation.
    
    X Y
      
    
    Save as Polygon Mesh.
    
    Read points:
    
    The editor can read points from an image and save the as points of a mesh.
    
    

    
   ************** PERFORMANCE NOTES   
   
   	The actual drawing operation reaches a maximum of 28 fps,  with a minimum of 17 fps.
   	
   	
   	************ SCALE NOTE
   	
   	Roughly one can estimates the scale conversion 1m = 26px
   	
   	So, a speed of 100px/frame is circa 100/26 m / (1000/28 s)=  2800/2600 m/s = 1.08 m/s
   	
   	************* NOTES ON CUSTOM EDITORS MODELS
   	
   	Use f1,f2 to zoom-in/out, q and w keys to rotate.
   	
   	--Vehicles:
   	
   	Car proportions based on BMW 5 Series Sedan (length=4907 mm, wheelbase=2968 mm).

	Truck proportions based on Iveco New Eurocargo Euro VI ( length=8692mm).
	
	Tractor proportions based on John Deer 6105R ( wheelbase=2580mm)
	
	Railroad car 0 proportions based on Sgmns-w by Kockums Industrier, 1435 mm gauge,
	with a 40 X 8 X 8 feet container, length=14000 mm, distance of boogie centers=10200 mm.
	
	Railroad car 1 proportions based on 70m3 4-axle Tank Car for Chemical Products
	by Greenbrier Companies, 1435 mm gauge,	length=14260 mm, distance of boogie centers=10460 mm.
	
	Railroad car 2 proportions based on 102t 60m3 4-axle  Box Wagon
	by Greenbrier Companies, 1435 mm gauge,	length=12730 mm, distance of boogie centers=8520 mm.
	
	Railroad car 3 proportions based on Fammoorr 31 tonnes Ore Wagon by Kockums Industrier,1435 mm gauge,
 	length=9220 mm, distance of boogie centers=6744 mm.
 	
 	Railroad car 4 proportions based on TC trailer car of Viaggio Light by Siemens AG, 1435 mm gauge,
 	length=26100 mm, distance of boogie centers=19000 mm.
	
	Airplane proportions based on Airbus A320 (length=37.57m, wing span 34.10M).
	
	Tanker ship sizes:
	length=175m  breadth=27.6m
	from keel to upper deck=17.6m
	Stern castle height from deck=23m.
	
	--Weapons:
	
	Weapon gun measures based on Colt 45 MK IV series 70 Government model,
	length= 210 mm, barrel length=127 mm.
	
	Weapon shotgun measures based on Remington Model 870 American Classic Pump  12 gauge,
	length= 1230 mm, barrel length= 710 mm; 
	
	Weapon shotgun measures based on Stoeger Coach Gun Supreme Shotgun  12 gauge,
	length 927 mm, barrel length= 508 mm; 
	
	Weapon submachine gun measures based on Maschinenpistole (MP) 40,
	length=  833 mm  stock extended,  630 mm stock folded, barrel length=251 mm; 
	
	Weapon revolver measures based on Colt Walker 1847, length 395 mm, barrel length= 229 mm; 
    
	--Forniture real measures:
	
	bed height with mattress: 52cm 
	bed height no mattress: 34 cm
	bed total LxW: 201 cm x 87cm
	bookcase 3 shelters L X W X H =34.3 cm x 45.2 cm x 99.9 cm 
			+ basement height = 71.7 cm
	chair seat height: 48 cm
	cupboard LxWxH: 111.5 cm x 49.5 cm x 206 cm
	sofa seat height: 45 cm
	street light: total straightened length: 6367 mm, actual max height: 5361 mm
				 base dimater: 121 mm, light lamp length:588mm
	table LxWxH:  140 cm x  79cm x 79 cm
	toilet LxWxH: 69 cm X 36 cm x 36.5 cm
	wardrobe LxWxH: 58 cm X 89.4 cm X 260 cm
	
	
	--On the buildings:
	
	Two stories house dimensions:
	L x W x H= 11m x 8m x 6.3m; roof height=1m
	Four stories house dimensions:
	L x W x H= 11m x 8m x 12.6m; roof height=1m
	
	The bell towers are modeled on:	
	The Treviglio's (BG) bell Tower, tall 65m with a base of
	6.5X6.5 meters.
	The Rivolta d'Adda's (CR) bell Tower, tall 45m with a base of
	6X6 meters.
	
	building block_0=7.7m x 7.7m x 7.7m
	building block_1=7.7m x 7.7m x 15.4m 
	building block_2=15.4m x 15.4m x 23.1m 
	building block_3=15.4m x 30.8m x 23.1m 
	building block_4=7.7m x 23.1m x 15.4m 
	building block_5=23.1m x 23.1m x 15.4m 
	building block_6(tower) radius=3.8m, h=15.4m 
	
	