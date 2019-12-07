package it.statzner.testlab.spring;

import com.vaadin.annotations.Viewport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route()
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(
        name = "Daniel's TestLab",
        shortName = "DTL",
        description = "Some experiments"
)
public class MainView extends VerticalLayout {

    public MainView() {
        add(new H1("Daniels Test-Lab"));
    }

}
