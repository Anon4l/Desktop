import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class GuiCadastroFilmes extends JFrame {
    JLabel label1, label2, label3, label4, label5;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btSair;
    static JTextField tfCodigo, tfTitulo, tfGenero, tfProdutora, tfDataCompra;
    private FilmesDAO filmes;
    
    public static void main(String args[]) {
        JFrame janela = new GuiCadastroFilmes();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }
    public GuiCadastroFilmes() {
        inicializarComponentes();
        definirEventos();
    }
    public void inicializarComponentes() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setTitle("Cadastramento de Filmes");
        setBounds(200, 100, 610, 120);
        label1 = new JLabel("Codigo ");
        label2 = new JLabel("Titulo");
        label3 = new JLabel("Genero");
        label4 = new JLabel("Produtora");
        label5 = new JLabel("Data de Compra  ");
        tfCodigo = new JTextField(10);
        tfTitulo = new JTextField(35);
        tfGenero = new JTextField(10);
        tfProdutora = new JTextField(15);
        tfDataCompra = new JTextField(8);
        btGravar = new JButton("Gravar");
        btAlterar = new JButton("Alterar");
        btExcluir = new JButton("Excluir");
        btLocalizar = new JButton("Localizar");
        btNovo = new JButton("Novo");
        btCancelar = new JButton("Cancelar");
        btSair = new JButton("Sair");
        add(label1);
        add(tfCodigo);
        add(label2);
        add(tfTitulo);
        add(label3);
        add(tfGenero);
        add(label4);
        add(tfProdutora);
        add(label5);
        add(tfDataCompra);
        add(btNovo);
        add(btLocalizar);
        add(btGravar);
        add(btAlterar);
        add(btExcluir);
        add(btCancelar);
        add(btSair);
        setResizable(false);
        setBotoes(true, true, false, false, false, false);
        filmes = new FilmesDAO();
        if (!filmes.bd.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexao, o sistema sera fechado!");
            System.exit(0);
        }
    }

    public void definirEventos() {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filmes.bd.close();
                System.exit(0);
            }
        });
        btNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            limpaCampos();
            setBotoes(false, false, true, false, false, true);
            }
        });
        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            limpaCampos();
            }
        });
        btGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(tfCodigo.getText().equals("")){
                   JOptionPane.showMessageDialog(null, "O código não pode ser vazio!");
                   tfCodigo.requestFocus();
                   return;
               }
               if(tfTitulo.getText().equals("")){
                   JOptionPane.showMessageDialog(null, "O titulo não pode ser vazio!");
                   tfTitulo.requestFocus();
                   return;
               }
               if(tfGenero.getText().equals("")){
                   JOptionPane.showMessageDialog(null, "O genero não pode ser vazio!");
                   tfGenero.requestFocus();
                   return;
               }
               if(tfProdutora.getText().equals("")){
                   JOptionPane.showMessageDialog(null, "A produtora não pode ser vazia!");
                   tfProdutora.requestFocus();
                   return;
               }
               if(tfDataCompra.getText().equals("")){
                   JOptionPane.showMessageDialog(null, "A data de compra não pode ser vazia!");
                   tfDataCompra.requestFocus();
                   return;
               }
               filmes.filme.setCodigo(tfCodigo.getText());
               filmes.filme.setTitulo(tfTitulo.getText());
               filmes.filme.setGenero(tfGenero.getText());
               filmes.filme.setProdutora(tfProdutora.getText());
               filmes.filme.setDataCompra(tfDataCompra.getText());
               JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.INCLUSAO));
               limpaCampos();
            }
        });
        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               filmes.filme.setCodigo(tfCodigo.getText());
               filmes.filme.setTitulo(tfTitulo.getText());
               filmes.filme.setGenero(tfGenero.getText());
               filmes.filme.setProdutora(tfProdutora.getText());
               filmes.filme.setDataCompra(tfDataCompra.getText());
               JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.ALTERACAO));
               limpaCampos();
            }
        });
        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               filmes.filme.setCodigo(tfCodigo.getText());
               filmes.localizar();
               int n = JOptionPane.showConfirmDialog(null, filmes.filme.getTitulo(), " Excluir o Filme? ", JOptionPane.YES_NO_OPTION);
               if (n == JOptionPane.YES_OPTION){
                  JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.EXCLUSAO));
                  limpaCampos();
               }
            }
        });
        btLocalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizaCampos();
            }
        });
    }

    public void limpaCampos() {
        tfCodigo.setText("");
        tfTitulo.setText("");
        tfGenero.setText("");
        tfProdutora.setText("");
        tfDataCompra.setText("");
        tfCodigo.requestFocus();
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizaCampos() {
            filmes.filme.setCodigo(tfCodigo.getText());
            if(filmes.localizar()){
                tfCodigo.setText(filmes.filme.getCodigo());
                tfTitulo.setText(filmes.filme.getTitulo());
                tfGenero.setText(filmes.filme.getGenero());
                tfProdutora.setText(filmes.filme.getProdutora());
                tfDataCompra.setText(filmes.filme.getDataCompra());
                setBotoes(true, true, false, true, true, true);
            }
            else{
                JOptionPane.showMessageDialog(null, "Filme não encontrado!");
                limpaCampos();
            }
    }

    public void setBotoes(boolean bNovo, boolean bLocalizar, boolean bGravar, boolean bAlterar, boolean bExcluir, boolean bCancelar) {
        btNovo.setEnabled(bNovo);
        btLocalizar.setEnabled(bLocalizar);
        btGravar.setEnabled(bGravar);
        btAlterar.setEnabled(bAlterar);
        btExcluir.setEnabled(bExcluir);
        btCancelar.setEnabled(bCancelar);
    }
}