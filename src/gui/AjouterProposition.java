/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;
import services.ProduitsService;
import services.ServiceProposition;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.CN.FACE_SYSTEM;
import static com.codename1.ui.CN.SIZE_SMALL;
import static com.codename1.ui.CN.STYLE_BOLD;
import static com.codename1.ui.CN.STYLE_PLAIN;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import entites.Locaux;
import entites.Programmes;
import entites.Transporteur;
import entites.proposition;
import gui.BaseForm;
import java.text.SimpleDateFormat;
import projet.models.Livreur;
import services.ServiceLivreurs;
import services.ServiceProgrammes;
import services.ServiceTransporteurs;
import utils.mailing;
import utils.mailingEvent;
import utils.mailingLivreur;
import utils.mailingReclamation;
/**
 *
 * @author malek
 */
public class AjouterProposition extends BaseForm{
    Form current ; 
    
    
    
public AjouterProposition(Resources res){
           updateNetworkThreadCount(2);

        res = UIManager.initFirstTheme("/theme");
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);
        
        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
      
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
    setLayout(BoxLayout.y());
         Container c1 = new Container(BoxLayout.x());
        c1.revalidate();
        Label rs = new Label();
        rs.getAllStyles().setPadding(0, 0, 53, 53);
        rs.setIcon(res.getImage("Fichier 2R.png")) ;
        
        c1.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
        c1.getAllStyles().setBackgroundGradientStartColor(ColorUtil.rgb(77, 180, 125),true);
        c1.getAllStyles().setBackgroundGradientEndColor(ColorUtil.rgb(77, 180, 125),true);
        rs.getAllStyles().setAlignment(CENTER);
                c1.add(rs);
add (c1);
    setTransporteur(res);
    setEvenement(res);
           setProgrammes(res);
setLivreur(res);
     
    }
public void setTransporteur(Resources res){
      
        Container payement = new Container(BoxLayout.y());
        payement.setUIID("ContainerP");
      
        Label title = new Label("Transporteur");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(16);
                                payement.add(title);

    Label ds = new Label("Ici, vous pouvez nous envoyer une demande pour travailler comme un transporteur");
        ds.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM,STYLE_PLAIN ,SIZE_SMALL ));
        ds.getAllStyles().setAlignment(CENTER); 
                ds.getAllStyles().setMarginBottom(13);
                                                payement.add(ds);

    
    
    TextField type = new TextField("","");
    type.setUIID("textFieldblack");
    addStringValue("Type",type,payement);
    
    
    
    TextField numero = new TextField("","");
    numero.setUIID("textFieldblack");
    addStringValue("Numero",numero,payement); 
    

    TextField nom = new TextField("","");
    nom.setUIID("textFieldblack");
    addStringValue("Nom",nom,payement);         
       
    TextField adresse = new TextField("","");
    adresse.setUIID("textFieldblack");
    addStringValue("Adresse",adresse,payement); 
    
    TextField mail = new TextField("","");
    mail.setUIID("textFieldblack");
    addStringValue("E-mail",mail,payement); 
    
       Button submit = new Button("Submit");

    
     submit.addActionListener((e)->{
       try{
       
       if(adresse.getText() == "" ||nom.getText()== ""){
         Dialog.show("veuillez vérifier les donnees ","","annuler","ok");
       
       } else {
           InfiniteProgress ip = new InfiniteProgress();
           final Dialog  iDialog = ip.showInfiniteBlocking();
           Transporteur of = new Transporteur(String.valueOf(type.getText().toString()),Integer.parseInt(numero.getText()),String.valueOf(nom.getText().toString()),String.valueOf(adresse.getText().toString()),String.valueOf(mail.getText().toString()));
           ServiceTransporteurs.getInstance().ajouttransporteur(of);
           iDialog.dispose();
           refreshTheme();
           mailingReclamation.sendEmail("mohamedamine.tlili.1@esprit.tn", "Ajout Transporteur", "Vous avez ajouté un transporteur avec succés");

       }
       
       }catch(Exception ex){
       
       ex.printStackTrace();
       }
     
     });
      payement.addAll(submit);
       add(payement);
}

public void setEvenement(Resources res){
  Container c1 = new Container(BoxLayout.x());
  c1.getAllStyles().setMarginTop(22);
        c1.revalidate();
 
add (c1);
        Container payement = new Container(BoxLayout.y());
        payement.setUIID("ContainerP");
      
        Label title = new Label("Evenement");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(16);
                                payement.add(title);

    Label ds = new Label("Ici, vous pouvez nous proposer un evenement.");
        ds.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM,STYLE_PLAIN ,SIZE_SMALL ));
        ds.getAllStyles().setAlignment(CENTER); 
                ds.getAllStyles().setMarginBottom(13);
                payement.add(ds);

    
    TextField nom = new TextField("","");
    nom.setUIID("textFieldblack");
    addStringValue("Nom",nom,payement);
    
    
    Picker debut = new Picker();
        debut.setUIID("TextFieldBlack");
        addStringValue("date", debut,payement);
    
    TextField nombre_place = new TextField("","");
    nombre_place.setUIID("textFieldblack");
    addStringValue("Nombre de places",nombre_place,payement); 
    

    TextField mail = new TextField("","");
    mail.setUIID("textFieldblack");
    addStringValue("E-mail",mail,payement);   
    
       Button submit = new Button("Submit");

    
     submit.addActionListener((e)->{
         
       try{
           
       if(nom.getText() == "" ||mail.getText()== ""){
         Dialog.show("veuillez vérifier les donnees ","","annuler","ok");
       } else {
           InfiniteProgress ip = new InfiniteProgress();
           final Dialog  iDialog = ip.showInfiniteBlocking();
           proposition of = new proposition(String.valueOf(nom.getText().toString()),debut.getDate(),Integer.parseInt(nombre_place.getText()),String.valueOf(mail.getText().toString()));
            ServiceProposition.getInstance().ajoutproposition(of);
           iDialog.dispose();
           refreshTheme();
                     mailingEvent.sendEmail("chayma.hamrouni@esprit.tn", "Ajout Proposition", "Votre proposition est bien reçue Merci.");

       }
          }catch(Exception ex){
       
       ex.printStackTrace();
       }
     
     });
      payement.addAll(submit);
       add(payement);
}
       private void addStringValue(String s, Component v,Container c) {
        c.add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        c.add(createLineSeparator(0xeeeeee));
    }


   public void setProgrammes (Resources res){
  Container c1 = new Container(BoxLayout.x());
  c1.getAllStyles().setMarginTop(22);
        c1.revalidate();
 
add (c1);
        Container payement = new Container(BoxLayout.y());
        payement.setUIID("ContainerP");
      
        Label title = new Label("Programme");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(16);
                                payement.add(title);

    Label ds = new Label("Ici, vous pouvez nous envoyer une proposition d'un programme.");
        ds.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM,STYLE_PLAIN ,SIZE_SMALL ));
        ds.getAllStyles().setAlignment(CENTER); 
                ds.getAllStyles().setMarginBottom(13);
                payement.add(ds);
                
TextField nom = new TextField("","");
nom.setUIID("TextFieldBlack");
addStringValue("nom",nom,payement);
        

TextField date = new TextField("","");
date.setUIID("TextFieldBlack");
addStringValue("date",date,payement);


TextField duree = new TextField("","");
duree.setUIID("TextFieldBlack");
addStringValue("duree",duree,payement);



TextField details = new TextField("","");
details.setUIID("TextFieldBlack");
addStringValue("details",details,payement);


TextField locale = new TextField("","");
locale.setUIID("TextFieldBlack");
addStringValue("locale",locale,payement);



 Button btnAjouter = new Button("Ajouter");
 
 //onclick button event
 
 btnAjouter.addActionListener((e)->{
 try {
 if (nom.getText()==""||date.getText()==""||duree.getText()==""||details.getText()==""){
 Dialog.show("Veuillez verifier les données","","Annuler","OK");
 }
 else {
   InfiniteProgress ip = new InfiniteProgress();;  //loading after insert data
   final Dialog iDialog=ip.showInfiniteBlocking();
   com.codename1.l10n.SimpleDateFormat format = new com.codename1.l10n.SimpleDateFormat("yyyy-MM-dd");
   Locaux   loc = new Locaux();
   loc.setNom(locale.getText());
Programmes l = new Programmes(String.valueOf(nom.getText()).toString(),String.valueOf(date.getText()).toString(),Integer.parseInt(duree.getText()),String.valueOf(details.getText()).toString(),loc);
  
     System.out.println("data programmes =="+l); 
     
     //appelle methode ajouterReclamation
     ServiceProgrammes.getInstance().ajoutProgrammes(l);
     iDialog.dispose();
         new ListProgrammesForm(res).show();
     
     
     refreshTheme();//actualisation
          mailing.sendEmail("hassene.afif@esprit.tn", "Ajout Programme", "Vous avez ajouté un programme avec succés");
      new ListProgrammesForm(res).show();
 }     
 }catch(Exception ex) {
     ex.printStackTrace();
 }    
 });
     payement.add(btnAjouter);

           add(payement);

}
    
   
   
   
   public void setLivreur (Resources res){
  Container c1 = new Container(BoxLayout.x());
  c1.getAllStyles().setMarginTop(22);
        c1.revalidate();
 
add (c1);
        Container payement = new Container(BoxLayout.y());
        payement.setUIID("ContainerP");
      
        Label title = new Label("Livreurs");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(16);
                                payement.add(title);

    Label ds = new Label("Ici, vous pouvez nous envoyer une demande pour travailler comme un livreur");
        ds.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM,STYLE_PLAIN ,SIZE_SMALL ));
        ds.getAllStyles().setAlignment(CENTER); 
                ds.getAllStyles().setMarginBottom(13);
                payement.add(ds);
                
TextField telephone = new TextField("","");
telephone.setUIID("TextFieldBlack");
addStringValue("Telephone",telephone,payement);
        

TextField adresse = new TextField("","");
adresse.setUIID("TextFieldBlack");
addStringValue("Adresse",adresse,payement);


TextField mail = new TextField("","");
mail.setUIID("TextFieldBlack");
addStringValue("E-mail",mail,payement);



TextField nom = new TextField("","");
nom.setUIID("TextFieldBlack");
addStringValue("Nom",nom,payement);




 Button btnAjouter = new Button("Ajouter");
 
 //onclick button event
 
 btnAjouter.addActionListener((e)->{
 try {
 if (telephone.getText()==""||adresse.getText()==""||mail.getText()==""||nom.getText()==""){
 Dialog.show("Veuillez verifier les données","","Annuler","OK");
 }
 else {
   InfiniteProgress ip = new InfiniteProgress();
           final Dialog  iDialog = ip.showInfiniteBlocking();
           Livreur of = new Livreur(Integer.parseInt(telephone.getText()),String.valueOf(adresse.getText().toString()),String.valueOf(mail.getText().toString()),String.valueOf(nom.getText().toString()));
           ServiceLivreurs.getInstance().ajoutlivreurs(of);
           iDialog.dispose();
           refreshTheme();
           mailingLivreur.sendEmail("yassine.essghaier@esprit.tn", "Ajout Livreur", "Vous avez ajouté un livreur avec succés");

 }     
 }catch(Exception ex) {
     ex.printStackTrace();
 }    
 });
     payement.add(btnAjouter);

           add(payement);

}
    
    public void bindButtonSelection(Button btn , Label l)
    {
       btn.addActionListener(e -> {
           if (btn.isSelected()) {
            
           updateArrowPosition(btn,l);
           }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT,btn.getX() + btn.getWidth() /2 -l.getWidth() / 2);
    
        l.getParent().repaint();
    }


   
}
