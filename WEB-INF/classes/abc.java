import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class abc{

private static final String POST_URL = "https://accounts-uat.paytm.com/signin/validate/otp";


public static String create_JSON(String otp, String state){

    StringBuffer json=new StringBuffer("");
    json.append("{\n");
    json.append("\"otp\" : \""+otp+"\",\n");
    json.append("\"state\" : \""+state+"\"\n");
    json.append("}");
    return json.toString();
    
/*
    {
"otp" : "972872",
"state" : "ea6ee203-554e-43f3-8607-4271f612291f"
}
*/

}


public static String sendPost(String otp, String state) throws IOException {  


        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Basic c3RhZ2luZy1ncm9mZXJzOjUxZTZkMDk2LTU2ZjYtNDBiNC1hMmI5LTllMGY4ZmE3MDRiOA==");
        //con.setHeader

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        String a = create_JSON(otp, state);
        os.write(a.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            return "POST request not worked";
        }
    }

public static void main(String[] args) {
    try{
        String a = sendPost("647027","cf11f8d9-c903-4cc4-a059-69d7ca36ad9a");
        System.out.println(a);
    }
    catch(Exception e){
        System.out.println(e);
    }
    
}

}