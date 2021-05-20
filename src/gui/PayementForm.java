/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import services.ServiceCommande;

/**
 *
 * @author user
 */
public class PayementForm extends BaseForm {
         private Resources theme;
         private int stotal ;
         private int com ;

    public void setCom(int com) {
        this.com = com;
    }
         
    public void setStotal(int stotal) {
        this.stotal = stotal;
    }
         
    public PayementForm(){
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
       setPayement();
    }
    
       public void setPayement(){
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
                                
        TextField card = new TextField(null,"");
        card.setUIID("TextFieldBlack");
        addStringValue("Payement card", card,payement);
        
        TextField cvc = new TextField(null,"");
        cvc.setUIID("TextFieldBlack");
        addStringValue("CVC", cvc,payement);

        TextField month = new TextField(null,"");
        month.setUIID("TextFieldBlack");
        addStringValue("Month", month,payement);
        
        TextField year = new TextField(null,"");
        year.setUIID("TextFieldBlack");
        addStringValue("Year", year,payement);
        

        /*  signup.addActionListener(l->{
             Client client = new Client(nom,prenom,Integer.parseInt(telephone),adresse,password,email,null,null);
             ServiceUtilisateur.getInstance().addUtilisateur(client);
      });*/
       Button submit = new Button("Submit");
       submit.addActionListener(l->{
           Payement(card.getText(),cvc.getText(),month.getText(),year.getText());
          /*   Client client = new Client(c.getId(),nom,prenom,Integer.parseInt(telephone),adresse,email,null);
             ServiceUtilisateur.getInstance().editUtilisateur(client);*/
      });
       
       Button payeliv = new Button("Payé à livraison");
       payeliv.addActionListener(l->{
                     new AddLivraisonForm().show();

          /*   Client client = new Client(c.getId(),nom,prenom,Integer.parseInt(telephone),adresse,email,null);
             ServiceUtilisateur.getInstance().editUtilisateur(client);*/
      });
     payement.addAll(submit,payeliv);
       add(payement);
        
     }
         public void Payement(String cardnum,String cvc,String exp_month,String exp_year){
        Stripe.apiKey = "sk_test_51IXl9nAyyifkJ2GTw02VQPccPVPzbU7UW382UezlP4Npm0ajBpy9eJMhiFk3PHdfvO7Co06fR2dzmXlqMei3CqPC00ZksblkBB";
        Map<String,Object> Param = new HashMap<String,Object>();
        Param.put("number", cardnum);
        Param.put("exp_month",exp_month);
        Param.put("exp_year", exp_year);
        Param.put("cvc", cvc);
        Map<String,Object> TokenParam = new HashMap<String,Object>();
        TokenParam.put("card",Param);
        Token token=null;
        try {
            token = Token.create(TokenParam);
        } catch (StripeException ex) {
        }
        Map<String,Object> chargeParam = new HashMap<String,Object>();
        chargeParam.put("amount", Math.round((stotal+10)*0.3*100));
        chargeParam.put("currency", "EUR");
        chargeParam.put("source", token.getId());
        try {
        Charge a =   Charge.create(chargeParam);
        if(a.getPaid()){
               Dialog.show("Payement", "Succes","ok","cancel");
          AddLivraisonForm liv = new AddLivraisonForm();
          ServiceCommande.getInstance().topaye(com);
          liv.setCom(com);
          liv.show();
        }
        } catch (StripeException ex) {
        }
             
        
    }
           private void addStringValue(String s, Component v,Container c) {
        c.add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        c.add(createLineSeparator(0xeeeeee));
    }
}
