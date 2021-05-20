/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entites.Programmes;
import services.ServiceProgrammes;


/**
 *
 * @author Hass
 */
public class AjoutProgrammesForm extends BaseForm {
    
Form current;
public AjoutProgrammesForm (Resources res){
super ("Newsfeed",BoxLayout.y());
Toolbar tb = new Toolbar(true);
current = this;
setToolbar(tb);
getTitleArea().setUIID("Container");
setTitle("Ajout Programmes");
getContentPane().setScrollVisible(false);



TextField nom = new TextField("","Entrez nom!");
nom.setUIID("TextFieldBlack");
addStringValue("nom",nom);
        

TextField date = new TextField("","Entrez date!");
date.setUIID("TextFieldBlack");
addStringValue("date",date);


TextField duree = new TextField("","Entrez duree!");
duree.setUIID("TextFieldBlack");
addStringValue("duree",duree);



TextField details = new TextField("","Entrez details!");
details.setUIID("TextFieldBlack");
addStringValue("details",details);




 Button btnAjouter = new Button("Ajouter");
 addStringValue("", btnAjouter);
 
 //onclick button event
 
 btnAjouter.addActionListener((e)->{
 try {
 if (nom.getText()==""||date.getText()==""||duree.getText()==""||details.getText()==""){
 Dialog.show("Veuillez verifier les données","","Annuler","OK");
 }
 else {
   InfiniteProgress ip = new InfiniteProgress();;  //loading after insert data
   final Dialog iDialog=ip.showInfiniteBlocking();
   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
   Programmes l = new Programmes(String.valueOf(nom.getText()).toString(),String.valueOf(date.getText()).toString(),Integer.parseInt(duree.getText()),String.valueOf(details.getText()).toString());
 
     System.out.println("data programmes =="+l); 
     
     //appelle methode ajouterReclamation
     ServiceProgrammes.getInstance().ajoutProgrammes(l);
     iDialog.dispose();
         new ListProgrammesForm(res).show();
     
     
     refreshTheme();//actualisation
 }     
 }catch(Exception ex) {
     ex.printStackTrace();
 }    
 });
    
    
}

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
      
    }
        

    
}
