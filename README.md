Jogo da Velha em Java (Arquitetura MVC)

Este repositório contém a implementação de um Jogo da Velha (Tic-Tac-Toe) utilizando a linguagem Java e a biblioteca gráfica Swing. O projeto foi estruturado seguindo rigorosos padrões de Engenharia de Software.

Arquitetura de Sistemas

O projeto utiliza o padrão de design estrutural Model-View-Controller (MVC) modificado para duas camadas puras, garantindo o encapsulamento e a separação de responsabilidades (Separation of Concerns):

Model (TabuleiroLogico.java): Atua como uma Máquina de Estados Finita. É responsável exclusivo pela manipulação da memória (o array estado), validação de transições (regras de vitória/empate) e não possui conhecimento de elementos de interface gráfica.

View (TelaJogo.java): Responsável por capturar a entrada do utilizador de forma assíncrona (Event Dispatch Thread) e projetar o estado atual do Model no ecrã. A comunicação com o Model é feita de forma estritamente unidirecional através de métodos acessores (Getters) e funções de transição encapsuladas.

Prevenção de Condição de Corrida (Race Condition)

A interface implementa uma trava de estado (State Lock) assíncrona. Durante o turno da máquina, gerido por um Timer do Swing, a interface bloqueia eventos de clique do utilizador para impedir alterações corrompidas de estado antes que o algoritmo do computador finalize a sua rotina computacional.

Estrutura de Diretórios

/src: Contém o código-fonte (.java).

/src/assets: Contém os recursos visuais estáticos, carregados dinamicamente na memória via ClassLoader.

/bin: Diretório de saída (Output Directory) onde o compilador injeta os ficheiros de máquina (.class).

Execução

Para executar o ficheiro binário compilado (.jar):

java -jar JogoDaVelha.jar
