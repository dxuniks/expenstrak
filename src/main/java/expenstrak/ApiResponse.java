package expenstrak;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class ApiResponse extends ResourceSupport{
	private String status;
	private String message;
	private String modelName;
	private List data;
	
	 
	public ApiResponse(String status, String message, String modelName, List data) {
		super();
		this.status = status;
		this.message = message;
		this.modelName = modelName;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	
	

}
