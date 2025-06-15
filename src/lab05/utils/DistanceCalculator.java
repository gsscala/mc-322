// Declaração do pacote ao qual esta classe pertence
// Pacotes organizam classes relacionadas logicamente
package utils;

// Importação da classe Entidade que será utilizada neste código
// A classe Entidade está em um pacote diferente, por isso precisa ser importada
import entity.Entidade;

/**
 * Classe responsável por calcular a distância entre dois pontos no plano cartesiano.
 * Pode receber as coordenadas diretamente ou através de objetos do tipo Entidade.
 * Segue o princípio de responsabilidade única (SOLID), tendo apenas uma função específica.
 */
public class DistanceCalculator {
    
    // Variáveis de instância para armazenar as coordenadas dos pontos
    // São declaradas como private para encapsulamento (boas práticas de OO)
    private int posX1, posX2, posY1, posY2;

    /**
     * Construtor que recebe as coordenadas diretamente como inteiros.
     * 
     * @param x1 Coordenada X do primeiro ponto
     * @param x2 Coordenada X do segundo ponto
     * @param y1 Coordenada Y do primeiro ponto
     * @param y2 Coordenada Y do segundo ponto
     */
    public DistanceCalculator(int x1, int x2, int y1, int y2) {
        // Atribui os valores passados como parâmetros às variáveis de instância
        posX1 = x1;
        posX2 = x2;
        posY1 = y1;
        posY2 = y2;
    }

    /**
     * Construtor alternativo que recebe duas entidades e extrai suas coordenadas.
     * Demonstra sobrecarga de métodos (polimorfismo em tempo de compilação).
     * 
     * @param entidade1 Primeira entidade para cálculo de distância
     * @param entidade2 Segunda entidade para cálculo de distância
     */
    public DistanceCalculator(Entidade entidade1, Entidade entidade2) {
        // Obtém as coordenadas X e Y de cada entidade usando seus métodos getters
        // Preserva o encapsulamento, acessando os dados apenas através de métodos
        posX1 = entidade1.getPosicaoX();
        posX2 = entidade2.getPosicaoX();
        posY1 = entidade1.getPosicaoY();
        posY2 = entidade2.getPosicaoY();
    }

    /**
     * Calcula a distância euclidiana entre os dois pontos armazenados.
     * Utiliza a fórmula matemática: √((x1-x2)² + (y1-y2)²)
     * 
     * @return Distância calculada como um valor double de precisão dupla
     */
    public double calculateDistance() {
        // Calcula a diferença entre as coordenadas X e Y
        int dx = posX1 - posX2;
        int dy = posY1 - posY2;
        
        // Retorna a raiz quadrada da soma dos quadrados das diferenças
        // Math.sqrt é um método da biblioteca padrão para cálculo de raiz quadrada
        return Math.sqrt(dx * dx + dy * dy);
    }
}