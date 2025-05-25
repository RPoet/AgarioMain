import javax.swing.*;

public class AgarioMain extends JFrame {

    public AgarioMain() {
        super("Agario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        RenderPanel game = new RenderPanel(50, new PlayScene());
        add(game);
        game.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgarioMain::new);
    }
}

