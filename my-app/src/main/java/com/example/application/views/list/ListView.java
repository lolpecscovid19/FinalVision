package com.example.application.views.list;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import com.example.application.views.list.CountryList.Country;
import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

import com.example.application.views.list.CountryList;


import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "list", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("List")
@CssImport("./views/list/list-view.css")
public class ListView extends Div {

    private GridPro<Client> grid;
    private ListDataProvider<Client> dataProvider;

    private Grid.Column<Client> idColumn;
    private Grid.Column<Client> clientColumn;
    private Grid.Column<Client> amountColumn;
    private Grid.Column<Client> statusColumn;
    private Grid.Column<Client> dateColumn;

    public ListView() {
        addClassName("list-view");
        setSizeFull();
        createGrid();
        add(grid);
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        addFiltersToGrid();
    }

    private void createGridComponent() {
        grid = new GridPro<>();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        dataProvider = new ListDataProvider<>(getClients());
        grid.setDataProvider(dataProvider);
    }

    private void addColumnsToGrid() {
        createIdColumn();
        createClientColumn();
        createAmountColumn();
        createStatusColumn();
        createDateColumn();
    }

    private void createIdColumn() {
        idColumn = grid.addColumn(Client::getId, "id").setHeader("ID").setWidth("120px").setFlexGrow(0);
    }

    private void createClientColumn() {
        clientColumn = grid.addColumn(new ComponentRenderer<>(client -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(Alignment.CENTER);
            Image img = new Image(client.getImg(), "");
            Span span = new Span();
            span.setClassName("name");
            span.setText(client.getClient());
            hl.add(img, span);
            return hl;
        })).setComparator(client -> client.getClient()).setHeader("Name");
    }

    private void createAmountColumn() {
        amountColumn = grid
                .addEditColumn(Client::getAmount,
                        new NumberRenderer<>(client -> client.getAmount(), NumberFormat.getCurrencyInstance(Locale.US)))
                .text((item, newValue) -> item.setAmount(Double.parseDouble(newValue)))
                .setComparator(client -> client.getAmount()).setHeader("Amount");
    }

    private void createStatusColumn() {
        statusColumn = grid.addEditColumn(Client::getClient, new ComponentRenderer<>(client -> {
            Span span = new Span();
            span.setText(client.getStatus());
            span.getElement().setAttribute("theme", "badge " + client.getStatus().toLowerCase());
            return span;
        })).select((item, newValue) -> item.setStatus(newValue), Arrays.asList("Pending", "Success", "Error"))
                .setComparator(client -> client.getStatus()).setHeader("Status");
    }

    private void createDateColumn() {
        dateColumn = grid
                .addColumn(new LocalDateRenderer<>(client -> LocalDate.parse(client.getDate()),
                        DateTimeFormatter.ofPattern("M/d/yyyy")))
                .setComparator(client -> client.getDate()).setHeader("Date").setWidth("180px").setFlexGrow(0);
    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField idFilter = new TextField();
        idFilter.setPlaceholder("Filter");
        idFilter.setClearButtonVisible(true);
        idFilter.setWidth("100%");
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.addValueChangeListener(event -> dataProvider.addFilter(
                client -> StringUtils.containsIgnoreCase(Integer.toString(client.getId()), idFilter.getValue())));
        filterRow.getCell(idColumn).setComponent(idFilter);

        TextField clientFilter = new TextField();
        clientFilter.setPlaceholder("Filter");
        clientFilter.setClearButtonVisible(true);
        clientFilter.setWidth("100%");
        clientFilter.setValueChangeMode(ValueChangeMode.EAGER);
        clientFilter.addValueChangeListener(event -> dataProvider
                .addFilter(client -> StringUtils.containsIgnoreCase(client.getClient(), clientFilter.getValue())));
        filterRow.getCell(clientColumn).setComponent(clientFilter);

        TextField amountFilter = new TextField();
        amountFilter.setPlaceholder("Filter");
        amountFilter.setClearButtonVisible(true);
        amountFilter.setWidth("100%");
        amountFilter.setValueChangeMode(ValueChangeMode.EAGER);
        amountFilter.addValueChangeListener(event -> dataProvider.addFilter(client -> StringUtils
                .containsIgnoreCase(Double.toString(client.getAmount()), amountFilter.getValue())));
        filterRow.getCell(amountColumn).setComponent(amountFilter);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems(Arrays.asList("Pending", "Success", "Error"));
        statusFilter.setPlaceholder("Filter");
        statusFilter.setClearButtonVisible(true);
        statusFilter.setWidth("100%");
        statusFilter.addValueChangeListener(
                event -> dataProvider.addFilter(client -> areStatusesEqual(client, statusFilter)));
        filterRow.getCell(statusColumn).setComponent(statusFilter);

        DatePicker dateFilter = new DatePicker();
        dateFilter.setPlaceholder("Filter");
        dateFilter.setClearButtonVisible(true);
        dateFilter.setWidth("100%");
        dateFilter.addValueChangeListener(event -> dataProvider.addFilter(client -> areDatesEqual(client, dateFilter)));
        filterRow.getCell(dateColumn).setComponent(dateFilter);
    }

    private boolean areStatusesEqual(Client client, ComboBox<String> statusFilter) {
        String statusFilterValue = statusFilter.getValue();
        if (statusFilterValue != null) {
            return StringUtils.equals(client.getStatus(), statusFilterValue);
        }
        return true;
    }

    private boolean areDatesEqual(Client client, DatePicker dateFilter) {
        LocalDate dateFilterValue = dateFilter.getValue();
        if (dateFilterValue != null) {
            LocalDate clientDate = LocalDate.parse(client.getDate());
            return dateFilterValue.equals(clientDate);
        }
        return true;
    }

    private List<Client> getClients() {
    	List<Client> ClienteNams = new ArrayList<Client>();
    	CountryList gcon = new CountryList();
		List<Country> countries = new ArrayList<Country>();
		
		countries = gcon.getCountryList();
		int id = 0;
		 for (Country country : countries) {
			 String img = String.format("https://www.countryflags.io/%s/flat/64.png", country);
			 id ++;
			 ClienteNams.add(createClient(id, img, country.tonameString(), 47427.0,"Success", "2021-03-09"));
	        }
    	
        return ClienteNams;
    }

    private Client createClient(int id, String img, String client, double amount, String status, String date) {
        Client c = new Client();
        c.setId(id);
        c.setImg(img);
        c.setClient(client);
        c.setAmount(amount);
        c.setStatus(status);
        c.setDate(date);

        return c;
    }
};
