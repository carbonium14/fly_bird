package game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ground {
	BufferedImage image;
	int x,y;
	int width,height;
	public ground() throws Exception
	{
		image=ImageIO.read(getClass().getResource("/resource/column.png"));
		width=image.getWidth();
		height=image.getHeight();
		x=0;
		y=500;
	}
	public void step()
	{
		x--;
		if(x==-109)
		{
			x=0;
		}
	}
}
