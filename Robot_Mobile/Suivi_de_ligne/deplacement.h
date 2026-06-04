#ifndef DEPLACEMENT_H
#define DEPLACEMENT_H

#include <stdint.h>

void horloge_init(void);
void moteurs_init(void);
void moteurs_commander(int16_t vitesse_gauche, int16_t vitesse_droite);

#endif
