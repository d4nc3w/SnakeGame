import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ScorePanel extends JFrame {
    private final int WIDTH = 600;
    private final int HEIGHT = 300;
    private Controller controller;
    private JTable scoreTable;
    private DefaultTableModel tableModel;

    public ScorePanel(Controller controller) {
        setTitle("Game Over");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        tableModel = new DefaultTableModel(new String[]{"Name", "Score"}, 0);
        scoreTable = new JTable(tableModel);
        scoreTable.setEnabled(false);
        scoreTable.setRowSelectionAllowed(false);
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        getContentPane().setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("TOP PLAYERS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);

        this.controller = controller;
        loadTopPlayers();
    }

    public void loadTopPlayers() {
        try (FileInputStream inputStream = new FileInputStream("players.bin")) {
            List<Player> playerList = new ArrayList<>();

            int nameLength;
            while ((nameLength = inputStream.read()) != -1) {
                byte[] nameBytes = new byte[nameLength];
                inputStream.read(nameBytes);
                String name = new String(nameBytes, StandardCharsets.UTF_8);

                int score = 0;
                for (int i = 3; i >= 0; i--) {
                    int byteValue = inputStream.read();
                    score |= byteValue << (i * 8);
                }

                Player player = new Player(name, score);
                playerList.add(player);
            }

            playerList.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

            tableModel.setRowCount(0);

            int limit = Math.min(playerList.size(), 10);
            for (int i = 0; i < limit; i++) {
                Player player = playerList.get(i);
                tableModel.addRow(new Object[]{player.getName(), player.getScore()});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void loadTopPlayers() {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream("players.bin"))) {
            List<Player> playerList = new ArrayList<>();

            while (inputStream.available() > 0) {
                int nameLength = inputStream.readByte();

                byte[] nameBytes = new byte[nameLength];
                inputStream.readFully(nameBytes);
                String name = new String(nameBytes);

                int score = inputStream.readInt();

                Player player = new Player(name, score);
                playerList.add(player);
            }

            playerList.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

            tableModel.setRowCount(0);

            int limit = Math.min(playerList.size(), 10);
            for (int i = 0; i < limit; i++) {
                Player player = playerList.get(i);
                tableModel.addRow(new Object[]{player.getName(), player.getScore()});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
