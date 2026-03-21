import java.util.List;

public class ServicoSolicitacoes {
    private final SolicitacaoRepository repository;
    private final ValidadorUsuario validador;

    public ServicoSolicitacoes(SolicitacaoRepository repository, ValidadorUsuario validador) {
        this.repository = repository;
        this.validador = validador;
    }

    public String registrarNovaSolicitacao(String desc, String local, Categoria cat, Usuario autor) {
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
}
