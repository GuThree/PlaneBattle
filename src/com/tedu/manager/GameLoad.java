package com.tedu.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;

/**
 * @说明 假的加载器（工具：用户读取配置文件的工具）工具类，大多数提供的是static方法
 * @author 10129
 *
 */
public class GameLoad {
	private static ElementManager em = ElementManager.getManager();

	/* 图片集合 使用map来进行存储 枚举类型配合移动（扩展） */
	public static Map<String, ImageIcon> imgMap = new HashMap<>();
	public static Map<String, List<ImageIcon>> imgMaps;

	/**
	 * 用户读取文件的类
	 */
	private static Properties pro = new Properties();

	/**
	 * @说明 传入地图id有加载方法依据文件规则自动产生文件名称，加载文件
	 * @param mapId 文件编号 文件id
	 */
	public static void MapLoad(int mapId) {
		loadObj();
		String mapStr = "img/background/" + mapId + ".png";
		ElementObj obj = getObj("map");
		ElementObj map = obj.createElement(mapStr);
		em.addElement(map, GameElement.MAPS);
	}
	/**
	 * 加载玩家
	 */
	public static void loadPlay() {
		loadObj();
		String playStr = "img/play/1.png";
		ElementObj obj = getObj("play");
		ElementObj play = obj.createElement(playStr);
		em.addElement(play, GameElement.PLAY);
	}
	
	/*加载小兵*/
	public static void loadEnemy(int level) {
		loadObj();
		Random ran = new Random();
		int enemyId = ran.nextInt(3)+1;
		String enemyStr = "img/enemy/" + level + "-" + enemyId + ".png";
		ElementObj obj = getObj("enemy");
		ElementObj enemy = obj.createElement(enemyStr);
		em.addElement(enemy, GameElement.ENEMY);
	}

	/*加载Boss*/
	public static void loadBoss(int level) {
		loadObj();
		String bossStr = "img/boss/" + level + ".png";
		ElementObj obj = getObj("boss");
		ElementObj boss = obj.createElement(bossStr);
		em.addElement(boss, GameElement.BOSS);
	}
	/*加载道具*/
	public static void loadProp(int x, int y) {
		loadObj();
		Random random = new Random();
		int itemId = random.nextInt(3)+1;
		String item = "img/props/"+itemId+".png";
		String propStr= "x:"+x+",y:"+y + ",item:" +item;
		ElementObj obj = getObj("prop");
		ElementObj prop = obj.createElement(propStr);
		em.addElement(prop, GameElement.PROP);
	}
	
	public static ElementObj getObj(String str) {
		try {
			Class<?> class1 = objMap.get(str);
			Object newInstance = class1.newInstance();
			if (newInstance instanceof ElementObj) {
				return (ElementObj) newInstance;// 这个对象就和 new Play（）等价
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Map<String, Class<?>> objMap = new HashMap<>();

	/**
	 * 扩展：使用配置文件，来实例化对象 通过固定的key（字符串实例来实例化）
	 * @param arg
	 */
	public static void loadObj() {
		String texturl = "com/tedu/text/obj.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();// 得到类加载器
		InputStream texts = classLoader.getResourceAsStream(texturl);

		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();// 是一个set集合
			for (Object o : set) {
				String classUrl = pro.getProperty(o.toString());
				// 使用反射的方式直接将类进行获取
				Class<?> forName = Class.forName(classUrl);
				objMap.put(o.toString(), forName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
