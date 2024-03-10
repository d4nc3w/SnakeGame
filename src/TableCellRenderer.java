import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.*;
public class TableCellRenderer extends DefaultTableCellRenderer {
    private Map<Color, JLabel> cellRenderers;

    public TableCellRenderer() {
        cellRenderers = new HashMap<>();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        JLabel cellLabel = getOrCreateCellRenderer(value);
        cellLabel.setSize(new Dimension(table.getRowHeight(), table.getRowHeight()));
        return cellLabel;
    }

    private JLabel getOrCreateCellRenderer(Object value) {
        Color color = (value instanceof Color) ? (Color) value : Color.BLACK;

        if (!cellRenderers.containsKey(color)) {
            JLabel renderer = (color.equals(Color.RED)) ? createCircularLabel(color) : createCellLabel(color);
            cellRenderers.put(color, renderer);
        }

        return cellRenderers.get(color);
    }

    private JLabel createCellLabel(Color color) {
        JLabel cellLabel = new JLabel();
        cellLabel.setOpaque(true);
        cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cellLabel.setVerticalAlignment(SwingConstants.CENTER);
        cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cellLabel.setBackground(color);
        cellLabel.setForeground(null);
        cellLabel.setText("");
        return cellLabel;
    }

    private JLabel createCircularLabel(Color color) {
        JLabel cellLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(color);
                int size = Math.min(getWidth(), getHeight());
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                g2d.fillOval(x, y, size, size);

                g2d.dispose();
            }
        };

        cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cellLabel.setVerticalAlignment(SwingConstants.CENTER);
//      cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cellLabel.setForeground(null);
        cellLabel.setText("");
        return cellLabel;
    }
}

