package sx3Configuration.model;

public class DebugLevel {
	private String DEBUG_ENABLE;
	private int DEBUG_LEVEL;
	private int AUTO_GENERATE_SERIAL_NUMBER;

	private String RESERVED;

	public String getDEBUG_ENABLE() {
		return DEBUG_ENABLE;
	}

	public void setDEBUG_ENABLE(String dEBUG_ENABLE) {
		DEBUG_ENABLE = dEBUG_ENABLE;
	}

	public int getDEBUG_LEVEL() {
		return DEBUG_LEVEL;
	}

	public void setDEBUG_LEVEL(int dEBUG_LEVEL) {
		DEBUG_LEVEL = dEBUG_LEVEL;
	}

	public int getAUTO_GENERATE_SERIAL_NUMBER() {
		return AUTO_GENERATE_SERIAL_NUMBER;
	}

	public void setAUTO_GENERATE_SERIAL_NUMBER(int aUTO_GENERATE_SERIAL_NUMBER) {
		AUTO_GENERATE_SERIAL_NUMBER = aUTO_GENERATE_SERIAL_NUMBER;
	}

	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}

}
