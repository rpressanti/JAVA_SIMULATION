package SIMULATION.View;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

import SIMULATION.Datatypes.Repere;

/**
 * @author pressari
 *
 */
public class ActionSaisiePlanDeVol implements ActionListener
{
	
	private InterfaceModele modele ;
	
	private String indicatif ;
	private int flight_level ;
	private Date heure_depart ;
	@SuppressWarnings("unused")
	private Date heure_arrivee ;
	private Repere depart ;
	private Repere arrivee ;
	private Double vitesse ;
	
	public ActionSaisiePlanDeVol( InterfaceModele modele ) {
		
		this.modele = modele ;
		
		this.indicatif = "";
		this.flight_level = 0  ;
		this.heure_depart = null ;
		this.heure_arrivee = null ;
		this.depart = null ;
		this.arrivee = null ;
		this.vitesse = 0.0 ;
		
	}
	
	
	public void setModele( InterfaceModele modele ) {
		this.modele = modele ;
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("saisie Plan de vol");
		//creation fenetre
		
		JFrame jf_pdv= new JFrame("Saisie plan de vol");
		jf_pdv.setSize(320, 150);
		
		// TODO RM
		//JPanel pannel = new JPanel();
		
		Container pan_pdv = jf_pdv.getContentPane();
		pan_pdv.setLayout( new GridLayout( 5 , 4 ) ) ;
		
		JLabel indicatif_prompt = new JLabel( "Indicatif:" ) ;
		pan_pdv.add( indicatif_prompt ) ;
		JTextField indicatif_input =new JTextField("Indicatif");
		pan_pdv.add( indicatif_input ) ;
		
		// TODO LISTENER 
		//indicatif.addItemListener(new ActionSaisieIndicatif());
		
		
		JLabel fl_prompt = new JLabel( "Niveau de vol:" ) ;
		pan_pdv.add( fl_prompt ) ;
		// TODO LISTENER
		JTextField niveau_vol_input =new JTextField("niveau de vol");
		pan_pdv.add( niveau_vol_input ) ;
		
		
		
		
		JLabel heure_depart_prompt = new JLabel( "Heure de d�part:" ) ;
		pan_pdv.add( heure_depart_prompt ) ;
		
		Calendar now = Calendar.getInstance();
		Calendar earliestDate_dep = new GregorianCalendar( 2012 , 1 ,1) ;
		Calendar latestDate_dep = new GregorianCalendar( 2014 , 12 ,31 ) ;
		SpinnerModel model_dep = new SpinnerDateModel( now.getTime(), 
		earliestDate_dep.getTime(), latestDate_dep.getTime(), Calendar.WEEK_OF_YEAR );
		JSpinner spinner_dep = new JSpinner( model_dep );
		pan_pdv.add( spinner_dep ) ;
		// TODO LISTENER SAISIE DATE
		//TODO RM  FAKE
		//JTextField fake_dep = new JTextField( "fake_dep" ) ;
		//pan_pdv.add( fake_dep ) ;
		
		JLabel heure_arrivee_prompt = new JLabel( "Heure d'arriv�e:" ) ;
		pan_pdv.add( heure_arrivee_prompt ) ;
		
		Calendar earliestDate_arr = new GregorianCalendar( 2012 , 1 ,1) ;
		Calendar latestDate_arr = new GregorianCalendar( 2014 , 12 ,31 ) ;
		SpinnerModel model_arr = new SpinnerDateModel( now.getTime(), 
		earliestDate_arr.getTime(), latestDate_arr.getTime(), Calendar.WEEK_OF_YEAR );
		JSpinner spinner = new JSpinner( model_arr );
		pan_pdv.add( spinner ) ;						
		// TODO LISTENER SAISIE DATE 
		// TODO RM FAKE
		//JTextField fake_arr = new JTextField( "fake_arr" ) ;
		//pan_pdv.add( fake_arr ) ;
		
		
		JLabel depart_prompt = new JLabel( "D�part:" ) ;
		pan_pdv.add( depart_prompt ) ;
		
		ButtonGroup chx_dep = new ButtonGroup() ;
		JRadioButton chx_dep_ad = new JRadioButton( "Aerodrome" ) ;
		chx_dep.add( chx_dep_ad ) ;
		pan_pdv.add( chx_dep_ad ) ;
		JRadioButton chx_dep_balises = new JRadioButton( "Balise" ) ;
		chx_dep.add( chx_dep_balises ) ;
		pan_pdv.add( chx_dep_balises ) ;
		JComboBox<Repere> combo_dep = new JComboBox<Repere>() ;
		pan_pdv.add( combo_dep ) ;
		
		// TODO ADD LISTENER
		
		JLabel arrivee_prompt = new JLabel( "Arriv�e:" ) ;
		pan_pdv.add( arrivee_prompt ) ;
		
		ButtonGroup chx_arr = new ButtonGroup() ;
		JRadioButton chx_arr_ad = new JRadioButton( "Aerodrome" ) ;
		chx_arr.add( chx_arr_ad ) ;
		pan_pdv.add( chx_arr_ad ) ;
		JRadioButton chx_arr_balises = new JRadioButton( "Balise" ) ;
		chx_dep.add( chx_arr_balises ) ;
		pan_pdv.add( chx_arr_balises ) ;
		JComboBox<Repere> combo_arr = new JComboBox<Repere>() ;
		pan_pdv.add( combo_arr ) ;
		
		
		
		
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
		
		
		
		
		this.modele.creer_avion( this.indicatif , this.depart , this.arrivee , this.flight_level , this.vitesse, this.heure_depart ) ;
		
		
		
		
		
		
		System.out.println("fin saisie Plan de vol");
					
	}
}