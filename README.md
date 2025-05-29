## Simulador de Robôs — Laboratórios MC322 (UNICAMP, 2025/1)

**Autores:** Giovanni Santos Scalabrin (RA 281210) & Rodrigo Banin Ferraz Camargo (RA 238257)

---

## 1. Visão Geral

O projeto simula múltiplos robôs operando em um ambiente tridimensional com obstáculos estáticos, entidades dinâmicas e sistema de comunicação. O simulador aplica conceitos avançados de POO como herança múltipla via interfaces, polimorfismo, tratamento robusto de exceções e alocação dinâmica em coleções. O sistema permite que robôs com comportamentos distintos interajam, movam-se e executem tarefas cooperativas em tempo real.

Implementações-chave:

-    Ambiente 3D com mapa de ocupação dinâmico (TipoEntidade[][][])

-    Sistema polimórfico de entidades via interface Entidade

-    Comportamentos especializados através de interfaces (Sensoreavel, Comunicavel)

-    Herança múltipla com interfaces customizadas (Autonomo, Explorador, Atacante)

-    Sistema robusto de exceções com 6 classes personalizadas

-    Menu interativo para controle e monitoramento em tempo real

### Ferramentas
- **IDE:** Visual Studio Code
- **Java:** OpenJDK 21.0.5
- **Diagramação** PlantUML
---

### Compilação e Execução
1. **Compilar o laboratório desejado**
   ```bash
   javac -d bin $(find src/lab04/ -name "*.java")
   ```
2. **Executar a aplicação**
   ```bash
   java -cp bin Main
   ```
> Substitua `lab04` pelo diretório correspondente ao laboratório que deseja compilar.

---
## 4. Estrutura do Projeto

```
src/
├── lab04/
│   ├── Main.java
│   ├── ambiente/
│   │   ├── Ambiente.java
│   │   └── ForaMapaException.java
│   ├── comunicacao/
│   │   ├── CentralComunicacao.java
│   │   ├── Comunicavel.java
│   │   ├── Mensagem.java
│   │   └── ErroComunicacaoException.java
│   ├── entity/
│   │   ├── Entidade.java
│   │   └── TipoEntidade.java
│   ├── obstaculos/
│   │   ├── Obstaculo.java
│   │   ├── TipoObstaculo.java
│   │   └── ColisaoException.java
│   ├── robos/
│   │   ├── Robo.java
│   │   ├── RoboTerrestre.java
│   │   ├── RoboAereo.java
│   │   ├── RoboAleatorio.java
│   │   ├── RoboAtirador.java
│   │   ├── Ladrao.java
│   │   ├── Explodidor.java
│   │   ├── EnchedorDeSaco.java
│   │   ├── EstadoRobo.java
│   │   ├── RoboNotFoundException.java
│   │   ├── TaskNotFoundException.java
│   │   └── RoboDesligadoException.java
│   ├── sensores/
│   │   ├── Sensor.java
│   │   ├── SensorProximidade.java
│   │   ├── SensorUmidade.java
│   │   ├── Sensoreavel.java
│   │   └── NaoSensoriavelException.java
│   ├── utils/
│   │   ├── RandomNumberGenerator.java
│   │   ├── RandomStringGenerator.java
│   │   └── DistanceCalculator.java
│   └── menus/
│       └── MenuInterativo.java
└── lab04.puml
```

---

## 5. Descrição de Arquivos e Classes

### 5.1 Main.java

* **Pacote**: `lab04`
* **Responsabilidade**: Inicializa o simulador. Lê parâmetros pelo `MenuInterativo`, instanciar `Ambiente`, registra robôs em `CentralComunicacao` e inicia o loop de simulação.
* **Métodos**:

  * `public static void main(String[] args)`

    * Comanda fluxo: `criarAmbiente()`, `configurarRobos()`, `loopSimulacao()`.
  * `private static void loopSimulacao()`

    * Itera ticks de tempo, solicita a cada `Robo` que execute `executarTask()`, atualiza estado e renderiza console.

---

## 6. Pacote `ambiente`

### Ambiente.java

* **Atributos**:

  * `private int largura, altura;` — dimensões do mapa.
  * `private Map<String, Entidade> entidades;` — chave: ID, valor: objeto.
  * `private Map<Point, Obstaculo> obstaculos;` — obstáculos por coordenada.
* **Métodos**:

  * `public Ambiente(int largura, int altura)` — construtor, lança `IllegalArgumentException` se tamanho <= 0.
  * `public void adicionarEntidade(Entidade e)` — registra entidade; lança `ForaMapaException` se fora dos limites.
  * `public void adicionarObstaculo(Obstaculo o)` — verifica `o.getX(), o.getY()`, joga `ForaMapaException`.
  * `public void moverRobo(Robo r, int x, int y)` — valida `dentroDoMapa`, testa colisão em `obstaculos.get(new Point(x,y))`, lança `ColisaoException` ou `ForaMapaException`, e chama `r.setPosicao(x,y)`.
  * `public Entidade getEntidade(int x, int y)` — retorna a entidade nessa posição ou `null`.
  * `public boolean dentroDoMapa(int x, int y)` — retorna `0 <= x < largura && 0 <= y < altura`.

### ForaMapaException.java

* **Estende**: `RuntimeException`
* **Construtores**:

  * `public ForaMapaException(String mensagem)`

---

## 7. Pacote `comunicacao`

### CentralComunicacao.java

* **Padrão**: Singleton
* **Atributos**:

  * `private static CentralComunicacao instancia;`
  * `private Map<String, Comunicavel> participantes;`
* **Métodos**:

  * `public static synchronized CentralComunicacao getInstance()`
  * `public void registrar(Comunicavel c)` — adiciona `c.getId()` em `participantes`.
  * `public void enviar(String idOrigem, String idDestino, Mensagem msg)` — busca `destino`, se não existe lança `ErroComunicacaoException`; chama `destino.receber(msg)`.

### Comunicavel.java

* **Método**:

  * `void receber(Mensagem msg)`
  * `String getId()`

### Mensagem.java

* **Atributos**:

  * `private String conteudo;`
  * `private LocalDateTime timestamp;`
* **Métodos**:

  * `public Mensagem(String conteudo)` — atribui `timestamp = LocalDateTime.now()`.
  * `public String getConteudo()`, `public LocalDateTime getTimestamp()`.

### ErroComunicacaoException.java

* **Estende**: `Exception`
* **Descrição**: falha ao enviar mensagem (destino inexistente, central não inicializada).

---

## 8. Pacote `entity`

### Entidade.java

* **Classe abstrata** que define toda coisa no ambiente.
* **Atributos**:

  * `protected String id;`
  * `protected int x, y;`
  * `protected TipoEntidade tipo;`
* **Métodos**:

  * `public String getId()`
  * `public int getX()`, `public int getY()`
  * `public void setPosicao(int x, int y)` — valida `Ambiente.dentroDoMapa`.
  * `public TipoEntidade getTipo()`

### TipoEntidade.java

* **Enum**: `ROBO`, `OBSTACULO`, `PONTO_INTERESSE`, `BASE_COMUNICACAO`

---

## 9. Pacote `obstaculos`

### Obstaculo.java

* **Atributos**:

  * `private String id;`
  * `private int x, y;`
  * `private TipoObstaculo tipo;`
* **Métodos**:

  * `public boolean bloqueiaMovimento()` — retorna `true` para `PAREDE`, `LAGO`; `false` para `AREIA`.
  * `public int getX()`, `public int getY()`, `public TipoObstaculo getTipo()`, `public String getId()`.

### TipoObstaculo.java

* **Enum**: `PAREDE`, `LAGO`, `AREIA`, `OJEITO` (permeável)

### ColisaoException.java

* **Estende**: `Exception`
* **Descrição**: lançada em `Ambiente.moverRobo` ao tentar transpor obstáculo com `bloqueiaMovimento() == true`.

---

## 10. Pacote `robos`

### Robo.java (abstrata)

* **Herda**: `Entidade implements Comunicavel`
* **Atributos**:

  * `protected EstadoRobo estado;`
  * `protected Queue<Task> tarefas;` — fila de tarefas.
* **Métodos**:

  * `public void moverPara(int x, int y) throws ForaMapaException, ColisaoException, RoboDesligadoException`
  * `public abstract void executarTask() throws TaskNotFoundException, RoboDesligadoException`
  * `public void comunicar(String idDestino, Mensagem msg) throws ErroComunicacaoException, RoboDesligadoException`
  * `public void receber(Mensagem msg)` — implementa `Comunicavel`.
  * `public EstadoRobo getEstado()`
  * `public void desligar()`, `public void ligar()` — altera `estado`.

### Subclasses de Robo

* **RoboTerrestre.java**: velocidade fixa, não ultrapassa `Lago`.
* **RoboAereo.java**: ignora obstáculos, custo de movimento uniforme.
* **RoboAleatorio.java**: estrategia de movimento que seleciona direção aleatória (usa `RandomNumberGenerator`).
* **RoboAtirador.java**: método `executarTask()` tenta destruir `obstaculos` do tipo `OJEITO`.
* **Ladrao.java**: coleta entidades de `PONTO_INTERESSE` (adiciona ao inventário interno `List<Entidade>`).
* **Explodidor.java**: remove obstáculos destrutíveis.
* **EnchedorDeSaco.java**: simula coleta de recursos, mantém `private int capacidadeAtual, capacidadeMaxima;`.

### EstadoRobo.java

* **Enum**: `DESLIGADO`, `IDLE`, `EM_MOVIMENTO`, `EXECUTANDO_TASK`

### Exceções específicas

* **RoboNotFoundException**: robo não registrado em central.
* **TaskNotFoundException**: fila de tarefas vazia.
* **RoboDesligadoException**: ação de robo cujo `estado == DESLIGADO`.

---

## 11. Pacote `sensores`

### Sensor.java

* **Interface**:

  * `double ler() throws NaoSensoriavelException;`

### SensorProximidade.java

* **Atributos**: `private double alcanceMaximo;`
* **Método**:

  * `public double ler()` — retorna a menor distância até obstáculo (usa `Ambiente.getEntidades`);

### SensorUmidade.java

* **Atributos**: nenhum
* **Método**:

  * `public double ler()` — retorna valor randômico de umidade (`0.0 a 1.0`).

### Sensoreavel.java

* **Interface**:

  * `List<Sensor> getSensores()`

### NaoSensoriavelException.java

* **Estende**: `Exception`
* **Descrição**: lançada em `Entidade.getSensores()` se não implementar `Sensoreavel`.

---

## 12. Pacote `utils`

### RandomNumberGenerator.java

* **Métodos estáticos**:

  * `public static int gerarInt(int min, int max)`
  * `public static double gerarDouble()`

### RandomStringGenerator.java

* **Métodos estáticos**:

  * `public static String gerarId(int tamanho)` — combina letras e números.

### DistanceCalculator.java

* **Métodos estáticos**:

  * `public static double euclidiana(int x1, int y1, int x2, int y2)`
  * `public static int manhattan(int x1, int y1, int x2, int y2)`

---

## 13. Pacote `menus`

### MenuInterativo.java

* **Métodos**:

  * `public void exibirOpcoes()` — mostra menu principal com opções: configurar ambiente, adicionar robô, iniciar simulação.
  * `public String lerEntrada(String prompt)` — retorna texto do console.
  * `public void configurarAmbiente()` — solicita `largura`, `altura`; instancia `Ambiente`.
  * `public void configurarRobo()` — escolhe tipo de `Robo` via fábrica, define posição inicial, registra em `CentralComunicacao`, adiciona ao `Ambiente`.

---

## 14. Diagrama UML

O arquivo `lab04.puml` contém todas as classes, interfaces e relações (associação, herança, dependência). Gere com:

```bash
plantuml src/lab04/lab04.puml
```

---

*Este README segue boas práticas: detalhamento completo de arquivos, classes, atributos, métodos, exceções, padrões de projeto e instruções claras de uso.*

