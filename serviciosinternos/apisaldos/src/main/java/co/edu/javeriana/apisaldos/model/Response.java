package co.edu.javeriana.apisaldos.model;

public class Response implements java.io.Serializable {

    private double saldo;

    public Response(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
