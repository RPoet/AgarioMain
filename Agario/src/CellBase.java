import java.awt.*;

public class CellBase extends GameObject{

    protected Color cellColor;
    protected float radius = 0;

    CellBase(float x, float y, float radius, Color color)
    {
        this.radius = radius;
        this.cellColor = color;
        this.shapes.add(new ShapeInfo(ShapeInfo.Type.OVAL, (int)(radius), 1, new Color(cellColor.getRed(),cellColor.getGreen(),cellColor.getBlue(), 128 )));
        this.shapes.add(new ShapeInfo(ShapeInfo.Type.OVAL, (int)(radius), 0.8f, color));
        this.collider.add(new Collision(radius, 1, Collision.Type.COLLISION));
        
        this.SetPosition(x, y);
    }

    public void setRadius(float newRadius)
    {
        this.radius = newRadius;

        for(var shape : shapes)
        {
            shape.setRadius(newRadius);
        }

        for(var collision : collider)
        {
            collision.radius = newRadius;
        }
    }

    public float getRadius()
    {
        return radius;
    }
}
