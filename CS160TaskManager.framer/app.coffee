# This imports all the layers for "ChecklistWatch" into checklistwatchLayers
PSD = Framer.Importer.load "imported/ChecklistWatch"

Task_No = PSD.Task_No
Water_Roses = PSD.WaterRosesBack
Water_Peonies = PSD.WaterPeonies
Next_Popup = PSD.NextTaskPopup

#Keep the image layers off of the Framer canvas
for i in Object.keys(PSD)
	PSD[i].y += 1000

#PAGE COMPONENT
page =  new PageComponent 
	width: 300
	height: 300
	backgroundColor: "#FFF"
	scrollVertical: false
	borderRadius: 320/2
page.center()
page.borderRadius = page.width/2
page.x += 2.5

RosesLayer = new Layer
	x: -300
	y: 200
	width: 300
	height: 300
	image: Water_Roses.image
	superLayer: page.content
	
page.addPage(RosesLayer)

NextPopupLayer = new Layer
	x: 0
	y: 0
	width: 300
	height: 300
	image: Next_Popup.image
	superLayer: page.content
	
page.addPage(NextPopupLayer)

NoChoice = new Layer
 	superLayer: NextPopupLayer
 	x: 155
 	y: 115
 	width: 110
 	height: 160
 	
YesChoice = new Layer
	superLayer: NextPopupLayer
	x: NextPopupLayer.x - 265
	y: NextPopupLayer.y + 115
	width: 110
	height: 160

PeoniesLayer = new Layer
	x: -255
	y: 200
	width: 300
	height: 300
	image: Water_Peonies.image
	superLayer: page.content
	
page.addPage(PeoniesLayer)

#x = 0


# ADD SKIN ON TOP OF EVERYTHING ELSE
moto360 = new Layer
	width: 600, height: 900
	image: "images/moto_moto360-mask.png"
moto360.scaleX = 0.60
moto360.scaleY = 0.60
moto360.center()
#moto360

Events.wrap(window).addEventListener "resize", (event) ->
	moto360.center()
	page.center()
	page.x += 2.5

animationA = new Animation
    layer: moto360
    properties: rotationZ: 2
	curve: "linear"
	time: 0.005

animationB = new Animation
	layer: moto360 
	properties: 
		rotationZ: 0 
	curve: "spring(4000,0,200)"  

#animationB = animationA.reverse()

RosesLayer.on Events.Click, ->
	animationA.start()
	RosesLayer.x -= 300
	NextPopupLayer.x -= 300
	#page.snapToPage(NextPopupLayer)
	
animationA.on Events.AnimationEnd, ->
	animationB.start()	

NoChoice.on Events.Click, ->
	RosesLayer.x += 300
	NextPopupLayer.x += 300
	#page.snapToPage(RosesLayer)
	
YesChoice.on Events.Click, ->
	NextPopupLayer.x -= 300
	PeoniesLayer.x -= 600
	#page.snapToPage(PeoniesLayer)


	


	
##PAGE COMPONENT
# page =  new PageComponent 
# 	width: 320
# 	height: 320
# 	backgroundColor: "#FFF"
# 	scrollVertical: false
# 	borderRadius: 320/2
# page.center()
# page.borderRadius = page.width/2
# page.x += 2.5
# 
# # ADDING MAIN UI SCREENS TO PAGE COMPONENT
# staffLayers2 = Framer.Importer.load "imported/staff"
# staff_keys = Object.keys(staffLayers2)
# for i in staff_keys
# 	staffLayers2[i].y = -180
# 
# # # Create layers in a for-loop
# i = 0
# for j in staff_keys
# 	layer = new Layer 
# 		superLayer: page.content
# 		width: 320
# 		height: 320
# 		image: staffLayers2[j].image
# 		backgroundColor: "#fff"
# 		borderRadius: 320/2
# 		opacity: 1
# 		x: 320 * i
# 		i++
# # 	
# # # Staging
# page.snapToNextPage()
# page.currentPage.opacity = 1
# 
# # Update pages
# page.on "change:currentPage", ->
# 	page.previousPage.animate 
# 		properties:
# 			opacity: 0.3
# 			scale: 0.8
# 		time: 0.4
# 		
# 	page.currentPage.animate 
# 		properties:
# 			opacity: 1
# 			scale: 1
# 		time: 0.4
# 
# # ADD SKIN ON TOP OF EVERYTHING ELSE
# moto360 = new Layer
# 	width: 600, height: 900
# 	image: "images/moto_moto360-mask.png"
# moto360.scaleX = 0.60
# moto360.scaleY = 0.60
# moto360.center()
# 
# Events.wrap(window).addEventListener "resize", (event) ->
# 	moto360.center()
# 	page.center()
# 	page.x += 2.5
m