/**
 * @author Nora
 * fenetre definit l' interface graphique du projet de simulation 
 * Elle decrit l'ensemble des boutons, méthodes nécessaires 
 * à la création de cette fenêtre
 */
package SIMULATION.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.print.DocFlavor.URL;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import SIMULATION.Datatypes.Repere;
import SIMULATION.Modele.Simulation;
import SIMULATION.Modele.ViewSimulation;

 @SuppressWarnings({ "serial", "unused" })
public class Fenetre extends JFrame implements ViewSimulation
{	
	// dimension des différents panels
	/**
	 * panel principal  600*600
	 */
	static final int height_panel = 600 ;
	static final int width_panel = 600 ;
	
	/**
	 * taille des boutons  ds le panel associe aux boutons
	 * pan_button
	 */
	static final int height_buttons = 50 ;
	static final int width_buttons = 600 ;
	
	static final int height_state_bar = 50 ;
	static final int width_state_bar  = 600 ;
	
	
	
	/**
	 * InterfaceModel est l'interface a laquelle répond le modéle sur lequel on agit et que l'on visualise
	 */
	// Modele
	private InterfaceModele modele ;
	
	/**
	 * Booléen servant à activer ou désactiver l'itération automatique via un timer
	 */
	private boolean execution_automatique ;
	private Timer timer_iteration ;
	
	
	/**
	 * barre du menu principal compose de 
	 *  Fichier
	 *  Plan de Vol 
	 *  Trajectoire
	 */
	//attributs graphiques
	
	private JMenuBar jmenubar;
	
	private JMenu jfichier;
	private JMenu jcharger;
	private JMenu jpdv;
	
	/**
	 * menu Fichier 
	 * menu Plan de Vol
	 * menu Trajectoire
	 */
	
	/**
	 * sous menu chargement aérodrome du menu Fichier
	 */
	private JMenuItem jaero;
	/**
	 * sous menu chargement balise du menu Fichier
	 */
	private JMenuItem jbal;
	/**
	 * sous-menu de  fichier 
	 */
	private JMenuItem jcharger_pdv;
	/**
	 * sous menu Plan de Vol
	 * chargement du plan de vol
	 */
	private JMenuItem jsaisie_pdv;
	/**
	 * sous menu Plan de Vol
	 * saisie du plan de vol
	 */
	private JMenuItem aff_traj;
	 /**
	 * sous menu Trajectoire
	 * affichage de la trajectoire
	 */
	private JMenuItem jsave_traj;
	
	/**
	 * sous-menu Fichier
	 * quitter l'application
	 */
	private JMenuItem jquitter;
	
	/**
	 * la distance maximale entre 2 avions
	 */
	private JTextField jtf_dbmax;
	/**
	 * bouton execution automatique
	 */
	private JButton jb_execution;
	/**
	 * bouton execution pas a pas
	 */
	private JButton jb_iterer;
	/**
	 * bouton arrêt
	 */
	private JButton jb_stop;
	/**
	 * bouton recommencer
	 */
	private JButton jb_recommencer;
	/**
	 * bouton quitter
	 */
	private JButton jb_quit;

	/**
	 * pan_affichage est le JLayeredPanel contenant les JPanels spécifiques à chaque type d'objets
	 */
	private PanelAffichage pan_principal ;
	/**
	 * niveau de zoom utilisé par les boutons de zoom ("+" et "-" )
	 */
	private Double niveauDeZoom ;
	
	//constructeurs
	/**
	 * {@link Fenetre#modele}
	 */
	public Fenetre( InterfaceModele modele )
	{
		super() ;
		
		this.modele =  modele ;
		this.modele.enregistrer( this ) ;
		
		this.execution_automatique = false ;
		this.timer_iteration = new Timer() ;
		this.timer_iteration.scheduleAtFixedRate( new IterationAutomatique() , 500 , 500 );
		
		
		/**
		 *  niveau de zoom
		 */
		this.niveauDeZoom = 2.0 ;
		
		JFrame jf= new JFrame("simulation");
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		
		// tentative pour mettre une icone a la fenetre
		//ImageIcon icon = new ImageIcon("./images/enac_logo.png");
		//jf.setIconImage(icon.getImage());
		jf.setIconImage( getToolkit().getImage("./images/enac_logo.png"));
		
		//this.setIconImage(new ImageIcon("image.gif"));
		
		/*
		 * On fixe la taille de la fenêtre comme étant la somme de celles du panel principal et du panel des boutons
		 * pour éviter que le panel principal n'apparaisse pas. 
		 */
		
		// TODO UNCOMMENT
		jf.setSize(Fenetre.width_panel , // + Fenetre.width_buttons + Fenetre.width_state_bar ,
				Fenetre.height_panel + Fenetre.height_buttons + Fenetre.height_state_bar );
		
		// conteneur pan
		/**
		 * container principal de la fenêtre
		 * ce container contiendra
		 * 1  panel de taille fixe pour les boutons en position haute
		 * 1 JlayeredPanel pour la partie graphique
		 * 1 panel pour les boutons en position basse 
		 */
		Container pan = jf.getContentPane();
		pan.setLayout( new BorderLayout() );

		// panel	contient le menu	
		JPanel pan_button = new JPanel();
		pan_button.setBackground(Color.ORANGE);
		
		pan_button.setLayout(new GridLayout(2,3));
		pan.add(pan_button,"North");
					
		// Jlayered pour la superposition des Jpanel aerodrome et balise
		this.pan_principal = new PanelAffichage( this.modele ) ;
		/**
		 * On fixe la taille du panel principal afin que celui-ci s'affiche au lancement
		 */
		this.pan_principal.setPreferredSize( 
				new Dimension( Fenetre.width_panel , Fenetre.height_panel )
			);
		pan.add(this.pan_principal);
		
		/**
		 * Ce panel contient les boutons zooms et quitter
		 */
		JPanel pan_secondaire= new JPanel();
		pan_secondaire.setBackground(Color.GREEN);
		
		pan_secondaire.setSize( new Dimension( Fenetre.width_state_bar , Fenetre.height_state_bar ) ) ;
		pan.add(pan_secondaire,BorderLayout.SOUTH );
		
		Calendar now = Calendar.getInstance() ;
		Calendar earliestDate_hc = new GregorianCalendar( 2012 , 1 ,1) ;
		Calendar latestDate_hc = new GregorianCalendar( 2014 , 12 ,31 ) ;
		SpinnerDateModel model_heure_courante = new SpinnerDateModel( now.getTime(), 
		earliestDate_hc.getTime(), latestDate_hc.getTime(), Calendar.WEEK_OF_YEAR );
		JSpinner spinner = new JSpinner( model_heure_courante );
		model_heure_courante.addChangeListener( new ActionSaisieHeureCourante() ) ;
		pan_secondaire.add( spinner ) ;						
		
		
		
		
		
		JButton jb_zoom_p = new JButton( "+" ) ;
		jb_zoom_p.addActionListener( new ActionZoomP() );
		pan_secondaire .add( jb_zoom_p ) ;
		JButton jb_zoom_m = new JButton( "-" ) ;
		jb_zoom_m.addActionListener( new ActionZoomM() );
		pan_secondaire.add( jb_zoom_m ) ;
		
	 
		jb_quit=new JButton("Quitter");
		pan_secondaire.add(jb_quit);
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
		
		jsaisie_pdv= new JMenuItem("Saisir_PDV");
		jpdv.add(jsaisie_pdv);
		jsaisie_pdv.addActionListener(new ActionSaisiePlanDeVol( this.modele ));
				
			
		//**** menu trajectoire		
		JMenu trajectoire=new JMenu("Trajectoire");
		jmenubar.add(trajectoire);
		
		JMenuItem calc_traj= new JMenuItem("Calculer");
		trajectoire.add(calc_traj);
		calc_traj.addActionListener( new ActionCalculTrajectoires() ) ;
		
		// TODO  RM
		//this.aff_traj= new JMenuItem("Afficher");
		//trajectoire.add(aff_traj);
		//jaff_traj.addActionListener(new ActionAfficherTrajectoire());
		
		this.jsave_traj= new JMenuItem("Sauvegarder");
		trajectoire.add(jsave_traj);
		this.jsave_traj.addActionListener(new ActionSauvegarderTrajectoire());
		
		//boutons
		jb_execution=new JButton("Execution Automatique");
		pan_button.add(jb_execution);
		jb_execution.addActionListener(new ActionExecuter());
		
		jb_iterer=new JButton("Pas à Pas");
		pan_button.add(jb_iterer);
		jb_iterer.addActionListener(new ActionIterer());
		
		jb_stop=new JButton("Pause");
		pan_button.add(jb_stop);
		jb_stop.addActionListener(new ActionStop());
		
				
		JLabel jl_dbmax=new JLabel("Distance Maximale ");
		pan_button.add(jl_dbmax);
				
		jtf_dbmax=new JTextField("Distance max");
		pan_button.add(jtf_dbmax);
		jtf_dbmax.addActionListener(new ActionDistanceDbmax());
		jtf_dbmax.addMouseListener( new Eff_DBMAX() );
				
		
		jb_recommencer=new JButton("Recommencer");
		pan_button.add(jb_recommencer);
		jb_recommencer.addActionListener(new ActionRecommencer());
		
	 
		// visualisation de la fenetre
		jf.pack();
		this.rafraichir() ;
		jf.setVisible(true);
		
	} // fin constructeur       fenetre 		
		
		 
	// methodes
	/**
	 * @param quitter quitte l'application avec confirmation
	 */
	public void quitter()
	{
		 System.out.println("Fin ...");
		
	// pour mettre licone aviation civil
	//	 ImageIcon img = new ImageIcon("images/enac.png");
	//	 JOptionPane jop1 = new JOptionPane();
		 
		 
	//	 if (jop1.showConfirmDialog(this, "Désirez-vous quitter l'application ?",img)
	      //        == jop1.YES_OPTION) System.exit(0); 
		 if (JOptionPane.showConfirmDialog(this, "Désirez-vous quitter l'application ?")
	              == JOptionPane.YES_OPTION) System.exit(0); 
		
		Fenetre.this.dispose();
		
	}
	
	/**
	 * 
	 * @param must_exist 
	 * @return le nom du fichier à charger
	 */
	private File demander_nom_fichier( boolean must_exist )
	{
		//String nomFichier = JOptionPane.showInputDialog(Fenetre.this, "Entrez le nom du fichier a charger","Nom du Fichier",-1);	
		File fichier = null ;
		JFileChooser fc = new JFileChooser( new File( "." ) ) ;
		int returnval = fc.showOpenDialog( this ) ;	
        
        if ( returnval == JFileChooser.APPROVE_OPTION)
        	fichier = fc.getSelectedFile() ;
        
        if( must_exist && ( fichier != null) )
        {
        	try {
        		JTextArea document = new JTextArea();
        		document.setBackground(Color.ORANGE);
        		document.read(new FileReader( fichier), null);
        		JFrame fichier_frame = new JFrame( fichier.getName() );
        		fichier_frame.add(new JScrollPane(document));
        		fichier_frame.setSize(400, 300);
        		fichier_frame.setVisible(true);
        		}   
        	catch (IOException ex) 
        	{
        		JOptionPane.showMessageDialog(null, 
        			"ATTENTION, le fichier " + fichier.getName() + " n'existe pas", 
        			"Alerte", 
        			JOptionPane.ERROR_MESSAGE) ;
        			fichier = null ;
        	}
        }
        
		return fichier ;
	} 
				 

	/**
	 * 
	 * @author Nora
	 * Cette classe ouvre la fenêtre de dialogue permettant de saisir un nouveau plan de vol
	 */
	public class ActionSaisiePlanDeVol implements ActionListener {

		public ActionSaisiePlanDeVol(InterfaceModele modele) {
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			SaisiePlanDeVol nouvelle_fenetre = new SaisiePlanDeVol( Fenetre.this.modele) ;
			
		}
		
	}
	
		//// listener
	/**
	 * 
	 * @author 	Nora
	 * Cette classe permet de quitter l'application.
	 */
	class ActionQuitter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Fenetre.this.quitter();
		}
	}
	
	/**
	 * 
	 * @author Nora
	 * Cette classe permet de charger un fichier d'aérodromes
	 */
	public class ActionChargerAd implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt ad");
			
			File ficname = Fenetre.this.demander_nom_fichier( true ) ;
			System.err.println( ficname ) ;

			if( ficname != null)
			{	
				Fenetre.this.modele.charger_aerodromes( ficname ) ;
				System.out.println("fin chargt ad");						
			}
		}
	}
	
	
	/**
	 * 
	 * @author Nora
	 * Cette classe permet de charger un fichier de balise
	 */
	public class ActionChargerBalises implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt balises");
			
			File ficname = Fenetre.this.demander_nom_fichier( true ) ;
			System.err.println( ficname ) ;
			
			if( ficname != null)
			{
				Fenetre.this.modele.charger_balises( ficname ) ;
				System.out.println("fin chargt balises");
			}			
		}
	}
	
	/**
	 * 
	 * @author Nora
	 * Cette classe permet de charger un fichier de plan de vol
	 */
	public class ActionChargerPlanDeVol implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("chargt Plan de vol");
			File ficname = Fenetre.this.demander_nom_fichier( true ) ;
			System.err.println( ficname ) ;		
			
			Fenetre.this.modele.charger_avions( ficname ) ;
			
			System.out.println("fin chargt Plan de vol");
						
		}
	}
	
	/**
	 * 
	 * @author Nora
	 * Cette classe permet d'effacer le prompt de DBmax lorsque l'on clique dessus
	 */
	protected class Eff_DBMAX implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			( (JTextField) e.getSource() ).setText( "" );
		}

		@Override
		public void mouseEntered(MouseEvent e) {
				
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
		
	}
	
	/**
	 * 
	 * @author Nora
	 * Cette classe permet de récuperer DBmax et de l'envoyer au modéle.
	 * 
	 */	
	protected class ActionDistanceDbmax implements ActionListener
	{
		public void actionPerformed(ActionEvent	arg0)
		{	
			System.out.println("DBmax");
			Fenetre.this.modele.setDistanceMax( Double.parseDouble( ( (JTextField) arg0.getSource() ).getText() ));
		}
	}
	
	
		
	
	/**
	 * 
	 * @author Nora
	 * Cette classe permet de zoomer sur les différents panels d'affichage. 
	 * 
	 */
	public class ActionZoomP implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("zoom");
			
			Fenetre.this.pan_principal.zoomer( Fenetre.this.niveauDeZoom ) ;
			Fenetre.this.rafraichir();
			}
	}
	
	
	/**
	 * 
	 * @author Nora
	 * Cette classe permet de dé-zoomer sur les différents panels d'affichage. 
	 * 
	 */
	public class ActionZoomM implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("zoom");
			
			Fenetre.this.pan_principal.zoomer( 1 / Fenetre.this.niveauDeZoom ) ;
			Fenetre.this.rafraichir();
			}
	}
	
	private class ActionExecuter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Fenetre.this.execution_automatique = true ;
		}
		
	}
	
	private class ActionIterer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Fenetre.this.modele.iterer() ;
		}
		
	}
	
	private class ActionStop implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Fenetre.this.execution_automatique = false ;
		}
		
	}
	
	
	private class IterationAutomatique extends TimerTask {

		@Override
		public void run() {
			
			if ( Fenetre.this.execution_automatique )
			{
				//System.out.println( "Execution du timer" ) ;
				Fenetre.this.modele.iterer() ;
			}
			
		}
		
	}
	
	
	private class ActionRecommencer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Fenetre.this.modele.reinitialiser() ;
		}
		
	}
	
	private class ActionCalculTrajectoires implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Fenetre.this.modele.calculer_trajectoires() ;
		}
		
	}
	
	
	
	
	
	private class ActionSauvegarderTrajectoire implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			File ficname = Fenetre.this.demander_nom_fichier( false) ;
			Fenetre.this.modele.exporter_trajectoires( ficname ) ;
			
		}
		
	}
	
	
	
	private class ActionSaisieHeureCourante implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			Fenetre.this.modele.setHeureCourante( ( (SpinnerDateModel) arg0.getSource() ).getDate() ) ;
		}
		
	}
	
	
	
	
	/**
	 * rafraichit les différents panels spécifiques aux objets
	 */
	public boolean rafraichir() {
		
		this.revalidate();
		this.repaint();
		
		this.pan_principal.rafraichir() ; 
		this.pan_principal.revalidate();
		this.pan_principal.repaint();
		
		return true ;
	}
	
	public static void main(String[] args) {
		
		Simulation modele = new Simulation() ;
		
		Fenetre fen_1 = new Fenetre( modele );
		//Fenetre fen_2 = new Fenetre( modele );
		
		modele.setHeureCourante( new GregorianCalendar( 2014 , 02 , 15 , 7 , 59 ).getTime() );
		modele.setDistanceMax( 500 ) ;
		
		//modele.charger_balises( new File("./fichiers/balises_fr.txt" ) ) ;
		//modele.charger_aerodromes( new File( "./fichiers/aerodromes_fr.txt" ) );
		//modele.charger_avions( new File("fichiers/avions.txt")) ;
		
		//modele.calculer_trajectoires() ;

	}
		}// end class
