package net.novatech.novaLibGDX.drawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.NumberUtils;

import net.novatech.library.math.*;
import net.novatech.library.math.vector.*;

import net.novatech.novaLibGDX.GDXSystem;
import static net.novatech.novaLibGDX.GDXSystem.batch;
import net.novatech.novaLibGDX.graphics.*;
import net.novatech.novaLibGDX.utils.*;

public class DrawingManager {
	
	private static TextureRegion blankregion = Pixmaps.blankTextureRegion();

	private static float thickness = 1f;
	private static Vector2 vector = GDXVectorUtils.createVector(0, 0);
	private static Vector2[] circle;
	private static Vector2[] dashCircle;
	private static Sprite sprite;
	private static Color tmpcolor = new Color();

	static {
		int defCircle = 30, defDashCircle = 36;

		setCircleVertices(defCircle);
		setDashedCircleVertices(defDashCircle);
	}

	private static void setCircleVertices(Vector2[] vertices, int amount) {
		float step = 360f / amount;
		vector.setX(1f);
		vector.setY(0);
		GDXVectorUtils.update(vector);
		for (int i = 0; i < amount; i++) {
			GDXVectorUtils.getGDXVector2(vector).setAngle(i * step);
			GDXVectorUtils.getGDXVector2(vector).cpy();
			vertices[i] = vector;
		}
	}

	public static void setCircleVertices(int amount) {
		circle = new Vector2[amount];
		setCircleVertices(circle, amount);
	}

	public static void setDashedCircleVertices(int amount) {
		dashCircle = new Vector2[amount];
		setCircleVertices(dashCircle, amount);
	}

	public static void sprite(Sprite sprite) {
		sprite.draw(batch);
	}

	public static void patch(String name, float x, float y, float width, float height) {
		getPatch(name).draw(batch, x, y, width, height);
	}

	public static Drawable getPatch(String name) {
		return null; //TO DO
	}

	public static void tint(Color color) {
		color(color.r, color.g, color.b, batch.getColor().a);
	}

	public static void color(Color color) {
		batch.setColor(color);
	}

	public static void colorl(Color color, float multiply) {
		batch.setColor(TempUtils.c1.set(color).mul(multiply, multiply, multiply, 1f));
	}

	public static void color(Color a, Color b, float s) {
		batch.setColor(ColorUtils.mix(a, b, s, tmpcolor));
	}

	public static void color(String name) {
		batch.setColor(Colors.get(name));
	}

	public static void color() {
		color(Color.WHITE);
	}

	public static void color(float r, float g, float b) {
		batch.setColor(r, g, b, 1f);
	}

	public static void color(float r, float g, float b, float a) {
		batch.setColor(r, g, b, a);
	}

	public static void colorl(float l) {
		color(l, l, l);
	}

	public static void colorl(float l, float a) {
		color(l, l, l, a);
	}

	public static void alpha(float alpha) {
		Color color = batch.getColor();
		batch.setColor(color.r, color.g, color.b, alpha);
	}

	public static void gradient(Color left, Color right, float alpha, float x, float y, float w, float h) {
		if (sprite == null) {
			sprite = new Sprite(blankregion);
		}
		
		sprite.setBounds(x, y, w, h);

		tmpcolor.set(left);
		tmpcolor.a *= alpha;
		float cl = tmpcolor.toFloatBits();

		tmpcolor.set(right);
		tmpcolor.a *= alpha;
		float cr = tmpcolor.toFloatBits();

		float[] v = sprite.getVertices();
		v[SpriteBatch.C1] = cl;
		v[SpriteBatch.C2] = cl;

		v[SpriteBatch.C3] = cr;
		v[SpriteBatch.C4] = cr;

		sprite.draw(batch);
	}

	public static void rect(Texture texture, float x, float y) {
		batch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2);
	}

	public static void rect(TextureRegion region, float x, float y) {
		batch.draw(region, x - region.getRegionWidth() / 2, y - region.getRegionHeight() / 2);
	}

	public static void rect(TextureRegion region, float x, float y, float width, float height) {
		batch.draw(region, x - width / 2, y - height / 2, width, height);
	}

	public static void rect(String name, float x, float y) {
		TextureRegion region = region(name);
		batch.draw(region, x - region.getRegionWidth() / 2, y - region.getRegionHeight() / 2);
	}

	public static void sirect(String name, float x, float y, float rotation) {
		TextureRegion region = region(name);
		batch.draw(region, x, y - region.getRegionHeight() / 2f, 0, region.getRegionHeight() / 2f,
				region.getRegionWidth(), region.getRegionHeight(), 1, 1, rotation);
	}

	public static void borect(String name, float x, float y, float rotation) {
		TextureRegion region = region(name);
		batch.draw(region, x - region.getRegionWidth() / 2f, y, region.getRegionWidth() / 2f, 0,
				region.getRegionWidth(), region.getRegionHeight(), 1, 1, rotation);
	}

	public static void grect(String name, float x, float y) {
		TextureRegion region = region(name);
		batch.draw(region, x - region.getRegionWidth() / 2, y);
	}

	public static void grect(String name, float x, float y, boolean flipx) {
		TextureRegion region = region(name);
		if (flipx) {
			batch.draw(region, x + region.getRegionWidth() / 2, y, -region.getRegionWidth(), region.getRegionHeight());
		} else {
			batch.draw(region, x - region.getRegionWidth() / 2, y);
		}
	}

	public static void grect(String name, float x, float y, float w, float h) {
		TextureRegion region = region(name);
		batch.draw(region, x - w / 2, y, w, h);
	}

	public static void rect(String name, float x, float y, float rotation) {
		TextureRegion region = region(name);
		batch.draw(region, x - region.getRegionWidth() / 2, y - region.getRegionHeight() / 2,
				region.getRegionWidth() / 2, region.getRegionHeight() / 2, region.getRegionWidth(),
				region.getRegionHeight(), 1, 1, rotation);
	}

	public static void rect(String name, float x, float y, float w, float h, float rotation) {
		TextureRegion region = region(name);
		batch.draw(region, x - w / 2, y - h / 2, w / 2, h / 2, w, h, 1, 1, rotation);
	}

	public static void rect(String name, float x, float y, float w, float h) {
		TextureRegion region = region(name);
		batch.draw(region, x - w / 2, y - h / 2, w, h);
	}

	public static void crect(Texture texture, float x, float y, float w, float h) {
		batch.draw(texture, x, y, w, h);
	}

	public static void crect(String name, float x, float y, float w, float h) {
		TextureRegion region = region(name);
		batch.draw(region, x, y, w, h);
	}

	public static void crect(String name, float x, float y) {
		TextureRegion region = region(name);
		batch.draw(region, x, y);
	}

	public static void laser(String line, String edge, float x, float y, float x2, float y2, float scale) {
		vector.setX(x2 - x);
		vector.setY(y2 - y);
		GDXVectorUtils.update(vector);
		float angle = GDXVectorUtils.getGDXVector2(vector).angle();
		laser(line, edge, x, y, x2, y2, angle, scale);
	}

	public static void laser(String line, String edge, float x, float y, float x2, float y2) {
		vector.setX(x2 - x);
		vector.setY(y2 = y);
		GDXVectorUtils.update(vector);
		float angle = GDXVectorUtils.getGDXVector2(vector).angle();
		laser(line, edge, x, y, x2, y2, angle, 1f);
	}

	public static void laser(String line, String edge, float x, float y, float x2, float y2, float rotation, float scale) {
		thickness = 12f * scale;
		DrawingManager.unspacedLine(region(line), x, y, x2, y2);
		thickness = 1f;

		TextureRegion region = region(edge);
		DrawingManager.rect(edge, x, y, region.getRegionWidth(), region.getRegionHeight() * scale, rotation + 180);
		DrawingManager.rect(edge, x2, y2, region.getRegionWidth(), region.getRegionHeight() * scale, rotation);
	}

	public static void lineAngle(float x, float y, float angle, float length) {
		GDXVectorUtils.getGDXVector2(vector).setLength(length).setAngle(angle);
		line(x, y, x + (float)vector.getX(), y + (float)vector.getY());
	}

	public static void lineAngle(float x, float y, float offset, float angle, float length) {
		GDXVectorUtils.getGDXVector2(vector).setLength(length + offset).setAngle(angle);
		line(x, y, x + (float)vector.getX(), y + (float)vector.getY());
	}

	public static void lineAngleCenter(float x, float y, float angle, float length) {
		GDXVectorUtils.getGDXVector2(vector).setLength(length).setAngle(angle);
		line(x - (float)vector.getX() / 2, y - (float)vector.getY() / 2, x + (float)vector.getX() / 2, y + (float)vector.getY() / 2);
	}

	public static void line(float x, float y, float x2, float y2) {
		float length = com.badlogic.gdx.math.Vector2.dst(x, y, x2, y2) + thickness / 2;
		float angle = ((float) Math.atan2(y2 - y, x2 - x) * MathUtils.radDeg);
		batch.draw(blankregion, x - thickness / 2, y - thickness / 2, thickness / 2, thickness / 2, length, thickness, 1f, 1f, angle);
	}

	public static void dashLine(float x1, float y1, float x2, float y2, int divisions) {
		float dx = x2 - x1, dy = y2 - y1;
		for (int i = 0; i < divisions; i++) {
			if (i % 2 == 0) {
				line(x1 + ((float) i / divisions) * dx, y1 + ((float) i / divisions) * dy, x1 + ((i + 1f) / divisions) * dx, y1 + ((i + 1f) / divisions) * dy);
			}
		}
	}

	public static void line(TextureRegion texture, float x, float y, float x2, float y2) {
		float length = com.badlogic.gdx.math.Vector2.dst(x, y, x2, y2) + thickness / 2;
		float angle = ((float) Math.atan2(y2 - y, x2 - x) * MathUtils.radDeg);

		batch.draw(texture, x - thickness / 2, y - thickness / 2, thickness / 2, thickness / 2, length, thickness, 1f,
				1f, angle);
	}

	public static void unspacedLine(TextureRegion texture, float x, float y, float x2, float y2) {
		float length = com.badlogic.gdx.math.Vector2.dst(x, y, x2, y2);
		float angle = ((float) Math.atan2(y2 - y, x2 - x) * MathUtils.radDeg);

		batch.draw(texture, x, y - thickness / 2, 0, thickness / 2, length, thickness, 1f, 1f, angle);
	}

	public static void circle(float x, float y, float rad) {
		polygon(circle, x, y, rad);
	}

	public static void dashCircle(float x, float y, float scl) {
		dashpolygon(dashCircle, x, y, scl);
	}

	public static void spikes(float x, float y, float radius, float length, int spikes, float rot) {
		vector.setX(0);
		vector.setY(1);
		GDXVectorUtils.update(vector);
		float step = 360f / spikes;
		for (int i = 0; i < spikes; i++) {
			GDXVectorUtils.getGDXVector2(vector).setAngle(i * step + rot);
			GDXVectorUtils.getGDXVector2(vector).setLength(radius);
			float x1 = (float)vector.getX(), y1 = (float)vector.getY();
			GDXVectorUtils.getGDXVector2(vector).setLength(radius + length);

			line(x + x1, y + y1, x + (float)vector.getX(), y + (float)vector.getY());
		}
	}

	public static void spikes(float x, float y, float rad, float length, int spikes) {
		spikes(x, y, rad, length, spikes, 0);
	}

	public static void polygon(int sides, float x, float y, float radius, float angle) {
		vector.setX(0);
		vector.setY(0);
		GDXVectorUtils.update(vector);
		for (int i = 0; i < sides; i++) {
			vector.setX(radius);
			vector.setY(0);
			GDXVectorUtils.update(vector);
			GDXVectorUtils.getGDXVector2(vector).setAngle(360f / sides * i + angle + 90);
			float x1 = (float)vector.getX();
			float y1 = (float)vector.getY();

			vector.setX(radius);
			vector.setY(0);
			GDXVectorUtils.update(vector);
			GDXVectorUtils.getGDXVector2(vector).setAngle(360f / sides * (i + 1) + angle + 90);
			DrawingManager.line(x1 + x, y1 + y, (float)vector.getX() + x, (float)vector.getY() + y);
		}
	}

	public static void polySegment(int sides, int from, int to, float x, float y, float radius, float angle) {
		vector.setX(0);
		vector.setY(0);
		GDXVectorUtils.update(vector);
		for (int i = from; i < to; i++) {
			vector.setX(radius);
			vector.setY(0);
			GDXVectorUtils.update(vector);
			GDXVectorUtils.getGDXVector2(vector).setAngle(360f / sides * i + angle + 90);
			float x1 = (float)vector.getX();
			float y1 = (float)vector.getY();

			vector.setX(radius);
			vector.setY(0);
			GDXVectorUtils.update(vector);
			GDXVectorUtils.getGDXVector2(vector).setAngle(360f / sides * (i + 1) + angle + 90);

			DrawingManager.line(x1 + x, y1 + y, (float)vector.getX() + x, (float)vector.getY() + y);
		}
	}

	public static void curve(float x1, float y1, float cx1, float cy1, float cx2, float cy2, float x2, float y2, int segments) {
		float subdiv_step = 1f / segments;
		float subdiv_step2 = subdiv_step * subdiv_step;
		float subdiv_step3 = subdiv_step * subdiv_step * subdiv_step;

		float pre1 = 3 * subdiv_step;
		float pre2 = 3 * subdiv_step2;
		float pre4 = 6 * subdiv_step2;
		float pre5 = 6 * subdiv_step3;

		float tmp1x = x1 - cx1 * 2 + cx2;
		float tmp1y = y1 - cy1 * 2 + cy2;

		float tmp2x = (cx1 - cx2) * 3 - x1 + x2;
		float tmp2y = (cy1 - cy2) * 3 - y1 + y2;

		float fx = x1;
		float fy = y1;

		float dfx = (cx1 - x1) * pre1 + tmp1x * pre2 + tmp2x * subdiv_step3;
		float dfy = (cy1 - y1) * pre1 + tmp1y * pre2 + tmp2y * subdiv_step3;

		float ddfx = tmp1x * pre4 + tmp2x * pre5;
		float ddfy = tmp1y * pre4 + tmp2y * pre5;

		float dddfx = tmp2x * pre5;
		float dddfy = tmp2y * pre5;

		while (segments-- > 0) {
			float fxold = fx, fyold = fy;
			fx += dfx;
			fy += dfy;
			dfx += ddfx;
			dfy += ddfy;
			ddfx += dddfx;
			ddfy += dddfy;
			line(fxold, fyold, fx, fy);
		}

		line(fx, fy, x2, y2);
	}

	public static void swirl(float x, float y, float radius, float fraction) {
		int sides = 50;
		int max = (int) (sides * (fraction + 0.001f));
		vector.setX(0);
		vector.setY(0);
		GDXVectorUtils.update(vector);

		for (int i = 0; i < max; i++) {
			vector.setX(radius);
			vector.setY(0);
			GDXVectorUtils.update(vector);
			GDXVectorUtils.getGDXVector2(vector).setAngle(360f / sides * i);
			float x1 = (float) vector.getX();
			float y1 = (float) vector.getY();

			vector.setX(radius);
			vector.setY(0);
			GDXVectorUtils.update(vector);
			GDXVectorUtils.getGDXVector2(vector).setAngle(360f / sides * (i + 1));

			DrawingManager.line(x1 + x, y1 + y, (float)vector.getX() + x, (float)vector.getY() + y);
		}
	}

	public static void polygon(int sides, float x, float y, float radius) {
		polygon(sides, x, y, radius, 0);
	}

	public static void polygon(Vector2[] vertices, float offsetx, float offsety, float scl) {
		for (int i = 0; i < vertices.length; i++) {
			Vector2 current = vertices[i];
			Vector2 next = i == vertices.length - 1 ? vertices[0] : vertices[i + 1];
			line((float)current.getX() * scl + offsetx, (float)current.getY() * scl + offsety, (float)next.getX() * scl + offsetx, (float)next.getY() * scl + offsety);
		}
	}

	public static void dashpolygon(Vector2[] vertices, float offsetx, float offsety, float scl) {
		for (int i = 0; i < vertices.length; i++) {
			if (i % 2 != 0)
				continue;
			Vector2 current = vertices[i];
			Vector2 next = i == vertices.length - 1 ? vertices[0] : vertices[i + 1];
			line((float)current.getX() * scl + offsetx, (float) current.getY() * scl + offsety, (float) next.getX() * scl + offsetx, (float)next.getY() * scl + offsety);
		}
	}

	public static void polygon(float[] vertices, float offsetx, float offsety, float scl) {
		for (int i = 0; i < vertices.length / 2; i++) {
			float x = vertices[i * 2];
			float y = vertices[i * 2 + 1];

			float x2 = 0, y2 = 0;
			if (i == vertices.length / 2 - 1) {
				x2 = vertices[0];
				y2 = vertices[1];
			} else {
				x2 = vertices[i * 2 + 2];
				y2 = vertices[i * 2 + 3];
			}

			line(x * scl + offsetx, y * scl + offsety, x2 * scl + offsetx, y2 * scl + offsety);
		}
	}

	public static void square(float x, float y, float rad) {
		linerect(x - rad, y - rad, rad * 2, rad * 2);
	}

	public static void fillrect(float x, float y, float width, float height) {
		batch.draw(blankregion, x - width / 2f, y - height / 2f, width, height);
	}

	public static void fillcrect(float x, float y, float width, float height) {
		batch.draw(blankregion, x, y, width, height);
	}

	public static void linerect(float x, float y, float width, float height, int xspace, int yspace) {
		x -= xspace;
		y -= yspace;
		width += xspace * 2;
		height += yspace * 2;

		batch.draw(blankregion, x, y, width, thickness);
		batch.draw(blankregion, x, y + height, width, -thickness);

		batch.draw(blankregion, x + width, y, -thickness, height);
		batch.draw(blankregion, x, y, thickness, height);
	}

	public static void linerect(float x, float y, float width, float height) {
		linerect(x, y, width, height, 0);
	}

	public static void linecrect(float x, float y, float width, float height) {
		linerect(x - width / 2, y - height / 2, width, height, 0);
	}

	public static void linerect(Rectangle rect) {
		linerect(rect.x, rect.y, rect.width, rect.height, 0);
	}

	public static void linerect(float x, float y, float width, float height, int space) {
		linerect(x, y, width, height, space, space);
	}

	public static void thickness(float thick) {
		thickness = thick;
	}

	public static void thick(float thick) {
		thickness = thick;
	}

	public static float getThickness() {
		return thickness;
	}

	public static void tscl(float scl) {
		GDXSystem.font.getData().setScale(scl);
	}

	public static void text(String text, float x, float y) {
		text(text, x, y, Align.center);
	}

	public static void text(String text, float x, float y, int align) {
		GDXSystem.font.draw(batch, text, x, y, 0, align, false);
	}

	public static void tcolor(Color color) {
		GDXSystem.font.setColor(color);
	}

	public static void tcolor(float r, float g, float b, float a) {
		GDXSystem.font.setColor(r, g, b, a);
	}

	public static void tcolor(float r, float g, float b) {
		GDXSystem.font.setColor(r, g, b, 1f);
	}

	public static void tcolor(float alpha) {
		GDXSystem.font.setColor(1f, 1f, 1f, alpha);
	}

	public static void tcolor() {
		GDXSystem.font.setColor(Color.WHITE);
	}

	public static void reset() {
		if (GDXSystem.atlas != null && blankregion.getTexture().getWidth() == 1 && GDXSystem.atlas.hasRegion("blank")) {
			blankregion = GDXSystem.atlas.getRegion("blank");
		}

		thickness(1f);
		color();
		if (GDXSystem.font != null)
			tcolor();
	}

	public static TextureRegion region(String name) {
		return GDXSystem.atlas.getRegion(name);
	}

	public static boolean hasRegion(String name) {
		return GDXSystem.atlas.hasRegion(name);
	}

	static void dispose() {
		if (blankregion.getTexture().getWidth() == 1)
			blankregion.getTexture().dispose();
		blankregion = null;
	}

}