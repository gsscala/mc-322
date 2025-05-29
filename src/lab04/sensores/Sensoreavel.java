package sensores;

import robos.RoboDesligadoException;

public interface Sensoreavel {
    void acionarSensores() throws RoboDesligadoException, NaoSensoriavelException;
}
