import socket
import select
import sys

SERVER_PORT = 9999

read_fds = []
listen_sock = 0
client_count = 0

def close_connection(client):
    global client_count

    print(f'Connection to client {client.getpeername()[1]} closed!')
    read_fds.remove(client)
    client_count -= 1

def send_to_all(data, exception):
    global listen_sock

    for fd in read_fds:
        if fd != listen_sock and fd != exception:
            try:
                fd.sendall(data)
            except socket.error as e:
                print(e.strerror)


if __name__ == '__main__':
    try:
        listen_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        listen_sock.bind(('0.0.0.0', SERVER_PORT))
        listen_sock.listen(2)

    except socket.error as e:
        print(e.strerror)
        sys.exit(-1)

    read_fds.append(listen_sock)

    while True:
        try:
            rlist, _, _ = select.select(read_fds, [], [])
        except Exception as e:
            print(e)
            sys.exit(-1)

        for fd in rlist:
            if fd == listen_sock:
                client_sock, client_address = listen_sock.accept()
                client_count += 1
                read_fds.append(client_sock)

                host, port = client_sock.getpeername()
                print(f'\t>>> New connection from client {host}:{port}')
                client_sock.sendall(bytes(f'Hi! You are client {host}:{port}!\nThere are {client_count} clients connected!\n', 'ascii'))

            else:
                try:
                    data = fd.recv(4089)

                    if len(data) == 0:
                        close_connection(fd)
                        continue

                    send_to_all(data, fd)

                except socket.error:
                    close_connection(fd)


