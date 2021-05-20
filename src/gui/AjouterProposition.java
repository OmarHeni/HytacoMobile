/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;

import services.ServiceProposition;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
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
import java.util.Date;

import entites.proposition;
import gui.BaseForm;


import static javafx.beans.binding.Bindings.format;
import utils.mailingevenement;



/**
 *
 * @author malek
 */
public class AjouterProposition extends BaseForm{
    Form current ; 

    
    
    public AjouterProposition(Resources res){
   Toolbar tb = new Toolbar(true);
         setToolbar(tb);
        getTitleArea().setUIID("Container");
      
         getContentPane().setScrollVisible(false);
       super.addSideMenu(res);
        
        Container c2 = new Container(BoxLayout.x());
        
        Label rs = new Label();
        rs.getAllStyles().setPadding(0, 0, 53, 53);
        rs.setIcon(res.getImage("Fichier 2R.png")) ;
        
        c2.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
         c2.getAllStyles().setBackgroundGradientStartColor(ColorUtil.rgb(77, 180, 125),true);
        c2.getAllStyles().setBackgroundGradientEndColor(ColorUtil.rgb(77, 180, 125),true);
        rs.getAllStyles().setAlignment(CENTER);
          c2.add(rs);
add(c2);
  

    
    TextField nom = new TextField("","entrer le nom!!");
    nom.setUIID("textFieldblack");
    addStringValue("Nom",nom);
    
     Picker debut = new Picker();
        debut.setUIID("TextFieldBlack");
        addStringValue("date debut", debut); 
    
    TextField nombre_place = new TextField("","entrer le nombre de places!!");
    nombre_place.setUIID("textFieldblack");
    addStringValue("Nombre de places",nombre_place); 
    

    TextField mail = new TextField("","entrer le mail !!");
    mail.setUIID("textFieldblack");
    addStringValue("E-mail",mail);  
    
    
   
       
     Button btnAjouter = new Button ("ajouter");
     addStringValue("",btnAjouter);
    //SimpleDateFormat    formatter = new SimpleDateFormat("dd-M-yyyy");  
     btnAjouter.addActionListener((e)->{
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
   mailingevenement.sendEmail("chayma.hamrouni@esprit.tn", "Ajout Proposition", "Votre proposition est reçue avec succés");


       }
       
       }catch(Exception ex){
       
       ex.printStackTrace();
       }
     
     });
     
    }

    private void addStringValue(String s,Component v) {
       add(BorderLayout.west(new Label (s,"paddedLabel"))
       
       .add(BorderLayout.CENTER,v)
       );
       
       add(createLineSeparator(0xeeeeee));
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
