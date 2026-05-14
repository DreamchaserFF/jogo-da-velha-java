import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Scanner;
import java.util.random.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Main{
    public static void main(String[] args){
        String[][] campo =  {{"","",""}
                            ,{"","",""}
                            ,{"","",""}}; //Campo do jogo. Vou mudar para botões, mas deixa aqui por enquanto

        Scanner sc = new Scanner(System.in); //Tem que instanciar o Scanner

        boolean jogando = true;
        

        /////////////////////////////////////////////////////////////
        ///                                                       ///
        ///                  CONFIGURAÇÃO DA TELA                 ///
        ///                                                       ///
        /////////////////////////////////////////////////////////////
        
        // Listener de redimensionamento

        Image btnVazio       = new ImageIcon("botao_vazio.png").getImage(); // Carrega a imagem original para manter a qualidade
        ImageIcon icone      = new ImageIcon("velha.png"); // Cria um icone de imagem
        Border border        = BorderFactory.createLineBorder(Color.BLACK,3); // Cria a borda

        JLabel label = new JLabel(); // Cria o label

        // Listener de redimensionamento para deixar o label responsivo.
        label.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                
                int largura = label.getWidth();
                int altura  = label.getHeight();
                Image btnVazioRedim  = btnVazio.getScaledInstance(largura, altura, Image.SCALE_SMOOTH); // Redimensiona a imagem
                ImageIcon botaoVazio = new ImageIcon(btnVazioRedim);

                label.setText("O jogo vai aqui.");
                label.setIcon(botaoVazio);
                label.setIconTextGap(-25); // Muda a distancia entre texto e imagem
                label.setBackground(Color.DARK_GRAY); // Muda cor do background do label. O label por default ocupa a janela inteira
                label.setOpaque(true); // Deixa background do label visivel
                label.setBorder(border); // Coloca a borda no label
            }
        });
        label.setHorizontalTextPosition(JLabel.CENTER); // Coloca texto na esquerda, centro ou direita
        label.setVerticalTextPosition(JLabel.TOP); // Coloca o texto em cima, centro ou em baixo
        label.setForeground(new Color(255, 255, 255)); // Muda a cor do label
        label.setFont(new Font("arial", Font.BOLD,20)); // Muda a fonta do label
        label.setVerticalAlignment(JLabel.CENTER); // Determina a posição vertical de imagem + texto dentro do label
        label.setHorizontalAlignment(JLabel.CENTER); // Determina a posição horizontal de imagem + texto dentro do label


        JFrame frame = new JFrame(); // Cria o frame
        frame.setTitle("Jogo da Velha"); // Titulo da janela
        // Usar DO_NOTHING_ON_CLOSE não deixa o usuário fechar a janela. Util pra depois QUESTION MARK?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sai do programa quando frame é fechado
        frame.add(label);
        // frame.setResizable(false); // Não deixa o frame ser resizado
        frame.setSize(800,800); // Determina as dimensões x e y da janela
        frame.setIconImage(icone.getImage()); // Coloca o icone na janela
        frame.getContentPane().setBackground(new Color(0,0,0)); // Colore background
        frame.setVisible(true); // Deixa ele visível. Deve sempre ir por ultimo para garantir que tudo carregue

        

        /////////////////////////////////////////////////////////////
        ///                                                       ///
        ///                       GAME LOOP                       ///
        ///                                                       ///
        /////////////////////////////////////////////////////////////
        while(jogando == true){
            System.out.println("Informe a sua jogada.");
            String jogada = sc.nextLine();

            jogando = false;
        
        System.out.println(jogada);

        sc.close(); //Tem que fechar o scanner se não o programa fica esperando input
        
        }
    }
}