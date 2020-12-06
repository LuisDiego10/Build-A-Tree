import pygame
import random
import time

import Socket

# ///////Constants///////#
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
BLUE = (0, 0, 255)
RED = (255, 0, 0)
GREEN = (0, 255, 0)

height_win = 800
width_win = 1200


# ///////Constants///////#

class Tokens(pygame.sprite.Sprite):
    def __init__(self):
        super().__init__()
        self.image = pygame.image.load("circulo.png")
        self.rect = self.image.get_rect()
        self.token = None

    def update(self):
        self.rect.y += 1
        if self.rect.y > 600:
            self.rect.y = -10
            self.rect.x = random.randrange(600)


class Player(pygame.sprite.Sprite):
    change_x = 0
    change_y = 0
    level = 0
    airjump = False
    shield = False
    forcepush = True

    def __init__(self):
        super().__init__()
        self.Jump = False
        self.jumpCount = 10
        self.image = pygame.image.load("Player1.png")
        self.rect = self.image.get_rect()

    def update(self):
        self.calc_grav()
        self.rect.x += self.change_x
        hit_block_list = pygame.sprite.spritecollide(self, self.level.platform_list, False)

        for block in hit_block_list:
            if self.change_x > 0:
                self.rect.right = block.rect.left
            elif self.change_x < 0:
                self.rect.left = block.rect.right
        self.rect.y += self.change_y
        hit_block_list = pygame.sprite.spritecollide(self, self.level.platform_list, False)

        for block in hit_block_list:
            if self.change_y > 0:
                self.rect.bottom = block.rect.top
            elif self.change_y < 0:
                self.rect.top = block.rect.bottom
            self.change_y = 0

    def calc_grav(self):
        if self.change_y == 0:
            self.change_y = 1
        else:
            self.change_y += .35

    def jump(self):
        self.rect.y += 2
        hit_platform_list = pygame.sprite.spritecollide(self, self.level.platform_list, False)
        self.rect.y -= 2
        if len(hit_platform_list) > 0 or self.rect.bottom >= height_win:
            self.change_y = -10

    def left(self):
        self.change_x = -6

    def right(self):
        self.change_x = 6

    def stop(self):
        self.change_x = 0


class Platforms(pygame.sprite.Sprite):
    def __init__(self, height, width):
        super().__init__()
        self.image = pygame.Surface([height, width])
        self.image = pygame.image.load("Plataforma.png")
        self.image = pygame.transform.scale(self.image, (200, 60))
        self.rect = self.image.get_rect()


class Level(object):
    def __init__(self, player1, player2):
        self.platform_list = pygame.sprite.Group()
        self.player1 = player1
        self.player2 = player2

    def update(self):
        self.platform_list.update()

    def draw(self, win):
        background = pygame.image.load("backgound.jpg")
        win.blit(background, [0, 0])
        self.platform_list.draw(win)


class Level1(Level):
    def __init__(self, player1, player2):
        Level.__init__(self, player1, player2)
        level = [[210, 70, 500, 400],
                 [210, 70, 135, 550],
                 [210, 70, 865, 550],
                 [210, 70, 390, 700],
                 [210, 70, 590, 700]]

        for platform in level:
            block = Platforms(platform[0], platform[1])
            block.rect.x = platform[2]
            block.rect.y = platform[3]
            block.player1 = self.player1
            block.player2 = self.player2
            self.platform_list.add(block)


background = pygame.image.load("backgound.jpg")
pygame.init()
win = pygame.display.set_mode((1200, 600))
pygame.display.set_caption("Build-A-Tree")
font = pygame.font.SysFont(None, 80)

# //////Sockets/////// #
#   global Variables  #
socket = Socket.ClientSocket()
socket.start()
time.sleep(2)
challenge = None
tokens = []


# Functions for socket#
def check_msg():
    global socket, challenge, tokens
    tokens = socket.tokens
    for x in tokens:
        create = True
        for a in tokens_list.sprites():
            if x == a.token:
                create = False
        if create:
            figure = Tokens()
            figure.token = x
            if x.tree_type == "class BST.BST":
                figure.image = pygame.image.load("rombo.png")
            if x.tree_type == "class SPLAY.SPLAYTree":
                figure.image = pygame.image.load("triangulo.png")
            if x.tree_type == "class BTree.Btree":
                figure.image = pygame.image.load("cuadrado.png")
            if x.tree_type == "class AVL.AVL":
                figure.image = pygame.image.load("circulo.png")
            figure.rect.x = random.randrange(900)
            figure.rect.y = random.randrange(600)
            tokens_list.add(figure)
            all_sprite_list.add(figure)
    if socket.challenge != challenge:
        challenge = socket.challenge
    else:
        pass


# ////////////////////#

# Tokens#

tokens_list = pygame.sprite.Group()
all_sprite_list = pygame.sprite.Group()
all_platforms_list = pygame.sprite.Group()

check_msg()


def draw_text(text, font, color, surface, x, y):
    textobj = font.render(text, 1, color)
    textrect = textobj.get_rect()
    textrect.topleft = (x, y)
    surface.blit(textobj, textrect)


def main_menu():
    while True:
        win.fill((0, 0, 0))
        draw_text("Build-A-Tree's Main Menu", font, (255, 255, 255), win, 250, 20)
        draw_text("START", font, (255, 255, 255), win, 500, 200)
        click = False
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    pygame.quit()

            if event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    click = True

        mx, my = pygame.mouse.get_pos()
        button_start = pygame.Rect(550, 270, 75, 75)
        if button_start.collidepoint((mx, my)):
            if click:
                game()
        pygame.draw.rect(win, (255, 0, 0), button_start)

        pygame.display.update()


def show_timer():
    aux = 1
    time = pygame.time.get_ticks() / 1000
    if aux == time:
        aux += 1
    counter = font.render("Time: " + str(time), True, (0, 0, 0), (255, 255, 255))
    win.blit(counter, (100, 100))
    pygame.display.update()


def game():
    running = True
    pygame.init()
    dimentions = [width_win, height_win]
    win = pygame.display.set_mode(dimentions)
    pygame.display.set_caption("BUILD-A-TREE")

    player1 = Player()
    player2 = Player()
    player2.image = pygame.image.load("Player2.png")

    level_list = []
    level_list.append(Level1(player1, player2))
    levelact_no = 0
    levelact = level_list[levelact_no]

    all_sprite_list = pygame.sprite.Group()
    player1.level = levelact
    player1.rect.x = 340
    player1.rect.y = height_win - player1.rect.height
    player2.level = levelact
    player2.rect.x = 340
    player2.rect.y = height_win - player2.rect.height

    all_sprite_list.add(player1)
    all_sprite_list.add(player2)
    done = False
    clock = pygame.time.Clock()
    show_timer()

    while not done:
        check_msg()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                done = True
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    player1.left()
                if event.key == pygame.K_RIGHT:
                    player1.right()
                if event.key == pygame.K_UP:
                    player1.jump()
                if event.key == pygame.K_a:
                    player2.left()
                if event.key == pygame.K_d:
                    player2.right()
                if event.key == pygame.K_w:
                    player2.jump()
                if event.key == pygame.K_k and player1.forcepush == True:
                    player2.rect.x += 1000

        if event.type == pygame.KEYUP:
            if event.key == pygame.K_LEFT and player1.change_x < 0:
                player1.stop()
            if event.key == pygame.K_RIGHT and player1.change_x > 0:
                player1.stop()
            if event.key == pygame.K_a and player2.change_x < 0:
                player2.stop()
            if event.key == pygame.K_d and player2.change_x > 0:
                player2.stop()

        tokens_list.update()

        tokens_hit_list = None
        tokens_hit_list = pygame.sprite.spritecollide(player1, tokens_list, True)
        for x in tokens_hit_list:
            x.token.player = 1
            socket.send_msg(x.token.__dict__)
            socket.tokens.remove(x.token)
        tokens_hit_list = None
        tokens_hit_list = pygame.sprite.spritecollide(player2, tokens_list, True)
        for x in tokens_hit_list:
            x.token.player = 2
            socket.send_msg(x.token.__dict__)

        all_sprite_list.update()
        levelact.update()

        if player1.rect.right > width_win:
            player1.rect.right = width_win
        if player1.rect.left < 0:
            player1.rect.left = 0

        if player2.rect.right > width_win:
            player2.rect.right = width_win
        if player2.rect.left < 0:
            player2.rect.left = 0

        levelact.draw(win)
        all_sprite_list.draw(win)
        tokens_list.draw(win)
        clock.tick(60)
        pygame.display.flip()
        pygame.display.update()

    pygame.quit()


if __name__ == "__main__":
    game()
