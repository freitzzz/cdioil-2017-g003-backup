#include "FileReader.h"
#include "StringUtils.h"
#include "DeviceSimulator.h"

/*Constant that represents if a DeviceSimulator was configured correctly*/
#define VALID 1
/*Constant that represents if a DeviceSimulator was not configured correctly*/
#define INVALID 0
/*Constant that represents the ignore line identifier*/
#define IGNORE_LINE "#"

/*Identifiers relatively to the Device Configuration*/

/*Identifier for the send time between reviews*/
#define DEVICE_CONFIGURATION_REVIEW_SEND_TIME_IDENTIFIER "REVIEW_SEND_TIME"
/*Identifier for the number of reviews being sent*/
#define DEVICE_CONFIGURATION_REVIEW_QUANTITY_IDENTIFIER "REVIEW_QUANTITY"

/*Configures a certain DeviceSimulator according to a configuration file*/
/*Returns Integer 1 if valid 0 if not*/
int configureDeviceSimulator(DeviceSimulator simulator,char* configFile);