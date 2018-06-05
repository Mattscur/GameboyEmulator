import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public class MemoryManager {

	byte[] RAM;
	byte[] BIOS;
	boolean inBIOS;

	CPU cpu;
	GPU gpu;

	final private int BYTEMASK = 0xFF;
	final private int SHORTMASK = 0xFFFF;

	public MemoryManager() {
		RAM = new byte[65536];
		inBIOS = true;
		BIOS = new byte[256];
		loadBIOS();
		loadGame();
	}

	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}

	public void setGpu(GPU gpu) {
		this.gpu = gpu;
	}

	private void loadBIOS() {
		// TODO Implement
		String BIOSpath = "gbBIOS.gb";
		try {
			BIOS = Files.readAllBytes(new File(BIOSpath).toPath());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadGame() {
		String gamePath = "tetris.gb";
		byte[] gameRom;
		try {
			gameRom = Files.readAllBytes(new File(gamePath).toPath());
			for (int i = 0; i < gameRom.length; i++) {
				RAM[0x4000 + i] = gameRom[i];
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//////////////////////// MEMORY BEGIN ////////////////////////
	public byte readByte(int address) {
		
		if (inBIOS) {
			if (address <= 255) {
				//System.out.println("BIOS READ");
				System.out.println("Read Address : " + address + " Got: " + (BIOS[address]&0xFF));
				return BIOS[address];
			}
			else if (cpu.getProgramCounter() >= 0x0100) {

				System.out.println("BIOS DONE");
				inBIOS = false;
				//while(true){}
			}
		}
		//	System.out.println("READING RAM " + address);
		System.out.println("Read Address : " + address + " Got: " + (RAM[address]&0xFF));
		return RAM[address];
	}

	public int readWord(int address) {
		int returnVal = toUnsigned(readByte(address + 1)) << 8;
		returnVal |= (toUnsigned(readByte(address)));
		//System.out.println("read word " + returnVal);
		return returnVal;
	}

	public void writeByte(int address, byte value) {
		// TODO: Handle ram shadow
		//	System.out.println("Writing: " + address);
		//	System.out.println("RAM SIZE " + RAM.length);
		RAM[address] = value;
		if (address >= 0x8000 && address <= 0x97FF) {
			gpu.updateTile(address, value);
		}
	}

	public void writeWord(int address, short value) {
		byte byte1 = (byte) ((value >> 8) & 0xff); // MSB
		byte byte2 = (byte) (value & 0xff); // LSB
		writeByte(address, byte1);
		writeByte(address + 1, byte2);
	}
	
	public void writeWord(int address, int value) {
		writeWord(address, (short) (value & 0xFF));
	}

	//////////////////////// MEMORY END ////////////////////////

	public int toUnsigned(byte val) {
		return val & BYTEMASK;
	}

	public int toUnsigned(short val) {
		return val & SHORTMASK;
	}

}
