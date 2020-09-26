package sx3Configuration.model;

public class SensorConfig {
	
	private String REGISTER_ADDRESS;
	private String REGISTER_VALUE;
	private String SLAVE_ADDRESS;
	
	public SensorConfig() {} 
	
	public SensorConfig(String REGISTER_ADDRESS,String REGISTER_VALUE,String SLAVE_ADDRESS) {
		this.REGISTER_ADDRESS = REGISTER_ADDRESS;
		this.REGISTER_VALUE = REGISTER_VALUE;
		this.SLAVE_ADDRESS = SLAVE_ADDRESS;
	}

	public String getREGISTER_ADDRESS() {
		return REGISTER_ADDRESS;
	}

	public void setREGISTER_ADDRESS(String rEGISTER_ADDRESS) {
		REGISTER_ADDRESS = rEGISTER_ADDRESS;
	}

	public String getREGISTER_VALUE() {
		return REGISTER_VALUE;
	}

	public void setREGISTER_VALUE(String rEGISTER_VALUE) {
		REGISTER_VALUE = rEGISTER_VALUE;
	}

	public String getSLAVE_ADDRESS() {
		return SLAVE_ADDRESS;
	}

	public void setSLAVE_ADDRESS(String sLAVE_ADDRESS) {
		SLAVE_ADDRESS = sLAVE_ADDRESS;
	}
	
	

}
