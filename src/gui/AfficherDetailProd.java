/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;
import services.ProduitsService;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import entites.produits;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.text.SimpleDateFormat;
/**
 *
 * @author Taher
 */
class AfficherDetailProd extends BaseForm {
     Form DetailProd;
        private Resources theme;
         private static int id ;
public AfficherDetailProd (int id) {
  theme = UIManager.initFirstTheme("/theme");
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
            this.id = id ;
            TextModeLayout tl = new TextModeLayout(3, 2);
                 ProduitsService es = new ProduitsService();
                 produits e =   es.afficherprod(id);
                 Container c1 = new Container(BoxLayout.y());
                  Image imgUrl;
               
                 Label Nomevet = new Label(e.getNom_produit());
                 Nomevet.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
                 Label descrp = new Label("Description : " +e.getDescription_produit());
                // Label prixe  = new Label("prix : " +Integer.toString(e.));
                // Label prixe  = new Label("Prix :  " +Float.toString(e.getPrix_produit()));
                 Label Quantites  = new Label("Quantite :  " +Integer.toString(e.getQuantite_produit()));
                 TextArea description= new TextArea(e.getDescription_produit());
                 description.getAllStyles().setFgColor(0x000000);
                 description.setMaxSize(2147483647 );
                 description.setEditable(false);
                 Image placeholder = Image.createImage(500, 120); 
                 EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);

           
             imgUrl = URLImage.createToStorage(encImage, "file:/C:/xampp/web/web/Hytaco/public/images/properties/" + e.getImage_name(), "file:/C:/xampp/web/web/Hytaco/public/images/properties/" + e.getImage_name()).scaled(300, 300);
                     c1.add(Nomevet);
                     c1.add(imgUrl);
                     c1.addAll(description,Quantites);
              Container c3 = new Container(BoxLayout.y());
                 Container d2 = new Container(BoxLayout.x());
                 Container d1 = new Container(BoxLayout.x());

        final FontImage time = FontImage.createMaterial(FontImage.MATERIAL_DATE_RANGE, "Label", 6);
        
      
        Container c20 = new Container(BoxLayout.x());

          
               

                      
                     
              
           /* DetailEvent .getToolbar().addCommandToOverflowMenu("Back ", null, evd-> { 
                 AfficherAllEvents aff = new AfficherAllEvents();
                 aff.getF().showBack();
              
         });*/
                          

                Button btnajoutcmt = new Button("Ajouter au panier"); 

         addAll(c2,c1,c3);
        
 };}