import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcao;

        do {

            System.out.println("\n--- SISTEMA ESCOLAR ---");
            System.out.println("1 - Criar solicitação");
            System.out.println("2 - Listar solicitações");
            System.out.println("3 - Buscar por protocolo");
            System.out.println("4 - Sair");

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    System.out.println("Criando solicitação...");
                    break;

                case 2:
                    System.out.println("Listando solicitações...");
                    break;

                case 3:
                    System.out.println("Buscando solicitação...");
                    break;

                case 4:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");

            }

        } while (opcao != 4);

    }

}