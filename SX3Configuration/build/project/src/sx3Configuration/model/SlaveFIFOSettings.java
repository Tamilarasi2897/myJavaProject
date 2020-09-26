package sx3Configuration.model;

public class SlaveFIFOSettings {
	private int BURST_LENGTH;
	private int BUFFER_COUNT;
	private int BUFFER_SIZE;
	private int BUFFER_SPACE;
	private String RESERVED;

	public int getBURST_LENGTH() {
		return BURST_LENGTH;
	}

	public void setBURST_LENGTH(int bURST_LENGTH) {
		BURST_LENGTH = bURST_LENGTH;
	}

	public int getBUFFER_COUNT() {
		return BUFFER_COUNT;
	}

	public void setBUFFER_COUNT(int bUFFER_COUNT) {
		BUFFER_COUNT = bUFFER_COUNT;
	}

	public int getBUFFER_SIZE() {
		return BUFFER_SIZE;
	}

	public void setBUFFER_SIZE(int bUFFER_SIZE) {
		BUFFER_SIZE = bUFFER_SIZE;
	}

	public int getBUFFER_SPACE() {
		return BUFFER_SPACE;
	}

	public void setBUFFER_SPACE(int bUFFER_SPACE) {
		BUFFER_SPACE = bUFFER_SPACE;
	}

	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}

}
