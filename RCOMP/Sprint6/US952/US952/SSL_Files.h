#ifndef SSL_FILES_H
#define SSL_FILES_H

/*OpenSSL includes*/
#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/ssl.h>
#include <openssl/conf.h>
#include <openssl/evp.h>
#include <openssl/opensslv.h>

/*Constant that represents the SSL certificate file*/
#define CERT_FILE "certificate.pem"
/*Constant that represents the SSL private key file*/
#define PRIVATE_KEY_FILE "privatekey.pem"
/*Password for the key file*/
#define KEY_PASSWD "Password123"

/*Initializes SSL libraries for encryption and error reporting*/
void initializeSSL(){SSL_load_error_strings();SSL_library_init();OpenSSL_add_all_algorithms();}

#endif