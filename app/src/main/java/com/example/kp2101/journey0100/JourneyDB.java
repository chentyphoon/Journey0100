package com.example.kp2101.journey0100;

import android.view.View;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yu on 2016/1/6.
 */
public class JourneyDB {

    String add = "INSERT INTO journey (jName) VALUES ";
    String delete = "DELETE FROM journey WHERE jId=";
    String read = "SELECT * FROM journey ";
    DBManager dbManager = new DBManager();
    View view;

    public JourneyDB(){
    }

    public void addJourney(String jName){
        String sql = add+"(\""+jName+"\");";
        dbManager.DBexecuteUpdate(sql);
    }

    public void deleteJourney(String jId){
        String sql = delete+"\""+jId+"\"";
        dbManager.DBexecuteUpdate(sql);
    }

    public List<Journey> journeyList(){
        ResultSet resultSet = dbManager.DBexecute(read);

        List<Journey> journeyList = new ArrayList<Journey>();
        try {
            while (resultSet.next()){
                Journey journey = new Journey(resultSet.getString(1), resultSet.getString(2));
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
