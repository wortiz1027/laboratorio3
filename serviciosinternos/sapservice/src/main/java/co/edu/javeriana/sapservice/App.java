package co.edu.javeriana.sapservice;

import javax.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
    	Endpoint.publish("http://sap-service:55725/sap/SapService", new SapSystem());
    }
}