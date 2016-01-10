package com.example.kp2101.journey0100;

import android.util.Log;
import android.view.View;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yu on 2016/1/6.
 */
public class JourneyDB {

    static DBManager dbManager = new DBManager();
    View view;




    public JourneyDB(){
    }

    public static String addJourney(String jName,String uId){
        String sql = "INSERT INTO journey (jName) VALUES (\""+jName+"\");";
        String id = dbManager.DBexecuteUpdate(sql);
        Log.d("addJourney jId",id);
        String sql2 = "INSERT INTO userjourney (jId,uId) VALUES ("+id+","+uId+");";
        Log.d("addJourney sql2",sql2);
        dbManager.DBexecuteUpdate(sql2);
        return id;

    }

    public static void deleteJourney(String jId){
        String sql = "DELETE FROM journey WHERE jId=\""+jId+"\"";
        dbManager.DBexecuteUpdate(sql);
    }

    public static List<Journey> journeyList(String uId){
        String sql="SELECT * FROM userjourney  NATURAL JOIN  journey where uId=\""+uId+"\"";
        Log.d("journeyList sql",sql);
        ResultSet resultSet = dbManager.DBexecute(sql);

        List<Journey> journeyList = new ArrayList<Journey>();
        try {
            while (resultSet.next()){
                Journey journey = new Journey(resultSet.getString("jId"), resultSet.getString("jName"), resultSet.getString("jPic"), resultSet.getString("ujMoney"));
                journeyList.add(journey);
            }
            //dbManager.statement.close();
            //resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return journeyList;

    }
}
