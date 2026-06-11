import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

/////////////////////////////////////////////////////////////
///                                                       ///
///                  CONFIGURAÇÃO DA TELA                 ///
///                                                       ///
/////////////////////////////////////////////////////////////

public class TelaJogo extends JFrame {
    // Atributos da classe
    Image imgVazia    = new ImageIcon("botao_vazio.png").getImage(); // Carrega a imagem original para manter a qualidade
    Image imgX        = new ImageIcon("botao_x.png").getImage();
    Image imgO        = new ImageIcon("botao_o.png").getImage();
    ImageIcon icone   = new ImageIcon("velha.png"); // Cria um icone de imagem
    Border border     = BorderFactory.createLineBorder(Color.BLACK,3); // Cria a borda - Não está fazendo nada
    char simboloAtual = ' '; //Variavel que vai pegar o simbolo do jogador atual
    private char[] estado = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

    JLabel label = new JLabel(); // Cria o label
    private JButton[] botoes = new JButton[9]; // Cria os botoes

    // METODOS
    // Redimenciona a imagem para que ela fique do tamanho certo quando mudar. Não funciona direito
    public void inserirImagem(JButton botao, Image imagem){
        int largura = botao.getWidth();
        int altura  = botao.getHeight();
        Image imgRedim = imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH); // Redimensiona a imagem
        botao.setIcon(new ImageIcon(imgRedim));
    };

    // TELA DO JOGO
    public TelaJogo(){ // Se passar argumentos, ele precisa desses argumentos quando chamado no main
    // Construtor da classe
        this.setTitle("Jogo da Velha"); // Titulo da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sai do programa quando frame é fechado
        this.setSize(800,800); // Determina as dimensões x e y da janela
        this.getContentPane().setBackground(new Color(0,0,0)); // Colore background
        this.setLayout(new GridLayout(3, 3)); // Cria a grid que vai ser o tabuleiro 
        

        // Preenche os espaços com os botões vazios     
        for(int i = 0; i < botoes.length; i++) {
            // Precisamos criar constantes com "final" porque se usarmos variaveis, elas podem mudar ou não existir mais antes do jogo carregar.
            botoes[i] = new JButton();
            botoes[i].setBorder(border); // Passa a border criada para os botões.
            botoes[i].setFont(new Font("Arial", Font.BOLD, 60));
            final JButton btnAtual = botoes[i]; // captura uma "imagem" da variavel do botao nesse momento
            final int indiceAtual  = i; // Captura uma imagem do indice
            System.out.println(botoes[i]);

            // Lógica do click
            btnAtual.addActionListener(e -> {
                estado[indiceAtual] = 'o'; // Salva o dado na memoria (model)
                inserirImagem(btnAtual, imgO); // Atualiza a tela (view)
            });

            // Listener de redimensionamento para deixar a imgem responsiva.
            btnAtual.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e){
                    // O redimencionador vai atualizar a imagem para a que foi passada como parametro sempre que ele for chamado (quando a janela muda de tamanho.)
                    char simboloAtual = estado[indiceAtual];

                    // Pega cada imagem baseada no turno e redimenciona
                    if(simboloAtual == 'x'){
                        inserirImagem(btnAtual, imgX);
                    }
                    else if(simboloAtual == 'o'){
                        inserirImagem(btnAtual, imgO);
                    }
                    else {
                        inserirImagem(btnAtual, imgVazia);
                    }
                }
            });


        add(botoes[indiceAtual]);
        }

        setIconImage(icone.getImage()); // Coloca o icone na janela
        setVisible(true); // Deixa ele visível. Deve sempre ir por ultimo para garantir que tudo carregue
    }
}