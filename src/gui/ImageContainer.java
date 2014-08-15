package gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author FINDarkside
 */
public class ImageContainer extends JPanel {

    private Image img;
    int IMG_WIDTH = 400;
    int IMG_HEIGHT = 150;

    public ImageContainer() {
    }

    public ImageContainer(Image img) {
        setImage(img);
    }

    public void setImage(Image img) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.SCALE_DEFAULT);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(img, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        this.img = resizedImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            int x = (this.getWidth() - img.getWidth(null)) / 2;
            int y = (this.getHeight() - img.getHeight(null)) / 2;

            g.drawImage(img, x, y, null);
        }

    }

}
