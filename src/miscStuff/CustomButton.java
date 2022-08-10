package miscStuff;

import java.awt.Dimension;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
public class CustomButton extends JButton{
    public CustomButton(ImageIcon label, int w, int h, Color txtColor, Color bgColor){
        super(label);
        setPreferredSize(new Dimension(w, h));
        Font font = new Font("Arial", Font.BOLD, 20);
        setFont(font);
        setBackground(bgColor);
        setForeground(txtColor);
    }

    public CustomButton(String label, int w, int h, Color txtColor, Color bgColor){
        super(label);
        setPreferredSize(new Dimension(w, h));
        Font font = new Font("Arial", Font.BOLD, 20);
        setFont(font);
        setBackground(bgColor);
        setForeground(txtColor);
    }

    // protected void paintComponent(Graphics g){
    //     Dimension size = super.getPreferredSize();
    // }
}
