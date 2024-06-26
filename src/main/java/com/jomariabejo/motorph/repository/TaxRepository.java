package com.jomariabejo.motorph.repository;

import com.jomariabejo.motorph.database.DatabaseConnectionUtility;
import com.jomariabejo.motorph.entity.Tax;
import com.jomariabejo.motorph.utility.TextReader;

import java.sql.*;

public class TaxRepository {
    public void saveTax(Tax tax)  {
        String query = TextReader.readTextFile("src\\main\\java\\com\\jomariabejo\\motorph\\query\\tax\\create_tax.sql");

        try (Connection connection = DatabaseConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,tax.getTaxId());
            preparedStatement.setBigDecimal(2, tax.getTaxableIncome()   );
            preparedStatement.setInt(3, tax.getTaxCategoryId());
            preparedStatement.setBigDecimal(4, tax.getWithheldTax());
            preparedStatement.setInt(5, tax.getEmployeeId());
            preparedStatement.setDate(6, tax.getDateCreated());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Insert tax success");
            } else {
                System.out.println("Insert tax failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Tax fetchTaxByEmployeeIdAndDate(int employeeId, Date dateCreated) {
        String query = TextReader.readTextFile("src\\main\\java\\com\\jomariabejo\\motorph\\query\\deduction\\get_deduction.sql");
        Tax tax = new Tax();

        try (Connection connection = DatabaseConnectionUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1,dateCreated);
            preparedStatement.setInt(2,employeeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                tax.setEmployeeId(resultSet.getInt("employee_id"));
                tax.setWithheldTax(resultSet.getBigDecimal("withheld_tax"));
                tax.setDateCreated(resultSet.getDate("date_created"));
                tax.setTaxableIncome(resultSet.getBigDecimal("taxable_income"));
                tax.setTaxCategoryId(resultSet.getInt("tax_cat_id"));
                tax.setTaxId(resultSet.getInt("tax_id"));

                return tax;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tax;
    }
}
