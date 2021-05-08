/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;
import static com.codename1.charts.util.ColorUtil.BLACK;
import static com.codename1.charts.util.ColorUtil.WHITE;
import com.codename1.components.ImageViewer;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import static com.codename1.ui.CN.FACE_SYSTEM;
import static com.codename1.ui.CN.SIZE_SMALL;
import static com.codename1.ui.CN.STYLE_BOLD;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import static com.codename1.ui.TextArea.NUMERIC;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entites.Client;
import entites.produits;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.control.DatePicker;
import jdk.nashorn.internal.parser.DateParser;

import utils.UserSession;

/**
 *
 * @author Hassene
 */
public class PanierForm extends Form {
     private Resources theme;
     int total =0 ;
    public PanierForm() {
         updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");
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
        
       setPanier();
    }
    
   public void setPanier(){
        setLayout(BoxLayout.y());
        Container c1 = new Container(BoxLayout.x());
        
        Label rs = new Label();
        rs.getAllStyles().setPadding(0, 0, 53, 53);
        rs.setIcon(theme.getImage("Fichier 2R.png")) ;
        
        c1.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
         c1.getAllStyles().setBackgroundGradientStartColor(ColorUtil.rgb(77, 180, 125),true);
        c1.getAllStyles().setBackgroundGradientEndColor(ColorUtil.rgb(77, 180, 125),true);
        rs.getAllStyles().setAlignment(CENTER);
                c1.add(rs);

        Container panier = new Container(BoxLayout.y());
        panier.setUIID("ContainerP");
        panier.getAllStyles().setMarginLeft(40);
        panier.getAllStyles().setMarginRight(40);
        panier.getAllStyles().setMarginTop(50);

        Label title = new Label("Panier");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(15);
                                panier.add(title);

           UserSession.setInstace(new Client());
        UserSession.getInstace().AddLignePanier(new produits(1,1,"Tent","Tent for camping","tent-6048dea807880667130188.jpg",1222,89.0));
        UserSession.getInstace().AddLignePanier(new produits(2,1,"Sleeping Bag","SleepingBag for sleeping","bag-6048debf908b3119444202.jpg",1222,89.0));
              
        HashMap<produits,Integer> prs =   UserSession.getInstace().getPanier();

              Label totalsl=new Label("");
               Label totall = new Label("");

        for (produits produit : prs.keySet()) {
         Container lp = new Container(BoxLayout.x());
        lp.getAllStyles().setMarginTop(10);
        lp.getAllStyles().setMarginLeft(10);
        Label v=null;
            try {
               Image  img = Image.createImage("file:/C:/Users/Hassene/Documents/Symfony/Hytaco/public/images/properties/" + produit.getImage_name()).fill(80, 80);
            v = new Label();
            v.setIcon(img);
            } catch (IOException ex) {
             }
           
            Container lnd = new Container(BoxLayout.y());

  Label nomp = new Label(produit.getNom_produit());
        nomp.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_SMALL));
    nomp.getAllStyles().setFgColor(BLACK);
  Label descp = new Label(produit.getDescription_produit());
        descp.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD,SIZE_SMALL));
         descp.getAllStyles().setMarginTop(10);
    TextArea  qt = new TextArea();
    qt.addDataChangedListener((d,l) ->{
  
    int qunt = Integer.parseInt(qt.getText());
        
    total -= produit.getPrix_produit()*prs.get(produit);
    total += produit.getPrix_produit()*qunt ;
    UserSession.getInstace().SetLignePanier(produit, qunt);
 
    totalsl.setText("SubTotal = "+String.valueOf(total));
    totall.setText("Total = "+String.valueOf(total+10));

    });
   qt.getAllStyles().setMargin(4, 10, 8, 4);
            qt.setConstraint(NUMERIC);
             Button remove = new Button();
                     remove.getAllStyles().setFgColor(BLACK);
                     remove.getAllStyles().setBgTransparency(0);
                     remove.getAllStyles().setMarginBottom(4);
        remove.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_SHOPPING_CART, remove.getAllStyles()));
        lnd.addAll(nomp,descp);
        remove.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent l) {
                total -= produit.getPrix_produit()*prs.get(produit);
                 UserSession.getInstace().RemoveLignePanier(produit);
    totalsl.setText("SubTotal = "+String.valueOf(total));
    totall.setText("Total = "+String.valueOf(total+10));
                 panier.removeComponent(lp);
                 panier.revalidate();
             }
         });
        
            DatePicker dt = new DatePicker();
            
        lp.addAll(v,lnd,qt,remove);
        panier.add(lp);
        total += produit.getPrix_produit()*prs.get(produit);
         } 
        
        Container totalc = new Container(BoxLayout.y());
        totalc.setUIID("ContainerP");
        totalc.getAllStyles().setMarginLeft(70);
        totalc.getAllStyles().setMarginRight(40);
        totalc.getAllStyles().setMarginTop(4);
        totalc.getAllStyles().setPaddingLeft(10);
        totalsl.setText("Sub Total :"+String.valueOf(total));
      totalsl.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD,CN.SIZE_MEDIUM));
      Label livraisonl = new Label("Livraison : 10");
            livraisonl.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD,CN.SIZE_MEDIUM));

     totall.setText("Total : "+String.valueOf(total+10));
            totall.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD,CN.SIZE_MEDIUM));
        
     
            Button finalb = new Button("Finalizer la commande ");
      finalb.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
         finalb.getAllStyles().setBackgroundGradientStartColor(ColorUtil.rgb(77, 180, 125),true);
        finalb.getAllStyles().setBackgroundGradientEndColor(ColorUtil.rgb(77, 180, 125),true);
        finalb.getAllStyles().setFgColor(WHITE);
         finalb.getAllStyles().setMargin(11,7,30,56);
         finalb.getAllStyles().setPadding(3, 3, 0, 0);
         
         
    totalc.addAll(totalsl,livraisonl,totall,finalb);
    
        addAll(c1,panier,totalc);
   }
}
