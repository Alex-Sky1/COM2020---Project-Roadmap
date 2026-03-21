import csv
import random as r
from time_gen import random_date_time_and_weather

CATEGORIES = ["bakery", "snacks", "dairy", "plants", "groceries", "other"]

FOODS = []

ALLERGENS = ["celery", "gluten", "crustaceons", "eggs", "fish", "lupin", "milk", "molluscs", "mustard", "peanuts", "sesame", "soybeans", "sulphur", "nuts"]

MAX_FOODS = 10
MIN_FOODS = 4


def load_foods():
    
    for path in ["cheese.txt", "fruit.txt", "veg.txt", "breads.txt", "meats.txt", "snacks.txt", "misc.txt"]:
        arr = []
        f = open("data/" + path)
        for line in f.readlines():
            arr.append(line.strip())
        f.close();
        FOODS.append(arr)


load_foods()

PICKUP_WINDOWS = ["00:00-01:00", "01:00-02:00", "02:00-03:00", "03:00-04:00", "04:00-05:00", "05:00-06:00", "06:00-07:00", "07:00-08:00", "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00", "20:00-21:00", "21:00-22:00", "22:00-23:00", "23:00-00:00"]



FOODS_FOR_CATEGORIES = {
    "bakery": [3],
    "snacks": [5],
    "dairy": [0],
    "plants": [1, 2],
    "groceries": [0, 1, 2, 3, 4, 5],
    "other": [6]
}

            

def seed_bundles(sellers, count_per_seller):

    info = []

    for seller_id in range(len(sellers)):
        for x in range(count_per_seller):

            cat = r.choice(CATEGORIES)
            food_cats = FOODS_FOR_CATEGORIES[cat]

            foods = []
            allergens = []

            for i in range(r.randint(MIN_FOODS, MAX_FOODS)):
                foods.append(r.choice(FOODS[r.choice(food_cats)]))

            ### TODO correct allergens
            for i in range(r.randint(0, 3)):
                allergens.append(r.choice(ALLERGENS))

            allergens = list(set(allergens)) # remove duplicates
            foods.sort()

            price = r.randint(50, 1000) / 10

            ## time and weather
            [date_time, weather, pickup_window] = random_date_time_and_weather()

           
            info.append([
                seller_id + 1, ## 0
                cat, ## 1
                foods, ## 2
                allergens, ## 3
                date_time, ## 4
                price, ## 5
                round(max(price / 2, r.randint(5, 20))), #discount ## 6
                pickup_window, ## 7
                False, # reserverd ## 8
                True, # expired ## 9
                weather ## 10
            ])

            print(f"Seeded Bundle {x+1}")

    r.shuffle(info)

    return info

def print_bundle_info(bundles):
    for bundle in bundles:
        print(f"Bundle from seller with ID {bundle[0]} -- {bundle[1]} -- {bundle[2]} -- {bundle[3]} -- {bundle[4]}, {bundle[7]}, {bundle[10]} -- {bundle[5]}, {bundle[6]}")