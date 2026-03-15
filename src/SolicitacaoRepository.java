import java.util.List;

public interface SolicitacaoRepository {
    void salvar(Solicitacao solicitacao);

    Solicitacao buscarPorProtocolo(String protocolo);
    List<Solicitacao> listarTodas();
}
