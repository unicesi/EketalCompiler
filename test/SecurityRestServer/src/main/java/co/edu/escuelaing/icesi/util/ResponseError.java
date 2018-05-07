package co.edu.escuelaing.icesi.util;

public class ResponseError  {
	private String message;

	public ResponseError(String string, String... args) {
		this.message = String.format(string, args.toString());
	}
	public ResponseError(Exception e) {
		message = e.getMessage();
	}
	public String getMessage() {
		return message;
		//return super.getMessage();
	}
	
}
