var Player=function (x,y,w,h)
{
	this.x=x;
	this.y=y;
	this.width=w;
	this.height=h;
	this.isAlive=true;
	
};


Player.prototype.draw = function(ctx)
{
	ctx.fillStyle=rgb(100,100,200);
	ctx.fillRect(this.x,this.y,this.width,this.height);
};


Player.prototype.clear = function()
{

};

Player.prototype.move = function(x,y)
{

	this.x = x;
	this.y = y;
};


Player.prototype.moveLeft=function()
{
	this.x -= 10;
};
Player.prototype.moveRight=function()
{
	this.x += 10;
};
Player.prototype.moveUp=function()
{
	this.y -= 10;
};
Player.prototype.moveDown=function()
{
	this.y += 10;
};

Player.prototype.checkCollision = function (e){
 
    var collides=false;
         
        //do the two bounding boxes overlap?
        if ((this.x < e.x + e.width) &&
        (this.x + this.width > e.x) &&
        (this.y + this.height > e.y) &&
        (this.y < e.y + e.height) )
        {           
             
            collides = true;
                     
        }
         
        return collides;
};