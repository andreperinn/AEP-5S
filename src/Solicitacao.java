public class Solicitacao {
    private static int contadorProtocolo = 1000;
    private String protocolo;
    private Categoria categoria;
    private String descricao;
    private String localizacao;
    private String status;
    private Usuario autor;

    public Solicitacao(Categoria categoria, String descricao, String localizacao, Usuario autor) {
        this.protocolo = "2026-" + contadorProtocolo;
        contadorProtocolo++;

        this.categoria = categoria;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.autor = autor;
        this.status = "aberto";
    }

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
}
