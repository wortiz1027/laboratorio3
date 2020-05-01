package co.edu.javeriana.sapservice;

public class User implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String  username;
	private String  nombres;
	private String  apellidos;
	private String  direccion;
	private Integer telefono;
	
	public User(int code, String username, String nombres, String apellidos, String direccion, Integer telefono) {
		super();
		this.code = code;
		this.username = username;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getNombres() {
		return nombres;
	}



	public void setNombres(String nombres) {
		this.nombres = nombres;
	}



	public String getApellidos() {
		return apellidos;
	}



	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public Integer getTelefono() {
		return telefono;
	}



	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "Usuario [code=" + code + ", username=" + username + ", nombres=" + nombres + ", apellidos=" + apellidos
				+ ", direccion=" + direccion + ", telefono=" + telefono + "]";
	}
	
}
