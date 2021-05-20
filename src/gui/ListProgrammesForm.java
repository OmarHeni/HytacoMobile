/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import entites.Programmes;

import services.ServiceProgrammes;
import java.util.ArrayList;

/**
 *
 * @author Hass
 */
public class ListProgrammesForm extends BaseForm {
    
    
    
    
    
    
 Form current; 
public ListProgrammesForm(Resources res){
   
    
 super ("Newsfeed",BoxLayout.y());
Toolbar tb = new Toolbar(true);
current = this;
setToolbar(tb);
getTitleArea().setUIID("Container");
setTitle("Affichage Programmes");
getContentPane().setScrollVisible(false);
    refreshTheme();//actualisation
 ArrayList<Programmes>list= ServiceProgrammes.getInstance().affichageProgrammes();
 for (Programmes rec : list){
     System.out.println(rec);
     String urlImage = "back.png";
     Image placeHolder=Image.createImage(120,90);
     EncodedImage enc = EncodedImage.createFromImage(placeHolder,false);
     URLImage urlim=URLImage.createToStorage(enc,urlImage,urlImage,URLImage.RESIZE_SCALE);
   addButton(urlim,rec.getNom(),rec.getDate(),rec.getDuree(),rec.getDetails(),rec); 
   ScaleImageLabel image=new ScaleImageLabel(urlim);
   Container ContainerImg = new Container();
   image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
 }










    
}    

    private void addButton(Image img,String nom, String date, int duree, String details, Programmes rec) {
       int height = Display.getInstance().convertToPixels(11.5f);
         int width = Display.getInstance().convertToPixels(14f);
     
   Button image = new Button(img.fill(width,height));
   image.setUIID("Label");
         
      Container cnt = BorderLayout.west(image);
      TextArea ta = new TextArea(nom);
      ta.setUIID("NewsTopLine");
      ta.setEditable(false);
      cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(ta));
      add(cnt);
      
    }    
    
    
    
    
    
    
    
    
    
}
