package SIMULATION.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
//import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
//import javax.swing.JScrollPane;
import javax.swing.JTextField;

import SIMULATION.Datatypes.Repere;
import SIMULATION.Modele.Simulation;
import SIMULATION.Modele.ViewSimulation;
import SIMULATION.View.PanelAffichage ;

 
@SuppressWarnings({ "serial", "unused" })
public class Fenetre extends JFrame implements ViewSimulation
{	
	// Constantes
	static final int height_panel = 600 ;
	static final int width_panel = 600 ;
	
	static final int height_buttons = 150 ;
	static final int width_buttons = 150 ;
	
	// Modele
	private InterfaceModele modele ;
	
	//attributs graphiques
	private JMenuBar jmenubar;
	private JMenu jfichier,jcharger;
	private JMenuItem jquitter;
	private JMenu jpdv;
	
	private JMenuItem jaero,jbal;
	private JMenuItem jcharger_pdv,jsaisie_pdv,aff_traj,jsave_traj;
	
	private JTextField jtf_dbmax;
	private JButton jb_execution,jb_iterer,jb_stop,jb_recommencer,jb_quit;

	private PanelAffichage pan_principal ;
	
	private Double niveauDeZoom ;
	
	//constructeurs
	public Fenetre( InterfaceModele modele )
	{
		super() ;
		
		this.modele =  modele ;
		this.modele.enregistrer( this ) ;
		
		this.niveauDeZoom = 2.0 ;
		
		JFrame jf= new JFrame("simulation");
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		jf.setSize(Fenetre.width_panel + Fenetre.width_buttons ,
				Fenetre.height_panel + Fenetre.height_buttons );
		
		// conteneur pan
		Container pan = jf.getContentPane();
		pan.setLayout( new BorderLayout() );

		// panel	contient le menu	
		JPanel pan_button = new JPanel();
		pan_button.setBackground(Color.ORANGE);
		//pan_button.setSize( Fenetre.width_buttons , Fenetre.height_buttons );
		pan_button.setLayout(new GridLayout(2,3));
		pan.add(pan_button,"North");
					
		// Jlayered pour la superposition des Jpanel ad et balise
		this.pan_principal = new PanelAffichage( this.modele ) ;
		this.pan_principal.setPreferredSize( 
				new Dimension( Fenetre.width_panel , Fenetre.height_panel )
			);
		pan.add(this.pan_principal);
		
	 	
		
		JPanel pan_secondaire= new JPanel();
		pan_secondaire.setBackground(Color.GREEN);  
		pan.add(pan_secondaire,BorderLayout.SOUTH );
		
		JButton jb_zoom_p = new JButton( "+" ) ;
		jb_zoom_p.addActionListener( new ActionZoomP() );
		pan_secondaire .add( jb_zoom_p ) ;
		JButton jb_zoom_m = new JButton( "-" ) ;
		jb_zoom_m.addActionListener( new ActionZoomM() );
		pan_secondaire.add( jb_zoom_m ) ;
		
	 	//JLabel test =new JLabel("test0");
	 	//pan_secondaire.add(test);
		jb_quit=new JButton("Quitter");
		pan_secondaire.add(jb_quit);//,BorderLayout.EAST);//a verifier
		jb_quit.addActionListener(new ActionQuitter());
				
	    // Menu bar
	    jmenubar =new JMenuBar();
	
	    //**** menu "Fichier"	
		
		jf.setJMenuBar(jmenubar);
		jfichier=new JMenu("Fichier");
		jmenubar.add(jfichier);
		
		jcharger= new JMenu("Charger");
		jfichier.add(jcharger);
			
	    jquitter= new JMenuItem("Quitter");
		jfichier.add(jquitter);
	    jquitter.addActionListener(new ActionQuitter());
		
		// ss menu de "Charger"
		jaero= new JMenuItem("Aerodrome");
		jcharger.add(jaero);
		jaero.addActionListener(new ActionChargerAd());
		//jaero.addActionListener(new ActionChargerAd());
		
		jbal= new JMenuItem("Balise");
		jcharger.add(jbal);
		jbal.addActionListener(new ActionChargerBalises());
		
		//**** menu pdv		
		jpdv=new JMenu("Plan de Vol");
		jmenubar.add(jpdv);
		
		// ss menu de "pdv"
	    jcharger_pdv= new JMenuItem("Charger_PDV");
		jpdv.add(jcharger_pdv);
		jcharger_pdv.addActionListener(new ActionChargerPlanDeVol());
		
		jsaisie_pdv= new JMenuItem("Saisie_PDV");
		jpdv.add(jsaisie_pdv);
		jsaisie_pdv.addActionListener(new ActionSaisiePlanDeVol());
		
		
		
			
		//**** menu trajectoire		
		JMenu trajectoire=new JMenu("Trajectoire");
		jmenubar.add(trajectoire);
		
		//	JMenuItem calc_traj= new JMenuItem("Calculer");
		//trajectoire.add(calc_traj);
		
		this.aff_traj= new JMenuItem("Afficher");
		trajectoire.add(aff_traj);
		//jaff_traj.addActionListener(new ActionAfficherTrajectoire());
		this.jsave_traj= new JMenuItem("Sauvegarder");
		trajectoire.add(jsave_traj);
		// TODO  Listener ActionSauvegarderTrajectoire()
		//jaff_traj.addActionListener(new ActionSauvegarderTrajectoire());
		
		//boutons
		jb_execution=new JButton("Execution Automatique");
		pan_button.add(jb_execution);
		// TODO  Listener ActionExecuter()
		//jb_execution.addActionListener(new ActionExecuter());
		
		jb_iterer=new JButton("Pas √† Pas");
		pan_button.add(jb_iterer);
		// TODO  Listener ActionIterer()
		//jb_iterer.addActionListener(new ActionIterer());
		
		jb_stop=new JButton("Pause");
		pan_button.add(jb_stop);
		//jb_stop.addActionListener(new ActionStop());
		
				
		JLabel jl_dbmax=new JLabel("Distance Maximale ");
		pan_button.add(jl_dbmax);
				
		
		jtf_dbmax=new JTextField("Distance max");
		pan_button.add(jtf_dbmax);
		// TODO  Listener ActionDistanceDbmax()
		//jtf_dbmax.addItemListener(new ActionDistanceDbmax())
				
		// TODO fusionner avec pause
		jb_recommencer=new JButton("Recommencer");
		pan_button.add(jb_recommencer);
		// TODO  Listener ActionRecommencer
		//	jb.addActionListener(new ActionRecommencer());
		
	 
		// visualisation de la fenetre
		jf.pack();
		jf.setVisible(true);   
	
	
	} // fin constructeur       fenetre 		
		
		 
	// methodes
	// DONE
	public void quitter()
	{
		 System.out.println("Fin ...");
		 if (JOptionPane.showConfirmDialog(this, "DÈsirez-vous quitter l'application ?")
	              == JOptionPane.YES_OPTION) System.exit(0); 
		 setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		 setVisible(true);
		 //System.exit(0);
	}
	
	// DONE
	private String demander_nom_fichier( boolean must_exist )
	{
		//JFrame boite_message = new JFrame("nom du fichier");
		// boite_message.setVisible(true);
        String nomFichier = JOptionPane.showInputDialog(Fenetre.this, "Entrez le nom du fichier a charger","Nom du Fichier",-1);	
        
        if( must_exist )
        {
        	try {
        		JTextArea document = new JTextArea();
        		document.setBackground(Color.ORANGE);
        		document.read(new FileReader(nomFichier), null);
        		JFrame fichier = new JFrame(nomFichier);
        		fichier.add(new JScrollPane(document));
        		//fichier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		fichier.setSize(400, 300);
        		fichier.setVisible(true);
        		}   
        	catch (IOException ex) 
        	{
        		JOptionPane.showMessageDialog(null, 
        			"ATTENTION, le fichier " +nomFichier+ " n'existe pas", 
        			"Alerte", 
        			JOptionPane.ERROR_MESSAGE);
        			nomFichier= null ;
        	}
        }
        
		return (nomFichier) ;
	} 
				 
	
	
	
	
	//// listener
	
	
	// DONE
	class ActionQuitter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Fenetre.this.quitter();
		}
	}
	
	// DONE
	public class ActionChargerAd implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt ad");
			
			String ficname = Fenetre.this.demander_nom_fichier( true ) ;
			System.err.println( ficname ) ;

			if( ficname != null)
			{	
				Fenetre.this.modele.charger_aerodromes( ficname ) ;
				System.out.println("fin chargt ad");						
			}
		}
	}
	
	
	// DONE
	public class ActionChargerBalises implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt balises");
			
			String ficname = Fenetre.this.demander_nom_fichier( true ) ;
			System.err.println( ficname ) ;
			
			if( ficname != null)
			{
				Fenetre.this.modele.charger_balises( ficname ) ;
				System.out.println("fin chargt balises");
			}			
		}
	}
	
	// DONE
	public class ActionChargerPlanDeVol implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt Plan de vol");
			String ficname = Fenetre.this.demander_nom_fichier( true ) ;
			System.err.println( ficname ) ;		
			
			Fenetre.this.modele.charger_avions( ficname ) ;
			
			System.out.println("fin chargt Plan de vol");
						
		}
	}
	
	
	
	
	public class ActionSaisiePlanDeVol implements ActionListener
	{
		
		private String indicatif ;
		private int flight_level ;
		private Date heure_depart ;
		private Date heure_arrivee ;
		private Repere depart ;
		private Repere arrivee ;
		private Double vitesse ;
		
		public void ActionSaisiePlandeVol() {
			
			this.indicatif = "";
			this.flight_level = 0  ;
			this.heure_depart = null ;
			this.heure_arrivee = null ;
			this.depart = null ;
			this.arrivee = null ;
			this.vitesse = 0.0 ;
			
		}
		
		
		
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("saisie Plan de vol");
			//creation fenetre
			
			JFrame jf_pdv= new JFrame("Saisie plan de vol");
			jf_pdv.setSize(320, 150);
			
			JPanel pannel = new JPanel();
			
			Container pan_pdv = jf_pdv.getContentPane();
			pan_pdv.setLayout( new GridLayout( 6 , 2 ) ) ;
			
			JLabel indicatif_prompt = new JLabel( "Indicatif:" ) ;
			pan_pdv.add( indicatif_prompt ) ;
			JTextField indicatif_input =new JTextField("Indicatif");
			pan_pdv.add( indicatif_input ) ;
			
			// TODO LISTENER 
			//indicatif.addItemListener(new ActionSaisieIndicatif());
			
			
			JLabel heure_depart_prompt = new JLabel( "Heure de départ:" ) ;
			pan_pdv.add( heure_depart_prompt ) ;
			// TODO LISTENER SAISIE DATE
			// FAKE
			JTextField fake_dep = new JTextField( "fake_dep" ) ;
			pan_pdv.add( fake_dep ) ;
			
			JLabel heure_arrivee_prompt = new JLabel( "Heure d'arrivée:" ) ;
			pan_pdv.add( heure_arrivee_prompt ) ;
			// TODO LISTENER SAISIE DATE 
			// FAKE
			JTextField fake_arr = new JTextField( "fake_arr" ) ;
			pan_pdv.add( fake_arr ) ;
			
			
			JLabel depart_prompt = new JLabel( "Départ:" ) ;
			pan_pdv.add( depart_prompt ) ;
			// TODO COMPLETER RADIOBUTTON + INTEGRER COMBOBOX
			JRadioButton depart_type = new JRadioButton() ;
			// TODO ADD LISTENER
			
			JLabel arrivee_prompt = new JLabel( "Arrivée:" ) ;
			pan_pdv.add( arrivee_prompt ) ;
			// TODO COMPLETER RADIOBUTTON + INTEGRER COMBOBOX
			JRadioButton arrrivee_type = new JRadioButton() ;
			// TODO  ADD LISTENER
			
			JLabel fl_prompt = new JLabel( "Niveau de vol:" ) ;
			pan_pdv.add( fl_prompt ) ;
			// TODO LISTENER
			JTextField niveau_vol_input =new JTextField("niveau de vol");
			pan_pdv.add( niveau_vol_input ) ;
			
			JLabel vitesse_prompt = new JLabel( "Vitesse:" ) ;
			pan_pdv.add( vitesse_prompt ) ;
			// TODO LISTENER
			JTextField vitesse_input =new JTextField("vitesse");
			pan_pdv.add( vitesse_input ) ;
			
			
			// TODO LISTENER
			//indicatif.addItemListener(new ActionSaisieNiveauDeVol());
			
			//jf_pdv.getContentPane().add(pannel);
			jf_pdv.pack() ;		
			jf_pdv.setVisible(true);  
			
			
			
			
			Fenetre.this.modele.creer_avion( this.indicatif , this.depart , this.arrivee , this.flight_level , this.vitesse, this.heure_depart ) ;
			
			
			
			
			
			
			System.out.println("fin saisie Plan de vol");
						
		}
	}

	
	
	
	
	
	
	
	// DONE
	public class ActionZoomP implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("zoom");
			
			Fenetre.this.pan_principal.zoomer( Fenetre.this.niveauDeZoom ) ;
			Fenetre.this.rafraichir();
			}
	}
	
	
	// DONE
	public class ActionZoomM implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("zoom");
			
			Fenetre.this.pan_principal.zoomer( 1 / Fenetre.this.niveauDeZoom ) ;
			Fenetre.this.rafraichir();
			}
	}
	
	
	// DONE
	public boolean rafraichir() {
		
		this.pan_principal.revalidate();
		this.pan_principal.repaint();
		
		return true ;
	}
	
	public static void main(String[] args) {
		
		Simulation modele = new Simulation() ;
		
		
		
		Fenetre fen_1 = new Fenetre( modele );
		//Fenetre fen_2 = new Fenetre( modele );
		
		modele.charger_balises( "./fichiers/balises_fr.txt" ) ;
		modele.charger_aerodromes( "./fichiers/aerodromes_fr.txt" ) ;
	}
	
	
}// end class
