package co.edu.javeriana.sapservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SapService {
	
	@WebMethod
	public Response existeUsuario(String username);	
	
}