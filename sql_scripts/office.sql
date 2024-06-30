use employee;
-- select tables
select*from employee;
select*from Dept;

-- insert data into employee
insert into employee (Name , Designation , Salary , ManagerId, DepartmentId)
values ("Kobe", null , "120", null , 4);

-- insert data into Dept
insert into dept (DeptId ,DeptName)
values ();

-- update column type 
ALTER TABLE employee
MODIFY COLUMN ManagerId int NOT NULL;

-- update data in employee
UPDATE employee
SET ManagerId = 2
WHERE ManagerId = null;

-- inner join
select e.EmployeeId, e.Name, e.Designation, e.ManagerId, d.DeptName
from employee e
join Dept d on e.DepartmentId = d.DeptId;

-- inner join 
SELECT 
    e1.EmployeeId AS EmployeeId,
    e1.Name AS Name,
    e1.Designation AS Designation,
    e2.Name AS ManagerName
FROM 
    employee e1
LEFT JOIN 
    employee e2
ON 
    e1.ManagerId = e2.EmployeeId;

-- safe update
SET SQL_SAFE_UPDATES = 0;



