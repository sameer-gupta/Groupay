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

public class Pay1 extends HttpServlet {

    private String jdbcDriver   = "";
    private String jdbcURL      = "";
    private String jdbcUser     = "";
    private String jdbcPass     = "";
    private String homeURL      = "";


    private static final String POST_URL = "https://accounts-uat.paytm.com/signin/validate/otp";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();

        jdbcDriver  = context.getInitParameter("jdbcDriver");
        jdbcURL     = context.getInitParameter("jdbcURL");
        jdbcUser    = context.getInitParameter("jdbcUser");
        jdbcPass    = context.getInitParameter("jdbcPass");
        homeURL     = context.getInitParameter("homeURL");
    }



public static String create_JSON(String otp, String state){

    StringBuffer json=new StringBuffer("");
    json.append("{\n");
    json.append("\"otp\" : \""+otp+"\",\n");
    json.append("\"state\" : \""+state+"\"\n");
    json.append("}");
    return json.toString();
    
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


public static String proc(String a){


    if(a.contains("FAILURE")){
        return "-1";
    }
    else{
        int i = a.indexOf(':');
        int j = a.indexOf(',');
        return a.substring(i+2,j-1);
    }
}


public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
{
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String redir = request.getHeader("referer"); // Yes, with the legendary misspelling.

        String c1o = request.getParameter("c1o");
        String c1s = request.getParameter("c1s");
        String r1 = proc(sendPost(c1o, c1s));

        String c2o = request.getParameter("c2o");
        String c2s = request.getParameter("c2s");
        String r2 = proc(sendPost(c2o, c2s));


        out.print(r1+"|"+r2);


        out.close();
}

}
