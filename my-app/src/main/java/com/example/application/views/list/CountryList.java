package com.example.application.views.list;


import java.text.Collator;
import java.util.*;
import com.example.application.views.list.CountryList.Country;

import com.example.application.views.list.CountryList.CountryComparator;

public class CountryList {
    public List<Country> getCountryList() {
        // A collection to store our country object
        List<Country> countries = new ArrayList<Country>();
        Locale.setDefault(Locale.US);  
        // Get ISO countries, create Country object and
        // store in the collection.
        String[] isoCountries = Locale.getISOCountries();
        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
        
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();
            
            if (!"".equals(code) && !"".equals(name)) {
                countries.add(new Country(code, name));
            }
        }

        // Sort the country by their name and then display the content
        // of countries collection object.
        Collections.sort(countries, new CountryComparator());
     
        
        return countries;
    }

    /**
     * Country pojo class.
     */
    static class Country {
      
        private String code;
        private String name;

        Country(String code, String name) {
        
            this.code = code;
            this.name = name;
        }

        public String toString() {
            return code;
        }
        public String tonameString() {
            return name;
        }
    }

    /**
     * CountryComparator class.
     */
    public static class CountryComparator implements Comparator<Country> {
        private Comparator<Object> comparator;

        CountryComparator() {
            comparator = Collator.getInstance();
        }

        public int compare(Country c1, Country c2) {
            return comparator.compare(c1.name, c2.name);
        }
    }
}