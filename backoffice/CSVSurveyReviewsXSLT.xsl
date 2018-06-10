<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : CSVSurveyReviewsXSLT.xsl
    Created on : 5 de Junho de 2018, 16:36
    Author     : <a href="1160907@isep.ipp.pt">João Freitas</a>
    Description: XSLT Document containg the structure of the CSV file containg all 
        Survey Reviews that will be transformed with XSLT
        
Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/Survey">
        Informacoes Inquerito
        NºAvaliações:;<!-- #TO-DO: Select all answers and count them -->
        ;
        ; <!-- Three semicolons for Excel Spacing -->
        ;
        Sugestões
        ;
        ; <!-- Three semicolons for Excel Spacing -->
        ;
        Questão;Respostas
        <xsl:for-each select="Question"> <!-- Iterates through all Survey Question Labels -->
            <xsl:value-of select="@name"/><!-- Selects the value of the attribute "name" -->
            <xsl:for-each select="Answer"> <!-- Iterates all "Answer" nodes -->
                ;<xsl:value-of select="."/> <!-- Selects the node internal value -->
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
