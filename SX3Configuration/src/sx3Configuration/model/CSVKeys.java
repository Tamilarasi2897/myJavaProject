package sx3Configuration.model;

public class CSVKeys {

	private String parentKey;
	private String actualKey;
	
	public String getParentKey() {
		return parentKey;
	}
	
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getActualKey() {
		return actualKey;
	}

	public void setActualKey(String actualKey) {
		this.actualKey = actualKey;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualKey == null) ? 0 : actualKey.hashCode());
		result = prime * result + ((parentKey == null) ? 0 : parentKey.hashCode());
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
		CSVKeys other = (CSVKeys) obj;
		if (actualKey == null) {
			if (other.actualKey != null)
				return false;
		} else if (!actualKey.equals(other.actualKey))
			return false;
		if (parentKey == null) {
			if (other.parentKey != null)
				return false;
		} else if (!parentKey.equals(other.parentKey))
			return false;
		return true;
	}
}
