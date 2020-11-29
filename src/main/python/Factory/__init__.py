import json
import Interface


class Token:
    def __init__(self, value, type):
        self.player = None
        self.int_value = value
        self.tree_type = type

class Challenge:
    def __init__(self, tree, deeper, grade):
        self.treeType = tree
        self.deep = deeper
        self.order = grade
        self.timeleft = 2000
        self.reward = 5000
