package sx3Configuration.model;

public class ColorMatching {

	private String COLOR_PRIMARIES;
	private String TRANSFER_CHARACTERISTICS;
	private String MATRIX_COEFFICIENTS;
	private String RESERVED;

	public String getCOLOR_PRIMARIES() {
		return COLOR_PRIMARIES;
	}

	public void setCOLOR_PRIMARIES(String cOLOR_PRIMARIES) {
		COLOR_PRIMARIES = cOLOR_PRIMARIES;
	}

	public String getTRANSFER_CHARACTERISTICS() {
		return TRANSFER_CHARACTERISTICS;
	}

	public void setTRANSFER_CHARACTERISTICS(String tRANSFER_CHARACTERISTICS) {
		TRANSFER_CHARACTERISTICS = tRANSFER_CHARACTERISTICS;
	}

	public String getMATRIX_COEFFICIENTS() {
		return MATRIX_COEFFICIENTS;
	}

	public void setMATRIX_COEFFICIENTS(String mATRIX_COEFFICIENTS) {
		MATRIX_COEFFICIENTS = mATRIX_COEFFICIENTS;
	}

	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}

}
