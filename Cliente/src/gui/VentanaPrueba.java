package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

import businessDelegate.BusinessDelegate;
import dtos.*;
import enums.TipoEnvite;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class VentanaPrueba extends JFrame implements  ActionListener{

	private static final long serialVersionUID = 1L;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	private DefaultTableModel listaMovimientos, listaCartas;
	private JButton btnActBaza, btnActCantos, btnActPuntaje, btnActCartas, btnCantar, btnTirar;

	private BusinessDelegate businessDelegate;
	private JugadorDTO yo;
	private PartidoDTO miPartido;

	private List<PuntajeParejaDTO> puntajes;
	private JSeparator jSeparator1;
	private JLabel lblPuntosPareja2;
	private JLabel lblPuntos1;
	private JLabel lblPuntosPareja1;
	private JLabel lblPuntos;
	private JPanel pnlNE;
	private JLabel lblJugadorActual;
	private JLabel lblNewLabel;
	private JButton btnActualizarInformacionJuego;
	private JLabel jLabel1;
	private JSeparator jSeparator2;
	private JLabel lblPuntos2;
	private JPanel panel;
	private JScrollPane jScrollPane1;
	private List<CartaJugadorDTO> cartasJugador;
	private JComboBox<String> comboBox;
	private JTable tableBaza;
	private JTable tableCartas;
	private JScrollPane scrollPane;
	
	
	public VentanaPrueba(PartidoDTO partido, JugadorDTO jugador) throws RemoteException
	{
		setTitle(jugador.toString());
		
		businessDelegate = new BusinessDelegate();
		
		yo = jugador;
		miPartido = partido;
				
		initUI();
		this.setLocation(50 , 100);
		setVisible(true);
		this.pack();
		this.setSize(654, 404);
	}

	private void initUI()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		FlowLayout thisLayout = new FlowLayout();
		getContentPane().setLayout(thisLayout);
		JPanel pnlNO = new JPanel(new GridLayout(4, 1));
		pnlNO.setLayout(null);
		{
			panel = new JPanel();
			pnlNO.add(panel);
			panel.setLayout(null);
			panel.setOpaque(true);
			panel.setBounds(7, 5, 619, 43);
			panel.setSize(619, 30);
			{
				lblNewLabel = new JLabel("Jugador Actual :");
				panel.add(lblNewLabel);
				lblNewLabel.setFont(new Font("Tahoma",Font.BOLD,11));
				lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel.setText("Jugador Actual :");
				lblNewLabel.setBounds(4, 0, 101, 30);
			}
			{
				lblJugadorActual = new JLabel("");
				panel.add(lblJugadorActual);
				lblJugadorActual.setBackground(Color.BLACK);
				lblJugadorActual.setText("");
				lblJugadorActual.setFont(new java.awt.Font("Segoe UI",1,16));
				lblJugadorActual.setBounds(103, 0, 157, 30);
			}
			{
				btnActBaza = new JButton("Actualizar Baza");
				panel.add(btnActBaza);
				btnActBaza.addActionListener(this);
				btnActBaza.setAlignmentX(0.5f);
				btnActBaza.setText("Actualizar MOVIMIENTOS BAZA");
				btnActBaza.setFont(new java.awt.Font("Segoe UI",0,12));
				btnActBaza.setBounds(408, 0, 211, 30);
			}
		}

		{
			jScrollPane1 = new JScrollPane();
			pnlNO.add(jScrollPane1);
			jScrollPane1.setMinimumSize(new java.awt.Dimension(21, 21));
			jScrollPane1.setBounds(2, 41, 628, 75);
			{
				tableBaza = new JTable();
				jScrollPane1.setViewportView(tableBaza);
				listaMovimientos = new DefaultTableModel(
						new Object[][] {
							{null, null, null},
						},
						new String[] {
							"TipoMov", "Carta / Envite", "Fecha / Hora"
						}
						);
				listaMovimientos.setRowCount(20);
				tableBaza.setModel(listaMovimientos);
				tableBaza.setPreferredSize(new java.awt.Dimension(622, 50));
				tableBaza.getTableHeader().setSize(622, 50);
				tableBaza.getTableHeader().setPreferredSize(new java.awt.Dimension(225, 20));
				tableBaza.getTableHeader().setAutoscrolls(true);
			}
		}
		{
			JPanel pnlSO = new JPanel(new GridLayout(3, 2));
			pnlNO.add(pnlSO);
			pnlSO.setLayout(null);
			pnlSO.setBorder(BorderFactory.createTitledBorder(""));
			pnlSO.setBounds(2, 121, 628, 235);
			{
				btnActCartas = new JButton("Actualizar Cartas");
				pnlSO.add(btnActCartas);
				FlowLayout btnActCartasLayout = new FlowLayout();
				btnActCartas.setLayout(btnActCartasLayout);
				btnActCartas.setBounds(40, 105, 150, 30);
				btnActCartas.addActionListener(this);
			}
			{
				btnTirar = new JButton("Tirar Carta");
				pnlSO.add(btnTirar);
				FlowLayout btnTirarLayout = new FlowLayout();
				btnTirar.setLayout(btnTirarLayout);
				btnTirar.addActionListener(this);
				btnTirar.setBounds(191, 105, 150, 30);
			}
			{
				scrollPane = new JScrollPane();
				pnlSO.add(scrollPane);
				scrollPane.setBounds(5, 28, 337, 74);
				{
					tableCartas = new JTable();
					scrollPane.setViewportView(tableCartas);
					listaCartas = new DefaultTableModel(
							new Object[][] {
									{null, null},
									{null, null},
									{null, null},
							},
							new String[] {
									"Carta", "Tirada"
							}
							);
					tableCartas.setModel(listaCartas);
				}
			}
			{
				pnlNE = new JPanel();
				pnlSO.add(pnlNE);
				pnlNE.setLayout(null);
				pnlNE.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
				pnlNE.setToolTipText("Partido a 30 puntos!");
				pnlNE.setBounds(424, 32, 159, 64);
				{
					lblPuntosPareja1 = new JLabel();
					pnlNE.add(lblPuntosPareja1);
					lblPuntosPareja1.setText("Pareja 1");
					lblPuntosPareja1.setHorizontalAlignment(SwingConstants.RIGHT);
					lblPuntosPareja1.setBounds(3, 4, 67, 29);
					lblPuntosPareja1.setFont(new java.awt.Font("Segoe UI",2,12));
				}
				{
					lblPuntos1 = new JLabel();
					pnlNE.add(lblPuntos1);
					lblPuntos1.setBounds(89, 4, 34, 29);
				}
				{
					lblPuntosPareja2 = new JLabel();
					pnlNE.add(lblPuntosPareja2);
					lblPuntosPareja2.setText("Pareja 2");
					lblPuntosPareja2.setHorizontalAlignment(SwingConstants.RIGHT);
					lblPuntosPareja2.setBounds(3, 33, 72, 29);
					lblPuntosPareja2.setFont(new java.awt.Font("Segoe UI",2,12));
					lblPuntosPareja2.setSize(67, 29);
				}
				{
					lblPuntos2 = new JLabel();
					pnlNE.add(lblPuntos2);
					lblPuntos2.setBounds(89, 33, 34, 29);
				}
				{
					jSeparator1 = new JSeparator();
					pnlNE.add(jSeparator1);
					jSeparator1.setBounds(81, 12, 8, 42);
					jSeparator1.setOrientation(SwingConstants.VERTICAL);
				}
			}
			{
				lblPuntos = new JLabel();
				pnlSO.add(lblPuntos);
				lblPuntos.setText("Puntajes");
				lblPuntos.setFont(new java.awt.Font("Segoe UI",1,12));
				lblPuntos.setHorizontalAlignment(SwingConstants.LEFT);
				lblPuntos.setBounds(424, 3, 69, 21);
			}
			{
				btnActPuntaje = new JButton();
				pnlSO.add(btnActPuntaje);
				btnActPuntaje.setText("Actualizar Puntaje");
				btnActPuntaje.setBounds(433, 100, 150, 30);
				btnActPuntaje.addActionListener(this);
			}
			{
				jSeparator2 = new JSeparator();
				pnlSO.add(jSeparator2);
				jSeparator2.setOrientation(SwingConstants.VERTICAL);
				jSeparator2.setBounds(379, 8, 12, 209);
			}
			{
				JPanel pnlSE = new JPanel(new GridLayout(3, 2));
				pnlSO.add(pnlSE);
				pnlSE.setLayout(null);
				pnlSE.setBounds(7, 141, 307, 86);
				{
					JLabel lblCantos = new JLabel("Cantos");
					pnlSE.add(lblCantos, "0, 0");
					lblCantos.setBounds(0, 0, 131, 21);
					lblCantos.setFont(new java.awt.Font("Segoe UI",1,12));
				}
				{
					comboBox = new JComboBox<String>();
					pnlSE.add(comboBox, "1, 0");
					comboBox.setBounds(0, 25, 300, 24);
				}
				{
					btnActCantos = new JButton("Actualizar Cantos");
					pnlSE.add(btnActCantos, "2, 0");
					btnActCantos.setBounds(0, 51, 150, 30);
					btnActCantos.addActionListener(this);
				}
				{
					btnCantar = new JButton("Cantar");
					pnlSE.add(btnCantar, "3, 0");
					btnCantar.setBounds(150, 51, 150, 30);
					btnCantar.addActionListener(this);
				}
			}
			{
				jLabel1 = new JLabel();
				pnlSO.add(jLabel1, "0, 0");
				jLabel1.setText("Cartas en mano");
				jLabel1.setFont(new java.awt.Font("Segoe UI",1,12));
				jLabel1.setBounds(7, 3, 96, 21);
			}
			{
				btnActualizarInformacionJuego = new JButton("Actualizar Informacion Juego");
				pnlSO.add(btnActualizarInformacionJuego);
				btnActualizarInformacionJuego.setText("Actualizar Informacion Juego");
				btnActualizarInformacionJuego.setBounds(403, 145, 206, 59);
				btnActualizarInformacionJuego.setForeground(new java.awt.Color(255,0,0));
				btnActualizarInformacionJuego.setFont(new java.awt.Font("Segoe UI",1,12));
				btnActualizarInformacionJuego.addActionListener(this);
			}
		}

		getContentPane().add(pnlNO);
		pnlNO.setPreferredSize(new java.awt.Dimension(640, 381));

	}

	private void actualizarInformacionJuego() {
		btnActBaza.doClick();
		btnActCartas.doClick();
		btnActPuntaje.doClick();
		btnActCantos.doClick();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnActPuntaje)) {
			try {
				puntajes = businessDelegate.obtenerPuntajeChico(miPartido, yo);
				lblPuntos1.setText(Integer.toString(puntajes.get(0).getPuntaje()));
				lblPuntos2.setText(Integer.toString(puntajes.get(1).getPuntaje()));
			} catch (RemoteException re) {
				System.out.println(re.getMessage());
			}
		} else

		if (e.getSource().equals(btnActCartas)) {
			cartasJugador = new ArrayList<CartaJugadorDTO>();
			try {
				cartasJugador = businessDelegate.obtenerCartasJugador(miPartido, yo);

				listaCartas.setRowCount(0);
				for (CartaJugadorDTO cartaJugador : cartasJugador) {
					Vector<String> aux = new Vector<String>();
					
					aux.add(cartaJugador.getCarta().toString());
					aux.add(cartaJugador.isTirada() ? "En Mesa" : "Sin Tirar");
					
					listaCartas.addRow(aux);
				}
				
			} catch (RemoteException e1) {
				System.out.println(e1.getMessage());
			}
		} else
		
		if (e.getSource().equals(btnActCantos)) {
			try {
				comboBox.removeAllItems();
				List<TipoEnvite> envitesDisponibles = businessDelegate.obtenerEnvitesDisponibles(miPartido, yo);
				for (TipoEnvite envite: envitesDisponibles) {					
					comboBox.addItem(envite.name());
				}
			} catch (RemoteException e1) {
				System.out.println(e1.getMessage());
			}
		} else

		if (e.getSource().equals(btnTirar)) {
			CartaTiradaDTO cartaTirada = new CartaTiradaDTO();
			for (CartaJugadorDTO cartaJugador: cartasJugador) {
				if (cartaJugador.getCarta().toString().equalsIgnoreCase((String) listaCartas.getValueAt(tableCartas.getSelectedRow(), 0))) {
					cartaTirada.setCartaJugador(cartaJugador);
					cartaTirada.setFechaHora(new Timestamp (Calendar.getInstance().getTimeInMillis()));
					break;
				}
			}
			try {
				businessDelegate.nuevoMovimientoPartido(miPartido, yo, cartaTirada);
				btnActCartas.doClick();
			} catch (RemoteException re) {
				System.out.println(re.getMessage());
			}
		} else

		if (e.getSource().equals(btnActualizarInformacionJuego)) {
			try {
				JugadorDTO jugadorActual = businessDelegate.obtenerJugadorActual(miPartido, yo);

				if (jugadorActual.getApodo().equals(yo.getApodo()))
					lblJugadorActual.setForeground(Color.red);
				else
					lblJugadorActual.setForeground(Color.black);

				lblJugadorActual.setText(jugadorActual.getApodo());

				actualizarInformacionJuego();
			} catch (RemoteException re) {
				System.out.println(re.getMessage());;
			}
		} else
		
		if (e.getSource().equals(btnCantar)) {
			EnviteDTO envite = new EnviteDTO();
			envite.setFechaHora(new Timestamp (Calendar.getInstance().getTimeInMillis()));
			envite.setTipoEnvite(TipoEnvite.obtenerPorTipo((String) comboBox.getSelectedItem()));			
			try {
				businessDelegate.nuevoMovimientoPartido(miPartido, yo, envite);
				actualizarInformacionJuego();
			} catch (RemoteException re) {
				System.out.println(re.getMessage());
			}
		} else

		if (e.getSource().equals(btnActBaza)) {
			try {
				List<MovimientoDTO> movimientos = businessDelegate.obtenerMovimientosUltimaBaza(miPartido, yo);

				// borro la tabla entera porque obtengo TODOS los movimientos
				listaMovimientos.setRowCount(0);
				for (MovimientoDTO movimiento : movimientos) {
					Vector<String> aux = new Vector<String>();

					//	CABECERA : "TipoMov", "Carta / Envite", "Fecha / Hora"

					if (movimiento instanceof EnviteDTO) {
						EnviteDTO envite = (EnviteDTO) movimiento;

						aux.add("Envite");
						aux.add(envite.getJugador().getApodo() + " canto " + envite.getTipoEnvite().name());
						aux.add(sdf.format(envite.getFechaHora()));
					} else {
						CartaTiradaDTO cartaTirada = (CartaTiradaDTO) movimiento;

						aux.add("CartaTirada");
						aux.add(cartaTirada.getCartaJugador().getJugador().getApodo() + " tiro " + cartaTirada.getCartaJugador().getCarta().toString());
						aux.add(sdf.format(cartaTirada.getFechaHora()));
					}

					listaMovimientos.addRow(aux);
				}

			} catch (RemoteException re) {
				System.out.println(re.getMessage());
			}
		}
	}
		
/*
		if(e.getSource().equals(btnSalir))
		{
			System.exit(0);
		}
		if(e.getSource().equals(btnBorrar))
		{
			try {
				String eliminar = (String) (listaDeAlumnos.getValueAt(table.getSelectedRow(),0));
				businessDelegate.eliminarAlumno(Integer.parseInt(eliminar));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
					
		}
		if(e.getSource().equals(btnVolver))
		{
			MenuPrincipal menu = new MenuPrincipal();
			businessDelegate.eliminarObserver(this);
			setVisible(false);
		}
	}*/

}
