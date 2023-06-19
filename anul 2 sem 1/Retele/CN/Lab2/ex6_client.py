import socket
import random
import struct
import time

SERVER_IP = '192.168.1.6'
SERVER_PORT = 9999

LARGER = 'larger'
SMALLER = 'smaller'

if __name__ =='__main__':
    try:
        sock = socket.create_connection((SERVER_IP, SERVER_PORT))
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)

    welcome_message = sock.recv(1024)
    print(welcome_message.decode('ascii'))

    st = 4
    end = 2**17-1
    finished = False
    while not finished:
        my_nr = random.randint(st, end)

        try:
            sock.sendall(struct.pack('!I', my_nr))
            answer = sock.recv(128)
            answer = answer.decode('ascii')

            print(f'Sent: {my_nr} -> Answer: {answer}')

        except socket.error as msg:
            print(msg.strerror)
            sock.close()
            exit(-2)

        if answer == LARGER:
            st = my_nr + 1
        elif answer == SMALLER:
            end = my_nr - 1
        else:
            print(answer)
            finished = True

        time.sleep(0.5)

    sock.close()