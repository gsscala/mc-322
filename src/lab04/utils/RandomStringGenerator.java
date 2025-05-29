// Declaração do pacote ao qual esta classe pertence
// Pacotes organizam classes relacionadas logicamente e facilitam a modularização
package utils;

// Importação da classe Random da biblioteca padrão Java
// Necessária para geração de números aleatórios
import java.util.Random;

/**
 * Classe utilitária final para geração de strings aleatórias com caracteres imprimíveis.
 * 
 * <p>Esta classe é marcada como final para impedir herança, já que foi projetada
 * para ser uma classe utilitária com comportamento completo e imutável.</p>
 * 
 * <p>Seguindo o princípio de responsabilidade única, tem apenas a função de
 * gerar strings aleatórias com parâmetros configuráveis.</p>
 */
public final class RandomStringGenerator {

    /**
     * Gera uma string aleatória contendo caracteres ASCII imprimíveis.
     * 
     * @param stringSize Tamanho da string a ser gerada (quantidade de caracteres)
     * @param numCharacters Número de caracteres diferentes a serem usados (1-95)
     * @return String aleatória gerada com os parâmetros especificados
     * @throws IllegalArgumentException Se numCharacters estiver fora do intervalo 1-95
     */
    public static String generatePrintableRandomString(int stringSize, int numCharacters) {
        // Validação do parâmetro numCharacters
        // ASCII imprimível vai de 32 a 126 (95 caracteres no total)
        if (numCharacters < 1 || numCharacters > 95) {
            // Lança exceção com mensagem descritiva para ajudar no debugging
            throw new IllegalArgumentException("numCharacters must be between 1 and 95");
        }
        
        // Cria uma instância de Random para geração de números aleatórios
        Random random = new Random();
        
        // StringBuilder é mais eficiente para concatenação em loops do que String
        // Inicializado com capacidade inicial igual ao tamanho da string desejada
        StringBuilder sb = new StringBuilder(stringSize);
        
        // Loop para gerar cada caractere da string
        for (int i = 0; i < stringSize; i++) {
            // Gera um caractere aleatório no intervalo [32, 32+numCharacters-1]
            // random.nextInt(numCharacters) gera número entre 0 e numCharacters-1
            // Somando 32, mapeamos para os primeiros 'numCharacters' caracteres imprimíveis
            char randomChar = (char) (random.nextInt(numCharacters) + 32);
            
            // Adiciona o caractere gerado ao StringBuilder
            sb.append(randomChar);
        }
        
        // Converte o StringBuilder para String e retorna o resultado
        return sb.toString();
    }
}