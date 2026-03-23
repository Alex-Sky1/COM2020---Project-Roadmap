import random as r
import name_gen as ng
import location_gen as lg
import misc_gen as mg





def gen_store_email(store_name: str):
    return store_name.replace(" ", "_") + "@hotmail.co.uk"




def seed_sellers(count):

    info = []

    for x in range(count):
        [fname, sname] = ng.random_name()
        store_name = ng.gen_store_name(fname, sname)
        [address, postcode] = lg.random_location()

        info.append([
            fname,
            sname,
            store_name,
            address,
            postcode,
            lg.COUNTY,
            gen_store_email(store_name),
            mg.random_phone_number(),
            mg.random_password()
        ])

        print(f"Seeded Seller {x+1}")

    return info



def print_seller_info(sellers):
    for seller in sellers:
        print(f"{seller[0]} {seller[1]} -- {seller[2]} -- {seller[3]}, {seller[5]}, {seller[4]} -- {seller[6]}, {seller[7]}, {seller[8]}")
    
