/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;
import services.ServiceReclamations;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.FACE_SYSTEM;
import static com.codename1.ui.CN.SIZE_SMALL;
import static com.codename1.ui.CN.STYLE_BOLD;
import static com.codename1.ui.CN.STYLE_PLAIN;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entites.Reclamations;

/**
 *
 * @author malek
 */
public class AjouterReclamations extends BaseForm{
    Form current ; 
    
    
    
    public AjouterReclamations(Resources res){
             updateNetworkThreadCount(2);

        res = UIManager.initFirstTheme("/theme");
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
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
    setLayout(BoxLayout.y());
         Container c1 = new Container(BoxLayout.x());
        c1.revalidate();
        Label rs = new Label();
        rs.getAllStyles().setPadding(0, 0, 53, 53);
        rs.setIcon(res.getImage("Fichier 2R.png")) ;
        
        c1.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
        c1.getAllStyles().setBackgroundGradientStartColor(ColorUtil.rgb(77, 180, 125),true);
        c1.getAllStyles().setBackgroundGradientEndColor(ColorUtil.rgb(77, 180, 125),true);
        rs.getAllStyles().setAlignment(CENTER);
                c1.add(rs);
add (c1);
   setReclamation(res);
  

    
    

    
         
       
 
    
    
    
     
    }

   public void setReclamation (Resources res){
  Container c1 = new Container(BoxLayout.x());
        c1.revalidate();
 
add (c1);
        Container payement = new Container(BoxLayout.y());
        payement.setUIID("ContainerP");
      
        Label title = new Label("Contactez nous");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(16);
                                payement.add(title);

    Label ds = new Label("Ici, vous pouvez nous envoyer une réclamation de quelque chose que vous n'aimez pas ou que vous aimeriez voir dans le futur ou de toute autre chose.");
        ds.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM,STYLE_PLAIN ,SIZE_SMALL ));
        ds.getAllStyles().setAlignment(CENTER); 
                ds.getAllStyles().setMarginBottom(13);
                payement.add(ds);
                
                
    
                
TextField type = new TextField("","");
type.setUIID("TextFieldBlack");
addStringValue("type",type,payement);
        

TextField description = new TextField("","");
description.setUIID("TextFieldBlack");
addStringValue("description",description,payement);



TextField email = new TextField("","");
email.setUIID("TextFieldBlack");
addStringValue("email",email,payement);


 
    
     Button btnAjouter = new Button ("ajouter");
    
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
