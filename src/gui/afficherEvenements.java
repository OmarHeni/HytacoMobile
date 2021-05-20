/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entites.evenements;

import java.io.IOException;
import java.util.ArrayList;

import static java.util.concurrent.ThreadLocalRandom.current;
import services.EvenementsServices;
/**
 *
 * @author Taher
 */

public class afficherEvenements extends BaseForm{
    Form current;

    public afficherEvenements(Resources theme) {
   Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
      
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(theme);
        
        tb.addSearchCommand(e -> {});
        Container c2 = new Container(BoxLayout.x());
        
        Label rs = new Label();
        rs.getAllStyles().setPadding(0, 0, 53, 53);
        rs.setIcon(theme.getImage("Fichier 2R.png")) ;
        
        c2.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
         c2.getAllStyles().setBackgroundGradientStartColor(ColorUtil.rgb(77, 180, 125),true);
        c2.getAllStyles().setBackgroundGradientEndColor(ColorUtil.rgb(77, 180, 125),true);
        rs.getAllStyles().setAlignment(CENTER);
          c2.add(rs);
        add(c2);
   
   EvenementsServices es = new EvenementsServices();
        ArrayList<evenements> listEvenements = new ArrayList<evenements>();
        listEvenements = es.affichevenements();
        Container c1 = new Container(BoxLayout.y());
        Image imgUrl;
        Label label = new Label("Liste  des evenements ");
        c1.add(label);
        Container s  = new Container(BoxLayout.x());
         Dimension d = new Dimension(900,100);
         TextField search = new TextField("","search");
         FloatingActionButton searchbtn = FloatingActionButton.createFAB(FontImage.MATERIAL_SEARCH);
         search .setPreferredSize(d);
        //Button searchbtn = new Button("Search");
        searchbtn.addActionListener((e) -> {
            String a = search.getText();
        
          //  new searcheventsForm(current,a).show();
             
        });
        //c1.add(search);
        //c1.add(searchbtn);
        s.addAll(search,searchbtn);
        c1.add(s);
        ///////////
        for (evenements e : listEvenements) {
         
             Label Nomevet = new Label(e.getNom());
             c1.add(Nomevet);
               Label v=null;
            try {
               Image  img = Image.createImage( "file:/C:/Users/Firqs/Documents/Hytaco/Hytaco/public/images/properties/" + e.getImage_name()).fill(500, 400);
               
            v = new Label();
            v.setIcon(img);
            v.getAllStyles().setAlignment(CENTER);
            } catch (IOException ex) {
             }
             Button btnEnsavoirplus = new Button("En savoir plus ");
            c1.getAllStyles().setAlignment(CENTER);
            
            c1.add(v);
           c1.add(btnEnsavoirplus);
 
       btnEnsavoirplus.addActionListener((evv)-> {
             System.out.println(e.getId());

      AfficherDetailEvent affd = new AfficherDetailEvent(e.getId());
      
      
  affd.show();
       });
               }  
               add(c1);

    } 
    public Form getF() {
        return current;
    }
}