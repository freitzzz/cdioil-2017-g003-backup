@Echo OFF
REM Backoffice Console batch script based on ECAFETERIA APP made by EAPLI professors
REM set the class path,

REM assumes the build was executed with maven copy-dependencies
SET BACKOFFICE_CONSOLE=backoffice\target\backoffice-5.0-SNAPSHOT.jar;backoffice\target\dependency\*;

REM call the java VM, e.g,
java -cp %BACKOFFICE_CONSOLE% cdioil.backoffice.console.presentation.BackofficeMain
