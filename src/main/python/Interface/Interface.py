import pygame
import random
class Tokens (pygame.sprite.Sprite):
    def __init__(self):
        super().__init__()
        self.image=pygame.image.load("circulo.png")
        self.rect=self.image.get_rect()

    def update(self):
        self.rect.y+=1
        if self.rect.y>600:
            self.rect.y=-10
            self.rect.x=random.randrange(600)

class Player(pygame.sprite.Sprite):
    def __init__(self):
        super().__init__()
        self.image=pygame.image.load("Player1.png")
        self.rect=self.image.get_rect()


background = pygame.image.load("backgound.jpg")
pygame.init()
win = pygame.display.set_mode((1200, 600))
pygame.display.set_caption("Build-A-Tree")
font = pygame.font.SysFont(None, 80)
##Tokens
tokens_list = pygame.sprite.Group()
all_sprite_list = pygame.sprite.Group()

for i in range(10):
    triangule = Tokens()
    triangule.image=pygame.image.load("triangulo.png")
    triangule.rect.x = random.randrange(900)
    triangule.rect.y = random.randrange(600)

    tokens_list.add(triangule)
    all_sprite_list.add(triangule)

player1 = Player()
all_sprite_list.add(player1)

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
            pygame.display.update()
            all_sprite_list.update()
            mouse_pos = pygame.mouse.get_pos()
            player1.rect.x = mouse_pos[0]
            player1.rect.y = mouse_pos[1]
            tokens_hit_list=pygame.sprite.spritecollide(player1,tokens_list,True)
            all_sprite_list.draw(win)
            pygame.display.flip()

main_menu()