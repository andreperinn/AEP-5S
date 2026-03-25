import java.time.LocalDateTime;

// Após aberta solicitação tem status (ABERTO, EM ANDAMENTO, ENCERRADO)

public class HistoricoStatus {
    private String status;
    private String comentario;
    private LocalDateTime data;

    public HistoricoStatus(String status, String comentario) {
        this.status = status;
        this.comentario = comentario;
        this.data = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public String getComentario() {
        return comentario;
    }

    public LocalDateTime getData() {
        return data;
    }
}