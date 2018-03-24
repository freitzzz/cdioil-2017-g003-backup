@Echo OFF
REM Bootstrap batch script based on ECAFETERIA APP made by EAPLI professors
REM set the class path,

REM assumes the build was executed with maven copy-dependencies
SET BOOTSTRAP=bootstrap\target\bootstrap-0.0.1-SNAPSHOT.jar;bootstrap\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BOOTSTRAP% cdioil.application.bootstrap.BootstrapRunner
