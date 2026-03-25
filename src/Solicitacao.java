import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Solicitacao {
    private static int contadorProtocolo = 1000;
    private String protocolo;
    private Categoria categoria;
    private String descricao;
    private String localizacao;
    private String status;
    private Usuario autor;
    private LocalDate dataAbertura;
    private LocalDate prazoAtual;

    public Solicitacao(Categoria categoria, String descricao, String localizacao, Usuario autor) {
        this.protocolo = "2026-" + contadorProtocolo ;
        contadorProtocolo++;

        this.categoria = categoria;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.autor = autor;
        this.status = "aberto";
        this.historico.add(new HistoricoStatus("aberto", "Solicitação criada."));
        this.dataAbertura = LocalDate.now();
        this.prazoAtual = calcularPrazoUteis(dataAbertura, 3);
    }

    private List<HistoricoStatus> historico = new ArrayList<>();

    public String getDescricao() {
        return descricao;
    }

    public String getProtocolo() {
        return this.protocolo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public LocalDate getPrazoAtual() {
        return prazoAtual;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public List<HistoricoStatus> getHistorico() {
        return historico;
    }


    public void adicionarHistorico(String novoStatus, String comentario) {
        this.status = novoStatus;
        this.historico.add(new HistoricoStatus(novoStatus, comentario));
    }


    // Calcula data pulando finais de semana
    private LocalDate calcularPrazoUteis(LocalDate inicio, int diasUteis) {
        LocalDate data = inicio;
        int contagem = 0;
        while (contagem < diasUteis) {
            data = data.plusDays(1);
            if (data.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    data.getDayOfWeek() != DayOfWeek.SUNDAY) {
                contagem++;
            }
        }
        return data;
    }

    // Função de verificar os prazos
    //REGRA: Em execução 3 dias
    //resolvido 5 dias
    //após encerrado
    //se houver prazo vencido sera informado
    public void verificarEAvancarStatus() {
        LocalDate hoje = LocalDate.now();

        if (status.equals("encerrado")) {
            return;
        }

        if (hoje.isAfter(prazoAtual)) {
            switch (status) {
                case "triagem":
                    adicionarHistorico("em execucao", "Avanco apos 3 dias uteis.");
                    prazoAtual = calcularPrazoUteis(hoje, 5);
                    break;
                case "em execucao":
                    adicionarHistorico("resolvido", "Avanco apos 5 dias uteis.");
                    prazoAtual = calcularPrazoUteis(hoje, 0);
                    break;
                case "resolvido":
                    adicionarHistorico("encerrado", "Encerrado automaticamente.");
                    break;
                default:
                    adicionarHistorico("ATRASADO", "Prazo vencido sem movimentacao.");
                    break;
            }
        }
    }


}
