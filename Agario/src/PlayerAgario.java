import java.awt.*;

public class PlayerAgario extends CellBase{

    private String playerName = "";
    private int score = 0;
    
    PlayerAgario(float x, float y, float radius, Color color)
    {
        super(x,y,radius,color);
    }

    public void setName(String name){
        this.playerName=name;
    }
    public String getName(){
        return this.playerName;
    }

    public void addScore(int points){
        score += points;
    }
    public int getScore(){
        return score;
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
                this.addScore((int)enemyRadius);
            }
            else if(other instanceof BombAgario){
                this.setValidity(false);
            }
            else
            {
                this.setValidity(false);
            }
        }
    }

    @Override
    public void update(float tick)
    {

    }
}
