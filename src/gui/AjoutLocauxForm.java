/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.InfiniteProgress;
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
import entites.Locaux;
import services.ServiceLocaux;

/**
 *
 * @author Hass
 */
public class AjoutLocauxForm extends BaseForm {
    
Form current;
public AjoutLocauxForm (Resources res){
super ("Newsfeed",BoxLayout.y());
Toolbar tb = new Toolbar(true);
current = this;
setToolbar(tb);
getTitleArea().setUIID("Container");
setTitle("Ajout Locaux");
getContentPane().setScrollVisible(false);



TextField nom = new TextField("","");
nom.setUIID("TextFieldBlack");
addStringValue("Nom",nom);
        

TextField adresse = new TextField("","");
adresse.setUIID("TextFieldBlack");
addStringValue("Adresse",adresse);


TextField description = new TextField("","");
description.setUIID("TextFieldBlack");
addStringValue("Description",description);



TextField imageName = new TextField("","");
imageName.setUIID("TextFieldBlack");
addStringValue("Image",imageName);



TextField note = new TextField("","");
note.setUIID("TextFieldBlack");
addStringValue("Note",note);
  

TextField googleMap = new TextField("","");
googleMap.setUIID("TextFieldBlack");
addStringValue("Lien Maps",googleMap);      
        
        
 Button btnAjouter = new Button("Ajouter");
 addStringValue("", btnAjouter);
 
 //onclick button event
 
 btnAjouter.addActionListener((e)->{
 try {
 if (nom.getText()==""||adresse.getText()==""||description.getText()==""||imageName.getText()==""||note.getText()==""||googleMap.getText()==""){
 Dialog.show("Veuillez verifier les données","","Annuler","OK");
 }
 else {
   InfiniteProgress ip = new InfiniteProgress();;  //loading after insert data
   final Dialog iDialog=ip.showInfiniteBlocking();
  // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
   Locaux l = new Locaux(String.valueOf(nom.getText()).toString(),String.valueOf(adresse.getText()).toString(),String.valueOf(description.getText()).toString(),String.valueOf(imageName.getText()).toString(),Integer.parseInt(note.getText()),String.valueOf(googleMap.getText()).toString());
 
     System.out.println("data locaux =="+l); 
     
     //appelle methode ajouterReclamation
     ServiceLocaux.getInstance().ajoutLocaux(l);
     iDialog.dispose();
     
     
     new ListLocauxForm(res).show();
     
     
     
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
