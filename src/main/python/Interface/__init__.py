import pygame

pygame.init()

win = pygame.display.set_mode((1200, 600))
pygame.display.set_caption("Build-A-Tree")

font = pygame.font.SysFont(None, 80)


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
    # Player 1
    x = 500
    y = 250

    Jump1 = False
    jumpCount1 = 10

    # Player 2
    w = 500
    s = 250

    Jump2 = False
    jumpCount2 = 10

    running = True
    while running:
        pygame.time.delay(100)
        win.fill((0, 0, 0))

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    running = False

        keys = pygame.key.get_pressed()

        # Player 1 Keys
        if keys[pygame.K_LEFT] and x > 10:
            x -= 15

        if keys[pygame.K_RIGHT] and x < 800:
            x += 15

        if not Jump1:
            if keys[pygame.K_UP] and y > 100:
                Jump1 = True

            if keys[pygame.K_DOWN] and y < 500:
                y += 5
        else:
            if jumpCount1 >= -10:
                neg = 1
                if jumpCount1 < 0:
                    neg = -1
                y -= (jumpCount1 ** 2) * 0.5 * neg
                jumpCount1 -= 1

            else:
                Jump1 = False
                jumpCount1 = 10

        # Player 2 Keys
        if keys[pygame.K_a] and w > 10:
            w -= 15

        if keys[pygame.K_d] and w < 800:
            w += 15

        if not Jump2:
            if keys[pygame.K_w] and s > 100:
                Jump2 = True

            if keys[pygame.K_s] and s < 500:
                s += 5
        else:
            if jumpCount2 >= -10:
                neg = 1
                if jumpCount2 < 0:
                    neg = -1
                s -= (jumpCount2 ** 2) * 0.5 * neg
                jumpCount2 -= 1

            else:
                Jump2 = False
                jumpCount2 = 10

        pygame.draw.rect(win, (0, 255, 0), (x, y, 40, 60))
        pygame.draw.rect(win, (0, 0, 128), (w, s, 40, 60))

        pygame.display.update()


main_menu()
