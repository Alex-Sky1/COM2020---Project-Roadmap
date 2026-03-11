import csv
import random as r
import general

from general import *


## CAN DO

DNAMES = ["Foods", "and Co.", "General Store"]
USED_STORE_NAMES = [""]


def gen_store_name(fname: str, sname: str):
    name = ""
    while name in USED_STORE_NAMES:
        name = fname + " " + sname + " " + r.choice(DNAMES)
    USED_STORE_NAMES.append(name)
    return name


def gen_email(store_name: str):
    return store_name.replace(" ", "_") + "@hotmail.co.uk"



def seed_sellers(count):

    info = []

    for x in range(count):
        [fname, sname] = random_name()
        store_name = gen_store_name(fname, sname)
        

        info.append([
            fname,
            sname,
            store_name,
            random_address(),
            random_postcode(),
            r.choice(COUNTIES),
            gen_email(store_name),
            random_phone_number(),
            "Password1234"
        ])

        print(f"Seeded Seller {x+1}")

    return info

    
