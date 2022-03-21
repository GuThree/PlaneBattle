package com.tedu.element;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class Boom extends ElementObj{
	private int x,y,w,h;
	
	private int type = 0;
	
	private long preTime = 0L;
	
	private int gapTime = 200;
	
	private ElementManager em = ElementManager.getManager();
	
	private ArrayList<Image> boomImage = new ArrayList<>();
	
	public Boom(int x, int y, int w, int h) {
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		
		boomImage.add(new ImageIcon("img/boom/boom1.png").getImage());
		boomImage.add(new ImageIcon("img/boom/boom2.png").getImage());
		boomImage.add(new ImageIcon("img/boom/boom3.png").getImage());
		boomImage.add(new ImageIcon("img/boom/boom4.png").getImage());
		
		this.setPreTime(System.currentTimeMillis());
		
		em.addElement(this, GameElement.DIE);
	}
	
	public void showElement(Graphics g) {
		long gameTime = System.currentTimeMillis();
		if(gameTime - preTime >= gapTime) {
			type++;
			preTime = gameTime;
		}

		if(type<4) {
			g.drawImage(boomImage.get(type), this.getX(), this.getY(), this.getW(), this.getH(), null);
		}		
		if(type>=4) {
			this.setLive(true);
		}
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getPreTime() {
		return preTime;
	}

	public void setPreTime(long preTime) {
		this.preTime = preTime;
	}
	
	
}
