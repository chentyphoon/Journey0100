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
public class ConsumeDB {

    static DBManager dbManager = new DBManager();
    View view;




    public ConsumeDB(){
    }

    public static void addConsume(String jId,String cName,String cDollar){
        String sql = "INSERT INTO consume (cName,cDollar) VALUES (\""+cName+"\",\""+cDollar+"\");";
        Log.d("addConsume",sql);
        String id = dbManager.DBexecuteUpdate(sql);

        String sql2 = "INSERT INTO journeyconsume (jid,cid) VALUES (\""+jId+"\",\""+id+"\");";
        Log.d("addConsume",sql2);
        dbManager.DBexecuteUpdate(sql2);

        //String sql4 = "INSERT INTO userjourney (jId,uId) VALUES ("+id+","+uId+");";
        //Log.d("addJourney sql2",sql2);
        //dbManager.DBexecuteUpdate(sql2);

    }

    public static void deleteJourney(String jId){
        String sql = "DELETE FROM journey WHERE jId=\""+jId+"\"";
        dbManager.DBexecuteUpdate(sql);
    }

    public static List<Consume> consumeList(String jId){
        String sql="SELECT * FROM journeyconsume  NATURAL JOIN consume where jId=\""+jId+"\"";
        Log.d("consumeList sql",sql);
        ResultSet resultSet = dbManager.DBexecute(sql);

        List<Consume> consumeList = new ArrayList<Consume>();
        try {
            while (resultSet.next()){
                Consume consume = new Consume(resultSet.getString("cId"), resultSet.getString("cName"), resultSet.getString("cDollar"));
                consumeList.add(consume);
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

        return consumeList;

    }
}
