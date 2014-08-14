
package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author FINDarkside
 */
public class ImageContainer extends JPanel {

    private Image img;


    public ImageContainer(Image img) {
        this.img = img;
    }

    public void setImage(Image img) {
        this.img = img;
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (this.getWidth() - img.getWidth(null)) / 2;
        int y = (this.getHeight() - img.getHeight(null)) / 2;
        g.drawImage(img, x, y, null);

    }

}
