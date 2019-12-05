package it.statzner.testlab.spring.modules;

import com.sun.org.apache.bcel.internal.generic.LSTORE;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import it.statzner.testlab.spring.modules.lanis.model.Role;
import it.statzner.testlab.spring.modules.lanis.model.User;
import it.statzner.testlab.spring.modules.lanis.reader.UserListFileReader;
import it.statzner.testlab.spring.modules.lanis.thr.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("lanis")
public class LanisView extends VerticalLayout {

    private UserListFileReader lanisReader;
    private List<User> userList;

    private String userNameFilter = "";
    private boolean onlyTeacherFilter = false;


    private Grid<User> grid;
    private Paragraph foundUsersParagraph;

    public LanisView(@Autowired UserListFileReader lanisReader) {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getThemeList().add("dark");

        this.lanisReader = lanisReader;

        // Grid
        this.grid = new Grid<>();
        this.grid.addColumn(User::getFirstName).setHeader("Vorname");
        this.grid.addColumn(User::getLastName).setHeader("Nachname");
        this.grid.addColumn(User::getBirthDayString).setHeader("Geburtsdatum");
        this.grid.addColumn(User::getClassName).setHeader("Klasse");
        this.grid.addColumn(User::getPassword).setHeader("Passwort");

        this.userList = new ArrayList<>();


        add(
                new H1("LANiS Decoder"),
                new Paragraph("Decrypts LANiS Users")
        );

        TextField pathField = new TextField();
        pathField.setValue("userliste_kss.txt");
        pathField.setClearButtonVisible(true);

        Button submitButton = new Button("Decode");
        submitButton.setThemeName("primary");
        submitButton.addClickListener(click -> {
            loadData(pathField.getValue());
        });

        add(new HorizontalLayout(
                pathField,
                submitButton
        ));

        TextField searchField = new TextField();
        searchField.setPlaceholder("Filter ...");
        searchField.setWidth("100%");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(event -> {
            this.userNameFilter = event.getValue();
            updateList();
        });

        this.foundUsersParagraph = new Paragraph("Gefunden: 0");
        this.foundUsersParagraph.setWidth("100%");

        // Checkbox
        Checkbox onlyTeacherCheck = new Checkbox("Nur Lehrer");
        onlyTeacherCheck.setWidth("100%");
        onlyTeacherCheck.addValueChangeListener(event -> {
            this.onlyTeacherFilter = event.getValue();
            this.updateList();
        });

        add(
                new Hr(),
                new HorizontalLayout(
                        searchField,
                        this.foundUsersParagraph,
                        onlyTeacherCheck
                ),
                this.grid
        );

    }

    public void loadData(String path) {
        try {
            this.userList = lanisReader.readUserList(new File(path));
            this.updateList();
        } catch (IOException | ParseException | InvalidUserException e) {
            Notification.show("Exception: " + e.getMessage());
        }
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

        this.foundUsersParagraph.setText("Gefunden: " + items.size());
    }

}