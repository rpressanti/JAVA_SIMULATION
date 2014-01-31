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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
//import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	private Simulation modele ;
	
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
	
	
	//constructeurs
	public Fenetre( Simulation modele )
	{
		super() ;
		
		this.modele =  modele ;
		this.modele.enregistrer( this ) ;
		
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
		
	 	
	 // TODO  placer ascenseur
		/*JScrollPane  j_scroll=new JScrollPane();
		//pan_principal.add(j_scroll, BorderLayout.SOUTH);
		*/
			
		
		JPanel pan_secondaire= new JPanel();
		pan_secondaire.setBackground(Color.GREEN);  
		pan.add(pan_secondaire,BorderLayout.SOUTH );
		
		JButton jb_zoom_p = new JButton( "+" ) ;
		//jb_zoom_p.addActionListener( new ActionZoomP() );
		pan_secondaire .add( jb_zoom_p ) ;
		JButton jb_zoom_m = new JButton( "-" ) ;
		//jb_zoom_m.addActionListener( new ActionZoomM() );
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
		// TODO  Listener ActionSaisiePdv()
		//jsaisie_pdv.addActionListener(new ActionSaisiePdv());
		
		
			
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
		
		jb_iterer=new JButton("Pas à Pas");
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
	public void quitter()
	{
		 System.out.println("Fin ...");
		 System.exit(0);
	}
	
	// TODO CODE DEMANDER NOM FICHIER
	private String demander_nom_fichier()
	{
			
		return "" ;
	} 
				 
			 // TODO Deplacer dans modele
			 /*	
			 Scanner scan=null;
				try {
					scan = new Scanner(new FileReader(ficname));
					 while(scan.hasNext()) { // on lit le fichier ligne par ligne. une ligne = une personne.
						Scanner ps=new Scanner(scan.next()); 
						//ps.useDelimiter(";|\n");
						ps.useDelimiter(",||\n");
						System.out.println(ps);
						//liste.add(new Personne(ps.next(),ps.next(),ps.next()));
					 }
					 scan.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}*/

		 
	
	//// listener
	class ActionQuitter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//System.exit(0);
			Fenetre.this.quitter();
		}
	}
	
	
	public class ActionChargerAd implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt ad");
			
			String ficname = Fenetre.this.demander_nom_fichier() ;
			System.err.println( ficname ) ;
			// TODO UNCOMMENT
			//this.modele.chargerAerodromes( ficname ) ;
			
			System.out.println("fin chargt ad");						
		}
	}
	
	public class ActionChargerBalises implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt balises");
			
			String ficname = Fenetre.this.demander_nom_fichier() ;
			System.err.println( ficname ) ;
			// TODO UNCOMMENT
			//this.modele.chargerBalises( ficname ) ;
			
			System.out.println("fin chargt balises");
						
		}
	}
	
	
	public class ActionChargerPlanDeVol implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt Plan de vol");
			String ficname = Fenetre.this.demander_nom_fichier() ;
			System.err.println( ficname ) ;		
			
			Fenetre.this.modele.charger_avions( ficname ) ;
			
			System.out.println("fin chargt Plan de vol");
						
		}
	}
	

	public class ActionZoomP implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("zoom");
			
			Fenetre.this.pan_principal.zoomer(2) ;
			Fenetre.this.rafraichir();
			}
	}
	
	
	
	// TODO RAFRAICHIR
	public boolean rafraichir() {
		
		return true ;
	}
	
	public static void main(String[] args) {
		
		Simulation modele = new Simulation() ;
		
		Fenetre fen_1 = new Fenetre( modele );
		Fenetre fen_2 = new Fenetre( modele );
		modele.charger_balises( "/home/eleve/IESSA/pressari/PROJET_JAVA/balises_fr.txt" ) ;
		//modele.charger_aerodromes( "/home/eleve/IESSA/pressari/PROJET_JAVA/aerodromes_fr.txt" ) ;
	}
	
	
}// end class
