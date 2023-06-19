import socket, struct, time, sys

#packets_sent_time = []

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server_addr = (sys.argv[1], int(sys.argv[2]))

    while True:
        currentTime = time.time()
        #packets_sent_time.append(currentTime)
        #print(currentTime)
        s.sendto(struct.pack("!d", currentTime), server_addr)
        time_sent, server_addr = s.recvfrom(1024)

        time_sent = struct.unpack("!d", time_sent)[0]
        currentTime = time.time()

        print("PING reply got - round trip time: " + str(currentTime - time_sent))
        time.sleep(1)
