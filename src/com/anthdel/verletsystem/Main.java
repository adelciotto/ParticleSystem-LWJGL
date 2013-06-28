package com.anthdel.verletsystem;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Main {
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	private static final boolean FULL_SCREEN = false;

	private long lastFrame;
	private int fps;
	private long lastFps;
	private boolean vSync = false;

	private ParticleSystem pSystem = new ParticleSystem();

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL();
		getDelta();
		lastFps = getTime();

		while (!Display.isCloseRequested()) {
			float delta = getDelta() / 1000;

			update(delta);
			renderGL();
			Display.update();
			// Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}

	private void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

	}

	private void update(float delta) {
		pSystem.update(delta);
		updateFps();
	}

	private void renderGL() {
		glClear(GL_COLOR_BUFFER_BIT);
		pSystem.render();
	}

	private long getTime() {
		return System.nanoTime() / 1000000;
	}

	public float getDelta() {
		long time = getTime();
		float delta = (float) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public void updateFps() {
		if (getTime() - lastFps > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFps += 1000;
		}
		fps++;
	}

	/*
	 * Main Entry Point
	 */
	public static void main(String[] args) {
		new Main().start();
	}
}
