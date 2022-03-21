package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class BossFire extends ElementObj {

	private int attack = 1;//攻击力
	private int moveNum = 2;//移动速度
	private String fireindex;//子弹类型
	
	public BossFire() {
		
	}
	
	/**对创建这个对象的过程进行封装，外界只需要传输必要的约定参数，返回值就是对象实体*/
	@Override
	public ElementObj createElement(String str) {//定义字符串的规则
		String[] split = str.split(",");
		for(String str1 : split) {
			String[] split2 = str1.split(":");
			switch (split2[0]) {
			case "x": this.setX(Integer.parseInt(split2[1]));break;
			case "y": this.setY(Integer.parseInt(split2[1]));break;
			case "fire":this.fireindex = split2[1];break;
			}
		}
		this.setW(90);
		this.setH(90);
		this.setIcon(new ImageIcon(fireindex));
		return this;
	}
	
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
		} else {
			this.setLive(false);
		}
	}
	
	@Override
	protected void move() {
		if(this.getY() > 700) {
			this.setLive(false);
			return;
		}
		if(this.getX() < 0 || this.getX() > 450 || 
				this.getY() < 0 || this.getY() > 700) {//死亡判定
			this.setLive(false);
			return;
		}
		this.setY(this.getY() + this.moveNum);
	}
}
