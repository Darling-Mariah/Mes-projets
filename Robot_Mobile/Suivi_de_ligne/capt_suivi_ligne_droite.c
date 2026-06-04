#include <msp430.h>
#include "ADC.h"
#include "capt_suivi_ligne_droite.h"

volatile int capt_droite = 0;

void capt_droite_init(void)
{
    ADC_init();   // initialisation ADC
}

void capt_droite_lire(void)
{
    // capteur droit sur P1.5 -> A5 -> voie 5
    ADC_Demarrer_conversion(5);
    capt_droite = ADC_Lire_resultat();
}
