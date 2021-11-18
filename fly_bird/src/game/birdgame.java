package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class birdgame extends JPanel{
	BufferedImage background,startimage,gameoverimage;
	ground gr;
	column co1,co2;
	bird bi;
	int score;
	int state;
	public static final int start=0;
	public static final int running=1;
	public static final int gameover=2;
	public birdgame() throws Exception
	{
		background=ImageIO.read(getClass().getResource("/resource/bg.png"));
		startimage=ImageIO.read(getClass().getResource("/resource/start.png"));
		gameoverimage=ImageIO.read(getClass().getResource("/resource/gameover.png"));
		gr=new ground();
		co1=new column(1);
		co2=new column(2);
		bi=new bird();
		score=0;
		state=start;
	}
	public void paint(Graphics g)
	{
		g.drawImage(background,0,0,null);
		g.drawImage(gr.image,gr.x,gr.y,null);
		g.drawImage(co1.image,co1.x-co1.width/2,co1.y-co1.height/2,null);
		g.drawImage(co2.image,co2.x-co2.width/2,co2.y-co2.height/2,null);
		Graphics2D g2=(Graphics2D)g;
		g2.rotate(-bi.alpha,bi.x,bi.y);
		g.drawImage(bi.image,bi.x-bi.width/2,bi.y-bi.height/2,null);
		g2.rotate(bi.alpha,bi.x,bi.y);
		Font f=new Font(Font.SANS_SERIF,Font.BOLD,40);
		g.setFont(f);
		g.drawString(""+score,40,60);
		g.setColor(Color.white);
		g.drawString(""+score,40-3,60-3);
		switch(state)
		{
			case start:
				g.drawImage(startimage,0,0,null);
				break;
			case gameover:
				g.drawImage(gameoverimage,0,0,null);
				break;
		}
	}
	public void action() throws Exception
	{
		MouseListener l=new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				try
				{
					switch(state)
					{
						case start:
							state=running;
							break;
						case running:
							bi.flappy();
							break;
						case gameover:
							co1=new column(1);
							co2=new column(2);
							bi=new bird();
							score=0;
							state=start;
							break;
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		};
		addMouseListener(l);
		while(true)
		{
			switch(state)
			{
				case start:
					bi.fly();
					gr.step();
					break;
				case running:
					gr.step();
					co1.step();
					co2.step();
					bi.fly();
					bi.step();
					if(bi.x==co1.x||bi.x==co2.x)
					{
						score++;
					}
					if(bi.hit(gr)||bi.hit(co1)||bi.hit(co2))
					{
						state=gameover;
					}
					break;
			}
			repaint();
			Thread.sleep(1000/60);
		}
	}
	public static void main(String[] args) throws Exception
	{
		JFrame frame=new JFrame();
		birdgame game=new birdgame();
		frame.add(game);
		frame.setSize(440,670);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.action();
	}
}
