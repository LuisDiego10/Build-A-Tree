import pygame
import random
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
BLUE = (0, 0, 255)
RED = (255, 0, 0)
GREEN = (0, 255, 0)

height_win=800
width_win=1200

"""
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
"""
class Player(pygame.sprite.Sprite):
    change_x=0
    change_y=0
    level=0

    def __init__(self):
        super().__init__()
        self.Jump = False
        self.jumpCount = 10
        self.image=pygame.image.load("Player1.png")
        self.rect=self.image.get_rect()

    def update(self):
        self.calc_grav()
        self.rect.x+=self.change_x
        hit_block_list=pygame.sprite.spritecollide(self,self.level.plataform_list,False)

        for block in hit_block_list:
            if self.change_x>0:
                self.rect.right=block.rect.left
            elif self.change_x<0:
                self.rect.left=block.rect.right
        self.rect.y+=self.change_y
        hit_block_list=pygame.sprite.spritecollide(self,self.level.plataform_list,False)

        for block in hit_block_list:
            if self.change_y>0:
                self.rect.bottom=block.rect.top
            elif self.change_y<0:
                self.rect.top=block.rect.bottom
            self.change_y=0

    def calc_grav (self):
        if self.change_y==0:
            self.change_y=1
        else:
            self.change_y+=.35


    def jump(self):
        self.rect.y+=2
        hit_plataform_list=pygame.sprite.spritecollide(self,self.level.plataform_list,False)
        self.rect.y-=2
        if len(hit_plataform_list)>0 or self.rect.bottom>=height_win:
            self.change_y=-10
    def left(self):
        self.change_x=-6
    def right(self):
        self.change_x=6
    def stop(self):
        self.change_x=0

class Plataforms(pygame.sprite.Sprite):
    def __init__(self,height,width):
        super().__init__()
        #self.image=pygame.image.load("Plataform.png")
        #self.image= pygame.image.load("Plataform.png")
        self.image=pygame.Surface([height,width])
        self.image.fill(GREEN)
        self.rect=self.image.get_rect()

class Level(object):
    def __init__(self,player1):
        self.plataform_list=pygame.sprite.Group()
        self.player1=player1

    def update(self):
        self.plataform_list.update()

    def draw(self,win):
        background = pygame.image.load("backgound.jpg")
        win.blit(background, [0, 0])
        self.plataform_list.draw(win)

class Level1(Level):
    def __init__(self,player1):
        Level.__init__(self,player1)
        level=[ [210, 70, 800, 500],
                [210, 70, 340, 700],
                [210, 70, 500, 600],
                [210, 70, 400, 400],
                ]
        for plataform in level:
            block=Plataforms(plataform[0],plataform[1])
            block.rect.x=plataform[2]
            block.rect.y=plataform[3]
            block.player1=self.player1
            self.plataform_list.add(block)

def game():
    pygame.init()
    dimentions=[width_win,height_win]
    win=pygame.display.set_mode(dimentions)
    pygame.display.set_caption("BUILD-A-TREE")
    player1=Player()
    level_list=[]
    level_list.append(Level1(player1))
    levelact_no=0
    levelact=level_list[levelact_no]
    all_sprite_list=pygame.sprite.Group()
    player1.level=levelact
    player1.rect.x=340
    player1.rect.y=height_win-player1.rect.height
    all_sprite_list.add(player1)
    done=False
    clock=pygame.time.Clock()
    while not done:
        for event in pygame.event.get():
            if event.type==pygame.QUIT:
                done=True
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    player1.left()
                if event.key == pygame.K_RIGHT:
                    player1.right()
                if event.key == pygame.K_UP:
                    player1.jump()

            if event.type == pygame.KEYUP:
                if event.key == pygame.K_LEFT and player1.change_x < 0:
                    player1.stop()
                if event.key == pygame.K_RIGHT and player1.change_x > 0:
                    player1.stop()
        all_sprite_list.update()
        levelact.update()
        if player1.rect.right>width_win:
            player1.rect.right=width_win
        if player1.rect.left<0:
            player1.rect.left=0
        levelact.draw(win)
        all_sprite_list.draw(win)
        clock.tick(60)
        pygame.display.flip()
    pygame.quit()

if __name__=="__main__":
    game()