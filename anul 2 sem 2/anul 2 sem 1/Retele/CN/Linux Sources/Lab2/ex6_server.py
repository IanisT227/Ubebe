import socket, threading, struct, random

SERVER_PORT = 9999

server_number = random.randint(1, 2**17-1)

socket_list = []
tries_list = []
threads = []
game_ended = False
client_count = 0
winner = -1
lock = threading.Lock()
event = threading.Event()
event.clear()

LARGER = b'larger'
SMALLER = b'smaller'
WIN = 'You win!'
LOSE = 'You lost'


def reset_game():
    global lock, event, winner, threads, tries_list, socket_list, server_number, game_ended, client_count

    while True:
        event.wait()
        for t in threads:
            t.join()

        for i in range(len(tries_list)):
            if i == winner:
                message = WIN + f' After {tries_list[i]} tries!'
            else:
                message = LOSE + f' After {tries_list[i]} tries!'

            message = bytes(message, 'ascii')
            socket_list[i].sendall(message)


        event.clear()
        lock.acquire()
        server_number = random.randint(1, 2**17-1)
        socket_list = []
        tries_list = []
        threads = []
        game_ended = False
        client_count = 0
        winner = -1
        lock.release()

        print(f'Game started! Number is {server_number}')



def worker(client_sock, client_address, client_nr):
    global lock, event, winner, server_number, tries_list, game_ended

    print(f'Client connected: {client_address}')

    message = f'You are client {str(client_nr)}!'
    client_sock.sendall(bytes(message, 'ascii'))

    tries = 0
    while not game_ended:
        try:
            tries += 1
            number =  client_sock.recv(4)
            number = struct.unpack('!I', number)[0]

            if number < server_number:
                client_sock.sendall(LARGER)
            elif number > server_number:
                client_sock.sendall(SMALLER)
            else:
                lock.acquire()
                game_ended = True
                winner = client_nr
                event.set()
                lock.release()

        except socket.error as msg:
            print(msg.strerror)
            break

    tries_list[client_nr] = tries


if __name__ == '__main__':
    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.bind(('0.0.0.0', SERVER_PORT))
        sock.listen(2)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)

    t = threading.Thread(target=reset_game, daemon=True)
    t.start()
    
    print(f'Game started! Number is {server_number}')
    while True:
        client_sock, client_address = sock.accept()
        t = threading.Thread(target=worker, args=(client_sock, client_address, client_count))
        threads.append(t)
        socket_list.append(client_sock)
        tries_list.append(0)
        client_count += 1
        t.start()