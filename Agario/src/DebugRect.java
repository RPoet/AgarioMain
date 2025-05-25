import java.awt.*;

class DebugCircle extends GameObject {
    boolean bHit;

    DebugCircle(float x, float y, float radius, Color color)
    {
        this.shapes.add(new ShapeInfo(ShapeInfo.Type.OVAL, (int)radius, 1, color));
        this.SetPosition(x,y);
        this.collider.add(new Collision(radius, 1, Collision.Type.TRIGGER));
        
        this.bHit = false;
    }

    @Override
    public void onHit( Collision myHitCollsion, Collision otherHitCollision, GameObject other )
    {
        this.bHit = true;
    }

    @Override
    public void update(float tick)
    {
        if( this.bHit )
        {
            for(var shape : this.shapes)
            {
                shape.color = Color.PINK;
            }
        }
        else
        {
            for(var shape : this.shapes)
            {
                shape.color = Color.BLUE;
            }
        }

        this.bHit = false;
    }
}

public class DebugRect extends GameObject {

    DebugRect()
    {
        this.shapes.add(new ShapeInfo(ShapeInfo.Type.RECTANGLE, 100,1, Color.YELLOW));
    }

    @Override
    public void onHit( Collision myHitCollsion, Collision otherHitCollision, GameObject other )
    {
    }

    @Override
    public void update(float tick)
    {

    }
}
