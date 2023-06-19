import socket
import threading
import random
import struct
import sys
import time

random.seed()
server_random_num = random.uniform(10.7, 100.8)
mylock=threading.Lock()
time_passed=10.0
best_crf=10000000
threads = []
client_count=0
client_sockets=[]
best_socket=0

def worker(cs):
    global client_count, best_crf, best_socket
    print("Hello client #", str(client_count), " ", cs.getpeername(), " ", cs)
    message = "Hello client #" + str(client_count)
    cs.sendall(bytes(message, 'ascii'))

    float_number = cs.recv(4)
    float_number = struct.unpack('!f', float_number)[0]
    print(float_number)

    mylock.acquire()
    if abs(server_random_num - float_number) < best_crf:
        best_crf = abs(server_random_num - float_number)
        best_socket = cs
    mylock.release()

def resetServer():
    pass

if __name__ == '__main__':
    print(server_random_num)
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.settimeout(10)
        rs.listen(5)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)

    while True:
        try:
            client_socket, addr = rs.accept()
            t = threading.Thread(target=worker, args=(client_socket,))
            threads.append(t)
            client_count+=1
            client_sockets.append(client_socket)
            t.start()
        except socket.timeout as msg:
            print(msg)

            for socket_elem in client_sockets:
                if socket_elem == best_socket:
                    message = "You have the best guess with an error of " + str(best_crf)
                    socket_elem.sendall(bytes(message, 'utf-8'))
                else:
                    socket_elem.sendall(bytes("You Lost!", 'utf-8'))
                socket_elem.close()

            exit(0)
