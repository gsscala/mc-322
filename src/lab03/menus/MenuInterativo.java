package menus;

import java.util.Scanner;
import robos.Robo;
import robos.RoboAleatorio;
import robos.RoboAereo;
import robos.RoboAtirador;
import robos.RoboTerrestre;
import ambiente.Ambiente;
import sensores.Sensor;
import sensores.SensorProximidade;
import sensores.SensorUmidade;

public class MenuInterativo {
    private boolean terminated = false;
    private Ambiente ambienteAtual;

    public MenuInterativo(Ambiente ambiente) {
        this.ambienteAtual = ambiente;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Simulador de Robôs!");
        System.out.println("Digite 'help' para ver a lista de comandos disponíveis.");
        System.out.println("Ambiente: " + ambienteAtual.getNome());

        while (!terminated) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            handleInput(input);
        }

        System.out.println("Simulação finalizada");
        scanner.close();
    }

    private void handleInput(String input) {
        if (input.isEmpty()) {
            System.out.println("Digite um comando. Use 'help' para ver opções.");
            return;
        }

        String[] args = input.split("\\s+");

        switch (args[0].toLowerCase()) {
            case "end":
                terminated = true;
                break;

            case "help":
                showHelp();
                break;

            case "showrobos":
                showRobos();
                break;

            case "move":
                move(args);
                break;

            case "special":
                skill(args);
                break;

            case "status":
                showStatus();
                break;

            case "monitorar":
                monitorar(args);
                break;

            default:
                System.out.println("Comando inválido. Digite 'help' para ver os comandos disponíveis.");
                break;
        }
    }

    private void showHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println("- end                          : Finaliza a simulação");
        System.out.println("- showRobos                    : Lista todos os robôs no ambiente");
        System.out.println("- status                       : Mostra posição e status dos robôs");
        System.out.println(
                "- move <nome_robo> <x> <y>     : Move o robô especificado pelo deslocamento (deltaX, deltaY)");
        System.out.println("- special <nome_robo> <ação> [...] : Executa uma habilidade especial de um robô");
        System.out.println("  Ações especiais:");
        System.out.println("    - RoboAtirador: atirar");
        System.out.println("    - RoboTerrestre: turbo <deltaX> <deltaY> <velocidade>");
        System.out.println("    - RoboAereo: subir <deltaZ> | descer <deltaZ>");
        System.out.println("    - RoboAleatorio: aleatorio");
        System.out.println(
                "- monitorar <nome_robo> <tipo_sensor> : Usa um sensor específico do robô para monitorar o ambiente");
        System.out.println("  Tipos de sensores:");
        System.out.println("    - proximidade");
        System.out.println("    - umidade");
    }

    private void showRobos() {
        for (Robo robo : ambienteAtual.getRobos()) {
            System.out.println(robo.getNome() + " (" + robo.getClass().getSimpleName() + ")");
        }
    }

    private void showStatus() {
        System.out.println("Status dos robôs:");
        for (Robo robo : ambienteAtual.getRobos()) {
            System.out.printf("%s (%s) - Posição: (%d, %d, %d) - Bateria: %d%n",
                    robo.getNome(),
                    robo.getClass().getSimpleName(),
                    robo.getPosicaoX(),
                    robo.getPosicaoY(),
                    robo.getAltitude(),
                    robo.getBateria());
        }
    }

    private void move(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: move <nome_robo> <deltaX> <deltaY>");
            return;
        }

        Robo robo = findRobo(args[1]);
        if (robo == null) {
            System.out.println("Robô não encontrado: " + args[1]);
            return;
        }

        try {
            int deltaX = Integer.parseInt(args[2]);
            int deltaY = Integer.parseInt(args[3]);
            robo.mover(deltaX, deltaY);
            System.out
                    .println(robo.getNome() + " movido para (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
        } catch (NumberFormatException e) {
            System.out.println("Coordenadas inválidas. Use números inteiros para deltaX e deltaY.");
        }
    }

    private void skill(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: special <nome_robo> <ação> [argumentos]");
            return;
        }

        // Encontra o robô pelo nome
        Robo robo = findRobo(args[1]);
        if (robo == null) {
            System.out.println("Robô não encontrado: " + args[1]);
            return;
        }

        // Ação a ser realizada
        String comando = args[2];

        // Verifica se o robô é do tipo RoboAtirador e executa o comando de atirar
        if (robo instanceof RoboAtirador && comando.equalsIgnoreCase("atirar")) {
            ((RoboAtirador) robo).atirar();
        }
        // Verifica se o robô é do tipo RoboTerrestre e executa o comando de turbo
        else if (robo instanceof RoboTerrestre && comando.equalsIgnoreCase("turbo")) {
            if (args.length < 6) {
                System.out.println("Uso: special " + robo.getNome() + " turbo <deltaX> <deltaY> <velocidade>");
                return;
            }
            int deltaX = Integer.parseInt(args[3]);
            int deltaY = Integer.parseInt(args[4]);
            int velocidade = Integer.parseInt(args[5]);
            ((RoboTerrestre) robo).mover(deltaX, deltaY, velocidade);
        }
        // Verifica se o robô é do tipo RoboAereo (ou RoboAleatorio, pois herda de
        // RoboAereo) e executa os comandos de subir/descer
        else if (robo instanceof RoboAereo && comando.equalsIgnoreCase("subir")) {
            int deltaZ = Integer.parseInt(args[3]);
            ((RoboAereo) robo).subir(deltaZ);
        } else if (robo instanceof RoboAereo && comando.equalsIgnoreCase("descer")) {
            int deltaZ = Integer.parseInt(args[3]);
            ((RoboAereo) robo).descer(deltaZ);
        }
        // Verifica se o robô é do tipo RoboAleatorio (herda de RoboAereo e
        // RoboAtirador) e executa o comando aleatório
        else if (robo instanceof RoboAleatorio && comando.equalsIgnoreCase("aleatorio")) {
            ((RoboAleatorio) robo).mover();
        }
        // Caso o comando não corresponda a nenhuma ação válida
        else {
            System.out.println("Este robô não possui habilidade especial ou comando inválido.");
        }
    }

    private void monitorar(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: monitorar <nome_robo> <tipo_sensor>");
            return;
        }

        Robo robo = findRobo(args[1]);
        if (robo == null) {
            System.out.println("Robô não encontrado: " + args[1]);
            return;
        }

        String sensorTipo = args[2].toLowerCase();
        boolean encontrado = false;

        for (Sensor sensor : robo.getSensores()) {
            if (sensorTipo.equals("proximidade") && sensor instanceof SensorProximidade) {
                ((SensorProximidade) sensor).monitorar();
                encontrado = true;
            } else if (sensorTipo.equals("umidade") && sensor instanceof SensorUmidade) {
                ((SensorUmidade) sensor).monitorar();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Sensor do tipo '" + sensorTipo + "' não encontrado no robô " + args[1]);
        }
    }

    private Robo findRobo(String nome) {
        for (Robo robo : ambienteAtual.getRobos()) {
            if (robo.getNome().equalsIgnoreCase(nome)) {
                return robo;
            }
        }
        return null;
    }
}
