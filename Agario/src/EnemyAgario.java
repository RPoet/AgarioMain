import java.awt.*;
import java.util.Random;

public class EnemyAgario extends CellBase{
    private static final Random RANDOM = new Random();
    private static final float SPEED_MIN            = 50f;
    private static final float SPEED_MAX            = 200f;
    private static final float CHANGE_INT_MIN       = 0.3f;
    private static final float CHANGE_INT_MAX       = 3.0f;

    private float speed; 
    private float minChangeInterval; 
    private float maxChangeInterval;
    private float dirAngle;
    private float changeTimer;

    EnemyAgario(float x, float y, float radius, Color color)
    {
        super(x,y,radius,color);

        this.speed             = 100f;
        this.minChangeInterval = 0.5f;
        this.maxChangeInterval = 2.0f;

        // 초기 방향과 타이머 설정
        this.dirAngle   = RANDOM.nextFloat() * (float)(2*Math.PI);
        this.changeTimer = nextInterval();
    }

    @Override
    public void onHit( Collision myHitCollsion, Collision otherHitCollision, GameObject other )
    {
        //float otherRadius = otherHitCollision.radius;
    }

    @Override
    public void update(float tick)
    {
        changeTimer -= tick;
        if (changeTimer <= 0f) {
            pickNewMovement();
        }

        float dx = (float)Math.cos(dirAngle) * speed * tick;
        float dy = (float)Math.sin(dirAngle) * speed * tick;
        transform.x += dx;
        transform.y += dy;
    }
    
    private float nextInterval() {
        return minChangeInterval +
               RANDOM.nextFloat() * (maxChangeInterval - minChangeInterval);
    }

    private void pickNewMovement() {
        dirAngle = RANDOM.nextFloat() * (float)(2.0 * Math.PI);

        speed = SPEED_MIN + RANDOM.nextFloat() * (SPEED_MAX - SPEED_MIN);

        changeTimer = CHANGE_INT_MIN +
                      RANDOM.nextFloat() * (CHANGE_INT_MAX - CHANGE_INT_MIN);
    }
}
