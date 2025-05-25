import java.awt.*;

public class PlayerAgario extends CellBase{

    PlayerAgario(float x, float y, float radius, Color color)
    {
        super(x,y,radius,color);
    }

    @Override
    public void onHit( Collision myHitCollsion, Collision otherHitCollision, GameObject other )
    {
        float otherRadius = otherHitCollision.radius;
    }

    @Override
    public void update(float tick)
    {

    }
}
