import json


class Token:
    def __init__(self, value, type):
        self.int_value = value
        self.tree_type = type


def Json_To_Token(string):
    sub_object = json.load(string)
    token = Token(sub_object["value"], sub_object["type"])
    return token
