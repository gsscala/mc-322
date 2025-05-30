import ambiente.Ambiente;
import obstaculos.Obstaculo;
import obstaculos.TipoObstaculo;
import robos.Robo;
import robos.RoboAereo;
import robos.RoboAleatorio;
import robos.RoboAtirador;
import robos.RoboTerrestre;
import sensores.Sensor;
import sensores.SensorProximidade;
import sensores.SensorUmidade;
import menus.MenuInterativo;

public class Main {
    // Método principal (entry point) da aplicação
    public static void main(String[] args) {
        // Criando o ambiente "ilhas_dog" com as dimensões 200x100 e capacidade para 200 robôs
        Ambiente ilhas_dog = new Ambiente(200, 100, "ilhas dog", 5);
        
        // Criando diferentes tipos de robôs com seus respectivos parâmetros
        RoboAleatorio roboaleatorio = new RoboAleatorio("random", 0, 0, "Norte", 50);  
        RoboAtirador roboatirador = new RoboAtirador("praprapra", 17, 13, "Leste", 100, "Ak-47");
        RoboTerrestre roboterrestre = new RoboTerrestre("cebolinha", 69, 69, "Sul", 300);        
        RoboAereo roboaereo = new RoboAereo("dog drone", 33, 70, "Norte", 100);  
        Robo robo = new Robo("npc", 20, 0, "Norte");

        // Criando sensores alternados para cada robô
        Sensor sensorAleatorio = new SensorProximidade(10);  // SensorProximidade para RoboAleatorio
        Sensor sensorAtirador = new SensorUmidade(20);      // SensorUmidade para RoboAtirador
        Sensor sensorTerrestre = new SensorProximidade(30);  // SensorProximidade para RoboTerrestre
        Sensor sensorAereo = new SensorUmidade(20);         // SensorUmidade para RoboAereo
        Sensor sensorNpc = new SensorProximidade(10);       // SensorProximidade para Robo

        // Atribuindo sensores aos robôs
        roboaleatorio.addSensor(sensorAleatorio);
        roboatirador.addSensor(sensorAtirador);
        roboterrestre.addSensor(sensorTerrestre);
        roboaereo.addSensor(sensorAereo);
        robo.addSensor(sensorNpc);

        // Adicionando os robôs ao ambiente
        ilhas_dog.adicionarRobo(roboaleatorio);
        ilhas_dog.adicionarRobo(roboatirador);
        ilhas_dog.adicionarRobo(roboterrestre);
        ilhas_dog.adicionarRobo(roboaereo);
        ilhas_dog.adicionarRobo(robo);

        // Criando obstáculos
        Obstaculo pocao = new Obstaculo(10, 10, 15, 15, TipoObstaculo.POCA); // Tipo POCA
        Obstaculo pedra = new Obstaculo(20, 20, 25, 25, TipoObstaculo.PEDRA); // Tipo PEDRA
        Obstaculo predio = new Obstaculo(30, 30, 35, 35, TipoObstaculo.PREDIO); // Tipo PREDIO
        Obstaculo cacto = new Obstaculo(40, 40, 45, 45, TipoObstaculo.CACTO); // Tipo CACTO

        // Adicionando os obstáculos ao ambiente
        ilhas_dog.adicionarObstaculo(pocao);
        ilhas_dog.adicionarObstaculo(pedra);
        ilhas_dog.adicionarObstaculo(predio);
        ilhas_dog.adicionarObstaculo(cacto);

        // Inicializando o menu interativo com o ambiente
        MenuInterativo menu = new MenuInterativo(ilhas_dog);

        // Iniciando o menu
        menu.run();
    }
}
