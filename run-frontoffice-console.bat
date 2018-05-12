@Echo OFF
REM Frontoffice Console batch script based on ECAFETERIA APP made by EAPLI professors
REM set the class path,

REM assumes the build was executed with maven copy-dependencies
SET FRONTOFFICE_CONSOLE=frontoffice\target\frontoffice-5.0-SNAPSHOT.jar;frontoffice\target\dependency\*;

REM call the java VM, e.g,
java -cp %FRONTOFFICE_CONSOLE% cdioil.frontoffice.presentation.FrontOfficeMain
