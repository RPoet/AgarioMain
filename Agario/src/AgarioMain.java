import javax.swing.*;

public class AgarioMain extends JFrame {

    public AgarioMain() {
        super("Agario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        String name = JOptionPane.showInputDialog("플레이어 이름을 입력하세요:");
        RenderPanel game = new RenderPanel(50, new PlayScene(name));
        add(game);
        game.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgarioMain::new);
    }
}

