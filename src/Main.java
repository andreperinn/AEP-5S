import java.util.Scanner;
import java.util.List;

public class Main {

    //metodo de cadastro do usuario, inserir nome, anonimo e seguir com o cadastro
    private static Usuario cadastrarUsuario(Scanner scanner) {
        System.out.println("\n==== CADASTRO DO SOLICITANTE ====");
        System.out.println("Para registrar uma solicitação, precisamos de alguns dados");
        System.out.println("(Eles não serão expostos caso escolha denúncia anônima)\n");

        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();

        System.out.print("CPF (somente numeros): ");
        String cpf = scanner.nextLine();

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.println("\nComo deseja registrar sua solicitação?");
        System.out.println("1 - Pública (seu nome sera exibido)");
        System.out.println("2 - Anonima (seus dados não serão expostos)");
        System.out.print("Escolha: ");
        String opcaoTipo = scanner.nextLine();

        boolean anonimo = opcaoTipo.equals("2");

        if (anonimo) {
            System.out.println("\nDenuncia anonima selecionada.");
            System.out.println("Seus dados pessoais estão protegidos e não serão exibidos publicamente.");
        } else {
            System.out.println("\nDenuncia publica selecionada. Seu nome sera exibido na solicitação.");
        }

        return new Usuario(nome, "Cidadao", cpf, email, anonimo);
    }

    //metodo de retorno para sucesso e ou erro na solicitação
    private static void exibirResultado(String resultado) {
        if (resultado.startsWith("ERRO")) {
            System.out.println("Falha: " + resultado);
        } else {
            System.out.println("\nSUCESSO!");
            System.out.println("Protocolo Gerado: " + resultado);
        }
    }

    private static void exibirSolicitacao(Solicitacao s) {
        System.out.println("Protocolo : " + s.getProtocolo());
        System.out.println("Categoria : " + s.getCategoria().getCat());
        System.out.println("Descrição : " + s.getDescricao());
        System.out.println("Local     : " + s.getLocalizacao());
        System.out.println("Status    : " + s.getStatus());
        System.out.println("Prazo     : " + s.getPrazoAtual());
        System.out.println("Autor     : " + s.getAutor().getNomeExibicao());
    }

    private static String escolherPerfil(Scanner scanner) {
        System.out.println("\n==== ACESSO AO SISTEMA ====");
        System.out.println("1 - Usuário");
        System.out.println("2 - Admin");
        System.out.print("Escolha o perfil: ");
        String perfil = scanner.nextLine();

        if (perfil.equals("2")) {
            return "Admin";
        }

        return "Usuario";
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        SolicitacaoRepository repo = new MemoriaSolicitacao();
        ValidadorUsuario validador = (usuario) -> usuario.getNome() != null && !usuario.getNome().isEmpty();
        ServicoSolicitacoes servico = new ServicoSolicitacoes(repo, validador);

        String perfil = escolherPerfil(scanner);

        do {
            servico.verificarPrazos();

            System.out.println("\n--- SISTEMA ESCOLAR ---");
            System.out.println("Perfil logado: " + perfil);

            if (perfil.equals("Admin")) {
                System.out.println("1 - Listar solicitações");
                System.out.println("2 - Buscar por protocolo");
                System.out.println("3 - Sair do sistema");
            } else {
                System.out.println("1 - Criar solicitação");
                System.out.println("2 - Buscar por protocolo");
                System.out.println("3 - Sair do sistema");
            }

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (perfil.equals("Admin")) {
                switch (opcao) {

                    case 1:
                        List<Solicitacao> lista = servico.listarTodas();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhuma solicitação registrada!");
                        } else {
                            System.out.println("\n==== SOLICITAÇÕES REGISTRADAS ====");
                            for (Solicitacao s : lista) {
                                System.out.println("----------------------------------");
                                exibirSolicitacao(s);
                            }
                            System.out.println("----------------------------------");
                        }
                        break;

                    case 2:
                        System.out.print("Digite o protocolo: ");
                        String protocolo = scanner.nextLine();
                        Solicitacao encontrada = servico.buscarPeloProtocolo(protocolo);
                        if (encontrada == null) {
                            System.out.println("Solicitação não encontrada para o protocolo: " + protocolo);
                        } else {
                            System.out.println("\n==== SOLICITAÇÃO ENCONTRADA ====");
                            exibirSolicitacao(encontrada);
                        }
                        break;

                    case 3:
                        System.out.println("Encerrando sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }

            } else {
                switch (opcao) {

                    case 1:
                        System.out.println("\n==== REGISTRO DE DEMANDA ====");

                        Usuario usuarioLogado = cadastrarUsuario(scanner);

                        System.out.print("Descreva o problema (Ex: Falta de rampa, Merenda): ");
                        String desc = scanner.nextLine();

                        System.out.print("Qual a escola/local?: ");
                        String local = scanner.nextLine();

                        System.out.print("Categoria (Denúncia, Infraestrutura ou PCD): ");
                        String categoriaTexto = scanner.nextLine();

                        Categoria cat = new Categoria();
                        cat.setCat(categoriaTexto);

                        String resultado = servico.registrarNovaSolicitacao(desc, local, cat, usuarioLogado);

                        exibirResultado(resultado);

                        break;

                    case 2:
                        System.out.print("Digite o protocolo: ");
                        String protocolo = scanner.nextLine();
                        Solicitacao encontrada = servico.buscarPeloProtocolo(protocolo);
                        if (encontrada == null) {
                            System.out.println("Solicitação não encontrada para o protocolo: " + protocolo);
                        } else {
                            System.out.println("\n==== SOLICITAÇÃO ENCONTRADA ====");
                            exibirSolicitacao(encontrada);
                        }
                        break;

                    case 3:
                        System.out.println("Encerrando sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            }

        } while (opcao != 3);
    }
}