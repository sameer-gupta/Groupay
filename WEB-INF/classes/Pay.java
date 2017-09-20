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

public class Pay extends HttpServlet {

    private String jdbcDriver   = "";
    private String jdbcURL      = "";
    private String jdbcUser     = "";
    private String jdbcPass     = "";
    private String homeURL      = "";


    private static final String POST_URL = "https://accounts-uat.paytm.com/signin/otp";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();

        jdbcDriver  = context.getInitParameter("jdbcDriver");
        jdbcURL     = context.getInitParameter("jdbcURL");
        jdbcUser    = context.getInitParameter("jdbcUser");
        jdbcPass    = context.getInitParameter("jdbcPass");
        homeURL     = context.getInitParameter("homeURL");
    }





public static String create_JSON(String number){

    StringBuffer json=new StringBuffer("");
    json.append("{\n");
    json.append("\"phone\":\""+number+"\",\n");
    json.append("\"clientId\":\"staging-grofers\",\n");
    json.append("\"scope\":\"wallet\",\n");
    json.append("\"responseType\":\"token\"\n");
    json.append("}");
    return json.toString();
}

public static String sendPost(String num) throws IOException {  


        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        //con.setHeader

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        String a = create_JSON(num);
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
    int b = a.lastIndexOf(':');
    return a.substring(b+2,a.length()-2);

}


public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
{
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String redir = request.getHeader("referer"); // Yes, with the legendary misspelling.
        


        String c1p = request.getParameter("c1p");
        String r1 = proc(sendPost(c1p));
        
        String c2p = request.getParameter("c2p");
        String r2 = proc(sendPost(c2p));

        out.print(r1+"|"+r2);


        out.close();
}

}
