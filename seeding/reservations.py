import random as r
from time_gen import get_reservation_time


NO_SHOW_CHANCE = 0.05

def random_claim_code():
    return str(r.randint(0, 9)) + str(r.randint(0, 9)) + str(r.randint(0, 9)) + str(r.randint(0, 9))



def seed_reservations(customers, bundles, count):

    info = []
    ids = r.sample(range(len(bundles)), count)

    for bundle_id in ids:
        
        customer_id = r.randint(1, len(customers))
        seller_id = bundles[bundle_id][0]
        date_time = get_reservation_time(bundles[bundle_id][4], bundles[bundle_id][7])

        no_show = r.random() <= NO_SHOW_CHANCE

        info.append([
            bundle_id + 1,
            customer_id,
            seller_id,
            date_time,
            random_claim_code(),
            no_show, # no show
            not no_show, # Collected
        ])

        print(f"Seeded Reservation {bundle_id+1}")


    ## updarte bundles
    for id in range(len(bundles)):
        if id in ids:
            bundles[id][8] = True
        else:
            bundles[id][9] = False


    return info

    