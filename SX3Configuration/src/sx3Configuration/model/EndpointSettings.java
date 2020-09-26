package sx3Configuration.model;

public class EndpointSettings {

	private String ENDPOINT_TRANSFER_TYPE;
	private int BURST_LENGTH;
	private int BUFFER_COUNT;
	private int BUFFER_SIZE;
	private int USED_BUFFER_SPACE;
	private String RESERVED;

	public String getENDPOINT_TRANSFER_TYPE() {
		return ENDPOINT_TRANSFER_TYPE;
	}

	public void setENDPOINT_TRANSFER_TYPE(String eNDPOINT_TRANSFER_TYPE) {
		ENDPOINT_TRANSFER_TYPE = eNDPOINT_TRANSFER_TYPE;
	}

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

	public int getUSED_BUFFER_SPACE() {
		return USED_BUFFER_SPACE;
	}

	public void setUSED_BUFFER_SPACE(int uSED_BUFFER_SPACE) {
		USED_BUFFER_SPACE = uSED_BUFFER_SPACE;
	}

	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}
	

}