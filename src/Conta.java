import java.util.ArrayList;
import java.util.List;

class Conta {
    private static int contadorContas = 0;
    private int numeroConta;
    private double saldo;
    private String senha;
    private Pessoa titular;
    private List<String> extrato;

    public Conta(double saldoInicial, Pessoa titular, String senha) {
        this.numeroConta = ++contadorContas;
        this.saldo = saldoInicial;
        this.titular = titular;
        this.senha = senha;
        this.extrato = new ArrayList<>();
        registrarExtrato("Conta criada com saldo inicial de R$ " + saldoInicial);
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public Pessoa getTitular() {
        return titular;
    }

    public String getSenha() {
        return senha;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            registrarExtrato("Depósito de R$ " + valor);
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            registrarExtrato("Saque de R$ " + valor);
            return true;
        }
        return false;
    }

    public boolean transferir(Conta contaDestino, double valor) {
        if (sacar(valor)) {
            contaDestino.depositar(valor);
            registrarExtrato("Transferência de R$ " + valor + " para a conta " + contaDestino.getNumeroConta());
            return true;
        }
        return false;
    }

    private void registrarExtrato(String movimento) {
        extrato.add(movimento);
    }

    public List<String> getExtrato() {
        return extrato;
    }
}
