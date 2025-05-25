import java.awt.*;

public class PlayerAgario extends CellBase{

    PlayerAgario(float x, float y, float radius, Color color)
    {
        super(x,y,radius,color);
    }

    @Override
    public void onHit( Collision myHitCollsion, Collision otherHitCollision, GameObject other )
    {
        if(otherHitCollision.type == Collision.Type.COLLISION)
        {
            float enemyRadius = otherHitCollision.radius;
            if( radius * 0.75 > enemyRadius )
            {
                other.setValidity(false);
                this.setRadius(radius + (enemyRadius * 0.1f));
            }
            else
            {
                //this.setRadius(radius * 0.75f);
            }
        }
    }

    @Override
    public void update(float tick)
    {

    }
}
