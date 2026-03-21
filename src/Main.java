import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcao;

        SolicitacaoRepository repo = new MemoriaSolicitacaoRepository();
        ValidadorUsuario validador = (usuario) -> usuario.getNome() != null && !usuario.getNome().isEmpty();
        ServicoSolicitacoes servico = new ServicoSolicitacoes(repo, validador);

        Usuario usuarioLogado = new Usuario("Andre Perin", "Anonimo");
        usuarioLogado.setNome("Responsável Legal");

        do {

            System.out.println("\n--- SISTEMA ESCOLAR ---");
            System.out.println("1 - Criar solicitação");
            System.out.println("2 - Listar solicitações");
            System.out.println("3 - Buscar por protocolo");
            System.out.println("4 - Sair");

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            scanner.nextLine();
            switch (opcao) {

                case 1:
                    System.out.println("\n==== REGISTRO DE DEMANDA ====");

                    System.out.print("Descreva o problema (Ex: Falta de rampa, Merenda): ");
                    String desc = scanner.nextLine();

                    System.out.print("Qual a escola/local?: ");
                    String local = scanner.nextLine();

                    System.out.print("Categoria (Denúncia, Infraestrutura ou PCD): ");
                    String categoriaTexto = scanner.nextLine();

                    Categoria cat = new Categoria();
                    cat.setCat(categoriaTexto);

                    String resultado = servico.registrarNovaSolicitacao(desc, local, cat, usuarioLogado);

                    if (resultado.startsWith("ERRO")) {
                        System.out.println("Falha: " + resultado);
                    } else {
                        System.out.println("\nSUCESSO!");
                        System.out.println("Protocolo Gerado: " + resultado);
                    }
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