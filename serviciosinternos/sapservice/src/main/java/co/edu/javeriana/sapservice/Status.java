package co.edu.javeriana.sapservice;

public class Status implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String type;
	private String description;
	
	public Status(int code, String type, String description) {
		super();
		this.code = code;
		this.type = type;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}