
public class CPU {

	final private int BYTEMASK = 0xFF;
	final private int SHORTMASK = 0xFFFF;

	byte A;
	byte B;
	byte C;
	byte D;
	byte E;
	byte H;
	byte L;
	byte F;
	byte mClockRegister; // 4times slower
	byte tClockRegister; // faster

	public byte gettClockRegister() {
		return tClockRegister;
	}

	int programCounter;
	int stackPointer;

	long mClock;
	long tClock;

	GPU gpu;
	MemoryManager memory;

	//////////////////////// INITIALIZATION BEGIN ////////////////////////
	public CPU() {
		reset();
	}

	private static void printByte(byte b) {
		System.out.println(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
	}

	public void setGpu(GPU gpu) {
		this.gpu = gpu;
	}

	public void setMemory(MemoryManager memory) {
		this.memory = memory;
	}

	//
	public void reset() {
		clearRegisters();
		resetClock();
	}

	public void setFlags(boolean zero, boolean subtract, boolean halfCarry, boolean carry) {
		F = 0;
		if (zero) {
			F |= (1 << 8);
		}
		if (subtract) {
			F |= (1 << 7);
		}
		if (halfCarry) {
			F |= (1 << 6);
		}
		if (carry) {
			F |= (1 << 5);
		}

	}

	public boolean getZeroFlag() {
		return ((toUnsigned(F) & (1 << 8)) != 0);
	}

	public boolean getSubtractFlag() {
		return ((toUnsigned(F) & (1 << 7)) != 0);
	}

	public boolean getZHalfCarryFlag() {
		return ((toUnsigned(F) & (1 << 6)) != 0);
	}

	public boolean getCarryFlag() {
		return ((toUnsigned(F) & (1 << 5)) != 0);
	}

	/*
	 * Clears all registers by setting them to 0, (0x0)
	 * 
	 */
	public void clearRegisters() {
		A = 0;
		B = 0;
		C = 0;
		D = 0;
		E = 0;
		H = 0;
		L = 0;
		F = 0;

		mClockRegister = 0;
		tClockRegister = 0;

		programCounter = 0;
		stackPointer = 0xFFFE;
	}

	public void resetClock() {
		mClock = 0;
		tClock = 0;
	}

	private int getAF() {
		return ((toUnsigned(A) << 8) + (toUnsigned(F) << 0));
	}

	private int getBC() {
		return ((toUnsigned(B) << 8) + (toUnsigned(C) << 0));
	}

	private int getDE() {
		return ((toUnsigned(D) << 8) + (toUnsigned(E) << 0));
	}

	private int getHL() {
		return ((toUnsigned(H) << 8) + (toUnsigned(L) << 0));
	}

	//////////////////////// INITIALIZATION END ////////////////////////

	/*
	 * Computes one cycle of the gameboys CPU
	 */
	public void step() {

		byte operation = memory.readByte(programCounter); // Fetch
		programCounter++; // Increment program counter

		// TODO add CB operation support
		execute(operation); // Run

		mClock += toUnsigned(mClockRegister); // Update Clock
		tClock += toUnsigned(tClockRegister);

	}

	/*
	 * Executes the appropiate opcode.
	 */

	public int toUnsigned(byte val) {
		return val & BYTEMASK;
	}

	public int toUnsigned(int val) {
		return val;
	}

	public int toUnsigned(short val) {
		return val & SHORTMASK;
	}

	public boolean equals(byte opCode, int checkAgainst) {
		return (((0xFF & opCode) & (0xFF & checkAgainst)) == (0xFF & opCode));
	}

	private void execute(byte opCode) {
		// TODO Implement opcodes
		System.out.println("Executing Opcode : 0x" + Integer.toHexString(opCode & 0xFF));

		if (equals(opCode, 0xCB)) {
			byte operation = memory.readByte(programCounter); // Fetch
			programCounter++; // Increment program counter
			executeCB(operation); // Run
			return;
		}

		if (equals(opCode, 0x00)) {
			x00();
		}
		else if (equals(opCode, 0x00)) {
			x00();
		}
		else if (equals(opCode, 0x01)) {
			x01();
		}
		else if (equals(opCode, 0x02)) {
			x02();
		}
		else if (equals(opCode, 0x03)) {
			x03();
		}
		else if (equals(opCode, 0x04)) {
			x04();
		}
		else if (equals(opCode, 0x05)) {
			x05();
		}
		else if (equals(opCode, 0x06)) {
			x06();
		}
		else if (equals(opCode, 0x07)) {
			x07();
		}
		else if (equals(opCode, 0x08)) {
			x08();
		}
		else if (equals(opCode, 0x09)) {
			x09();
		}
		else if (equals(opCode, 0x0A)) {
			x0A();
		}
		else if (equals(opCode, 0x0B)) {
			x0B();
		}
		else if (equals(opCode, 0x0C)) {
			x0C();
		}
		else if (equals(opCode, 0x0D)) {
			x0D();
		}
		else if (equals(opCode, 0x0E)) {
			x0E();
		}
		else if (equals(opCode, 0x0F)) {
			x0F();
		}
		else if (equals(opCode, 0x11)) {
			x11();
		}
		else if (equals(opCode, 0x12)) {
			x12();
		}
		else if (equals(opCode, 0x13)) {
			x13();
		}
		else if (equals(opCode, 0x14)) {
			x14();
		}
		else if (equals(opCode, 0x15)) {
			x15();
		}
		else if (equals(opCode, 0x16)) {
			x16();
		}
		else if (equals(opCode, 0x17)) {
			x17();
		}
		else if (equals(opCode, 0x18)) {
			x18();
		}
		else if (equals(opCode, 0x19)) {
			x19();
		}
		else if (equals(opCode, 0x1A)) {
			x1A();
		}
		else if (equals(opCode, 0x1B)) {
			x1B();
		}
		else if (equals(opCode, 0x1C)) {
			x1C();
		}
		else if (equals(opCode, 0x1D)) {
			x1D();
		}
		else if (equals(opCode, 0x1E)) {
			x1E();
		}
		else if (equals(opCode, 0x1F)) {
			x1F();
		}
		else if (equals(opCode, 0x20)) {
			x20();
		}
		else if (equals(opCode, 0x21)) {
			x21();
		}
		else if (equals(opCode, 0x22)) {
			x22();
		}
		else if (equals(opCode, 0x23)) {
			x23();
		}
		else if (equals(opCode, 0x24)) {
			x24();
		}
		else if (equals(opCode, 0x25)) {
			x25();
		}
		else if (equals(opCode, 0x26)) {
			x26();
		}
		else if (equals(opCode, 0x27)) {
			x27();
		}
		else if (equals(opCode, 0x28)) {
			x28();
		}
		else if (equals(opCode, 0x29)) {
			x29();
		}
		else if (equals(opCode, 0x2A)) {
			x2A();
		}
		else if (equals(opCode, 0x2B)) {
			x2B();
		}
		else if (equals(opCode, 0x2C)) {
			x2C();
		}
		else if (equals(opCode, 0x2D)) {
			x2D();
		}
		else if (equals(opCode, 0x2E)) {
			x2E();
		}
		else if (equals(opCode, 0x2F)) {
			x2F();
		}
		else if (equals(opCode, 0x30)) {
			x30();
		}
		else if (equals(opCode, 0x31)) {
			x31();
		}
		else if (equals(opCode, 0x32)) {
			x32();
		}
		else if (equals(opCode, 0x33)) {
			x33();
		}
		else if (equals(opCode, 0x34)) {
			x34();
		}
		else if (equals(opCode, 0x35)) {
			x35();
		}
		else if (equals(opCode, 0x36)) {
			x36();
		}
		else if (equals(opCode, 0x37)) {
			x37();
		}
		else if (equals(opCode, 0x38)) {
			x38();
		}
		else if (equals(opCode, 0x39)) {
			x39();
		}
		else if (equals(opCode, 0x3A)) {
			x3A();
		}
		else if (equals(opCode, 0x3B)) {
			x3B();
		}
		else if (equals(opCode, 0x3C)) {
			x3C();
		}
		else if (equals(opCode, 0x3D)) {
			x3D();
		}
		else if (equals(opCode, 0x3E)) {
			x3E();
		}
		else if (equals(opCode, 0x3F)) {
			x3F();
		}
		else if (equals(opCode, 0x40)) {
			x40();
		}
		else if (equals(opCode, 0x41)) {
			x41();
		}
		else if (equals(opCode, 0x42)) {
			x42();
		}
		else if (equals(opCode, 0x43)) {
			x43();
		}
		else if (equals(opCode, 0x44)) {
			x44();
		}
		else if (equals(opCode, 0x45)) {
			x45();
		}
		else if (equals(opCode, 0x46)) {
			x46();
		}
		else if (equals(opCode, 0x47)) {
			x47();
		}
		else if (equals(opCode, 0x48)) {
			x48();
		}
		else if (equals(opCode, 0x49)) {
			x49();
		}
		else if (equals(opCode, 0x4A)) {
			x4A();
		}
		else if (equals(opCode, 0x4B)) {
			x4B();
		}
		else if (equals(opCode, 0x4C)) {
			x4C();
		}
		else if (equals(opCode, 0x4D)) {
			x4D();
		}
		else if (equals(opCode, 0x4E)) {
			x4E();
		}
		else if (equals(opCode, 0x4F)) {
			x4F();
		}
		else if (equals(opCode, 0x50)) {
			x50();
		}
		else if (equals(opCode, 0x51)) {
			x51();
		}
		else if (equals(opCode, 0x52)) {
			x52();
		}
		else if (equals(opCode, 0x53)) {
			x53();
		}
		else if (equals(opCode, 0x54)) {
			x54();
		}
		else if (equals(opCode, 0x55)) {
			x55();
		}
		else if (equals(opCode, 0x56)) {
			x56();
		}
		else if (equals(opCode, 0x57)) {
			x57();
		}
		else if (equals(opCode, 0x58)) {
			x58();
		}
		else if (equals(opCode, 0x59)) {
			x59();
		}
		else if (equals(opCode, 0x5A)) {
			x5A();
		}
		else if (equals(opCode, 0x5B)) {
			x5B();
		}
		else if (equals(opCode, 0x5C)) {
			x5C();
		}
		else if (equals(opCode, 0x5D)) {
			x5D();
		}
		else if (equals(opCode, 0x5E)) {
			x5E();
		}
		else if (equals(opCode, 0x5F)) {
			x5F();
		}
		else if (equals(opCode, 0x60)) {
			x60();
		}
		else if (equals(opCode, 0x61)) {
			x61();
		}
		else if (equals(opCode, 0x62)) {
			x62();
		}
		else if (equals(opCode, 0x63)) {
			x63();
		}
		else if (equals(opCode, 0x64)) {
			x64();
		}
		else if (equals(opCode, 0x65)) {
			x65();
		}
		else if (equals(opCode, 0x66)) {
			x66();
		}
		else if (equals(opCode, 0x67)) {
			x67();
		}
		else if (equals(opCode, 0x68)) {
			x68();
		}
		else if (equals(opCode, 0x69)) {
			x69();
		}
		else if (equals(opCode, 0x6A)) {
			x6A();
		}
		else if (equals(opCode, 0x6B)) {
			x6B();
		}
		else if (equals(opCode, 0x6C)) {
			x6C();
		}
		else if (equals(opCode, 0x6D)) {
			x6D();
		}
		else if (equals(opCode, 0x6E)) {
			x6E();
		}
		else if (equals(opCode, 0x6F)) {
			x6F();
		}
		else if (equals(opCode, 0x70)) {
			x70();
		}
		else if (equals(opCode, 0x71)) {
			x71();
		}
		else if (equals(opCode, 0x72)) {
			x72();
		}
		else if (equals(opCode, 0x73)) {
			x73();
		}
		else if (equals(opCode, 0x74)) {
			x74();
		}
		else if (equals(opCode, 0x75)) {
			x75();
		}
		else if (equals(opCode, 0x76)) {
			x76();
		}
		else if (equals(opCode, 0x77)) {
			x77();
		}
		else if (equals(opCode, 0x78)) {
			x78();
		}
		else if (equals(opCode, 0x79)) {
			x79();
		}
		else if (equals(opCode, 0x7A)) {
			x7A();
		}
		else if (equals(opCode, 0x7B)) {
			x7B();
		}
		else if (equals(opCode, 0x7C)) {
			x7C();
		}
		else if (equals(opCode, 0x7D)) {
			x7D();
		}
		else if (equals(opCode, 0x7E)) {
			x7E();
		}
		else if (equals(opCode, 0x7F)) {
			x7F();
		}
		else if (equals(opCode, 0x80)) {
			x80();
		}
		else if (equals(opCode, 0x81)) {
			x81();
		}
		else if (equals(opCode, 0x82)) {
			x82();
		}
		else if (equals(opCode, 0x83)) {
			x83();
		}
		else if (equals(opCode, 0x84)) {
			x84();
		}
		else if (equals(opCode, 0x85)) {
			x85();
		}
		else if (equals(opCode, 0x86)) {
			x86();
		}
		else if (equals(opCode, 0x87)) {
			x87();
		}
		else if (equals(opCode, 0x88)) {
			x88();
		}
		else if (equals(opCode, 0x89)) {
			x89();
		}
		else if (equals(opCode, 0x8A)) {
			x8A();
		}
		else if (equals(opCode, 0x8B)) {
			x8B();
		}
		else if (equals(opCode, 0x8C)) {
			x8C();
		}
		else if (equals(opCode, 0x8D)) {
			x8D();
		}
		else if (equals(opCode, 0x8E)) {
			x8E();
		}
		else if (equals(opCode, 0x8F)) {
			x8F();
		}
		else if (equals(opCode, 0x90)) {
			x90();
		}
		else if (equals(opCode, 0x91)) {
			x91();
		}
		else if (equals(opCode, 0x92)) {
			x92();
		}
		else if (equals(opCode, 0x93)) {
			x93();
		}
		else if (equals(opCode, 0x94)) {
			x94();
		}
		else if (equals(opCode, 0x95)) {
			x95();
		}
		else if (equals(opCode, 0x96)) {
			x96();
		}
		else if (equals(opCode, 0x97)) {
			x97();
		}
		else if (equals(opCode, 0x98)) {
			x98();
		}
		else if (equals(opCode, 0x99)) {
			x99();
		}
		else if (equals(opCode, 0x9A)) {
			x9A();
		}
		else if (equals(opCode, 0x9B)) {
			x9B();
		}
		else if (equals(opCode, 0x9C)) {
			x9C();
		}
		else if (equals(opCode, 0x9D)) {
			x9D();
		}
		else if (equals(opCode, 0x9E)) {
			x9E();
		}
		else if (equals(opCode, 0x9F)) {
			x9F();
		}
		else if (equals(opCode, 0xA0)) {
			xA0();
		}
		else if (equals(opCode, 0xA1)) {
			xA1();
		}
		else if (equals(opCode, 0xA2)) {
			xA2();
		}
		else if (equals(opCode, 0xA3)) {
			xA3();
		}
		else if (equals(opCode, 0xA4)) {
			xA4();
		}
		else if (equals(opCode, 0xA5)) {
			xA5();
		}
		else if (equals(opCode, 0xA6)) {
			xA6();
		}
		else if (equals(opCode, 0xA7)) {
			xA7();
		}
		else if (equals(opCode, 0xA8)) {
			xA8();
		}
		else if (equals(opCode, 0xA9)) {
			xA9();
		}
		else if (equals(opCode, 0xAA)) {
			xAA();
		}
		else if (equals(opCode, 0xAB)) {
			xAB();
		}
		else if (equals(opCode, 0xAC)) {
			xAC();
		}
		else if (equals(opCode, 0xAD)) {
			xAD();
		}
		else if (equals(opCode, 0xAE)) {
			xAE();
		}
		else if (equals(opCode, 0xAF)) {
			xAF();
		}
		else if (equals(opCode, 0xB0)) {
			xB0();
		}
		else if (equals(opCode, 0xB1)) {
			xB1();
		}
		else if (equals(opCode, 0xB2)) {
			xB2();
		}
		else if (equals(opCode, 0xB3)) {
			xB3();
		}
		else if (equals(opCode, 0xB4)) {
			xB4();
		}
		else if (equals(opCode, 0xB5)) {
			xB5();
		}
		else if (equals(opCode, 0xB6)) {
			xB6();
		}
		else if (equals(opCode, 0xB7)) {
			xB7();
		}
		else if (equals(opCode, 0xB8)) {
			xB8();
		}
		else if (equals(opCode, 0xB9)) {
			xB9();
		}
		else if (equals(opCode, 0xBA)) {
			xBA();
		}
		else if (equals(opCode, 0xBB)) {
			xBB();
		}
		else if (equals(opCode, 0xBC)) {
			xBC();
		}
		else if (equals(opCode, 0xBD)) {
			xBD();
		}
		else if (equals(opCode, 0xBE)) {
			xBE();
		}
		else if (equals(opCode, 0xBF)) {
			xBF();
		}
		else if (equals(opCode, 0xC0)) {
			xC0();
		}
		else if (equals(opCode, 0xC1)) {
			xC1();
		}
		else if (equals(opCode, 0xC2)) {
			xC2();
		}
		else if (equals(opCode, 0xC3)) {
			xC3();
		}
		else if (equals(opCode, 0xC4)) {
			xC4();
		}
		else if (equals(opCode, 0xC5)) {
			xC5();
		}
		else if (equals(opCode, 0xC6)) {
			xC6();
		}
		else if (equals(opCode, 0xC7)) {
			xC7();
		}
		else if (equals(opCode, 0xC8)) {
			xC8();
		}
		else if (equals(opCode, 0xC9)) {
			xC9();
		}
		else if (equals(opCode, 0xCA)) {
			xCA();
		}
		else if (equals(opCode, 0xCC)) {
			xCC();
		}
		else if (equals(opCode, 0xCD)) {
			xCD();
		}
		else if (equals(opCode, 0xCE)) {
			xCE();
		}
		else if (equals(opCode, 0xCF)) {
			xCF();
		}
		else if (equals(opCode, 0xD0)) {
			xD0();
		}
		else if (equals(opCode, 0xD1)) {
			xD1();
		}
		else if (equals(opCode, 0xD2)) {
			xD2();
		}
		else if (equals(opCode, 0xD4)) {
			xD4();
		}
		else if (equals(opCode, 0xD5)) {
			xD5();
		}
		else if (equals(opCode, 0xD6)) {
			xD6();
		}
		else if (equals(opCode, 0xD7)) {
			xD7();
		}
		else if (equals(opCode, 0xD8)) {
			xD8();
		}
		else if (equals(opCode, 0xD9)) {
			xD9();
		}
		else if (equals(opCode, 0xDA)) {
			xDA();
		}
		else if (equals(opCode, 0xDC)) {
			xDC();
		}
		else if (equals(opCode, 0xDE)) {
			xDE();
		}
		else if (equals(opCode, 0xDF)) {
			xDF();
		}
		else if (equals(opCode, 0xE0)) {
			xE0();
		}
		else if (equals(opCode, 0xE1)) {
			xE1();
		}
		else if (equals(opCode, 0xE2)) {
			xE2();
		}
		else if (equals(opCode, 0xE5)) {
			xE5();
		}
		else if (equals(opCode, 0xE6)) {
			xE6();
		}
		else if (equals(opCode, 0xE7)) {
			xE7();
		}
		else if (equals(opCode, 0xE8)) {
			xE8();
		}
		else if (equals(opCode, 0xE9)) {
			xE9();
		}
		else if (equals(opCode, 0xEA)) {
			xEA();
		}
		else if (equals(opCode, 0xEE)) {
			xEE();
		}
		else if (equals(opCode, 0xEF)) {
			xEF();
		}
		else if (equals(opCode, 0xF0)) {
			xF0();
		}
		else if (equals(opCode, 0xF1)) {
			xF1();
		}
		else if (equals(opCode, 0xF2)) {
			xF2();
		}
		else if (equals(opCode, 0xF3)) {
			xF3();
		}
		else if (equals(opCode, 0xF5)) {
			xF5();
		}
		else if (equals(opCode, 0xF6)) {
			xF6();
		}
		else if (equals(opCode, 0xF7)) {
			xF7();
		}
		else if (equals(opCode, 0xF8)) {
			xF8();
		}
		else if (equals(opCode, 0xF9)) {
			xF9();
		}
		else if (equals(opCode, 0xFA)) {
			xFA();
		}
		else if (equals(opCode, 0xFB)) {
			xFB();
		}
		else if (equals(opCode, 0xFE)) {
			xFE();
		}
		else if (equals(opCode, 0xFF)) {
			xFF();
		}

		else {
			System.out.println("op code not found: " + opCode);
		}

	}

	private void executeCB(byte opCode) {
		System.out.println("\nExecuting Opcode : 0xCB" + Integer.toHexString(toUnsigned(opCode)));

		if (equals(opCode, 0x00)) {
			System.out.println("AYYYY");
			xCB00();
		}
		else if (equals(opCode, 0x01)) {
			xCB01();
		}
		else if (equals(opCode, 0x02)) {
			xCB02();
		}
		else if (equals(opCode, 0x03)) {
			xCB03();
		}
		else if (equals(opCode, 0x04)) {
			xCB04();
		}
		else if (equals(opCode, 0x05)) {
			xCB05();
		}
		else if (equals(opCode, 0x06)) {
			xCB06();
		}
		else if (equals(opCode, 0x07)) {
			xCB07();
		}
		else if (equals(opCode, 0x08)) {
			xCB08();
		}
		else if (equals(opCode, 0x09)) {
			xCB09();
		}
		else if (equals(opCode, 0x0A)) {
			xCB0A();
		}
		else if (equals(opCode, 0x0B)) {
			xCB0B();
		}
		else if (equals(opCode, 0x0C)) {
			xCB0C();
		}
		else if (equals(opCode, 0x0D)) {
			xCB0D();
		}
		else if (equals(opCode, 0x0E)) {
			xCB0E();
		}
		else if (equals(opCode, 0x0F)) {
			xCB0F();
		}
		else if (equals(opCode, 0x10)) {
			xCB10();
		}
		else if (equals(opCode, 0x11)) {
			xCB11();
		}
		else if (equals(opCode, 0x12)) {
			xCB12();
		}
		else if (equals(opCode, 0x13)) {
			xCB13();
		}
		else if (equals(opCode, 0x14)) {
			xCB14();
		}
		else if (equals(opCode, 0x15)) {
			xCB15();
		}
		else if (equals(opCode, 0x16)) {
			xCB16();
		}
		else if (equals(opCode, 0x17)) {
			xCB17();
		}
		else if (equals(opCode, 0x18)) {
			xCB18();
		}
		else if (equals(opCode, 0x19)) {
			xCB19();
		}
		else if (equals(opCode, 0x1A)) {
			xCB1A();
		}
		else if (equals(opCode, 0x1B)) {
			xCB1B();
		}
		else if (equals(opCode, 0x1C)) {
			xCB1C();
		}
		else if (equals(opCode, 0x1D)) {
			xCB1D();
		}
		else if (equals(opCode, 0x1E)) {
			xCB1E();
		}
		else if (equals(opCode, 0x1F)) {
			xCB1F();
		}
		else if (equals(opCode, 0x20)) {
			xCB20();
		}
		else if (equals(opCode, 0x21)) {
			xCB21();
		}
		else if (equals(opCode, 0x22)) {
			xCB22();
		}
		else if (equals(opCode, 0x23)) {
			xCB23();
		}
		else if (equals(opCode, 0x24)) {
			xCB24();
		}
		else if (equals(opCode, 0x25)) {
			xCB25();
		}
		else if (equals(opCode, 0x26)) {
			xCB26();
		}
		else if (equals(opCode, 0x27)) {
			xCB27();
		}
		else if (equals(opCode, 0x28)) {
			xCB28();
		}
		else if (equals(opCode, 0x29)) {
			xCB29();
		}
		else if (equals(opCode, 0x2A)) {
			xCB2A();
		}
		else if (equals(opCode, 0x2B)) {
			xCB2B();
		}
		else if (equals(opCode, 0x2C)) {
			xCB2C();
		}
		else if (equals(opCode, 0x2D)) {
			xCB2D();
		}
		else if (equals(opCode, 0x2E)) {
			xCB2E();
		}
		else if (equals(opCode, 0x2F)) {
			xCB2F();
		}
		else if (equals(opCode, 0x30)) {
			xCB30();
		}
		else if (equals(opCode, 0x31)) {
			xCB31();
		}
		else if (equals(opCode, 0x32)) {
			xCB32();
		}
		else if (equals(opCode, 0x33)) {
			xCB33();
		}
		else if (equals(opCode, 0x34)) {
			xCB34();
		}
		else if (equals(opCode, 0x35)) {
			xCB35();
		}
		else if (equals(opCode, 0x36)) {
			xCB36();
		}
		else if (equals(opCode, 0x37)) {
			xCB37();
		}
		else if (equals(opCode, 0x38)) {
			xCB38();
		}
		else if (equals(opCode, 0x39)) {
			xCB39();
		}
		else if (equals(opCode, 0x3A)) {
			xCB3A();
		}
		else if (equals(opCode, 0x3B)) {
			xCB3B();
		}
		else if (equals(opCode, 0x3C)) {
			xCB3C();
		}
		else if (equals(opCode, 0x3D)) {
			xCB3D();
		}
		else if (equals(opCode, 0x3E)) {
			xCB3E();
		}
		else if (equals(opCode, 0x3F)) {
			xCB3F();
		}
		else if (equals(opCode, 0x40)) {
			xCB40();
		}
		else if (equals(opCode, 0x41)) {
			xCB41();
		}
		else if (equals(opCode, 0x42)) {
			xCB42();
		}
		else if (equals(opCode, 0x43)) {
			xCB43();
		}
		else if (equals(opCode, 0x44)) {
			xCB44();
		}
		else if (equals(opCode, 0x45)) {
			xCB45();
		}
		else if (equals(opCode, 0x46)) {
			xCB46();
		}
		else if (equals(opCode, 0x47)) {
			xCB47();
		}
		else if (equals(opCode, 0x48)) {
			xCB48();
		}
		else if (equals(opCode, 0x49)) {
			xCB49();
		}
		else if (equals(opCode, 0x4A)) {
			xCB4A();
		}
		else if (equals(opCode, 0x4B)) {
			xCB4B();
		}
		else if (equals(opCode, 0x4C)) {
			xCB4C();
		}
		else if (equals(opCode, 0x4D)) {
			xCB4D();
		}
		else if (equals(opCode, 0x4E)) {
			xCB4E();
		}
		else if (equals(opCode, 0x4F)) {
			xCB4F();
		}
		else if (equals(opCode, 0x50)) {
			xCB50();
		}
		else if (equals(opCode, 0x51)) {
			xCB51();
		}
		else if (equals(opCode, 0x52)) {
			xCB52();
		}
		else if (equals(opCode, 0x53)) {
			xCB53();
		}
		else if (equals(opCode, 0x54)) {
			xCB54();
		}
		else if (equals(opCode, 0x55)) {
			xCB55();
		}
		else if (equals(opCode, 0x56)) {
			xCB56();
		}
		else if (equals(opCode, 0x57)) {
			xCB57();
		}
		else if (equals(opCode, 0x58)) {
			xCB58();
		}
		else if (equals(opCode, 0x59)) {
			xCB59();
		}
		else if (equals(opCode, 0x5A)) {
			xCB5A();
		}
		else if (equals(opCode, 0x5B)) {
			xCB5B();
		}
		else if (equals(opCode, 0x5C)) {
			xCB5C();
		}
		else if (equals(opCode, 0x5D)) {
			xCB5D();
		}
		else if (equals(opCode, 0x5E)) {
			xCB5E();
		}
		else if (equals(opCode, 0x5F)) {
			xCB5F();
		}
		else if (equals(opCode, 0x60)) {
			xCB60();
		}
		else if (equals(opCode, 0x61)) {
			xCB61();
		}
		else if (equals(opCode, 0x62)) {
			xCB62();
		}
		else if (equals(opCode, 0x63)) {
			xCB63();
		}
		else if (equals(opCode, 0x64)) {
			xCB64();
		}
		else if (equals(opCode, 0x65)) {
			xCB65();
		}
		else if (equals(opCode, 0x66)) {
			xCB66();
		}
		else if (equals(opCode, 0x67)) {
			xCB67();
		}
		else if (equals(opCode, 0x68)) {
			xCB68();
		}
		else if (equals(opCode, 0x69)) {
			xCB69();
		}
		else if (equals(opCode, 0x6A)) {
			xCB6A();
		}
		else if (equals(opCode, 0x6B)) {
			xCB6B();
		}
		else if (equals(opCode, 0x6C)) {
			xCB6C();
		}
		else if (equals(opCode, 0x6D)) {
			xCB6D();
		}
		else if (equals(opCode, 0x6E)) {
			xCB6E();
		}
		else if (equals(opCode, 0x6F)) {
			xCB6F();
		}
		else if (equals(opCode, 0x70)) {
			xCB70();
		}
		else if (equals(opCode, 0x71)) {
			xCB71();
		}
		else if (equals(opCode, 0x72)) {
			xCB72();
		}
		else if (equals(opCode, 0x73)) {
			xCB73();
		}
		else if (equals(opCode, 0x74)) {
			xCB74();
		}
		else if (equals(opCode, 0x75)) {
			xCB75();
		}
		else if (equals(opCode, 0x76)) {
			xCB76();
		}
		else if (equals(opCode, 0x77)) {
			xCB77();
		}
		else if (equals(opCode, 0x78)) {
			xCB78();
		}
		else if (equals(opCode, 0x79)) {
			xCB79();
		}
		else if (equals(opCode, 0x7A)) {
			xCB7A();
		}
		else if (equals(opCode, 0x7B)) {
			xCB7B();
		}
		else if (equals(opCode, 0x7C)) {
			xCB7C();
		}
		else if (equals(opCode, 0x7D)) {
			xCB7D();
		}
		else if (equals(opCode, 0x7E)) {
			xCB7E();
		}
		else if (equals(opCode, 0x7F)) {
			xCB7F();
		}
		else if (equals(opCode, 0x80)) {
			xCB80();
		}
		else if (equals(opCode, 0x81)) {
			xCB81();
		}
		else if (equals(opCode, 0x82)) {
			xCB82();
		}
		else if (equals(opCode, 0x83)) {
			xCB83();
		}
		else if (equals(opCode, 0x84)) {
			xCB84();
		}
		else if (equals(opCode, 0x85)) {
			xCB85();
		}
		else if (equals(opCode, 0x86)) {
			xCB86();
		}
		else if (equals(opCode, 0x87)) {
			xCB87();
		}
		else if (equals(opCode, 0x88)) {
			xCB88();
		}
		else if (equals(opCode, 0x89)) {
			xCB89();
		}
		else if (equals(opCode, 0x8A)) {
			xCB8A();
		}
		else if (equals(opCode, 0x8B)) {
			xCB8B();
		}
		else if (equals(opCode, 0x8C)) {
			xCB8C();
		}
		else if (equals(opCode, 0x8D)) {
			xCB8D();
		}
		else if (equals(opCode, 0x8E)) {
			xCB8E();
		}
		else if (equals(opCode, 0x8F)) {
			xCB8F();
		}
		else if (equals(opCode, 0x90)) {
			xCB90();
		}
		else if (equals(opCode, 0x91)) {
			xCB91();
		}
		else if (equals(opCode, 0x92)) {
			xCB92();
		}
		else if (equals(opCode, 0x93)) {
			xCB93();
		}
		else if (equals(opCode, 0x94)) {
			xCB94();
		}
		else if (equals(opCode, 0x95)) {
			xCB95();
		}
		else if (equals(opCode, 0x96)) {
			xCB96();
		}
		else if (equals(opCode, 0x97)) {
			xCB97();
		}
		else if (equals(opCode, 0x98)) {
			xCB98();
		}
		else if (equals(opCode, 0x99)) {
			xCB99();
		}
		else if (equals(opCode, 0x9A)) {
			xCB9A();
		}
		else if (equals(opCode, 0x9B)) {
			xCB9B();
		}
		else if (equals(opCode, 0x9C)) {
			xCB9C();
		}
		else if (equals(opCode, 0x9D)) {
			xCB9D();
		}
		else if (equals(opCode, 0x9E)) {
			xCB9E();
		}
		else if (equals(opCode, 0x9F)) {
			xCB9F();
		}
		else if (equals(opCode, 0xA0)) {
			xCBA0();
		}
		else if (equals(opCode, 0xA1)) {
			xCBA1();
		}
		else if (equals(opCode, 0xA2)) {
			xCBA2();
		}
		else if (equals(opCode, 0xA3)) {
			xCBA3();
		}
		else if (equals(opCode, 0xA4)) {
			xCBA4();
		}
		else if (equals(opCode, 0xA5)) {
			xCBA5();
		}
		else if (equals(opCode, 0xA6)) {
			xCBA6();
		}
		else if (equals(opCode, 0xA7)) {
			xCBA7();
		}
		else if (equals(opCode, 0xA8)) {
			xCBA8();
		}
		else if (equals(opCode, 0xA9)) {
			xCBA9();
		}
		else if (equals(opCode, 0xAA)) {
			xCBAA();
		}
		else if (equals(opCode, 0xAB)) {
			xCBAB();
		}
		else if (equals(opCode, 0xAC)) {
			xCBAC();
		}
		else if (equals(opCode, 0xAD)) {
			xCBAD();
		}
		else if (equals(opCode, 0xAE)) {
			xCBAE();
		}
		else if (equals(opCode, 0xAF)) {
			xCBAF();
		}
		else if (equals(opCode, 0xB0)) {
			xCBB0();
		}
		else if (equals(opCode, 0xB1)) {
			xCBB1();
		}
		else if (equals(opCode, 0xB2)) {
			xCBB2();
		}
		else if (equals(opCode, 0xB3)) {
			xCBB3();
		}
		else if (equals(opCode, 0xB4)) {
			xCBB4();
		}
		else if (equals(opCode, 0xB5)) {
			xCBB5();
		}
		else if (equals(opCode, 0xB6)) {
			xCBB6();
		}
		else if (equals(opCode, 0xB7)) {
			xCBB7();
		}
		else if (equals(opCode, 0xB8)) {
			xCBB8();
		}
		else if (equals(opCode, 0xB9)) {
			xCBB9();
		}
		else if (equals(opCode, 0xBA)) {
			xCBBA();
		}
		else if (equals(opCode, 0xBB)) {
			xCBBB();
		}
		else if (equals(opCode, 0xBC)) {
			xCBBC();
		}
		else if (equals(opCode, 0xBD)) {
			xCBBD();
		}
		else if (equals(opCode, 0xBE)) {
			xCBBE();
		}
		else if (equals(opCode, 0xBF)) {
			xCBBF();
		}
		else if (equals(opCode, 0xC0)) {
			xCBC0();
		}
		else if (equals(opCode, 0xC1)) {
			xCBC1();
		}
		else if (equals(opCode, 0xC2)) {
			xCBC2();
		}
		else if (equals(opCode, 0xC3)) {
			xCBC3();
		}
		else if (equals(opCode, 0xC4)) {
			xCBC4();
		}
		else if (equals(opCode, 0xC5)) {
			xCBC5();
		}
		else if (equals(opCode, 0xC6)) {
			xCBC6();
		}
		else if (equals(opCode, 0xC7)) {
			xCBC7();
		}
		else if (equals(opCode, 0xC8)) {
			xCBC8();
		}
		else if (equals(opCode, 0xC9)) {
			xCBC9();
		}
		else if (equals(opCode, 0xCA)) {
			xCBCA();
		}
		else if (equals(opCode, 0xCB)) {
			xCBCB();
		}
		else if (equals(opCode, 0xCC)) {
			xCBCC();
		}
		else if (equals(opCode, 0xCD)) {
			xCBCD();
		}
		else if (equals(opCode, 0xCE)) {
			xCBCE();
		}
		else if (equals(opCode, 0xCF)) {
			xCBCF();
		}
		else if (equals(opCode, 0xD0)) {
			xCBD0();
		}
		else if (equals(opCode, 0xD1)) {
			xCBD1();
		}
		else if (equals(opCode, 0xD2)) {
			xCBD2();
		}
		else if (equals(opCode, 0xD3)) {
			xCBD3();
		}
		else if (equals(opCode, 0xD4)) {
			xCBD4();
		}
		else if (equals(opCode, 0xD5)) {
			xCBD5();
		}
		else if (equals(opCode, 0xD6)) {
			xCBD6();
		}
		else if (equals(opCode, 0xD7)) {
			xCBD7();
		}
		else if (equals(opCode, 0xD8)) {
			xCBD8();
		}
		else if (equals(opCode, 0xD9)) {
			xCBD9();
		}
		else if (equals(opCode, 0xDA)) {
			xCBDA();
		}
		else if (equals(opCode, 0xDB)) {
			xCBDB();
		}
		else if (equals(opCode, 0xDC)) {
			xCBDC();
		}
		else if (equals(opCode, 0xDD)) {
			xCBDD();
		}
		else if (equals(opCode, 0xDE)) {
			xCBDE();
		}
		else if (equals(opCode, 0xDF)) {
			xCBDF();
		}
		else if (equals(opCode, 0xE0)) {
			xCBE0();
		}
		else if (equals(opCode, 0xE1)) {
			xCBE1();
		}
		else if (equals(opCode, 0xE2)) {
			xCBE2();
		}
		else if (equals(opCode, 0xE3)) {
			xCBE3();
		}
		else if (equals(opCode, 0xE4)) {
			xCBE4();
		}
		else if (equals(opCode, 0xE5)) {
			xCBE5();
		}
		else if (equals(opCode, 0xE6)) {
			xCBE6();
		}
		else if (equals(opCode, 0xE7)) {
			xCBE7();
		}
		else if (equals(opCode, 0xE8)) {
			xCBE8();
		}
		else if (equals(opCode, 0xE9)) {
			xCBE9();
		}
		else if (equals(opCode, 0xEA)) {
			xCBEA();
		}
		else if (equals(opCode, 0xEB)) {
			xCBEB();
		}
		else if (equals(opCode, 0xEC)) {
			xCBEC();
		}
		else if (equals(opCode, 0xED)) {
			xCBED();
		}
		else if (equals(opCode, 0xEE)) {
			xCBEE();
		}
		else if (equals(opCode, 0xEF)) {
			xCBEF();
		}
		else if (equals(opCode, 0xF0)) {
			xCBF0();
		}
		else if (equals(opCode, 0xF1)) {
			xCBF1();
		}
		else if (equals(opCode, 0xF2)) {
			xCBF2();
		}
		else if (equals(opCode, 0xF3)) {
			xCBF3();
		}
		else if (equals(opCode, 0xF4)) {
			xCBF4();
		}
		else if (equals(opCode, 0xF5)) {
			xCBF5();
		}
		else if (equals(opCode, 0xF6)) {
			xCBF6();
		}
		else if (equals(opCode, 0xF7)) {
			xCBF7();
		}
		else if (equals(opCode, 0xF8)) {
			xCBF8();
		}
		else if (equals(opCode, 0xF9)) {
			xCBF9();
		}
		else if (equals(opCode, 0xFA)) {
			xCBFA();
		}
		else if (equals(opCode, 0xFB)) {
			xCBFB();
		}
		else if (equals(opCode, 0xFC)) {
			xCBFC();
		}
		else if (equals(opCode, 0xFD)) {
			xCBFD();
		}
		else if (equals(opCode, 0xFE)) {
			xCBFE();
		}
		else if (equals(opCode, 0xFF)) {
			xCBFF();
		}
		else {
			System.out.println("op code not found: CB" + opCode);
		}
	}

	private void setClockRegisters(byte newT, byte newM) {
		tClockRegister = newT;
		mClockRegister = newM;
	}

	private void setClockRegisters(int newT) {
		setClockRegisters((byte) newT, (byte) (newT / 4));
	}
	/*
	 * TODO check if these are identical to other copy
	 * 
	 * private byte eightBitAddHelper(byte byteToAdd, boolean withCarry) {
	 * //TODO Check/ int temp = toUnsigned(A) + toUnsigned(byteToAdd); if
	 * (withCarry && getCarryFlag()) { temp += 1; } boolean setCarry = (temp >
	 * 255);
	 * 
	 * temp = temp & 0xFF; boolean setZero = (temp == 0); boolean setHalfCarry =
	 * (((temp ^ byteToAdd ^ A) & 0x10) > 0); boolean setSubtraction = false;
	 * 
	 * setFlags(setZero, setSubtraction, setHalfCarry, setCarry); return ((byte)
	 * temp); }
	 * 
	 * private byte eightBitAddHelper(byte byteToAdd) { return
	 * eightBitAddHelper(byteToAdd, false); }
	 * 
	 * private byte eightBitSubHelper(byte byteToSub, boolean withCarry) {
	 * //TODO Check int temp = toUnsigned(A) - toUnsigned(byteToSub); if
	 * (withCarry && getCarryFlag()) { temp -= 1; }
	 * 
	 * boolean setCarry = (temp < 0);
	 * 
	 * temp = temp & 0xFF; boolean setZero = (temp == 0); boolean setHalfCarry =
	 * (((temp ^ byteToSub ^ A) & 0x10) > 0); boolean setSubtraction = true;
	 * 
	 * setFlags(setZero, setSubtraction, setHalfCarry, setCarry); return ((byte)
	 * temp); }
	 * 
	 * private byte eightBitSubHelper(byte byteToSub) { return
	 * eightBitSubHelper(byteToSub, false); }
	 */

	////////////////////////////// 3.3.1 BEGIN /////////////////////////////
	/* 3.3.1 - 1 LD Register 8BitImmediate BEGIN */

	// TODO CHECK 3.3.1 - 1
	// TODO CHECK 3.3.1 - 2
	// TODO CHECK 3.3.1 - 3
	// TODO CHECK 3.3.1 - 4
	// TODO CHECK 3.3.1 - 5
	// TODO CHECK 3.3.1 - 6
	// TODO CHECK 3.3.1 - 7 / 8 / 9
	// TODO CHECK 3.3.1 - 10 / 11 / 12
	// TODO CHECK 3.3.1 - 13 / 14 / 15
	// TODO CHECK 3.3.1 - 16 / 17 / 18
	// TODO CHECK 3.3.1 - 19
	// TODO CHECK 3.3.1 - 20

	////////////////////////////// 3.3.1 BEGIN /////////////////////////////
	/* 3.3.1 - 1 LD Register 8BitImmediate BEGIN */

	private void x3E() {
		/* MNEMONIC: LD A n 
		   Loads 8 bit value into register A
		   OPCODE: 0x3E */
		A = memory.readByte(programCounter);
		programCounter++;

		setClockRegisters(8);
	}

	private void x06() {
		/* MNEMONIC: LD B n 
		   Loads 8 bit value into register B
		   OPCODE: 0x06 */
		B = memory.readByte(programCounter);
		programCounter++;

		setClockRegisters(8);
	}

	private void x0E() {
		/* MNEMONIC: LD C n 
		   Loads 8 bit value into register C
		   OPCODE: 0x0E */
		C = memory.readByte(programCounter);
		programCounter++;

		setClockRegisters(8);
	}

	private void x16() {
		/* MNEMONIC: LD D n 
		   Loads 8 bit value into register D
		   OPCODE: 0x16 */
		D = memory.readByte(programCounter);
		programCounter++;

		setClockRegisters(8);
	}

	private void x1E() {
		/* MNEMONIC: LD E n 
		   Loads 8 bit value into register E
		   OPCODE: 0x1E */
		E = memory.readByte(programCounter);
		programCounter++;

		setClockRegisters(8);
	}

	private void x26() {
		/* MNEMONIC: LD H n 
		   Loads 8 bit value into register H
		   OPCODE: 0x26 */
		H = memory.readByte(programCounter);
		programCounter++;

		setClockRegisters(8);
	}

	private void x2E() {
		/* MNEMONIC: LD L n 
		   Loads 8 bit value into register L
		   OPCODE: 0x2E */
		L = memory.readByte(programCounter);
		programCounter++;

		setClockRegisters(8);
	}

	/* 3.3.1 - 1 LD Register 8BitImmediate END */

	/* 3.3.1 - 2 LD Register1 Register2 BEGIN */

	/* Load to Register A */
	private void x7F() {
		/* MNEMONIC: LD A, A
		   Loads register A into register A
		   OPCODE: 0x7F */
		A = A;
		setClockRegisters(4);
	}

	private void x78() {
		/* MNEMONIC: LD A, B
		   Loads register B into register A
		   OPCODE: 0x78 */
		A = B;
		setClockRegisters(4);
	}

	private void x79() {
		/* MNEMONIC: LD A, C
		   Loads register C into register A
		   OPCODE: 0x79 */
		A = C;
		setClockRegisters(4);
	}

	private void x7A() {
		/* MNEMONIC: LD A, D
		   Loads register D into register A
		   OPCODE: 0x7A */
		A = D;
		setClockRegisters(4);
	}

	private void x7B() {
		/* MNEMONIC: LD A, E
		   Loads register E into register A
		   OPCODE: 0x7B */
		A = E;
		setClockRegisters(4);
	}

	private void x7C() {
		/* MNEMONIC: LD A, H
		   Loads register H into register A
		   OPCODE: 0x7C */
		A = H;
		setClockRegisters(4);
	}

	private void x7D() {
		/* MNEMONIC: LD A, L
		   Loads register L into register A
		   OPCODE: 0x7D */
		A = L;
		setClockRegisters(4);
	}

	private void x7E() {
		/* MNEMONIC: LD A,(HL)
		   Loads value from RAM(register HL) into register A
		   OPCODE: 0x7E */
		int address = getHL();
		A = memory.readByte(address);
		setClockRegisters(8);
	}

	/* Load to Register B */
	private void x40() {
		/* MNEMONIC: LD B, B
		   Loads register B into register B
		   OPCODE: 0x40 */
		B = B;
		setClockRegisters(4);
	}

	private void x41() {
		/* MNEMONIC: LD B, C
		   Loads register C into register B
		   OPCODE: 0x41 */
		B = C;
		setClockRegisters(4);
	}

	private void x42() {
		/* MNEMONIC: LD B, D
		   Loads register D into register B
		   OPCODE: 0x42 */
		B = D;
		setClockRegisters(4);
	}

	private void x43() {
		/* MNEMONIC: LD B, E
		   Loads register E into register B
		   OPCODE: 0x43 */
		B = E;
		setClockRegisters(4);
	}

	private void x44() {
		/* MNEMONIC: LD B, H
		   Loads register H into register B
		   OPCODE: 0x44 */
		B = H;
		setClockRegisters(4);
	}

	private void x45() {
		/* MNEMONIC: LD B, L
		   Loads register L into register B
		   OPCODE: 0x45 */
		B = L;
		setClockRegisters(4);
	}

	private void x46() {
		/* MNEMONIC: LD B,(HL)
		   Loads value from RAM(register HL) into register B
		   OPCODE: 0x46 */
		int address = getHL();
		B = memory.readByte(address);
		setClockRegisters(8);
	}

	private void x47() {
		/* MNEMONIC: LD B, A
		   Loads register A into register B
		   OPCODE: 0x47 */
		B = A;
		setClockRegisters(4);
	}

	/* Load to Register C */
	private void x48() {
		/* MNEMONIC: LD C, B
		   Loads register B into register C
		   OPCODE: 0x48 */
		C = B;
		setClockRegisters(4);
	}

	private void x49() {
		/* MNEMONIC: LD C, C
		   Loads register C into register C
		   OPCODE: 0x49 */
		C = C;
		setClockRegisters(4);
	}

	private void x4A() {
		/* MNEMONIC: LD C, D
		   Loads register D into register C
		   OPCODE: 0x4A */
		C = D;
		setClockRegisters(4);
	}

	private void x4B() {
		/* MNEMONIC: LD C, E
		   Loads register E into register C
		   OPCODE: 0x4B */
		C = E;
		setClockRegisters(4);
	}

	private void x4C() {
		/* MNEMONIC: LD C, H
		   Loads register H into register C
		   OPCODE: 0x4C */
		C = H;
		setClockRegisters(4);
	}

	private void x4D() {
		/* MNEMONIC: LD C, L
		   Loads register L into register C
		   OPCODE: 0x4D */
		C = L;
		setClockRegisters(4);
	}

	private void x4E() {
		/* MNEMONIC: LD C,(HL)
		   Loads value from RAM(register HL) into register C
		   OPCODE: 0x4E */
		int address = getHL();
		C = memory.readByte(address);
		setClockRegisters(8);
	}

	private void x4F() {
		/* MNEMONIC: LD C, H
		   Loads register A into register C
		   OPCODE: 0x4F */
		C = A;
		setClockRegisters(4);
	}

	/* Load to Register D */
	private void x50() {
		/* MNEMONIC: LD D, B
		   Loads register B into register D
		   OPCODE: 0x50 */
		D = B;
		setClockRegisters(4);
	}

	private void x51() {
		/* MNEMONIC: LD D, C
		   Loads register C into register D
		   OPCODE: 0x51 */
		D = C;
		setClockRegisters(4);
	}

	private void x52() {
		/* MNEMONIC: LD D, D
		   Loads register D into register D
		   OPCODE: 0x52 */
		D = D;
		setClockRegisters(4);
	}

	private void x53() {
		/* MNEMONIC: LD D, E
		   Loads register E into register D
		   OPCODE: 0x53 */
		D = E;
		setClockRegisters(4);
	}

	private void x54() {
		/* MNEMONIC: LD D, H
		   Loads register H into register D
		   OPCODE: 0x54 */
		D = H;
		setClockRegisters(4);
	}

	private void x55() {
		/* MNEMONIC: LD D, L
		   Loads register L into register D
		   OPCODE: 0x55 */
		D = L;
		setClockRegisters(4);
	}

	private void x56() {
		/* MNEMONIC: LD D,(HL)
		   Loads value from RAM(register HL) into register D
		   OPCODE: 0x56 */
		int address = getHL();
		D = memory.readByte(address);
		setClockRegisters(8);
	}

	private void x57() {
		/* MNEMONIC: LD D, A
		   Loads register A into register D
		   OPCODE: 0x57 */
		D = A;
		setClockRegisters(4);
	}

	/* Load to Register E */
	private void x58() {
		/* MNEMONIC: LD E, B
		   Loads register B into register E
		   OPCODE: 0x58 */
		E = B;
		setClockRegisters(4);
	}

	private void x59() {
		/* MNEMONIC: LD E, C
		   Loads register C into register E
		   OPCODE: 0x59 */
		E = C;
		setClockRegisters(4);
	}

	private void x5A() {
		/* MNEMONIC: LD E, D
		   Loads register D into register E
		   OPCODE: 0x5A */
		E = D;
		setClockRegisters(4);
	}

	private void x5B() {
		/* MNEMONIC: LD E, E
		   Loads register E into register E
		   OPCODE: 0x5B */
		E = E;
		setClockRegisters(4);
	}

	private void x5C() {
		/* MNEMONIC: LD E, H
		   Loads register H into register E
		   OPCODE: 0x5C */
		E = H;
		setClockRegisters(4);
	}

	private void x5D() {
		/* MNEMONIC: LD E, L
		   Loads register L into register E
		   OPCODE: 0x5D */
		E = L;
		setClockRegisters(4);
	}

	private void x5E() {
		/* MNEMONIC: LD E,(HL)
		   Loads value from RAM(register HL) into register E
		   OPCODE: 0x5E */
		int address = getHL();
		E = memory.readByte(address);
		setClockRegisters(8);
	}

	private void x5F() {
		/* MNEMONIC: LD E, A
		   Loads register A into register E
		   OPCODE: 0x5F */
		E = A;
		setClockRegisters(4);
	}

	/* Load to Register H */
	private void x60() {
		/* MNEMONIC: LD H, B
		   Loads register B into register H
		   OPCODE: 0x60 */
		H = B;
		setClockRegisters(4);
	}

	private void x61() {
		/* MNEMONIC: LD H, C
		   Loads register C into register H
		   OPCODE: 0x61 */
		H = C;
		setClockRegisters(4);
	}

	private void x62() {
		/* MNEMONIC: LD H, D
		   Loads register D into register H
		   OPCODE: 0x62 */
		H = D;
		setClockRegisters(4);
	}

	private void x63() {
		/* MNEMONIC: LD H, E
		   Loads register E into register H
		   OPCODE: 0x63 */
		H = E;
		setClockRegisters(4);
	}

	private void x64() {
		/* MNEMONIC: LD H, H
		   Loads register H into register H
		   OPCODE: 0x64 */
		H = H;
		setClockRegisters(4);
	}

	private void x65() {
		/* MNEMONIC: LD H, L
		   Loads register L into register H
		   OPCODE: 0x65 */
		H = L;
		setClockRegisters(4);
	}

	private void x66() {
		/* MNEMONIC: LD H,(HL)
		   Loads value from RAM(register HL) into register H
		   OPCODE: 0x66 */
		int address = getHL();
		H = memory.readByte(address);
		setClockRegisters(8);
	}

	private void x67() {
		/* MNEMONIC: LD H, A
		   Loads register A into register H
		   OPCODE: 0x67 */
		H = A;
		setClockRegisters(4);
	}

	/* Load to Register L */
	private void x68() {
		/* MNEMONIC: LD L, B
		   Loads register B into register L
		   OPCODE: 0x68 */
		L = B;
		setClockRegisters(4);
	}

	private void x69() {
		/* MNEMONIC: LD L, C
		   Loads register C into register L
		   OPCODE: 0x69 */
		L = C;
		setClockRegisters(4);
	}

	private void x6A() {
		/* MNEMONIC: LD L, D
		   Loads register D into register L
		   OPCODE: 0x6A */
		L = D;
		setClockRegisters(4);
	}

	private void x6B() {
		/* MNEMONIC: LD L, E
		   Loads register E into register L
		   OPCODE: 0x6B */
		L = E;
		setClockRegisters(4);
	}

	private void x6C() {
		/* MNEMONIC: LD L, H
		   Loads register H into register L
		   OPCODE: 0x6C */
		L = H;
		setClockRegisters(4);
	}

	private void x6D() {
		/* MNEMONIC: LD L, L
		   Loads register L into register L
		   OPCODE: 0x6D */
		L = L;
		setClockRegisters(4);
	}

	private void x6E() {
		/* MNEMONIC: LD L,(HL)
		   Loads value from RAM(register HL) into register L
		   OPCODE: 0x6E */
		int address = getHL();
		L = memory.readByte(address);
		setClockRegisters(8);
	}

	private void x6F() {
		/* MNEMONIC: LD L, A
		   Loads register A into register L
		   OPCODE: 0x6F */
		L = A;
		setClockRegisters(4);
	}

	/* Load to RAM(register HL)*/
	private void x70() {
		/* MNEMONIC: LD (HL), B
		   Loads value from register B into RAM(register HL)
		   OPCODE: 0x70 */
		int address = getHL();
		memory.writeByte(address, B);
		setClockRegisters(8);
	}

	private void x71() {
		/* MNEMONIC: LD (HL), C
		   Loads value from register C into RAM(register HL)
		   OPCODE: 0x71 */
		int address = getHL();
		memory.writeByte(address, C);
		setClockRegisters(8);
	}

	private void x72() {
		/* MNEMONIC: LD (HL), D
		   Loads value from register D into RAM(register HL)
		   OPCODE: 0x72 */
		int address = getHL();
		memory.writeByte(address, D);
		setClockRegisters(8);
	}

	private void x73() {
		/* MNEMONIC: LD (HL), E
		   Loads value from register E into RAM(register HL)
		   OPCODE: 0x73 */
		int address = getHL();
		memory.writeByte(address, E);
		setClockRegisters(8);
	}

	private void x74() {
		/* MNEMONIC: LD (HL), H
		   Loads value from register H into RAM(register HL)
		   OPCODE: 0x74 */
		int address = getHL();
		memory.writeByte(address, H);
		setClockRegisters(8);
	}

	private void x75() {
		/* MNEMONIC: LD (HL), L
		   Loads value from register L into RAM(register HL)
		   OPCODE: 0x75 */
		int address = getHL();
		memory.writeByte(address, L);
		setClockRegisters(8);
	}

	private void x36() {
		/* MNEMONIC: LD (HL), n
		   Loads 8 bit immediate into RAM(register HL)
		   OPCODE: 0x36 */
		int address = getHL();
		byte value = memory.readByte(programCounter);
		programCounter++;

		memory.writeByte(address, value);
		setClockRegisters(12);
	}

	/* 3.3.1 - 2 LD Register Register2 END */

	/* 3.3.1 - 3 LD A n BEGIN */
	private void x0A() {
		/* MNEMONIC: LD A,(BC)
		   Loads value from RAM(register BC) into register A
		   OPCODE: 0x0A */
		int address = getBC();
		A = memory.readByte(address);
		setClockRegisters(8);
	}

	private void x1A() {
		/* MNEMONIC: LD A,(DE)
		   Loads value from RAM(register DE) into register A
		   OPCODE: 0x1A */
		int address = getDE();
		A = memory.readByte(address);
		setClockRegisters(8);
	}

	private void xFA() {
		/* MNEMONIC: LD A,(nn)
		   Loads value from given 16bit address into register A
		   OPCODE: 0xFA */
		int address = toUnsigned(memory.readWord(programCounter));
		programCounter += 2;

		A = memory.readByte(address);
		setClockRegisters(16);
	}
	/* 3.3.1 - 3 LD A n END */

	/* 3.3.1 - 4 LD n A BEGIN */

	private void x02() {
		/* MNEMONIC: LD (BC), A
		   Loads A into RAM(register BC)
		   OPCODE: 0x02 */
		memory.writeByte(getBC(), A);
		setClockRegisters(8);
	}

	private void x12() {
		/* MNEMONIC: LD (DE), A
		   Loads A into RAM(register DE)
		   OPCODE: 0x12 */
		memory.writeByte(getDE(), A);
		setClockRegisters(8);
	}

	private void x77() {
		/* MNEMONIC: LD (HL), A
		   Loads A into RAM(register HL)
		   OPCODE: 0x77 */
		memory.writeByte(getHL(), A);
		setClockRegisters(8);
	}

	private void xEA() {
		/* MNEMONIC: LD (HL), A
		   Loads A into RAM(16 bit Immediate)
		   OPCODE: 0x77 */
		memory.writeByte(memory.readWord(programCounter), A);
		programCounter += 2;
		setClockRegisters(16);
	}

	/* 3.3.1 - 4 LD n A END */

	/* 3.3.1 - 5 LD A,(C) BEGIN */
	private void xF2() {
		/* MNEMONIC: LD A, (C)
		   Loads RAM(0xFF00 + C) into A
		   OPCODE: 0xF2 */
		A = memory.readByte(0xFF00 + toUnsigned(C));
		setClockRegisters(8);
	}
	/* 3.3.1 - 5 LD A,(C) END */

	/* 3.3.1 - 6 LD (C), A BEGIN */
	private void xE2() {
		/* MNEMONIC: LD (C), A
		   Loads A into  RAM(0xFF00 + C) 
		   OPCODE: 0xE2 */
		memory.writeByte(0xFF00 + toUnsigned(C), A);
		setClockRegisters(8);
	}
	/* 3.3.1 - 6 LD (C), A END */

	/* 3.3.1 - 7/8/9/ LD A, (HL-) BEGIN */
	private void x3A() {
		/* MNEMONIC: LDD A,(HL) / LD A,(HLD) / LD A,(HL-)
		   Loads value from RAM(register HL) into register A & decrements HL
		   OPCODE: 0x3A */
		int address = getHL();
		A = memory.readByte(address);

		//Decrement HL
		//TODO Check
		L = (byte) ((L - 1) & 0xFF);
		if ((L & 0xFF) == 0xFF) {
			H = (byte) ((H - 1) & 0xFF);
		}

		setClockRegisters(8);
	}
	/* 3.3.1 - 7/8/9/ LD A, (HL-) END */

	/* 3.3.1 - 10/11/12 LD (HL-), A BEGIN */
	private void x32() {
		/* MNEMONIC: LDD(HL), A / LD (HLD), A / LD (HL-), A
		   Loads value from register A into RAM(register HL) into  & decrements HL
		   OPCODE: 0x32 */
		int address = getHL();
		memory.writeByte(address, A);

		//Decrement HL
		//TODO Check
		L = (byte) ((L - 1) & 0xFF);
		if ((L & 0xFF) == 0xFF) {
			H = (byte) ((H - 1) & 0xFF);
		}

		setClockRegisters(8);
	}
	/* 3.3.1 - 10/11/12 LD (HL-), A END */

	/* 3.3.1 - 13/14/15 LD A, (HL+) BEGIN */
	private void x2A() {
		/* MNEMONIC: LDI A,(HL) / LD A,(HLI) / LD A,(HL+)
		   Loads value from RAM(register HL) into register A & increments HL
		   OPCODE: 0x2A */
		int address = getHL();
		A = memory.readByte(address);

		//Increment HL
		//TODO Check
		L = (byte) ((toUnsigned(L) + 1) & 0xFF);
		if (L == 0) {
			H = (byte) ((H + 1) & 0xFF);
		}

		setClockRegisters(8);
	}
	/* 3.3.1 - 13/14/15 LD A, (HL+) BEGIN */

	/* 3.3.1 - 16/17/18 LD (HL+), A BEGIN */
	private void x22() {
		/* MNEMONIC: LDI(HL), A / LD (HLI), A / LD (HL+), A
		   Loads value from register A into RAM(register HL) into  & decrements HL
		   OPCODE: 0x22 */
		int address = getHL();
		memory.writeByte(address, A);

		//Increment HL
		//TODO Check
		L = (byte) ((toUnsigned(L) + 1) & 0xFF);
		if (L == 0) {
			H = (byte) ((H + 1) & 0xFF);
		}

		setClockRegisters(8);
	}
	/* 3.3.1 - 16/17/18 LD (HL+), A END */

	/* 3.3.1 - 19 LD (n),A BEGIN */
	private void xE0() {
		/* MNEMONIC:  LD (n),A
		   Loads A into RAM(0xFF00 + n) 
		   OPCODE: 0xE0 */
		int offset = toUnsigned(memory.readByte(programCounter));
		programCounter++;

		memory.writeByte(0xFF00 + offset, A);
		setClockRegisters(12);
	}
	/* 3.3.1 - 19 LD (n),A END */

	/* 3.3.1 - 20 LD A,(n) BEGIN */
	private void xF0() {
		/* MNEMONIC:  LD A,(n)
		   Loads A into RAM(0xFF00 + n) 
		   OPCODE: 0xF0 */
		int offset = toUnsigned(memory.readByte(programCounter));
		programCounter++;

		A = memory.readByte(0xFF00 + offset);
		setClockRegisters(12);
	}
	/* 3.3.1 - 20 LD A,(n) END */

	////////////////////////////// 3.3.2  BEGIN /////////////////////////////

	/* 3.3.2 - 1 LD RG, nn BEGIN */

	private void x01() {
		/* MNEMONIC:  LD BC, nn
		   Loads 16 bit immediate into BC 
		   OPCODE: 0x01 */
		C = memory.readByte(programCounter);
		B = memory.readByte(programCounter + 1);
		programCounter += 2;

		setClockRegisters(12);
	}

	private void x11() {
		/* MNEMONIC:  LD DE, nn
		   Loads 16 bit immediate into DE
		   OPCODE: 0x11 */
		E = memory.readByte(programCounter);
		D = memory.readByte(programCounter + 1);
		programCounter += 2;

		setClockRegisters(12);
	}

	private void x21() {
		/* MNEMONIC:  LD HL, nn
		   Loads 16 bit immediate into HL
		   OPCODE: 0x21 */
		L = memory.readByte(programCounter);
		H = memory.readByte(programCounter + 1);
		programCounter += 2;

		setClockRegisters(12);
	}

	private void x31() {
		/* MNEMONIC:  LD SP, nn
		   Loads 16 bit immediate into SP
		   OPCODE: 0x31 */
		System.out.println("SP: " + stackPointer);
		stackPointer = toUnsigned(memory.readWord(programCounter));
		programCounter += 2;
		System.out.println("SP: " + stackPointer);
		setClockRegisters(12);
	}

	/* 3.3.1 - 1 LD RG, nn END */

	/* 3.3.2 - 2 LD SP, HL BEGIN */

	private void xF9() {
		/* MNEMONIC:  LD SP, HL
		   Loads HL into SP
		   OPCODE: 0xF9 */
		stackPointer = (toUnsigned(H) << 8) + (toUnsigned(L) << 0); //TODO check if need to reverse H & L

		setClockRegisters(8);
	}

	/* 3.3.2 - 2 LD SP, HL END */

	/* 3.3.2 - 3/4  LD HL,SP+n / LDHL SP,n BEGIN */
	private void xF8() {
		/* MNEMONIC:  LD SP, HL
		   Loads SP + 8 bit immediate into HL
		   OPCODE: 0xF8*/

		//TODO Set flags
		int n = toUnsigned(memory.readByte(programCounter));
		n += stackPointer;

		H = (byte) ((n >> 8) & 0xFF);
		L = (byte) (n & 0xFF);
		programCounter++;
		setClockRegisters(12);
	}

	/* 3.3.2 - 3/4  LD HL,SP+n / LDHL SP,n END */

	/* 3.3.2 - 5 LD(nn),SP BEGIN */
	private void x08() {
		/* MNEMONIC:  LD (nn), SP
		   Puts SP value into address N
		   OPCODE: 0x08*/
		//TODO check
		int address = toUnsigned(memory.readWord(programCounter));
		memory.writeWord(address, (short) (stackPointer & 0xFFFF));

		programCounter += 2;
		setClockRegisters(20);
	}

	/* 3.3.2 - 5 LD(nn),SP END */

	/* 3.3.2 - 6 PUSH nn BEGIN */
	private void xF5() {
		/* MNEMONIC: PUSH AF
		   Push AF onto stack & decrement SP twice
		   OPCODE: 0xF5*/

		stackPointer--;
		memory.writeByte(stackPointer, A);

		stackPointer--;
		memory.writeByte(stackPointer, F);

		setClockRegisters(16);
	}

	private void xC5() {
		/* MNEMONIC: PUSH BC
		   Push BC onto stack & decrement SP twice
		   OPCODE: 0xC5*/

		stackPointer--;
		memory.writeByte(stackPointer, B);

		stackPointer--;
		memory.writeByte(stackPointer, C);

		setClockRegisters(16);
	}

	private void xD5() {
		/* MNEMONIC: PUSH DE
		   Push DE onto stack & decrement SP twice
		   OPCODE: 0xD5*/

		stackPointer--;
		memory.writeByte(stackPointer, D);

		stackPointer--;
		memory.writeByte(stackPointer, E);

		setClockRegisters(16);
	}

	private void xE5() {
		/* MNEMONIC: PUSH HL
		   Push HL onto stack & decrement SP twice
		   OPCODE: 0xE5*/

		stackPointer--;
		memory.writeByte(stackPointer, H);

		stackPointer--;
		memory.writeByte(stackPointer, L);

		setClockRegisters(16);
	}

	/* 3.3.2 - 6 PUSH nn END */

	/* 3.3.2 - 7 POP nn BEGIN */
	private void xF1() {
		/* MNEMONIC: POP AF
		   Pop from stack into AF & increment SP twice
		   OPCODE: 0xF1*/

		F = memory.readByte(stackPointer);
		stackPointer++;

		A = memory.readByte(stackPointer);
		stackPointer++;

		setClockRegisters(12);
	}

	private void xC1() {
		/* MNEMONIC: POP BC
		   Pop from stack into BC & increment SP twice
		   OPCODE: 0xC1*/

		C = memory.readByte(stackPointer);
		stackPointer++;

		B = memory.readByte(stackPointer);
		stackPointer++;

		setClockRegisters(12);
	}

	private void xD1() {
		/* MNEMONIC: POP DE
		   Pop from stack into DE & increment SP twice
		   OPCODE: 0xD1*/

		E = memory.readByte(stackPointer);
		stackPointer++;

		D = memory.readByte(stackPointer);
		stackPointer++;

		setClockRegisters(12);
	}

	private void xE1() {
		/* MNEMONIC: POP HL
		   Pop from stack into HL & increment SP twice
		   OPCODE: 0xE1*/

		L = memory.readByte(stackPointer);
		stackPointer++;

		H = memory.readByte(stackPointer);
		stackPointer++;

		setClockRegisters(12);
	}
	/* 3.3.2 - 7 POP nn END */

	////////////////////////////// 3.3.1 8-bit ALU BEGIN /////////////////////////////

	/* 3.3.3 - 1 ADD A,n BEGIN */

	private void eightBitAddHelper(byte byteToAdd, boolean withCarry) {
		//TODO Check
		int temp = toUnsigned(A) + toUnsigned(byteToAdd);
		if (withCarry && getCarryFlag()) {
			temp += 1;
		}
		boolean setCarry = (temp > 255);

		temp = temp & 0xFF;
		boolean setZero = (temp == 0);
		boolean setHalfCarry = (((temp ^ byteToAdd ^ A) & 0x10) > 0);
		boolean setSubtraction = false;

		setFlags(setZero, setSubtraction, setHalfCarry, setCarry);
		A = ((byte) temp);
	}

	private void eightBitAddHelper(byte byteToAdd) {
		eightBitAddHelper(byteToAdd, false);
	}

	private void x87() {
		/* MNEMONIC: ADD A,A
		   Add A to A and sets flags
		   OPCODE: 0x87*/
		eightBitAddHelper(A);
		setClockRegisters(4);
	}

	private void x80() {
		/* MNEMONIC: ADD A,B
		   Add B to A and sets flags
		   OPCODE: 0x80*/
		eightBitAddHelper(B);
		setClockRegisters(4);
	}

	private void x81() {
		/* MNEMONIC: ADD A,C
		   Add C to A and sets flags
		   OPCODE: 0x81*/
		eightBitAddHelper(C);
		setClockRegisters(4);
	}

	private void x82() {
		/* MNEMONIC: ADD A,D
		   Add D to A and sets flags
		   OPCODE: 0x82*/
		eightBitAddHelper(D);
		setClockRegisters(4);
	}

	private void x83() {
		/* MNEMONIC: ADD A,E
		   Add E to A and sets flags
		   OPCODE: 0x83*/
		eightBitAddHelper(E);
		setClockRegisters(4);
	}

	private void x84() {
		/* MNEMONIC: ADD A,H
		   Add H to A and sets flags
		   OPCODE: 0x84*/
		eightBitAddHelper(H);
		setClockRegisters(4);
	}

	private void x85() {
		/* MNEMONIC: ADD A,L
		   Add L to A and sets flags
		   OPCODE: 0x85*/
		eightBitAddHelper(L);
		setClockRegisters(4);
	}

	private void x86() {
		/* MNEMONIC: ADD (HL)
		   Add RAM(HL) to A and sets flags
		   OPCODE: 0x86*/
		byte valueToAdd = memory.readByte(getHL());
		eightBitAddHelper(valueToAdd);
		setClockRegisters(8);
	}

	private void xC6() {
		/* MNEMONIC: ADD n
		   Add 8 bit imediate to A and sets flags
		   OPCODE: 0xC6*/
		byte valueToAdd = memory.readByte(programCounter);
		programCounter++;
		eightBitAddHelper(valueToAdd);
		setClockRegisters(8);
	}
	/* 3.3.3 - 1 ADD A,n END */

	/* 3.3.3 - 2 ADC A,n BEGIN */
	private void x8F() {
		/* MNEMONIC: ADC A,A
		   Add A + carry flag to A and sets flags
		   OPCODE: 0x8F*/
		eightBitAddHelper(A, true);
		setClockRegisters(4);
	}

	private void x88() {
		/* MNEMONIC: ADC A,B
		   Add B + carry flag to A and sets flags
		   OPCODE: 0x88*/
		eightBitAddHelper(B, true);
		setClockRegisters(4);
	}

	private void x89() {
		/* MNEMONIC: ADC A,C
		   Add C + carry flag to A and sets flags
		   OPCODE: 0x89*/
		eightBitAddHelper(C, true);
		setClockRegisters(4);
	}

	private void x8A() {
		/* MNEMONIC: ADC A,D
		   Add D + carry flag to A and sets flags
		   OPCODE: 0x8A*/
		eightBitAddHelper(D, true);
		setClockRegisters(4);
	}

	private void x8B() {
		/* MNEMONIC: ADC A,E
		   Add E + carry flag to A and sets flags
		   OPCODE: 0x8B*/
		eightBitAddHelper(E, true);
		setClockRegisters(4);
	}

	private void x8C() {
		/* MNEMONIC: ADC A,H
		   Add H + carry flag to A and sets flags
		   OPCODE: 0x8C*/
		eightBitAddHelper(H, true);
		setClockRegisters(4);
	}

	private void x8D() {
		/* MNEMONIC: ADC A,L
		   Add L + carry flag to A and sets flags
		   OPCODE: 0x8D*/
		eightBitAddHelper(L, true);
		setClockRegisters(4);
	}

	private void x8E() {
		/* MNEMONIC: ADC (HL)
		   Add RAM(HL) + carry flag to A and sets flags
		   OPCODE: 0x8E*/
		byte valueToAdd = memory.readByte(getHL());
		eightBitAddHelper(valueToAdd, true);
		setClockRegisters(8);
	}

	private void xCE() {
		/* MNEMONIC: ADC n
		   Add 8 bit imediate + carry flag to A and sets flags
		   OPCODE: 0xCE*/
		byte valueToAdd = memory.readByte(programCounter);
		programCounter++;
		eightBitAddHelper(valueToAdd, true);
		setClockRegisters(8);
	}
	/* 3.3.3 - 2 ADC A,n END */

	/* 3.3.3 - 3 SUB A,n BEGIN */

	private void eightBitSubHelper(byte byteToSub, boolean withCarry) {
		//TODO Check
		int temp = toUnsigned(A) - toUnsigned(byteToSub);
		if (withCarry && getCarryFlag()) {
			temp -= 1;
		}

		boolean setCarry = (temp < 0);

		temp = temp & 0xFF;
		boolean setZero = (temp == 0);
		boolean setHalfCarry = (((temp ^ byteToSub ^ A) & 0x10) > 0);
		boolean setSubtraction = true;

		setFlags(setZero, setSubtraction, setHalfCarry, setCarry);
		A = ((byte) temp);
	}

	private void eightBitSubHelper(byte byteToSub) {
		eightBitSubHelper(byteToSub, false);
	}

	private void x97() {
		/* MNEMONIC: SUB A,A
		   Subtracts A from A and sets flags
		   OPCODE: 0x97*/
		eightBitSubHelper(A);
		setClockRegisters(4);
	}

	private void x90() {
		/* MNEMONIC: SUB A,B
		   Subtracts B from A and sets flags
		   OPCODE: 0x90*/
		eightBitSubHelper(B);
		setClockRegisters(4);
	}

	private void x91() {
		/* MNEMONIC: SUB A,C
		   Subtracts C from A and sets flags
		   OPCODE: 0x91*/
		eightBitSubHelper(C);
		setClockRegisters(4);
	}

	private void x92() {
		/* MNEMONIC: SUB A,D
		   Subtracts D from A and sets flags
		   OPCODE: 0x92*/
		eightBitSubHelper(D);
		setClockRegisters(4);
	}

	private void x93() {
		/* MNEMONIC: SUB A,E
		   Subtracts E from A and sets flags
		   OPCODE: 0x93*/
		eightBitSubHelper(E);
		setClockRegisters(4);
	}

	private void x94() {
		/* MNEMONIC: SUB A,H
		   Subtracts H from A and sets flags
		   OPCODE: 0x94*/
		eightBitSubHelper(H);
		setClockRegisters(4);
	}

	private void x95() {
		/* MNEMONIC: SUB A,L
		   Subtracts L from A and sets flags
		   OPCODE: 0x95*/
		eightBitSubHelper(L);
		setClockRegisters(4);
	}

	private void x96() {
		/* MNEMONIC: SUB (HL)
		   Subtracts RAM(HL) from A and sets flags
		   OPCODE: 0x96*/
		byte valueToSub = memory.readByte(getHL());
		eightBitSubHelper(valueToSub);
		setClockRegisters(8);
	}

	private void xD6() {
		/* MNEMONIC: SUB n
		   Subtracts 8 bit imediate from A and sets flags
		   OPCODE: 0xD6*/
		byte valueToSub = memory.readByte(programCounter);
		programCounter++;
		eightBitSubHelper(valueToSub);
		setClockRegisters(8);
	}
	/* 3.3.3 - 3 SUB A,n END */

	/* 3.3.3 - 4 SBC A,n BEGIN */
	private void x9F() {
		/* MNEMONIC: SBC A,A
		   Subtracts A + carry flag to A and sets flags
		   OPCODE: 0x9F*/
		eightBitSubHelper(A, true);
		setClockRegisters(4);
	}

	private void x98() {
		/* MNEMONIC: SBC A,B
		   Subtracts B + carry flag to A and sets flags
		   OPCODE: 0x98*/
		eightBitSubHelper(B, true);
		setClockRegisters(4);
	}

	private void x99() {
		/* MNEMONIC: SBC A,C
		   Subtracts C + carry flag to A and sets flags
		   OPCODE: 0x99*/
		eightBitSubHelper(C, true);
		setClockRegisters(4);
	}

	private void x9A() {
		/* MNEMONIC: SBC A,D
		   Subtracts D + carry flag to A and sets flags
		   OPCODE: 0x9A*/
		eightBitSubHelper(D, true);
		setClockRegisters(4);
	}

	private void x9B() {
		/* MNEMONIC: SBC A,E
		   Subtracts E + carry flag to A and sets flags
		   OPCODE: 0x9B*/
		eightBitSubHelper(E, true);
		setClockRegisters(4);
	}

	private void x9C() {
		/* MNEMONIC: SBC A,H
		   Subtracts H + carry flag to A and sets flags
		   OPCODE: 0x9C*/
		eightBitSubHelper(H, true);
		setClockRegisters(4);
	}

	private void x9D() {
		/* MNEMONIC: SBC A,L
		   Subtracts L + carry flag to A and sets flags
		   OPCODE: 0x9D*/
		eightBitSubHelper(L, true);
		setClockRegisters(4);
	}

	private void x9E() {
		/* MNEMONIC: SBC (HL)
		   Subtracts RAM(HL) + carry flag to A and sets flags
		   OPCODE: 0x9E*/
		byte value = memory.readByte(getHL());
		eightBitSubHelper(value, true);
		setClockRegisters(8);
	}

	private void xDE() {
		/* MNEMONIC: SBC n
		   Subtracts 8 bit imediate + carry flag to A and sets flags
		   OPCODE: 0xDE*/
		byte value = memory.readByte(programCounter);
		programCounter++;
		eightBitSubHelper(value, true);
		setClockRegisters(8);
	}
	/* 3.3.3 - 4 SBC A,n END */

	/* 3.3.3 - 5 AND A,n BEGIN */

	private void eightBitAndHelper(byte valueToAnd) {
		A = (byte) (toUnsigned(A) & toUnsigned(valueToAnd));
		setFlags((A == 0), false, false, false);
	}

	private void xA7() {
		/* MNEMONIC: AND A,A
		   Logical AND A with A and stores in A
		   OPCODE: 0xA7*/
		eightBitAndHelper(A);
		setClockRegisters(4);
	}

	private void xA0() {
		/* MNEMONIC: AND A,B
		   Logical AND B with A and stores in A
		   OPCODE: 0xA0*/
		eightBitAndHelper(B);
		setClockRegisters(4);
	}

	private void xA1() {
		/* MNEMONIC: AND A,C
		   Logical AND C with A and stores in A
		   OPCODE: 0xA1*/
		eightBitAndHelper(C);
		setClockRegisters(4);
	}

	private void xA2() {
		/* MNEMONIC: AND A,D
		   Logical AND D with A and stores in A
		   OPCODE: 0xA2*/
		eightBitAndHelper(D);
		setClockRegisters(4);
	}

	private void xA3() {
		/* MNEMONIC: AND A,E
		   Logical AND E with A and stores in A
		   OPCODE: 0xA3*/
		eightBitAndHelper(E);
		setClockRegisters(4);
	}

	private void xA4() {
		/* MNEMONIC: AND A,H
		   Logical AND H with A and stores in A
		   OPCODE: 0xA4*/
		eightBitAndHelper(H);
		setClockRegisters(4);
	}

	private void xA5() {
		/* MNEMONIC: AND A,L
		   Logical AND L with A and stores in A
		   OPCODE: 0xA5*/
		eightBitAndHelper(L);
		setClockRegisters(4);
	}

	private void xA6() {
		/* MNEMONIC: AND A,(HL)
		   Logical AND RAM(HL) with A and stores in A
		   OPCODE: 0xA6*/
		byte value = memory.readByte(getHL());
		eightBitAndHelper(value);
		setClockRegisters(8);
	}

	private void xE6() {
		/* MNEMONIC: AND A,n
		   Logical AND 8 bit immediate with A and stores in A
		   OPCODE: 0xE6*/
		byte value = memory.readByte(programCounter);
		programCounter++;
		eightBitAndHelper(value);
		setClockRegisters(8);
	}

	/* 3.3.3 - 5 AND A,n END */

	/* 3.3.3 - 6 OR A,n BEGIN */

	private void eightBitOrHelper(byte valueToOr) {
		A = (byte) (toUnsigned(A) | toUnsigned(valueToOr));
		setFlags((A == 0), false, false, false);
	}

	private void xB7() {
		/* MNEMONIC: OR A,A
		   Logical OR A with A and stores in A
		   OPCODE: 0xB7*/
		eightBitOrHelper(A);
		setClockRegisters(4);
	}

	private void xB0() {
		/* MNEMONIC: OR A,B
		   Logical OR B with A and stores in A
		   OPCODE: 0xB0*/
		eightBitOrHelper(B);
		setClockRegisters(4);
	}

	private void xB1() {
		/* MNEMONIC: OR A,C
		   Logical OR C with A and stores in A
		   OPCODE: 0xB1*/
		eightBitOrHelper(C);
		setClockRegisters(4);
	}

	private void xB2() {
		/* MNEMONIC: OR A,D
		   Logical OR D with A and stores in A
		   OPCODE: 0xB2*/
		eightBitOrHelper(D);
		setClockRegisters(4);
	}

	private void xB3() {
		/* MNEMONIC: OR A,E
		   Logical OR E with A and stores in A
		   OPCODE: 0xB3*/
		eightBitOrHelper(E);
		setClockRegisters(4);
	}

	private void xB4() {
		/* MNEMONIC: OR A,H
		   Logical OR H with A and stores in A
		   OPCODE: 0xB4*/
		eightBitOrHelper(H);
		setClockRegisters(4);
	}

	private void xB5() {
		/* MNEMONIC: OR A,L
		   Logical OR L with A and stores in A
		   OPCODE: 0xB5*/
		eightBitOrHelper(L);
		setClockRegisters(4);
	}

	private void xB6() {
		/* MNEMONIC: OR A,(HL)
		   Logical OR RAM(HL) with A and stores in A
		   OPCODE: 0xB6*/
		byte value = memory.readByte(getHL());
		eightBitOrHelper(value);
		setClockRegisters(8);
	}

	private void xF6() {
		/* MNEMONIC: OR A,n
		   Logical OR 8 bit immediate with A and stores in A
		   OPCODE: 0xF6*/
		byte value = memory.readByte(programCounter);
		programCounter++;
		eightBitOrHelper(value);
		setClockRegisters(8);
	}

	/* 3.3.3 - 6 OR A,n END */

	/* 3.3.3 - 7 XOR A,n BEGIN */

	private void eightBitXorHelper(byte valueToXor) {
		A = (byte) (toUnsigned(A) ^ toUnsigned(valueToXor));
		setFlags((A == 0), false, false, false);
	}

	private void xAF() {
		/* MNEMONIC: XOR A,A
		   Logical XOR A with A and stores in A
		   OPCODE: 0xAF*/
		eightBitXorHelper(A);
		setClockRegisters(4);
	}

	private void xA8() {
		/* MNEMONIC: XOR A,B
		   Logical XOR B with A and stores in A
		   OPCODE: 0xA8*/
		eightBitXorHelper(B);
		setClockRegisters(4);
	}

	private void xA9() {
		/* MNEMONIC: XOR A,C
		   Logical XOR C with A and stores in A
		   OPCODE: 0xA9*/
		eightBitXorHelper(C);
		setClockRegisters(4);
	}

	private void xAA() {
		/* MNEMONIC: XOR A,D
		   Logical XOR D with A and stores in A
		   OPCODE: 0xAA*/
		eightBitXorHelper(D);
		setClockRegisters(4);
	}

	private void xAB() {
		/* MNEMONIC: XOR A,E
		   Logical XOR E with A and stores in A
		   OPCODE: 0xAB*/
		eightBitXorHelper(E);
		setClockRegisters(4);
	}

	private void xAC() {
		/* MNEMONIC: XOR A,H
		   Logical XOR H with A and stores in A
		   OPCODE: 0xAC*/
		eightBitXorHelper(H);
		setClockRegisters(4);
	}

	private void xAD() {
		/* MNEMONIC: XOR A,L
		   Logical XOR L with A and stores in A
		   OPCODE: 0xAD*/
		eightBitXorHelper(L);
		setClockRegisters(4);
	}

	private void xAE() {
		/* MNEMONIC: XOR A,(HL)
		   Logical XOR RAM(HL) with A and stores in A
		   OPCODE: 0xAE*/
		byte value = memory.readByte(getHL());
		eightBitXorHelper(value);
		setClockRegisters(8);
	}

	private void xEE() {
		/* MNEMONIC: XOR A,n
		   Logical XOR 8 bit immediate with A and stores in A
		   OPCODE: 0xEE*/
		byte value = memory.readByte(programCounter);
		programCounter++;
		eightBitXorHelper(value);
		setClockRegisters(8);
	}

	/* 3.3.3 - 7 XOR A,n END */

	/* 3.3.3 - 8 CP,n BEGIN */

	private void eightBitCompareHelper(byte valueToCompare) {

		int tempA = toUnsigned(A);
		tempA -= toUnsigned(valueToCompare);
		tempA = tempA & 0xFF;

		boolean setZero = (A == valueToCompare);
		boolean setSubtraction = true;
		boolean setHalfCarry = (((A ^ valueToCompare ^ tempA) & 0x10) > 0); //TODO check
		boolean setCarry = (toUnsigned(A) < toUnsigned(valueToCompare));

		setFlags(setZero, setSubtraction, setHalfCarry, setCarry);
	}

	private void xBF() {
		/* MNEMONIC: CP A
		   Compares A with A
		   OPCODE: 0xBF*/
		eightBitCompareHelper(A);
		setClockRegisters(4);
	}

	private void xB8() {
		/* MNEMONIC: CP B
		   Compares B with A
		   OPCODE: 0xB8*/
		eightBitCompareHelper(B);
		setClockRegisters(4);
	}

	private void xB9() {
		/* MNEMONIC: CP C
		   Compares C with A
		   OPCODE: 0xB9*/
		eightBitCompareHelper(C);
		setClockRegisters(4);
	}

	private void xBA() {
		/* MNEMONIC: CP D
		   Compares D with A
		   OPCODE: 0xBA*/
		eightBitCompareHelper(D);
		setClockRegisters(4);
	}

	private void xBB() {
		/* MNEMONIC: CP E
		   Compares E with A
		   OPCODE: 0xBB*/
		eightBitCompareHelper(E);
		setClockRegisters(4);
	}

	private void xBC() {
		/* MNEMONIC: CP H
		   Compares H with A
		   OPCODE: 0xBC*/
		eightBitCompareHelper(H);
		setClockRegisters(4);
	}

	private void xBD() {
		/* MNEMONIC: CP L
		   Compares L with A
		   OPCODE: 0xBD*/
		eightBitCompareHelper(L);
		setClockRegisters(4);
	}

	private void xBE() {
		/* MNEMONIC: CP (HL)
		   Compares RAM(HL) with A
		   OPCODE: 0xBE*/
		byte value = memory.readByte(getHL());
		eightBitCompareHelper(value);
		setClockRegisters(8);
	}

	private void xFE() {
		/* MNEMONIC: CP n
		   Compares 8 bit immediate with A
		   OPCODE: 0xFE*/
		byte value = memory.readByte(programCounter);
		programCounter++;
		eightBitCompareHelper(value);
		setClockRegisters(8);
	}

	/* 3.3.3 - 8 CP,n END */

	/* 3.3.3 - 9 INC, n BEGIN */

	private void x3C() {
		/* MNEMONIC: INC A
		   Increments register A and sets flags
		   OPCODE: 0x3C*/
		int sum = toUnsigned(A) + 1;
		A = (byte) (sum & 0xFF);

		setFlags(A == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x04() {
		/* MNEMONIC: INC B
		   Increments register B and sets flags
		   OPCODE: 0x04*/
		int sum = toUnsigned(B) + 1;
		B = (byte) (sum & 0xFF);

		setFlags(B == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x0C() {
		/* MNEMONIC: INC C
		   Increments register C and sets flags
		   OPCODE: 0x0C*/
		int sum = toUnsigned(C) + 1;
		C = (byte) (sum & 0xFF);

		setFlags(C == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x14() {
		/* MNEMONIC: INC D
		   Increments register D and sets flags
		   OPCODE: 0x14*/
		int sum = toUnsigned(D) + 1;
		D = (byte) (sum & 0xFF);

		setFlags(D == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x1C() {
		/* MNEMONIC: INC E
		   Increments register E and sets flags
		   OPCODE: 0x1C*/
		int sum = toUnsigned(E) + 1;
		E = (byte) (sum & 0xFF);

		setFlags(E == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x24() {
		/* MNEMONIC: INC H
		   Increments register H and sets flags
		   OPCODE: 0x24*/
		int sum = toUnsigned(H) + 1;
		H = (byte) (sum & 0xFF);

		setFlags(H == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x2C() {
		/* MNEMONIC: INC L
		   Increments register L and sets flags
		   OPCODE: 0x2C*/
		int sum = toUnsigned(L) + 1;
		L = (byte) (sum & 0xFF);

		setFlags(L == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x34() {
		/* MNEMONIC: INC RAM(HL)
		   Increments RAM(HL) and sets flags
		   OPCODE: 0x34*/
		int sum = toUnsigned(memory.readByte(getHL())) + 1;
		memory.writeByte(getHL(), (byte) (sum & 0xFF));

		setFlags(sum == 0, false, false, false);
		setClockRegisters(12);
	}
	/* 3.3.3 - 9 INC, n END */

	/* 3.3.3 - 10 DEC, n BEGIN */
	private void x3D() {
		/* MNEMONIC: DEC A
		   Decrements register A and sets flags
		   OPCODE: 0x3D*/
		int sum = toUnsigned(A) - 1;
		A = (byte) (sum & 0xFF);

		setFlags(A == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x05() {
		/* MNEMONIC: DEC B
		   Decrements register B and sets flags
		   OPCODE: 0x05*/
		int sum = toUnsigned(B) - 1;
		B = (byte) (sum & 0xFF);

		setFlags(B == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x0D() {
		/* MNEMONIC: DEC C
		   Decrements register C and sets flags
		   OPCODE: 0x0D*/
		int sum = toUnsigned(C) - 1;
		C = (byte) (sum & 0xFF);

		setFlags(C == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x15() {
		/* MNEMONIC: DEC D
		   Decrements register D and sets flags
		   OPCODE: 0x15*/
		int sum = toUnsigned(D) - 1;
		D = (byte) (sum & 0xFF);

		setFlags(D == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x1D() {
		/* MNEMONIC: DEC E
		   Decrements register E and sets flags
		   OPCODE: 0x1D*/
		int sum = toUnsigned(E) - 1;
		E = (byte) (sum & 0xFF);

		setFlags(E == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x25() {
		/* MNEMONIC: DEC H
		   Decrements register H and sets flags
		   OPCODE: 0x25*/
		int sum = toUnsigned(H) - 1;
		H = (byte) (sum & 0xFF);

		setFlags(H == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x2D() {
		/* MNEMONIC: DEC L
		   Decrements register L and sets flags
		   OPCODE: 0x2D*/
		int sum = toUnsigned(L) - 1;
		L = (byte) (sum & 0xFF);

		setFlags(L == 0, false, false, false);
		setClockRegisters(4);
	}

	private void x35() {
		/* MNEMONIC: DEC RAM(HL)
		   Decrements RAM(HL) and sets flags
		   OPCODE: 0x35*/
		int sum = toUnsigned(memory.readByte(getHL())) - 1;
		memory.writeByte(getHL(), (byte) (sum & 0xFF));

		setFlags(sum == 0, false, false, false);
		setClockRegisters(12);
	}
	/* 3.3.3 - 10 DEC, n END */

	/*************************************************************
	 * Q 3.3.4 16 Bit Arithmetic
	 */

	/* 3.3.4 - 1 ADD HL,n BEGIN */

	private void AddToHLhelper(byte alpha, byte beta) {
		int hlValue = (toUnsigned(H) << 8) + toUnsigned(L);
		hlValue += (toUnsigned(alpha) << 8) + toUnsigned(beta);

		if (hlValue > 65535) {
			F |= 0x10;
		}
		else {
			F &= 0xEF; // Check
		}
		H = (byte) ((hlValue >> 8) & 0xFF);
		L = (byte) ((hlValue & 0xFF));
	}

	private void x09() {
		/* MNEMONIC: ADD HL, BC
		   Adds register BC to HL and sets flags
		   OPCODE: 0x09 */
		AddToHLhelper(B, C);
		setClockRegisters(8);
	}

	private void x19() {
		/* MNEMONIC: ADD HL, DE
		   Adds register DE to HL and sets flags
		   OPCODE: 0x19 */
		AddToHLhelper(D, E);
		setClockRegisters(8);
	}

	private void x29() {
		/* MNEMONIC: ADD HL, HL
		   Adds register HL to HL and sets flags
		   OPCODE: 0x29 */
		AddToHLhelper(H, L);
		setClockRegisters(8);
	}

	private void x39() {
		/* MNEMONIC: ADD HL, SP
		   Adds register SP to HL and sets flags
		   OPCODE: 0x39 */
		byte S = (byte) ((stackPointer >> 8) & 0xFF);
		byte P = (byte) (stackPointer & 0xFF);
		AddToHLhelper(S, P);
		setClockRegisters(8);
	}
	/* 3.3.4 - 1 ADD HL,n END */

	/* 3.3.4 - 2 ADD SP,n BEGIN */
	private void xE8() {
		/* MNEMONIC: ADD SP, n
		   Adds one bit imeediate to Stack Pointer and sets flags.
		   OPCODE: 0xE8 */

		int SPvalue = stackPointer;

		byte immediateValue = memory.readByte(programCounter);
		programCounter++;

		SPvalue += toUnsigned(immediateValue);
		if (SPvalue > 65535) {
			F |= 0x10;
		}
		else {
			F &= 0xEF; //TODO Check for real
		}

		stackPointer = SPvalue & 0xFFFF;

		setClockRegisters(16);
	}

	/* 3.3.4 - 2 ADD SP,n END */

	/* 3.3.4 - 3 INC nn BEGIN */
	private void x03() {
		/* MNEMONIC: INC BC
		   Increment register BC
		   OPCODE: 0x03 */

		C = (byte) ((toUnsigned(C) + 1) & 0xFF);
		if (C == 0) {
			B = (byte) ((toUnsigned(B) + 1) & 0xFF);
		}

		setClockRegisters(8);
	}

	private void x13() {
		/* MNEMONIC: INC DE
		   Increment register DE
		   OPCODE: 0x13 */

		E = (byte) ((toUnsigned(E) + 1) & 0xFF);
		if (E == 0) {
			D = (byte) ((toUnsigned(D) + 1) & 0xFF);
		}

		setClockRegisters(8);
	}

	private void x23() {
		/* MNEMONIC: INC HL
		   Increment register HL
		   OPCODE: 0x23 */

		L = (byte) ((toUnsigned(L) + 1) & 0xFF);
		if (L == 0) {
			H = (byte) ((toUnsigned(H) + 1) & 0xFF);
		}

		setClockRegisters(8);
	}

	private void x33() {
		/* MNEMONIC: INC SP
		   Increment Stack Pointer
		   OPCODE: 0x33 */

		stackPointer++;
		stackPointer &= 0xFFFF;
		setClockRegisters(8);
	}

	/* 3.3.4 - 3 INC nn END */

	/* 3.3.4 - 4 DEC nn BEGIN */
	private void x0B() {
		/* MNEMONIC: DEC BC
		   Decrement register BC
		   OPCODE: 0x0B */

		C = (byte) ((toUnsigned(C) - 1) & 0xFF);
		if (C == 0xFF) {
			B = (byte) ((toUnsigned(B) - 1) & 0xFF);
		}

		setClockRegisters(8);
	}

	private void x1B() {
		/* MNEMONIC: DEC DE
		   Decrement register DE
		   OPCODE: 0x1B */

		E = (byte) ((toUnsigned(E) - 1) & 0xFF);
		if (E == 0xFF) {
			D = (byte) ((toUnsigned(D) - 1) & 0xFF);
		}

		setClockRegisters(8);
	}

	private void x2B() {
		/* MNEMONIC: DEC HL
		   Decrement register HL
		   OPCODE: 0x2B */

		L = (byte) ((toUnsigned(L) - 1) & 0xFF);
		if (L == 0xFF) {
			H = (byte) ((toUnsigned(H) - 1) & 0xFF);
		}

		setClockRegisters(8);
	}

	private void x3B() {
		/* MNEMONIC: DEC SP
		   Decrement Stack Pointer
		   OPCODE: 0x3B */

		stackPointer--;
		stackPointer &= 0xFFFF;
		setClockRegisters(8);
	}

	/* 3.3.4 - 4 DEC nn END */

	/* 3.3.5 - 1 SWAP N BEGIN */

	private void xCB37() {
		/* MNEMONIC: SWAP A
		   Swap upper and lower nibbles of register A
		   OPCODE: 0xCB37 */
		byte temp = A;
		A = (byte) (((((temp & 0xF) << 4) | ((temp & 0xF0) >> 4))) & 0xFF);

		setFlags((A == 0x0), false, false, false);
		setClockRegisters(8);
	}

	private void xCB30() {
		/* MNEMONIC: SWAP B
		   Swap upper and lower nibbles of register B
		   OPCODE: 0xCB30 */
		byte temp = B;
		B = (byte) (((((temp & 0xF) << 4) | ((temp & 0xF0) >> 4))) & 0xFF);

		setFlags((B == 0x0), false, false, false);
		setClockRegisters(8);
	}

	private void xCB31() {
		/* MNEMONIC: SWAP C
		   Swap upper and lower nibbles of register C
		   OPCODE: 0xCB31 */
		byte temp = C;
		C = (byte) (((((temp & 0xF) << 4) | ((temp & 0xF0) >> 4))) & 0xFF);

		setFlags((C == 0x0), false, false, false);
		setClockRegisters(8);
	}

	private void xCB32() {
		/* MNEMONIC: SWAP D
		   Swap upper and lower nibbles of register D
		   OPCODE: 0xCB32 */

		byte temp = D;
		D = (byte) (((((temp & 0xF) << 4) | ((temp & 0xF0) >> 4))) & 0xFF);

		setFlags((D == 0x0), false, false, false);
		setClockRegisters(8);
	}

	private void xCB33() {
		/* MNEMONIC: SWAP E
		   Swap upper and lower nibbles of register E
		   OPCODE: 0xCB33 */
		byte temp = E;
		E = (byte) (((((temp & 0xF) << 4) | ((temp & 0xF0) >> 4))) & 0xFF);

		setFlags((E == 0x0), false, false, false);
		setClockRegisters(8);
	}

	private void xCB34() {
		/* MNEMONIC: SWAP H
		   Swap upper and lower nibbles of register H
		   OPCODE: 0xCB34 */
		byte temp = H;
		H = (byte) (((((temp & 0xF) << 4) | ((temp & 0xF0) >> 4))) & 0xFF);

		setFlags((H == 0x0), false, false, false);
		setClockRegisters(8);
	}

	private void xCB35() {
		/* MNEMONIC: SWAP L
		   Swap upper and lower nibbles of register L
		   OPCODE: 0xCB35 */
		byte temp = L;
		L = (byte) (((((temp & 0xF) << 4) | ((temp & 0xF0) >> 4))) & 0xFF);

		setFlags((L == 0x0), false, false, false);
		setClockRegisters(8);
	}

	private void xCB36() {
		/* MNEMONIC: SWAP D
		   Swap upper and lower nibbles of RAM(HL)
		   OPCODE: 0xCB36 */
		byte temp = memory.readByte(getHL());
		temp = (byte) (((((temp & 0xF) << 4) | ((temp & 0xF0) >> 4))) & 0xFF);
		memory.writeByte(getHL(), temp);

		setFlags((temp == 0x0), false, false, false);
		setClockRegisters(16);
	}
	/* 3.3.5 - 1 SWAP N END */

	/* 3.3.5 - 2 DAA BEGIN */
	private void x27() {
		/* MNEMONIC: DAA
		   Decimal Adjust Register A
		   OPCODE: 0x27*/
		//TODO test

		byte tempA = A;

		if ((F & 0x20) >0 || ((A & 15) > 9)) {
			A += 6;
		}

		F &= 0xEF;

		if ((F & 0x20) >0 || (tempA > 0x99)) {
			A += 0x60;
			F |= 0x10;
		}

		setClockRegisters(4);
	}

	/* 3.3.5 - 2 DAA END */

	/* 3.3.5 - 3 CPL BEGIN */
	private void x2F() {
		/* MNEMONIC: CPL
		   Complement Register A
		   OPCODE: 0x2F*/
		//TODO Check flags

		A = (byte)~A;

		setClockRegisters(4);
	}
	/* 3.3.5 - 3 CPL END */

	/* 3.3.5 - 4 CCF BEGIN */
	private void x3F() {
		/* MNEMONIC: CCF
		   Complement Carry Flag
		   OPCODE: 0x3F*/
		//TODO Check
		setFlags(getCarryFlag(), false, false, getCarryFlag());

		setClockRegisters(4);
	}
	/* 3.3.5 - 4 CCF END */

	/* 3.3.5 - 5 SCF BEGIN */
	private void x37() {
		/* MNEMONIC: SCF
		   Set Carry Flag
		   OPCODE: 0x37*/
		//TODO Check
		setFlags(getCarryFlag(), false, false, true);

		setClockRegisters(4);
	}
	/* 3.3.5 - 5 SCF END */

	/* 3.3.5 - 6 NOP BEGIN */
	private void x00() {
		/* MNEMONIC: NOP
		   No Operation
		   OPCODE: 0x00*/

		setClockRegisters(4);
	}
	/* 3.3.5 - 6 NOP END */

	/* 3.3.5 - 7 HALT BEGIN */
	private void x76() {
		/* MNEMONIC: HALT
		   HALT
		   OPCODE: 0x76*/
		//TODO Implement
		setClockRegisters(4);
	}
	/* 3.3.5 - 7 HALT END */

	/* 3.3.5 - 9 DI BEGIN */
	private void xF3() {
		/* MNEMONIC: DI
		   Disables interrupts
		   OPCODE: 0xF3*/
		//TODO Implement
		setClockRegisters(4);
	}
	/* 3.3.5 - 9 DI END */

	/* 3.3.5 - 10 EI BEGIN */
	private void xFB() {
		/* MNEMONIC: EI
		   Enables interrupts
		   OPCODE: 0xFB*/
		//TODO Implement
		setClockRegisters(4);
	}
	/* 3.3.5 - 10 EI END */
	/*--------- 3.3.5 SECTION END ------------*/

	/* 3.3.6 ROTATES & SHIFTS  BEGIN */

	/* 3.3.6 - 1 RLCA BEGIN */
	private void x07() {
		/* MNEMONIC: RLCA
		   Rotate left, 
		   old bit 7 to Carry flag and to bit 0
		   OPCODE: 0x07*/
		boolean carryFlag = (((A >> 7) & 1) > 0);
		A = ((byte) (A << 1));
		if (carryFlag) {
			A = (byte)(A | 1);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(4);
	}

	/* 3.3.6 - 2 RLA BEGIN */
	private void x17() {
		/* MNEMONIC: RLA
		   Rotate A left through carry flag, 
		   old bit 7 to Carry flag and carry flagto bit 0
		   OPCODE: 0x17*/
		boolean carryFlag = (((A >> 7) & 1) > 0);
		A = ((byte) (A << 1));
		if (getCarryFlag()) {
			A = (byte)(A | 1);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(4);
	}

	/* 3.3.6 - 3 RRCA BEGIN */
	private void x0F() {
		/* MNEMONIC: RRCA
		   Rotate A Right 
		   old bit 0 to carry flag and bit 7
		   OPCODE: 0x0F*/
		boolean carryFlag = ((A & 1) > 0);
		A = ((byte) (A >> 1));
		if (carryFlag) {
			A = (byte)(A | 0b10000000);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(4);
	}

	/* 3.3.6 - 4 RRA BEGIN */
	private void x1F() {
		/* MNEMONIC: RRA
		   Rotate A Right through carryFlag
		   old bit 0 to carry flag and carry flag to bit 7
		   OPCODE: 0x1F*/
		boolean carryFlag = ((A & 1) > 0);
		A = ((byte) (A >> 1));
		if (getCarryFlag()) {
			A = (byte) (A | 0b10000000);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(4);
	}

	/* 3.3.6 - 5 RLC n BEGIN */
	private void xCB07() {
		/* MNEMONIC: RLC A
		   Rotate A left.
		   Old bit 7 to carry flag and bit 0
		   OPCODE: 0xCB07 */
		boolean carryFlag = (((A >> 7) & 1) > 0);
		A = ((byte) (A << 1));
		if (carryFlag) {
			A = (byte) (A | 1);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB00() {
		/* MNEMONIC: RLC B
		   Rotate B left.
		   Old bit 7 to carry flag and bit 0
		   OPCODE: 0xCB00 */
		boolean carryFlag = (((B >> 7) & 1) > 0);
		B = ((byte) (B << 1));
		if (carryFlag) {
			B = (byte) (B | 1);
		}

		boolean zeroFlag = (B == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB01() {
		/* MNEMONIC: RLC C
		   Rotate C left.
		   Old bit 7 to carry flag and bit 0
		   OPCODE: 0xCB01 */
		boolean carryFlag = (((C >> 7) & 1) > 0);
		C = ((byte) (C << 1));
		if (carryFlag) {
			C = (byte) (C | 1);
		}

		boolean zeroFlag = (C == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB02() {
		/* MNEMONIC: RLC D
		   Rotate D left.
		   Old bit 7 to carry flag and bit 0
		   OPCODE: 0xCB02 */
		boolean carryFlag = (((D >> 7) & 1) > 0);
		D = ((byte) (D << 1));
		if (carryFlag) {
			D = (byte) (D | 1);
		}

		boolean zeroFlag = (D == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB03() {
		/* MNEMONIC: RLC E
		   Rotate E left.
		   Old bit 7 to carry flag and bit 0
		   OPCODE: 0xCB03 */
		boolean carryFlag = (((E >> 7) & 1) > 0);
		E = ((byte) (E << 1));
		if (carryFlag) {
			E = (byte) (E | 1);
		}

		boolean zeroFlag = (E == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB04() {
		/* MNEMONIC: RLC H
		   Rotate H left.
		   Old bit 7 to carry flag and bit 0
		   OPCODE: 0xCB04 */
		boolean carryFlag = (((H >> 7) & 1) > 0);
		H = ((byte) (H << 1));
		if (carryFlag) {
			H = (byte) (H | 1);
		}

		boolean zeroFlag = (H == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB05() {
		/* MNEMONIC: RLC L
		   Rotate L left.
		   Old bit 7 to carry flag and bit 0
		   OPCODE: 0xCB05 */
		boolean carryFlag = (((L >> 7) & 1) > 0);
		L = ((byte) (L << 1));
		if (carryFlag) {
			L = (byte) (L | 1);
		}

		boolean zeroFlag = (L == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB06() {
		/* MNEMONIC: RLC (HL)
		   Rotate RAM(HL) left.
		   Old bit 7 to carry flag and bit 0
		   OPCODE: 0xCB06 */

		byte temp = memory.readByte(getHL());

		boolean carryFlag = (((temp >> 7) & 1) > 0);
		temp = ((byte) (temp << 1));
		if (carryFlag) {
			temp = (byte) (temp | 1);
		}

		boolean zeroFlag = (temp == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		memory.writeByte(getHL(), temp);
		setClockRegisters(16);
	}

	/* 3.3.6 - 6 RL n BEGIN */
	private void xCB17() {
		/* MNEMONIC: RL A
		   Rotate A left through carry flag.
		   Old bit 7 to carry flag carry flag to bit 0
		   OPCODE: 0xCB17 */
		boolean carryFlag = (((A >> 7) & 1) > 0);
		A = ((byte) (A << 1));
		if (getCarryFlag()) {
			A = (byte) (A | 1);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB10() {
		/* MNEMONIC: RL B
		   Rotate B left through carry flag.
		   Old bit 7 to carry flag carry flag to bit 0
		   OPCODE: 0xCB10 */
		boolean carryFlag = (((B >> 7) & 1) > 0);
		B = ((byte) (B << 1));
		if (getCarryFlag()) {
			B = (byte) (B | 1);
		}

		boolean zeroFlag = (B == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB11() {
		/* MNEMONIC: RL C
		   Rotate C left through carry flag.
		   Old bit 7 to carry flag carry flag to bit 0
		   OPCODE: 0xCB11 */
		boolean carryFlag = (((C >> 7) & 1) > 0);
		C = ((byte) (C << 1));
		if (getCarryFlag()) {
			C = (byte) (C | 1);
		}

		boolean zeroFlag = (C == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB12() {
		/* MNEMONIC: RL D
		   Rotate D left through carry flag.
		   Old bit 7 to carry flag carry flag to bit 0
		   OPCODE: 0xCB12 */
		boolean carryFlag = (((D >> 7) & 1) > 0);
		D = ((byte) (D << 1));
		if (getCarryFlag()) {
			D = (byte) (D | 1);
		}

		boolean zeroFlag = (D == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB13() {
		/* MNEMONIC: RL E
		   Rotate E left through carry flag.
		   Old bit 7 to carry flag carry flag to bit 0
		   OPCODE: 0xCB13 */
		boolean carryFlag = (((E >> 7) & 1) > 0);
		E = ((byte) (E << 1));
		if (getCarryFlag()) {
			E = (byte) (E | 1);
		}

		boolean zeroFlag = (E == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB14() {
		/* MNEMONIC: RL H
		   Rotate H left through carry flag.
		   Old bit 7 to carry flag carry flag to bit 0
		   OPCODE: 0xCB14 */
		boolean carryFlag = (((H >> 7) & 1) > 0);
		H = ((byte) (H << 1));
		if (getCarryFlag()) {
			H = (byte)(H | 1);
		}

		boolean zeroFlag = (H == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB15() {
		/* MNEMONIC: RL L
		   Rotate L left through carry flag.
		   Old bit 7 to carry flag and carry flag to bit 0
		   OPCODE: 0xCB15 */
		boolean carryFlag = (((L >> 7) & 1) > 0);
		L = ((byte) (L << 1));
		if (getCarryFlag()) {
			L = (byte) (L | 1);
		}

		boolean zeroFlag = (L == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB16(){
	/* MNEMONIC: RL (HL)
	   Rotate RAM(HL) left through carry flag.
	   Old bit 7 to carry flag and carry flag to bit 0
	   OPCODE: 0xCB16 */
	   
	byte temp = memory.readByte(getHL());

	boolean carryFlag = (((temp>> 7) & 1) > 0);
	temp = ((byte)(temp<< 1));
	if(getCarryFlag()){
		temp = (byte)(temp|1);
	}
	
	boolean zeroFlag = (temp == 0);	
	setFlags(zeroFlag, false, false, carryFlag);
	
	memory.writeByte(getHL(), temp);
	setClockRegisters(16);

}

	/* 3.3.6 - 7 RRC n BEGIN */
	private void xCB0F() {
		/* MNEMONIC: RRC A
		   Rotate A right.
		   Old bit 0 to carry flag and bit 7
		   OPCODE: 0xCB0F */
		boolean carryFlag = (((A >> 0) & 1) > 0);
		A = ((byte) (A >> 1));
		if (carryFlag) {
			A = (byte) (A | 0b10000000);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB08() {
		/* MNEMONIC: RRC B
		   Rotate B right.
		   Old bit 0 to carry flag and bit 7
		   OPCODE: 0xCB08 */
		boolean carryFlag = (((B >> 0) & 1) > 0);
		B = ((byte) (B >> 1));
		if (carryFlag) {
			B = (byte) (B | 0b10000000);
		}

		boolean zeroFlag = (B == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB09() {
		/* MNEMONIC: RRC C
		   Rotate C right.
		   Old bit 0 to carry flag and bit 7
		   OPCODE: 0xCB09 */
		boolean carryFlag = (((C >> 0) & 1) > 0);
		C = ((byte) (C >> 1));
		if (carryFlag) {
			C = (byte) (C | 0b10000000);
		}

		boolean zeroFlag = (C == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB0A() {
		/* MNEMONIC: RRC D
		   Rotate D right.
		   Old bit 0 to carry flag and bit 7
		   OPCODE: 0xCB0B */
		boolean carryFlag = (((D >> 0) & 1) > 0);
		D = ((byte) (D >> 1));
		if (carryFlag) {
			D = (byte) (D | 0b10000000);
		}

		boolean zeroFlag = (D == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB0B() {
		/* MNEMONIC: RRC E
		   Rotate E right.
		   Old bit 0 to carry flag and bit 7
		   OPCODE: 0xCB0B */
		boolean carryFlag = (((E >> 0) & 1) > 0);
		E = ((byte) (E >> 1));
		if (carryFlag) {
			E = (byte) (E | 0b10000000);
		}

		boolean zeroFlag = (E == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB0C() {
		/* MNEMONIC: RRC H
		   Rotate H right.
		   Old bit 0 to carry flag and bit 7
		   OPCODE: 0xCB0C */
		boolean carryFlag = (((H >> 0) & 1) > 0);
		H = ((byte) (H >> 1));
		if (carryFlag) {
			H = (byte) (H | 0b10000000);
		}

		boolean zeroFlag = (H == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB0D() {
		/* MNEMONIC: RRC L
		   Rotate L right.
		   Old bit 0 to carry flag and bit 7
		   OPCODE: 0xCB0D */
		boolean carryFlag = (((L >> 0) & 1) > 0);
		L = ((byte) (L >> 1));
		if (carryFlag) {
			L = (byte) (L | 0b10000000);
		}

		boolean zeroFlag = (L == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB0E() {
		/* MNEMONIC: RRC (HL)
		   Rotate RAM(HL) right.
		   Old bit 0 to carry flag and bit 7
		   OPCODE: 0xCB0E */

		byte temp = memory.readByte(getHL());

		boolean carryFlag = (((temp >> 7) & 1) > 0);
		temp = ((byte) (temp >> 1));
		if (carryFlag) {
			temp = (byte) (temp | 0b10000000);
		}

		boolean zeroFlag = (temp == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		memory.writeByte(getHL(), temp);
		setClockRegisters(16);
	}

	/* 3.3.6 - 8 RR n BEGIN */
	private void xCB1F() {
		/* MNEMONIC: RR A
		   Rotate A right through carry flag.
		   Old bit 0 to carry flag carry flag to bit 7
		   OPCODE: 0xCB1F */
		boolean carryFlag = (((A >> 0) & 1) > 0);
		A = ((byte) (A >> 1));
		if (getCarryFlag()) {
			A = (byte) (A | 0b10000000);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB18() {
		/* MNEMONIC: RR B
		   Rotate B right through carry flag.
		   Old bit 0 to carry flag carry flag to bit 7
		   OPCODE: 0xCB18 */
		boolean carryFlag = (((B >> 0) & 1) > 0);
		B = ((byte) (B >> 1));
		if (getCarryFlag()) {
			B = (byte) (B | 0b10000000);
		}

		boolean zeroFlag = (B == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB19() {
		/* MNEMONIC: RR C
		   Rotate C right through carry flag.
		   Old bit 0 to carry flag carry flag to bit 7
		   OPCODE: 0xCB19 */
		boolean carryFlag = (((C >> 0) & 1) > 0);
		C = ((byte) (C >> 1));
		if (getCarryFlag()) {
			C = (byte) (C | 0b10000000);
		}

		boolean zeroFlag = (C == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB1A() {
		/* MNEMONIC: RR D
		   Rotate D right through carry flag.
		   Old bit 0 to carry flag carry flag to bit 7
		   OPCODE: 0xCB1B */
		boolean carryFlag = (((D >> 0) & 1) > 0);
		D = ((byte) (D >> 1));
		if (getCarryFlag()) {
			D = (byte) (D | 0b10000000);
		}

		boolean zeroFlag = (D == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB1B() {
		/* MNEMONIC: RR E
		   Rotate E right through carry flag.
		   Old bit 0 to carry flag carry flag to bit 7
		   OPCODE: 0xCB1B */
		boolean carryFlag = (((E >> 0) & 1) > 0);
		E = ((byte) (E >> 1));
		if (getCarryFlag()) {
			E = (byte) (E | 0b10000000);
		}

		boolean zeroFlag = (E == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB1C() {
		/* MNEMONIC: RR H
		   Rotate H right through carry flag.
		   Old bit 0 to carry flag carry flag to bit 7
		   OPCODE: 0xCB1C */
		boolean carryFlag = (((H >> 0) & 1) > 0);
		H = ((byte) (H >> 1));
		if (getCarryFlag()) {
			H = (byte) (H | 0b10000000);
		}

		boolean zeroFlag = (H == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB1D() {
		/* MNEMONIC: RR L
		   Rotate L right through carry flag.
		   Old bit 0 to carry flag carry flag to bit 7
		   OPCODE: 0xCB1D */
		boolean carryFlag = (((L >> 0) & 1) > 0);
		L = ((byte) (L >> 1));
		if (getCarryFlag()) {
			L = (byte) (L | 0b10000000);
		}

		boolean zeroFlag = (L == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB1E() {
		/* MNEMONIC: RR (HL)
		   Rotate RAM(HL) right through carry flag.
		   Old bit 0 to carry flag carry flag to bit 7
		   OPCODE: 0xCB1E */

		byte temp = memory.readByte(getHL());

		boolean carryFlag = (((temp >> 7) & 1) > 0);
		temp = ((byte) (temp >> 1));
		if (getCarryFlag()) {
			temp = (byte) (temp | 0b10000000);
		}

		boolean zeroFlag = (temp == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		memory.writeByte(getHL(), temp);
		setClockRegisters(16);
	}

	/* 3.3.6 - 9 SLA n BEGIN */
	private void xCB27() {
		/* MNEMONIC: SLA A
		   Shifts A left into carry flag. LSB Set to 0
		   OPCODE: 0xCB27 */
		boolean carryFlag = (((A >> 7) & 1) > 0);
		A = ((byte) (A << 1));
		A = (byte) (A & 0b11111110);

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB20() {
		/* MNEMONIC: SLA B
		   Shifts B left into carry flag. LSB Set to 0
		   OPCODE: 0xCB20 */
		boolean carryFlag = (((B >> 7) & 1) > 0);
		B = ((byte) (B << 1));
		B = (byte) (B & 0b11111110);

		boolean zeroFlag = (B == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB21() {
		/* MNEMONIC: SLA C
		   Shifts C left into carry flag. LSB Set to 0
		   OPCODE: 0xCB21 */
		boolean carryFlag = (((C >> 7) & 1) > 0);
		C = ((byte) (C << 1));
		C = (byte) (C & 0b11111110);

		boolean zeroFlag = (C == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB22() {
		/* MNEMONIC: SLA D
		   Shifts D left into carry flag. LSB Set to 0
		   OPCODE: 0xCB22 */
		boolean carryFlag = (((D >> 7) & 1) > 0);
		D = ((byte) (D << 1));
		D = (byte) (D & 0b11111110);

		boolean zeroFlag = (D == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB23() {
		/* MNEMONIC: SLA E
		   Shifts E left into carry flag. LSB Set to 0
		   OPCODE: 0xCB23 */
		boolean carryFlag = (((E >> 7) & 1) > 0);
		E = ((byte) (E << 1));
		E = (byte) (E & 0b11111110);

		boolean zeroFlag = (E == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB24() {
		/* MNEMONIC: SLA H
		   Shifts H left into carry flag. LSB Set to 0
		   OPCODE: 0xCB24 */
		boolean carryFlag = (((H >> 7) & 1) > 0);
		H = ((byte) (H << 1));
		H = (byte) (H & 0b11111110);

		boolean zeroFlag = (H == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB25() {
		/* MNEMONIC: SLA L
		   Shifts L left into carry flag. LSB Set to 0
		   OPCODE: 0xCB25 */
		boolean carryFlag = (((L >> 7) & 1) > 0);
		L = ((byte) (L << 1));
		L = (byte) (L & 0b11111110);

		boolean zeroFlag = (L == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB26() {
		/* MNEMONIC: SLA (HL)
		   Shifts RAM(HL) left into carry flag. LSB Set to 0
		   OPCODE: 0xCB26 */

		byte temp = memory.readByte(getHL());

		boolean carryFlag = (((temp >> 7) & 1) > 0);
		temp = ((byte) (temp << 1));
		temp = (byte) (L & 0b11111110);

		boolean zeroFlag = (temp == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		memory.writeByte(getHL(), temp);
		setClockRegisters(16);

	}

	/* 3.3.6 - 10 SRA n BEGIN */
	private void xCB2F() {
		/* MNEMONIC: SRA A
		   Shifts A right into carry flag. MSB does not change.
		   OPCODE: 0xCB2F */
		boolean carryFlag = (((A >> 0) & 1) > 0);
		boolean MSB = (((A >> 7) & 1) > 0);

		A = ((byte) (A >> 1));
		if (MSB) {
			A = (byte) (A | 0b10000000);
		}

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB28() {
		/* MNEMONIC: SRA B
		   Shifts B right into carry flag. MSB does not change.
		   OPCODE: 0xCB28 */
		boolean carryFlag = (((B >> 0) & 1) > 0);
		boolean MSB = (((B >> 7) & 1) > 0);

		B = ((byte) (B >> 1));
		if (MSB) {
			B = (byte) (B | 0b10000000);
		}

		boolean zeroFlag = (B == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB29() {
		/* MNEMONIC: SRA C
		   Shifts C right into carry flag. MSB does not change.
		   OPCODE: 0xCB29 */
		boolean carryFlag = (((C >> 0) & 1) > 0);
		boolean MSB = (((C >> 7) & 1) > 0);

		C = ((byte) (C >> 1));
		if (MSB) {
			C = (byte) (C | 0b10000000);
		}

		boolean zeroFlag = (C == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB2A() {
		/* MNEMONIC: SRA D
		   Shifts D right into carry flag. MSB does not change.
		   OPCODE: 0xCB2A */
		boolean carryFlag = (((D >> 0) & 1) > 0);
		boolean MSB = (((D >> 7) & 1) > 0);

		D = ((byte) (D >> 1));
		if (MSB) {
			D = (byte) (D | 0b10000000);
		}

		boolean zeroFlag = (D == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB2B() {
		/* MNEMONIC: SRA E
		   Shifts E right into carry flag. MSB does not change.
		   OPCODE: 0xCB2B */
		boolean carryFlag = (((E >> 0) & 1) > 0);
		boolean MSB = (((E >> 7) & 1) > 0);

		E = ((byte) (E >> 1));
		if (MSB) {
			E = (byte) (E | 0b10000000);
		}

		boolean zeroFlag = (E == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB2C() {
		/* MNEMONIC: SRA H
		   Shifts H right into carry flag. MSB does not change.
		   OPCODE: 0xCB2C */
		boolean carryFlag = (((H >> 0) & 1) > 0);
		boolean MSB = (((F >> 7) & 1) > 0);

		H = ((byte) (H >> 1));
		if (MSB) {
			H = (byte) (H | 0b10000000);
		}

		boolean zeroFlag = (H == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB2D() {
		/* MNEMONIC: SRA L
		   Shifts L right into carry flag. MSB does not change.
		   OPCODE: 0xCB2D */
		boolean carryFlag = (((L >> 0) & 1) > 0);
		boolean MSB = (((L >> 7) & 1) > 0);

		L = ((byte) (L >> 1));
		if (MSB) {
			L = (byte) (L | 0b10000000);
		}

		boolean zeroFlag = (L == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB2E() {
		/* MNEMONIC: SRA (HL)
		   Shifts RAM(HL) right into carry flag. MSB does not change.
		   OPCODE: 0xCB2E */

		byte temp = memory.readByte(getHL());
		boolean carryFlag = (((temp >> 7) & 1) > 0);
		boolean MSB = (((A >> 7) & 1) > 0);

		temp = ((byte) (temp >> 1));
		if (MSB) {
			temp = (byte) (temp | 0b10000000);
		}

		boolean zeroFlag = (temp == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		memory.writeByte(getHL(), temp);
		setClockRegisters(16);

	}

	/* 3.3.6 - 11 SRL n BEGIN */
	private void xCB3F() {
		/* MNEMONIC: SRL A
		   Shifts A right into carry flag. MSB set to 0.
		   OPCODE: 0xCB3F */
		boolean carryFlag = (((A >> 0) & 1) > 0);

		A = ((byte) (A >> 1));
		A = (byte) (A & 01111111);

		boolean zeroFlag = (A == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB38() {
		/* MNEMONIC: SRL B
		   Shifts B right into carry flag. MSB set to 0.
		   OPCODE: 0xCB38 */
		boolean carryFlag = (((B >> 0) & 1) > 0);

		B = ((byte) (B >> 1));
		B = (byte) (B & 01111111);

		boolean zeroFlag = (B == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB39() {
		/* MNEMONIC: SRL C
		   Shifts C right into carry flag. MSB set to 0.
		   OPCODE: 0xCB39 */
		boolean carryFlag = (((C >> 0) & 1) > 0);

		C = ((byte) (C >> 1));
		C = (byte) (C & 01111111);

		boolean zeroFlag = (C == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB3A() {
		/* MNEMONIC: SRL D
		   Shifts D right into carry flag. MSB set to 0.
		   OPCODE: 0xCB3A */
		boolean carryFlag = (((D >> 0) & 1) > 0);

		D = ((byte) (D >> 1));
		D = (byte) (D & 01111111);

		boolean zeroFlag = (D == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB3B() {
		/* MNEMONIC: SRL E
		   Shifts E right into carry flag. MSB set to 0.
		   OPCODE: 0xCB3B */
		boolean carryFlag = (((E >> 0) & 1) > 0);

		E = ((byte) (E >> 1));
		E = (byte) (E & 01111111);

		boolean zeroFlag = (E == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB3C() {
		/* MNEMONIC: SRL H
		   Shifts H right into carry flag. MSB set to 0.
		   OPCODE: 0xCB3C */
		boolean carryFlag = (((H >> 0) & 1) > 0);

		H = ((byte) (H >> 1));
		H = (byte) (H & 01111111);

		boolean zeroFlag = (H == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB3D() {
		/* MNEMONIC: SRL L
		   Shifts L right into carry flag. MSB set to 0.
		   OPCODE: 0xCB3D */
		boolean carryFlag = (((L >> 0) & 1) > 0);

		L = ((byte) (L >> 1));
		L = (byte) (L & 01111111);

		boolean zeroFlag = (L == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		setClockRegisters(8);
	}

	private void xCB3E() {
		/* MNEMONIC: SRL (HL)
		   Shifts RAM(HL) right into carry flag. MSB set to 0.
		   OPCODE: 0xCB3E */

		byte temp = memory.readByte(getHL());
		boolean carryFlag = (((temp >> 7) & 1) > 0);

		temp = ((byte) (temp >> 1));
		temp = (byte) (temp & 01111111);

		boolean zeroFlag = (temp == 0);
		setFlags(zeroFlag, false, false, carryFlag);

		memory.writeByte(getHL(), temp);
		setClockRegisters(16);
	}

	/* 3.3.6 ROTATES & SHIFTS END */

	/* 3.3.7 BIT OPCODES BEGIN */

	/* 3.3.7 - 1 BIT b, r BEGIN */
	/* BIT 0, R BEGIN */
	/* Mnemonic of the form BIT B,R puts the value of bit B of register R in Z flag*/
	private void xCB47() {
		/* MNEMONIC: BIT 0,A
		   OPCODE: 0xCB47 */
		setFlags((((A >> 0) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB40() {
		/* MNEMONIC: BIT 0,B
		   OPCODE: 0xCB40 */
		setFlags((((B >> 0) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB41() {
		/* MNEMONIC: BIT 0,C
		   OPCODE: 0xCB41 */
		setFlags((((C >> 0) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB42() {
		/* MNEMONIC: BIT 0,D
		   OPCODE: 0xCB42 */
		setFlags((((D >> 0) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB43() {
		/* MNEMONIC: BIT 0,E
		   OPCODE: 0xCB43 */
		setFlags((((E >> 0) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB44() {
		/* MNEMONIC: BIT 0,H
		   OPCODE: 0xCB44 */
		setFlags((((H >> 0) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB45() {
		/* MNEMONIC: BIT 0,L
		   OPCODE: 0xCB45 */
		setFlags((((L >> 0) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB46() {
		/* MNEMONIC: BIT 0,(HL)
		   OPCODE: 0xCB46 */
		setFlags((((memory.readByte(getHL()) >> 0) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(16);
	}
	/* BIT 0, R END */

	/* BIT 1, R BEGIN */
	/* Mnemonic of the form BIT B,R puts the value of bit B of register R in Z flag*/
	private void xCB4F() {
		/* MNEMONIC: BIT 1,A
		   OPCODE: 0xCB4F */
		setFlags((((A >> 1) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB48() {
		/* MNEMONIC: BIT 1,B
		   OPCODE: 0xCB48 */
		setFlags((((B >> 1) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB49() {
		/* MNEMONIC: BIT 1,C
		   OPCODE: 0xCB49 */
		setFlags((((C >> 1) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB4A() {
		/* MNEMONIC: BIT 1,D
		   OPCODE: 0xCB4A */
		setFlags((((D >> 1) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB4B() {
		/* MNEMONIC: BIT 1,E
		   OPCODE: 0xCB4B */
		setFlags((((E >> 1) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB4C() {
		/* MNEMONIC: BIT 1,H
		   OPCODE: 0xCB4C */
		setFlags((((H >> 1) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB4D() {
		/* MNEMONIC: BIT 1,L
		   OPCODE: 0xCB4D */
		setFlags((((L >> 1) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB4E() {
		/* MNEMONIC: BIT 1,(HL)
		   OPCODE: 0xCB4E */
		setFlags((((memory.readByte(getHL()) >> 1) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(16);
	}
	/* BIT 1, R END */

	/* BIT 2, R BEGIN */
	/* Mnemonic of the form BIT B,R puts the value of bit B of register R in Z flag*/
	private void xCB57() {
		/* MNEMONIC: BIT 2,A
		   OPCODE: 0xCB57 */
		setFlags((((A >> 2) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB50() {
		/* MNEMONIC: BIT 2,B
		   OPCODE: 0xCB50 */
		setFlags((((B >> 2) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB51() {
		/* MNEMONIC: BIT 2,C
		   OPCODE: 0xCB51 */
		setFlags((((C >> 2) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB52() {
		/* MNEMONIC: BIT 2,D
		   OPCODE: 0xCB52 */
		setFlags((((D >> 2) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB53() {
		/* MNEMONIC: BIT 2,E
		   OPCODE: 0xCB53 */
		setFlags((((E >> 2) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB54() {
		/* MNEMONIC: BIT 2,H
		   OPCODE: 0xCB54 */
		setFlags((((H >> 2) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB55() {
		/* MNEMONIC: BIT 2,L
		   OPCODE: 0xCB55 */
		setFlags((((L >> 2) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB56() {
		/* MNEMONIC: BIT 2,(HL)
		   OPCODE: 0xCB56 */
		setFlags((((memory.readByte(getHL()) >> 2) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(16);
	}
	/* BIT 2, R END */

	/* BIT 3, R BEGIN */
	/* Mnemonic of the form BIT B,R puts the value of bit B of register R in Z flag*/
	private void xCB5F() {
		/* MNEMONIC: BIT 3,A
		   OPCODE: 0xCB5F */
		setFlags((((A >> 3) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB58() {
		/* MNEMONIC: BIT 3,B
		   OPCODE: 0xCB58 */
		setFlags((((B >> 3) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB59() {
		/* MNEMONIC: BIT 3,C
		   OPCODE: 0xCB59 */
		setFlags((((C >> 3) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB5A() {
		/* MNEMONIC: BIT 3,D
		   OPCODE: 0xCB5A */
		setFlags((((D >> 3) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB5B() {
		/* MNEMONIC: BIT 3,E
		   OPCODE: 0xCB5B */
		setFlags((((E >> 3) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB5C() {
		/* MNEMONIC: BIT 3,H
		   OPCODE: 0xCB5C */
		setFlags((((H >> 3) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB5D() {
		/* MNEMONIC: BIT 3,L
		   OPCODE: 0xCB5D */
		setFlags((((L >> 3) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB5E() {
		/* MNEMONIC: BIT 3,(HL)
		   OPCODE: 0xCB5E */
		setFlags((((memory.readByte(getHL()) >> 3) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(16);
	}

	/* BIT 3, R END */

	/* BIT 4, R BEGIN */
	/* Mnemonic of the form BIT B,R puts the value of bit B of register R in Z flag*/
	private void xCB67() {
		/* MNEMONIC: BIT 4,A
		   OPCODE: 0xCB67 */
		setFlags((((A >> 4) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB60() {
		/* MNEMONIC: BIT 4,B
		   OPCODE: 0xCB60 */
		setFlags((((B >> 4) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB61() {
		/* MNEMONIC: BIT 4,C
		   OPCODE: 0xCB61 */
		setFlags((((C >> 4) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB62() {
		/* MNEMONIC: BIT 4,D
		   OPCODE: 0xCB62 */
		setFlags((((D >> 4) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB63() {
		/* MNEMONIC: BIT 4,E
		   OPCODE: 0xCB63 */
		setFlags((((E >> 4) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB64() {
		/* MNEMONIC: BIT 4,H
		   OPCODE: 0xCB64 */
		setFlags((((H >> 4) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB65() {
		/* MNEMONIC: BIT 4,L
		   OPCODE: 0xCB65 */
		setFlags((((L >> 4) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB66() {
		/* MNEMONIC: BIT 4,(HL)
		   OPCODE: 0xCB66 */
		setFlags((((memory.readByte(getHL()) >> 4) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(16);
	}
	/* BIT 4, R END */

	/* BIT 5, R BEGIN */
	/* Mnemonic of the form BIT B,R puts the value of bit B of register R in Z flag*/
	private void xCB6F() {
		/* MNEMONIC: BIT 5,A
		   OPCODE: 0xCB6F */
		setFlags((((A >> 5) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB68() {
		/* MNEMONIC: BIT 5,B
		   OPCODE: 0xCB68 */
		setFlags((((B >> 5) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB69() {
		/* MNEMONIC: BIT 5,C
		   OPCODE: 0xCB69 */
		setFlags((((C >> 5) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB6A() {
		/* MNEMONIC: BIT 5,D
		   OPCODE: 0xCB6A */
		setFlags((((D >> 5) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB6B() {
		/* MNEMONIC: BIT 5,E
		   OPCODE: 0xCB6B */
		setFlags((((E >> 5) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB6C() {
		/* MNEMONIC: BIT 5,H
		   OPCODE: 0xCB6C */
		setFlags((((H >> 5) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB6D() {
		/* MNEMONIC: BIT 5,L
		   OPCODE: 0xCB6D */
		setFlags((((L >> 5) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB6E() {
		/* MNEMONIC: BIT 5,(HL)
		   OPCODE: 0xCB6E */
		setFlags((((memory.readByte(getHL()) >> 5) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(16);
	}
	/* BIT 5, R END*/

	/* BIT 6, R BEGIN */
	/* Mnemonic of the form BIT B,R puts the value of bit B of register R in Z flag*/
	private void xCB77() {
		/* MNEMONIC: BIT 6,A
		   OPCODE: 0xCB77 */
		setFlags((((A >> 6) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB70() {
		/* MNEMONIC: BIT 6,B
		   OPCODE: 0xCB70 */
		setFlags((((B >> 6) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB71() {
		/* MNEMONIC: BIT 6,C
		   OPCODE: 0xCB71 */
		setFlags((((C >> 6) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB72() {
		/* MNEMONIC: BIT 6,D
		   OPCODE: 0xCB72 */
		setFlags((((D >> 6) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB73() {
		/* MNEMONIC: BIT 6,E
		   OPCODE: 0xCB73 */
		setFlags((((E >> 6) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB74() {
		/* MNEMONIC: BIT 6,H
		   OPCODE: 0xCB74 */
		setFlags((((H >> 6) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB75() {
		/* MNEMONIC: BIT 6,L
		   OPCODE: 0xCB75 */
		setFlags((((L >> 6) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB76() {
		/* MNEMONIC: BIT 6,(HL)
		   OPCODE: 0xCB76 */
		setFlags((((memory.readByte(getHL()) >> 6) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(16);
	}
	/* BIT 6, R END */

	/* BIT 7, R BEGIN */
	/* Mnemonic of the form BIT B,R puts the value of bit B of register R in Z flag*/
	private void xCB7F() {
		/* MNEMONIC: BIT 7,A
		   OPCODE: 0xCB7F */
		setFlags((((A >> 7) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB78() {
		/* MNEMONIC: BIT 7,B
		   OPCODE: 0xCB78 */
		setFlags((((B >> 7) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB79() {
		/* MNEMONIC: BIT 7,C
		   OPCODE: 0xCB79 */
		setFlags((((C >> 7) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB7A() {
		/* MNEMONIC: BIT 7,D
		   OPCODE: 0xCB7A */
		setFlags((((D >> 7) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB7B() {
		/* MNEMONIC: BIT 7,E
		   OPCODE: 0xCB7B */
		setFlags((((E >> 7) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB7C() {
		/* MNEMONIC: BIT 7,H
		   OPCODE: 0xCB7C */
		setFlags((((H >> 7) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB7D() {
		/* MNEMONIC: BIT 7,L
		   OPCODE: 0xCB7D */
		setFlags((((L >> 7) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(8);
	}

	private void xCB7E() {
		/* MNEMONIC: BIT 7,(HL)
		   OPCODE: 0xCB7E */
		setFlags((((memory.readByte(getHL()) >> 7) & 1) == 0), false, true, getCarryFlag());
		setClockRegisters(16);
	}
	/* BIT 7, R END*/

	/* 3.3.7 - 1 BIT b, r END */

	/* 3.3.7 - 2 SET b, r BEGIN */
	/* SET 0, R BEGIN */
	/* Mnemonic of the form SET B,R sets value of SET B of register R */
	private void xCBC7() {
		/* MNEMONIC: SET 0,A
		   OPCODE: 0xCBC7 */
		A |= (0b00000001);
		setClockRegisters(8);
	}

	private void xCBC0() {
		/* MNEMONIC: SET 0,B
		   OPCODE: 0xCBC0 */
		B |= (0b00000001);
		setClockRegisters(8);
	}

	private void xCBC1() {
		/* MNEMONIC: SET 0,C
		   OPCODE: 0xCBC1 */
		C |= (0b00000001);
		setClockRegisters(8);
	}

	private void xCBC2() {
		/* MNEMONIC: SET 0,D
		   OPCODE: 0xCBC2 */
		D |= (0b00000001);
		setClockRegisters(8);
	}

	private void xCBC3() {
		/* MNEMONIC: SET 0,E
		   OPCODE: 0xCBC3 */
		E |= (0b00000001);
		setClockRegisters(8);
	}

	private void xCBC4() {
		/* MNEMONIC: SET 0,H
		   OPCODE: 0xCBC4 */
		H |= (0b00000001);
		setClockRegisters(8);
	}

	private void xCBC5() {
		/* MNEMONIC: SET 0,L
		   OPCODE: 0xCBC5 */
		L |= (0b00000001);
		setClockRegisters(8);
	}

	private void xCBC6() {
		/* MNEMONIC: SET 0,(HL)
		   OPCODE: 0xCBC6 */
		byte val = (byte) (memory.readByte(getHL()) | (0b00000001));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* SET 0, R END */

	/* SET 1, R BEGIN */
	/* Mnemonic of the form SET B,R sets value of SET B of register R */
	private void xCBCF() {
		/* MNEMONIC: SET 1,A
		   OPCODE: 0xCBCF */
		A |= ((0b00000010));
		setClockRegisters(8);
	}

	private void xCBC8() {
		/* MNEMONIC: SET 1,B
		   OPCODE: 0xCBC8 */
		B |= ((0b00000010));
		setClockRegisters(8);
	}

	private void xCBC9() {
		/* MNEMONIC: SET 1,C
		   OPCODE: 0xCBC9 */
		C |= ((0b00000010));
		setClockRegisters(8);
	}

	private void xCBCA() {
		/* MNEMONIC: SET 1,D
		   OPCODE: 0xCBCA */
		D |= ((0b00000010));
		setClockRegisters(8);
	}

	private void xCBCB() {
		/* MNEMONIC: SET 1,E
		   OPCODE: 0xCBCB */
		E |= ((0b00000010));
		setClockRegisters(8);
	}

	private void xCBCC() {
		/* MNEMONIC: SET 1,H
		   OPCODE: 0xCBCC */
		H |= ((0b00000010));
		setClockRegisters(8);
	}

	private void xCBCD() {
		/* MNEMONIC: SET 1,L
		   OPCODE: 0xCBCD */
		L |= ((0b00000010));
		setClockRegisters(8);
	}

	private void xCBCE() {
		/* MNEMONIC: SET 1,(HL)
		   OPCODE: 0xCBCE */
		byte val = (byte) (memory.readByte(getHL()) | (0b00000010));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* SET 1, R END */

	/* SET 2, R BEGIN */
	/* Mnemonic of the form SET B,R sets value of SET B of register R */
	private void xCBD7() {
		/* MNEMONIC: SET 2,A
		   OPCODE: 0xCBD7 */
		A |= (0b00000100);
		setClockRegisters(8);
	}

	private void xCBD0() {
		/* MNEMONIC: SET 2,B
		   OPCODE: 0xCBD0 */
		B |= (0b00000100);
		setClockRegisters(8);
	}

	private void xCBD1() {
		/* MNEMONIC: SET 2,C
		   OPCODE: 0xCBD1 */
		C |= (0b00000100);
		setClockRegisters(8);
	}

	private void xCBD2() {
		/* MNEMONIC: SET 2,D
		   OPCODE: 0xCBD2 */
		D |= (0b00000100);
		setClockRegisters(8);
	}

	private void xCBD3() {
		/* MNEMONIC: SET 2,E
		   OPCODE: 0xCBD3 */
		E |= (0b00000100);
		setClockRegisters(8);
	}

	private void xCBD4() {
		/* MNEMONIC: SET 2,H
		   OPCODE: 0xCBD4 */
		H |= (0b00000100);
		setClockRegisters(8);
	}

	private void xCBD5() {
		/* MNEMONIC: SET 2,L
		   OPCODE: 0xCBD5 */
		L |= (0b00000100);
		setClockRegisters(8);
	}

	private void xCBD6() {
		/* MNEMONIC: SET 2,(HL)
		   OPCODE: 0xCBD6 */
		byte val = (byte) (memory.readByte(getHL()) | (0b00000100));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* SET 2, R END */

	/* SET 3, R BEGIN */
	/* Mnemonic of the form SET B,R sets value of SET B of register R */
	private void xCBDF() {
		/* MNEMONIC: SET 3,A
		   OPCODE: 0xCBDF */
		A |= (0b00001000);
		setClockRegisters(8);
	}

	private void xCBD8() {
		/* MNEMONIC: SET 3,B
		   OPCODE: 0xCBD8 */
		B |= (0b00001000);
		setClockRegisters(8);
	}

	private void xCBD9() {
		/* MNEMONIC: SET 3,C
		   OPCODE: 0xCBD9 */
		C |= (0b00001000);
		setClockRegisters(8);
	}

	private void xCBDA() {
		/* MNEMONIC: SET 3,D
		   OPCODE: 0xCBDA */
		D |= (0b00001000);
		setClockRegisters(8);
	}

	private void xCBDB() {
		/* MNEMONIC: SET 3,E
		   OPCODE: 0xCBDB */
		E |= (0b00001000);
		setClockRegisters(8);
	}

	private void xCBDC() {
		/* MNEMONIC: SET 3,H
		   OPCODE: 0xCBDC */
		H |= (0b00001000);
		setClockRegisters(8);
	}

	private void xCBDD() {
		/* MNEMONIC: SET 3,L
		   OPCODE: 0xCBDD */
		L |= (0b00001000);
		setClockRegisters(8);
	}

	private void xCBDE() {
		/* MNEMONIC: SET 3,(HL)
		   OPCODE: 0xCBDE */
		byte val = (byte) (memory.readByte(getHL()) | (0b00001000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}

	/* SET 3, R END */

	/* SET 4, R BEGIN */
	/* Mnemonic of the form SET B,R sets value of SET B of register R */
	private void xCBE7() {
		/* MNEMONIC: SET 4,A
		   OPCODE: 0xCBE7 */
		A |= (0b00010000);
		setClockRegisters(8);
	}

	private void xCBE0() {
		/* MNEMONIC: SET 4,B
		   OPCODE: 0xCBE0 */
		B |= (0b00010000);
		setClockRegisters(8);
	}

	private void xCBE1() {
		/* MNEMONIC: SET 4,C
		   OPCODE: 0xCBE1 */
		C |= (0b00010000);
		setClockRegisters(8);
	}

	private void xCBE2() {
		/* MNEMONIC: SET 4,D
		   OPCODE: 0xCBE2 */
		D |= (0b00010000);
		setClockRegisters(8);
	}

	private void xCBE3() {
		/* MNEMONIC: SET 4,E
		   OPCODE: 0xCBE3 */
		E |= (0b00010000);
		setClockRegisters(8);
	}

	private void xCBE4() {
		/* MNEMONIC: SET 4,H
		   OPCODE: 0xCBE4 */
		H |= (0b00010000);
		setClockRegisters(8);
	}

	private void xCBE5() {
		/* MNEMONIC: SET 4,L
		   OPCODE: 0xCBE5 */
		L |= (0b00010000);
		setClockRegisters(8);
	}

	private void xCBE6() {
		/* MNEMONIC: SET 4,(HL)
		   OPCODE: 0xCBE6 */
		byte val = (byte) (memory.readByte(getHL()) | (0b00010000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* SET 4, R END */

	/* SET 5, R BEGIN */
	/* Mnemonic of the form SET B,R sets value of SET B of register R */
	private void xCBEF() {
		/* MNEMONIC: SET 5,A
		   OPCODE: 0xCBEF */
		A |= (0b00100000);
		setClockRegisters(8);
	}

	private void xCBE8() {
		/* MNEMONIC: SET 5,B
		   OPCODE: 0xCBE8 */
		B |= (0b00100000);
		setClockRegisters(8);
	}

	private void xCBE9() {
		/* MNEMONIC: SET 5,C
		   OPCODE: 0xCBE9 */
		C |= (0b00100000);
		setClockRegisters(8);
	}

	private void xCBEA() {
		/* MNEMONIC: SET 5,D
		   OPCODE: 0xCBEA */
		D |= (0b00100000);
		setClockRegisters(8);
	}

	private void xCBEB() {
		/* MNEMONIC: SET 5,E
		   OPCODE: 0xCBEB */
		E |= (0b00100000);
		setClockRegisters(8);
	}

	private void xCBEC() {
		/* MNEMONIC: SET 5,H
		   OPCODE: 0xCBEC */
		H |= (0b00100000);
		setClockRegisters(8);
	}

	private void xCBED() {
		/* MNEMONIC: SET 5,L
		   OPCODE: 0xCBED */
		L |= (0b00100000);
		setClockRegisters(8);
	}

	private void xCBEE() {
		/* MNEMONIC: SET 5,(HL)
		   OPCODE: 0xCBEE */
		byte val = (byte) (memory.readByte(getHL()) | (0b00100000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* SET 5, R END*/

	/* SET 6, R BEGIN */
	/* Mnemonic of the form SET B,R sets value of SET B of register R */
	private void xCBF7() {
		/* MNEMONIC: SET 6,A
		   OPCODE: 0xCBF7 */
		A |= (0b01000000);
		setClockRegisters(8);
	}

	private void xCBF0() {
		/* MNEMONIC: SET 6,B
		   OPCODE: 0xCBF0 */
		B |= (0b01000000);
		setClockRegisters(8);
	}

	private void xCBF1() {
		/* MNEMONIC: SET 6,C
		   OPCODE: 0xCBF1 */
		C |= (0b01000000);
		setClockRegisters(8);
	}

	private void xCBF2() {
		/* MNEMONIC: SET 6,D
		   OPCODE: 0xCBF2 */
		D |= (0b01000000);
		setClockRegisters(8);
	}

	private void xCBF3() {
		/* MNEMONIC: SET 6,E
		   OPCODE: 0xCBF3 */
		E |= (0b01000000);
		setClockRegisters(8);
	}

	private void xCBF4() {
		/* MNEMONIC: SET 6,H
		   OPCODE: 0xCBF4 */
		H |= (0b01000000);
		setClockRegisters(8);
	}

	private void xCBF5() {
		/* MNEMONIC: SET 6,L
		   OPCODE: 0xCBF5 */
		L |= (0b01000000);
		setClockRegisters(8);
	}

	private void xCBF6() {
		/* MNEMONIC: SET 6,(HL)
		   OPCODE: 0xCBF6 */
		byte val = (byte) (memory.readByte(getHL()) | (0b01000000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* SET 6, R END */

	/* SET 7, R BEGIN */
	/* Mnemonic of the form SET B,R sets value of SET B of register R */
	private void xCBFF() {
		/* MNEMONIC: SET 7,A
		   OPCODE: 0xCBFF */
		A |= (0b10000000);
		setClockRegisters(8);
	}

	private void xCBF8() {
		/* MNEMONIC: SET 7,B
		   OPCODE: 0xCBF8 */
		B |= (0b10000000);
		setClockRegisters(8);
	}

	private void xCBF9() {
		/* MNEMONIC: SET 7,C
		   OPCODE: 0xCBF9 */
		C |= (0b10000000);
		setClockRegisters(8);
	}

	private void xCBFA() {
		/* MNEMONIC: SET 7,D
		   OPCODE: 0xCBFA */
		D |= (0b10000000);
		setClockRegisters(8);
	}

	private void xCBFB() {
		/* MNEMONIC: SET 7,E
		   OPCODE: 0xCBFB */
		E |= (0b10000000);
		setClockRegisters(8);
	}

	private void xCBFC() {
		/* MNEMONIC: SET 7,H
		   OPCODE: 0xCBFC */
		H |= (0b10000000);
		setClockRegisters(8);
	}

	private void xCBFD() {
		/* MNEMONIC: SET 7,L
		   OPCODE: 0xCBFD */
		L |= (0b10000000);
		setClockRegisters(8);
	}

	private void xCBFE() {
		/* MNEMONIC: SET 7,(HL)
		   OPCODE: 0xCBFE */
		byte val = (byte) (memory.readByte(getHL()) | (0b10000000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* SET 7, R END*/

	/* 3.3.7 -
	/* 3.3.7 - 3 RES b, r BEGIN */
	/* RES 0, R BEGIN */
	/* Mnemonic of the form RES B,R resets value of RES B of register R */
	private void xCB87() {
		/* MNEMONIC: RES 0,A
		   OPCODE: 0xCB87 */
		A &= ~(0b00000001);
		setClockRegisters(8);
	}

	private void xCB80() {
		/* MNEMONIC: RES 0,B
		   OPCODE: 0xCB80 */
		B &= ~(0b00000001);
		setClockRegisters(8);
	}

	private void xCB81() {
		/* MNEMONIC: RES 0,C
		   OPCODE: 0xCB81 */
		C &= ~(0b00000001);
		setClockRegisters(8);
	}

	private void xCB82() {
		/* MNEMONIC: RES 0,D
		   OPCODE: 0xCB82 */
		D &= ~(0b00000001);
		setClockRegisters(8);
	}

	private void xCB83() {
		/* MNEMONIC: RES 0,E
		   OPCODE: 0xCB83 */
		E &= ~(0b00000001);
		setClockRegisters(8);
	}

	private void xCB84() {
		/* MNEMONIC: RES 0,H
		   OPCODE: 0xCB84 */
		H &= ~(0b00000001);
		setClockRegisters(8);
	}

	private void xCB85() {
		/* MNEMONIC: RES 0,L
		   OPCODE: 0xCB85 */
		L &= ~(0b00000001);
		setClockRegisters(8);
	}

	private void xCB86() {
		/* MNEMONIC: RES 0,(HL)
		   OPCODE: 0xCB86 */
		byte val = (byte) (memory.readByte(getHL()) & ~(0b00000001));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* RES 0, R END */

	/* RES 1, R BEGIN */
	/* Mnemonic of the form RES B,R resets value of RES B of register R */
	private void xCB8F() {
		/* MNEMONIC: RES 1,A
		   OPCODE: 0xCB8F */
		A &= ~((0b00000010));
		setClockRegisters(8);
	}

	private void xCB88() {
		/* MNEMONIC: RES 1,B
		   OPCODE: 0xCB88 */
		B &= ~((0b00000010));
		setClockRegisters(8);
	}

	private void xCB89() {
		/* MNEMONIC: RES 1,C
		   OPCODE: 0xCB89 */
		C &= ~((0b00000010));
		setClockRegisters(8);
	}

	private void xCB8A() {
		/* MNEMONIC: RES 1,D
		   OPCODE: 0xCB8A */
		D &= ~((0b00000010));
		setClockRegisters(8);
	}

	private void xCB8B() {
		/* MNEMONIC: RES 1,E
		   OPCODE: 0xCB8B */
		E &= ~((0b00000010));
		setClockRegisters(8);
	}

	private void xCB8C() {
		/* MNEMONIC: RES 1,H
		   OPCODE: 0xCB8C */
		H &= ~((0b00000010));
		setClockRegisters(8);
	}

	private void xCB8D() {
		/* MNEMONIC: RES 1,L
		   OPCODE: 0xCB8D */
		L &= ~((0b00000010));
		setClockRegisters(8);
	}

	private void xCB8E() {
		/* MNEMONIC: RES 1,(HL)
		   OPCODE: 0xCB8E */
		byte val = (byte) (memory.readByte(getHL()) & ~(0b00000010));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* RES 1, R END */

	/* RES 2, R BEGIN */
	/* Mnemonic of the form RES B,R resets value of RES B of register R */
	private void xCB97() {
		/* MNEMONIC: RES 2,A
		   OPCODE: 0xCB97 */
		A &= ~(0b00000100);
		setClockRegisters(8);
	}

	private void xCB90() {
		/* MNEMONIC: RES 2,B
		   OPCODE: 0xCB90 */
		B &= ~(0b00000100);
		setClockRegisters(8);
	}

	private void xCB91() {
		/* MNEMONIC: RES 2,C
		   OPCODE: 0xCB91 */
		C &= ~(0b00000100);
		setClockRegisters(8);
	}

	private void xCB92() {
		/* MNEMONIC: RES 2,D
		   OPCODE: 0xCB92 */
		D &= ~(0b00000100);
		setClockRegisters(8);
	}

	private void xCB93() {
		/* MNEMONIC: RES 2,E
		   OPCODE: 0xCB93 */
		E &= ~(0b00000100);
		setClockRegisters(8);
	}

	private void xCB94() {
		/* MNEMONIC: RES 2,H
		   OPCODE: 0xCB94 */
		H &= ~(0b00000100);
		setClockRegisters(8);
	}

	private void xCB95() {
		/* MNEMONIC: RES 2,L
		   OPCODE: 0xCB95 */
		L &= ~(0b00000100);
		setClockRegisters(8);
	}

	private void xCB96() {
		/* MNEMONIC: RES 2,(HL)
		   OPCODE: 0xCB96 */
		byte val = (byte) (memory.readByte(getHL()) & ~(0b00000100));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* RES 2, R END */

	/* RES 3, R BEGIN */
	/* Mnemonic of the form RES B,R resets value of RES B of register R */
	private void xCB9F() {
		/* MNEMONIC: RES 3,A
		   OPCODE: 0xCB9F */
		A &= ~(0b00001000);
		setClockRegisters(8);
	}

	private void xCB98() {
		/* MNEMONIC: RES 3,B
		   OPCODE: 0xCB98 */
		B &= ~(0b00001000);
		setClockRegisters(8);
	}

	private void xCB99() {
		/* MNEMONIC: RES 3,C
		   OPCODE: 0xCB99 */
		C &= ~(0b00001000);
		setClockRegisters(8);
	}

	private void xCB9A() {
		/* MNEMONIC: RES 3,D
		   OPCODE: 0xCB9A */
		D &= ~(0b00001000);
		setClockRegisters(8);
	}

	private void xCB9B() {
		/* MNEMONIC: RES 3,E
		   OPCODE: 0xCB9B */
		E &= ~(0b00001000);
		setClockRegisters(8);
	}

	private void xCB9C() {
		/* MNEMONIC: RES 3,H
		   OPCODE: 0xCB9C */
		H &= ~(0b00001000);
		setClockRegisters(8);
	}

	private void xCB9D() {
		/* MNEMONIC: RES 3,L
		   OPCODE: 0xCB9D */
		L &= ~(0b00001000);
		setClockRegisters(8);
	}

	private void xCB9E() {
		/* MNEMONIC: RES 3,(HL)
		   OPCODE: 0xCB9E */
		byte val = (byte) (memory.readByte(getHL()) & ~(0b00001000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}

	/* RES 3, R END */

	/* RES 4, R BEGIN */
	/* Mnemonic of the form RES B,R resets value of RES B of register R */
	private void xCBA7() {
		/* MNEMONIC: RES 4,A
		   OPCODE: 0xCBA7 */
		A &= ~(0b00010000);
		setClockRegisters(8);
	}

	private void xCBA0() {
		/* MNEMONIC: RES 4,B
		   OPCODE: 0xCBA0 */
		B &= ~(0b00010000);
		setClockRegisters(8);
	}

	private void xCBA1() {
		/* MNEMONIC: RES 4,C
		   OPCODE: 0xCBA1 */
		C &= ~(0b00010000);
		setClockRegisters(8);
	}

	private void xCBA2() {
		/* MNEMONIC: RES 4,D
		   OPCODE: 0xCBA2 */
		D &= ~(0b00010000);
		setClockRegisters(8);
	}

	private void xCBA3() {
		/* MNEMONIC: RES 4,E
		   OPCODE: 0xCBA3 */
		E &= ~(0b00010000);
		setClockRegisters(8);
	}

	private void xCBA4() {
		/* MNEMONIC: RES 4,H
		   OPCODE: 0xCBA4 */
		H &= ~(0b00010000);
		setClockRegisters(8);
	}

	private void xCBA5() {
		/* MNEMONIC: RES 4,L
		   OPCODE: 0xCBA5 */
		L &= ~(0b00010000);
		setClockRegisters(8);
	}

	private void xCBA6() {
		/* MNEMONIC: RES 4,(HL)
		   OPCODE: 0xCBA6 */
		byte val = (byte) (memory.readByte(getHL()) & ~(0b00010000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* RES 4, R END */

	/* RES 5, R BEGIN */
	/* Mnemonic of the form RES B,R resets value of RES B of register R */
	private void xCBAF() {
		/* MNEMONIC: RES 5,A
		   OPCODE: 0xCBAF */
		A &= ~(0b00100000);
		setClockRegisters(8);
	}

	private void xCBA8() {
		/* MNEMONIC: RES 5,B
		   OPCODE: 0xCBA8 */
		B &= ~(0b00100000);
		setClockRegisters(8);
	}

	private void xCBA9() {
		/* MNEMONIC: RES 5,C
		   OPCODE: 0xCBA9 */
		C &= ~(0b00100000);
		setClockRegisters(8);
	}

	private void xCBAA() {
		/* MNEMONIC: RES 5,D
		   OPCODE: 0xCBAA */
		D &= ~(0b00100000);
		setClockRegisters(8);
	}

	private void xCBAB() {
		/* MNEMONIC: RES 5,E
		   OPCODE: 0xCBAB */
		E &= ~(0b00100000);
		setClockRegisters(8);
	}

	private void xCBAC() {
		/* MNEMONIC: RES 5,H
		   OPCODE: 0xCBAC */
		H &= ~(0b00100000);
		setClockRegisters(8);
	}

	private void xCBAD() {
		/* MNEMONIC: RES 5,L
		   OPCODE: 0xCBAD */
		L &= ~(0b00100000);
		setClockRegisters(8);
	}

	private void xCBAE() {
		/* MNEMONIC: RES 5,(HL)
		   OPCODE: 0xCBAE */
		byte val = (byte) (memory.readByte(getHL()) & ~(0b00100000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* RES 5, R END*/

	/* RES 6, R BEGIN */
	/* Mnemonic of the form RES B,R resets value of RES B of register R */
	private void xCBB7() {
		/* MNEMONIC: RES 6,A
		   OPCODE: 0xCBB7 */
		A &= ~(0b01000000);
		setClockRegisters(8);
	}

	private void xCBB0() {
		/* MNEMONIC: RES 6,B
		   OPCODE: 0xCBB0 */
		B &= ~(0b01000000);
		setClockRegisters(8);
	}

	private void xCBB1() {
		/* MNEMONIC: RES 6,C
		   OPCODE: 0xCBB1 */
		C &= ~(0b01000000);
		setClockRegisters(8);
	}

	private void xCBB2() {
		/* MNEMONIC: RES 6,D
		   OPCODE: 0xCBB2 */
		D &= ~(0b01000000);
		setClockRegisters(8);
	}

	private void xCBB3() {
		/* MNEMONIC: RES 6,E
		   OPCODE: 0xCBB3 */
		E &= ~(0b01000000);
		setClockRegisters(8);
	}

	private void xCBB4() {
		/* MNEMONIC: RES 6,H
		   OPCODE: 0xCBB4 */
		H &= ~(0b01000000);
		setClockRegisters(8);
	}

	private void xCBB5() {
		/* MNEMONIC: RES 6,L
		   OPCODE: 0xCBB5 */
		L &= ~(0b01000000);
		setClockRegisters(8);
	}

	private void xCBB6() {
		/* MNEMONIC: RES 6,(HL)
		   OPCODE: 0xCBB6 */
		byte val = (byte) (memory.readByte(getHL()) & ~(0b01000000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* RES 6, R END */

	/* RES 7, R BEGIN */
	/* Mnemonic of the form RES B,R resets value of RES B of register R */
	private void xCBBF() {
		/* MNEMONIC: RES 7,A
		   OPCODE: 0xCBBF */
		A &= ~(0b10000000);
		setClockRegisters(8);
	}

	private void xCBB8() {
		/* MNEMONIC: RES 7,B
		   OPCODE: 0xCBB8 */
		B &= ~(0b10000000);
		setClockRegisters(8);
	}

	private void xCBB9() {
		/* MNEMONIC: RES 7,C
		   OPCODE: 0xCBB9 */
		C &= ~(0b10000000);
		setClockRegisters(8);
	}

	private void xCBBA() {
		/* MNEMONIC: RES 7,D
		   OPCODE: 0xCBBA */
		D &= ~(0b10000000);
		setClockRegisters(8);
	}

	private void xCBBB() {
		/* MNEMONIC: RES 7,E
		   OPCODE: 0xCBBB */
		E &= ~(0b10000000);
		setClockRegisters(8);
	}

	private void xCBBC() {
		/* MNEMONIC: RES 7,H
		   OPCODE: 0xCBBC */
		H &= ~(0b10000000);
		setClockRegisters(8);
	}

	private void xCBBD() {
		/* MNEMONIC: RES 7,L
		   OPCODE: 0xCBBD */
		L &= ~(0b10000000);
		setClockRegisters(8);
	}

	private void xCBBE() {
		/* MNEMONIC: RES 7,(HL)
		   OPCODE: 0xCBBE */
		byte val = (byte) (memory.readByte(getHL()) & ~(0b10000000));
		memory.writeByte(getHL(), val);
		setClockRegisters(16);
	}
	/* RES 7, R END*/

	/* 3.3.7 - 3 RES b, r END */

	/* 3.3.8 JUMPS BEGIN */

	/* 3.3.8 - 1 JP nn BEGIN */
	private void xC3() {
		/* MNEMONIC: JP nn
		   Jumps to address nn
		   OPCODE: 0xC3*/
		programCounter = memory.readWord(programCounter);
		setClockRegisters(12);
	}

	/* 3.3.8 - 2 JP cc,nn BEGIN */
	private void xC2() {
		/* MNEMONIC: JP NZ, nn
		   Jumps to address nn if Z flag is 0
		   OPCODE: 0xC2*/
		if (getZeroFlag() == false) {
			programCounter = memory.readWord(programCounter);
		}
		else {
			programCounter += 2;
		}

		setClockRegisters(12);
	}

	private void xCA() {
		/* MNEMONIC: JP Z, nn
		   Jumps to address nn if Z flag is 1
		   OPCODE: 0xCA*/
		if (getZeroFlag() == true) {
			programCounter = memory.readWord(programCounter);
		}
		else {
			programCounter += 2;
		}

		setClockRegisters(12);
	}

	private void xD2() {
		/* MNEMONIC: JP NC, nn
		   Jumps to address nn if carry flag is 0
		   OPCODE: 0xD2*/
		if (getCarryFlag() == false) {
			programCounter = memory.readWord(programCounter);
		}
		else {
			programCounter += 2;
		}

		setClockRegisters(12);
	}

	private void xDA() {
		/* MNEMONIC: JP C, nn
		   Jumps to address nn if carry flag is 1
		   OPCODE: 0xDA*/
		if (getCarryFlag() == true) {
			programCounter = memory.readWord(programCounter);
		}
		else {
			programCounter += 2;
		}

		setClockRegisters(12);
	}

	/* 3.3.8 - 3 JP (HL) BEGIN */
	private void xE9() {
		/* MNEMONIC: JP (HL)
		   Jumps to address RAM[HL]
		   OPCODE: 0xE9*/
		programCounter = memory.readWord(getHL());
		setClockRegisters(4);
	}

	/* 3.3.8 - 4 JP n BEGIN */
	private void x18() {
		/* MNEMONIC: JP n
		   Jumps to address PC + n
		   OPCODE: 0x18*/
		int n = (memory.readByte(programCounter) & 0xFF);
		programCounter++;

		programCounter += n;

		setClockRegisters(8);
	}

	/* 3.3.8 - 5 JR cc,nn BEGIN */
	private void x20() {
		/* MNEMONIC: JR NZ, nn
		   Jumps to address PC + n if Z flag is 0
		   OPCODE: 0x20*/
		int n = (memory.readByte(programCounter) & 0xFF);
		programCounter++;

		if (getZeroFlag() == false) {
			programCounter += n;
		}

		setClockRegisters(8);
	}

	private void x28() {
		/* MNEMONIC: JR Z, nn
		   Jumps to address PC + n if Z flag is 1
		   OPCODE: 0x28*/
		int n = (memory.readByte(programCounter) & 0xFF);
		programCounter++;

		if (getZeroFlag() == true) {
			programCounter += n;
		}

		setClockRegisters(8);
	}

	private void x30() {
		/* MNEMONIC: JR NC, nn
		   Jumps to address PC + n if carry flag is 0
		   OPCODE: 0x30*/
		int n = (memory.readByte(programCounter) & 0xFF);
		programCounter++;

		if (getCarryFlag() == false) {
			programCounter += n;
		}

		setClockRegisters(8);
	}

	private void x38() {
		/* MNEMONIC: JR C, nn
		   Jumps to address PC + n if carry flag is 1
		   OPCODE: 0x38*/
		int n = (memory.readByte(programCounter) & 0xFF);
		programCounter++;

		if (getCarryFlag() == true) {
			programCounter += n;
		}

		setClockRegisters(8);
	}

	/* 3.3.8 JUMPS END */

	/* 3.3.9 CALLS BEGIN */

	/* 3.3.9 - 1 CALL nn BEGIN */
	private void xCD() {
		/* MNEMONIC: CALL nn
		   Push addres of next instruction to stack then jumps to RAM[nn]
		   OPCODE: 0xCD*/
		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter + 2);

		programCounter = (memory.readWord(programCounter) & 0xFF);

		setClockRegisters(12);
	}

	/* 3.3.9 2 - CALL  cc,nn BEGIN */
	private void xC4() {
		/* MNEMONIC: CALL NZ, nn
		   Push next adress onto stack and jumps to nn if Z flag is 0
		   OPCODE: 0xC4*/

		if (getZeroFlag() == false) {
			stackPointer -= 2;
			memory.writeWord(stackPointer, programCounter + 2);

			programCounter = (memory.readWord(programCounter) & 0xFF);
		}
		else {
			programCounter += 2;
		}

		setClockRegisters(12);
	}

	private void xCC() {
		/* MNEMONIC: CALL Z, nn
		   Push next adress onto stack and jumps to nn if Z flag is 1
		   OPCODE: 0xCC*/
		if (getZeroFlag() == true) {
			stackPointer -= 2;
			memory.writeWord(stackPointer, programCounter + 2);

			programCounter = (memory.readWord(programCounter) & 0xFF);
		}
		else {
			programCounter += 2;
		}

		setClockRegisters(12);
	}

	private void xD4() {
		/* MNEMONIC: CALL NC, nn
		   Push next adress onto stack and jumps to nn if carry flag is 0
		   OPCODE: 0xD4*/
		if (getCarryFlag() == false) {
			stackPointer -= 2;
			memory.writeWord(stackPointer, programCounter + 2);

			programCounter = (memory.readWord(programCounter) & 0xFF);
		}
		else {
			programCounter += 2;
		}

		setClockRegisters(12);
	}

	private void xDC() {
		/* MNEMONIC: CALL C, nn
		   Push next adress onto stack and jumps to nn if carry flag is 1
		   OPCODE: 0xDC*/

		if (getCarryFlag() == true) {
			stackPointer -= 2;
			memory.writeWord(stackPointer, programCounter + 2);

			programCounter = (memory.readWord(programCounter) & 0xFF);
		}
		else {
			programCounter += 2;
		}
		setClockRegisters(12);

	}

	/* 3.3.9 2 - CALL  cc,nn END */
	/* 3.3.9 CALLS END */

	/* 3.3.10 RESTARTS BEGIN */

	/* 3.3.9 - 1 RST n BEGIN */
	private void xC7() {
		/* MNEMONIC: RST 00H
		   Push address onto stack and jumps to address 0x00
		   OPCODE: 0xC7*/

		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter);

		programCounter = 0x00;

		setClockRegisters(32);
	}

	private void xCF() {
		/* MNEMONIC: RST 08H
		   Push address onto stack and jumps to address 0x08
		   OPCODE: 0xCF*/

		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter);

		programCounter = 0x08;

		setClockRegisters(32);
	}

	private void xD7() {
		/* MNEMONIC: RST 10H
		   Push address onto stack and jumps to address 0x10
		   OPCODE: 0xD7*/

		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter);

		programCounter = 0x10;

		setClockRegisters(32);
	}

	private void xDF() {
		/* MNEMONIC: RST 18H
		   Push address onto stack and jumps to address 0x18
		   OPCODE: 0xDF*/

		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter);

		programCounter = 0x18;

		setClockRegisters(32);
	}

	private void xE7() {
		/* MNEMONIC: RST 20H
		   Push address onto stack and jumps to address 0x20
		   OPCODE: 0xE7*/

		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter);

		programCounter = 0x20;

		setClockRegisters(32);
	}

	private void xEF() {
		/* MNEMONIC: RST 28H
		   Push address onto stack and jumps to address 0x28
		   OPCODE: 0xEF*/

		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter);

		programCounter = 0x28;

		setClockRegisters(32);
	}

	private void xF7() {
		/* MNEMONIC: RST 30H
		   Push address onto stack and jumps to address 0x30
		   OPCODE: 0xF7*/

		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter);

		programCounter = 0x30;

		setClockRegisters(32);
	}

	private void xFF() {
		/* MNEMONIC: RST 38H
		   Push address onto stack and jumps to address 0x38
		   OPCODE: 0xFF*/

		stackPointer -= 2;
		memory.writeWord(stackPointer, programCounter);

		programCounter = 0x38;

		setClockRegisters(32);
	}

	/* 3.3.10 RESTARTS END */

	/* 3.3.11 RETURNS BEGIN */

	/* 3.3.9 - 1 RET BEGIN */
	private void xC9() {
		/* MNEMONIC: RET
		   Pop 2 bytes from stack and jumps to address
		   OPCODE: 0xC9*/

		programCounter = (memory.readWord(stackPointer) & 0xFF);
		stackPointer += 2;

		setClockRegisters(8);
	}

	/* 3.3.9 - 2 RET cc BEGIN */
	private void xC0() {
		/* MNEMONIC: RET NZ
		   Pop 2 bytes from stack and jumps to address if Z flag is 0
		   OPCODE: 0xC0*/
		if (getZeroFlag() == false) {
			programCounter = (memory.readWord(stackPointer) & 0xFF);
			stackPointer += 2;
		}

		setClockRegisters(8);
	}

	private void xC8() {
		/* MNEMONIC: RET Z
		   Pop 2 bytes from stack and jumps to address if Z flag is 1
		   OPCODE: 0xC8*/
		if (getZeroFlag() == true) {
			programCounter = (memory.readWord(stackPointer) & 0xFF);
			stackPointer += 2;
		}

		setClockRegisters(8);
	}

	private void xD0() {
		/* MNEMONIC: RET NC
		   Pop 2 bytes from stack and jumps to address if C flag is 0
		   OPCODE: 0xD0*/
		if (getCarryFlag() == false) {
			programCounter = (memory.readWord(stackPointer) & 0xFF);
			stackPointer += 2;
		}

		setClockRegisters(8);
	}

	private void xD8() {
		/* MNEMONIC: RET C
		   Pop 2 bytes from stack and jumps to address if C flag is 1
		   OPCODE: 0xD8*/
		if (getCarryFlag() == true) {
			programCounter = (memory.readWord(stackPointer) & 0xFF);
			stackPointer += 2;
		}

		setClockRegisters(8);
	}

	/* 3.3.9 - 3 RETI cc BEGIN */
	private void xD9() {
		/* MNEMONIC: RETI
		   Pop 2 bytes from stack and jumps to address and enable interrupts
		   OPCODE: 0xD9*/

		//TODO Implement Interuppts
		programCounter = (memory.readWord(stackPointer) & 0xFF);
		stackPointer += 2;

		setClockRegisters(8);
	}

	/* 3.3.11 RETURNS END */

	/* 3.3.7 BIT OPCODES END */

	public int getProgramCounter() {
		return programCounter;
	}

	/* 3.3.7 - 3 RES b, r END */

	/* 3.3.7 BIT OPCODES END */

}
