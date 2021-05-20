/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package gui;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import static com.codename1.io.Log.e;
import java.util.Properties;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import static com.codename1.ui.TextArea.NUMERIC;
import static com.codename1.ui.TextArea.PASSWORD;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.sun.mail.smtp.SMTPTransport;
import entites.Client;
import java.io.IOException;
import java.util.Date;
import com.codename1.io.Util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import services.ServiceUtilisateur;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {
String FileNameInServer;
    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                TextField nom = new TextField();
        TextField prenom  = new TextField();
       TextField email = new TextField();
        TextField password = new TextField();
         TextField adresse = new TextField();
        TextField telephone = new TextField();
        Container h1 = new Container(BoxLayout.x());
        TextField imagel = new TextField();
        Button imageb = new Button();

         

       
        
        
        
         email.setHint("E-mail");
       password.setHint("Mot de passe");
       nom.setHint("Nom");
       prenom.setHint("Prenom");
       adresse.setHint("Adresse");
       telephone.setHint("Telephone");
       password.setConstraint(PASSWORD);
        telephone.setConstraint(NUMERIC);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        nom.setSingleLineTextArea(false);
        prenom.setSingleLineTextArea(false);
        adresse.setSingleLineTextArea(false);
        telephone.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        
        
        Button next = new Button("Next");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(prenom),
                createLineSeparator(),
                
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(adresse),
                createLineSeparator(),
                new FloatingHint(telephone),
                createLineSeparator()
                
        );
        
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        
        next.requestFocus();
           signIn.addActionListener(l->{
         previous.showBack();
      });
           
         next.addActionListener(l->{
             Client client = new Client(nom.getText(),prenom.getText(),Integer.parseInt(telephone.getText()),adresse.getText(),password.getText(),email.getText(),null,Util.getUUID().toString());
             ServiceUtilisateur.getInstance().addUtilisateur(client);
             String text = "Activation de votre compte\n" +
"veuillez clicker sur le lien ci-dessus pour l'activer  votre compte\n" +
"http://127.0.0.1:8000/activation/"+ client.getActivation_token();
            try {
                sendMail(res,client.getEmail(),text);
            } catch (MessagingException ex) {
            }
              new SignInForm(res).show() ;
      });
         
    }
    public void sendMail(Resources res ,String email,String text) throws MessagingException{
       try{
           


           Properties props = new Properties();
       
            String user = "hytacocampi@gmail.com";
            String pass = "hytaco123";
		props.put("mail.transport.protocol", "smtp"); //SMTP Host
		props.put("mail.smtp.host", "gmail.com"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                Session session;
           session = Session.getInstance(props,null);
           
                MimeMessage msg = new MimeMessage(session);

                 msg.setFrom(new InternetAddress("Verification"));
                 
                 msg.setRecipients(Message.RecipientType.TO, email);// msg.setRecipients(Message.RecipientType.TO, email.getText().toString());
                 
                 msg.setSubject("Verification");
                 msg.setSentDate(new Date(System.currentTimeMillis()));
                 
                // String code = ServiceUser.getInstance().getPasswordByEmaile(email.getText().toString(), res) ;
                 msg.setText(text);
                 
                 SMTPTransport st = (SMTPTransport) session.getTransport("smtp");
                 
                System.out.println(st.isSSL());
                st.connect("smtp.gmail.com",587 ,user,pass);
                
                             System.out.println(st.isSSL());

                 st.sendMessage(msg,msg.getAllRecipients());
                 System.out.println("server response : "+st.getLastServerResponse());
    
    
       }catch(Exception e){
           e.printStackTrace();
       }        
    
    
}
}
