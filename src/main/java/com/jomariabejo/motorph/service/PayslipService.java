package com.jomariabejo.motorph.service;

import com.jomariabejo.motorph.entity.EmployeePayrollSummaryReport;
import com.jomariabejo.motorph.entity.Payslip;
import com.jomariabejo.motorph.repository.PayslipRepository;
import com.jomariabejo.motorph.utility.AutoIncrementUtility;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PayslipService {
    private final PayslipRepository payslipRepository;

    public PayslipService() {
        this.payslipRepository = new PayslipRepository();
    }

    public int getNextPrimaryKeyValue() {
        return AutoIncrementUtility.getNextAutoIncrementValueForPayslip();
    }

    public void saveGeneratedPayslip(ObservableList < EmployeePayrollSummaryReport > employeePayrollSummaryReports) {
        payslipRepository.saveMultiplePayslip(employeePayrollSummaryReports);
    }

    public ArrayList < Payslip > fetchPayslipByEmployeeId(int employeeId) {
        return payslipRepository.fetchPayslipByPayslipId(employeeId);
    }

    public Payslip.PayslipViewer fetchPayslip(int payslipId) {
        return payslipRepository.fetchPayslipBreakdown(payslipId);
    }

    public ObservableList<Payslip> fetchPayslipSummary() {
        return payslipRepository.fetchPayslipSummary();
    }
}