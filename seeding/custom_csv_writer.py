


def write_to_csv(path, data):
    f = open(path, "w")
    for line in data:
        write_line = ""
        for item in line:
            write_line += str(item) + "&"
        write_line = write_line[:-1] + "\n"
        f.write(write_line)
    f.close()