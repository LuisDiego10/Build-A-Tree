import socket
import threading
from Factory import *
import json
from Interface import *


class ClientSocket(threading.Thread):

    def __init__(self):
        super().__init__()
        Host = "localhost"
        PORT: 996
        self.socket_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket_client.connect((Host, 996))

    def run(self):
        while True:
            msg = self.socket_client.recv(1024)
            print("\n")
            print(msg)
            msg = msg.decode()
            self.recieve_msg(msg)

    def send_msg(self, msg):
        self.socket_client.send(len(msg).to_bytes((msg.__sizeof__().bit_length() + 7) // 8, 'big'))
        msg = msg.encode(encoding='UTF-8', errors='strict')
        self.socket_client.sendall(msg)

    def recieve_msg(self, msg):
        if msg == "win":
            pass
        elif msg == "":
            pass
        else:
            try:
                print(msg)
                msg= msg[1:]
                sub_object = json.loads(msg)
                print(sub_object)
                if "reward" in msg:
                    sub_object = Challenge(sub_object["treeType"], sub_object["deep"], sub_object["order"])
                    Interface.check_msg(sub_object)
                    print("a4")

                else:
                    sub_object = Token(sub_object["value"], sub_object["type"])
                    Interface.check_msg(sub_object)
                    print("a4")
            except json.JSONDecodeError:
                print("error")
                pass
            finally:
                pass
