import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class GameScene
{
    protected final List<GameObject> gameObjects = new ArrayList<>();
    private final List<GameObject> toAdd    = new ArrayList<>();
    private final List<GameObject> toRemove = new ArrayList<>();

    protected PlayerAgario player = null;

    public GameScene()
    {
    }
    
    public void addGameObject(GameObject obj) {
        if (obj == null) return;
        toAdd.add(obj);
    }

    public void removeGameObject(GameObject obj) {
        if (obj == null) return;
        toRemove.add(obj);
    }

    private void flushPending() {
        if (!toAdd.isEmpty()) {
            gameObjects.addAll(toAdd);
            toAdd.clear();
        }
        if (!toRemove.isEmpty()) {
            gameObjects.removeAll(toRemove);
            toRemove.clear();
        }
    }
    public void cleanup() {
        for(var i : gameObjects)
        {
            if (!i.isValid())
            {
                removeGameObject(i); 
            }
        }
    }

    public PlayerAgario getPlayer()
    {
        return player;
    }

    public void update()
    {
        cleanup();
        
        flushPending();
        
        collide();

        float tick = 0.016f;
        for(var object : gameObjects)
        {
            object.update(tick);
        }
    }
    
    public void render(Graphics2D g2, View view)
    {
        for(var object : gameObjects)
        {
            object.render(g2, view);
        }
    }

    public void collide()
    {
        for(var object0 : gameObjects)
        {          
            for(var object1 : gameObjects)
            {
                if( object0 != object1 )
                {
                    // test collision
                    var transform0 = object0.getTransform();
                    var transform1 = object1.getTransform();

                    var colliders0 = object0.getCollider();
                    var colliders1 = object1.getCollider();

                    for(int i = 0; i < colliders0.size(); ++i)
                    {
                        var collider0 = colliders0.get(i);
                        float rad0 = collider0.collisionScale * collider0.radius;

                        for(int j = 0; j < colliders1.size(); ++j)
                        {
                            var collider1 = colliders1.get(j);
                            float rad1 = collider1.collisionScale  * collider1.radius;

                            float dx = transform0.x - transform1.x;
                            float dy = transform0.y - transform1.y;
                            
                            float distSqr = dx * dx + dy * dy;

                            float radSum = rad0 + rad1;
                            if (distSqr < (radSum * radSum))
                            {
                                object0.onHit(collider0, collider1, object1);
                                object1.onHit(collider1, collider0, object0);
                            }               
                        }
                    }
                }
            }
        }
    }
};