import pygame
import random


class Tokens(pygame.sprite.Sprite):
    def __init__(self):
        super().__init__()
        self.image = pygame.image.load("circulo.png")
        self.rect = self.image.get_rect()

    def update(self):
        self.rect.y += 1
        if self.rect.y > 600:
            self.rect.y = -10
            self.rect.x = random.randrange(600)


class Player(pygame.sprite.Sprite):
    cambio_y = 0
    cambio_x = 0
    def __init__(self):
        super().__init__()
        self.Jump = False
        self.jumpCount = 10
        self.image = pygame.image.load("Player1.png")
        self.rect = self.image.get_rect()
        self.rect.x = 240
        self.rect.y = 360

    def calc_grav(self):
        if self.cambio_y == 0:
            self.cambio_y = 1
        else:
            self.cambio_y += .35

        if self.rect.y >= 590 - self.rect.height and self.cambio_y >=0:
            self.cambio_y = 0
            self.rect.y = 590 - self.rect.height
    def update(self):
        self.calc_grav()
        self.rect.x += self.cambio_x

class Plataforms(pygame.sprite.Sprite):
    def __init__(self):
        super().__init__()
        self.image = pygame.image.load("Plataforma.png")
        self.rect = self.image.get_rect()
        self.rect.x = 240
        self.rect.y = 480


background = pygame.image.load("backgound.jpg")
pygame.init()
win = pygame.display.set_mode((1200, 600))
pygame.display.set_caption("Build-A-Tree")
font = pygame.font.SysFont(None, 80)
##Tokens
tokens_list = pygame.sprite.Group()
all_sprite_list = pygame.sprite.Group()
all_platforms_list = pygame.sprite.Sprite()

for i in range(9):
    triangule = Tokens()
    triangule.image = pygame.image.load("triangulo.png")
    triangule.rect.x = random.randrange(900)
    triangule.rect.y = random.randrange(600)

    tokens_list.add(triangule)
    all_sprite_list.add(triangule)

# Instances
player1 = Player()
player1.rect.x = 500
player2 = Player()
player2.image = pygame.image.load("Player2.png")

plataform1 = Plataforms()

plataform2 = Plataforms()
plataform2.rect.x = 20
plataform2.rect.y = 300
plataform2.image = pygame.transform.scale(plataform2.image, (220, 60))

plataform3 = Plataforms()
plataform3.rect.x = 670
plataform3.rect.y = 300
plataform3.image = plataform2.image



all_sprite_list.add(player1)
all_sprite_list.add(player2)
all_sprite_list.add(plataform1)
all_sprite_list.add(plataform2)
all_sprite_list.add(plataform3)

all_platforms_list.add(plataform1)
all_platforms_list.add(plataform2)
all_platforms_list.add(plataform3)


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


def game():
    running = True
    while running:
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
        # Collitions
        # Player 1 Keys
        if keys[pygame.K_LEFT] and player1.rect.x > 10:
            player1.rect.x -= 15

        if keys[pygame.K_RIGHT] and player1.rect.x < 800:
            player1.rect.x += 15

        if not player1.Jump:
            if keys[pygame.K_UP] and player1.rect.y > 100:
                player1.Jump = True
            if keys[pygame.K_DOWN] and player1.rect.y < 500:
                player1.rect.y += 5
        else:
            if player1.jumpCount >= -10:
                neg = 1
                if player1.jumpCount < 0:
                    neg = -1
                player1.rect.y -= (player1.jumpCount ** 2) * 0.5 * neg
                player1.jumpCount -= 1
            else:
                player1.Jump = False
                player1.jumpCount = 10

        if keys[pygame.K_UP] and player1.rect.y > 100:
            if player1.jumpCount >= 0:
                neg = 1
                if player1.jumpCount < 0:
                    neg = -1
                player1.rect.y -= (player1.jumpCount * 4) - (player1.jumpCount ** 0.5 // 1)
                player1.jumpCount -= 1

            else:
                player1.Jump = False
                player1.jumpCount = 10

        # Player 2 Keys
        if keys[pygame.K_a] and player2.rect.x > 10:
            player2.rect.x -= 15

        if keys[pygame.K_d] and player2.rect.x < 850:
            player2.rect.x += 15

        if not player2.Jump:
            if keys[pygame.K_w] and player2.rect.y > 100:
                player2.Jump = True

            if keys[pygame.K_s] and player2.rect.y < 500:
                player2.rect.y += 5
        else:
            if player2.jumpCount >= -30:
                neg = 1
                if player2.jumpCount < 0:
                    neg = -1
                player2.rect.y -= (player2.jumpCount ** 2) * 0.5 * neg
                player2.jumpCount -= 1

            else:
                player2.Jump = False
                player2.jumpCount = 10
        tokens_list.update()
        tokens_hit_list = pygame.sprite.spritecollide(player1, tokens_list, True)
        tokens_hit_list = pygame.sprite.spritecollide(player2, tokens_list, True)
        all_sprite_list.draw(win)
        pygame.display.flip()
        pygame.display.update()


main_menu()
