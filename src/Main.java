import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcao;

        SolicitacaoRepository repo = new MemoriaSolicitacao();
        ValidadorUsuario validador = (usuario) -> usuario.getNome() != null && !usuario.getNome().isEmpty();
        ServicoSolicitacoes servico = new ServicoSolicitacoes(repo, validador);

        Usuario usuarioLogado = new Usuario("Andre Perin", "Anonimo");
        usuarioLogado.setNome("Responsável Legal");

        do {

            servico.verificarPrazos();

            System.out.println("\n--- SISTEMA ESCOLAR ---");
            System.out.println("1 - Criar solicitação");
            System.out.println("2 - Listar solicitações");
            System.out.println("3 - Buscar por protocolo");
            System.out.println("4 - Sair do sistema");
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
                    List<Solicitacao> lista = servico.listarTodas();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhuma solicitação registrada!");
                    } else {
                        System.out.println("\n==== SOLICITAÇÕES REGISTRADAS ====");
                        for (Solicitacao s : lista) {
                            System.out.println("----------------------------------");
                            System.out.println("Protocolo : " + s.getProtocolo());
                            System.out.println("Categoria : " + s.getCategoria().getCat());
                            System.out.println("Descrição : " + s.getDescricao());
                            System.out.println("Local     : " + s.getLocalizacao());
                            System.out.println("Prazo     : " + s.getPrazoAtual());
                            System.out.println("Autor     : " + s.getAutor().getNome());
                        }
                        System.out.println("----------------------------------");
                    }
                    break;

                case 3:
                    System.out.print("Digite o protocolo: ");
                    String protocolo = scanner.nextLine();
                    Solicitacao encontrada = servico.buscarPeloProtocolo(protocolo);
                    if (encontrada == null) {
                        System.out.println("Solicitação não encontrada para o protocolo: " + protocolo);
                    } else {
                        System.out.println("\n==== SOLICITAÇÃO ENCONTRADA ====");
                        System.out.println("Protocolo : " + encontrada.getProtocolo());
                        System.out.println("Categoria : " + encontrada.getCategoria().getCat());
                        System.out.println("Descrição : " + encontrada.getDescricao());
                        System.out.println("Local     : " + encontrada.getLocalizacao());
                        System.out.println("Prazo     : " + s.getPrazoAtual());
                        System.out.println("Autor     : " + encontrada.getAutor().getNome());
                    }
                    break;

                case 4:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");

            }

        } while (opcao != 5);

    }

}