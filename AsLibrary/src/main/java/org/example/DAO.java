package org.example;

import oracle.jdbc.OracleDriver;

import java.sql.*;
import java.util.Scanner;

public class DAO {
    private static DAO dao=new DAO();
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;
    private int returned;
    private Driver driver=null;
    private Connection connection=null;
    private String query="";
    private Scanner scanner=new Scanner(System.in);
    private DAO(){
        try {
            driver=new OracleDriver();
            DriverManager.registerDriver(driver);
            connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
            System.out.println("Driver is connected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static DAO getDao(){return dao;}
    public void delete(){
        System.out.println("Enter the id ");
        int id= scanner.nextInt();
        try {
            query="delete from hai where id=?";
            preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            returned=preparedStatement.executeUpdate();
            if(returned>0){
                System.out.println("Deletion done");
            }
            else{
                System.out.println("Deletion failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(){
        System.out.println("Enter the id ");
        int id= scanner.nextInt();
        System.out.println("Enter the name ");
        String name= scanner.next();
        System.out.println("Enter the price ");
        int price= scanner.nextInt();
        try {
            query="update hai set name=?,price=? where id=?";
            preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,price);
            preparedStatement.setInt(3,id);
            returned=preparedStatement.executeUpdate();
            if(returned>0)
                System.out.println("Done");
            else
                System.out.println("Updation failed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void readOne(){
        System.out.println("Enter the id ");
        int id= scanner.nextInt();
        try {
            query="select * from hai where id=?";
            preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println(resultSet.getInt("id")+" "+resultSet.getString("name")+" "+resultSet.getInt("price"));
            }
            else{
                System.out.println("Read failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertNew(){
        System.out.println("Enter the name ");
        String name= scanner.next();
        System.out.println("Enter the price ");
        int price= scanner.nextInt();
        query="insert into hai(id,name,price) values(hai_seq.nextval,?,?)";
        try {
            preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,price);
            returned=preparedStatement.executeUpdate();
            if(returned>0)
                System.out.println("Done");
            else
                System.out.println("Insertion failed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void check(){
        query="select * from hai";
        try {
            preparedStatement= connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id")+" "+resultSet.getString("name")+" "+resultSet.getInt("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

