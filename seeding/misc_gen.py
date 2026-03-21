import random as r
import string

USED_PHONE_NUMEBRS = []
CHARS = string.ascii_letters + string.digits + string.punctuation

## these are all fake
def random_phone_number():
    num =  "07700 900" + str(r.randint(0, 999))
    while num in USED_PHONE_NUMEBRS:
        num =  "07700 900" + str(r.randint(0, 999))
    USED_PHONE_NUMEBRS.append(num)

    ## check if we have used all possible names
    if len(USED_PHONE_NUMEBRS) == 1000:
        print("RUN OUT OF PHONE NUMBERS CLOSE THE PROGRAM")

    return num


## requirements
## one lower, one upper, one special, one digit, min 8
def check_password(password):
    if ("&" not in password) and (len(password) >= 8) and (any(c.islower() for c in password)) and (any(c.isupper() for c in password)) and (any(c.isdigit() for c in password)) and (any(not c.isalnum() for c in password)):
        return True
    return False




### NOT FOR USE ELSEWHERE
def gen_password():
    password = ""
    for i in range(r.randint(8, 32)):
        password += r.choice(CHARS)
    return password


def random_password():
    password = gen_password()
    while not check_password(password):
        password = gen_password()
    return password