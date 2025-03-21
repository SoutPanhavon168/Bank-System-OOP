import mysql.connector
from faker import Faker
import random

fake = Faker()

# Function to generate customer data
def generate_customer():
    return {
        'customerId': random.randint(1000, 9999),
        'lastname': fake.last_name(),
        'firstname': fake.first_name(),
        'email': fake.email(),
        'password': fake.password(),
        'phone_number': fake.phone_number(),
        'birthdate': fake.date_of_birth(minimum_age=18, maximum_age=80).strftime('%Y-%m-%d'),
        'governmentId': f"*******{random.randint(1000, 9999)}"
    }

# Generate 20 customer records
customers = [generate_customer() for _ in range(20)]

# Connect to the MySQL database
db = mysql.connector.connect(
    host="jbanksystem-prumsereyreaksa-c339.h.aivencloud.com:23842",
    user="avnadmin",
    password="AVNS_eEoTvjWciUxHeIQBJHR",
    database="banksystem"
)


cursor = db.cursor()

# Insert data into the customers table
insert_query = """
    INSERT INTO customers (customerId, lastname, firstname, email, password, phone_number, birthdate, governmentId)
    VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
"""

# Execute the insert for each customer
for customer in customers:
    cursor.execute(insert_query, (
        customer['customerId'],
        customer['lastname'],
        customer['firstname'],
        customer['email'],
        customer['password'],
        customer['phone_number'],
        customer['birthdate'],
        customer['governmentId']
    ))

# Commit the changes
db.commit()

# Close the connection
cursor.close()
db.close()

print("Fake data inserted successfully!")
