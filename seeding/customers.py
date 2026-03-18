import name_gen as ng
import location_gen as lg
import misc_gen as mg





# badges array: [1 meal saved, 5 meals saved, 10 meals saved,
#         //              1 category, 3 categories, 5 categories,
#         //              1 seller, 5 sellers, 10 sellers,
#         //              20 co2 saved, 50 co2 saved, 100 co2 saved]
BADGE_COUNT = 12



def seed_customers(count):

    info = []

    for x in range(count):
        [fname, sname] = ng.random_name()
        dname = ng.gen_dname(fname, sname)
        [address, postcode] = lg.random_location()
        

        info.append([
            fname,
            sname,
            dname,
            address,
            postcode,
            lg.COUNTY,
            dname + "@yahoo.org.co.uk.edu.gov",
            mg.random_phone_number(),
            mg.random_password(),
            0, ## streak
            [False] * 12 ## BADGES
        ])

        print(f"Seeded Customer {x+1}")


    return info


def print_customer_info(customers):
    for customer in customers:
        print(f"{customer[0]} {customer[1]} -- {customer[2]} -- {customer[3]}, {customer[5]}, {customer[4]} -- {customer[6]}, {customer[7]}, {customer[8]}")