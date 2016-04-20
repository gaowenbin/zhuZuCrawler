package com.zhizu.crawler.util;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class RobotExec {

	public static Map<String, int[]> keyMaps = new HashMap<String, int[]>();

	public RobotExec() {
		if (keyMaps == null || keyMaps.size() == 0) {
			initKeyMaps();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new RobotExec();
			Robot robot = new Robot();
			Runtime.getRuntime().exec("cmd /c start notepad");
			robot.delay(1000);// 等待记事本打开
			pressKeyWithCrl(robot, KeyEvent.VK_SPACE);// 启动输入法
			// for (int i = 0; i < 100; i++) {
			// pressKey(robot, (int) (Math.random() * 25 + 'A'));
			// pressKey(robot, KeyEvent.VK_SPACE);
			// }
			pressKey(robot, "---___+_)");
			// pressKeyWithShift(robot, KeyEvent.VK_SUBTRACT);
			// robot.keyPress(KeyEvent.SHIFT_DOWN_MASK | KeyEvent.VK_SUBTRACT);
			// robot.keyRelease(KeyEvent.VK_SUBTRACT |
			// KeyEvent.SHIFT_DOWN_MASK);
			robot.delay(4000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * 按下并释放按钮
	 * 
	 * @author liuheli
	 * @param robot
	 * @param keyvalue
	 */
	public static void pressKey(Robot robot, int keyvalue) {
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);
	}

	/**
	 * 依次按下并释放value对应的按钮
	 * 
	 * @author liuheli
	 * @param robot
	 * @param value
	 * @throws InterruptedException
	 */
	public static void pressKey(Robot robot, String value)
			throws InterruptedException {
		pressKey(robot, value, 0);
	}

	/**
	 * 依次按下并释放value对应的按钮
	 * 
	 * @author liuheli
	 * @param robot
	 * @param value
	 * @throws InterruptedException
	 */
	public static void pressKey(Robot robot, String value, int sleep)
			throws InterruptedException {
		if (value != null && value.length() > 0) {
			char[] chars = value.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char temp = chars[i];
				int[] tempInt = keyMaps.get("" + temp);
				if (tempInt != null && tempInt.length > 1) {
					Thread.sleep(sleep);
					if (tempInt[1] > 0) {
						pressKeyWithShift(robot, tempInt[0]);
					} else {
						pressKey(robot, tempInt[0]);
					}
				}
			}
		}
	}

	/**
	 * 按下并释放鼠标
	 * 
	 * @author liuheli
	 * @param robot
	 */
	public static void pressMouse(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_MASK);// 按下鼠标
		robot.mouseRelease(InputEvent.BUTTON1_MASK);// 释放鼠标
	}

	/**
	 * CONTROL键+其他键
	 * 
	 * @author liuheli
	 * @param robot
	 * @param keyvalue
	 */
	public static void pressKeyWithCrl(Robot robot, int keyvalue) {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	/**
	 * SHIFT键+其他键
	 * 
	 * @author liuheli
	 * @param robot
	 * @param keyvalue
	 */
	public static void pressKeyWithShift(Robot robot, int keyvalue) {
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);
		robot.keyRelease(KeyEvent.VK_SHIFT);
	}

	/**
	 * 关闭当前应用
	 * 
	 * @author liuheli
	 * @param robot
	 */
	public static void closeApplication(Robot robot) {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_N);
		robot.keyRelease(KeyEvent.VK_N);
	}

	public static void initKeyMaps() {

		keyMaps.put("a", new int[] { KeyEvent.VK_A, 0 });
		keyMaps.put("A", new int[] { KeyEvent.VK_A, 1 });
		keyMaps.put("b", new int[] { KeyEvent.VK_B, 0 });
		keyMaps.put("B", new int[] { KeyEvent.VK_B, 1 });
		keyMaps.put("c", new int[] { KeyEvent.VK_C, 0 });
		keyMaps.put("C", new int[] { KeyEvent.VK_C, 1 });
		keyMaps.put("d", new int[] { KeyEvent.VK_D, 0 });
		keyMaps.put("D", new int[] { KeyEvent.VK_D, 1 });
		keyMaps.put("e", new int[] { KeyEvent.VK_E, 0 });
		keyMaps.put("E", new int[] { KeyEvent.VK_E, 1 });
		keyMaps.put("f", new int[] { KeyEvent.VK_F, 0 });
		keyMaps.put("F", new int[] { KeyEvent.VK_F, 1 });
		keyMaps.put("g", new int[] { KeyEvent.VK_G, 0 });
		keyMaps.put("G", new int[] { KeyEvent.VK_G, 1 });
		keyMaps.put("h", new int[] { KeyEvent.VK_H, 0 });
		keyMaps.put("H", new int[] { KeyEvent.VK_H, 1 });
		keyMaps.put("i", new int[] { KeyEvent.VK_I, 0 });
		keyMaps.put("I", new int[] { KeyEvent.VK_I, 1 });
		keyMaps.put("j", new int[] { KeyEvent.VK_J, 0 });
		keyMaps.put("J", new int[] { KeyEvent.VK_J, 1 });
		keyMaps.put("k", new int[] { KeyEvent.VK_K, 0 });
		keyMaps.put("K", new int[] { KeyEvent.VK_K, 1 });
		keyMaps.put("l", new int[] { KeyEvent.VK_L, 0 });
		keyMaps.put("L", new int[] { KeyEvent.VK_L, 1 });
		keyMaps.put("m", new int[] { KeyEvent.VK_M, 0 });
		keyMaps.put("M", new int[] { KeyEvent.VK_M, 1 });
		keyMaps.put("n", new int[] { KeyEvent.VK_N, 0 });
		keyMaps.put("N", new int[] { KeyEvent.VK_N, 1 });
		keyMaps.put("o", new int[] { KeyEvent.VK_O, 0 });
		keyMaps.put("O", new int[] { KeyEvent.VK_O, 1 });
		keyMaps.put("p", new int[] { KeyEvent.VK_P, 0 });
		keyMaps.put("P", new int[] { KeyEvent.VK_P, 1 });
		keyMaps.put("q", new int[] { KeyEvent.VK_Q, 0 });
		keyMaps.put("Q", new int[] { KeyEvent.VK_Q, 1 });
		keyMaps.put("r", new int[] { KeyEvent.VK_R, 0 });
		keyMaps.put("R", new int[] { KeyEvent.VK_R, 1 });
		keyMaps.put("s", new int[] { KeyEvent.VK_S, 0 });
		keyMaps.put("S", new int[] { KeyEvent.VK_S, 1 });
		keyMaps.put("t", new int[] { KeyEvent.VK_T, 0 });
		keyMaps.put("T", new int[] { KeyEvent.VK_T, 1 });
		keyMaps.put("u", new int[] { KeyEvent.VK_U, 0 });
		keyMaps.put("U", new int[] { KeyEvent.VK_U, 1 });
		keyMaps.put("v", new int[] { KeyEvent.VK_V, 0 });
		keyMaps.put("V", new int[] { KeyEvent.VK_V, 1 });
		keyMaps.put("w", new int[] { KeyEvent.VK_W, 0 });
		keyMaps.put("W", new int[] { KeyEvent.VK_W, 1 });
		keyMaps.put("x", new int[] { KeyEvent.VK_X, 0 });
		keyMaps.put("X", new int[] { KeyEvent.VK_X, 1 });
		keyMaps.put("y", new int[] { KeyEvent.VK_Y, 0 });
		keyMaps.put("Y", new int[] { KeyEvent.VK_Y, 1 });
		keyMaps.put("z", new int[] { KeyEvent.VK_Z, 0 });
		keyMaps.put("Z", new int[] { KeyEvent.VK_Z, 1 });

		keyMaps.put("1", new int[] { KeyEvent.VK_1, 0 });
		keyMaps.put("!", new int[] { KeyEvent.VK_1, 1 });
		keyMaps.put("2", new int[] { KeyEvent.VK_2, 0 });
		keyMaps.put("@", new int[] { KeyEvent.VK_2, 1 });
		keyMaps.put("3", new int[] { KeyEvent.VK_3, 0 });
		keyMaps.put("#", new int[] { KeyEvent.VK_3, 1 });
		keyMaps.put("4", new int[] { KeyEvent.VK_4, 0 });
		keyMaps.put("$", new int[] { KeyEvent.VK_4, 1 });
		keyMaps.put("5", new int[] { KeyEvent.VK_5, 0 });
		keyMaps.put("%", new int[] { KeyEvent.VK_5, 1 });
		keyMaps.put("6", new int[] { KeyEvent.VK_6, 0 });
		keyMaps.put("^", new int[] { KeyEvent.VK_6, 1 });
		keyMaps.put("7", new int[] { KeyEvent.VK_7, 0 });
		keyMaps.put("&", new int[] { KeyEvent.VK_7, 1 });
		keyMaps.put("8", new int[] { KeyEvent.VK_8, 0 });
		keyMaps.put("*", new int[] { KeyEvent.VK_8, 1 });
		keyMaps.put("9", new int[] { KeyEvent.VK_9, 0 });
		keyMaps.put("(", new int[] { KeyEvent.VK_9, 1 });
		keyMaps.put("0", new int[] { KeyEvent.VK_0, 0 });
		keyMaps.put(")", new int[] { KeyEvent.VK_0, 1 });
		keyMaps.put("-", new int[] { KeyEvent.VK_MINUS, 0 });
		keyMaps.put("_", new int[] { KeyEvent.VK_MINUS, 1 });
		keyMaps.put("=", new int[] { KeyEvent.VK_EQUALS, 0 });
		keyMaps.put("+", new int[] { KeyEvent.VK_EQUALS, 1 });

		keyMaps.put(" ", new int[] { KeyEvent.VK_SPACE, 0 });
		keyMaps.put("[", new int[] { KeyEvent.VK_OPEN_BRACKET, 0 });
		keyMaps.put("{", new int[] { KeyEvent.VK_OPEN_BRACKET, 1 });
		keyMaps.put("]", new int[] { KeyEvent.VK_CLOSE_BRACKET, 0 });
		keyMaps.put("}", new int[] { KeyEvent.VK_CLOSE_BRACKET, 1 });
		keyMaps.put("\\", new int[] { KeyEvent.VK_BACK_SLASH, 0 });
		keyMaps.put("|", new int[] { KeyEvent.VK_BACK_SLASH, 1 });
		keyMaps.put(",", new int[] { KeyEvent.VK_COMMA, 0 });
		keyMaps.put("<", new int[] { KeyEvent.VK_COMMA, 1 });
		keyMaps.put(".", new int[] { KeyEvent.VK_PERIOD, 0 });
		keyMaps.put(">", new int[] { KeyEvent.VK_PERIOD, 1 });
		keyMaps.put("/", new int[] { KeyEvent.VK_SLASH, 0 });
		keyMaps.put("?", new int[] { KeyEvent.VK_SLASH, 1 });
		keyMaps.put(";", new int[] { KeyEvent.VK_SEMICOLON, 0 });
		keyMaps.put(":", new int[] { KeyEvent.VK_SEMICOLON, 1 });
		keyMaps.put("'", new int[] { KeyEvent.VK_QUOTE, 0 });
		keyMaps.put("\"", new int[] { KeyEvent.VK_QUOTE, 1 });
	}
}
