import random as r
import string

USED_ADDRESSES = []

COUNTY = "Devon"

ROAD_NAMES = []
ROAD_SUFFIXES = ["Road", "Street", "Green", "Close"]
MAX_ADDRESS_COUNT = len(ROAD_NAMES) * len(ROAD_SUFFIXES) * 250



def load_road_names():
    f = open("data/road_names.txt")
    for line in f.readlines():
        ROAD_NAMES.append(line.strip())
    f.close();

load_road_names()



def random_address():
    address = str(r.randint(1, 250)) + " " + r.choice(ROAD_NAMES) + " " + r.choice(ROAD_SUFFIXES)
    while address in USED_ADDRESSES:
        address = str(r.randint(1, 250)) + " " + r.choice(ROAD_NAMES) + " " + r.choice(ROAD_SUFFIXES)

    ## check if we have used all possible names
    if len(USED_ADDRESSES) - 1 == MAX_ADDRESS_COUNT:
        print("RUN OUT OF ADDRESSES CLOSE THE PROGRAM")

    return address + ", Exeter Two"

## still no relation
def random_postcode():
    return "EX" + str(r.randint(1, 10)) + " " + str(r.randint(1, 10)) + r.choice(string.ascii_uppercase) + r.choice(string.ascii_uppercase)


def random_location():
    return [random_address(), random_postcode()]