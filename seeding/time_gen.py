from datetime import datetime
import random as r


START_DATE = [2025, 1, 1, 0, 0, 0]
END_DATE = [2026, 2, 28, 23, 59, 59]

YEARS = [2025] * 12 + [2026] * 2
MONTHS = {
    2025: list(range(1, 13)),
    2026: list(range(1, 3))
}
DAYS = dict.fromkeys(list(range(1, 12)), 31)
DAYS.update(dict.fromkeys([9, 4, 6, 11], 30))
DAYS[2] = 28


def random_date_time():
    year = r.choice(YEARS)
    month = r.choice(MONTHS[year])
    day = r.randint(1, DAYS[month])

    return datetime(
        year,
        month,
        day,
        r.randint(START_DATE[3], END_DATE[3]),
        r.randint(START_DATE[4], END_DATE[4]),
        r.randint(START_DATE[5], END_DATE[5]),
    )

def format_date_time(date_time):
    return str(date_time).replace(" ", "T")


def date_time_to_index(date_time: datetime):
    year_index = date_time.year - START_DATE[0]
    month_index = date_time.month - 1
    day_index = date_time.day - 1
    hour_index = int(date_time.hour) // 8
    ## this is not "good" or "fully fills every number" but it will be unique per block
    return hour_index + (day_index + (month_index + (year_index) * 12) * 31) * 3

WEATHERS = ["Sunny", "Cloudy", "Rainy"]
## who cares only one weather every 8 hours
## TODO: PICKUP TIME FOR WEATHER NOT POSTING TIME
DATE_TIME_WEATHERS = {}

def random_date_time_and_weather():
    time = random_date_time()
    time_index = date_time_to_index(time)

    if time_index not in DATE_TIME_WEATHERS:
        DATE_TIME_WEATHERS[time_index] = r.choice(WEATHERS)
    return format_date_time(time), DATE_TIME_WEATHERS[time_index]



if __name__ == "__main__":
    print(YEARS)
    print(MONTHS)
    print(DAYS)
    time = random_date_time()
    print(time)
    print(format_date_time(time))
    print(date_time_to_index(time))
    start_time = datetime(2025, 1, 1, 0, 0, 0)
    time_two = datetime(2025, 1, 1, 8, 0, 0)
    print(date_time_to_index(start_time))
    print(date_time_to_index(time_two))