package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * 背景地图
 * @author 10129
 *
 */
public class Map extends ElementObj {
	private static ElementManager em = ElementManager.getManager();
	int id;
	public static int point =0;//得分
	public Map() {}
	public Map(int x, int y, int w, int h,ImageIcon icon) {
		super(x, y, w, h, icon);
	}
	
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		/*绘制背景图片*/
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY()-700, 
				this.getW(), this.getH(), null);
		/*绘制血条*/
		praintBlood(g);
		/*绘制得分*/
		praintPoint(g);
	}
	
	/**
	 * 绘制血条
	 */
	public void praintBlood(Graphics g) {
		List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
		ElementObj only_play = play.get(this.id-1);
		int blood = only_play.getBlood();
		if(blood>20) {
			g.setColor(Color.GREEN);
			g.fillRect(280, 10, (int) (blood*1.5), 19);
			g.setColor(Color.WHITE);
			g.setFont( new Font("微软雅黑", Font.BOLD, 12));
			g.drawString("血量", 250, 30);
			g.drawRect(280, 10, 150, 20);
			g.setFont( new Font("微软雅黑", Font.BOLD, 15));
			g.drawString(""+blood, 352, 25);
		}
		else {
			g.setColor(Color.RED);
			g.fillRect(281, 15, (int) (blood*1.5), 19);
			g.setColor(Color.WHITE);
			g.setFont( new Font("微软雅黑", Font.BOLD, 12));
			g.drawString("血量", 250, 30);
			g.drawRect(280, 15, 150, 20);
			g.setFont( new Font("微软雅黑", Font.BOLD, 15));
			g.drawString(""+blood, 352, 30);
		}
	}
	
	/**
	 * 绘制得分
	 */
	public void praintPoint(Graphics g) {
		//point得分绘制
		g.setFont( new Font("微软雅黑", Font.BOLD, 12));
		g.drawString("得分：", 10, 30);
		g.setColor(Color.white);
		g.setFont( new Font("微软雅黑", Font.BOLD, 22));
		g.drawString(""+this.point, 45, 35);
	}
	
	/**
	 * 传入相应的关卡可以显示不同的图片
	 */
	@Override
	public ElementObj createElement(String str) {
		int id = Integer.parseInt(str.substring(15, 16));
		this.id=id;
		this.setIcon(new ImageIcon(str));
		this.setX(0);
		this.setY(0);
		this.setW(450);
		this.setH(700);
		return this;
	}
	
	/**
	 * 背景图片滚动
	 */
	@Override
	protected void move() {
		if (this.getY() < 700
				) {
			this.setY(this.getY() + 1);
		}else {
			this.setY(0);
		}
	}
	
	
	
	/*设置获取游戏得分*/
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	
}
