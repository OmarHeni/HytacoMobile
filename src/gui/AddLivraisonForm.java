/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entites.Livraison;
import com.codename1.charts.util.ColorUtil;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.FACE_SYSTEM;
import static com.codename1.ui.CN.STYLE_BOLD;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import javafx.scene.control.DatePicker;
import services.ServicesLivraisons;

/**
 *
 * @author dell
 */
public class AddLivraisonForm extends BaseForm {
             private Resources theme;
             private int com ;

    public void setCom(int com) {
        this.com = com;
    }
             
     public AddLivraisonForm(){
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
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
      
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(theme);
        
        tb.addSearchCommand(e -> {});
       setliv();
    }
    
       public void setliv(){
        setLayout(BoxLayout.y());
        Container c1 = new Container(BoxLayout.x());
        c1.revalidate();
        Label rs = new Label();
        rs.getAllStyles().setPadding(0, 0, 53, 53);
        rs.setIcon(theme.getImage("Fichier 2R.png")) ;
        
        c1.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
        c1.getAllStyles().setBackgroundGradientStartColor(ColorUtil.rgb(77, 180, 125),true);
        c1.getAllStyles().setBackgroundGradientEndColor(ColorUtil.rgb(77, 180, 125),true);
        rs.getAllStyles().setAlignment(CENTER);
                c1.add(rs);
add (c1);
        Container payement = new Container(BoxLayout.y());
        payement.setUIID("ContainerP");
      
        Label title = new Label("Payement");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(15);
                                payement.add(title);
                                
        TextField Adresse = new TextField(null,"Adresse");
        Adresse.setUIID("TextFieldBlack");
        addStringValue("Adresse", Adresse,payement);
        
        TextField dt = new TextField();
        dt.setUIID("TextFieldBlack");
        addStringValue("Date", dt,payement);


        

        /*  signup.addActionListener(l->{
             Client client = new Client(nom,prenom,Integer.parseInt(telephone),adresse,password,email,null,null);
             ServiceUtilisateur.getInstance().addUtilisateur(client);
      });*/
       Button submit = new Button("Submit");
       submit.addActionListener(l->{
           Livraison liv = new Livraison(dt.getText(), Adresse.getText());
           System.out.print(liv);
                        ServicesLivraisons.getInstance().addLivraison(liv,com);

          /*   Client client = new Client(c.getId(),nom,prenom,Integer.parseInt(telephone),adresse,email,null);
             ServiceUtilisateur.getInstance().editUtilisateur(client);*/
      });

     payement.addAll(submit);
       add(payement);
        
     }
              private void addStringValue(String s, Component v,Container c) {
        c.add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        c.add(createLineSeparator(0xeeeeee));
    }
      
}
