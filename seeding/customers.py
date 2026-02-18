import csv
import random as r
import general

from general import *



FNAMES = ["Alex", "Thomas", "Isla", "Rosie", "Kayleigh", "Alice", "Christian"]
SNAMES = ["Pinington", "McArthur", "Wabsontio", "Bleems"]


PLACES = ["Glue Town", "Horseton", "Stoke on Trent"]
ROADS = ["Tree Way", "Soy Pleasant", "Glass Street"]
COUNTIES = ["Oxfordshire", "Hertfordshire", "Devon", "Greater Manchester", "Lesser Manchester", "Average Manchester", "Sad Manchester", "Happy Manchester"]
POSTCODES = ["OX", "HF", "DV", "GM", "LM", "AM", "SM", "HM"]


def gen_dname(fname: str, sname: str):
    return sname + fname[0]

def gen_email(dname):
    return dname + "@yahoo.org.co.uk.edu.gov"


def seed_customers(count):

    info = []

    for x in range(count):
        [fname, sname] = random_name()
        dname = gen_dname(fname, sname)
        

        info.append([
            fname,
            sname,
            dname,
            random_address(),
            random_postcode(),
            r.choice(COUNTIES),
            gen_email(dname),
            random_phone_number(),
            "Password1234",
            0,
            []
        ])

        print(f"Seeded Customer {x+1}")


    return info
