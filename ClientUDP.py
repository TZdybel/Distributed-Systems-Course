import socket
import sys
from datetime import datetime
from threading import Thread, Lock
from time import sleep
from TokenType import TokenType

my_ID = None
my_ip = None
my_port = None
next_client_ip = None
next_client_port = None

logger_ip = "230.0.0.0"
logger_port = 9000

token = False
token_lock = Lock()

my_socket = None
logger_socket = None


def get_arguments():
    global my_ID, my_ip, my_port, next_client_ip, next_client_port, token

    if len(sys.argv) > 7 or len(sys.argv) < 6:
        print("Wrong number of arguments")
        print("Format: python ClientUDP.py <ID> <port of this client> <next clients IP> <next clients port> [token]")
        exit(1)
    elif len(sys.argv) == 7:
        if sys.argv[6] == "token":
            token = True
        else:
            print("Invalid last argument - if you want it to be with token, as last argument write 'token'")
            exit(1)

    my_ID = sys.argv[1]
    my_ip = sys.argv[2]
    try:
        my_port = int(sys.argv[3])
        next_client_port = int(sys.argv[5])
    except ValueError:
        print("Port needs to be a number")
        exit(1)
    next_client_ip = sys.argv[4]


def check_if_proper_ip(ip_addr):
    list = ip_addr.split(".")
    if len(list) != 4:
        return False
    for x in list:
        try:
            if 0 <= int(x) <= 255:
                continue
            else:
                return False
        except ValueError:
            return False
    return True


def init():
    global my_socket, logger_socket, my_ip
    get_arguments()
    my_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    my_socket.bind((my_ip, my_port))
    print("My IP: {}:{}".format(my_socket.getsockname()[0], my_socket.getsockname()[1]))
    logger_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)


def keep_token():
    global token
    sleep(1)
    token_lock.acquire()
    if token:
        my_socket.sendto(bytes(str(TokenType.EMPTY.value), 'utf-8'), (next_client_ip, next_client_port))
        token = False
    token_lock.release()


def send_message():
    global token
    while True:
        print("Enter destination address:")
        dest_address = input()
        if not check_if_proper_ip(dest_address):
            print("Bad IP address")
            continue
        print("Enter message: ")
        message = input()
        message = "{} {} {}".format(TokenType.MESSAGE.value, dest_address, message)
        token_lock.acquire()
        while not token:
            token_lock.release()
            sleep(1)
            token_lock.acquire()
        my_socket.sendto(bytes(message, 'utf-8'), (next_client_ip, next_client_port))
        token = False
        token_lock.release()
        print("Message successfully sent")


def receive_messages():
    global token, next_client_ip, next_client_port
    buff = []
    while True:
        buff, source_address = my_socket.recvfrom(1024)
        token_lock.acquire()
        logger_socket.sendto(bytes("{} acquired token - {}".format(my_ID, datetime.now()), 'utf-8'), (logger_ip, logger_port))
        token = True
        token_lock.release()
        message = str(buff, 'utf-8')
        try:
            type = int(message.split(" ")[0])
        except ValueError:
            print("Type not a number, corrupted message")
            continue
        if type == TokenType.EMPTY.value:
            Thread(target=keep_token).start()
        elif type == TokenType.MESSAGE.value:
            if message.split(" ")[1] == my_ip:
                print("Received message: {}".format(" ".join(message.split(" ")[2:])))
                Thread(target=keep_token).start()
            else:
                print("Forwarding message")
                token_lock.acquire()
                sleep(1)
                token = False
                token_lock.release()
                my_socket.sendto(buff, (next_client_ip, next_client_port))
        elif type == TokenType.CONNECT.value:
            message = "{} {} {} {}".format(TokenType.ANSWER.value, source_address[0], next_client_ip, next_client_port)
            my_socket.sendto(bytes(message, 'utf-8'), source_address)
            next_client_ip = source_address[0]
            next_client_port = int(source_address[1])
        elif type == TokenType.ANSWER.value:
            message = message.split(" ")
            if message[1] == my_ip:
                next_client_ip = message[2]
                next_client_port = int(message[3])
            print("Connected.")


def connect_to_token_ring():
    print("Connecting to token ring...")
    message = "{} {}".format(TokenType.CONNECT.value, next_client_ip)
    my_socket.sendto(bytes(message, 'utf-8'), (next_client_ip, next_client_port))


def main():
    init()
    if token:
        Thread(target=keep_token).start()
    else:
        connect_to_token_ring()
    Thread(target=receive_messages).start()
    Thread(target=send_message).start()


if __name__ == '__main__':
    main()
