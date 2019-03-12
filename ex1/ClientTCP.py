import socket
import sys
from datetime import datetime
from threading import Thread, Lock
from time import sleep
from TokenType import TokenType
from random import randrange

my_ID = None
my_ip = None
my_port = None
next_client_ip = None
next_client_port = None

logger_ip = "230.0.0.0"
logger_port = 9000

token = False
token_ID = None
verified_token = False

my_socket = None
next_socket = None
logger_socket = None
conn = None

token_lock = Lock()
next_lock = Lock()
conn_lock = Lock()


def get_arguments(argv):
    global my_ID, my_ip, my_port, next_client_ip, next_client_port, token, token_ID

    if len(argv) > 7 or len(argv) < 6:
        print("Wrong number of arguments")
        print("Format: python ?.py <ID> <IP of this client> <port of this client> <next clients IP> <next clients port> [token]")
        exit(1)
    elif len(argv) == 7:
        if argv[6] == "token":
            token = True
            token_ID = str(randrange(1, 100000))
        else:
            print("Invalid last argument - if you want it to be with token, as last argument write 'token'")
            exit(1)

    my_ID = argv[1]
    my_ip = argv[2]
    try:
        my_port = int(argv[3])
        next_client_port = int(argv[5])
    except ValueError:
        print("Port needs to be a number")
        exit(1)
    next_client_ip = argv[4]


def check_if_proper_ip(ip_addr):
    list = ip_addr.split(":")
    if len(list) != 2:
        return False
    try:
        if not 1024 <= int(list[1]) <= 65535:
            return False
    except ValueError:
        return False
    list = list[0].split(".")
    if len(list) != 4:
        return False
    for x in list:
        try:
            if not 0 <= int(x) <= 255:
                return False
        except ValueError:
            return False
    return True


def infinite_listening():
    global conn
    while True:
        temp_conn, addr = my_socket.accept()
        conn_lock.acquire()
        conn.close()
        conn = temp_conn
        conn_lock.release()


def listening():
    global next_client_ip, next_client_port, next_socket
    temp_conn, addr = my_socket.accept()
    message = temp_conn.recv(1024).decode("utf-8")
    temp_conn.close()
    message = message.split(" ")
    if message[0] == str(TokenType.CONNECT.value):
        next_lock.acquire()
        next_socket.close()
        next_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        next_socket.connect((message[1], int(message[2])))
        next_socket.send(bytes("{} {} {}".format(TokenType.ANSWER.value, next_client_ip, next_client_port), 'utf-8'))
        next_client_ip = message[1]
        next_client_port = int(message[2])
        next_lock.release()
    else:
        print("Connection error")
        exit(1)


def init(argv):
    global my_socket, logger_socket, my_ip, conn, next_socket, next_client_ip, next_client_port
    get_arguments(argv)
    next_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    my_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    my_socket.bind((my_ip, my_port))
    print("My IP: {}:{}".format(my_socket.getsockname()[0], my_socket.getsockname()[1]))
    logger_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    if my_ip == next_client_ip and my_port == next_client_port:
        my_socket.listen(1)
        print("Waiting for connection...")
        conn, addr = my_socket.accept()
        message = conn.recv(1024).decode("utf-8")
        message = message.split(" ")
        if message[0] == str(TokenType.CONNECT.value):
            answer = "{} {} {}".format(TokenType.ANSWER.value, next_client_ip, next_client_port)
            next_client_ip = message[1]
            next_client_port = int(message[2])
            next_socket.connect((next_client_ip, next_client_port))
            next_socket.send(bytes(answer, 'utf-8'))
            print("Connected")
            Thread(target=infinite_listening).start()
        else:
            print("Connection error")
            exit(1)
    else:
        my_socket.listen(1)
        next_socket.connect((next_client_ip, next_client_port))
        next_socket.send(bytes("{} {} {}".format(TokenType.CONNECT.value, my_ip, my_port), 'utf-8'))
        print("Waiting for connection...")
        conn, addr = my_socket.accept()
        message = conn.recv(1024).decode("utf-8")
        message = message.split(" ")
        if message[0] == str(TokenType.ANSWER.value):
            if next_client_ip != message[1] or next_client_port != int(message[2]):
                next_socket.close()
                next_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                next_client_ip = message[1]
                next_client_port = int(message[2])
                next_socket.connect((next_client_ip, next_client_port))
            print("Connected")
            Thread(target=listening).start()
        else:
            print("Connection error")
            exit(1)


def keep_token():
    global token
    sleep(1)
    token_lock.acquire()
    if token:
        next_lock.acquire()
        next_socket.send(bytes("{} {}".format(TokenType.EMPTY.value, token_ID), 'utf-8'))
        token = False
        next_lock.release()
    token_lock.release()


def send_message():
    global token
    while True:
        print("Enter destination address (<IP>:<port>):")
        dest_address = input()
        if not check_if_proper_ip(dest_address):
            print("Bad destination address")
            continue
        print("Enter message: ")
        message = input()
        while token_ID is None:
            pass
        message = "{} {} {} {} {}".format(TokenType.MESSAGE.value, dest_address, my_ID, token_ID, message)
        token_lock.acquire()
        while not token:
            token_lock.release()
            sleep(1)
            token_lock.acquire()
        next_lock.acquire()
        next_socket.send(bytes(message, 'utf-8'))
        next_lock.release()
        token = False
        token_lock.release()
        print("Message successfully sent")


def acquire_token():
    global token
    token_lock.acquire()
    logger_socket.sendto(bytes("{} acquired token - {}".format(my_ID, datetime.now()), 'utf-8'), (logger_ip, logger_port))
    token = True
    token_lock.release()


def is_token_id_ok(tokenID):
    global token_ID, verified_token
    if token_ID is not None:
        if verified_token:
            return tokenID == token_ID
        else:
            token_ID = tokenID
            verified_token = True
            return True
    else:
        token_ID = tokenID
        verified_token = True
        return True


def receive_messages():
    global token, next_client_ip, next_client_port
    buff = []
    while True:
        conn_lock.acquire()
        buff = conn.recv(1024)
        conn_lock.release()
        message = str(buff, 'utf-8')
        try:
            type = int(message.split(" ")[0])
        except ValueError:
            continue
        if type == TokenType.EMPTY.value:
            if is_token_id_ok(message.split(" ")[1]):
                acquire_token()
                Thread(target=keep_token).start()
            else:
                print("Deleting token")
        elif type == TokenType.MESSAGE.value:
            message = message.split(" ")
            if not is_token_id_ok(message[3]):
                print("Deleting token")
                continue
            ip_addr = message[1].split(":")
            if ip_addr[0] == my_ip and int(ip_addr[1]) == my_port:
                acquire_token()
                print("Received message: '{}' from {}".format(" ".join(message[4:]), message[2]))
                Thread(target=keep_token).start()
            else:
                if message[2] == my_ID:
                    print("Deleting message")
                    acquire_token()
                    Thread(target=keep_token).start()
                    continue
                print("Forwarding message")
                sleep(1)
                logger_socket.sendto(bytes("{} acquired token - {}".format(my_ID, datetime.now()), 'utf-8'), (logger_ip, logger_port))
                next_lock.acquire()
                next_socket.send(buff)
                next_lock.release()


def main(argv=sys.argv):
    init(argv)
    if token:
        Thread(target=keep_token).start()
    Thread(target=receive_messages).start()
    Thread(target=send_message).start()


if __name__ == '__main__':
    main()
