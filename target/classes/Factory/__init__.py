class Challenge:
    def __init__(self):
        self.treeType = None
        self.deep = None
        self.order = None
        self.timeleft = None
        self.reward = None

class Token:
    def __init__(self):
        self.player = None
        self.int_value = None
        self.tree_type = None

def challenge():
    return Challenge()

def token():
    return Token()

