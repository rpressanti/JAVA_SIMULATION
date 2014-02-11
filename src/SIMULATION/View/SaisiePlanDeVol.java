package SIMULATION.View;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

import SIMULATION.Datatypes.Aerodrome;
import SIMULATION.Datatypes.Balise;
import SIMULATION.Datatypes.Repere;
/*
import SIMULATION.View.ActionSaisiePlanDeVol.ActionAnnuler;
import SIMULATION.View.ActionSaisiePlanDeVol.SelectArrAd;
import SIMULATION.View.ActionSaisiePlanDeVol.SelectArrBalises;
import SIMULATION.View.ActionSaisiePlanDeVol.SelectDepAd;
import SIMULATION.View.ActionSaisiePlanDeVol.SelectDepBalises;
*/
public class SaisiePlanDeVol extends JFrame implements ActionListener {

	
	
	
	
	
	
	
	
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private InterfaceModele modele ;
	
	private String indicatif ;
	private int flight_level ;
	private Date heure_depart ;
	@SuppressWarnings("unused")
	private Date heure_arrivee ;
	private Repere depart ;
	private Repere arrivee ;
	private Double vitesse ;
	
	private JFrame jf_pdv ;
	private Container pan_pdv ;
	private JComboBox<Repere> combo_dep ;
	private JComboBox<Repere> combo_arr ;
	
	public SaisiePlanDeVol( InterfaceModele modele ) {
		
		this.modele = modele ;
		
		this.indicatif = "";
		this.flight_level = 0  ;
		this.heure_depart = null ;
		this.heure_arrivee = null ;
		this.depart = null ;
		this.arrivee = null ;
		this.vitesse = 0.0 ;
		
		this.jf_pdv = new JFrame("Saisie plan de vol");
		this.jf_pdv.setSize(320, 150);
		
		
		this.pan_pdv = jf_pdv.getContentPane();
		this.pan_pdv.setLayout( new GridLayout( 5 , 4 ) ) ;
		
		this.combo_dep = new JComboBox<Repere>() ;
		this.combo_arr = new JComboBox<Repere>() ;
		
		System.out.println("saisie Plan de vol");
		//creation fenetre
		
		
		
		JLabel indicatif_prompt = new JLabel( "Indicatif:" ) ;
		this.pan_pdv.add( indicatif_prompt ) ;
		JTextField indicatif_input =new JTextField("Indicatif");
		this.pan_pdv.add( indicatif_input ) ;
		
		// TODO LISTENER 
		//indicatif.addItemListener(new ActionSaisieIndicatif());
		
		
		JLabel fl_prompt = new JLabel( "Niveau de vol:" ) ;
		this.pan_pdv.add( fl_prompt ) ;
		// TODO LISTENER
		JTextField niveau_vol_input =new JTextField("niveau de vol");
		this.pan_pdv.add( niveau_vol_input ) ;
		
		
		
		
		JLabel heure_depart_prompt = new JLabel( "Heure de d�part:" ) ;
		this.pan_pdv.add( heure_depart_prompt ) ;
		
		Calendar now = Calendar.getInstance();
		Calendar earliestDate_dep = new GregorianCalendar( 2012 , 1 ,1) ;
		Calendar latestDate_dep = new GregorianCalendar( 2014 , 12 ,31 ) ;
		SpinnerModel model_dep = new SpinnerDateModel( now.getTime(), 
		earliestDate_dep.getTime(), latestDate_dep.getTime(), Calendar.WEEK_OF_YEAR );
		JSpinner spinner_dep = new JSpinner( model_dep );
		this.pan_pdv.add( spinner_dep ) ;
		
		JLabel heure_arrivee_prompt = new JLabel( "Heure d'arriv�e:" ) ;
		this.pan_pdv.add( heure_arrivee_prompt ) ;
		
		Calendar earliestDate_arr = new GregorianCalendar( 2012 , 1 ,1) ;
		Calendar latestDate_arr = new GregorianCalendar( 2014 , 12 ,31 ) ;
		SpinnerModel model_arr = new SpinnerDateModel( now.getTime(), 
		earliestDate_arr.getTime(), latestDate_arr.getTime(), Calendar.WEEK_OF_YEAR );
		JSpinner spinner = new JSpinner( model_arr );
		this.pan_pdv.add( spinner ) ;						
		// TODO LISTENER SAISIE DATE 
		
		
		
		JLabel depart_prompt = new JLabel( "D�part:" ) ;
		this.pan_pdv.add( depart_prompt ) ;
		
		ButtonGroup chx_dep = new ButtonGroup() ;
		
		JRadioButton chx_dep_ad = new JRadioButton( "Aerodrome" ) ;
		chx_dep.add( chx_dep_ad ) ;
		this.pan_pdv.add( chx_dep_ad ) ;
		chx_dep_ad.addActionListener( new SelectDepAd());
		
		JRadioButton chx_dep_balises = new JRadioButton( "Balise" ) ;
		chx_dep.add( chx_dep_balises ) ;
		this.pan_pdv.add( chx_dep_balises ) ;
		chx_dep_balises.addActionListener( new SelectDepBalises() ); ;
		
		this.pan_pdv.add( this.combo_dep ) ;
		
		// TODO ADD LISTENER
		
		JLabel arrivee_prompt = new JLabel( "Arriv�e:" ) ;
		this.pan_pdv.add( arrivee_prompt ) ;
		
		ButtonGroup chx_arr = new ButtonGroup() ;
		JRadioButton chx_arr_ad = new JRadioButton( "Aerodrome" ) ;
		chx_arr.add( chx_arr_ad ) ;
		this.pan_pdv.add( chx_arr_ad ) ;
		chx_arr_ad.addActionListener( new SelectArrAd() );
		
		JRadioButton chx_arr_balises = new JRadioButton( "Balise" ) ;
		chx_dep.add( chx_arr_balises ) ;
		this.pan_pdv.add( chx_arr_balises ) ;
		chx_arr_balises.addActionListener( new SelectArrBalises() ) ;
		
		this.pan_pdv.add( combo_arr ) ;	
		
		
		
		JLabel vitesse_prompt = new JLabel( "Vitesse:" ) ;
		this.pan_pdv.add( vitesse_prompt ) ;
		// TODO LISTENER
		JTextField vitesse_input =new JTextField("vitesse");
		this.pan_pdv.add( vitesse_input ) ;
		
		JButton annuler = new JButton( "Annuler" ) ;
		this.pan_pdv.add( annuler ) ;
		annuler.addActionListener( new ActionAnnuler() ) ;
		
		JButton valider = new JButton( "Valider" ) ;
		this.pan_pdv.add( valider ) ;
		valider.addActionListener( this ) ;
		
	
		this.jf_pdv.pack() ;		
		this.jf_pdv.setVisible(true); 
	
	
	}
	
	
	
	protected class SelectDepAd implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SaisiePlanDeVol.this.combo_dep.removeAllItems() ;
			
			for( Aerodrome aerodrome : SaisiePlanDeVol.this.modele.getAerodromes().values() )
				SaisiePlanDeVol.this.combo_dep.addItem( aerodrome );
		}
		
	}
	
	protected class SelectDepBalises implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SaisiePlanDeVol.this.combo_dep.removeAllItems(); ;
			
			for( Balise balise : SaisiePlanDeVol.this.modele.getBalises().values() )
				SaisiePlanDeVol.this.combo_dep.addItem( balise );
		}
		
	}
	
	
	protected class SelectArrAd implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SaisiePlanDeVol.this.combo_arr.removeAllItems() ;
			
			for( Aerodrome aerodrome : SaisiePlanDeVol.this.modele.getAerodromes().values() )
				SaisiePlanDeVol.this.combo_arr.addItem( aerodrome );
		}
		
	}
	
	protected class SelectArrBalises implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SaisiePlanDeVol.this.combo_arr.removeAllItems() ;
			
			for( Balise balise : SaisiePlanDeVol.this.modele.getBalises().values() )
				SaisiePlanDeVol.this.combo_arr.addItem( balise );
		}
		
	}
	
	
	protected class ActionAnnuler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println( "Fermeture" ) ;
			SaisiePlanDeVol.this.jf_pdv.dispose() ; 
			
		}
		
		
	}


	@Override
	// DONE
	// ACTION POUR CREER AVION
	public void actionPerformed(ActionEvent e) {
		
		this.modele.creer_avion( this.indicatif, this.depart, this.arrivee, this.flight_level, this.vitesse, this.heure_depart ) ;
		
		System.out.println( "Avion créé" + this.modele.getAvions().size() ) ;
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
