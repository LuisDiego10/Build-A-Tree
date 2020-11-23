import socket

Host = "127.0.0.1"
PORT: 669


class ClientSocket:
    def __init__(self, port=PORT):
        socket_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        socket_client.connect((Host, port))

    def keep_listen(self):
