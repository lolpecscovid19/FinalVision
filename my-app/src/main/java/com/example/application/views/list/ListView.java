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
@PageTitle("Country Covid_data")
@CssImport("./views/list/list-view.css")
public class ListView extends Div {

    private GridPro<Client> grid;
    private ListDataProvider<Client> dataProvider;

    private Grid.Column<Client> idColumn;
    private Grid.Column<Client> clientColumn;
    private Grid.Column<Client> amountColumn;
    private Grid.Column<Client> statusColumn;
    private Grid.Column<Client> dateColumn;
    private Grid.Column<Client> deathColum;
    private Grid.Column<Client> recoverColumn;
    private Grid.Column<Client> vaccineColum;
    

    public ListView() {
        addClassName("list-view");
        setSizeFull();
        createGrid();
        add(grid);
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        //addFiltersToGrid();
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
        createdeathColumn();
        createAmountColumn();
        createrecoverColumn();
        creatVvaccineColumn();
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
        amountColumn = grid.addColumn(Client::getTotal, "amount").setHeader("Cases").setWidth("120px").setFlexGrow(0);  }
   
    private void creatVvaccineColumn() {
    	vaccineColum = grid.addColumn(Client::getVaccine, "amount").setHeader("Vaccination").setWidth("140px").setFlexGrow(0);  }
   
    
    private void createdeathColumn() {
    	deathColum = grid.addColumn(Client::getDeath, "death").setHeader("Death").setWidth("120px").setFlexGrow(0);  }
   
    private void createrecoverColumn() {
    	recoverColumn = grid.addColumn(Client::getRecover, "death").setHeader("Recovery").setWidth("120px").setFlexGrow(0);  }
   
  

    private void createDateColumn() {
        dateColumn = grid
                .addColumn(new LocalDateRenderer<>(client -> LocalDate.parse(client.getDate()),
                        DateTimeFormatter.ofPattern("M/d/yyyy")))
                .setComparator(client -> client.getDate()).setHeader("Date").setWidth("180px").setFlexGrow(0);
    }



 
    private List<Client> getClients() {
    	List<Client> ClienteNams = new ArrayList<Client>();
    	
    	CountryList gcon = new CountryList();
		List<Country> countries = new ArrayList<Country>();
		int aString = 0;
		CovidData covid = new CovidData();
		try {
		 aString = covid.GetCountryName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			aString = 0;
		}
		
		countries = gcon.getCountryList();
		int id = 0;
		 for (Country country : countries) {
			 String img = String.format("https://www.countryflags.io/%s/flat/64.png", country);
			 id ++;
			 ClienteNams.add(createClient(id, img, country.tonameString(), aString,47427+id,id+100, "2021-03-09"));
	        }
    	
        return ClienteNams;
    }

    private Client createClient(int id, String img, String client,int death, int amount, int recover ,String date) {
        Client c = new Client();
        c.setId(id);
        c.setRecover(recover);
        c.setImg(img);
        c.setDeath(death);
        c.setClient(client);
        c.setTotal(amount);
   
        c.setDate(date);

        return c;
    }
};
