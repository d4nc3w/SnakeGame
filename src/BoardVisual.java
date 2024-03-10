import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BoardVisual extends JFrame implements GameView {
    public static final int BOARD_WIDTH = 25;
    public static final int BOARD_HEIGHT = 16;
    private JTable table;
    private static DefaultTableModel tableM;
    private Controller controller;
    private static JLabel scoreLabel;

    public BoardVisual(Controller controller) {
        this.controller = controller;

        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scoreLabel, BorderLayout.NORTH);

        // Initialize the table
        tableM = new DefaultTableModel(BOARD_HEIGHT, BOARD_WIDTH) {
            @Override
            public void setValueAt(
                    Object value, int row, int column) {

                super.setValueAt(value, row, column);

                fireTableCellUpdated(row, column);
            }
        };
        table = new JTable(tableM);
        table.setDefaultRenderer(Object.class, new TableCellRenderer());

        table.getTableHeader().setVisible(false);
        table.setRowHeight(40);
        table.setIntercellSpacing(new Dimension(0, 0));

        Dimension tableSize = new Dimension(BOARD_WIDTH * 40, (BOARD_HEIGHT + 1) * 40);
        table.setPreferredScrollableViewportSize(tableSize);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(tableSize);

        getContentPane().add(scrollPane);

        addKeyListener(controller.keyListener());

        setVisible(true);
        requestFocus();
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void updateCellColor(int x, int y, Color color) {
        if (x >= 0 && x < BoardModel.BOARD_WIDTH && y >= 0 && y < BoardModel.BOARD_HEIGHT) {
            tableM.setValueAt(color, y, x);
        }
    }

    @Override
    public void updateScoreLabel(int score) {
        scoreLabel.setText("Score: " + score);
    }
}