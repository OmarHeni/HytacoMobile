/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entites.Locaux;
import java.io.IOException;
import services.ServiceLocaux;
import java.util.ArrayList;

/**
 *
 * @author Hass
 */
public class ListLocauxForm extends BaseForm {
   Form current; 
public ListLocauxForm(Resources theme){
   

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
    ServiceLocaux es = new ServiceLocaux();
        ArrayList<Locaux> listLocaux = new ArrayList<Locaux>();
        listLocaux = es.affichageLocaux();
        Container c1 = new Container(BoxLayout.y());
        Image imgUrl;
        Label label = new Label("Liste  des Locaux ");
        c1.add(label);
        Container s  = new Container(BoxLayout.x());
         Dimension d = new Dimension(900,100);
         TextField search = new TextField("","search");
         FloatingActionButton searchbtn = FloatingActionButton.createFAB(FontImage.MATERIAL_SEARCH);
         search .setPreferredSize(d);
                 searchbtn.addActionListener((e) -> {
            String a = search.getText();

          //  new searcheventsForm(current,a).show();
             
        });
    Button btnEnsavoirplus = new Button("Ajouter un Locaux ");
 
       btnEnsavoirplus.addActionListener((evv)-> {
           new AjoutLocauxForm(theme).show();
   //   AfficherDetailProd affd = new AfficherDetailProd(e.getId_categorie()); 
    //affd..show();
 // System.out.println(affd);
       });
         //Button searchbtn = new Button("Search");

        //c1.add(search);
        //c1.add(searchbtn);
        s.addAll(search,searchbtn);
                 c1.add(s);

        c1.add(btnEnsavoirplus);

        ///////////
        for (Locaux e : listLocaux) {
         
          
  Label v=null;
            try {
               Image  img = Image.createImage( "file:/C:/Users/storm/Bureau/School/Hytaco/public/images/properties/" + e.getImageName()).fill(220, 220);
               
            v = new Label();
            v.setIcon(img);
            v.getAllStyles().setAlignment(CENTER);
            } catch (IOException ex) {
             }           
            c1.getAllStyles().setAlignment(CENTER);
            
            c1.add(v);
               Label nom = new Label(e.getNom());
                        nom.getAllStyles().setAlignment(CENTER);

             c1.add(nom);
           Label desc = new Label(e.getDescription());
           desc.getAllStyles().setAlignment(CENTER);
          
           desc.getAllStyles().setMarginBottom(100);
             c1.add(desc);
               }  
               add(c1);

    } 
    public Form getF() {
        return current;
    }
}