<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : CSVTemplateXSLT.xsl
    Created on : 14 de Junho de 2018, 21:26
    Authors    : António Sousa (1161371), Rita Gonçalves (1160912)
    Description: XSL Transformer that transforms a XML document into a CSV file.
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text" indent="no"/>

    <xsl:template match="/Template">
    Template Information
                Template title:;<xsl:value-of select= "@title"/>
                ; <!-- Semicolon for Excel spacing -->
                Questions:
                QuestionID;QuestionText      
               <xsl:for-each select = "Question">
                   Question   
                      ;<xsl:value-of select = "@ID"/>;<xsl:value-of select = "QuestionText"/>
                        Options:  
                      <xsl:for-each select="Options/Option">
                          ;<xsl:value-of select = "."/>
                    </xsl:for-each> 
               </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
