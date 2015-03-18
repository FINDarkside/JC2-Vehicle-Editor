package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import logic.StackTracePrinter;

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

    public void setImage(File f) {
        BufferedImage img;
        try {
            img = ImageIO.read(f);
        } catch (IOException ex) {
            StackTracePrinter.handle(ex, "Failed to load vehicle image for " + f.getName());
            img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
        setImage(img);
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
