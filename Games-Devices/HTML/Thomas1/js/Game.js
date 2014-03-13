//These are global variables and are NOT recommended because they cause bad things to happen
//Some refactoring will be needed...
var canvas, ctx;


function Game ()
{


}


Game.prototype.initWorld = function()
{
	this.player = new Player(50,50,75,75);
	this.goal = new Goal (250,300,100,100);
}


Game.prototype.initCanvas=function () { 

	canvas = document.createElement('canvas'); 
	ctx = canvas.getContext('2d'); 

	document.body.appendChild(canvas);
	//set canvas to size of the screen.
	canvas.width = window.innerWidth-25; 
	canvas.height = window.innerHeight-25;
}


Game.prototype.update = function () {
	  if(KeyController.isKeyDown(Key.LEFT)) {
	  	ctx.clearRect(this.player.x,this.player.y,this.player.width,this.player.height);
	  	this.player.moveLeft();
      }
      if(KeyController.isKeyDown(Key.RIGHT)) {
      	ctx.clearRect(this.player.x,this.player.y,this.player.width,this.player.height);
	  	this.player.moveRight();
      }	
      if(KeyController.isKeyDown(Key.UP)) {
      	ctx.clearRect(this.player.x,this.player.y,this.player.width,this.player.height);
	  	this.player.moveUp();
      }	  	
      if(KeyController.isKeyDown(Key.DOWN)) {
      	ctx.clearRect(this.player.x,this.player.y,this.player.width,this.player.height);
	  	this.player.moveDown();
      }	 
    
      	this.collisionResponse();
        		
}

/*
 *  @param e game entity
 */
Game.prototype.collisionResponse=function ()
{
	  if(this.player.checkCollision(this.goal)){
	  
	    //stops the goal from drawing
		this.goal.isAlive = false;
		
		ctx.save();
		//add in your own colour
		ctx.fillStyle=rgb(100,0,100);
		//change this
		ctx.font = 'italic 40pt Calibri';
		//otherwise bottom
		ctx.textBaseline = "top";
		//Put your message here; x and y are where the message will appear...
		ctx.fillText("LEVEL COMPLETE!!!!",100,100);
		//Any idea what save and restore do?
		ctx.restore();
}
}
	
Game.prototype.gameLoop = function () 
{

 	  game.update();
	  game.draw();
	  
	  window.requestAnimFrame(game.gameLoop);
}

Game.prototype.draw =function ()
{
	this.player.draw(ctx);
	this.goal.draw(ctx);	
}