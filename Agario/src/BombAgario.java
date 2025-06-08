import java.awt.*;

public class BombAgario extends CellBase {

    private float blinkTimer = 0f;
    private float dx, dy;

    public BombAgario(float x, float y, float radius) {
        super(x, y, radius, Color.RED);  

        float angle = (float)(Math.random() * 2 * Math.PI);
        float speed = 80f; 

        dx = (float)Math.cos(angle) * speed;
        dy = (float)Math.sin(angle) * speed;
    }

    @Override
    public void update(float tick) {
        blinkTimer += tick;

        float blinkSpeed = 5.0f; 
        float intensity = (float)(Math.sin(blinkTimer * blinkSpeed) * 0.5 + 0.5);  

        Color blinkColor = new Color(
            (int)(255 * intensity),
            (int)(50 * intensity),
            (int)(50 * intensity)
        );

        for (ShapeInfo s : shapes) {
            s.color = blinkColor;
        }

        transform.x += dx * tick;
        transform.y += dy * tick;
    }
}
