package robos;

import utils.RandomNumberGenerator;
import sensores.NaoSensoriavelException;

public class RoboDistraido extends AgenteInteligente {

    private double nivelDistracao;

    public RoboDistraido(String nome, int xIni, int yIni, String direcao, double nivelDistracao) {
        super(nome, xIni, yIni, direcao);
        this.nivelDistracao = nivelDistracao;
    }

    public void setNivelDistracao(double nivelDistracao) {
        this.nivelDistracao = nivelDistracao;
    }

    public double getNivelDistracao() {
        return nivelDistracao;
    }

    public void executarMissao() {
        if (new RandomNumberGenerator(0, 100).generate() > nivelDistracao) {

            try {
                getMissao().executar(this, getAmbiente());
            } catch (NaoSensoriavelException e) {
                // Ignora exceção
            }
        }      

    }
    
}
