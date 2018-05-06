package dcc;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class Interface {
	private Blockchain blockchain;
	private FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("Fichier JSON", "json");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Interface();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
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

		JFrame frame = new JFrame();
		frame.setTitle("DuckCoinCoin");
		frame.setSize(800, 450);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/dcc/duck_icon.png")));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JFileChooser openFileChooser = new JFileChooser();
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel titre = new JLabel("<html><h1>DuckCoinCoin, ça vous en bouche un coin !</h1>"
				+ "<p>Ce logiciel se veut simple d'utilisation, pour cette raison, nous vous invitons à :<ol>"
				+ "<li>Vous détendre,</li>"
				+ "<li>Explorer le logiciel comme bon vous semble (éventuellement en plusieurs fois"
				+ ", oui, prenez votre temps, vraiment, on vous veut du bien)<br/><span style='font-size:20px'>🍹</span></li>"
				+ "<li>Répéter le mantra : « C'est moche, mais ça marche »</li></ol>"
				+ "Et enfin, bonne utilisation !<br/>---<br/>Logiciel réalisé par Jean, Mathilde et Maya</p></html>");
		titre.setPreferredSize(new Dimension (400, 350));
		panel_4.add(titre);

		JPanel panel_5 = new JPanel();
		panel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_5.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel.add(panel_5);
		
		JPanel space_top = new JPanel();
		space_top.setPreferredSize(new Dimension(0, 100));
		panel_5.add(space_top);
		// Image d'un canard
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(this.getClass().getResource("duck.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel_5.add(picLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
				
		JPanel space = new JPanel();
		space.setPreferredSize(new Dimension(0, 30));
		panel_5.add(space);

		JLabel msg = new JLabel("");

		JPanel panel_3 = new JPanel();
		panel_5.add(panel_3);
		msg.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(msg);

		// Panel 1
		JPanel panel_1 = new JPanel();

		panel_5.add(panel_1);
		JButton importBtn = new JButton("Importer depuis un JSON");
		importBtn.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_1.add(importBtn);
		importBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Initialisation du panel 2
		JPanel panel_2 = new JPanel();
		panel_5.add(panel_2);
		JButton newBtn = new JButton("Créer une nouvelle blockchain");
		panel_2.add(newBtn);
		newBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.PAGE_AXIS));
		newBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (newBtn.isEnabled()) newBcDialog(frame);
			}
		});
		importBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (importBtn.isEnabled()) {
					openFileChooser.setFileFilter(jsonFilter);
					if (JFileChooser.APPROVE_OPTION == openFileChooser.showOpenDialog(null)) {
						Thread t = new Thread() {
							public void run() {
								importBtn.setEnabled(false);
								newBtn.setEnabled(false);
								msg.setText("En cours de chargement");
								try {
									String jsonAsString = Utility.file2string(openFileChooser.getSelectedFile());
									new JsonParser().parse(jsonAsString);
									// Valid.
									blockchain = (new Gson()).fromJson(jsonAsString, Blockchain.class);
									blockchain.sortBC();
									displayBlockchain(frame);
								} catch (JsonParseException jsonE) {
									// Invalid JSON
									JOptionPane.showMessageDialog(null,
											"Erreur : Le fichier n'est\npas correctement encodé",
											"Erreur",
											JOptionPane.ERROR_MESSAGE);
								} catch(Throwable exception) {
									// error trying to read the file
									JOptionPane.showMessageDialog(null,
											"Erreur : Le fichier n'a pas pu être ouvert",
											"Erreur",
											JOptionPane.ERROR_MESSAGE);
								}
								importBtn.setEnabled(true);
								newBtn.setEnabled(true);
								msg.setText("");
							}
						};
						t.start();
					}
				}
			}
		});

		frame.setVisible(true);
	}

	private void displayBlockchain (JFrame frame) {
		displayBlockchain(frame, false, 0, 0, 0);
	}
	private void displayBlockchain (JFrame previousFrame, boolean newBc, int nbrBlocks, int difficulty, int nbr_transactions_max) {
		previousFrame.setVisible(false);

		// Initialisation des btns
		JButton backBtn = new JButton("← Revenir en arrière");
		JButton checkBcBtn = new JButton("Vérifier cette blockchain");
		JButton exportBtn = new JButton("Exporter au format Json");

		JFrame frame = new JFrame();
		frame.setTitle("DuckCoinCoin");
		frame.setSize(700, 400);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/dcc/duck_icon.png")));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		JTextArea textArea = new JTextArea();
		if (newBc) {
			backBtn.setEnabled(false);
			checkBcBtn.setEnabled(false);
			exportBtn.setEnabled(false);
			textArea.setText("Chargement, veuillez patienter ...");
		} else {
			textArea.setText(this.blockchain.toString());
		}

		// Back btn
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (backBtn.isEnabled()) {
					frame.setVisible(false);
					previousFrame.setVisible(true);
				}
			}
		});

		// Check btn
		checkBcBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (checkBcBtn.isEnabled()) {
					Thread t = new Thread() {
						public void run() {

							int [] bc = blockchain.checkBlockchain();
							if (bc[0] == Blockchain.BC_VALIDE) {
								JOptionPane.showMessageDialog(null,
										"Cette blockchain est valide !",
										"Blockchain valide",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								String tmp = "";
								if (bc[0] == Blockchain.GENESIS_CORROMPU) {
									tmp = "Le block Genesis est corrompu";
								} else if (bc[0] == Blockchain.CHAINAGE_CORROMPU) {
									tmp = "Le chainage est corrompu entre\n"
											+ "le block d'index " + (bc[1] - 1) + "\n"
											+ "et le block d'index " + bc[1];
								} else if (bc[0] == Blockchain.MERKLE_CORROMPU) {
									tmp = "L'arbre de merkle du block numéro " + bc[1] + " est corrompu";
								} else if (bc[0] == Blockchain.HASH_CORROMPU) {
									tmp = "Le bloc d'index " + bc[1] + " a un hash qui\n"
											+ "ne correspond pas au hash\n"
											+ "de ce qu'il contient";
								}
								JOptionPane.showMessageDialog(null,
										"Cette blockchain est corrompue !\n(" + tmp + ")",
										"Blockchain corrompue",
										JOptionPane.WARNING_MESSAGE);
							}
						}
					};
					t.start();
				}
			}
		});

		// Export btn
		exportBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (exportBtn.isEnabled()) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileFilter(jsonFilter);
					if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						Thread t = new Thread() {
							public void run() {							
								try {
									Utility.string2file((new Gson()).toJson(blockchain), fileChooser.getSelectedFile());
									JOptionPane.showMessageDialog(null,
											"Votre sauvegarde a été effectué !",
											"Sauvegarde réussie",
											JOptionPane.INFORMATION_MESSAGE);
								} catch (IOException e1) {
									JOptionPane.showMessageDialog(null,
											"Erreur : Impossible d'exporter la blockchain",
											"Erreur de sauvegarde",
											JOptionPane.ERROR_MESSAGE);
								}
							}
						};
						t.start();
					}
				}
			}
		});

		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel.add(backBtn);
		panel.add(checkBcBtn);
		panel.add(exportBtn);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new CardLayout(0, 0));

		textArea.setEditable(false);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		panel_1.add(scrollPane);

		if (newBc) {
			Thread newBcThread = new Thread() {
				public void run() {
					blockchain = new Blockchain(difficulty, nbrBlocks, nbr_transactions_max);
					textArea.setText(blockchain.toString());
					backBtn.setEnabled(true);
					checkBcBtn.setEnabled(true);
					exportBtn.setEnabled(true);
				}
			};
			newBcThread.start();
		}

		// Add panels to frame
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	private void newBcDialog (JFrame old) {
		old.setVisible(false);
		JFrame frame = new JFrame();
		frame.setTitle("DuckCoinCoin");
		frame.setSize(460, 390);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/dcc/duck_icon.png")));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.setPreferredSize(new Dimension(450, 200));

		JPanel blocksAmountPanel = new JPanel();
		blocksAmountPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel blocksAmountLabel = new JLabel("Nombre de blocks");
		blocksAmountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		blocksAmountPanel.add(blocksAmountLabel);

		JSlider blocksAmount = new JSlider();
		blocksAmountPanel.add(blocksAmount);
		blocksAmount.setValue(10);
		blocksAmount.setPaintLabels(true);
		blocksAmount.setPaintTicks(true);
		blocksAmount.setMajorTickSpacing(99);
		blocksAmount.setMinorTickSpacing(10);
		blocksAmount.setMinimum(1);
		blocksAmount.setMaximum(100);

		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel difficultyLabel = new JLabel("Difficulté");
		difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		difficultyPanel.add(difficultyLabel);
		
		JSlider difficulty = new JSlider();
		difficultyPanel.add(difficulty);
		difficulty.setMaximum(10);
		difficulty.setMinimum(1);
		difficulty.setValue(4);
		difficulty.setPaintTicks(true);
		difficulty.setPaintLabels(true);
		difficulty.setMajorTickSpacing(1);
		difficulty.setSnapToTicks(true);
		
		// Nbr transactions max
		JPanel nbr_transactions_max_panel = new JPanel();
		nbr_transactions_max_panel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel nbr_transactions_max_label = new JLabel("Nombre maximum de transactions par block");
		nbr_transactions_max_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		nbr_transactions_max_panel.add(nbr_transactions_max_label);
		
		JSlider nbr_transactions_max_slider = new JSlider();
		nbr_transactions_max_panel.add(nbr_transactions_max_slider);
		nbr_transactions_max_slider.setMaximum(100);
		nbr_transactions_max_slider.setMinimum(1);
		nbr_transactions_max_slider.setValue(10);
		nbr_transactions_max_slider.setPaintTicks(true);
		nbr_transactions_max_slider.setPaintLabels(true);
		nbr_transactions_max_slider.setMajorTickSpacing(99);
		nbr_transactions_max_slider.setMinorTickSpacing(10);

		JButton okBtn = new JButton("Valider");

		JPanel okBtnPanel = new JPanel();
		okBtnPanel.setPreferredSize(new Dimension(450, 50));
		okBtnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		okBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (okBtn.isEnabled()) displayBlockchain(
						frame,
						true,
						blocksAmount.getValue(),
						difficulty.getValue(),
						nbr_transactions_max_slider.getValue());
			}
		});

		JButton cancelBtn = new JButton("← Revenir en arrière");
		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (cancelBtn.isEnabled()) {
					frame.setVisible(false);
					old.setVisible(true);
				}
			}
		});

		// Add btns to panel
		okBtnPanel.add(cancelBtn);
		okBtnPanel.add(okBtn);

		// Add panels to frame
		panel.add(blocksAmountPanel);
		panel.add(difficultyPanel);
		panel.add(nbr_transactions_max_panel);
		panel.add(okBtnPanel);

		frame.setVisible(true);
	}
}
