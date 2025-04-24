// Declaração da classe Pair, que representa um par de valores inteiros
class Pair {
    // Declaração das variáveis privadas f e s, que armazenam os dois valores do par
    private int f;
    private int s;

    // Construtor da classe Pair, que inicializa f e s com os valores passados como parâmetros
    public Pair(int f, int s) {
        this.f = f;  // Atribui o valor do parâmetro f à variável f da classe
        this.s = s;  // Atribui o valor do parâmetro s à variável s da classe
    }

    // Método getter para acessar o primeiro valor do par
    public int first() {
        return f;  // Retorna o valor armazenado na variável f
    }

    // Método getter para acessar o segundo valor do par
    public int second() {
        return s;  // Retorna o valor armazenado na variável s
    }
}
