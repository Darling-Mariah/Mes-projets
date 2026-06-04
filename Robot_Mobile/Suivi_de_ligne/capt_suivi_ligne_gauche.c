#include <msp430.h>
#include "ADC.h"
#include "capt_suivi_ligne_gauche.h"

volatile int capt_gauche = 0;

void capt_gauche_init(void)
{
    ADC_init();   // initialisation ADC
}

void capt_gauche_lire(void)
{
    // capteur gauche sur P1.4 -> A4 -> voie 4
    ADC_Demarrer_conversion(4);
    capt_gauche = ADC_Lire_resultat();
}
