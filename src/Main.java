import java.util.Scanner;
import java.util.List;

public class Main {

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

    private static void exibirResultado(String resultado) {
        if (resultado.startsWith("ERRO")) {
            System.out.println("Falha: " + resultado);
        } else {
            System.out.println("\nSUCESSO!");
            System.out.println("Protocolo Gerado: " + resultado);
        }
    }

    private static void exibirSolicitacaoUsuario(Solicitacao s) {
        System.out.println("Protocolo : " + s.getProtocolo());
        System.out.println("Categoria : " + s.getCategoria().getCat());
        System.out.println("Descrição : " + s.getDescricao());
        System.out.println("Local     : " + s.getLocalizacao());
        System.out.println("Status    : " + s.getStatus());
        System.out.println("Prazo     : " + s.getPrazoAtual());
        System.out.println("Autor     : " + s.getAutor().getNomeExibicao());
    }

    private static void exibirSolicitacaoAdmin(Solicitacao s) {
        System.out.println("Protocolo : " + s.getProtocolo());
        System.out.println("Categoria : " + s.getCategoria().getCat());
        System.out.println("Descrição : " + s.getDescricao());
        System.out.println("Local     : " + s.getLocalizacao());
        System.out.println("Status    : " + s.getStatus());
        System.out.println("Prazo     : " + s.getPrazoAtual());
        System.out.println("Autor (exibição pública): " + s.getAutor().getNomeExibicao());
        System.out.println("Nome real              : " + s.getAutor().getNome());
        System.out.println("CPF                    : " + s.getAutor().getCpf());
        System.out.println("Email                  : " + s.getAutor().getEmail());
        System.out.println("Anônima?               : " + (s.getAutor().isAnonimo() ? "Sim" : "Não"));
        System.out.println("Histórico:");

        for (HistoricoStatus h : s.getHistorico()) {
            System.out.println("- " + h);
        }
    }

    private static String escolherPerfil(Scanner scanner) {
        while (true) {
            System.out.println("\n==== ACESSO AO SISTEMA ====");
            System.out.println("1 - Usuário");
            System.out.println("2 - Admin");
            System.out.println("0 - Encerrar programa");
            System.out.print("Escolha o perfil: ");
            String perfil = scanner.nextLine();

            if (perfil.equals("1")) {
                return "Usuario";
            } else if (perfil.equals("2")) {
                return "Admin";
            } else if (perfil.equals("0")) {
                return "Encerrar";
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    private static void atualizarSolicitacaoAdmin(Scanner scanner, ServicoSolicitacoes servico) {
        System.out.print("Digite o protocolo da solicitação: ");
        String protocolo = scanner.nextLine();

        Solicitacao solicitacao = servico.buscarPeloProtocolo(protocolo);

        if (solicitacao == null) {
            System.out.println("Solicitação não encontrada.");
            return;
        }

        System.out.println("\n==== SOLICITAÇÃO ATUAL ====");
        exibirSolicitacaoAdmin(solicitacao);

        System.out.println("\nNovo status:");
        System.out.println("1 - triagem");
        System.out.println("2 - em execucao");
        System.out.println("3 - resolvido");
        System.out.println("4 - encerrado");
        System.out.print("Escolha: ");
        String opcaoStatus = scanner.nextLine();

        String novoStatus;

        switch (opcaoStatus) {
            case "1":
                novoStatus = "triagem";
                break;
            case "2":
                novoStatus = "em execucao";
                break;
            case "3":
                novoStatus = "resolvido";
                break;
            case "4":
                novoStatus = "encerrado";
                break;
            default:
                System.out.println("Status inválido.");
                return;
        }

        System.out.print("Digite a observação do administrador: ");
        String observacao = scanner.nextLine();

        boolean atualizado = servico.atualizarStatus(protocolo, novoStatus, observacao);

        if (atualizado) {
            System.out.println("Solicitação atualizada com sucesso!");
        } else {
            System.out.println("Não foi possível atualizar a solicitação.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SolicitacaoRepository repo = new MemoriaSolicitacao();
        ValidadorUsuario validador = (usuario) -> usuario.getNome() != null && !usuario.getNome().isEmpty();
        ServicoSolicitacoes servico = new ServicoSolicitacoes(repo, validador);

        while (true) {
            String perfil = escolherPerfil(scanner);

            if (perfil.equals("Encerrar")) {
                System.out.println("Encerrando programa...");
                break;
            }

            int opcao;

            do {
                servico.verificarPrazos();

                System.out.println("\n--- SISTEMA ESCOLAR ---");
                System.out.println("Perfil logado: " + perfil);

                if (perfil.equals("Admin")) {
                    System.out.println("1 - Listar solicitações");
                    System.out.println("2 - Buscar por protocolo");
                    System.out.println("3 - Atualizar status/observação");
                    System.out.println("4 - Trocar de perfil");
                    System.out.println("0 - Encerrar programa");
                } else {
                    System.out.println("1 - Criar solicitação");
                    System.out.println("2 - Buscar por protocolo");
                    System.out.println("3 - Trocar de perfil");
                    System.out.println("0 - Encerrar programa");
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
                                    exibirSolicitacaoAdmin(s);
                                }
                                System.out.println("----------------------------------");
                            }
                            break;

                        case 2:
                            System.out.print("Digite o protocolo: ");
                            String protocoloAdmin = scanner.nextLine();
                            Solicitacao encontradaAdmin = servico.buscarPeloProtocolo(protocoloAdmin);
                            if (encontradaAdmin == null) {
                                System.out.println("Solicitação não encontrada para o protocolo: " + protocoloAdmin);
                            } else {
                                System.out.println("\n==== SOLICITAÇÃO ENCONTRADA ====");
                                exibirSolicitacaoAdmin(encontradaAdmin);
                            }
                            break;

                        case 3:
                            atualizarSolicitacaoAdmin(scanner, servico);
                            break;

                        case 4:
                            System.out.println("Trocando de perfil...");
                            break;

                        case 0:
                            System.out.println("Encerrando programa...");
                            scanner.close();
                            return;

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
                            String protocoloUsuario = scanner.nextLine();
                            Solicitacao encontradaUsuario = servico.buscarPeloProtocolo(protocoloUsuario);
                            if (encontradaUsuario == null) {
                                System.out.println("Solicitação não encontrada para o protocolo: " + protocoloUsuario);
                            } else {
                                System.out.println("\n==== SOLICITAÇÃO ENCONTRADA ====");
                                exibirSolicitacaoUsuario(encontradaUsuario);
                            }
                            break;

                        case 3:
                            System.out.println("Trocando de perfil...");
                            break;

                        case 0:
                            System.out.println("Encerrando programa...");
                            scanner.close();
                            return;

                        default:
                            System.out.println("Opção inválida.");
                    }
                }

            } while (!((perfil.equals("Admin") && opcao == 4) ||
                    (perfil.equals("Usuario") && opcao == 3)));
        }

        scanner.close();
    }
}