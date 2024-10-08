package com.jomariabejo.motorph.repository;

import com.jomariabejo.motorph.HibernateUtil;
import com.jomariabejo.motorph.model.Employee;
import com.jomariabejo.motorph.model.Timesheet;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public class TimesheetRepository extends _AbstractHibernateRepository<Timesheet, Integer> {
    public TimesheetRepository() {
        super(Timesheet.class);
    }

    public Optional<List<Integer>> fetchYearsOfTimesheetByEmployee(Employee employee) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Query<Integer> query = session.createNamedQuery("getYearsOfTimesheet", Integer.class);
            query.setParameter("employee", employee);
            List<Integer> resultList = query.getResultList();
            return Optional.of(resultList);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Optional<Timesheet> fetchTimesheet(Employee employee, LocalDate date) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Query<Timesheet> query = session.createNamedQuery("fetchTimesheetByEmployeeAndDate", Timesheet.class);
            query.setParameter("employee", employee);
            query.setParameter("date", date);
            Timesheet result = query.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Optional<List<Timesheet>> fetchEmployeeTimesheetsByYearAndMonth(Employee employee, Year year, Month month) {
        Session session = null;
        session = HibernateUtil.openSession();
        int yearValue = year.getValue();
        int monthValue = month.getValue();

        Query<Timesheet> query = session.createNamedQuery("fetchEmployeeTimesheetsByMonthAndYear", Timesheet.class);
        query.setParameter("EMPLOYEE", employee);
        query.setParameter("YEAR", yearValue);
        query.setParameter("MONTH", monthValue);

        List<Timesheet> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
}
