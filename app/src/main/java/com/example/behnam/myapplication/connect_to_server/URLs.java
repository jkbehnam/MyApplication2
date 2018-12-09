package com.example.behnam.myapplication.connect_to_server;

/**
 * Created by User on 2/4/2018.
 */

public class URLs {
  //  public static final String ROOT_URL = "http://185.105.239.235:1010";
  public static final String ROOT_URL = "http://145.239.165.130:3030";
    //public static final String URL_REGISTER = ROOT_URL + "api.php?apicall=signup";
    public static final String URL_LOGIN= ROOT_URL + "/app/user/login";
  public static final String URL_LOG= ROOT_URL + "/app/log";
  public static final String URL_exerciseDuration= ROOT_URL + "/app/alarm";
    public static final String URL_IDENTITY= ROOT_URL + "/app/user/identity";
    public static final String URL_SEND_DAILY_DATA= ROOT_URL + "/app/healthIndex";
    public static final String URL_GET_DRUG_DATA= ROOT_URL + "/app/drugs";
    public static final String URL_GET_MAX_HEART= ROOT_URL + "/app/exercises";
    public static final String URL_GET_MAX_HEART_DETAIL= ROOT_URL + "/app/exercise/";
    public static final String URL_GET_RISK_FACTORS= ROOT_URL + "/app/user/riskFactors";
    public static final String URL_GET_CHATMESSAGES= ROOT_URL + "/app/message";
  public static final String URL_GET_MESSAGEREAD= ROOT_URL + "/app/message/read";
  public static final String URL_GetHeartBeatBrief= ROOT_URL + "/app/user/brief";
  public static final String URL_SEND_CHATMESSAGES= ROOT_URL + "/app/message";
    public static final String URL_GET_DRUG_DATA_DETAILS= ROOT_URL + "/app/drug/patient/";
    public static final String URL_SEND_SIGN_DATA= ROOT_URL + "/app/symptom";
    public static final String URL_SEND_BAND_DATA= ROOT_URL + "/app/healthIndex/heartStep";
    public static final String URL_SEND_EXERCISE= ROOT_URL + "/app/sport";
    public static final String URL_AIRPOLLUTION="http://185.105.239.235:1010/app/airPollution";
   // public static final String URL_EXCEL= ROOT_URL + "api.php?apicall=excelCheck";
   // public static final String URL_GET_EXCEL= ROOT_URL + "api.php?apicall=getExcel";
}
