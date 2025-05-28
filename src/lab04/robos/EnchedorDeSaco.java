package robos;
import comunicacao.Comunicavel;

public interface EnchedorDeSaco {
    void encherOSaco(Comunicavel roboASerSpammado, int numeroDeVezes) throws RoboDesligadoException;
}
