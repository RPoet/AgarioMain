import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class RenderPanel extends JPanel {
    private final long animDuration = 500;
    private final int cellSize;

    private double camX = 0, camY = 0;
    private double startCamX, startCamY;
    private double destCamX, destCamY;
    private boolean animating = false;

    private long animStartTime;

    GameScene gameScene = null;
    View viewData = new View();
    
    RenderPanel(int cellSize, GameScene gameScene) {
        this.cellSize = cellSize;
        this.gameScene = gameScene;

        setBackground(Color.BLACK);
        setFocusable(true);
        initClickPan();
    }

    public void setGameScene(GameScene newScene) {
        this.gameScene = newScene;
    }
    
    private void initClickPan() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                int clickX = e.getX();
                int clickY = e.getY();
                
                if (gameScene.isGameOver() && gameScene.isFadeComplete()) {
                    if (gameScene.getRestartButtonRect().contains(clickX, clickY)) {
                        
                        String newName = JOptionPane.showInputDialog(null, "플레이어 이름을 입력하세요:", "게임 다시 시작", JOptionPane.PLAIN_MESSAGE);
                        
                        if (newName != null && !newName.trim().isEmpty()) {
                            RenderPanel.this.gameScene = new PlayScene(newName.trim());
                        }
                        return;
                    }
                }    
                
                double worldX = camX + clickX;
                double worldY = camY + clickY;
                
                destCamX = worldX - getWidth()  / 2.0;
                destCamY = worldY - getHeight() / 2.0;
                
                startCamX = camX;
                startCamY = camY;
                animStartTime = System.currentTimeMillis();
                animating = true;
            }
        });
    }

    private void cameraMove() {
        // hard coupling with player in here because of so sleepy day.
        // will not fix.
        gameScene.getPlayer().SetPosition((float)(camX + (getWidth()*0.5)), (float)(camY + (getHeight()*0.5)));

        if (!animating)
            return;
        long now = System.currentTimeMillis();
        long elapsed = now - animStartTime;
        double t = (double) elapsed / animDuration;

        if (t >= 1.0) {
            t = 1.0;
            animating = false;
        }

        camX = lerp(startCamX, destCamX, t);
        camY = lerp(startCamY, destCamY, t);

        viewData.viewX = camX; 
        viewData.viewY = camY;
    }

    public void start() {
        new Timer(4, e -> {

            cameraMove();
            gameScene.update();
            repaint();

        }).start();
    }


    private double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }


    void drawBackground(Graphics2D g2)
    {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_OFF);

        int w = getWidth(), h = getHeight();

        int offsetX = (int)(camX % cellSize);
        if (offsetX < 0) offsetX += cellSize;
        int offsetY = (int)(camY % cellSize);
        if (offsetY < 0) offsetY += cellSize;

        g2.setColor(Color.DARK_GRAY);

        for (int x = -offsetX; x <= w; x += cellSize) {
            g2.drawLine(x, 0, x, h);
        }
        for (int y = -offsetY; y <= h; y += cellSize) {
            g2.drawLine(0, y, w, y);
        }

        int worldRectX = 0, worldRectY = 0, sz = 100;
        g2.setColor(Color.RED);
        g2.drawLine(
            (int)(worldRectX - camX),
            (int)(worldRectY - camY),
            (int)(worldRectX - camX) + sz, (int)(worldRectY - camY)
        );

        g2.setColor(Color.GREEN);
        g2.drawLine(
            (int)(worldRectX - camX),
            (int)(worldRectY - camY),
            (int)(worldRectX - camX), (int)(worldRectY - camY) + sz
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        gameScene.render(g2, viewData);
    }
}