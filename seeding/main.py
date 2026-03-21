import name_gen
import csv
import sellers
import customers
import bundles
import reservations
import custom_csv_writer


## PASSWORD: min 8 chars, 1 upper, 


### INDICES ARE INCREMENTED BY 1 FOR DATABASE IDS
### DATETIME FORMAT: 2026-02-17T18:25:43.014748

### SOMETIMES THE BUNDLES RANDOMLY REPLACE 5 WITH TRUE, FIX THIS


def write_to_csv(filename, info):
    with open(filename, "w", newline="") as csvfile:

        writer = csv.writer(csvfile, delimiter="&", quotechar="\\", quoting=csv.QUOTE_MINIMAL)
        for line in info:
            writer.writerow(line)
        csvfile.close()

def main():
    print("---------------------Seeding Sellers---------------------")
    seller_info = sellers.seed_sellers(25)
    # sellers.print_seller_info(seller_info)
    print("---------------------Seeded Sellers----------------------\n\n")

    print("---------------------Seeding Customers-------------------")
    customer_info = customers.seed_customers(10)
    # customers.print_customer_info(customer_info)
    print("---------------------Seeded Customers--------------------\n\n")

    print("---------------------Seeding Bundles---------------------")
    bundle_info = bundles.seed_bundles(seller_info, 25)
    # bundles.print_bundle_info(bundle_info)
    print("---------------------Seeded Bundles----------------------\n\n")

    print("---------------------Seeding Reservations----------------")
    reservation_info = reservations.seed_reservations(customer_info, bundle_info, 25)
    reservations.print_reservation_info(reservation_info)
    print("---------------------Seeded Reservations-----------------\n\n")

    # write_to_csv("../Project Files/Team_Roadmap/src/main/resources/static/csv/sellers.csv", seller_info)
    # write_to_csv("../Project Files/Team_Roadmap/src/main/resources/static/csv/customers.csv", customer_info)
    # write_to_csv("../Project Files/Team_Roadmap/src/main/resources/static/csv/bundles.csv", bundle_info)
    # write_to_csv("../Project Files/Team_Roadmap/src/main/resources/static/csv/reservation.csv", reservation_info)

main()