#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/sha.h>

/* Generates a Authentication Key based on the computer Mac Address & MSN
 * <br>Uses SHA-512 for hash generation
 * <br>Inspired from https://stackoverflow.com/a/2458382
 */
unsigned char* generate();