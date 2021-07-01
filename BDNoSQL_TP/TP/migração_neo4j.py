import cx_Oracle as oracle
from neo4j import GraphDatabase
import json
import urllib.request
import time
import pymongo


######################### Fetch from oracle #########################

oracle.init_oracle_client(lib_dir=r"C:\Users\luisp\Desktop\4ano_1sem\BD_NOSQL\instantclient-basic-windows.x64-19.8.0.0.0dbru\instantclient_19_8")

conn = oracle.connect('HR','hr', 'localhost/orclpdb1.localdomain', encoding='UTF-8')
cursor = conn.cursor()

cursor.execute('select * from COUNTRIES')
countries = cursor.fetchall()

cursor.execute('select * from DEPARTMENTS')
departments = cursor.fetchall()

cursor.execute('select * from EMPLOYEES')
employees = cursor.fetchall()

cursor.execute('select * from JOB_HISTORY')
job_history = cursor.fetchall()

cursor.execute('select * from JOBS')
jobs = cursor.fetchall()

cursor.execute('select * from LOCATIONS')
locations = cursor.fetchall()

cursor.execute('select * from REGIONS')
regions = cursor.fetchall()

######################### NEO4J #########################

graphdb = GraphDatabase.driver(uri='neo4j://localhost:7687', auth=('neo4j', 'test'))
session = graphdb.session()

for c in countries:
    cypher = 'CREATE (n:Country {country_id: \'' + str(c[0]) + '\', country_name: \'' + str(c[1]) + '\'})'
    session.run(cypher)
print('countries inserted')


for d in departments:
    if str(d[2]) != 'None': 
        cypher = 'CREATE (n:Department {department_id: ' + str(d[0]) + ', department_name: \'' + str(d[1]) + '\'})'
    else:
        cypher = 'CREATE (n:Department {department_id: ' + str(d[0]) + ', department_name: \'' + str(d[1]) + '\'})'
    session.run(cypher)
print('departments inserted')


for d in employees:
    cypher = 'CREATE (n:Employees {employee_id: ' + str(d[0]) + ', first_name: \'' + str(d[1]) + '\', last_name: \'' + str(d[2]) + '\', email: \'' + str(d[3]) + '\', phone_number: \'' + str(d[4]) + '\', hire_date: \'' + str(d[5]) + '\', salary: ' + str(d[7]) + ', commission_pct: \'' + str(d[8]) + '\'})'
    session.run(cypher)
print('employees inserted')



for j in jobs:
    cypher = 'CREATE (n:Job {job_id: \'' + str(j[0]) + '\', job_title: \'' + str(j[1]) + '\', min_salary: ' + str(j[2]) + ', max_salary: ' + str(j[3]) + '})'
    session.run(cypher)
print('jobs inserted')


for l in locations:
    cypher = 'CREATE (n:Location {location_id: ' + str(l[0]) + ', street_address: \'' + str(l[1]) + '\', postal_code: \'' + str(l[2]) + '\', city: \'' + str(l[3]) + '\', state_province: \'' + str(l[4]) + '\'})'
    session.run(cypher)
print('locations inserted')


for r in regions:
    cypher = 'CREATE (n:Region {region_id: ' + str(r[0]) + ', region_name: \'' + str(r[1]) + '\'})'
    session.run(cypher)
print('regions inserted')



# department -> employee
def relations_emp_dep():
    for d in departments:
        for e in employees:
            if(str(d[0]) == str(e[10])):
                if(str(d[2]) == str(e[0])):
                    q='MATCH (a:Department),(b:Employees) WHERE a.department_id = ' + str(d[0]) + ' AND b.employee_id = ' + str(e[0]) + ' CREATE (a)<-[w:works_at {manages :\'yes\'}]-(b) RETURN type(w), w.manages'
                else:
                    q='MATCH (a:Department),(b:Employees) WHERE a.department_id = ' + str(d[0]) + ' AND b.employee_id = ' + str(e[0]) + ' CREATE (a)<-[w:works_at {manages :\'no\'}]-(b) RETURN type(w), w.manages'
                session.run(q)

# Relações entre countries e regions
def relations_coun_reg():
    for c in countries:
        for r in regions:
            if(str(c[2]) == str(r[0])):
                q='MATCH (a:Country),(b:Region) WHERE a.country_id = \'' + str(c[0]) + '\' AND b.region_id = ' + str(r[0]) + ' CREATE (a)-[i:is_in]->(b) RETURN type(i)'
                session.run(q)

# location -> country
def relations_loc_coun():
    for l in locations:
        for c in countries:
            if(str(c[0]) == str(l[5])):
                q='MATCH (a:Country),(b:Location) WHERE a.country_id = \'' + str(c[0]) + '\' AND b.location_id = ' + str(l[0]) + ' CREATE (a)<-[i:is_in]-(b) RETURN type(i)'
                session.run(q)

# deparment -> location
def relations_dep_loc():
    for d in departments:
        for l in locations:
            if(str(l[0]) == str(d[3])):
                q='MATCH (a:Department),(b:Location) WHERE a.department_id = ' + str(d[0]) + ' AND b.location_id = ' + str(l[0]) + ' CREATE (a)-[i:is_in]->(b) RETURN type(i)'
                session.run(q)

# employee -> manager 
def relations_emp_man():
    for e1 in employees:
        for e2 in employees:
            if(str(e1[9]) == str(e2[0])):
                q='MATCH (a:Employees),(b:Employees) WHERE a.employee_id = ' + str(e1[0]) + ' AND b.employee_id = ' + str(e2[0]) + ' CREATE (a)-[x:is_managed_by]->(b) RETURN type(x)'
                session.run(q)

# job history
def relations_jobHist():
    for j in job_history:
        q1='MATCH (a:Employees),(b:Job) WHERE a.employee_id = ' + str(j[0]) + ' AND b.job_id = \'' + str(j[3]) + '\' CREATE (a)-[w:worked_in {start: \'' + str(j[1]) + '\' , finish: \'' + str(j[2]) + '\' }]->(b) RETURN type(w), w.start, w.finish'
        session.run(q1)
        q2='MATCH (a:Employees),(b:Department) WHERE a.employee_id = ' + str(j[0]) + ' AND b.department_id = ' + str(j[4]) + ' CREATE (a)-[w:worked_at {start: \'' + str(j[1]) + '\' , finish: \''+ str(j[2]) + '\' }]->(b) RETURN type(w), w.start, w.finish'
        session.run(q2)  
        
       
def relations_emp_job():
    for j in jobs:
        for e in employees:
            if(str(j[0]) == str(e[6])):
                q='MATCH (a:Job),(b:Employees) WHERE a.job_id = \'' + str(j[0]) + '\' AND b.employee_id = ' + str(e[0]) + ' CREATE (a)<-[w:works_in]-(b) RETURN type(w)'
                session.run(q)

relations_emp_dep()
relations_coun_reg()
relations_loc_coun()
relations_dep_loc()
relations_emp_man()
relations_jobHist()
relations_emp_job()
print('relações criadas')


