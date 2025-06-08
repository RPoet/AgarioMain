import java.awt.*;

public class PlayScene extends GameScene{

    PlayScene(String playerName)
    {
        player = new PlayerAgario(0,0, 30, Color.RED);
        player.setName(playerName);
        
        gameObjects.add(new DebugCircle(250,250, 50, Color.RED));
        gameObjects.add(player);

        EnemySpawner spawner = new EnemySpawner(
            this,
            0.5f, 4.0f,
            300f, 200f,    // 플레이어 주변 X,Y 반경
            10f, 30f       // 적 반지름 범위
        );

        gameObjects.add(spawner);
    }
}
