import java.awt.*;
import java.util.ArrayList;

class View
{
    double viewX = 0; 
    double viewY = 0;
    int widthOffset = 0;
    int heightOffset = 0;    
}

class Collision
{
    enum Type
    {
        TRIGGER, COLLISION
    }
    public Type type = Type.COLLISION;
    public float radius;
    public float collisionScale = 1.0f; 

    public Collision( float radius, float collisionScale, Type type )
    {
        this.radius = radius;
        this.collisionScale = collisionScale;
        this.type = type;
    }
}


public class GameObject {
    protected boolean bValid = true;
    protected String name = "undefined";
    protected ArrayList<ShapeInfo> shapes = new ArrayList<>();
    protected ArrayList<Collision> collider = new ArrayList<>();
    protected Transform transform = new Transform();

    public boolean isValid()
    {
        return bValid;
    }

    public void setValidity(boolean bValid)
    {
        this.bValid = bValid;
    }
    
    public ArrayList<ShapeInfo> getShapes()
    {
        return shapes;
    }

    public ArrayList<Collision> getCollider()
    {
        return collider;
    }

    public Transform getTransform()
    {
        return transform;
    }

    public void SetPosition(float x, float y)
    {
        this.transform.x = x;
        this.transform.y = y;
    }

    public void onHit( Collision myHitCollsion, Collision otherHitCollision, GameObject other )
    {
    }

    public void update(float tick)
    {

    }

    public void render(Graphics2D g2, View view)
    {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = (int)transform.x - (int)view.viewX;
        int y = (int)transform.y - (int)view.viewY;
        int z = (int)transform.scale;

        for (ShapeInfo s : shapes)
        {     
            int localOffsetX = (int)(s.width * s.scale * z * 0.5);
            int localOffsetY = (int)(s.height * s.scale * z * 0.5);
            
            int width = (int)(s.width * s.scale * z);
            int height = (int)(s.height * s.scale * z);

            g2.setColor(s.color);
            switch (s.type) {
                case RECTANGLE:
                    g2.fillRect(x - localOffsetX, y - localOffsetY, width, height);
                    break;
                case OVAL:
                    g2.fillOval(x - localOffsetX, y - localOffsetY, width, height);
                    break;
                case LINE:
                    g2.drawLine(x - localOffsetX, y - localOffsetY, width, height);
                    break;
            }
        }
    }
}
