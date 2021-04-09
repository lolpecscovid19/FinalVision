package com.example.application.views.list;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.application.views.list.CountryList.Country;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class CovidData {
	
	public void cratejoson() 
				 throws Exception {
				      URL url = new URL("https://api.covid19api.com/summary");
				      BufferedReader reader = new BufferedReader
				      (new InputStreamReader(url.openStream()));
				      BufferedWriter writer = new BufferedWriter
				      (new FileWriter("/data.json"));
				      String line;
				      while ((line = reader.readLine()) != null) {
				         System.out.println(line);
				         writer.write(line);
				         writer.newLine();
				      }
				      reader.close();
				      writer.close();
				     
				   }
	
	
	
	public String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	static class Country {
	      
        private String code;
        private String name;
        private int NewConfirmed;
        private int TotalConfirmed;
        private int NewDeaths;
        private int TotalDeaths;
        private int NewRecovered;
        private int TotalRecovered;
        private String dateString;
        Country(String code, String name,int NewConfirmed,int TotalConfirmed,int NewDeaths,int TotalDeaths,int NewRecovered,int TotalRecovered,String dateString) {
        
            this.code = code;
            this.name = name;
            this.setNewConfirmed(NewConfirmed);
            this.setNewDeaths(NewDeaths);
            this.setNewConfirmed(NewConfirmed);
            this.setTotalConfirmed(TotalConfirmed);
            this.setTotalDeaths(TotalDeaths);
            this.setDateString(dateString);
            this.setTotalRecovered(TotalRecovered);
        }

        public String toString() {
            return code;
        }
        public String tonameString() {
            return name;
        }

		public int getNewConfirmed() {
			return NewConfirmed;
		}

		public void setNewConfirmed(int newConfirmed) {
			NewConfirmed = newConfirmed;
		}

		public int getTotalRecovered() {
			return TotalRecovered;
		}

		public void setTotalRecovered(int totalRecovered) {
			TotalRecovered = totalRecovered;
		}

		public int getTotalConfirmed() {
			return TotalConfirmed;
		}

		public void setTotalConfirmed(int totalConfirmed) {
			TotalConfirmed = totalConfirmed;
		}

		public int getTotalDeaths() {
			return TotalDeaths;
		}

		public void setTotalDeaths(int totalDeaths) {
			TotalDeaths = totalDeaths;
		}

		public int getNewRecovered() {
			return NewRecovered;
		}

		public void setNewRecovered(int newRecovered) {
			NewRecovered = newRecovered;
		}

		public int getNewDeaths() {
			return NewDeaths;
		}

		public void setNewDeaths(int newDeaths) {
			NewDeaths = newDeaths;
		}

		public String getDateString() {
			return dateString;
		}

		public void setDateString(String dateString) {
			this.dateString = dateString;
		}
     
    }
	
	public List<Country> GetCountryName() throws  Exception{
		cratejoson();
		//String path = CovidData.class.getClassLoader().getResource("/data.json").getPath();
	
		String content = readJsonFile("./data.json");
		
		JSONObject jobj = JSON.parseObject(content);
		 
		
        JSONArray links = jobj.getJSONArray("Countries");
        List<Country> countries = new ArrayList<Country>();
        
        for (int i = 0 ; i < links.size();i++){
        	
            JSONObject key1 = (JSONObject)links.get(i);
            String code = (String)key1.get("CountryCode");
            String name = (String)key1.get("Country");
            String date = ((String)key1.get("Date")).substring(0,10);
            int NewConfirmed = (int)key1.get("NewConfirmed");
            int TotalConfirmed = (int)key1.get("TotalConfirmed");
            int NewDeaths = (int)key1.get("NewDeaths");
            int TotalDeaths = (int)key1.get("TotalDeaths");
            int NewRecovered = (int)key1.get("NewRecovered");
            int TotalRecovered = (int)key1.get("TotalRecovered");

            countries.add(new Country(code, name, NewConfirmed, TotalConfirmed, NewDeaths, TotalDeaths, NewRecovered, TotalRecovered,date));
            
            
        }
		return countries;

	}
}
