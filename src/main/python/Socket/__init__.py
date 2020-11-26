import socket
import threading
import Factory


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
            self.recieve_msg(self.msg.decode())

    def send_msg(self, msg):
        self.socket_client.send(len(msg).to_bytes((msg.__sizeof__().bit_length() + 7) // 8, 'big'))
        msg = msg.encode(encoding='UTF-8', errors='strict')
        print(msg)
        self.socket_client.sendall(msg)

    def recieve_msg(self, msg):
        if msg == "win":
            pass
        elif msg == "":
            pass
        else:
            Factory.Json_To_Token(msg)
