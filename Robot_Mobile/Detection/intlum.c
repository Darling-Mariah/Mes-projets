#include <msp430.h>
#include "deplacement.h"
#include "ADC.h"
#include "luminosite.h"

/* ================== PARAMÈTRES ================== */
#define NB_INCREMENTS_SCAN    14       // Pas pour 360°
#define TEMPS_INCR_ROTATION   60000   // Rotation pour 1 pas
#define TEMPS_ALIGN_FINE      60000   // Petite rotation alignement
#define TEMPS_AVANCE          60000   // Avance courte
#define TOL_MIN             15      // Tolérance alignement lumière
#define TOL_MAX             50

/* ================== FONCTIONS ================== */
void tourner_incr(void)
{
    moteurs_commander(50, -50);
    __delay_cycles(TEMPS_INCR_ROTATION);
    moteurs_commander(0, 0);
    __delay_cycles(10000);
}

/* ================== PROGRAMME PRINCIPAL ================== */
int main(void)
{
    WDTCTL = WDTPW | WDTHOLD;

    horloge_init();
    moteurs_init();
    ADC_init();

    unsigned int lumiere_tableau[NB_INCREMENTS_SCAN];
    unsigned int max_lum, meilleur_incr;
    unsigned int position_actuelle;
    unsigned int lum_actuelle;
    unsigned int i, j;

    while (1)
    {
        /* ---------- SCAN 360 ---------- */
        max_lum = 0;
        meilleur_incr = 0;

        for (i = 0; i < NB_INCREMENTS_SCAN; i++)
        {
            tourner_incr();
            lumiere_tableau[i] = lumiere();

            if (lumiere_tableau[i] > max_lum)
            {
                max_lum = lumiere_tableau[i];
                meilleur_incr = i;
            }
        }

        /* retour orientation initiale */
        position_actuelle = 0;

        /* ---------- ORIENTATION GROSSIÈRE ---------- */
     /*   unsigned int rotation =
        (meilleur_incr - position_actuelle + NB_INCREMENTS_SCAN)
        % NB_INCREMENTS_SCAN;

        for (j = 0; j < rotation; j++)
        {
            tourner_incr();
            position_actuelle = (position_actuelle + 1) % NB_INCREMENTS_SCAN;
        }*/

        /* ---------- ALIGNEMENT FIN ---------- */
        while (1)
        {
            lum_actuelle = lumiere();

            if (lum_actuelle >= (max_lum - TOL_MIN))
            {
                moteurs_commander(0, 0);
                break;      // alignement OK
            }

            moteurs_commander(50, -50);
            __delay_cycles(TEMPS_ALIGN_FINE);
            moteurs_commander(0, 0);
            __delay_cycles(100000);
        }

        /* ---------- AVANCEMENT ASSERVI ---------- */
        while (1)
        {
            moteurs_commander(50, 50);
            __delay_cycles(TEMPS_AVANCE);
            moteurs_commander(0, 0);
            __delay_cycles(10000);

            lum_actuelle = lumiere();

            /* perte d’alignement  retour alignement */
            if (lum_actuelle < (max_lum - TOL_MIN))
                break;

            /* arrivé très proche de la source */
            if (lum_actuelle >= (max_lum + TOL_MAX))
                goto FIN_LUMIERE;
        }

        /* retour à l’alignement fin */
        continue;

        FIN_LUMIERE:
        moteurs_commander(0, 0);
        __delay_cycles(1000000);

        /* reprise du suivi de ligne ici */
        // suivre_ligne();
    }
}
