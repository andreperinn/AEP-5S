import java.util.List;

public class ServicoSolicitacoes {
    private final SolicitacaoRepository repository;
    private final ValidadorUsuario validador;

    public ServicoSolicitacoes(SolicitacaoRepository repository, ValidadorUsuario validador) {
        this.repository = repository;
        this.validador = validador;
    }

    public String registrarNovaSolicitacao(String desc, String local, Categoria cat, Usuario autor) {

        if (desc == null || desc.trim().isEmpty()) {
            return "ERRO: Descrição obrigatória";
        }

        if (local == null || local.trim().isEmpty()) {
            return "ERRO: local obrigatório";
        }

        if (validador.ehValido(autor)) {
            Solicitacao nova = new Solicitacao(cat, desc, local, autor);
            repository.salvar(nova);
            return nova.getProtocolo();
        }

        return "ERRO: Usuário não validado";
    }

    public Solicitacao buscarPeloProtocolo(String protocolo) {
        return repository.buscarPorProtocolo(protocolo);
    }

    public List<Solicitacao> listarTodas() {
        return repository.listarTodas();
    }

    public void verificarPrazos() {
        for (Solicitacao s : repository.listarTodas()) {
            s.verificarEAvancarStatus();
        }
    }

    public boolean atualizarStatus(String protocolo, String novoStatus, String observacao) {
        Solicitacao solicitacao = repository.buscarPorProtocolo(protocolo);

        if (solicitacao == null) {
            return false;
        }

        solicitacao.adicionarHistorico(novoStatus, observacao);
        return true;
    }
}