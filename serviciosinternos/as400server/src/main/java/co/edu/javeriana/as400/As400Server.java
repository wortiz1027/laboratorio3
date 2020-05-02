package co.edu.javeriana.as400;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class As400Server {

    private final List<User> users = new ArrayList<User>();

    public As400Server() {
        database();
    }

    public void database() {
        this.users.add( new User(1111, "cperez", "Carlos Andres", "Perez Rodriguez", "Calle 123 # 123", 951478, 1200000));
        this.users.add( new User(2222, "mrodriguez", "Monica Marcela", "Rodriguez Roncancio", "Calle 456 # 456", 753159,500000));
        this.users.add( new User(3333, "iortiz", "Ivan Dario", "Ortiz Castillo", "Carrera 789 # 789", 852741, 785600));
        this.users.add( new User(4444, "mluque", "Maria Angelica", "Luque Camargo", "Calle 963 # 963", 157953, 1000));
        this.users.add( new User(5555, "cmachado", "Carolina yuri", "Machado Perez", "Carrera 852 # 850", 359862, 5684000));
    }

    public double consultarSalario(String username) {
        double salario = 0;
        for (User u : this.users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                salario = u.getSalario();
                break;
            }
        }
        return salario;
    }

    public void start(int port) {
        try {
            ServerSocket server = new ServerSocket(port);

            System.out.println("Servidor iniciado en el puerto: {" + port + "}...");
            int numero = 1;

            while (true) {
                Socket socket = server.accept();

                System.out.println("Cliente #{" + numero + "} conectado...");

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                String mensaje = (String) ois.readObject();

                System.out.println("Consultado salario de {" + mensaje + "}...");
                double salario = consultarSalario(mensaje);

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(salario);

                ois.close();
                oos.close();
                socket.close();

                if(mensaje.equalsIgnoreCase("exit")) break;

                numero++;
            }

            System.out.println("Apagando el servidor...");
            server.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error iniciando el servidor: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}