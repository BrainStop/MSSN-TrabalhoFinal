package Trabalho05;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

//User Imports
//import RobotLego.RobotLego;

public class interfaceRobo extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Asminhas variaveis
	protected boolean isDebugAtivo = true;
	protected boolean onoff = false;
	protected int offsetEsquerdo = 0;
	protected int offsetDireito = 0;
	protected int raio = 0;
	protected int angulo = 0;
	protected int distancia = 0;
	protected String nome = "none";
	private wrapper robo = new wrapper();
	private vaguear vg = new vaguear(robo);
	private fugir fug = new fugir(robo, vg);
	private evitar evi = new evitar(robo, vg, fug);
	private String[] valConfig = null;
	private Boolean waitingtoStartEvitar = false;
	private Boolean waitingtoStartFugir = false;
	private Boolean waitingtoStartVaguear = false;

	
	//Trabalho5
	private GravarConfig saveConfig = new GravarConfig(this);
	private CarregarConfig loadConfig = new CarregarConfig();
	
	// Funções auxiliares
	
	private void evitar() {
		try {
			if(!evi.isAlive()){
				mostrarMensagem("Evitando");
				if(onoff == true){
					evi.start();	
					waitingtoStartVaguear = false;
				}
				else{
					waitingtoStartVaguear = true;
				}
			}else{
				if(evi.mySuspend() == false) {
					mostrarMensagem("Evitando");
					evi.myResume();
				} else 
					mostrarMensagem("Evitando em pausa");
			}
		} catch (Exception e1) {
			// JOptionPane.showMessageDialog(...);
			e1.printStackTrace();

		}
	}
	
	private void fugir() {
		try {
			if(!fug.isAlive()){
				mostrarMensagem("Fugindo");
				if(onoff == true){
					fug.start();	
					waitingtoStartFugir = false;
				}
				else{
					waitingtoStartFugir = true;
				}
			}else{
				if(fug.mySuspend() == false) {
					mostrarMensagem("Fugindo");
					fug.myResume();
				} else 
					mostrarMensagem("Fugindo em pausa");
			}
		} catch (Exception e1) {
			// JOptionPane.showMessageDialog(...);
			e1.printStackTrace();

		}
	}
	
	private void vaguear() {
		try {
			if(!vg.isAlive()){
				mostrarMensagem("Vaguiando");
				if(onoff == true){
					vg.start();	
					waitingtoStartVaguear= false;
				}
				else{
					waitingtoStartVaguear = true;
				}
			}else{
				if(vg.mySuspend() == false) {
					mostrarMensagem("Vaguiando");
					vg.myResume();
				} else 
					mostrarMensagem("Vaguiando em pausa");
			}
		} catch (Exception e1) {
			// JOptionPane.showMessageDialog(...);
			e1.printStackTrace();

		}
	}
	
	private void mostrarMensagem(String mensagem) {
		if (this.isDebugAtivo == true)
			this.log.append(mensagem + "\n");

	}
	
	private String[] carregarConfig(){
		return loadConfig.loadConfig(this, nome+".properties");
	}
	
	private void gravarConfig(){
		saveConfig.run();
	}

	private void interruptall() {
		if(vg.isAlive())
			vg.mySuspend();
		if(fug.isAlive())
			fug.mySuspend();
		if(evi.isAlive())
			evi.mySuspend();
	}

	private void stop() {
		interruptall();
		robo.Parar(true, false);
		if(radioVaguear.isSelected())
			radioVaguear.setSelected(false);
		if(radioFugir.isSelected())
			radioFugir.setSelected(false);
		if(radioEvitar.isSelected())
			radioEvitar.setSelected(false);
	}

	private void forward() {
		stop();
		robo.Reta(distancia, true);
		robo.Parar(false, true);
	}

	private void backward() {
		stop();
		robo.Reta(-distancia, true);
		robo.Parar(false, true);
	}

	private void left() {
		stop();
		robo.CurvarEsquerda(raio, angulo, true);
		robo.Parar(false, true);
	}

	private void rigth() {
		stop();
		robo.CurvarDireita(raio, angulo, true);
		robo.Parar(false, true);
	}

	private void leftOffset() {
		robo.AjustarVME(offsetEsquerdo);
	}

	private void rigthOffset() {
		robo.AjustarVMD(offsetDireito);
	}

	private void myInit() {
		this.isDebugAtivo = true;
		this.checkboxDebugAction.setSelected(this.isDebugAtivo);
		this.textFieldOffsetEsquerda.setText(Integer.toString(this.offsetEsquerdo));
	}

	// Atribulos "automáticos"

	private JPanel contentPane;
	JTextField textFieldOffsetEsquerda;
	JTextField textFieldOffsetDireita;
	private JCheckBox checkboxDebugAction;
	JLabel lblNomeDoRobot;
	JTextField textFieldNome;
	JTextField textFieldRaio;
	JTextField textFieldAngulo;
	JTextField textFieldDistancia;
	private JButton btnNewButton_1;
	private JButton btnNewButton_3;
	private JButton btnNewButton;
	private JButton btnNewButton_4;
	private JButton btnNewButton_2;
	private JTextArea log;
	protected JRadioButton radioVaguear;
	protected JRadioButton radioEvitar;
	protected JRadioButton radioFugir;
	private JButton bGravarConfig;
	private JButton bCarregarConfig;
	private JButton bReproduzirPercurso;
	private JButton bReproduzirPercursoInverso;
	private JRadioButton bGravarPercurso;
	private JButton bapagarPercurso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			interfaceRobo frame = new interfaceRobo();
			frame.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		for (;;) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public interfaceRobo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 582);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldOffsetEsquerda = new JTextField();
		textFieldOffsetEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					offsetEsquerdo = Integer.parseInt(textFieldOffsetEsquerda.getText());
					mostrarMensagem("Offset esquerdo -> " + offsetEsquerdo);
					leftOffset();
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		textFieldOffsetEsquerda.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldOffsetEsquerda.setText("0");
		textFieldOffsetEsquerda.setBounds(10, 40, 72, 20);
		contentPane.add(textFieldOffsetEsquerda);
		textFieldOffsetEsquerda.setColumns(10);

		textFieldOffsetDireita = new JTextField();
		textFieldOffsetDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					offsetDireito = Integer.parseInt(textFieldOffsetDireita.getText());
					mostrarMensagem("Offset direita -> " + offsetDireito);
					rigthOffset();
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		textFieldOffsetDireita.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldOffsetDireita.setText("0");
		textFieldOffsetDireita.setBounds(306, 40, 67, 20);
		contentPane.add(textFieldOffsetDireita);
		textFieldOffsetDireita.setColumns(10);

		JLabel lblOffsetEsquerda = new JLabel("Offset Esquerda");
		lblOffsetEsquerda.setBounds(10, 13, 97, 14);
		contentPane.add(lblOffsetEsquerda);

		JLabel lblOffsetDireita = new JLabel("Offset Direita");
		lblOffsetDireita.setBounds(306, 12, 97, 17);
		contentPane.add(lblOffsetDireita);

		checkboxDebugAction = new JCheckBox("Debug Action");
		checkboxDebugAction.setBackground(Color.LIGHT_GRAY);
		checkboxDebugAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isDebugAtivo = checkboxDebugAction.isSelected();
				mostrarMensagem("Debug state ->" + isDebugAtivo);
			}
		});
		checkboxDebugAction.setBounds(272, 307, 101, 23);
		contentPane.add(checkboxDebugAction);

		lblNomeDoRobot = new JLabel("Nome do Robot");
		lblNomeDoRobot.setBounds(148, 13, 88, 14);
		contentPane.add(lblNomeDoRobot);

		textFieldNome = new JTextField();
		textFieldNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					nome = textFieldNome.getText();

					mostrarMensagem("Nome -> " + nome);
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		textFieldNome.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNome.setText("none");
		textFieldNome.setBounds(144, 40, 86, 20);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);

		JRadioButton bOnOff = new JRadioButton("On/Off");
		bOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					onoff = bOnOff.isSelected();
					if (onoff == true) {
						onoff = robo.OpenNXT(nome);
						mostrarMensagem("Robo ligado");
						if(waitingtoStartEvitar == true)
							evitar();
						if(waitingtoStartFugir == true)
							fugir();
						if(waitingtoStartVaguear == true)
							vaguear();
					} else {
						robo.CloseNXT();
						mostrarMensagem("Robo desligado");
					}
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		bOnOff.setBackground(Color.LIGHT_GRAY);
		bOnOff.setForeground(Color.BLACK);
		bOnOff.setBounds(154, 67, 67, 23);
		contentPane.add(bOnOff);

		JLabel lblRaio = new JLabel("Raio");
		lblRaio.setBounds(10, 112, 30, 14);
		contentPane.add(lblRaio);

		textFieldRaio = new JTextField();
		textFieldRaio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					raio = Integer.parseInt(textFieldRaio.getText());

					mostrarMensagem("Raio -> " + raio);
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		textFieldRaio.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldRaio.setText("0");
		textFieldRaio.setBounds(49, 108, 30, 20);
		contentPane.add(textFieldRaio);
		textFieldRaio.setColumns(10);

		JLabel lblCm = new JLabel("cm");
		lblCm.setBounds(84, 112, 23, 14);
		contentPane.add(lblCm);

		JLabel lblAngulo = new JLabel("Angulo");
		lblAngulo.setBounds(120, 112, 46, 14);
		contentPane.add(lblAngulo);

		textFieldAngulo = new JTextField();
		textFieldAngulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					angulo = Integer.parseInt(textFieldAngulo.getText());

					mostrarMensagem("Angulo -> " + angulo);
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		textFieldAngulo.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAngulo.setText("0");
		textFieldAngulo.setBounds(172, 110, 30, 20);
		contentPane.add(textFieldAngulo);
		textFieldAngulo.setColumns(10);

		JLabel lblGraus = new JLabel("graus");
		lblGraus.setBounds(205, 112, 46, 14);
		contentPane.add(lblGraus);

		JLabel lblDistancia = new JLabel("Dist\u00E2ncia");
		lblDistancia.setBounds(261, 112, 53, 14);
		contentPane.add(lblDistancia);

		textFieldDistancia = new JTextField();
		textFieldDistancia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					distancia = Integer.parseInt(textFieldDistancia.getText());

					mostrarMensagem("Distância -> " + distancia);
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		textFieldDistancia.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDistancia.setText("0");
		textFieldDistancia.setBounds(322, 109, 30, 20);
		contentPane.add(textFieldDistancia);
		textFieldDistancia.setColumns(10);

		JLabel lblCm_1 = new JLabel("cm");
		lblCm_1.setBounds(357, 112, 23, 14);
		contentPane.add(lblCm_1);

		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(12, 312, 53, 14);
		contentPane.add(lblLog);

		btnNewButton = new JButton("Frente");
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mostrarMensagem("Andou para a frente -> " + distancia + "cm.");
					forward();
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		btnNewButton.setBounds(138, 158, 109, 46);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("Parar");
		btnNewButton_1.setBackground(Color.RED);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mostrarMensagem("Parou.");
					stop();
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		btnNewButton_1.setBounds(138, 215, 109, 46);
		contentPane.add(btnNewButton_1);

		btnNewButton_2 = new JButton("Retaguarda");
		btnNewButton_2.setBackground(Color.BLUE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mostrarMensagem("Andou para trás -> " + distancia + "cm.");
					backward();
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		btnNewButton_2.setBounds(138, 272, 109, 46);
		contentPane.add(btnNewButton_2);

		btnNewButton_3 = new JButton("Esquerda");
		btnNewButton_3.setBackground(Color.YELLOW);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mostrarMensagem("Virou para a esquerda -> " + angulo + "º");
					left();
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		btnNewButton_3.setBounds(21, 215, 109, 46);
		contentPane.add(btnNewButton_3);

		btnNewButton_4 = new JButton("Direita");
		btnNewButton_4.setBackground(Color.PINK);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mostrarMensagem("Virou para a direita -> " + angulo + "º");
					rigth();
				} catch (Exception e1) {
					// JOptionPane.showMessageDialog(...);
					e1.printStackTrace();

				}
			}
		});
		btnNewButton_4.setBounds(257, 215, 109, 46);
		contentPane.add(btnNewButton_4);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 331, 364, 107);
		contentPane.add(scrollPane);

		log = new JTextArea();
		scrollPane.setViewportView(log);

		radioVaguear = new JRadioButton("Vaguear");
		radioVaguear.setBounds(20, 133, 109, 23);
		radioVaguear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vaguear();
			}
		});
		contentPane.add(radioVaguear);

		radioEvitar = new JRadioButton("Evitar");
		radioEvitar.setBounds(20, 159, 109, 23);
		radioEvitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evitar();
			}
		});
		contentPane.add(radioEvitar);

		radioFugir = new JRadioButton("Fugir");
		radioFugir.setBounds(20, 186, 109, 23);
		radioFugir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fugir();
			}
		});
		contentPane.add(radioFugir);
		
		bGravarConfig = new JButton("Gravar Config");
		bGravarConfig.setBounds(10, 479, 123, 23);
		bGravarConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravarConfig();
			}
		});
		contentPane.add(bGravarConfig);
		
		bCarregarConfig = new JButton("Carregar Config");
		bCarregarConfig.setBounds(10, 513, 123, 23);
		bCarregarConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valConfig = carregarConfig();
				
				if(valConfig[0] != null) {
					textFieldNome.setText(valConfig[0]);
					nome = valConfig[0];
					
					textFieldOffsetEsquerda.setText((valConfig[1]));
					offsetEsquerdo = Integer.parseInt(valConfig[1]);
					
					textFieldOffsetDireita.setText((valConfig[2]));
					offsetDireito = Integer.parseInt(valConfig[2]);
					
					textFieldAngulo.setText((valConfig[3]));
					angulo = Integer.parseInt(valConfig[3]);
					
					checkboxDebugAction.setSelected(Boolean.parseBoolean(valConfig[4]));
					isDebugAtivo = Boolean.parseBoolean(valConfig[4]);
					
					textFieldRaio.setText(valConfig[5]);
					raio  = Integer.parseInt(valConfig[5]);
					
					textFieldDistancia.setText(valConfig[6]);
					distancia = Integer.parseInt(valConfig[6]);
					
					radioVaguear.setSelected(Boolean.parseBoolean(valConfig[7]));
					if(Boolean.parseBoolean(valConfig[7]))
						vaguear();
					radioEvitar.setSelected(Boolean.parseBoolean(valConfig[8]));
					if(Boolean.parseBoolean(valConfig[8]))
						evitar();
					radioFugir.setSelected(Boolean.parseBoolean(valConfig[9]));
					if(Boolean.parseBoolean(valConfig[7]))
						fugir();
				}
			}
		});
		contentPane.add(bCarregarConfig);
		
		bReproduzirPercurso = new JButton("Reproduzir Percurso");
		bReproduzirPercurso.setBounds(148, 479, 225, 23);
		bReproduzirPercurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robo.reproduzir();
			}
		});
		contentPane.add(bReproduzirPercurso);
		
		bReproduzirPercursoInverso = new JButton("Reproduzir Percurso Inverso");
		bReproduzirPercursoInverso.setBounds(148, 513, 225, 23);
		bReproduzirPercursoInverso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robo.reproduzirInverso();
			}
		});
		contentPane.add(bReproduzirPercursoInverso);
		
		bGravarPercurso = new JRadioButton("Gravar Percurso");
		bGravarPercurso.setHorizontalAlignment(SwingConstants.CENTER);
		bGravarPercurso.setBounds(180, 445, 193, 23);
		bGravarPercurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robo.gravarPercurso(bReproduzirPercurso.isSelected());
			}
		});
		contentPane.add(bGravarPercurso);
		
		bapagarPercurso = new JButton("Apagar Percurso");
		bapagarPercurso.setBounds(10, 445, 156, 23);
		bapagarPercurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robo.apagarPercurso();
			}
		});
		contentPane.add(bapagarPercurso);

		setVisible(true);

		myInit();
	}
}
