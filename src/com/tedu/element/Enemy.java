package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * 敌人类
 * @author 10129
 *
 */
public class Enemy extends ElementObj{
	
	
	private int value=100;//得分
	boolean fx;//true向左移，false向右移
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	@Override
	protected void move() {
		if(this.getY() > 700) {
			this.setLive(false);
			return;
		}
		if (this.getX()<0) {
			fx=false;
			this.setX(this.getX()+1);
		}
		else if(this.getX()>380) {
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
		this.setY(this.getY()+1);
	}
	@Override
	public void isCollosion(boolean status) {
		if(!status) {
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
		Random ra = new Random();
		Boom boom = new Boom(this.getX(), this.getY(), this.getW(), this.getH());
		if(ra.nextInt(3) < 1) {//道具出现的概率
			GameLoad.loadProp(getX(), getY());
		}
		Map.point+=value;
	}
	/**添加子弹*/
	@Override
	protected void add(long gameTime) {
		if(gameTime/800!=0 && gameTime%800==0) {
			ElementObj obj = GameLoad.getObj("enemyfire");
			ElementObj element = obj.createElement(this.toString());
			ElementManager.getManager().addElement(element, GameElement.EnemyFire);
		}
	}
	
	@Override
	public String toString() {
		int x = this.getX()+this.getW()/2-2;
		int y = this.getY()+this.getH()-2;
		String fire = "img/fire/e-1.png";
		return "x:"+x+",y:"+y + ",fire:" +fire;
	}
	@Override
	public ElementObj createElement(String str) {
		Random ran = new Random();
		int x = ran.nextInt(380);//小兵x坐标随机出现
		int y = 0;
		this.setX(x);
		this.setY(y);
		this.setW(50);
		this.setH(50);
		this.setBlood(2);
		this.setIcon(new ImageIcon(str));
		this.fx=ran.nextBoolean();
		return this;
	}
}
