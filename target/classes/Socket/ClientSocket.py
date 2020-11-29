import socket
import threading

class ClientSocket:

    def __init__(self):
        Host = "localhost"
        PORT: 996
        self.socket_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket_client.connect((Host, 996))
        self.thread = threading.Thread(target=self.keep_listen())
        self.msg = ""

    def keep_listen(self):
        while True:
            self.msg = self.socket_client.recv(1024)
            print(self.msg.decode())
            self.send_msg("holaserver")

    def send_msg(self, msg):
        self.socket_client.send(len(msg).to_bytes((msg.__sizeof__().bit_length() + 7) // 8, 'big'))
        msg = msg.encode(encoding='UTF-8', errors='strict')
        print(msg)
        self.socket_client.sendall(msg)