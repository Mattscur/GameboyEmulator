import java.awt.*;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel {

	private int width, height;
	public Color[][] pixels;

	private final Color WHITE = new Color(255, 255, 255);
	private final Color LGRAY = new Color(192, 192, 192);
	private final Color DGRAY = new Color(96, 96, 96);
	private final Color BLACK = new Color(0, 0, 0);



	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new Color[width][height];
		clearPixels();

		Frame frame = new Frame("Mini Tennis");
		frame.add(this);
		frame.setSize(width, height);
		frame.setVisible(true);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				g.setColor(pixels[x][y]);
				g.drawLine(x, y, x, y);
			}
		}
	}

	private void clearPixels() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x][y] = BLACK;
			}
		}
	}
	
	public void renderGameboyScreen(int[][] gpuData) {
		/*Takes in an int[][] of numbers from [0,3] which represents the colors of individual pixels on the screen
		 * Sets graphics data to be that of screen passed in*/
		
		for (int x = 0; x < gpuData.length; x++) {
			for (int y = 0; y < gpuData[0].length; y++) {
				pixels[x][y] = getColorFromData(gpuData, x, y);
			}
		}
	}

	private Color getRandom() {
		Random r = new Random();
		
		int x = r.nextInt(2);
		if(x == 0){
			return new Color(0,0,0);
		}
		else if(x == 1){
			return new Color(255,255,255);
		}
		return new Color(255,0,0);
	}




	private Color getColorFromData(int[][] gpuData, int x, int y) {
		if (gpuData[x][y] == 0) {
			return BLACK;
		} else if (gpuData[x][y] == 1) {
			return DGRAY;
		} else if (gpuData[x][y] == 2) {
			return LGRAY;
		}
		return WHITE;
	}

}
