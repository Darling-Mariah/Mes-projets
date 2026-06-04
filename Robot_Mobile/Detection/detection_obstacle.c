#include <msp430.h>
#include <stdint.h>
#include "detection_obstacle.h"
#define DISTANCE_STOP 5




/* Impulsion TRIG 10 µs sur P1.6 */
void trigger_ultrason(void)
{
    P1OUT |= BIT6;
    __delay_cycles(10);     // 10 µs à 1 MHz
    P1OUT &= ~BIT6;
}

void capteur_US_init(){
    /* TRIG : P1.6 */
    P1DIR |= BIT6;
    P1OUT &= ~BIT6;

    /* ECHO : P1.1 (Timer A0.CCI0A) */
    P1DIR &= ~BIT1;
    P1SEL |= BIT1;
    P1SEL2 &= ~BIT1;

    /* Timer A0 en mode capture continu */
    TA0CTL = TASSEL_2 | MC_2 | TACLR;     // SMCLK, mode continu
    TA0CCTL0 = CM_3 | CCIS_0 | CAP | CCIE;

}


