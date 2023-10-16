package model_class_;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService_Impl_ConnectBD implements CustomerService{

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/customer_management";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "slogoman";
    public  Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
        return connection;
    }

    @Override
    public List<Customer> findAll() throws SQLException, ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        Connection connection = connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                customers.add(new Customer(id, name, email, address));
            }

        return customers;
    }


    @Override
    public void save(Customer customer) {
        String query = "INSERT INTO customers (name, email, address) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(int id) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer();
        Connection connection = connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customers where id = '"+ id +"'");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            customer = new Customer(id, name, email, address);
        }
        return customer;
    }

    @Override
    public void update(int id, Customer customer) throws SQLException, ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        String query = "update customers set name = ?, email = ?, address=? where id = ?";
        PreparedStatement preparedStatement = connection().prepareStatement(query);
        preparedStatement.setString(1,customer.getName());
        preparedStatement.setString(2,customer.getEmail());
        preparedStatement.setString(3,customer.getAddress());
        preparedStatement.setInt(4,id);

        preparedStatement.executeUpdate();
    }

    @Override
    public void remove(int id) throws SQLException, ClassNotFoundException {
    List<Customer> customers = new ArrayList<>();
    String query = "Delete from customers where id = ?";
    PreparedStatement preparedStatement = connection().prepareStatement(query);
    preparedStatement.setInt(1,id);

    preparedStatement.executeUpdate();
    }
}
