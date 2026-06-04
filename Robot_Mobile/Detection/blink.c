#include <msp430.h>

#include "ADC.h"

#include "Afficheur.h"


int lumiere(void)

{

    int adc_val;

   /* int valeur_affichee;

    unsigned char buffer[4];*/

    WDTCTL = WDTPW | WDTHOLD;

    BCSCTL1 = CALBC1_1MHZ;

    DCOCTL  = CALDCO_1MHZ;



    P1DIR &= ~BIT0;

    ADC10AE0 |= BIT0;



    ADC_init();

    Aff_Init();

    while(1)

    {

        ADC_Demarrer_conversion(0);

        adc_val = ADC_Lire_resultat();

       /* valeur_affichee = convert_Hex_Dec(adc_val);

        convert_ASCII(valeur_affichee, buffer);

        Aff_4carac(buffer);*/

        return adc_val;


       // __delay_cycles(500000);

    }

}
