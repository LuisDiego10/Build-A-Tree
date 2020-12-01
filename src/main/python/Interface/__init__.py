import pygame
import random

import Factory
import Socket
"""Class Players
Contains all attributes and methods to Players
This class extends pygame Sprite class
"""
class Players(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.x = 220
        self.y = 320
        self.Jump = False
        self.jumpCount = 10
        self.color = (0, 0, 0)
        self.width = 40
        self.height = 60

    def draw_player(self):
        pygame.draw.rect(win, self.color, (self.x, self.y, self.width, self.height))

"""Class Plataform
Contains all attributes and methods to Plataform
This class extends pygame Sprite class
"""
class Plataform(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.xp = 0
        self.yp = 0
        self.color = (255, 0, 0)
        self.width = 150
        self.height = 20

    def draw_plataform(self):
        pygame.draw.rect(win, self.color, (self.xp, self.yp, self.width, self.height))
"""Class Tokens
Contains all attributes and methods to Tokens
This class extends pygame Sprite class
"""
class Tokens (pygame.sprite.Sprite):
    def __init__(self):
        super().__init__()
        self.image=pygame.image.load("triangulo.png")
        self.rect=self.image.get_rect()#Obtenemos las coordenadas del Sprite

background = pygame.image.load("backgound.jpg")
pygame.init()
win = pygame.display.set_mode((1200, 600))
pygame.display.set_caption("Build-A-Tree")
font = pygame.font.SysFont(None, 80)

# //////Sockets/////// #
#   global Variables  #
socket = Socket.ClientSocket()
socket.start()
challenge = None
tokens = []
# Functions for socket#
def check_msg():
    global socket, challenge, tokens
    if socket.tokens != tokens:
        tokens = socket.tokens
    if socket.challenge != challenge:
        challenge = socket.challenge
    else:
        pass
    # print(challenge, tokens[0].tree_type)


# ////////////////////#

def draw_text(text, font, color, surface, x, y):
    textobj = font.render(text, 1, color)
    textrect = textobj.get_rect()
    textrect.topleft = (x, y)
    surface.blit(textobj, textrect)
    "Show Tokens"
tokens_arr=pygame.sprite.Group() #Almacenar tokens de la clase de aquí cuando ya esté dibujados
allspriteslist=pygame.sprite.Group()

for i in range(2):
    triangule=Tokens()
    triangule.rect.x=random.randrange(840)
    triangule.rect.y=random.randrange(600)
    tokens_arr.add(triangule)
    allspriteslist.add(triangule)
    allspriteslist.draw(win)


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

"""This function is responsible for running the game"""
def game():
    check_msg()
    # socket.send_msg(tokens[0].__dict__)
    player1 = Players()
    player2 = Players()

    plataform1 = Plataform()
    plataform2 = Plataform()
    plataform3 = Plataform()
    plataform_princ = Plataform()

    falling_velocity = 3

    running = True
    while running:
        # socket.send_msg(tokens[0].__dict__)
        # tokens.pop(0)
        # check_msg()
        # print(len(tokens))
        pygame.time.delay(100)
        win.fill((0, 0, 0))
        win.blit(background, [-1000, 0])

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    running = False
        keys = pygame.key.get_pressed()
        allspriteslist.draw(win)
            # Collitions
        if player1.y <= plataform1.yp + player1.height and player1.y + falling_velocity >= plataform1.yp - player1.height:
            player1.y = plataform1.yp - player1.height

        else:
            player1.y += falling_velocity

        if player1.y <= plataform2.yp + player1.height and player1.y + falling_velocity >= plataform2.yp - player1.height:
            player1.y = plataform2.yp - player1.height

        else:
            player1.y += falling_velocity

        if player1.y <= plataform3.yp + player1.height and player1.y + falling_velocity >= plataform3.yp - player1.height:
            player1.y = plataform3.yp - player1.height

        else:
            player1.y += falling_velocity

        if player1.y <= plataform_princ.yp + player1.height and player1.y + falling_velocity >= plataform_princ.yp - player1.height:
            player1.y = plataform_princ.yp - player1.height

        else:
            player1.y += falling_velocity

            # Player 1 Keys
        if keys[pygame.K_LEFT] and player1.x > 10:
            player1.x -= 15

        if keys[pygame.K_RIGHT] and player1.x < 850:
            player1.x += 15

        if not player1.Jump:
            if keys[pygame.K_UP] and player1.y > 100:
                player1.Jump = True

        else:
            if player1.jumpCount >= -30:
                neg = 1
                if player1.jumpCount < 0:
                    neg = -1
                player1.y -= (player1.jumpCount ** 2) * 0.5 * neg
                player1.jumpCount -= 1

            else:
                player1.Jump = False
                player1.jumpCount = 10

        # Player 2 Keys
        if keys[pygame.K_a] and player2.x > 10:
            player2.x -= 15

        if keys[pygame.K_d] and player2.x < 850:
            player2.x += 15

        if not player2.Jump:
            if keys[pygame.K_w] and player2.y > 100:
                player2.Jump = True

            if keys[pygame.K_s] and player2.y < 500:
                player2.y += 5
        else:
            if player2.jumpCount >= -30:
                neg = 1
                if player2.jumpCount < 0:
                    neg = -1
                player2.y -= (player2.jumpCount ** 2) * 0.5 * neg
                player2.jumpCount -= 1

            else:
                player2.Jump = False
                player2.jumpCount = 10
        "Instances"
        player1.color = 255, 0, 0
        player2.color = 0, 0, 255
        player1.draw_player()
        player2.draw_player()

        plataform1.xp = 75
        plataform1.yp = 300
        plataform1.draw_plataform()

        plataform2.xp = 700
        plataform2.yp = 300
        plataform2.draw_plataform()

        plataform3.xp = 375
        plataform3.yp = 200
        plataform3.width = 200
        plataform3.draw_plataform()

        plataform_princ.xp = 210
        plataform_princ.yp = 450
        plataform_princ.width = 500
        plataform_princ.draw_plataform()
        pygame.display.update()


main_menu()
