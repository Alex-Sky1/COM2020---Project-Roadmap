import random as r

F_NAMES = []
S_NAMES = []

MAX_NAME_COUNT = len(F_NAMES) * len(S_NAMES)

STORE_NAME_SUFFIXES = ["Foods", "and Co.", "General Store", "Grocers", "Produce"]
USED_STORE_NAMES = [""]


USED_NAMES = [""]
USED_D_NAMES = []

def load_names():
    f = open("data/f_names.txt")
    for line in f.readlines():
        F_NAMES.append(line.strip())
    f.close();

    f = open("data/s_names.txt")
    for line in f.readlines():
        S_NAMES.append(line.strip())
    f.close();
    

load_names()




def random_name():
    name = ""
    while name in USED_NAMES:
        name = r.choice(F_NAMES) + " " + r.choice(S_NAMES)
    USED_NAMES.append(name)

    ## check if we have used all possible names
    if len(USED_NAMES) - 1 == MAX_NAME_COUNT:
        print("RUN OUT OF NAMES CLOSE THE PROGRAM")

    return name.split(" ")



def gen_dname(fname: str, sname: str):
    base_d_name = sname + fname[0]
    d_name = base_d_name
    count = 0
    while d_name in USED_D_NAMES:
        count += 1
        d_name = base_d_name + str(count)
    USED_D_NAMES.append(d_name)
    return d_name


def gen_store_name(fname: str, sname: str):
    name = ""
    while name in USED_STORE_NAMES:
        name = fname + " " + sname + " " + r.choice(STORE_NAME_SUFFIXES)
    USED_STORE_NAMES.append(name)
    return name