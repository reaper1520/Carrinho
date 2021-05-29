package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.DAO;

public class TelaCarrinho extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtCodigo;
	private JTextField txtProduto;
	private JTextField txtQuantidade;
	private JTextField txtValor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCarrinho frame = new TelaCarrinho();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCarrinho() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// ativação do formulario (formulario carregado)
				// status da conexão
				status();
			}
		});
		setTitle("Sistema de Consulta");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaCarrinho.class.getResource("/icones/favicon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/dberror.png")));
		lblStatus.setBounds(387, 204, 32, 32);
		contentPane.add(lblStatus);

		JLabel lblNewLabel = new JLabel("Codigo");
		lblNewLabel.setBounds(10, 25, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Produto");
		lblNewLabel_1.setBounds(10, 64, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Quantidade");
		lblNewLabel_2.setBounds(10, 107, 110, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Valor");
		lblNewLabel_3.setBounds(10, 151, 46, 14);
		contentPane.add(lblNewLabel_3);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(89, 22, 156, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtProduto = new JTextField();
		txtProduto.setBounds(89, 61, 262, 20);
		contentPane.add(txtProduto);
		txtProduto.setColumns(10);

		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(89, 104, 156, 20);
		contentPane.add(txtQuantidade);
		txtQuantidade.setColumns(10);

		txtValor = new JTextField();
		txtValor.setBounds(89, 148, 262, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);

		btnRead = new JButton("");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarCodigo();
			}
		});
		btnRead.setEnabled(false);
		btnRead.setBorder(null);
		btnRead.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/read.png")));
		btnRead.setToolTipText("Bucar Produto");
		btnRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRead.setBackground(SystemColor.control);
		btnRead.setBounds(372, 22, 64, 64);
		contentPane.add(btnRead);

		btnCreate = new JButton("");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirProduto();
			}
		});
		btnCreate.setEnabled(false);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setBorder(null);
		btnCreate.setBackground(SystemColor.control);
		btnCreate.setToolTipText("Adicionar Produto");
		btnCreate.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/create.png")));
		btnCreate.setBounds(56, 187, 64, 64);
		contentPane.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarProduto();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setBorder(null);
		btnUpdate.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/update.png")));
		btnUpdate.setToolTipText("Atualizar Produto");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setBackground(SystemColor.control);
		btnUpdate.setBounds(170, 187, 64, 64);
		contentPane.add(btnUpdate);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarProduto();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setBorder(null);
		btnDelete.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/delete.png")));
		btnDelete.setToolTipText("Deletar Produto");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setBackground(SystemColor.control);
		btnDelete.setBounds(287, 187, 64, 64);
		contentPane.add(btnDelete);
	} // fim do construtor

	// criação de um objeto para acessar o metodo da classe DAO
	DAO dao = new DAO();
	private JButton btnRead;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;

	/**
	 * status da conexão
	 */
	private void status() {
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			// status
			 //System.out.println(con);
			// trocando o icone do banco de dados (status da conexão)
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnRead.setEnabled(true);
				// btnCreate.setEnabled(true);
				// btnUpdate.setEnabled(true);
				// btnDelete.setEnabled(true);
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
			}
			// encerrar conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Selecionar contato
	 */
	private void selecionarCodigo() {
		// instrução sql para pesquisar um contato pelo nome
		String read = "select * from carrinho where codigo = ?";
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			// preparar a instrução sql
			PreparedStatement pst = con.prepareStatement(read);
			// subistituir o parametro(?) pelo nome do contato
			pst.setString(1, txtCodigo.getText());
			// resultado (obter os dados do contato pesquisado)
			ResultSet rs = pst.executeQuery();
			// se existir um contato correspondente
			if (rs.next()) {
				txtProduto.setText(rs.getString(2)); // 1 é o campo idcon
				txtQuantidade.setText(rs.getString(3)); // 3 é o fone
				txtValor.setText(rs.getString(4)); // 4 é o email
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnRead.setEnabled(false);

			} else {
				// criar uma caixa de mesagem do Java
				// JOptionPane.showMessageDialog(null, "Contato inexistente");
				btnCreate.setEnabled(true);
				btnRead.setEnabled(false);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * inserir um novo contato CRUD Creat
	 */
	private void inserirProduto() {
		// validação dos campos
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Produto");

		} else if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o quantidade do Produto");

		} else if (txtProduto.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome do Produto não pode ter mais que 50 caracteres");

		} else if (txtQuantidade.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo quantidade não pode ter mais que 15 caracteres");
		} else if (txtValor.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo valor não pode ter mais que 50 caracteres");
		} else {
			String create = "insert into carrinho (produto,quantidade,valor) values (?,?,?)";

			try {
				// estabelecer uma conexão
				Connection con = dao.conectar();
				// preparar a instrução sql
				PreparedStatement pst = con.prepareStatement(create);
				// subistituir os parametros (?,?,?) pelo conteudo das caixas de texto
				pst.setString(1, txtProduto.getText());
				pst.setString(2, txtQuantidade.getText());
				pst.setString(3, txtValor.getText());
				// executar a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso");
				con.close();
				limpar();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Editar contato CRUD Update
	 */

	private void alterarProduto() {
		// validação dos campos
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Produto");

		} else if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o quantidade do Produto");

		} else if (txtProduto.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome do Produto não pode ter mais que 50 caracteres");

		} else if (txtQuantidade.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo quantidade não pode ter mais que 15 caracteres");
		} else if (txtValor.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo valor não pode ter mais que 50 caracteres");
		} else {
			String update = "update carrinho set produto=?, quantidade=?, valor=? where codigo=?";

			try {
				// estabelecer uma conexão
				Connection con = dao.conectar();
				// preparar a instrução sql
				PreparedStatement pst = con.prepareStatement(update);
				// subistituir os parametros (?,?,?) pelo conteudo das caixas de texto
				pst.setString(1, txtProduto.getText());
				pst.setString(2, txtQuantidade.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtCodigo.getText());
				// executar a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto editado com sucesso");
				con.close();
				limpar();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * excluir contato CUD Delete
	 */
	private void deletarProduto() {
		String delete = "delete from carrinho where codigo=?";
		// confirmação de exclusão do contato
		int confirma = JOptionPane.showConfirmDialog(null, "Confirme a exclusão deste Produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);

		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtCodigo.getText());
				pst.executeUpdate();
				limpar();
				JOptionPane.showMessageDialog(null, "Produto excluido");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			limpar();
		}
	}

	/**
	 * limpar campos e configurar botões
	 */

	private void limpar() {
		// limpar os campos
		txtCodigo.setText(null);
		txtProduto.setText(null);
		txtQuantidade.setText(null);
		txtValor.setText(null);

		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnRead.setEnabled(true);
	}

}
