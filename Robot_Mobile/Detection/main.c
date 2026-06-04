#include <msp430.h>
#include <stdint.h>

#include "ADC.h"
#include "Afficheur.h"
#include "deplacement.h"
#include "suivi_ligne.h"
#include "detection_obstacle.h"
#define DISTANCE_STOP 5

/* Variables globales */
unsigned int t1 = 0;
unsigned int t2 = 0;
unsigned int duree = 0;
unsigned int distance = 0;
unsigned char etat = 0;

int main(void)
{
    WDTCTL = WDTPW | WDTHOLD;

    horloge_init();
    moteurs_init();
    ADC_init();
    Aff_Init();
    Aff_Efface();
    capteur_US_init();

    __enable_interrupt();
    while (1)
    {

        // PARTIE SUIVE DE LIGNE

        uint16_t g, d;

        ADC_Demarrer_conversion(4);   // A4 = gauche
        g = ADC_Lire_resultat();

        ADC_Demarrer_conversion(5);   // A5 = droite
        d = ADC_Lire_resultat();

        suivi_ligne_update(g, d);
        __delay_cycles(100);

        // PARTIE capteur ultrason

        trigger_ultrason();
       __delay_cycles(60000);   // 60 ms entre deux mesures
    }
}



/* Interruption capture ECHO */
#pragma vector = TIMER0_A0_VECTOR
__interrupt void capture_echo(void)
{
    if(etat == 0)
    {
        /* Front montant : début ECHO */
        t1 = TA0CCR0;
        etat = 1;
    }
    else
    {
        /* Front descendant : fin ECHO */
        t2 = TA0CCR0;

        /* Gestion overflow Timer */
        if(t2 >= t1)
            duree = t2 - t1;
        else
            duree = (65536 - t1) + t2;

        /* Conversion durée -> distance (cm) */
        distance = duree / 58;

        /* Limite distance max HC-SR04 */
        if(distance > 400)
            distance = 400;

        /* Affichage */
        Aff_valeur(distance);

        if(distance <= DISTANCE_STOP)
            moteurs_commander(0, 0);
        else
            moteurs_commander(40, 40);

        etat = 0;
    }
}
