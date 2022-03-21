package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;


/**
 * @说明 玩家子弹类，本类的实体对象是由玩家对象调用和创建
 * @author 10129
 * @子类开发步骤
 * 		1.继承与元素基类；重写show方法
 * 		2.按照需求选择性重写其他方法如：move等
 */
public class PlayFire extends ElementObj{
	
	private int moveNum = 3;//移动速度
	private String fireindex;
	public PlayFire() {}
	
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
		this.setW(20);
		this.setH(20);
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
		if(this.getY() < 0) {
			this.setLive(false);
			return;
		}
		if(this.getX() < 0 || this.getX() > 450 || 
				this.getY() < 0 || this.getY() > 700) {//死亡判定
			this.setLive(false);
			return;
		}
		this.setY(this.getY() - this.moveNum);
		
	}

}
