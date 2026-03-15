import java.util.ArrayList;
import java.util.List;

public class MemoriaSolicitacaoRepository implements SolicitacaoRepository {
    private List<Solicitacao> lista = new ArrayList<>();

    @Override
    public void salvar(Solicitacao solicitacao) {
        lista.add(solicitacao);
    }

    @Override
    public Solicitacao buscarPorProtocolo(String protocolo) {
        for (Solicitacao s : lista) {
            if (s.getProtocolo().equals(protocolo)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public List<Solicitacao> listarTodas() {
        return new ArrayList<>(lista);
    }
}
