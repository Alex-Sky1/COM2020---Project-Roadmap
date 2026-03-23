import random as r
from time_gen import get_reservation_time




BASE_RESERVATION_CHANCE = 4.0

WEATHER_EFFECTS = {
    "Sunny": 0.95,
    "Cloudy": 1.0,
    "Rainy": 0.75
}

WINDOW_EFFECTS = {
    0: 0.2,
    1: 0.1,
    2: 0.1,
    3: 0.1,
    4: 0.1,
    5: 0.2,
    6: 0.25,
    7: 0.75,
    8: 0.8,
    9: 1.0,
    10: 1.0,
    11: 1.0,
    12: 1.0,
    13: 1.0,
    14: 1.0,
    15: 1.0,
    16: 1.0,
    17: 0.8,
    18: 0.7,
    19: 0.6,
    20: 0.5,
    21: 0.4,
    22: 0.3,
    23: 0.2
}

def get_bundle_reservation_chance(bundle):
    return BASE_RESERVATION_CHANCE * WEATHER_EFFECTS[bundle[10]] * WINDOW_EFFECTS[bundle[7]]

NO_SHOW_CHANCE = 0.1

def random_claim_code():
    return str(r.randint(0, 9)) + str(r.randint(0, 9)) + str(r.randint(0, 9)) + str(r.randint(0, 9))



def seed_reservations(customers, bundles):

    info = []

    collected_count = 0
    not_reserved_count = 0
    no_show_count = 0
    


    for (bundle_id, bundle) in enumerate(bundles):

        ## decide if this bundle is reserved and/or no_showed
        reserved = r.random() <= get_bundle_reservation_chance(bundle)
        no_show = r.random() <= NO_SHOW_CHANCE

        if reserved:

            customer_id = r.randint(1, len(customers))
            seller_id = bundles[bundle_id][0]
            date_time = get_reservation_time(bundles[bundle_id][4], bundles[bundle_id][7])

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

        ## update bundle
        if reserved and not no_show:
            bundle[8] = True
            bundle[9] = False
            collected_count += 1
        elif not reserved:
            not_reserved_count += 1
        else:
            no_show_count += 1
        
    print(f"{collected_count} Collected Reservations -- {no_show_count} No Showed Reservations -- {not_reserved_count} Bundles not Reserved")
    return info

def print_reservation_info(reservations):
    for reservation in reservations:
        print(f"Reservation for bundle {reservation[0]} from customer {reservation[1]} -- {reservation[3]} -- {reservation[4]} -- Collected: {reservation[6]}")