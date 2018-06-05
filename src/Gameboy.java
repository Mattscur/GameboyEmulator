import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.*;

public class Gameboy extends Canvas {

	CPU cpu;
	GPU gpu;
	MemoryManager memory;
	Screen screen;

	int width = 160;
	int height = 144;

	/*
	 * Initializes the Gameboy's components and links them together
	 */
	public Gameboy() {
		// Initializes processors and memory
		cpu = new CPU();
		gpu = new GPU();
		memory = new MemoryManager();
		//screen = new Screen(width, height);

		// Links the components together
		cpu.setGpu(gpu);
		cpu.setMemory(memory);

		gpu.setCpu(cpu);
		gpu.setMemory(memory);

		memory.setCpu(cpu);
		memory.setGpu(gpu);
	}

	public void run() {
		int counter = 0;
		int framesPerSecond = 60;
		long timeJump = 1000/framesPerSecond;
		long desiredTime = System.currentTimeMillis() + timeJump;
		boolean tick = true;
		while (counter < 30) {
			if(tick){
				desiredTime = System.currentTimeMillis() + timeJump;
				tick = false;
			}
			
			System.out.println("\n\nSTEP : " + counter++);
		
			// System.out.println("Cpu");
			cpu.step();

			// System.out.println("Gpu");
			gpu.step(cpu.gettClockRegister());
			
			while(System.currentTimeMillis() < desiredTime){
				continue;
			}
			// 
			//if (gpu.isTimeToRender()) {
			if (System.currentTimeMillis() >= desiredTime){
				//System.out.println("Screen");
			//	screen.renderGameboyScreen(gpu.getScreenData());
			//	screen.repaint();
				tick = true;
			}

			
		}

	}

}
