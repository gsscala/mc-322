package main;
import ambiente.Ambiente;
import comunicacao.CentralComunicacao;
import obstaculos.Obstaculo;
import obstaculos.TipoObstaculo;
import robos.Robo;
import robos.RoboAereo;
import robos.RoboAleatorio;
import robos.RoboAtirador;
import robos.RoboDistraido;
import robos.RoboTerrestre;
import sensores.Sensor;
import sensores.SensorProximidade;
import sensores.SensorUmidade;
import menus.MenuInterativo;

public class Main {
    // Método principal (entry point) da aplicação
    public static void main(String[] args) {
        Ambiente ilhas_dog = new Ambiente(75, 75, 40, "ilhas dog", 5);

        CentralComunicacao centralComunicacao = new CentralComunicacao();
        ilhas_dog.adicionarCentralComunicacao(centralComunicacao); // Associando a central de comunicação ao ambiente

        // Criando diferentes tipos de robôs com seus respectivos parâmetros
        RoboAleatorio roboaleatorio = new RoboAleatorio("random", 0, 0, "Norte", 50);  
        RoboAtirador roboatirador = new RoboAtirador("praprapra", 17, 13, "Leste", "Ak-47");
        RoboTerrestre roboterrestre = new RoboTerrestre("cebolinha", 69, 69, "Sul", 300);        
        RoboAereo roboaereo = new RoboAereo("dog drone", 33, 70, "Norte", 100);  
        Robo robo = new Robo("npc", 20, 0, "Norte");
        RoboDistraido roboDistraido = new RoboDistraido("lerdo", 10, 10, "Leste", 40);

        // Criando sensores alternados para cada robô
        Sensor sensorAleatorio = new SensorProximidade(10);  // SensorProximidade para RoboAleatorio
        Sensor sensorAtirador = new SensorUmidade(20);      // SensorUmidade para RoboAtirador
        Sensor sensorTerrestre = new SensorProximidade(30);  // SensorProximidade para RoboTerrestre
        Sensor sensorAereo = new SensorUmidade(20);         // SensorUmidade para RoboAereo
        Sensor sensorNpc = new SensorProximidade(10);       // SensorProximidade para Robo
        Sensor sensorDistraido = new SensorProximidade(40);       // SensorProximidade para RoboDistraido

        // Criando obstáculos
        Obstaculo poca = new Obstaculo(10, 10, 12, 12, TipoObstaculo.POCA); // Tipo POCA
        Obstaculo pedra = new Obstaculo(20, 20, 22, 22, TipoObstaculo.PEDRA); // Tipo PEDRA
        Obstaculo predio = new Obstaculo(30, 30, 32, 32, TipoObstaculo.PREDIO); // Tipo PREDIO
        Obstaculo cacto = new Obstaculo(40, 40, 42, 42, TipoObstaculo.CACTO); // Tipo CACTO

        // Atribuindo sensores aos robôs
        roboaleatorio.addSensor(sensorAleatorio);
        roboatirador.addSensor(sensorAtirador);
        roboterrestre.addSensor(sensorTerrestre);
        roboaereo.addSensor(sensorAereo);
        robo.addSensor(sensorNpc);
        roboDistraido.addSensor(sensorDistraido);

        // Adicionando os robôs ao ambiente
        ilhas_dog.adicionarEntidade(roboaleatorio);
        ilhas_dog.adicionarEntidade(roboatirador);
        ilhas_dog.adicionarEntidade(roboterrestre);
        ilhas_dog.adicionarEntidade(roboaereo);
        ilhas_dog.adicionarEntidade(robo);
        ilhas_dog.adicionarEntidade(roboDistraido);

        // Adicionando os obstáculos ao ambiente
        ilhas_dog.adicionarEntidade(poca);
        ilhas_dog.adicionarEntidade(pedra);
        ilhas_dog.adicionarEntidade(predio);
        ilhas_dog.adicionarEntidade(cacto);

        // Inicializando o menu interativo com o ambiente
        MenuInterativo menu = new MenuInterativo(ilhas_dog);

        // Iniciando o menu
        menu.run();
    }
}
