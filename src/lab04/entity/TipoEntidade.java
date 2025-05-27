package entity;

public enum TipoEntidade {
    VAZIO('_'),
    ROBO('0'),
    OBSTACULO('X'),
    DESCONHECIDO('?');
    
    private final char representacao;  // Representação do tipo de entidade como um caractere

    TipoEntidade(char representacao) {
        this.representacao = representacao;  // Define a representação do tipo de entidade
    }

    public char getRepresentacao() {
        return representacao;  // Retorna a representação do tipo de entidade
    }
}
