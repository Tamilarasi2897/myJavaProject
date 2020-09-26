package sx3Configuration.model;

public class ConfigTableGeneral {

	private String SIGNATURE;
	private String CONFIG_TABLE_CHECKSUM;
	private String CONFIG_TABLE_LENGTH;
	private int VERSION_MAJOR;
	private int VERSION_MINOR;
	private int VERSION_PATCH;
	private int CONFIGURATION_TYPE;
	private String RESERVED;

	public String getSIGNATURE() {
		return SIGNATURE;
	}

	public void setSIGNATURE(String sIGNATURE) {
		SIGNATURE = sIGNATURE;
	}

	public int getVERSION_MAJOR() {
		return VERSION_MAJOR;
	}

	public void setVERSION_MAJOR(int vERSION_MAJOR) {
		VERSION_MAJOR = vERSION_MAJOR;
	}

	public int getVERSION_MINOR() {
		return VERSION_MINOR;
	}

	public void setVERSION_MINOR(int vERSION_MINOR) {
		VERSION_MINOR = vERSION_MINOR;
	}

	public int getVERSION_PATCH() {
		return VERSION_PATCH;
	}

	public void setVERSION_PATCH(int vERSION_PATCH) {
		VERSION_PATCH = vERSION_PATCH;
	}

	public String getCONFIG_TABLE_LENGTH() {
		return CONFIG_TABLE_LENGTH;
	}

	public void setCONFIG_TABLE_LENGTH(String cONFIG_TABLE_LENGTH) {
		CONFIG_TABLE_LENGTH = cONFIG_TABLE_LENGTH;
	}

	public String getCONFIG_TABLE_CHECKSUM() {
		return CONFIG_TABLE_CHECKSUM;
	}

	public void setCONFIG_TABLE_CHECKSUM(String cONFIG_TABLE_CHECKSUM) {
		CONFIG_TABLE_CHECKSUM = cONFIG_TABLE_CHECKSUM;
	}

	public int getCONFIGURATION_TYPE() {
		return CONFIGURATION_TYPE;
	}

	public void setCONFIGURATION_TYPE(int cONFIGURATION_TYPE) {
		CONFIGURATION_TYPE = cONFIGURATION_TYPE;
	}

	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}

}
