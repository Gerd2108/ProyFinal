package clases;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;

public class Estilos {

    public static final Color COLOR_FONDO = new Color(248, 249, 250);
    public static final Color COLOR_PRIMARY = new Color(44, 62, 80);
    public static final Color COLOR_SECONDARY = new Color(52, 152, 219);
    public static final Color COLOR_ACCENT = new Color(230, 126, 34);
    public static final Color COLOR_DANGER = new Color(231, 76, 60);
    public static final Color COLOR_TEXT = new Color(45, 52, 54);
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Color COLOR_BORDER = new Color(223, 228, 234);

    public static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);

    public static void estiloVentana(JFrame frame) {
        frame.getContentPane().setBackground(COLOR_FONDO);
    }

    public static void estiloPanel(JPanel panel) {
        panel.setBackground(COLOR_PRIMARY);
    }

    private static void configurarBaseBoton(JButton btn) {
        btn.setFont(FONT_BOLD);
        btn.setForeground(COLOR_WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    public static void estiloBoton(JButton btn, boolean esPrincipal) {
        configurarBaseBoton(btn);

        Color colorNormal = esPrincipal ? COLOR_ACCENT : COLOR_SECONDARY;
        Color colorHover = esPrincipal ? new Color(243, 156, 18) : new Color(41, 128, 185);

        btn.setBackground(colorNormal);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(colorNormal);
            }
        });
    }

    public static void estiloBotonDestructivo(JButton btn) {
        configurarBaseBoton(btn);

        Color colorNormal = COLOR_DANGER;
        Color colorHover = new Color(192, 57, 43);

        btn.setBackground(colorNormal);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(colorNormal);
            }
        });
    }

    public static void estiloCampo(JTextField txt) {
        txt.setFont(FONT_NORMAL);
        txt.setForeground(COLOR_TEXT);
        txt.setBackground(COLOR_WHITE);
        txt.setCaretColor(COLOR_PRIMARY);

        Border line = BorderFactory.createLineBorder(COLOR_BORDER, 1);
        Border empty = new EmptyBorder(8, 10, 8, 10);
        txt.setBorder(new CompoundBorder(line, empty));
    }

    public static void estiloEtiqueta(JLabel lbl, boolean esTitulo) {
        if (esTitulo) {
            lbl.setFont(FONT_TITULO);
            lbl.setForeground(COLOR_PRIMARY);
        } else {
            lbl.setFont(FONT_NORMAL);
            lbl.setForeground(COLOR_TEXT);
        }
    }

    public static void estiloTabla(JTable tabla, JScrollPane scroll) {

        tabla.setFont(FONT_NORMAL);
        tabla.setForeground(COLOR_TEXT);
        tabla.setGridColor(COLOR_BORDER);
        tabla.setSelectionBackground(new Color(220, 240, 255));
        tabla.setSelectionForeground(COLOR_TEXT);

        tabla.setRowHeight(35);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);

        tabla.getTableHeader().setFont(FONT_BOLD);
        tabla.getTableHeader().setBackground(COLOR_PRIMARY);
        tabla.getTableHeader().setForeground(COLOR_WHITE);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tabla.getTableHeader().setBorder(null);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setBorder(new EmptyBorder(0, 10, 0, 10));
        tabla.setDefaultRenderer(Object.class, centerRenderer);

        scroll.getViewport().setBackground(COLOR_WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        scroll.setBackground(COLOR_WHITE);
    }
    
    public static void cargarEstilosGlobales() {
        try {

            UIManager.put("OptionPane.background", COLOR_WHITE);
            UIManager.put("Panel.background", COLOR_WHITE);

            UIManager.put("OptionPane.messageForeground", COLOR_TEXT);
            UIManager.put("OptionPane.messageFont", FONT_NORMAL);

            UIManager.put("Button.background", COLOR_PRIMARY);
            UIManager.put("Button.foreground", COLOR_WHITE);
            UIManager.put("Button.font", FONT_BOLD);

            UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

            UIManager.put("Button.border", new CompoundBorder(
                    new LineBorder(COLOR_PRIMARY, 1),
                    new EmptyBorder(5, 15, 5, 15)
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
