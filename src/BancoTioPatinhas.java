import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BancoTioPatinhas {

    private List<Conta> contas;
    private Scanner scanner;

    public BancoTioPatinhas() {
        contas = new ArrayList<>();
        scanner = new Scanner(System.in);

        PessoaFisica cliente1 = new PessoaFisica("Romero", "12345678901");
        PessoaJuridica cliente2 = new PessoaJuridica("Empresa SCCP", "12345678000123");

        contas.add(new Conta(1000.00, cliente1, "123"));
        contas.add(new Conta(2000.00, cliente2, "1910"));
    }

    public void iniciarSistema() {
        boolean sistemaAtivo = true;

        while (sistemaAtivo) {
            System.out.println("\nBem-vindo ao Banco Tio Patinhas!");
            System.out.println("1 - Login");
            System.out.println("2 - Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    login();
                    break;
                case 2:
                    sistemaAtivo = false;
                    System.out.println("Encerrando o sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public void login() {
        boolean loginValido = false;

        while (!loginValido) {
            System.out.println("\n--- Tela de Login ---");
            System.out.print("Digite o número da conta: ");
            int numeroConta = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Digite a sua senha: ");
            String senha = scanner.nextLine();

            for (Conta conta : contas) {
                if (conta.getNumeroConta() == numeroConta && conta.getSenha().equals(senha)) {
                    System.out.println("Login bem-sucedido!");
                    loginValido = true;
                    menu(conta);
                    break;
                }
            }

            if (!loginValido) {
                System.out.println("Número da conta ou senha inválidos. Tente novamente.");
            }
        }
    }

    public void menu(Conta conta) {
        boolean sair = false;

        while (!sair) {
            System.out.println("\nSelecione uma opção:");
            System.out.println("1 - Verificar saldo");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Transferir");
            System.out.println("5 - Exibir dados e extrato");
            System.out.println("6 - Sair da conta");

            System.out.print("Digite sua escolha: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    System.out.println("Saldo: R$ " + conta.getSaldo());
                    break;
                case 2:
                    System.out.print("Digite o valor a ser depositado: ");
                    double deposito = scanner.nextDouble();
                    scanner.nextLine();
                    conta.depositar(deposito);
                    System.out.println("Você depositou R$ " + deposito);
                    break;
                case 3:
                    System.out.print("Digite o valor a ser sacado: ");
                    double saque = scanner.nextDouble();
                    scanner.nextLine();
                    if (conta.sacar(saque)) {
                        System.out.println("Você sacou R$ " + saque);
                    } else {
                        System.out.println("Saldo insuficiente.");
                    }
                    break;
                case 4:
                    System.out.print("Digite o número da conta destino: ");
                    int numeroContaDestino = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite o valor a ser transferido: ");
                    double valorTransferencia = scanner.nextDouble();
                    scanner.nextLine();

                    Conta contaDestino = buscarConta(numeroContaDestino);
                    if (contaDestino != null) {
                        if (conta.transferir(contaDestino, valorTransferencia)) {
                            System.out.println("Você transferiu R$ " + valorTransferencia + " para a conta " + numeroContaDestino);
                        } else {
                            System.out.println("Saldo insuficiente para a transferência.");
                        }
                    } else {
                        System.out.println("Conta destino não encontrada.");
                    }
                    break;
                case 5:
                    exibirDadosExtrato(conta);
                    break;
                case 6:
                    sair = true;
                    System.out.println("Saindo da conta...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private Conta buscarConta(int numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    private void exibirDadosExtrato(Conta conta) {
        System.out.println("\n--- Dados da Conta ---");
        System.out.println("Titular: " + conta.getTitular().getNome());
        if (conta.getTitular() instanceof PessoaFisica) {
            PessoaFisica pf = (PessoaFisica) conta.getTitular();
            System.out.println("CPF: " + pf.getCpf());
        } else if (conta.getTitular() instanceof PessoaJuridica) {
            PessoaJuridica pj = (PessoaJuridica) conta.getTitular();
            System.out.println("CNPJ: " + pj.getCnpj());
        }
        System.out.println("Saldo atual: R$ " + conta.getSaldo());
        System.out.println("Extrato: " + conta.getExtrato() + "\n");
    }

    public static void main(String[] args) {
        BancoTioPatinhas banco = new BancoTioPatinhas();
        banco.iniciarSistema();
    }
}
