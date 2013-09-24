package org.jboss.tools.cdi.bot.test.uiutils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

public class ClickHelper {

	private ClickHelper() {
	}
	
	/**
	 * It clicks at coordinations (60, 5) relatively to upper left corner of {@code shell}.
	 * <p> It utilizes {@link Robot}
	 */
	public static void clickOnTitleBar(Shell shell) {
		shellClick(60, 5, shell);
	}
	
	public static void shellClick(int offsetX, int offsetY, final Shell shell) {
		Point position = Display.syncExec(new ResultRunnable<Point>() {

			public Point run() {
				return shell.getLocation();
			}
		});
		Robot robot = createRobot();
		robot.mouseMove(position.x + offsetX, position.y + offsetY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	/**
	 * Creates new instance of {@link Robot} with 100ms autodelay set.
	 */
	private static Robot createRobot() {
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(100);
			return robot;
		} catch (AWTException ex) {
			throw new RuntimeException("Creation of java.awt.Robot failed", ex);
		}
	}

}
