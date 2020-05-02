package co.edu.javeriana.as400;

public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando servidor...");
        Thread.sleep(5000);
        As400Server server=new As400Server();
        server.start(42400);
        System.out.println("Servidor detenido...");
    }
}
