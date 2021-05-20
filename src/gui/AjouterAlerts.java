/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.util.ColorUtil;
import services.ProduitsService;
import services.ServiceAlerts;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.messaging.Message;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
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
import com.codename1.ui.Display;
import com.codename1.ui.Font;
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
import com.codename1.ui.util.Resources;
import entites.Alerts;
import entites.Programmes;
import gui.BaseForm;
import java.text.SimpleDateFormat;
import services.ServiceProgrammes;
import utils.mailingAlerts;

/**
 *
 * @author malek
 */
public class AjouterAlerts extends BaseForm{
    Form current ; 
    
    
    
    public AjouterAlerts(Resources res){
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
   setAlert(res);
  

    
    
    
    
     
    }
  public void setAlert (Resources res){
  Container c1 = new Container(BoxLayout.x());
        c1.revalidate();
 
add (c1);
        Container payement = new Container(BoxLayout.y());
        payement.setUIID("ContainerP");
      
        Label title = new Label("Alert");
        title.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, 13));
        title.getAllStyles().setAlignment(CENTER); 
                title.getAllStyles().setMarginBottom(16);
                                payement.add(title);

    Label ds = new Label("Ici, vous pouvez nous envoyer une alerte si quelque chose s'est mal passé durant le programme.");
        ds.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM,STYLE_PLAIN ,SIZE_SMALL ));
        ds.getAllStyles().setAlignment(CENTER); 
                ds.getAllStyles().setMarginBottom(13);
                payement.add(ds);
                
TextField localisation = new TextField("","");
localisation.setUIID("TextFieldBlack");
addStringValue("Localisation",localisation,payement);
        

TextField rapport = new TextField("","");
rapport.setUIID("TextFieldBlack");
addStringValue("Rapport",rapport,payement);


TextField telephone = new TextField("","");
telephone.setUIID("TextFieldBlack");
addStringValue("Telephone",telephone,payement);



TextField mail = new TextField("","");
mail.setUIID("TextFieldBlack");
addStringValue("Mail",mail,payement);

Button bt_contact = new Button("Urgence");

 bt_contact.addActionListener((evt) -> {
            
            String Body="Bonjour Mr.";
            Message m = new Message(Body);
            Display.getInstance().sendMessage(new String[] {"HYTACOCAMPII@gmail.com"}, "Alert", m);

        });
     Button btnAjouter = new Button ("Envoyer");
    
      btnAjouter.addActionListener((e)->{
       try{
       
       if(localisation.getText() == "" ||mail.getText()== ""){
         Dialog.show("veuillez vérifier les donnees ","","annuler","ok");
       
       } else {
           InfiniteProgress ip = new InfiniteProgress();
           final Dialog  iDialog = ip.showInfiniteBlocking();
           Alerts of = new Alerts(String.valueOf(localisation.getText().toString()),String.valueOf(rapport.getText().toString()),Integer.parseInt(telephone.getText()),String.valueOf(mail.getText().toString()));
           ServiceAlerts.getInstance().ajoutalerts(of);
           iDialog.dispose();
           refreshTheme();
           mailingAlerts.sendEmail("taher.bekri@esprit.tn", "Alert", "Cher Admin une nouvelle alert est recu.");


       }
       
       }catch(Exception ex){
       
       ex.printStackTrace();
       }
     
     });
 
     payement.add(btnAjouter);  
     payement.add(bt_contact);

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
