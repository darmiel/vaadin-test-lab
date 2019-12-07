package it.statzner.testlab.spring.modules.lanis;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import it.statzner.testlab.spring.modules.lanis.model.Role;
import it.statzner.testlab.spring.modules.lanis.model.User;
import it.statzner.testlab.spring.modules.lanis.reader.UserListFileReader;
import it.statzner.testlab.spring.modules.lanis.thr.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("lanis")
@Theme(value = Material.class, variant = Material.DARK)
public class LanisView extends VerticalLayout {

    private UserListFileReader lanisReader;
    private List<User> userList;

    private String userNameFilter = "";
    private boolean onlyTeacherFilter = false;

    private Grid<User> grid;
    private Paragraph foundUsersParagraph;
    private ProgressBar progressBar;

    public LanisView(@Autowired UserListFileReader lanisReader) {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getThemeList().add("dark");

        this.lanisReader = lanisReader;
        this.userList = new ArrayList<>();

        // Grid
        this.grid = new Grid<>();
        this.grid.addColumn(User::getFirstName).setHeader("Vorname");
        this.grid.addColumn(User::getLastName).setHeader("Nachname");
        this.grid.addColumn(User::getBirthDayString).setHeader("Geburtsdatum");
        this.grid.addColumn(User::getClassName).setHeader("Klasse");
        this.grid.addColumn(User::getPassword).setHeader("Passwort");

        // ProgressBar
        this.progressBar = new ProgressBar();

        // Text Path Field
        /*
        TextField pathField = new TextField();
        pathField.setValue("userliste_kss.txt");
        pathField.setClearButtonVisible(true);
        pathField.setHeightFull();
        pathField.setWidth("70%");
         */

        ComboBox<String> comboBox = new ComboBox<>("Browsers");
        comboBox.setItems("Google Chrome", "Mozilla Firefox", "Opera",
                "Apple Safari", "Microsoft Edge");

        comboBox.addValueChangeListener(event -> {
        });

        // Decode Button
        Button submitButton = new Button("Decode");
        submitButton.setThemeName("primary");
        submitButton.addClickListener(click -> {
            loadData(comboBox.getValue());
        });

        // Textfield
        TextField searchField = new TextField();
        searchField.setPlaceholder("Filter ...");
        searchField.setWidth("100%");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(event -> {
            this.userNameFilter = event.getValue();
            updateList();
        });

        // Found Info
        this.foundUsersParagraph = new Paragraph("Gefunden: 0");
        this.foundUsersParagraph.setWidth("100%");

        // Checkbox
        Checkbox onlyTeacherCheck = new Checkbox("Nur Lehrer");
        onlyTeacherCheck.setWidth("100%");
        onlyTeacherCheck.addValueChangeListener(event -> {
            this.onlyTeacherFilter = event.getValue();
            this.updateList();
        });


        // Select Teil
        HorizontalLayout selectLayout = new HorizontalLayout(
                comboBox,
                submitButton
        );
        selectLayout.setWidth("30%");
        selectLayout.setAlignItems(Alignment.CENTER);

        Div div = new Div();
        div.setHeightFull();
        div.setWidth("70%");
        div.add(

                // Select File
                new Hr(),
                selectLayout,

                // Search
                new Hr(),
                new HorizontalLayout(
                        searchField,
                        this.foundUsersParagraph,
                        onlyTeacherCheck
                ),
                this.progressBar,
                this.grid

        );

        add(
                // Header
                new Header(
                        new H1("LANiS-Decrypt"),
                        new Paragraph("Decrypts LANiS Users")
                ),

                // Main Div
                div
        );
    }

    public void loadData(String path) {
        // Progress start
        this.progressBar.setVisible(true);
        long startTime = System.currentTimeMillis();

        try {
            this.userList = lanisReader.readUserList(new File(path));

            // Progress end
            this.progressBar.setMax(this.userList.size());

            this.updateList();
        } catch (IOException | ParseException | InvalidUserException e) {
            Notification.show("Exception: " + e.getMessage());
        }

        Notification.show(
                "Data loaded in " + (System.currentTimeMillis() - startTime) + " ms!",
                1000,
                Notification.Position.MIDDLE
        );
    }

    public void updateList() {
        List<User> items = this.userList.stream()
                .filter(user -> {
                    if (onlyTeacherFilter && user.role != Role.TEACHER) {
                        return false;
                    }

                    if (this.userNameFilter != null && this.userNameFilter.length() > 0) {
                        return (user.firstName + " " + user.lastName).toLowerCase().contains(this.userNameFilter.toLowerCase());
                    }

                    return true;
                }).collect(Collectors.toList());

        this.grid.setItems(items);

        this.foundUsersParagraph.setText("Gefunden: " + items.size() + " (" +
                Math.round((double)items.size() / (double)this.userList.size()*100D) + "%)");
        this.progressBar.setValue(items.size());
    }

}