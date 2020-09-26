package sx3Configuration.model;

public class CameraControlAndProcessingUnitJson {

	private String LABEL_NAME;
	private String ENABLE;
	private Long MIN;
	private Long MAX;
	private Long STEP;
	private Long DEFAULT;
	private Long CURRENT_VALUE;
	private String REGISTER_ADDRESS;
	private Long LENGTH;

	public String getLABEL_NAME() {
		return LABEL_NAME;
	}

	public void setLABEL_NAME(String lABEL_NAME) {
		LABEL_NAME = lABEL_NAME;
	}

	public String getENABLE() {
		return ENABLE;
	}

	public void setENABLE(String eNABLE) {
		ENABLE = eNABLE;
	}

	public Long getMIN() {
		return MIN;
	}

	public void setMIN(Long mIN) {
		MIN = mIN;
	}

	public Long getMAX() {
		return MAX;
	}

	public void setMAX(Long mAX) {
		MAX = mAX;
	}

	public Long getSTEP() {
		return STEP;
	}

	public void setSTEP(Long sTEP) {
		STEP = sTEP;
	}

	public Long getDEFAULT() {
		return DEFAULT;
	}

	public void setDEFAULT(Long dEFAULT) {
		DEFAULT = dEFAULT;
	}

	public String getREGISTER_ADDRESS() {
		return REGISTER_ADDRESS;
	}

	public void setREGISTER_ADDRESS(String rEGISTER_ADDRESS) {
		REGISTER_ADDRESS = rEGISTER_ADDRESS;
	}

	public Long getLEN() {
		return LENGTH;
	}

	public void setLEN(Long lEN) {
		LENGTH = lEN;
	}

	public Long getCURRENT_VALUE() {
		return CURRENT_VALUE;
	}

	public void setCURRENT_VALUE(Long cURRENT_VALUE) {
		CURRENT_VALUE = cURRENT_VALUE;
	}
}
