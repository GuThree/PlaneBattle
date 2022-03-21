package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Boss extends ElementObj{
	public int value=3000;
	private int bossfireclass=2;
	boolean fx=true;//true向左移，false向右移
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	@Override
	public void isCollosion(boolean status) {
		if (!status) {
			return;
		}
		else {
			this.setBlood(this.getBlood()-Play.attack);
			if (this.getBlood()<=0) {
				this.setLive(false);
				die();
			}
		}
	}
	@Override
	public void die() {
		Boom boom = new Boom(this.getX(), this.getY(), this.getW(), this.getH());
		Map.point+=value;
	}
	@Override
	protected void move() {
		if (this.getX()<10) {
			fx=false;
			this.setX(this.getX()+1);
		}
		else if(this.getX()>290) {
			fx=true;
			this.setX(this.getX()-1);
		}
		else {
			if (fx) {
				this.setX(this.getX()-1);
			}else {
				this.setX(this.getX()+1);
			}
		}
	}
	@Override
	protected void add(long gameTime) {
		if(gameTime/1200!=0 && gameTime%1200==0) {
			ElementObj obj = GameLoad.getObj("bossfire");
			ElementObj element = obj.createElement(this.toString());
			ElementManager.getManager().addElement(element, GameElement.BossFire);
		}
	}
	
	@Override
	public String toString() {//这里是偷懒，直接使用toString，建议自己定义一个方法
		int x = this.getX()+30;
		int y = this.getY()+this.getH()-50;
		String fire = "img/fire/b-"+bossfireclass+".png";
		return "x:"+x+",y:"+y + ",fire:" +fire;
	}
	@Override
	public ElementObj createElement(String str) {
		Random ran = new Random();
		int x = ran.nextInt(250)+10;//Bossx坐标随机出现
		int y = 0;
		this.setX(x);
		this.setY(y);
		this.setW(150);
		this.setH(150);
		this.setBlood(40);
		this.setIcon(new ImageIcon(str));
		return this;
	}
}
