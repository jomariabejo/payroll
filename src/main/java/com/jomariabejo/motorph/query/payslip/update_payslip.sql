UPDATE
    PAYSLIP
SET
    (
        employee_id = ?, alw_id = ?, deduction_id = ?,
        tax_id = ?, pay_period_start = ?, pay_period_end = ?,
        total_hours_worked = ?, gross_income = ?,
        net_income = ?, date_created = ?
        )
WHERE
    payslip_id = ?
