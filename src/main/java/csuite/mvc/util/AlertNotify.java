package csuite.mvc.util;

import csuite.mvc.entidades.Mercado;
import csuite.mvc.servicios.AlmacenServicios;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlertNotify extends Thread{
    private static AlertNotify instancia;
    private static int port;
    private static boolean error = false;
    private static boolean parar = false;




    public static AlertNotify getInstancia(){
        if(instancia==null){
            instancia = new AlertNotify();
        }
        return instancia;
    }
    public static void StopProcess() {
        AlertNotify.parar = true;
    }
    public static void StartProcess() {
        AlertNotify.parar = false;
    }

    public static boolean isParar() {
        return parar;
    }

    public static void setParar(boolean parar) {
        AlertNotify.parar = parar;
    }

    public static void setInstancia(AlertNotify instancia) {
        AlertNotify.instancia = instancia;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        AlertNotify.port = port;
    }

    public static boolean isError() {
        return error;
    }

    public static void setError(boolean error) {
        AlertNotify.error = error;
    }

    private String getMensajeError(List<JsonError> jsonErrors, List<JsonError> jsonErrors2){


        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang='en' style='box-sizing: border-box;'>\n" +
                "<head style='box-sizing: border-box;'>\n" +
                "    <meta charset='utf-8' style='box-sizing: border-box;'>\n" +
                "    <title style='box-sizing: border-box;'></title>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no' style='box-sizing: border-box;'>\n" +
                "    <meta name='description' content='' style='box-sizing: border-box;'>\n" +
                "    <meta name='author' content='Mark Otto, Jacob Thornton, and Bootstrap contributors' style='box-sizing: border-box;'>\n" +
                "    <meta name='generator' content='Jekyll v4.0.1' style='box-sizing: border-box;'>\n" +
                "    <!-- Place favicon.ico in the root directory -->\n" +
                "\n" +
                "    <!--<link rel='stylesheet' href='bootstrap-4.5.2-dist/css/bootstrap.css1'>-->\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <style style='box-sizing: border-box;'>\n" +
                "        /*! CSS Used from: Embedded */\n" +
                "        .container{max-width:960px;}\n" +
                "        .card-deck .card{min-width:220px;}\n" +
                "        /*! CSS Used from: Embedded */\n" +
                "        *,*::before,*::after{box-sizing:border-box;}\n" +
                "        body{margin:0;font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';font-size:1rem;font-weight:400;line-height:1.5;color:#212529;text-align:left;background-color:#fff;}\n" +
                "        h4,h5{margin-top:0;margin-bottom:0.5rem;}\n" +
                "        p{margin-top:0;margin-bottom:1rem;}\n" +
                "        a{color:#007bff;text-decoration:none;background-color:transparent;}\n" +
                "        a:hover{color:#0056b3;text-decoration:underline;}\n" +
                "        a:not([href]):not([class]){color:inherit;text-decoration:none;}\n" +
                "        a:not([href]):not([class]):hover{color:inherit;text-decoration:none;}\n" +
                "        img{vertical-align:middle;border-style:none;}\n" +
                "        h4,h5{margin-bottom:0.5rem;font-weight:500;line-height:1.2;}\n" +
                "        h4{font-size:1.5rem;}\n" +
                "        h5{font-size:1.25rem;}\n" +
                "        .lead{font-size:1.25rem;font-weight:300;}\n" +
                "        .container{width:100%;padding-right:15px;padding-left:15px;margin-right:auto;margin-left:auto;}\n" +
                "        @media (min-width: 576px){\n" +
                "            .container{max-width:540px;}\n" +
                "        }\n" +
                "        @media (min-width: 768px){\n" +
                "            .container{max-width:720px;}\n" +
                "        }\n" +
                "        @media (min-width: 992px){\n" +
                "            .container{max-width:960px;}\n" +
                "        }\n" +
                "        @media (min-width: 1200px){\n" +
                "            .container{max-width:1140px;}\n" +
                "        }\n" +
                "        .row{display:-ms-flexbox;display:flex;-ms-flex-wrap:wrap;flex-wrap:wrap;margin-right:-15px;margin-left:-15px;}\n" +
                "        .col-4,.col{position:relative;width:100%;padding-right:15px;padding-left:15px;}\n" +
                "        .col{-ms-flex-preferred-size:0;flex-basis:0;-ms-flex-positive:1;flex-grow:1;max-width:100%;}\n" +
                "        .col-4{-ms-flex:0 0 33.333333%;flex:0 0 33.333333%;max-width:33.333333%;}\n" +
                "        .btn-danger{color:#fff;background-color:#dc3545;border-color:#dc3545;}\n" +
                "        .btn-danger:hover{color:#fff;background-color:#c82333;border-color:#bd2130;}\n" +
                "        .btn-danger:focus{color:#fff;background-color:#c82333;border-color:#bd2130;box-shadow:0 0 0 0.2rem rgba(225, 83, 97, 0.5);}\n" +
                "        .btn-danger:disabled{color:#fff;background-color:#dc3545;border-color:#dc3545;}\n" +
                "        .collapse:not(.show){display:none;}\n" +
                "        .navbar{position:relative;display:-ms-flexbox;display:flex;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-align:center;align-items:center;-ms-flex-pack:justify;justify-content:space-between;padding:0.5rem 1rem;}\n" +
                "        .navbar-brand{display:inline-block;padding-top:0.3125rem;padding-bottom:0.3125rem;margin-right:1rem;font-size:1.25rem;line-height:inherit;white-space:nowrap;}\n" +
                "        .navbar-brand:hover,.navbar-brand:focus{text-decoration:none;}\n" +
                "        .navbar-collapse{-ms-flex-preferred-size:100%;flex-basis:100%;-ms-flex-positive:1;flex-grow:1;-ms-flex-align:center;align-items:center;}\n" +
                "        @media (min-width: 768px){\n" +
                "            .navbar-expand-md{-ms-flex-flow:row nowrap;flex-flow:row nowrap;-ms-flex-pack:start;justify-content:flex-start;}\n" +
                "            .navbar-expand-md .navbar-collapse{display:-ms-flexbox!important;display:flex!important;-ms-flex-preferred-size:auto;flex-basis:auto;}\n" +
                "        }\n" +
                "        .navbar-dark .navbar-brand{color:#fff;}\n" +
                "        .navbar-dark .navbar-brand:hover,.navbar-dark .navbar-brand:focus{color:#fff;}\n" +
                "        .card{position:relative;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;min-width:0;word-wrap:break-word;background-color:#fff;background-clip:border-box;border:1px solid rgba(0, 0, 0, 0.125);border-radius:0.25rem;}\n" +
                "        .card-body{-ms-flex:1 1 auto;flex:1 1 auto;min-height:1px;padding:1.25rem;}\n" +
                "        .card-title{margin-bottom:0.75rem;}\n" +
                "        .card-subtitle{margin-top:-0.375rem;margin-bottom:0;}\n" +
                "        .card-text:last-child{margin-bottom:0;}\n" +
                "        .card-header{padding:0.75rem 1.25rem;margin-bottom:0;background-color:rgba(0, 0, 0, 0.03);border-bottom:1px solid rgba(0, 0, 0, 0.125);}\n" +
                "        .card-header:first-child{border-radius:calc(0.25rem - 1px) calc(0.25rem - 1px) 0 0;}\n" +
                "        .card-deck .card{margin-bottom:15px;}\n" +
                "        @media (min-width: 576px){\n" +
                "            .card-deck{display:-ms-flexbox;display:flex;-ms-flex-flow:row wrap;flex-flow:row wrap;margin-right:-15px;margin-left:-15px;}\n" +
                "            .card-deck .card{-ms-flex:1 0 0%;flex:1 0 0%;margin-right:15px;margin-bottom:0;margin-left:15px;}\n" +
                "        }\n" +
                "        .jumbotron{padding:2rem 1rem;margin-bottom:2rem;background-color:#e9ecef;border-radius:0.3rem;}\n" +
                "        @media (min-width: 576px){\n" +
                "            .jumbotron{padding:4rem 2rem;}\n" +
                "        }\n" +
                "        .justify-content-start{-ms-flex-pack:start!important;justify-content:flex-start!important;}\n" +
                "        .fixed-top{position:fixed;top:0;right:0;left:0;z-index:1030;}\n" +
                "        .shadow-sm{box-shadow:0 0.125rem 0.25rem rgba(0, 0, 0, 0.075)!important;}\n" +
                "        .my-0{margin-top:0!important;}\n" +
                "        .my-0{margin-bottom:0!important;}\n" +
                "        .mb-2{margin-bottom:0.5rem!important;}\n" +
                "        .mb-3{margin-bottom:1rem!important;}\n" +
                "        .mb-4{margin-bottom:1.5rem!important;}\n" +
                "        .text-left{text-align:left!important;}\n" +
                "        .text-center{text-align:center!important;}\n" +
                "        .font-weight-normal{font-weight:400!important;}\n" +
                "        .text-muted{color:#6c757d!important;}\n" +
                "        @media print{\n" +
                "            *,*::before,*::after{text-shadow:none!important;box-shadow:none!important;}\n" +
                "            a:not(.btn){text-decoration:underline;}\n" +
                "            img{page-break-inside:avoid;}\n" +
                "            p{orphans:3;widows:3;}\n" +
                "            body{min-width:992px!important;}\n" +
                "            .container{min-width:992px!important;}\n" +
                "            .navbar{display:none;}\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "<body style='min-height: 75rem;padding-top: 4.5rem;box-sizing: border-box;margin: 0;font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;;font-size: 1rem;font-weight: 400;line-height: 1.5;color: #212529;text-align: left;background-color: #fff;'>\n" +
                "\n" +
                "<div class='navbar navbar-expand-md navbar-dark fixed-top ' style='background-color: #97ddee !important;box-sizing: border-box;'>\n" +
                "  <span class='navbar-brand' style='box-sizing: border-box;'>\n" +
                "    <img class='sp-default-logo' src='https://app1.goniometer-exoglove.me/img/Xtreme%20Sport%20Channel%20Logo.gif' alt='Televiaducto' width='160' style='box-sizing: border-box;vertical-align: middle;border-style: none;'>\n" +
                "  </span>\n" +
                "\n" +
                "    <div class='collapse navbar-collapse' id='navbarCollapse' style='box-sizing: border-box;'>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "<br style='box-sizing: border-box;'>\n" +
                "<div role='main' class='container' style='box-sizing: border-box;max-width: 960px;width: 100%;padding-right: 15px;padding-left: 15px;margin-right: auto;margin-left: auto;'>\n" +
                "\n" +
                "\n" +
                "    <div class='jumbotron' style='box-sizing: border-box;'>\n" +
                "\n" +
                "        <div class='card' style='box-sizing: border-box;'>\n" +
                "            <div class='card-body' style='box-sizing: border-box;'>\n" +
                "                <h4 class='card-title' style='box-sizing: border-box;margin-top: 0;margin-bottom: 0.5rem;font-weight: 500;line-height: 1.2;font-size: 1.5rem;'>Alert</h4>\n" +
                "                <h5 class='card-subtitle mb-2 text-muted' style='box-sizing: border-box;margin-top: 0;margin-bottom: 0.5rem;font-weight: 500;line-height: 1.2;font-size: 1.25rem;'>Error en el servidor</h5>\n" +
                "\n" +
                "                <div class='row text-left' style='box-sizing: border-box;'>\n" +
                "                    <div class='col-4' style='box-sizing: border-box;'>\n" +
                "                        <h5 class='card-text' style='box-sizing: border-box;margin-top: 0;margin-bottom: 0.5rem;font-weight: 500;line-height: 1.2;font-size: 1.25rem;'>Severity: </h5>\n" +
                "                    </div>\n" +
                "                    <div class='col' style='box-sizing: border-box;'>\n" +
                "                        <p class='card-text lead btn-danger' style='box-sizing: border-box;margin-top: 0;margin-bottom: 1rem;font-size: 1.25rem;font-weight: 300;'>Critical</p>\n" +
                "                    </div>\n" +
                "                </div>\n" );

        for (JsonError jsonError : jsonErrors
        ){
            sb.append(                "                <div class='row text-left' style='box-sizing: border-box;'>\n" +
                    "                    <div class='col-4' style='box-sizing: border-box;'>\n" +
                    "                        <h5 class='card-text' style='box-sizing: border-box;margin-top: 0;margin-bottom: 0.5rem;font-weight: 500;line-height: 1.2;font-size: 1.25rem;'>"+jsonError.getKey()+"</h5>\n" +
                    "                    </div>\n" +
                    "                    <div class='col' style='box-sizing: border-box;'>\n" +
                    "                        <p class='card-text lead' style='box-sizing: border-box;margin-top: 0;margin-bottom: 1rem;font-size: 1.25rem;font-weight: 300;'>" +
                    jsonError.getValue()+
                    "</p>\n" +
                    "                    </div>\n" +
                    "                </div>\n" );
        }
        sb.append(          "                <div class='row text-left' style='box-sizing: border-box;'>\n" +
                "                    <div class='col-4' style='box-sizing: border-box;'>\n" +
                "                        <h5 class='card-text' style='box-sizing: border-box;margin-top: 0;margin-bottom: 0.5rem;font-weight: 500;line-height: 1.2;font-size: 1.25rem;'>Timestamp: </h5>\n" +
                "                    </div>\n" +
                "                    <div class='col' style='box-sizing: border-box;'>\n" +
                "                        <p class='card-text lead' style='box-sizing: border-box;margin-top: 0;margin-bottom: 1rem;font-size: 1.25rem;font-weight: 300;'>" +
                formatter.format(date).toString()+
                "</p>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "\n" +
                "\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <br style='box-sizing: border-box;'>\n"
                );
        for (JsonError error1 : jsonErrors2
        ){
            sb.append(                "        <div class='' style='box-sizing: border-box;'>\n" +
                    "            <div class='card-deck mb-3 text-center' style='box-sizing: border-box;'>\n" +
                    "\n" +
                    "                <div class='card mb-4 shadow-sm' style='box-sizing: border-box;min-width: 220px;'>\n" +
                    "                    <div class='card-header' style='box-sizing: border-box;'>\n" +
                    "                        <h4 class='my-0 font-weight-normal text-left' style='box-sizing: border-box;margin-top: 0;margin-bottom: 0.5rem;font-weight: 500;line-height: 1.2;font-size: 1.5rem;'>" +
                    //exception.getClass().getName() +
                    error1.getKey()+
                    "<a style='box-sizing: border-box;color: inherit;text-decoration: none;background-color: transparent;'></a></h4>\n" +
                    "                    </div>\n" +
                    "                    <div class='card-body ' style='box-sizing: border-box;'>\n" +
                    "                        <div class='justify-content-start' style='box-sizing: border-box;'>\n" +
                    "\n" +
                    "                            <div class='row text-left' style='box-sizing: border-box;'>\n" +
                    "                                <div class='col' style='box-sizing: border-box;'>\n" +
                    "                                    <p class='card-text' style='box-sizing: border-box;margin-top: 0;margin-bottom: 1rem;'>" +
                    //sw.toString() +
                            error1.getValue()+
                    "</p>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "\n" +
                    "\n" +
                    "                    </div>\n" +
                    "\n" +
                    "                </div>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </div>\n" );
        }

        sb.append("        </div>\n" +
                "\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
        return sb.toString();








    }

    @Override
    public void run() {
        System.out.println("System alert Start");
        Mercado.getInstance().send_correo_online("johncarlos1943@gmail.com","Sistema de alerta encendido","Notify server");
        Mercado.getInstance().send_correo_online("rm.dorville@gmail.com","Sistema de alerta encendido","Notify server");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL u = null;
        while (true){


            if (parar==false){
                try {
                    u = new URL( "http://localhost:80/");
                } catch (MalformedURLException e) {
                    error = true;
                    e.printStackTrace();
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    JsonError jsonError = new JsonError(e.getClass().getName(),sw.toString());
                    List<JsonError> jsonErrors = new ArrayList<JsonError>();
                    jsonErrors.add(jsonError);
                    Mercado.getInstance().send_correo_online("johncarlos1943@gmail.com",getMensajeError(new ArrayList<JsonError>(),jsonErrors),"Error del servidor");
                    Mercado.getInstance().send_correo_online("rm.dorville@gmail.com",getMensajeError(new ArrayList<JsonError>(),jsonErrors),"Error del servidor");
                }
                HttpURLConnection huc = null;
                try {
                    huc = (HttpURLConnection)  u.openConnection ();
                    huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
                    huc.connect () ;
                    int code = huc.getResponseCode() ;
                    if(code>399 && error == false){
                        error = true;
                        JsonError jsonError = new JsonError("Error code",Integer.toString(code));
                        List<JsonError> jsonErrors = new ArrayList<JsonError>();
                        jsonErrors.add(jsonError);
                        Mercado.getInstance().send_correo_online("johncarlos1943@gmail.com",getMensajeError(jsonErrors,new ArrayList<JsonError>()),"Error del servidor");
                        Mercado.getInstance().send_correo_online("rm.dorville@gmail.com",getMensajeError(jsonErrors,new ArrayList<JsonError>()),"Error del servidor");
                    }else{
                        error=false;
                    }
                } catch (IOException e) {
                    error = true;
                    e.printStackTrace();
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    JsonError jsonError = new JsonError(e.getClass().getName(),sw.toString());
                    List<JsonError> jsonErrors = new ArrayList<JsonError>();
                    List<JsonError> jsonErrors12 = new ArrayList<JsonError>();
                    jsonErrors12.add(new JsonError("Type Error","404"));


                    jsonErrors.add(jsonError);
                    Mercado.getInstance().send_correo_online("johncarlos1943@gmail.com",getMensajeError(jsonErrors12,jsonErrors),"Error del servidor");
                    Mercado.getInstance().send_correo_online("rm.dorville@gmail.com",getMensajeError(jsonErrors12,jsonErrors),"Error del servidor");
                }
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }




    }
}
