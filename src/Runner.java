
public class Runner {


	public static void main(String[] args) {
		
	test();
		
		runGameboy();
	}

	private static void test() {
		printByte((byte) -61);
		printByte((byte) 2);
	}
	private static void printByte(byte b){
		System.out.println(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
	}

	private static void runGameboy() {
		Gameboy gameBoy = new Gameboy();
		gameBoy.run();
	}

}
