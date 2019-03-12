from ClientTCP import main as TCPmain
from ClientUDP import main as UDPmain
import sys


def main():
    index = -1
    if len(sys.argv) == 7:
        index = 6
    elif len(sys.argv) == 8:
        index = 7
    else:
        print("Wrong number of arguments")
        print("Format: python ClientUDP.py <ID> <port of this client> <next clients IP> <next clients port> [token] <protocol>")
        exit(1)

    if sys.argv[index] == 'tcp':
        TCPmain(sys.argv[:index])
    elif sys.argv[index] == 'udp':
        UDPmain(sys.argv[:index])
    else:
        print("Invalid last argument, use 'tcp' or 'udp'")


if __name__ == '__main__':
    main()
