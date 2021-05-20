/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;
import services.CategoriesService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import static com.codename1.ui.CN.FACE_SYSTEM;
import static com.codename1.ui.CN.SIZE_SMALL;
import static com.codename1.ui.CN.STYLE_BOLD;
import static com.codename1.ui.CN.STYLE_PLAIN;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entites.Reclamations;
import entites.categories;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.concurrent.ThreadLocalRandom.current;
import services.ServiceReclamations;
/**
 *
 * @author Taher
 */

public class afficherCategories extends BaseForm{
    Form current;

    public afficherCategories(Resources theme) {

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
    CategoriesService es = new CategoriesService();
        ArrayList<categories> listCategories = new ArrayList<categories>();
        listCategories = es.affichcategories();
        Container c1 = new Container(BoxLayout.y());
        Image imgUrl;
        
        Label label = new Label("Notre Magasin");
        
        label.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        label.getAllStyles().setAlignment(CENTER); 
                label.getAllStyles().setMarginBottom(8);
                                label.getAllStyles().setMarginTop(18);
Label labeld = new Label("une diversité de catégories des produits liés au camping");
        
        labeld.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, CN.SIZE_SMALL));
        labeld.getAllStyles().setAlignment(CENTER); 
                        labeld.getAllStyles().setMargin(0,0,20,20);

                labeld.getAllStyles().setMarginBottom(20);
        c1.addAll(label,labeld);
        
         Dimension d = new Dimension(900,100);
             
    Button btnEnsavoirplus = new Button("Voir nos produits ");
 
       btnEnsavoirplus.addActionListener((evv)-> {
          new afficherProduits(theme).show();
   //   AfficherDetailProd affd = new AfficherDetailProd(e.getId_categorie()); 
    //affd..show();
 // System.out.println(affd);
       });
         //Button searchbtn = new Button("Search");

        //c1.add(search);
        //c1.add(searchbtn);


        ///////////
        for (categories e : listCategories) {
         
             Label nom = new Label(e.getNom_categorie());
             nom.getAllStyles().setMarginTop(20);
            nom.getAllStyles().setAlignment(CENTER);
             c1.add(nom);
  Label v=null;
            try {
               Image  img = Image.createImage( "file:/C:/xampp/web/web/Hytaco/public/images/properties/" + e.getImage_name()).fill(180, 180);
               
            v = new Label();
            v.setIcon(img);
            v.getAllStyles().setAlignment(CENTER);
            } catch (IOException ex) {
             }           
            c1.getAllStyles().setAlignment(CENTER);
            
            c1.add(v);
           Label desc = new Label(e.getDescription_categorie());
                       desc.getAllStyles().setAlignment(CENTER);
                       desc.getAllStyles().setPadding(0, 0, 5,5);
             c1.add(desc);
               }  
               add(c1);
                               btnEnsavoirplus.getAllStyles().setMarginBottom(16);
                               btnEnsavoirplus.getAllStyles().setMarginTop(9);

                       c1.add(btnEnsavoirplus);

               setReclamation(theme);

    }
       public void setReclamation (Resources res){
  Container c1 = new Container(BoxLayout.x());
        c1.revalidate();
 
add (c1);
        Container payement = new Container(BoxLayout.y());
 payement.getAllStyles().setMarginTop(20);
        payement.setUIID("ContainerP");
      
        Label title = new Label("Contactez nous");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(16);
                                payement.add(title);

    Label ds = new Label("Ici, vous pouvez nous envoyer une réclamation de quelque chose que vous n'aimez pas ou que vous aimeriez voir dans le futur ou de toute autre chose.");
                                        ds.getAllStyles().setPadding(0, 0, 10,10);
 
    ds.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM,STYLE_PLAIN ,SIZE_SMALL ));
        ds.getAllStyles().setAlignment(CENTER); 
                ds.getAllStyles().setMarginBottom(13);
                payement.add(ds);
                
                
    
                
TextField type = new TextField("","");
type.setUIID("TextFieldBlack");
addStringValue("Type",type,payement);
        

TextField description = new TextField("","");
description.setUIID("TextFieldBlack");
addStringValue("Description",description,payement);



TextField email = new TextField("","");
email.setUIID("TextFieldBlack");
addStringValue("E-mail",email,payement);


 
    
     Button btnAjouter = new Button ("Ajouter");
    
      btnAjouter.addActionListener((e)->{
       try{
       
       if(type.getText() == "" ||email.getText()== ""){
         Dialog.show("veuillez vérifier les donnees ","","annuler","ok");
       
       } else {
           InfiniteProgress ip = new InfiniteProgress();
           final Dialog  iDialog = ip.showInfiniteBlocking();
           Reclamations of = new Reclamations(String.valueOf(type.getText().toString()),String.valueOf(description.getText().toString()),String.valueOf(email.getText().toString()));
           ServiceReclamations.getInstance().ajoutreclamations(of);
           iDialog.dispose();
           refreshTheme();



       }
       
       }catch(Exception ex){
       
       ex.printStackTrace();
       }
     
     });


 
 //onclick button event
 
 
     payement.add(btnAjouter);

           add(payement);

}
        private void addStringValue(String s, Component v,Container c) {
        c.add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        c.add(createLineSeparator(0xeeeeee));
    }

   
    public Form getF() {
        return current;
    }
}