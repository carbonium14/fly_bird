package game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class bird {
	BufferedImage image;
	BufferedImage[] images;
	int x,y;
	int width,height;
	int size;
	double g;
	double t;
	double v0;
	double speed;
	double s;
	double alpha;
	int index;
	public bird() throws Exception
	{
		image=ImageIO.read(getClass().getResource("/resource/0.png"));
		width=image.getWidth();
		height=image.getHeight();
		x=132;
		y=280;
		size=40;
		g=4;
		v0=20;
		t=0.25;
		speed=v0;
		s=0;
		alpha=0;
		images=new BufferedImage[8];
		for(int i=0;i<8;i++)
		{
			images[i]=ImageIO.read(getClass().getResource("/resource/"+i+".png"));
		}
		index=0;
	}
	public void fly()
	{
		index++;
		image=images[(index/12)%8];
	}
	public void step()
	{
		double v0=speed;
		s=v0*t+g*t*t/2;
		y=y-(int)s;
		double v=v0-g*t;
		speed=v;
		alpha=Math.atan(s/8);
	}
	public void flappy()
	{
		speed=v0;
	}
	public boolean hit(ground gr)
	{
		boolean hit=y+size/2>gr.y;
		if(hit)
		{
			y=gr.y-size/2;
			alpha=-3.14159265358979323/2;
		}
		return hit;
	}
	public boolean hit(column co)
	{
		if(x>co.x-co.width/2-size/2&&x<co.x+co.width/2+size/2)
		{
			if(y>co.y-co.gap/2-size/2&&y<co.y+co.gap/2+size/2)
			{
				return false;
			}
			return true;
		}
		return false;
	}
}
