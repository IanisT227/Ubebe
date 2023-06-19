import socket, sys, random, time, struct

low_value = 1
high_value = 65000

random.seed()

if __name__ == "__main__":
    server_addr = (sys.argv[1], int(sys.argv[2]))
    rs = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)


    while True:
        random_number = random.randint(low_value, high_value)
        message = struct.pack("!H", random_number)
        rs.sendto(message, server_addr)
        print("Sent " + str(random_number) + " to server")

        data, server_addr = rs.recvfrom(4)
        print("Received " + data.decode() + " from server")

        if data.decode() == "H":
            low_value = random_number
        elif data.decode() == "S":
            high_value = random_number
        elif data.decode() == "G":
            print("YOU'VE GUESSED THE NUMBER!")
            exit(0)
        elif data.decode() == "L":
            print("YOU LOST!")
            exit(0)

        time.sleep(1)
