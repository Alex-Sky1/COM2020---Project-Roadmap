import csv
import random as r
from time_gen import random_date_time_and_weather

CATEGORIES = ["bakery", "snacks", "dairy", "plants", "groceries", "other"]

FOODS = []
ALLERGENS = ["celery", "gluten", "crustaceons", "eggs", "fish", "lupin", "milk", "molluscs", "mustard", "peanuts", "sesame", "soybeans", "sulphur", "nuts"]

MAX_FOODS = 10
MIN_FOODS = 4


def load_foods():
    for path in ["cheese.txt", "fruit.txt", "veg.txt"]:
        f = open("data/" + path)
        for line in f.readlines():
            FOODS.append(line.strip())
        f.close();


load_foods()

PICKUP_WINDOWS = ["00:00-01:00", "01:00-02:00", "02:00-03:00", "03:00-04:00", "04:00-05:00", "05:00-06:00", "06:00-07:00", "07:00-08:00", "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00", "20:00-21:00", "21:00-22:00", "22:00-23:00", "23:00-00:00"]

PICKUP_MIN = 0
PICKUP_MAX = 23

            

def seed_bundles(sellers, count_per_seller):

    info = []

    for seller_id in range(len(sellers)):
        for x in range(count_per_seller):

            foods = []
            allergens = []

            for i in range(r.randint(MIN_FOODS, MAX_FOODS)):
                foods.append(r.choice(FOODS))
            for i in range(r.randint(0, 3)):
                allergens.append(r.choice(ALLERGENS))

            allergens = list(set(allergens)) # remove duplicates
            foods.sort()

            price = r.randint(50, 1000) / 10

            ## time and weather
            [date_time, weather] = random_date_time_and_weather()

           
            info.append([
                seller_id + 1,
                r.choice(CATEGORIES),
                foods,
                allergens,
                date_time,
                price,
                round(max(price / 2, r.randint(5, 20))), #discount
                r.randint(PICKUP_MIN, PICKUP_MAX), # pickup window
                False, # reserverd
                False, # expired
                weather
            ])

            print(f"Seeded Bundle {x+1}")

    r.shuffle(info)


    return info

    