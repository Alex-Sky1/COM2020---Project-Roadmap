import csv
import random as r




def seed_reservations(customers, bundles, count):

    info = []
    ids = r.sample(range(len(bundles)), count)

    for bundle_id in ids:
        

        info.append([
            bundle_id + 1,
            r.randint(0, len(customers)-1) + 1, # customer id
            bundles[bundle_id][0], # seller id
            "2026-02-17T18:25:43.014748", # Date
            "1245", # claim code
            False, # no show
            True, # Collected
        ])

        print(f"Seeded Reservation {bundle_id+1}")


    ## updarte bundles
    for id in range(len(bundles)):
        if id in ids:
            bundles[id][7] = True


    return info

    