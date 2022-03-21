package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.controller.GameThread;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Play extends ElementObj{
	public static int attack = 1;//攻击力
	public Play() {}	
	public Play(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);		
	}
	
	
	@Override
	public ElementObj createElement(String str) {//小工厂
		this.setX(180);
		this.setY(550);
		this.setW(75);
		this.setH(75);
		this.setBlood(100);
		this.setIcon(new ImageIcon(str));
		this.setType(1);
		return this;
	}
	
	/**
	 * 面向对象的第一个思想：对象自己的事情自己做
	 */
	@Override
	public void showElement(Graphics g) {
		/* 绘制图片 */
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);		
	}
	@Override
	public void isCollosion(boolean status) {
		if(!status) {
			return;
		}else {
			this.setBlood(this.getBlood()-4);
			if (this.getBlood()<=0) {
				this.setLive(false);
			}
		}
	}
	/**
	 * 随鼠标移动主角
	 */
	public void move(int x,int y) {//具体坐标的调整待细化
			if(x>this.getW()/4&&x<450-this.getW()/3&&y>this.getH()+150&&y<700-this.getH()/8&&GameThread.ispause==true){
				this.setX(x-this.getW()/2-5);
				this.setY(y-this.getH());			
			}
	}
	
	/*
	 * 子弹的添加 需要的是发射者的坐标位置
	 */
	private long filetime = 0;
	/**添加子弹*/
	int cyc;
	@Override
	protected void add(long gameTime) {
		if(gameTime/300!=0 && gameTime%300==0) {
			if(this.getType()==2 && cyc >= 0) {
				ElementObj obj = GameLoad.getObj("fire");
				ElementObj element = obj.createElement(this.toString());
				ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
				--cyc;
				if (cyc==0) {
					cyc=10;
					this.setType(1);
				}
			}
			else if (this.getType()==3 && cyc >= 0) {
				ElementObj obj = GameLoad.getObj("fire");
				for (int i=0;i<5;++i) {
					ElementObj element = obj.createElement(this.toString());
					ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
				}
				--cyc;
				if(cyc==0) {
					cyc=10;
					this.setType(1);
				}
			}
			else {
				ElementObj obj = GameLoad.getObj("fire");
				ElementObj element = obj.createElement(this.toString());
				ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
			}
		}
	}
	
	public String toString() {//这里是偷懒，直接使用toString，建议自己定义一个方法
		int x = this.getX()+this.getW()/2-8;
		int y = this.getY()-15;
		String fire = "img/fire/p-"+this.getType()+".png";
		return "x:"+x+",y:"+y + ",fire:" +fire;
	}
	
	public void die() {
		Boom boom = new Boom(this.getX(), this.getY(), this.getW(), this.getH());
	}
	@Override
	public void setType(int i) {
		super.setType(i);
		if (i==1) {
			attack=1;
		}
		else if(i==2) {
			attack=2;
			cyc=10;
		}
		else if (i==3) {
			attack=1;
			cyc=10;
		}
	}

}
