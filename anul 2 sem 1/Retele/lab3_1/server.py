import socket, struct, time, threading

server_addr = ('127.0.0.1', 1234)

def server_connection():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.bind(server_addr)

    while True:
        time_sent, client_addr = s.recvfrom(1024)
        time_sent = struct.unpack("!d", time_sent)[0]
        print("PING REQUEST from " + str(client_addr[0]) + ":" + str(client_addr[1]) + " time_sent: " + str(time_sent))
        s.sendto(struct.pack("!d", time_sent), client_addr)

if __name__ == "__main__":
    threading.Thread(target=server_connection, daemon=True).start()

    while True:
        user_input = input()
        if user_input == "q":
            print("Server shutting down!")
            exit(0)
