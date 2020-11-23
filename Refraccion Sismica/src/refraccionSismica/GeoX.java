package refraccionSismica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import com.fazecast.jSerialComm.SerialPort;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Toolkit;

public class GeoX extends JFrame {

	//Begin Application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeoX frame = new GeoX();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create Frame
	public GeoX() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Cesar Ramirez F\\Documents\\Java\\Graficos\\amserving.jpg"));
		definirConfiguracion(configuracionDefecto);
		initComponents();
	}
	
	private void initComponents() {
		//Content Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 100, 800, 600);
		setTitle("Refraccion sismica");
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		//Elements Menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuArchivo = new JMenu("Archivo");
		JMenuItem guardarMenuI = new JMenuItem("Guardar");
		JMenuItem limpiarMenuI = new JMenuItem("Limpiar");
		JMenuItem salirMenuI = new JMenuItem("Salir");
		
		JMenu menuConfiguracion = new JMenu("Configuracion");
		JMenuItem porDefectoMenuI = new JMenuItem("Por defecto");
		JMenuItem personalizadoMenuI = new JMenuItem("Personalizado");
		
		JMenu menuEjecutar = new JMenu("Ejecutar");
		JMenuItem pruebaMenuI = new JMenuItem("Prueba");
		JMenuItem adquirirMenuI = new JMenuItem("Adquirir");
		JMenuItem detenerMenuI = new JMenuItem("Detener");
		
		JMenu menuInformacion = new JMenu("Informacion");
		JMenuItem comandosMenuI = new JMenuItem("Comandos");
		JMenuItem acercaDeMenuI = new JMenuItem("Acerca del software");
		
		menuBar.add(menuArchivo);
		menuArchivo.add(guardarMenuI);
		menuArchivo.add(limpiarMenuI);
		menuArchivo.add(salirMenuI);
		
		menuBar.add(menuConfiguracion);
		menuConfiguracion.add(porDefectoMenuI);
		menuConfiguracion.add(personalizadoMenuI);
		
		menuBar.add(menuEjecutar);
		menuEjecutar.add(pruebaMenuI);
		menuEjecutar.add(adquirirMenuI);
		menuEjecutar.add(detenerMenuI);
		
		menuBar.add(menuInformacion);
		menuInformacion.add(comandosMenuI);
		menuInformacion.add(acercaDeMenuI);
		
		//Elements Top
		JLabel lblpuertoSerial = new JLabel("PuertoSerial");
		textpuertoSerial = new JTextField();
		textpuertoSerial.setColumns(4);
		textpuertoSerial.setText("COM3");
		JLabel lblNChannel = new JLabel("N.Channel");
		textNChannel = new JTextField();
		textNChannel.setColumns(1);
		textNChannel.setText("1");
		rdbtnTrigger = new JRadioButton("Trigger");
		JLabel lblPTrigger = new JLabel("Posicion Trigger");
		textPTrigger = new JTextField();
		textPTrigger.setColumns(1);
		textPTrigger.setText("1");
		JLabel lblPMartillo = new JLabel("Posicion Martillo");
		textPMartillo = new JTextField();
		textPMartillo.setColumns(1);
		textPMartillo.setText("1");
		
		JButton btnAceptarConfig = new JButton("Aceptar");
		JPanel topPanelConfiguracion = new JPanel();
		
		topPanelConfiguracion.setBackground(Color.WHITE);
		topPanelConfiguracion.add(rdbtnTrigger);
		topPanelConfiguracion.add(lblpuertoSerial);
		topPanelConfiguracion.add(textpuertoSerial);
		topPanelConfiguracion.add(lblNChannel);
		topPanelConfiguracion.add(textNChannel);
		topPanelConfiguracion.add(lblPTrigger);
		topPanelConfiguracion.add(textPTrigger);
		topPanelConfiguracion.add(lblPMartillo);
		topPanelConfiguracion.add(textPMartillo);
		topPanelConfiguracion.add(btnAceptarConfig);
		contentPane.add(topPanelConfiguracion, BorderLayout.NORTH);
		topPanelConfiguracion.setVisible(false);
		
		//Elements Center
		centerPanel = new JPanel();
		infoPanel = new JPanel();
		graphPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(Color.WHITE);
		infoPanel.setBackground(Color.WHITE);
		graphPanel.setBackground(Color.WHITE);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.add(infoPanel);
		centerPanel.add(graphPanel);
		
		JLabel lblinfoTrigger = new JLabel("P.Trigger");
		textInfoTrigger = new JTextField();
		textInfoTrigger.setColumns(2);
		textInfoTrigger.setText(Integer.toString(pTrigger));
		textInfoTrigger.setEnabled(false);
		
		JLabel lblinfoMartillo = new JLabel("P.Martillo");
		textInfoMartillo = new JTextField();
		textInfoMartillo.setColumns(2);
		textInfoMartillo.setText(Integer.toString(pMartillo));
		textInfoMartillo.setEnabled(false);
		
		infoPanel.add(lblinfoTrigger);
		infoPanel.add(textInfoTrigger);
		infoPanel.add(lblinfoMartillo);
		infoPanel.add(textInfoMartillo);
		
		//Elements Low
		btnPrueba = new JButton("Prueba");
		btnIniciar = new JButton("Adquirir");
		btnSalir = new JButton("Salir");
		btnSgteCh = new JButton("Siguiente");
		btnSgteCh.setVisible(false);
		JPanel lowPanel = new JPanel();
		
		lowPanel.setBackground(Color.WHITE);
		lowPanel.add(btnPrueba);
		lowPanel.add(btnSgteCh);
		lowPanel.add(btnIniciar);
		lowPanel.add(btnSalir);
		contentPane.add(lowPanel, BorderLayout.SOUTH);
		
		btnPrueba.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtPrueba) {
				 
				 tipoAdquisicion = adquisicionPrueba;
				 adquirirData(tipoAdquisicion);
			 } 
			 
		});
		
		btnIniciar.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtIniciar) {
				 
				 capturarMuestras();
			 }
		});
		
		btnSalir.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtSalir) {
				 
				 System.exit(0);
			 }
		});
		
		btnSgteCh.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtSalir) {
				 
				 guardarAsImagen();
				 conteoChannel++;
				 x = 0;
				 btnIniciar.setText("Adquirir");
				 btnIniciar.setEnabled(true);
				 btnSgteCh.setVisible(false);
			 }
		});
		
		btnAceptarConfig.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtAceptarConfig) {
				 
				 definirConfiguracion(configuracionPerz);
				 topPanelConfiguracion.setVisible(false);
			 }
		});
		
		//Action Listener Archive
		
		guardarMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtSalir) {
				 
				 guardarAsImagen();
			 }
		});
		
		limpiarMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtSalir) {
				 initComponents();
			 }
		});
		
		salirMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtSalir) {
				 System.exit(0);
			 }
		});
		
		//Action Listener Configuration
		porDefectoMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtPorDefecto) {
				 definirConfiguracion(configuracionDefecto);
			 }
		});
		
		personalizadoMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtPersonalizado) {
				 topPanelConfiguracion.setVisible(true);
			 }
		});
		
		//Action Listener Ejecutar
		pruebaMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtPrueba) {
				 
				 tipoAdquisicion = adquisicionPrueba;
				 adquirirData(tipoAdquisicion);
			 }
		});
		
		adquirirMenuI.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evtIniciar) {
				
				 tipoAdquisicion = adquisicion;
				 capturarMuestras();
			}
		});
		
		detenerMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtSalir) {
				 	chosenPort.closePort();
					initComponents();
					contentPane.repaint();
			 }
		});
		
		//Action Listener Information
		acercaDeMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtSalir) {
				 String mensajeAcercaDe = "Desarrollado para AmservingS.A.S.  Ing.E. Cesar Ramirez Filomena. Email: cesarramriezfilomena@hotmail.com";
				 JOptionPane.showMessageDialog( GeoX.this, mensajeAcercaDe, "Acerca de", 1);
			 }
		});
		
		comandosMenuI.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evtSalir) {
				
				String mensajeComandos = "Los comandos son: ";
				JOptionPane.showMessageDialog( GeoX.this, mensajeComandos, "Comandos", 1);
			 }
		});
			
	}

	private void definirConfiguracion(String configuracion) {
		
		dConfiguracion = configuracion;
		
		if(dConfiguracion == "porDefecto") {
			
			puertoSerial = "COM4";
			Trigger = 0;
			nChannel = 1;
			pTrigger = 1;
			pMartillo = 1;
			}
			
		if(dConfiguracion == "perzonalizado") {
			
			puertoSerial = textpuertoSerial.getText();
			nChannel = Integer.parseInt(textNChannel.getText());
			pTrigger = Integer.parseInt(textPTrigger.getText());
			pMartillo = Integer.parseInt(textPMartillo.getText());
			textInfoTrigger.setText(Integer.toString(pTrigger));
			textInfoMartillo.setText(Integer.toString(pMartillo));
			}
	}
	
	private void crearCarpeta() {
		
		File directorios = new File("capturasRefraccion");
        if (!directorios.exists()) {
            if (directorios.mkdirs()) {
                System.out.println("Carpeta creada capturasRefraccion ");
            } else {
                System.out.println("Error al crear directorios");
            }
        }
	}
	
	private void guardarAsImagen() {
		
		if(dTipoAdquisicion == "adquisicion") {
			 try {
					ChartUtils.saveChartAsJPEG(new File("capturasRefraccion/Channel"+ conteoChannel +".jpg"), graficaPrueba, 500, 500 );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 else{ JOptionPane.showMessageDialog( GeoX.this, "Solo habilitado en adquisicion", "Alerta", 2);}
	}
	
	private void capturarMuestras() {
		
		tipoAdquisicion = adquisicion;
		nChannel = Integer.parseInt(textNChannel.getText());
		crearCarpeta();
		
		if(nChannel > 1) {
			
			btnSgteCh.setVisible(true);
		
			if(conteoChannel <= nChannel) {
			
				adquirirData(tipoAdquisicion);
				btnIniciar.setEnabled(false);
			}
			if(conteoChannel > nChannel) {
				
				JOptionPane.showMessageDialog( GeoX.this, "Adquisicion completada.", "Adquisicion", 1);
				chosenPort.closePort();
				btnPrueba.setVisible(true);
				btnIniciar.setText("Adquirir");
				series.clear();
				initComponents();
				contentPane.repaint();
				x = 0;
			}
		} else {
			adquirirData(tipoAdquisicion);
		}
	}
	
	private void adquirirData(String tipoAdquisicion) {
		
		dTipoAdquisicion = tipoAdquisicion;
		series = new XYSeries("Channel " + conteoChannel);
		XYSeriesCollection dataset = new XYSeriesCollection(series);

		if(dTipoAdquisicion == "prueba") { 
			graficaPrueba = ChartFactory.createXYLineChart("Refraccion sismica - Prueba", "Muestras", " ", dataset, 
					PlotOrientation.VERTICAL, true, false, false);
			graphPanel.add(new ChartPanel(graficaPrueba));
			arduData="a";
			}
	
		if(dTipoAdquisicion == "adquisicion") {
			graficaPrueba = ChartFactory.createXYLineChart("Refraccion sismica", "Muestras", " ", dataset, 
					PlotOrientation.VERTICAL, true, false, false);
		//	contentPane.add(new ChartPanel(graficaPrueba), BorderLayout.CENTER);
			graphPanel.add(new ChartPanel(graficaPrueba));
			arduData="b";
			}
		
		contentPane.repaint();
			
		if(btnIniciar.getText().equals("Adquirir")) {
			chosenPort = SerialPort.getCommPort(puertoSerial);
			chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
			if(chosenPort.openPort()) {
				btnPrueba.setVisible(false);
				btnIniciar.setText("Detener");
			}else {
				JOptionPane.showMessageDialog( GeoX.this, "Puerto serial sin conexion, confirmar el puerto asignado al Hardware.", "Puerto serial cerrado", 2);
			}

			Thread threadPrueba = new Thread(){
			@Override public void run() {
				try {Thread.sleep(100); } catch(Exception e) {}
				PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
				output.print(arduData);
				output.flush();
				Scanner scanner = new Scanner(chosenPort.getInputStream());
				while(scanner.hasNextLine()) {
					try {
						if(dTipoAdquisicion == "prueba") {
							String line = scanner.nextLine();
							int number = (Integer.parseInt(line));
							
							if( x == 1) {
								series.add(0, x1);
								series.add(0, -x1);
							}
							
							series.add(x++, number);
							contentPane.repaint();
	
							if(x > 4000) {
								x = 0;
								series.clear();
							}
						}
						
						if(dTipoAdquisicion == "adquisicion") {
							String line = scanner.nextLine();
							int number = (Integer.parseInt(line));
							series.add(x++, number);
							contentPane.repaint();
							if( x > 1000) chosenPort.closePort();
							}
						} catch(Exception e) {}
				
					}
					scanner.close();
				}
			};
			threadPrueba.start();		  
			}else {
				chosenPort.closePort();
				btnPrueba.setVisible(true);
				btnIniciar.setText("Adquirir");
				series.clear();
				initComponents();
				contentPane.repaint();
				x = 0;
			 	}
	}
	
	//Variables
	private JPanel contentPane, centerPanel, infoPanel, graphPanel;
	private JMenuBar menuBar;
	private SerialPort chosenPort;
	private JFreeChart graficaPrueba;
	private XYSeries series;
	private JTextField textNChannel, textpuertoSerial, textPTrigger, textPMartillo, textInfoTrigger, textInfoMartillo;
	private JRadioButton rdbtnTrigger;
	private String puertoSerial, dConfiguracion, configuracionDefecto = "porDefecto", configuracionPerz = "perzonalizado";
	private String tipoAdquisicion, dTipoAdquisicion, adquisicionPrueba ="prueba", adquisicion="adquisicion", arduData;
	private Integer Trigger, nChannel, pTrigger, pMartillo;
	private JButton btnPrueba, btnIniciar, btnSalir, btnSgteCh;
	static int x = 0, x1 = 60000000, conteoChannel = 1;
}
