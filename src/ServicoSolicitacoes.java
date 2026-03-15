public class ServicoSolicitacoes {
    // Dependemos de abstrações (Interfaces), não de implementações
    private SolicitacaoRepository repository;
    private ValidadorUsuario validador;

    public ServicoSolicitacoes(SolicitacaoRepository repository, ValidadorUsuario validador) {
        this.repository = repository;
        this.validador = validador;
    }

    public void criarSolicitacao(Solicitacao solicitacao, Usuario autor) {
        if (validador.ehValido(autor)) {
            repository.salvar(solicitacao);
            System.out.println("Protocolo gerado: " + solicitacao.getProtocolo());
        } else {
            System.out.println("Erro: Usuário não autenticado pelo sistema validador.");
        }
    }
}
