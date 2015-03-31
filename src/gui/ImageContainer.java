package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import logic.StackTracePrinter;

/**
 *
 * @author FINDarkside
 */
public class ImageContainer extends JPanel {

    private BufferedImage img;
    private double imgScale;
    private int imgHeight = 170;
    private int imgWidth = 1;

    public ImageContainer() {
    }

    public ImageContainer(BufferedImage img) {
        setImage(img);
    }

    public void setImage(BufferedImage img) {
        imgScale = (double) imgHeight / img.getHeight();
        imgWidth = (int) (img.getWidth() * imgScale);

        BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.SCALE_SMOOTH);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g.drawImage(img, 0, 0, imgWidth, imgHeight, null);
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
            int x = (this.getWidth() - img.getWidth()) / 2;
            int y = 0;

            g.drawImage(img, x, y, null);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imgWidth, imgHeight);
    }

}
