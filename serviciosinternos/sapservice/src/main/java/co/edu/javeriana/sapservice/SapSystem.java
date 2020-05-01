package co.edu.javeriana.sapservice;

import java.util.List;

import javax.jws.WebService;

import java.util.ArrayList;

@WebService(endpointInterface = "co.edu.javeriana.sapservice.SapService")
public class SapSystem implements SapService {

	private final List<User> users = new ArrayList<User>();
	
	public SapSystem() {
		database();
	}
	
	@Override
	public Response existeUsuario(String username) {
		Boolean existe = Boolean.FALSE;		
		User user = null;
		Response rs = null;
		
		for (User u : this.users) {
			if (u.getUsername().equalsIgnoreCase(username)) {
				existe = Boolean.TRUE;
				user = u;
				break;
			}
		}
		
		if (existe) {
			Status cabecera = new Status(0000, "OK", "Usuario registrado");			
			rs = new Response(cabecera, user);
		}
		
		if (!existe) {
			Status cabecera = new Status(1111, "ER", "Usuario NO registrado");			
			rs = new Response(cabecera, user);
		}
		
		return rs;
	}
	
	public void database() {				
		this.users.add( new User(1111, "cperez", "Carlos Andres", "Perez Rodriguez", "Calle 123 # 123", 951478));
		this.users.add( new User(2222, "mrodriguez", "Monica Marcela", "Rodriguez Roncancio", "Calle 456 # 456", 753159));
		this.users.add( new User(3333, "iortiz", "Ivan Dario", "Ortiz Castillo", "Carrera 789 # 789", 852741));
		this.users.add( new User(4444, "mluque", "Maria Angelica", "Luque Camargo", "Calle 963 # 963", 157953));
		this.users.add( new User(5555, "cmachado", "Carolina yuri", "Machado Perez", "Carrera 852 # 850", 359862));		
	}

}
