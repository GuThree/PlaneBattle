package com.tedu.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameMainJPanel;

/**
 * @说明 游戏主线程，用于控制加载，游戏关卡，游戏运行时自动化，游戏判定，游戏地图释放，资源释放和重新读取。。。
 * @author 10129
 * @继承 使用继承的方法实现多线程（一般建议使用接口实现）
 */
public class GameThread extends Thread {
	private ElementManager em;
	public static boolean ispause = true;//暂停控制
	private int level = 1;// 指哪个关卡

	/* 构造 */
	public GameThread() {
		em = ElementManager.getManager();
	}
	
	@Override
	public void run() {// 游戏的run方法 主线程

		// 游戏开始资源载入
		gameLoad(level);
		// 游戏进行时
		gameRun(level);
		// 游戏结束后
		gameOver();
	}

	/**
	 * @说明 游戏的加载
	 */
	private void gameLoad(int level) {
		/* 加载主角 */
		GameLoad.loadPlay();// 也可以带参数 单击或者两人
		/* 加载地图 */
		GameLoad.MapLoad(level);// 可以变为变量，每一关重新加载
	}

	/**
	 * @说明 游戏进行时
	 * @任务说明 游戏过程中需要做的事情：1.自动化玩家的移动、碰撞、死亡 2.新元素的增加（npc死亡后出现道具） 3.暂停等等。。。
	 */
	private void gameRun(int level) {
		long gameTime = 0L;
		long it = 0L;// 上一个Boss出现的时间点
		boolean isbossCome = false;// Boss是否来了

		while (true) {// 预留扩展，true可用变为变量，用于控制关卡结束等
			if (ispause) {
				if (!isbossCome && gameTime / 1000 != 0 && gameTime % 1000 == 0) {// 每一秒生成一个敌人
					/* 加载敌人 */
					GameLoad.loadEnemy(level);
				}
				if (!isbossCome && gameTime / 30000 != 0 && (gameTime == 30000 || gameTime - it > 30000)) {// 每关的第30秒出现Boss
					/* 加载Boss */
					isbossCome = true;
					GameLoad.loadBoss(level);

				}
				Map<GameElement, List<ElementObj>> all = em.getGameElements();
				List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
				List<ElementObj> fires = em.getElementsByKey(GameElement.PLAYFIRE);
				List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
				List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
				List<ElementObj> boss = em.getElementsByKey(GameElement.BOSS);
				List<ElementObj> enemyfires = em.getElementsByKey(GameElement.EnemyFire);
				List<ElementObj> bossfires = em.getElementsByKey(GameElement.BossFire);
				List<ElementObj> props = em.getElementsByKey(GameElement.PROP);
				moveAndUpdata(all, gameTime);// 游戏元素自动化方法

				//碰撞检测
				ElementPk(play, enemyfires);
				ElementPk(enemys, fires);
				ElementPk(boss, fires);
				ElementPk(play, bossfires);
				PlayAndPropPK(play, props);
				ElementCash(play, boss);
				ElementCash(enemys, play);
				
				// 如果玩家或Boss一方死了，就停止
				if (!play.get(0).isLive()) {
					break;
				}
				if (gameTime >= 10000 && isbossCome) {
					if (!boss.get(0).isLive()) {
						isbossCome = false;
						it = gameTime;
						level += 1;
						play.get(0).setBlood(100);
						maps.get(0).setIcon(new ImageIcon("img/background/" + level + ".png"));
						try {
							sleep(500);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (level == 4)
							break;
					}
				}

				gameTime += 10;// 唯一的时间控制

			}
			try {
				sleep(10);// 1秒刷新100次
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	/**
	 * 游戏元素自动化方法
	 */
	public void moveAndUpdata(Map<GameElement, List<ElementObj>> all, long gameTime) {
		for (GameElement ge : GameElement.values()) {// GameElement.values();隐藏方法，返回值是一个数组，数组的顺序就是定义的枚举类型的顺序
			List<ElementObj> list = all.get(ge);
			for (int i = list.size() - 1; i >= 0; i--) {// 编写这样直接操作集合数据的代码建议不用迭代器
				ElementObj obj = list.get(i);// 读取为基类
				/* 如果死亡 */
				if (!obj.isLive()) {
					/* 启动一个死亡方法（方法中可以做事情例如：死亡动画，掉装备） */
					// obj.die();// 待扩展
					list.remove(i);
					continue;
				}
				/* 调用的模板方法 */
				obj.model(gameTime);
			}
		}
	}

	// 玩家与敌人的碰撞
	public void ElementCash(List<ElementObj> listA, List<ElementObj> listB) {
		for (int i = 0; i < listA.size(); i++) {
			ElementObj smallone = listA.get(i);
			for (int j = 0; j < listB.size(); j++) {
				ElementObj bigone = listB.get(j);
				if (bigone.pk(smallone)) {
					smallone.setLive(false);
					smallone.die();
					smallone.setBlood(0);
					bigone.setBlood(bigone.getBlood() - 10);
					if (bigone.getBlood() <= 0) {
						bigone.setLive(false);
						bigone.die();
					}

					break;
				}
			}
		}
	}

	// 飞机与子弹间的碰撞
	public void ElementPk(List<ElementObj> listA, List<ElementObj> listB) {
		/* 使用循环，做一对一判定，如果为真，就设置2个对象的死亡状态 */
		for (int i = 0; i < listA.size(); i++) {
			ElementObj A = listA.get(i);
			for (int j = 0; j < listB.size(); j++) {
				ElementObj B = listB.get(j);
				if (A.pk(B)) {

					A.isCollosion(true);
					B.isCollosion(true);

					break;
				}
			}
		}
	}

	//玩家和道具之间的碰撞
	public void PlayAndPropPK(List<ElementObj> play, List<ElementObj> prop) {
		for (int i = 0; i < play.size(); i++) {
			ElementObj A = play.get(i);
			for (int j = 0; j < prop.size(); j++) {
				ElementObj B = prop.get(j);
				if (A.pk(B)) {
					B.setLive(false);
					//
					int itemclass = B.getType();
					switch (itemclass) {
						case 1:
							A.setBlood(A.getBlood() + 10 > 100 ? 100 : A.getBlood() + 10);
							break;
						case 2:
							A.setType(2);
							break;
						case 3:
							A.setType(3);
							break;
					}
				}
			}
		}
	}

	/**
	 * 游戏结束，回收资源
	 */
	private void gameOver() {
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
		for (GameElement ge : GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for (int i = list.size() - 1; i >= 0; i--) {
				list.remove(i);
			}
		}
		//跳转游戏结束页面
		GameMainJPanel.count = 3;
	}
}
