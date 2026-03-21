import random as r

ISSUE_PROBABILITY = 0.2

ISSUE_TYPES = [
    "Not as described",
    "Contains expired food",
    "Bundle not available",
    "Other"
]

ISSUES = {
    "Not as described": ["Items in my bundle were wrong, I am not pleased", "Items in my bundle were wrong, I am crying as I type this", "Items in my bundle were wrong, I am full of rage"],
    "Contains expired food": ["Items other than blue cheese were rotten", "Foods should not be fuzzy", "The rot comes and we are all scared"] * 50 + ["Nurgle has blessed my food"],
    "Bundle not available": ["*Insert picture of missing food here*", "I am so hungry please where is my food", "I paid for this food and yet I cannot see it what is wrong"],
    "Other": ["The bag was wet", "The foods has been crushed", "one of the items leaked over my bag"] * 20 + ["Full of rats"]
}


RESPONSES = ["refund issued"] * 40 + ["store credit"] * 20 + ["not our problem"]


## min 150
def seed_issues(reservations):

    info = []

    count = 0

    for reservation in reservations:
        if reservation[5] or r.random() > ISSUE_PROBABILITY:
            continue

        type = r.choice(ISSUE_TYPES)
        description = r.choice(ISSUES[type])

        info.append([
            reservation[0],
            reservation[1],
            type,
            description,
            True,
            r.choice(RESPONSES)
        ])

        count += 1
        print(f"Seeded Issue {count}")

    return info