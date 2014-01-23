package SIMULATION.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

 
@SuppressWarnings("serial")
public class Fenetre extends JFrame
{	
	//attributs//
	private JMenuBar jmenubar;
	private JMenu jfichier,jcharger;
	private JMenuItem jquitter;
	private JMenu jpdv;
	
	private JMenuItem jaero,jbal;
	private JMenuItem jcharger_pdv,jsaisie_pdv,aff_traj,jsave_traj;
	
	private JTextField jtf_dbmax;
	private JButton jb_execution,jb_iterer,jb_stop,jb_recommencer,jb_quit;

	
	//constructeurs
	public Fenetre()
	{
		JFrame jf= new JFrame("simulation");
		// conteneur pan
		Container pan = jf.getContentPane();
		pan.setLayout( new BorderLayout() );

		// panel		
		JPanel pan_button = new JPanel();
		pan_button.setBackground(Color.ORANGE);   
		pan_button.setLayout(new GridLayout(2,3));
		pan.add(pan_button,"North");
		
		//JPanel pan_principal = new JPanel();
		JLayeredPane pan_principal = new JLayeredPane();
		pan_principal.setBackground(Color.BLUE);  
		pan.add(pan_principal , BorderLayout.CENTER);
	
		//JLabel test1 =new JLabel("test1");
	 	//pan_principal.add(test1);
	//	JScrollPane  j_scroll=new JScrollPane();
	//	pan_principal.add(j_scroll, BorderLayout.SOUTH);
		
		
		JPanel pan_secondaire= new JPanel();
		pan_secondaire.setBackground(Color.GREEN);  
		pan.add(pan_secondaire,BorderLayout.SOUTH );
	 	//JLabel test =new JLabel("test0");
	 	//pan_secondaire.add(test);
		jb_quit=new JButton("Quitter");
		pan_secondaire.add(jb_quit,BorderLayout.EAST);
		jb_quit.addActionListener(new ActionQuitter());
		
		
	  // visualisation de la fenetre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	jf.setSize(500,400);
	  //	jf.pack();
	  	jf.setVisible(true);           
	  		
	     
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
		//jaff_traj.addActionListener(new ActionSauvegarderTrajectoire());
		
		//boutons
		jb_execution=new JButton("Execution Automatique");
		pan_button.add(jb_execution);
		//jb_execution.addActionListener(new ActionExecuter());
		
		jb_iterer=new JButton("Pas à Pas");
		pan_button.add(jb_iterer);
		//jb_iterer.addActionListener(new ActionIterer());
		
		jb_stop=new JButton("Pause");
		pan_button.add(jb_stop);
		//jb_stop.addActionListener(new ActionStop());
		
				
		JLabel jl_dbmax=new JLabel("Distance Maximale ");
		pan_button.add(jl_dbmax);
				
		
		jtf_dbmax=new JTextField("Distance max");
		pan_button.add(jtf_dbmax);
		//jtf_dbmax.addItemListener(new ActionDistanceDbmax())
				
		jb_recommencer=new JButton("Recommencer");
		pan_button.add(jb_recommencer);
		//	jb.addActionListener(new ActionRecommencer());
		
	 } // fin constructeur       fenetre 		
		
		 
	// methode
	public void quitter()
	{
		 System.out.println("Fin ...");
		System.exit(0);
	}
	
	// TODO CODE
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
			// TODO UNCOMMENT
			//this.modele.chargerPlanDeVol( ficname ) ;
			
			System.out.println("fin chargt Plan de vol");
						
		}
	}
	
	
}// end class
	
	
/*	
	
	public class ActionPerso implements ItemListener
	{
		public void itemStateChanged(ItemEvent evt)
		{
			String s=(String)jc.getSelectedItem();
			Iterator <Personne> i=a.iterator();
			boolean b=false;
			while(i.hasNext() && !b)
			{
				Personne p=i.next();
				if (s==p.getNom())
				{
					jta.setText(p.toString());
					b=true;
				}
			}
		}
	}
	*/
	

