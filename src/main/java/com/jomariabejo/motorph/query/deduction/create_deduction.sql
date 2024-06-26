/*
File: insert_deduction.sql
Description: Inserts a new record into the DEDUCTION table.
Author: Jomari Abejo
Date Created: April 10, 2024
Last Modified: April 10,2024
Version: 1.0
*/

-- Inserts a new record into the DEDUCTION table
-- Columns:
--   - 'employee_id': Specifies the employee ID associated with the deduction.
--   - 'sss': Represents the Social Security System (SSS) deduction for the employee.
--   - 'philhealth': Represents the PhilHealth deduction for the employee.
--   - 'pagibig': Represents the Pag-IBIG Fund deduction for the employee.
--   - 'total_contribution': Represents the total contribution amount for the employee.
--   - 'date_created': Timestamp for when the deduction record was created.

INSERT INTO DEDUCTION (
    deduction_id, employee_id, sss, philhealth, pagibig,
    total_contribution, date_created
)
VALUES (
           ?, -- deduction_id
           ?, -- employee_id
           ?, -- sss
           ?, -- philhealth
           ?, -- pagibig
           ?, -- total_contribution
           ?  -- date_created
       );
