
public class Tile {

	public byte[][] pixels;
	
	public Tile() {
		resetPixels();
	}
	public void resetPixels(){
		pixels = new byte[8][8];
		
		/*pixels[4][2] = 2;
		pixels[4][1] = 2;
		pixels[4][3] = 2;
		
		pixels[3][2] = 2;
		pixels[3][1] = 2;
		pixels[3][3] = 2;
		
		pixels[5][2] = 2;
		pixels[5][1] = 2;
		pixels[5][3] = 2;
		*/
	}
	
	public void updatePixels(int row, byte lowBits, byte highBits){
		for (int i = 0; i < 8; i++) {
			byte value = 0;
			if(((lowBits >> i) & 1) > 0)  {
				value += 1;
			}
			if(((highBits >> i) & 1) > 0)  {
				value += 2;
			}
			pixels[row][7 - i] = value;
		}
		
	}
}
