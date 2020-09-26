package sx3Configuration.model;

public class CSVValues {

	private Object jsonValue;
	private String mappedValue;
	private int byteSize;
	private String hexValue;
	
	public Object getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(Object jsonValue) {
		this.jsonValue = jsonValue;
	}

	public String getMappedValue() {
		return mappedValue;
	}

	public void setMappedValue(String mappedValue) {
		this.mappedValue = mappedValue;
	}

	public int getByteSize() {
		return byteSize;
	}

	public void setByteSize(int byteSize) {
		this.byteSize = byteSize;
	}

	public String getHexValue() {
		return hexValue;
	}

	public void setHexValue(String hexValue) {
		this.hexValue = hexValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + byteSize;
		result = prime * result + ((hexValue == null) ? 0 : hexValue.hashCode());
		result = prime * result + ((jsonValue == null) ? 0 : jsonValue.hashCode());
		result = prime * result + ((mappedValue == null) ? 0 : mappedValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CSVValues other = (CSVValues) obj;
		if (byteSize != other.byteSize)
			return false;
		if (hexValue == null) {
			if (other.hexValue != null)
				return false;
		} else if (!hexValue.equals(other.hexValue))
			return false;
		if (jsonValue == null) {
			if (other.jsonValue != null)
				return false;
		} else if (!jsonValue.equals(other.jsonValue))
			return false;
		if (mappedValue == null) {
			if (other.mappedValue != null)
				return false;
		} else if (!mappedValue.equals(other.mappedValue))
			return false;
		return true;
	}
}
