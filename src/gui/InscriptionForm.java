/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static com.codename1.charts.util.ColorUtil.WHITE;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.FACE_SYSTEM;
import static com.codename1.ui.CN.STYLE_PLAIN;
import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import static com.codename1.ui.TextArea.NUMERIC;
import static com.codename1.ui.TextArea.PASSWORD;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import entites.Client;
import services.ServiceUtilisateur;

/**
 *
 * @author Hassene
 */
public class InscriptionForm {
     private Resources theme;
      private Form form;

    public InscriptionForm(Form previous) {
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
        
        
          form = new Form();
              UIBuilder uib = new UIBuilder();
        Container c1 = uib.createContainer(theme, "LoginGUI");
        form = (Form) c1 ;
        
        Container LoginInputs = new  Container();
        LoginInputs.setLayout(BoxLayout.y());
        
        TextField nom = new TextField();
        TextField prenom  = new TextField();
       TextField email = new TextField();
        TextField password = new TextField();
         TextField adresse = new TextField();
        TextField telephone = new TextField();
        
       Style sinputs = new Style();
       sinputs.setBgTransparency(0);
      sinputs.setFgColor(WHITE);
       sinputs.setMarginLeft(74);
       sinputs.setBorder(Border.createUnderlineBorder(2,WHITE));
       sinputs.setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_PLAIN, 17));
    
       email.setHint("E-mail");
       password.setHint("Mot de passe");
       nom.setHint("Nom");
       prenom.setHint("Prenom");
       adresse.setHint("Adresse");
       telephone.setHint("Telephone");
       
       
        password.setUnselectedStyle(sinputs);
        email.setUnselectedStyle(sinputs);
        password.setSelectedStyle(sinputs);
        email.setSelectedStyle(sinputs);
        nom.setSelectedStyle(sinputs);
        prenom.setSelectedStyle(sinputs);
        nom.setUnselectedStyle(sinputs);
        prenom.setUnselectedStyle(sinputs);
        telephone.setSelectedStyle(sinputs);
        adresse.setSelectedStyle(sinputs);
        telephone.setUnselectedStyle(sinputs);
        adresse.setUnselectedStyle(sinputs);

        password.setPressedStyle(sinputs);
        email.setPressedStyle(sinputs);
        nom.setPressedStyle(sinputs);
        prenom.setPressedStyle(sinputs);
        telephone.setPressedStyle(sinputs);
        adresse.setPressedStyle(sinputs);
        

        
       Button signin = new Button("Login");
       signin.getAllStyles().setFgColor(WHITE);
       signin.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_PLAIN, 22));
       signin.getAllStyles().setBgTransparency(0);
       signin.getAllStyles().setBorder(Border.createInsetBorder(3, WHITE));
       signin.getAllStyles().setPadding(4,4, 9, 9);
       
Container LoginCon = new  Container();
      LoginCon.setLayout(BoxLayout.x());
      LoginCon.add(signin);
      LoginCon.getAllStyles().setMarginTop(20);
     LoginCon.getAllStyles().setMarginLeft(165);
     
     Button signup = new Button("Inscription");
       signup.getAllStyles().setFgColor(WHITE);
       signup.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_PLAIN, 22));
       signup.getAllStyles().setBgTransparency(0);
       signup.getAllStyles().setBorder(Border.createInsetBorder(3, WHITE));
       signup.getAllStyles().setPadding(5,5, 10, 10);
       
Container InscCon = new  Container();
      InscCon.setLayout(BoxLayout.x());
      InscCon.add(signup);
      InscCon.getStyle().setMarginTop(30);
     InscCon.getStyle().setMarginLeft(147);
     
           Container LoginEmail = new  Container();
      LoginEmail.setLayout(BoxLayout.x());
           Container LoginPass = new  Container();
      LoginPass.setLayout(BoxLayout.x());
           Container LoginNom = new  Container();
      LoginNom.setLayout(BoxLayout.x());
      Container LoginPrenom = new  Container();
      LoginPrenom.setLayout(BoxLayout.x());
      Container LoginTelephone = new  Container();
      LoginTelephone.setLayout(BoxLayout.x());
      Container LoginAdresse = new  Container();
      LoginAdresse.setLayout(BoxLayout.x());
      
         
      
      LoginEmail.add(email);
      LoginPass.add(password);
      LoginNom.add(nom);
      LoginPrenom.add(prenom);
      LoginTelephone.add(telephone);
      LoginAdresse.add(adresse);
      
       signin.addActionListener(l->{
         previous.showBack();
      });
         signup.addActionListener(l->{
             Client client = new Client(nom.getText(),prenom.getText(),Integer.parseInt(telephone.getText()),adresse.getText(),password.getText(),email.getText(),null,null);
             ServiceUtilisateur.getInstance().addUtilisateur(client);
      });
      Button but = new Button();
     
FontImage.setMaterialIcon(but, FontImage.MATERIAL_THUMB_UP);
        password.setConstraint(PASSWORD);
        telephone.setConstraint(NUMERIC);
        LoginInputs.addAll(LoginNom,LoginPrenom,LoginEmail,
                LoginPass,LoginAdresse,LoginTelephone,but,InscCon,LoginCon);
        form.add(LoginInputs);
       
    }

    public Form getForm() {
        return form;
    }
      
}
