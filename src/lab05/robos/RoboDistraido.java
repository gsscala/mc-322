package robos;
import utils.RandomNumberGenerator;

public class RoboDistraido extends AgenteInteligente {

    private int nivelDistracao;

    public RoboDistraido(String nome, int xIni, int yIni, String direcao, int nivelDistracao) {
        super(nome, xIni, yIni, direcao);
        this.nivelDistracao = nivelDistracao;
        setDescricao("Robô diagnosticado com TDAH");
    }

    public void setNivelDistracao(int nivelDistracao) {
        this.nivelDistracao = nivelDistracao;
    }

    public int getNivelDistracao() {
        return nivelDistracao;
    }

    public void executarMissao() {
        // Robô tem chance de não realizar a missão devido ao nível de distração
        if (new RandomNumberGenerator(0, 100).generate() <= nivelDistracao) {
            System.out.println("O robô " + getNome() + " está distraído e não consegue realizar a missão de " + missao);
            return; 
        }

        getMissao().executarMissao();

    }
    
}
