package com.anthdel.verletsystem;

import org.lwjgl.util.vector.Vector2f;
import static org.lwjgl.opengl.GL11.*;

public class Particle {
	public Vector2f position;
	public float px, py;
	public float magnitude;
	public float angle;
	public float mass;

	private static final float HALF_PI = 1.57079632679489661923f;

	public Particle(Vector2f position, float magnitude, float angle, float mass) {
		this.position = position;
		px = position.x;
		py = position.y;
		this.magnitude = magnitude;
		this.angle = angle;
		this.mass = mass;
	}

	public void applyForce(float x, float y, float mass, boolean isAttractor) {
		float f, mX, mY, angle;

		if ((position.x - x) * (position.x - x) + (position.y - y)
				* (position.y - y) != 0) {
			f = this.mass * mass * 1.15f;
			mX = (this.mass * position.x + mass * x) / (this.mass + mass);
			mY = (this.mass * position.y + mass * y) / (this.mass + mass);
			angle = isAttractor ? findAngle(mX - position.x, mY - position.y)
					: findAngle(position.x - mX, position.y - mY);

			mX = f * FastTrig.cos(angle);
			mY = f * FastTrig.sin(angle);

			mX += magnitude * FastTrig.cos(this.angle);
			mY += magnitude * FastTrig.sin(this.angle);

			magnitude = (float) Math.sqrt(mX * mX + mY * mY);
			this.angle = findAngle(mX, mY);
		}
	}

	public void update(float delta) {
		if (position.x < 0 || position.x > Main.SCREEN_WIDTH) {
			magnitude *= -1;
		} else if (position.y < 0 || position.y > Main.SCREEN_HEIGHT) {
			magnitude *= -1;
		}
		position.x += magnitude * FastTrig.cos(angle) * delta * 20;
		position.y += magnitude * FastTrig.sin(angle) * delta * 20;
	}

	public void render() {
		glBegin(GL_LINES);
			glVertex2f(px, py);
			glVertex2f(position.x, position.y);
		glEnd();
		px = position.x;
		py = position.y;
	}

	private static float findAngle(float x, float y) {
		float theta = 0;
		if (x == 0)
			theta = y > 0 ? HALF_PI : 3 * HALF_PI;
		else {
			theta = (float) Math.atan(y / x);
			if ((x < 0) && (y >= 0)) {
				theta += Math.PI;
			}
			if ((x < 0) && (y < 0)) {
				theta -= Math.PI;
			}
		}
		return theta;
	}

}
