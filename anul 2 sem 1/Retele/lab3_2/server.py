import socket, random, threading, time, struct

server_address = '127.0.0.1'
server_port = 1234

clients_list = []
number_guessed = False

low_value = 1
high_value = 65000
my_num = random.randint(low_value, high_value)

def connection_thread():
    global server_address, server_port, number_guessed
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        rs.bind(('127.0.0.1', 1234))
        #rs.listen()
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)
    print("SERVER LISTENING ON: " + server_address + ":" + str(server_port))

    while number_guessed == False:
        data, addr = rs.recvfrom(4)
        if addr not in clients_list:
            clients_list.append(addr)
        print("DATA RECEIVED: " + str(struct.unpack("!H", data)[0]) + " from: " + str(addr))
        client_number = struct.unpack("!H", data)[0]
        if client_number > my_num:
            rs.sendto(b'S', addr)
        elif client_number < my_num:
            rs.sendto(b'H', addr)
        elif client_number == my_num:
            number_guessed = True
            rs.sendto(b'G', addr)

            for client in clients_list:
                if client != addr:
                    rs.sendto(b'L', client)
    print("PROCESS ENDED!")

if __name__ == "__main__":
    print("RANDOM VALUE: " + str(my_num))
    threading.Thread(target=connection_thread, daemon=True).start()
    while True:
        user_input = input()
        if user_input == "q":
            print("Shutting down server!")
            exit(0)
