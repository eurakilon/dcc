package dcc;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ScrollPaneConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;

public class Ihm {
	private Blockchain blockchain;
	private JTextArea console;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/**
					 * Lire ?
					 * 		Oui
					 * 			GetFileName
					 * 		Non
					 * 			filename ?
					 * 			nbrBlock ?
					 * 			difficukty ?
					 * 			nbrmaxtransaction ?
					 * 			display exec time
					 * 			save2json
					 * 		checkBC
					 * 		"success" / "error"
					 * 		display exec time
					 * Tout afficher ?
					 * 		oui : display(my_bc)
					 * 		non : quel block afficher ? (entre 1 et ...)
					 * 			display(my_bc, int)
					 */
					
					Ihm window = new Ihm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ihm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("DuckCoinCoin");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton importBtn = new JButton("Importer depuis un JSON");
		importBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO afficher message d'erreur pour dire que ce n'est pas encore possible
			}
		});
		
		JButton newBtn = new JButton("Créer une nouvelle blockchain");
		newBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int nbrmaxtransaction = 10;
					int difficulty = 4;
					int nbrBlocks = 10;
					// Fenetre.this.blockchain == blockchain
					blockchain = new Blockchain(nbrmaxtransaction, difficulty, nbrBlocks);
					console.append(blockchain.toString());
				}
			});
		JButton btnSauvegarder = new JButton("Sauvegarder au format JSON");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(importBtn);
		panel.add(newBtn);
		panel.add(btnSauvegarder);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new CardLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		panel_1.add(scrollPane);
		
		this.console = textArea;
	}
}
