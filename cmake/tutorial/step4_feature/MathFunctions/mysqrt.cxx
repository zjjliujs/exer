// A simple program that computes the square root of a number
#include <stdio.h>
#include <math.h>
#include "MathFunctions.h"
#include "TutorialConfig.h"

double mysqrt(double value) {
	// if we have both log and exp then use them
#if defined (HAVE_LOG) && defined (HAVE_EXP)
	printf("mysqrt using ext and log!\n");
	return exp(log(value)*0.5);
#else // otherwise use an iterative approach
	printf("mysqrt using sqrt!\n");
	return sqrt(value);
#endif
}