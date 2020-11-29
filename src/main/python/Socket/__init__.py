import socket
import threading
import Factory
import json


class ClientSocket(threading.Thread):

    def __init__(self):
        super().__init__()
        Host = "localhost"
        PORT: 996
        self.socket_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket_client.connect((Host, 996))
        self.tokens = []
        self.challenge=None

    def run(self):
        while True:
            msg = self.socket_client.recv(1024)
            print("\n")
            print(msg)
            msg = msg.decode()
            self.recieve_msg(msg)

    def send_msg(self, msg):
        msg=json.dumps(msg)
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
                msg = msg[1:]
                print(msg[1:])
                sub_object = json.loads(msg[1:])
                print(sub_object)
                if "reward" in msg:
                    object = Factory.challenge()
                    object.deep = sub_object["deep"]
                    object.treeType = sub_object["treeType"]
                    object.order = sub_object["order"]
                    self.challenge=object
                    print("a4")

                else:
                    object = Factory.token()
                    object.int_value = sub_object["int_value"]
                    object.tree_type = sub_object["tree_type"]
                    self.tokens.append(object)
                    print("a4")
            except json.JSONDecodeError:
                print("error")
                pass
            finally:
                pass
