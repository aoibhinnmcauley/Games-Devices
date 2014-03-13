var Goal=function (x,y,w,h)
{
	this.x=x;
	this.y=y;
	this.width=w;
	this.height=h;
	this.isAlive=true;
	
};


Goal.prototype.draw = function(ctx)
{
	if(this.isAlive == true)
	{
	ctx.fillStyle=rgb(0,55,155);
	ctx.lineWidth = 1;
	ctx.strokeRect(this.x,this.y,this.width,this.height);
	}
};


Goal.prototype.clear = function()
{

};

Goal.prototype.move = function(x,y)
{

	this.x = x;
	this.y = y;
};