import json


class Token:
    def __init__(self, value, type):
        self.int_value = value
        self.tree_type = type

class Challenge:
    def __init__(self, tree, deeper, grade):
        self.treeType = tree
        self.deep = deeper
        self.order = grade
        self.timeleft = 2000
        self.reward = 5000


def Json_To_Object(string):

    sub_object = json.loads(string)
    if "reward" in sub_object:
        sub_object = Challenge(sub_object["treeType"], sub_object["deep"], sub_object["order"])
    else:
        sub_object = Token(sub_object["value"], sub_object["type"])
    return sub_object
