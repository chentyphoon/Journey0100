package com.example.kp2101.journey0100;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Yu on 2016/1/6.
 */
public class DBManager {
    Connection connection;
    Statement statement;
    String userName = "journey";
    String passWord = "journey";

    public DBManager() {
        connection = null;
        statement = null;
    }

    public ResultSet DBexecute(String sql){

        ResultSet resultSet = null;
        try {
            StrictMode.ThreadPolicy policy=
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://140.112.107.30:3306/journey?useUnicode=yes&characterEncoding=UTF-8", userName, passWord);
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);


            //statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        //Log.d("resultset in DBexecute", resultSet.toString());

        return resultSet;
    }



    public String DBexecuteUpdate(String sql){
        int risultato=-1;
        String id=null;
        int n=0;

        try {
            StrictMode.ThreadPolicy policy=
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://140.112.107.30:3306/journey?useUnicode=yes&characterEncoding=UTF-8", userName, passWord);

            Statement statement = connection.createStatement();
            n = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()){
                risultato=resultSet.getInt(1);
            }
            id= Integer.toString(risultato);


            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
}
