package it.statzner.testlab.spring.modules;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("davinci")
public class DavinciLookView extends VerticalLayout {

    public DavinciLookView() {

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getThemeList().add("dark");

        add(
                new H1("DaVinci-Look Decrypter"),
                new Paragraph("Decrypts a Davinci-Look Password")
        );

        TextField inputField = new TextField();

        // Button settings
        Button submitButton = new Button("Decrypt!");
        submitButton.setThemeName("primary");
        submitButton.addClickListener(click -> {

            StringBuilder builder = new StringBuilder();
            for (char c : inputField.getValue().toCharArray()) {
                builder.append( (char) ((int) c - 3) );
            }

            Notification.show("Decrypted: " + builder.toString());
            add(new Span(
                    new H3(inputField.getValue()),
                    new Pre(builder.toString())
            ));
            add(new Hr());
        });

        // Add layout
        add(new HorizontalLayout(
                inputField,
                submitButton
        ));
    }

}
