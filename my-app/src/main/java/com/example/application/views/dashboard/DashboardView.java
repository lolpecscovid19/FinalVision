package com.example.application.views.dashboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Crosshair;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "dashboard", layout = MainView.class)
@PageTitle("Dashboard")
@CssImport("./views/dashboard/dashboard-view.css")
public class DashboardView extends Div {

    private Grid<HealthGridItem> grid = new Grid<>();

    private Chart monthlyVisitors = new Chart();
    private Chart responseTimes = new Chart();
    private final H2 usersH2 = new H2();
    private final H2 eventsH2 = new H2();
    private final H2 conversionH2 = new H2();

    public DashboardView() {
        addClassName("dashboard-view");
        Board board = new Board();
        board.addRow(createBadge("Treating", usersH2, "primary-text", "Current People treated in the hospital", "badge"),
                createBadge("Test", eventsH2, "success-text", "People tested here", "badge success"),
                createBadge("Vaccine", conversionH2, "error-text", "Rate of People who receive the vaccine this city", "badge error"));

        monthlyVisitors.getConfiguration().setTitle("Monthly epidemic in the city");
        monthlyVisitors.getConfiguration().getChart().setType(ChartType.COLUMN);
        WrapperCard monthlyVisitorsWrapper = new WrapperCard("wrapper", new Component[]{monthlyVisitors}, "card");
        board.add(monthlyVisitorsWrapper);

        grid.addColumn(HealthGridItem::getName).setHeader("Name");
        grid.addColumn(new ComponentRenderer<>(item -> {
            Span span = new Span(item.getStatus());
            span.getElement().getThemeList().add(item.getTheme());
            return span;
        })).setHeader("Status").setFlexGrow(0).setAutoWidth(true);
        grid.addColumn(HealthGridItem::getDate).setHeader("Date").setFlexGrow(0).setAutoWidth(true);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        WrapperCard gridWrapper = new WrapperCard("wrapper", new Component[]{new H3("Patient health"), grid}, "card");
        responseTimes.getConfiguration().setTitle("Epidemic situation");
        WrapperCard responseTimesWrapper = new WrapperCard("wrapper", new Component[]{responseTimes}, "card");
        board.addRow(gridWrapper, responseTimesWrapper);

        add(board);

        populateCharts();
    }

    private WrapperCard createBadge(String title, H2 h2, String h2ClassName, String description, String badgeTheme) {
        Span titleSpan = new Span(title);
        titleSpan.getElement().setAttribute("theme", badgeTheme);

        h2.addClassName(h2ClassName);

        Span descriptionSpan = new Span(description);
        descriptionSpan.addClassName("secondary-text");

        return new WrapperCard("wrapper", new Component[]{titleSpan, h2, descriptionSpan}, "card", "space-m");
    }

    private void populateCharts() {
        // Set some data when this view is displayed.

        // Top row widgets
        usersH2.setText("745");
        eventsH2.setText("54.6k");
        conversionH2.setText("15%");

        // First chart
        Configuration configuration = monthlyVisitors.getConfiguration();
        configuration.addSeries(new ListSeries("Tested", 156, 204, 1002, 2315, 5891, 6701, 6526, 5845, 7604,
                8491, 6596, 9604));
        configuration.addSeries(
                new ListSeries("Active", 3, 14, 98, 133, 342, 845, 1050, 1393, 912, 1835, 1066, 923));
        configuration.addSeries(
                new ListSeries("Recover", 1, 2, 7, 19, 107, 483, 900, 2096, 5524, 3652, 5903, 4120));
        configuration.addSeries(
                new ListSeries("Death",0, 0, 1, 7, 6, 12, 23, 26, 17, 39, 46, 51));

        XAxis x = new XAxis();
        x.setCrosshair(new Crosshair());
        x.setCategories( "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec","Jan", "Feb", "Mar", "Apr");
        configuration.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        configuration.addyAxis(y);

        // Grid
        List<HealthGridItem> gridItems = new ArrayList<>();
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 3, 19), "Anna", "Germany", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 1, 16), "Denke", "Romania", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 2, 20), "Cada Victoria", "Mexico", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 2, 27), "Ebsu", "Japan", "Excellent", "badge success"));
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 4, 1), "Snno Bernardo do Campo", "Brazil", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 3, 24), "Mato", "Mozambique", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 4, 10), "Warsaw", "Poland", "Good", "badge"));
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 3, 12), "Kasa", "Japan", "Bad", "badge error"));
        gridItems.add(new HealthGridItem(LocalDate.of(2021, 3, 23), "Vava", "United States", "Excellent","badge success"));

        grid.setItems(gridItems);

        // Second chart
        configuration = responseTimes.getConfiguration();
        configuration
                .addSeries(new ListSeries("Total Caces", 2, 19, 295, 450, 802, 1215, 2532, 3659, 4233, 10023, 14139, 19698));
        configuration
                .addSeries(new ListSeries("Total Death", 0, 0, 2, 8, 11, 15, 17, 23, 43, 109, 130, 48));

        x = new XAxis();
        x.setCrosshair(new Crosshair());
        x.setCategories("May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec","Jan", "Feb", "Mar", "Apr" );
        configuration.addxAxis(x);

        y = new YAxis();
        y.setMin(0);
        configuration.addyAxis(y);
    }
}
