#include <msp430.h>
#include <stdint.h>
#include "deplacement.h"

#define PERIODE_PWM 1000      // période PWM

void horloge_init(void)
{
    // DCO 1 MHz
    BCSCTL1 = CALBC1_1MHZ;
    DCOCTL  = CALDCO_1MHZ;
}

void moteurs_init(void)
{
    // Mapping conforme au tableau :
    // Left Motor Dir  -> P2.1
    // Left Motor PWM  -> P2.2 (TA1.1)
    // Right Motor Dir -> P2.5
    // Right Motor PWM -> P2.4 (TA1.2)

    // broches sens moteurs
    P2DIR  |= BIT1 | BIT5;        // P2.1 gauche, P2.5 droite
    P2SEL  &= ~(BIT1 | BIT5);
    P2SEL2 &= ~(BIT1 | BIT5);
    P2OUT  &= ~(BIT1 | BIT5);

    // broches PWM moteurs
    P2DIR  |= BIT2 | BIT4;        // P2.2 = TA1.1, P2.4 = TA1.2
    P2SEL  |= BIT2 | BIT4;
    P2SEL2 &= ~(BIT2 | BIT4);

    // configuration Timer1_A PWM
    TA1CCR0  = PERIODE_PWM - 1;
    TA1CCTL1 = OUTMOD_7;          // TA1.1 -> P2.2 (gauche)
    TA1CCTL2 = OUTMOD_7;          // TA1.2 -> P2.4 (droit)
    TA1CCR1  = 0;
    TA1CCR2  = 0;
    TA1CTL   = TASSEL_2 | MC_1 | TACLR;   // SMCLK, up mode
}

void moteurs_commander(int16_t vitesse_gauche, int16_t vitesse_droite)
{
    // limitation simple
    if (vitesse_gauche > 100)  vitesse_gauche = 100;
    if (vitesse_gauche < -100) vitesse_gauche = -100;
    if (vitesse_droite > 100)  vitesse_droite = 100;
    if (vitesse_droite < -100) vitesse_droite = -100;

    // moteur gauche (P2.1 + TA1CCR1)
    if (vitesse_gauche >= 0)
    {
        P2OUT &= ~BIT1;        // avant gauche
    }
    else
    {
        P2OUT |= BIT1;         // arrière gauche
        vitesse_gauche = -vitesse_gauche;
    }

    // moteur droit (P2.5 + TA1CCR2)
    if (vitesse_droite >= 0)
    {
        P2OUT |= BIT5;         // avant droit (logique inversée sur la carte)
    }
    else
    {
        P2OUT &= ~BIT5;        // arrière droit
        vitesse_droite = -vitesse_droite;
    }

    // rapport cyclique PWM 0–100 %
    TA1CCR1 = (uint16_t)((PERIODE_PWM * (uint16_t)vitesse_gauche) / 100);
    TA1CCR2 = (uint16_t)((PERIODE_PWM * (uint16_t)vitesse_droite) / 100);
}
