import java.awt.Color;
import java.util.Random;

public class EnemySpawner extends GameObject {
    private final GameScene scene;
    private final Random random = new Random();
    
    private final float rangeX;
    private final float rangeY;

    private final float minInterval;
    private final float maxInterval;
    private float spawnTimer;

    private final float minRadius;
    private final float maxRadius;

    public EnemySpawner(GameScene scene,
                        float minInterval, float maxInterval,
                        float rangeX, float rangeY,
                        float minRadius, float maxRadius) {
        this.scene        = scene;
        this.minInterval  = minInterval;
        this.maxInterval  = maxInterval;
        this.rangeX       = rangeX;
        this.rangeY       = rangeY;
        this.minRadius    = minRadius;
        this.maxRadius    = maxRadius;

        resetTimer();
    }

    private void resetTimer() {
        spawnTimer = minInterval +
                     random.nextFloat() * (maxInterval - minInterval);
    }

    @Override
    public void update(float tick) {
        spawnTimer -= tick;
        if (spawnTimer <= 0f) {
            spawnEnemy();
            resetTimer();
        }
    }

    private void spawnEnemy() {
        Transform pt = scene.player.getTransform();

        float px = pt.x;
        float py = pt.y;

        float x = px + (random.nextFloat() * 2f - 1f) * rangeX;
        float y = py + (random.nextFloat() * 2f - 1f) * rangeY;

        if (random.nextFloat() < 0.15f) {
        BombAgario bomb = new BombAgario(x, y, 25f); // 반지름 25짜리 폭탄
        scene.addGameObject(bomb);
        return; // 폭탄 생성 후 종료 (적은 안 만들고)
        }

        float playerRadius = scene.player.getRadius();
        float baseSize = Math.min(playerRadius, 100f);        

        float radius = minRadius +
                       random.nextFloat() * (baseSize - minRadius);
        Color color = new Color(
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        );

        EnemyAgario enemy = new EnemyAgario(x, y, radius, color);
        scene.addGameObject(enemy);
    }

    @Override
    public void onHit(Collision myHit, Collision otherHit, GameObject other) {
    }
}