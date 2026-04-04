public class Usuario {
    private String nome;
    private String tipo;
    private String cpf;
    private String email;
    private boolean anonimo;

    public Usuario(String nome, String tipo, String cpf, String email, boolean anonimo) {
        this.nome = nome;
        this.tipo = tipo;
        this.cpf = cpf;
        this.email = email;
        this.anonimo = anonimo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public String getNomeExibicao() {
        return anonimo ? "Anônimo" : nome;
    }

    public boolean isAdmin() {
       return tipo != null && tipo.equalsIgnoreCase("Admin");
    }
}