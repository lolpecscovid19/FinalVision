package com.example.application.views.list;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.text.AbstractDocument.Content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CovidData {
	
	
	
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
	
	
	public int GetCountryName() throws  Exception{
		String path = "D:/java/SecondYear/javaweb/my-app/sojson.com.json";
		String content = readJsonFile(path);
		
		JSONObject jobj = JSON.parseObject(content);
		JSONObject global = jobj.getJSONObject("Global");
        int global1 = (int)global.get("NewConfirmed");
		
		return global1;

	}
}
