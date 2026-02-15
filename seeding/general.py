import random as r

FNAMES = ["Alex", "Thomas", "Isla", "Rosie", "Kayleigh", "Alice", "Christian", "Josh", "Harry", "Hannah", "Daniel", "Oliver"]
SNAMES = ["Pinington", "McArthur", "Wabsontio", "Bleems", "Harbinton", "Fishman", "Davids", "Davidser", "Seerin"]
USED_NAMES = [""]
MAX_NAME_COUNT = len(FNAMES) * len(SNAMES)

PLACES = ["Glue Town", "Horseton", "Stoke on Trent"]
ROADS = ["Tree Way", "Soy Pleasant", "Glass Street"]
COUNTIES = ["Oxfordshire", "Hertfordshire", "Devon", "Greater Manchester", "Lesser Manchester", "Average Manchester", "Sad Manchester", "Happy Manchester"]
POSTCODES = ["OX", "HF", "DV", "GM", "LM", "AM", "SM", "HM"]


def random_name():
    name = ""
    while name in USED_NAMES:
        name = r.choice(FNAMES) + " " + r.choice(SNAMES)
    USED_NAMES.append(name)

    ## check if we have used all possible names
    if len(USED_NAMES) - 1 == MAX_NAME_COUNT:
        print("RUN OUT OF NAMES CLOSE THE PROGRAM")


    return name.split(" ")


def random_address():
    return str(r.randint(1, 200)) + " " + r.choice(ROADS) + ", " + r.choice(PLACES)

## Zero relation to the actual address
def random_postcode():
    return r.choice(POSTCODES) + str(r.randint(1, 10)) + " " + str(r.randint(1, 10)) + r.choice(POSTCODES)



def random_phone_number():
    return str(r.randint(1000, 9999)) + " " + str(r.randint(100000, 999999))
