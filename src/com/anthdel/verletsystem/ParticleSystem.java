package com.anthdel.verletsystem;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class ParticleSystem {
	private static final int PARTICLE_COUNT = 25000;
	private Particle[] particles = new Particle[PARTICLE_COUNT];
	private static Random random = new Random();
	private static Color colorA = new Color(1, 1, 1, 1);
	private static Color colorB = new Color(1, 1, 1, 1);
	private float percentage;

	public ParticleSystem() {
		for (int i = 0; i < particles.length; i++)
			particles[i] = new Particle(new Vector2f(
					random.nextInt(Main.SCREEN_WIDTH),
					random.nextInt(Main.SCREEN_HEIGHT)), 0, 0, 1);
	}

	public void update(float delta) {
		// Update color
		percentage += delta;
		if (percentage > 1f) {
			colorB.setColor(random.nextFloat(), random.nextFloat(),
					random.nextFloat(), random.nextFloat() * (1 - 0.6f) + 0.6f);
			percentage = 0;
		}

		colorA.lerp(colorB, percentage);

		int pLength = particles.length;
		for (int i = 0; i < pLength; i++) {
			if (Mouse.isButtonDown(0))
				particles[i].applyForce(Mouse.getX(), Mouse.getY(), 1, true);
			else if (Mouse.isButtonDown(1))
				particles[i].applyForce(Mouse.getX(), Mouse.getY(), 1, false);
			else
				particles[i].magnitude *= 0.985f;

			particles[i].update(delta);
		}
	}

	public void render() {
		int pLength = particles.length;
		for (int i = 0; i < pLength; i++) {
			glColor4f(colorA.r, colorA.g, colorA.b, colorA.a);
			particles[i].render();
		}
	}
}
