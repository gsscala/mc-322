// Importa a classe Random da biblioteca java.util, que será usada para gerar números aleatórios
import java.util.Random;

// Declaração da classe RandomNumberGenerator, responsável por gerar números aleatórios dentro de um intervalo
public class RandomNumberGenerator {
    // Declaração das variáveis privadas min (mínimo) e max (máximo) para o intervalo
    private int min;
    private int max;

    // Método getter para a variável max (retorna o valor máximo)
    public int getMax() {
        return max;  // Retorna o valor de max
    }

    // Método setter para a variável max (define o valor máximo)
    public void setMax(int max) {
        this.max = max;  // Atribui o valor do parâmetro max à variável max da classe
    }

    // Método getter para a variável min (retorna o valor mínimo)
    public int getMin() {
        return min;  // Retorna o valor de min
    }

    // Método setter para a variável min (define o valor mínimo)
    public void setMin(int min) {
        this.min = min;  // Atribui o valor do parâmetro min à variável min da classe
    }

    // Construtor da classe RandomNumberGenerator, que inicializa os valores de min e max
    public RandomNumberGenerator(int min, int max) {
        setMin(min);  // Chama o setter para inicializar o valor de min
        setMax(max);  // Chama o setter para inicializar o valor de max
    }

    // Método para gerar um número aleatório dentro do intervalo [min, max]
    public int generate() {
        Random random = new Random();  // Cria um objeto da classe Random para gerar números aleatórios
        // Gera um número aleatório entre min e max (inclusive) e retorna o valor gerado
        return random.nextInt(getMax() - getMin() + 1) + getMin();
    }
}
