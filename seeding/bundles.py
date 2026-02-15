import csv
import random as r
import general

from general import *

CATEGORIES = ["cakes", "happy days", "sad bundles", "egg bundles", "tree time"]

FOODS = ["egg", "beans", "bread", "lobster thermador", "bread 2", "cheese", "mice", "alligator"]
ALLERGENS = ["celery", "gluten", "crustaceons", "eggs", "fish", "lupin", "milk", "molluscs", "mustard", "peanuts", "sesame", "soybeans", "sulphur", "nuts"]




def seed_bundles(sellers, count_per_seller):

    info = []

    for seller_id in range(len(sellers)):
        for x in range(count_per_seller):

            foods = []
            allergens = []

            for y in range(r.randint(4, 10)):
                foods.append(r.choice(FOODS))
            for y in range(r.randint(1, 3)):
                allergens.append(r.choice(ALLERGENS))

            allergens = list(set(allergens)) # remove duplicates
            foods.sort()

            price = r.randint(50, 1000) / 10
        
            info.append([
                seller_id,
                r.choice(CATEGORIES),
                foods,
                allergens,
                price,
                max(price / 2, r.randint(5, 20)), #discount
                5,
                False, # reserverd
                False # expired
            ])

            print(f"Seeded Bundle {x+1}")

    r.shuffle(info)


    return info

    