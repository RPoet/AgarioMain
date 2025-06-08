import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

public class GameScene {
    protected final List<GameObject> gameObjects = new ArrayList<>();
    private final List<GameObject> toAdd = new ArrayList<>();
    private final List<GameObject> toRemove = new ArrayList<>();
    protected PlayerAgario player = null;

    private boolean isGameOver = false;
    private boolean topScorePrinted = false;

    // 페이드 아웃
    private boolean isFading = false;
    private float fadeAlpha = 0f;
    private boolean isFadeComplete = false;
    private Rectangle restartButtonRect = new Rectangle(100, 500, 200, 50);

    // 지진 효과
    private float shakeTime = 0f;
    private final float shakeDuration = 1.0f;      
    private final float shakeIntensity = 5.0f;       
    private float tick = 0.016f;  // 고정된 프레임 간격 (60fps)

    public GameScene() {
    }

    public void addGameObject(GameObject obj) {
        if (obj != null) {
            this.toAdd.add(obj);
        }
    }

    public void removeGameObject(GameObject obj) {
        if (obj != null) {
            this.toRemove.add(obj);
        }
    }

    public Rectangle getRestartButtonRect() {
        return restartButtonRect;
    }

    private void flushPending() {
        if (!this.toAdd.isEmpty()) {
            this.gameObjects.addAll(this.toAdd);
            this.toAdd.clear();
        }

        if (!this.toRemove.isEmpty()) {
            this.gameObjects.removeAll(this.toRemove);
            this.toRemove.clear();
        }
    }

    public void cleanup() {
        for (GameObject obj : gameObjects) {
            if (!obj.isValid()) {
                this.removeGameObject(obj);
            }
        }
    }

    public PlayerAgario getPlayer() {
        return this.player;
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public float getShakeOffsetX() {
        if (shakeTime > 0) {
            return (float)((Math.random() * 2 - 1) * shakeIntensity);
        }
        return 0f;
    }

    public float getShakeOffsetY() {
        if (shakeTime > 0) {
            return (float)((Math.random() * 2 - 1) * shakeIntensity);
        }
        return 0f;
    }

    public boolean isFadeComplete() {
        return this.isFadeComplete;
    }

    public void update() {
        cleanup();
        flushPending();

        if (!player.isValid() && !isGameOver) {
            System.out.println("플레이어 죽음 감지!");
            isGameOver = true;
            isFading = true;
            shakeTime = shakeDuration;
        }

        if (isGameOver) {
            if (shakeTime > 0f) {
                shakeTime -= tick;
            }

            if (isFading) {
                fadeAlpha += 100f * tick;

                if (fadeAlpha >= 255f) {
                    System.out.println("현재 알파값: " + fadeAlpha);
                    fadeAlpha = 255f;
                    isFading = false;
                    isFadeComplete = true;

                    if (!topScorePrinted) {
                        TopScoreManager.addScore(player.getName(), player.getScore());
                        topScorePrinted = true;
                    }
                }
            }

                // 이 시점에 return하면 렌더링 상태를 유지하면서 페이드도 작동함
                return;
            }

            // 평상시 게임 오브젝트 갱신
            collide();
            for (GameObject object : gameObjects) {
                object.update(tick);
            }
        }


    public void render(Graphics2D g2, View view) {
        float shakeX = getShakeOffsetX();
        float shakeY = getShakeOffsetY();

        AffineTransform original = g2.getTransform();
        g2.translate(shakeX, shakeY); // 지진 효과 반영

        for (GameObject object : gameObjects) {
            object.render(g2, view);
        }

        g2.setTransform(original);

        if (isGameOver) {
            Graphics2D g2d = (Graphics2D) g2.create();

                // 페이드 아웃
                if (fadeAlpha > 0) {
                    g2d.setColor(new Color(0, 0, 0, (int)fadeAlpha));
                    g2d.fillRect(0, 0, 1200, 800); // 전체 화면 크기로 조정
                }

                // Top10과 버튼
                if (isFadeComplete) {
                    drawTopScores(g2d);
                    drawRestartButton(g2d);
                }

                g2d.dispose();
        }
        if (!isGameOver) {
            drawTop3HUD(g2);
            drawPlayerHUD(g2);
        }
    }

    private void drawTop3HUD(Graphics2D g2) {
        List<TopScoreManager.PlayerRecord> scores = TopScoreManager.getTopScores();
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));

        int y = 30;
        for (int i = 0; i < Math.min(3, scores.size()); i++) {
            TopScoreManager.PlayerRecord r = scores.get(i);
            String medal = switch (i) {
                case 0 -> "1st : ";
                case 1 -> "2nd : ";
                case 2 -> "3rd : ";
                default -> "";
            };
            g2.drawString(medal + r.name + " [" + r.score + "]", 20, y);
            y += 25;
        }
    }

    private void drawPlayerHUD(Graphics2D g2) {
        if (player == null) return;

        String name = player.getName();
        String score = Integer.toString(player.getScore());

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));

        FontMetrics fm = g2.getFontMetrics();
        int nameWidth = fm.stringWidth(name);
        g2.drawString(name, 1180 - nameWidth, 30);  // 우상단 이름

        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        int scoreWidth = g2.getFontMetrics().stringWidth(score);
        g2.drawString(score, 1180 - scoreWidth, 100);  // 우상단 점수
    }



    private void drawRestartButton(Graphics2D g2) {
        restartButtonRect = new Rectangle(500, 600, 200, 50);
    
        // 버튼 배경
        g2.setColor(new Color(255, 255, 255, 220)); // 약간 투명한 흰색
        g2.fillRoundRect(restartButtonRect.x, restartButtonRect.y, 
                         restartButtonRect.width, restartButtonRect.height, 15, 15);

        // 테두리
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(restartButtonRect.x, restartButtonRect.y, 
                         restartButtonRect.width, restartButtonRect.height, 15, 15);

        // 텍스트
        g2.setFont(new Font("맑은 고딕", Font.BOLD, 22)); // 한글 지원 폰트로 변경
        g2.setColor(Color.BLACK);

        String text = "다시 시작";
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        int textX = restartButtonRect.x + (restartButtonRect.width - textWidth) / 2;
        int textY = restartButtonRect.y + (restartButtonRect.height + textHeight) / 2 - 5;

        g2.drawString(text, textX, textY);
    }

    public void collide() {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject object0 = gameObjects.get(i);

            for (int j = 0; j < gameObjects.size(); j++) {
                if (i == j) continue;

                GameObject object1 = gameObjects.get(j);

                Transform transform0 = object0.getTransform();
                Transform transform1 = object1.getTransform();
                ArrayList<Collision> colliders0 = object0.getCollider();
                ArrayList<Collision> colliders1 = object1.getCollider();

                for (Collision collider0 : colliders0) {
                    float rad0 = collider0.collisionScale * collider0.radius;

                    for (Collision collider1 : colliders1) {
                        float rad1 = collider1.collisionScale * collider1.radius;
                        float dx = transform0.x - transform1.x;
                        float dy = transform0.y - transform1.y;
                        float distSqr = dx * dx + dy * dy;
                        float radSum = rad0 + rad1;

                        if (distSqr < radSum * radSum) {
                            object0.onHit(collider0, collider1, object1);
                            object1.onHit(collider1, collider0, object0);
                        }
                    }
                }
            }
        }
    }

    private void drawTopScores(Graphics2D g2) {
        int panelWidth = 1200;
        int boxWidth = 500;
        int boxHeight = 400;
        int boxX = (panelWidth - boxWidth) / 2;
        int boxY = 150;

        // 반투명 박스 배경
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // 테두리
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // 제목
        g2.setFont(new Font("Arial", Font.BOLD, 28));
        g2.setColor(Color.YELLOW);
        String title = "Top 10 Players";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, boxX + (boxWidth - titleWidth) / 2, boxY + 40);

        // 랭킹 리스트
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.WHITE);

        List<TopScoreManager.PlayerRecord> scores = TopScoreManager.getTopScores();
        int y = boxY + 80;
        int rank = 1;

        for (TopScoreManager.PlayerRecord record : scores) {
            String line = String.format("%2d. %-12s  %5d", rank++, record.name, record.score);
            g2.drawString(line, boxX + 40, y);
            y += 30;
        }
    }
}
