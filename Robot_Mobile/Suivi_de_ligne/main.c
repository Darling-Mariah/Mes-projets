#include <msp430.h>
#include "deplacement.h"
#include "capt_suivi_ligne_gauche.h"
#include "capt_suivi_ligne_droite.h"

// vitesse principale
#define VITESSE_BASE        50    // vitesse rapide

// gains PD optimises haute vitesse
#define KP                  22    // proportionnel
#define KD                  10    // derive

// divisieur global pour eviter une correction trop brutale
#define PID_DIVISOR        80

// zone neutre pour eviter le bruit
#define DEADZONE           20

// limite physique de correction (evite de trop tourner)
#define CORRECTION_MAX     25

int main(void)
{
    WDTCTL = WDTPW | WDTHOLD;

    horloge_init();
    moteurs_init();

    capt_gauche_init();
    capt_droite_init();

    int previous_error = 0;

    while (1)
    {
        int error;
        int derivative;
        int correction;
        int vitesse_gauche;
        int vitesse_droite;

        // lecture capteurs
        capt_gauche_lire();
        capt_droite_lire();

        // erreur droite - gauche
        // -> droite sur blanc => erreur negative => correction droite
        // -> gauche sur blanc => erreur positive => correction gauche
        error = capt_droite - capt_gauche;

        // zone morte
        if (error > -DEADZONE && error < DEADZONE)
            error = 0;

        // derivee
        derivative = error - previous_error;
        previous_error = error;

        // correcteur PD
        correction = (KP * error + KD * derivative) / PID_DIVISOR;

        // saturation
        if (correction > CORRECTION_MAX)  correction = CORRECTION_MAX;
        if (correction < -CORRECTION_MAX) correction = -CORRECTION_MAX;

        // application :
        // correction positive  -> tourner a gauche
        // correction negative  -> tourner a droite
        vitesse_gauche = VITESSE_BASE - correction;
        vitesse_droite = VITESSE_BASE + correction;

        // bornage
        if (vitesse_gauche > 100) vitesse_gauche = 100;
        if (vitesse_droite > 100) vitesse_droite = 100;
        if (vitesse_gauche < 0)   vitesse_gauche = 0;
        if (vitesse_droite < 0)   vitesse_droite = 0;

        moteurs_commander(vitesse_gauche, vitesse_droite);

        // correction plus rapide = meilleure stabilite a haute vitesse
        __delay_cycles(15000);
    }
}
