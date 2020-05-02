package co.edu.javeriana.sapservice;

public class Response implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Status cabecera;
	private User usuario;
	
	public Response(Status cabecera, User usuario) {
		this.cabecera = cabecera;
		this.usuario = usuario;
	}

	public Status getCabecera() {
		return cabecera;
	}

	public void setCabecera(Status cabecera) {
		this.cabecera = cabecera;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}