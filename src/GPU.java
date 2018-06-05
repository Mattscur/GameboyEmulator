
public class GPU {

	int mode;
	int modeClock;

	final int TileSet1Begin = 0x8000;
	final int TileSet1End = 0x8FFF;
	final int TileSet0Begin = 0x8800;
	final int TileSet0End = 0x97FF;
	final int TileMap0Begin = 0x9800;
	final int TileMap0End = 0x9BFF;
	final int TileMap1Begin = 0x9C00;
	final int TileMap1End = 0x9FFF;

	boolean backgroundOnOffFLAG;
	boolean spritesOnOffFLAG;
	boolean spriteSizeFLAG;
	boolean BGtileMapFLAG;
	boolean BGtileSetFLAG;
	boolean windowOnOffFLAG;
	boolean windowTileMapFLAG;
	boolean displayOnOffFLAG;

	boolean renderScanDone; 
	
	int scrollXregister;
	int scrollYregister;
	int currentLineRegister;
	int palleteRegister;

	private final int H_BLANK = 0;
	private final int V_BLANK = 1;
	private final int OAM_READ = 2;
	private final int VRAM_READ = 3;

	int[] pallete;

	CPU cpu;
	MemoryManager memory;

	Tile[] tileSet;

	byte[] screenData;

	//////////////////////// INITIALIZATION BEGIN ////////////////////////
	public GPU() {
		reset();
	}

	public void reset() {
		setMode(H_BLANK);
		resetClock();

		resetTileMap();
		resetFlags();
		resetRegisters();

		
		renderScanDone = false;
		screenData = new byte[160 * 144];
		pallete = new int[4]; // Mapto color????
	}

	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}

	public void setMemory(MemoryManager memory) {
		this.memory = memory;
	}

	private void setMode(int newMode) {
		this.mode = newMode;
	}

	private void resetLine() {
		currentLineRegister = 0;
	}

	private void resetClock() {
		this.modeClock = 0;
	}

	private void resetTileMap() {
		tileSet = new Tile[384];
		for (int i = 0; i < tileSet.length; i++) {
			tileSet[i] = new Tile();
		}
	}

	private void resetRegisters() {
		scrollXregister = 0;
		scrollYregister = 0;
		resetLine();
		palleteRegister = 0;
	}

	private void resetFlags() {
		backgroundOnOffFLAG = false;
		spritesOnOffFLAG = false;
		spriteSizeFLAG = false;
		BGtileMapFLAG = false;
		BGtileSetFLAG = false;
		windowOnOffFLAG = false;
		windowTileMapFLAG = false;
		displayOnOffFLAG = false;
	}

	private void updateFlags(byte val) {
		// TODO check
		backgroundOnOffFLAG = ((val >> 0) & 0x1) > 0;
		spritesOnOffFLAG = ((val >> 1) & 0x1) > 0;
		spriteSizeFLAG = ((val >> 2) & 0x1) > 0;
		BGtileMapFLAG = ((val >> 3) & 0x1) > 0;
		BGtileSetFLAG = ((val >> 4) & 0x1) > 0;
		windowOnOffFLAG = ((val >> 5) & 0x1) > 0;
		windowTileMapFLAG = ((val >> 6) & 0x1) > 0;
		displayOnOffFLAG = ((val >> 7) & 0x1) > 0;
	}

	public void updateFlags() {
		updateFlags(memory.readByte(0xFF40));
	}

	public void updateRegisters() {
		scrollXregister = toUnsigned(memory.readByte(0xFF42));
		scrollYregister = toUnsigned(memory.readByte(0xFF43));
		currentLineRegister = toUnsigned(memory.readByte(0xFF44));
		palleteRegister = toUnsigned(memory.readByte(0xFF47));
	}
	//////////////////////// INITIALIZATION END ////////////////////////

	//////////////////////// TILE UPDATE BEGIN ////////////////////////
	public void updateTile(int address, byte value) {
		int tileNum = getTileNumber(address);
		int tileRow = getTileRow(address);
		int lowBitAddress = (address % 2 == 0) ? address : address - 1;
		byte lowBits = memory.readByte(lowBitAddress);
		byte highBits = memory.readByte(lowBitAddress + 1);

		tileSet[tileNum].updatePixels(tileRow, lowBits, highBits);
	}

	private int getTileRow(int address) {
		// TODO Check
		int reducedAddress = address - TileSet1Begin;
		reducedAddress -= (getTileNumber(address) * 16);
		return (int) (reducedAddress / 2);
	}

	private int getTileNumber(int address) {
		// TODO Check
		int reducedAddress = address - TileSet1Begin;
		return (int) (reducedAddress / 16);
	}
	//////////////////////// TILE UPDATE END ////////////////////////

	public void step(byte tClockRegister) {
		modeClock += tClockRegister;

		if (mode == H_BLANK) {
			// Hblank
			// After last hblank, push data to Screen
			if (modeClock >= 204) {
				resetClock();
				currentLineRegister++;
				if (currentLineRegister == 143) {
					setMode(V_BLANK);
					// TODO push data to IRL display
				} else {
					setMode(OAM_READ);
				}
			}
		} else if (mode == V_BLANK) {
			if (modeClock >= 456) {
				resetClock();
				currentLineRegister++;
				if (currentLineRegister > 153) {
					setMode(OAM_READ);
					resetLine();
				}
			}
		} else if (mode == OAM_READ) {
			if (modeClock >= 80) {
				resetClock();
				setMode(VRAM_READ);
			}
		} else if (mode == VRAM_READ) {
			if (modeClock >= 172) {
				resetClock();
				setMode(H_BLANK);
				renderScan();
			}
		} else {
			System.out.println("ERROR IN GPU: INVALID MODE");
		}

	}

	private void renderScan() {
		// TODO Implement
		System.out.println("Render scan");
		int tileMapOffset = BGtileMapFLAG ? 0x9C00 : 0x9800;
		tileMapOffset += ((currentLineRegister + scrollYregister) & 255) >> 3; // TODO
																				// CHECK

		int lineOffset = scrollXregister >> 3;

		int y = (currentLineRegister + scrollYregister) & 7;
		int x = scrollXregister & 7;
		int screenDataOffset = currentLineRegister * 160;
		int color;
		System.out.println("a");
		int tileIndex = toUnsigned(memory.readByte(tileMapOffset + lineOffset));
		System.out.println("b");
		if (BGtileSetFLAG & tileIndex < 128) {
			tileIndex += 256;
		}

		for (int i = 0; i < 160; i++) {
			Tile currentTile = tileSet[tileIndex];

			screenData[screenDataOffset] = currentTile.pixels[x][y];
			screenDataOffset++;

			x++;
			if (x == 8) {
				x = 0;
				lineOffset = (lineOffset + 1) & 31;

				tileIndex = toUnsigned(memory.readByte(tileMapOffset + lineOffset));
				if (BGtileSetFLAG & tileIndex < 128) {
					tileIndex += 256;
				}
			}
		}
		renderScanDone = true;
		
	}

	final private int BYTEMASK = 0xFF;

	public int toUnsigned(byte val) {
		return val & BYTEMASK;
	}

	public boolean isTimeToRender() {
		if(renderScanDone){
			renderScanDone = false;
			return true;
		}
		return false;
	}

	public int[][] getScreenData() {
		/*Returns an int[][] with values [0,3] representing the pixels to be drawn on the screen*/
		int[][] screenData = new int[256][256];
		for (int tileX = 0; tileX < 32; tileX++) {
			for (int tileY = 0; tileY < 32; tileY++) {
				Tile currentTile = getCurrentTile(tileX, tileY);
				int topLeftX = tileX*8;
				int topLeftY = tileY*8;
				for (int x = 0; x < 8; x++) {
					for (int y = 0; y <  8; y++) {
						screenData[topLeftX + x][topLeftY+y] = (int)currentTile.pixels[x][y];
					}
				}
			}
		}
		
		int[][] screenDataToReturn = new int[160][144];
		for (int x = 0; x < 160; x++) {
			for (int y = 0; y < 144; y++) {
				screenDataToReturn[x][y] = screenData[scrollXregister+x][scrollYregister+y];
			}
		}
		return screenDataToReturn;
	}

	private Tile getCurrentTile(int tileX, int tileY) {
		return tileSet[tileX + (8*tileY)];
	}

}
