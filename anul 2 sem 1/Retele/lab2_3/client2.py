import socket, struct, random, sys, time

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as msg:
        print("Error: ", msg.strerror)
        exit(0)

    data = s.recv(1024)
    print(data.decode('ascii'))

    my_num = random.uniform(10.7, 100.8)
    print("GENERATED NUMBER: " + str(my_num))

    try:
        s.sendall(struct.pack("!f", my_num))
        result = s.recv(1024)
    except socket.error as msg:
        print(msg.strerror)
        s.close()

    print(result.decode('ascii'))
