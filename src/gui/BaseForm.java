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

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entites.Client;
import java.io.IOException;

import utils.UserSession;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }
    
    
    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {
        Toolbar tb = getToolbar();
//          Client c = UserSession.getInstace().getClient();
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        //try {
            tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                    sl,
                    FlowLayout.encloseCenterBottom(
                           // new Label(Image.createImage("file:/C:/xampp/web/web/Hytaco/public/images/properties/" + c.getImage_name()).fill(80, 80), "PictureWhiteBackgrond"))
                            new Label(img, "PictureWhiteBackgrond"))
            ));
        //} catch (IOException ex) {
       
        //}
      
      
         tb.addMaterialCommandToSideMenu("Newsfeed", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(res).show());
         tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
         tb.addMaterialCommandToSideMenu("Panier", FontImage.MATERIAL_SHOPPING_CART, e -> new PanierForm(res).show());
         tb.addMaterialCommandToSideMenu("Alerts", FontImage.MATERIAL_GET_APP, e -> new AjouterAlerts(res).show());
         tb.addMaterialCommandToSideMenu("Produits", FontImage.MATERIAL_GET_APP, e -> new afficherProduits(res).show());
         tb.addMaterialCommandToSideMenu("Categories", FontImage.MATERIAL_GET_APP, e -> new afficherCategories(res).show());
         tb.addMaterialCommandToSideMenu("Programmes", FontImage.MATERIAL_GET_APP, e -> new ListProgrammesForm(res).show());
         tb.addMaterialCommandToSideMenu("Locaux", FontImage.MATERIAL_GET_APP, e -> new ListLocauxForm(res).show());
         tb.addMaterialCommandToSideMenu("Proposition", FontImage.MATERIAL_GET_APP, e -> new AjouterProposition(res).show());
          tb.addMaterialCommandToSideMenu("Evenement", FontImage.MATERIAL_GET_APP, e -> new afficherEvenements(res).show());
           tb.addMaterialCommandToSideMenu("Sponsor", FontImage.MATERIAL_GET_APP, e -> new afficherSponsors(res).show());
         
         tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e ->{ });

    }
}
