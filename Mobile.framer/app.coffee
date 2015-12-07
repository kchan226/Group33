mask = new Layer 
	width: 736
	height: 1324
	backgroundColor:"#fff"
mask.center()

# This imports all the layers for "GreenthumbHomePage" into greenthumbhomepageLayers
PSD = Framer.Importer.load "imported/GreenthumbHomePage"

for i in Object.keys(PSD)
	#for each layer in the imported file, hide it
	PSD[i].y = -Screen.height

#print PSD['home_page'].size
#print Screen.size
page = new PageComponent
	height: Screen.height
	width: Screen.width
	scrollVertical: false
	
page.animationOptions =
    curve: "ease"
    time: 0.50
	
#print PSD['home_page'].size
	
HomeLayer = new Layer
	width: PSD['home_page'].width
	height: PSD['home_page'].height
	x: 0
	y: 0
	backgroundColor: "#ff0000"
	image: PSD["home_page"].image
	superLayer: page.content
		
AddButton = new Layer
	superLayer: HomeLayer
	x: 80
	y: 105
	width: 140
	height: 145
	opacity: 0
	
ToDoList = new Layer
	superLayer: HomeLayer
	x: 50
	y: 670
	width: 650
	height: 265
	opacity: 0
	
AddTaskList = new Layer
	superLayer: HomeLayer
	x: 50
	y: 1020
	width: 650
	height: 245
	opacity: 0
	
ToDoListPage = PSD.to_do_list_page
	
ToDoListPage = new Layer
	width: ToDoListPage.width
	height: ToDoListPage.height
	superLayer: page.content
	image: ToDoListPage.image
	
ToDoRoses = new Layer
	width: Screen.width - 12
	x: 6
	y: 255
	height: 300
	backgroundColor: "#ff"
	superLayer: ToDoListPage
	
ToDoPeonies = new Layer
	width: Screen.width - 12
	x: 6
	y: 565
	height: 300
	backgroundColor: "#ff"
	superLayer: ToDoListPage
		
page.addPage(ToDoListPage)

	
ToDoList.on Events.Click, ->
	page.snapToPage(ToDoListPage)
	
ToDoRoses.on Events.Click, ->
	ToDoRoses.backgroundColor = "green"
	ToDoRoses.opacity = 0.5	
	
ToDoPeonies.on Events.Click, ->
	ToDoPeonies.backgroundColor = "green"
	ToDoPeonies.opacity = 0.5
	
AddPlantLayer = new Layer
	width: PSD['add_plant_page'].width
	height: PSD['add_plant_page'].height
	superLayer: page.content
	image: PSD["add_plant_page"].image
	
page.addPage(AddPlantLayer)


HomeScreenWithNotif = new Layer
	width: PSD['HomeScreen_With_Notif'].width
	height: PSD['HomeScreen_With_Notif'].height
	superLayer: page.content
	image: PSD['HomeScreen_With_Notif'].image
	
NotifPage = new Layer
	width: PSD.Notif.width
	height: PSD.Notif.height
	superLayer: HomeScreenWithNotif	
	image: PSD['Notif'].image
	#opacity: 0
	
page.addPage(HomeScreenWithNotif)
	
AddButton.on Events.Click, ->
	page.snapToPage(AddPlantLayer)
	
DoneButton = new Layer
	superLayer: AddPlantLayer
	x: 515
	y: 132
	width: 185
	height: 88

BackButton = new Layer
	superLayer: AddPlantLayer
	x: 42
	y: 132
	width: 185
	height: 88
	
AddTaskPage = new Layer
	superLayer: page.content
	image: PSD['AddTask'].image
	width: PSD['AddTask'].width
	height: PSD['AddTask'].height
	
page.addPage(AddTaskPage)

TaskDoneButton = new Layer
	superLayer: AddTaskPage
	x: 512
	y: 132
	width: 185
	height: 88
	
TaskBackButton = new Layer
	superLayer: AddTaskPage
	x: 40
	y: 132
	width: 185
	height: 88
	
HomePageTaskNotif = new Layer
	superLayer: page.content
	image: PSD['HomePageWithTask'].image
	width: PSD['HomePageWithTask'].width
	height: PSD['HomePageWithTask'].height
	
page.addPage(HomePageTaskNotif)
	
DoneButton.on Events.Click, ->
	page.snapToPage(HomeScreenWithNotif)
	
BackButton.on Events.Click, ->
	page.snapToPreviousPage()
	
AddTaskList.on Events.Click, ->
	page.snapToPage(AddTaskPage)
	
HomeScreenWithNotif.on Events.Click, ->
	page.snapToPage(HomeLayer)
	
TaskBackButton.on Events.Click, ->
	page.snapToPage(HomeLayer)
	
TaskDoneButton.on Events.Click, ->
	page.snapToPage(HomePageTaskNotif)
	
HomePageTaskNotif.on Events.Click, ->
	page.snapToPage(HomeLayer)
	

	

	

	
	





