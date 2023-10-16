package model_class_;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    List<Customer> findAll() throws SQLException, ClassNotFoundException;

    void save(Customer customer);

    Customer findById(int id) throws SQLException, ClassNotFoundException;

    void update(int id, Customer customer) throws SQLException, ClassNotFoundException;

    void remove(int id) throws SQLException, ClassNotFoundException;
}