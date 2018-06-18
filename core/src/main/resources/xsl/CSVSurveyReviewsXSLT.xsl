    <?xml version="1.0" encoding="UTF-8"?>

    <!--
        Document   : CSVSurveyReviewsXSLT.xsl
        Created on : 5 de Junho de 2018, 16:36
        Author     : <a href="1160907@isep.ipp.pt">João Freitas</a>
        Description: XSL Document containg the structure of the CSV file containg all 
            Survey Reviews that will be transformed with XSLT

    Purpose of transformation follows.
    -->

    <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
        <xsl:output method="text" indent="no"/>

        <!-- TODO customize transformation rules 
        syntax recommendation http://www.w3.org/TR/xslt 
        -->
        <!-- Global Variables -->
        <xsl:variable name="SEPARATOR" select="';'"/><!-- Represents the CSV Separator (Semicolon ;) -->
        <xsl:variable name="NEW_LINE" select="'&#10;'"/><!-- Represents a new line (ASCII #10) -->
        <xsl:template match="/Survey">
            <xsl:text>Informacoes Inquerito</xsl:text><xsl:value-of select="$SEPARATOR"/>
            <xsl:value-of select="$NEW_LINE"/>
            <xsl:text>ID=</xsl:text><xsl:value-of select="$SEPARATOR"/><xsl:value-of select="@id"/><xsl:value-of select="$SEPARATOR"/>
            <xsl:value-of select="$NEW_LINE"/>
            <xsl:text>NºAvaliações:</xsl:text><xsl:value-of select="$SEPARATOR"/><xsl:value-of select="@reviews"/><xsl:value-of select="$SEPARATOR"/><!-- #TO-DO: Select all answers and count them -->
            <xsl:value-of select="$NEW_LINE"/>
            <xsl:value-of select="$SEPARATOR"/>
            <xsl:value-of select="$NEW_LINE"/>
            <xsl:value-of select="$SEPARATOR"/> <!-- Three semicolons for Excel Spacing -->
            <xsl:value-of select="$NEW_LINE"/>
            <xsl:value-of select="$SEPARATOR"/>
            <xsl:value-of select="$NEW_LINE"/>
            <xsl:if test="count(Suggestions/Suggestion)">
                <xsl:text>Sugestões</xsl:text><xsl:value-of select="$SEPARATOR"/>
                <xsl:apply-templates select="Suggestions"/>
                <xsl:value-of select="$NEW_LINE"/>
                <xsl:value-of select="$SEPARATOR"/>
                <xsl:value-of select="$NEW_LINE"/>
                <xsl:value-of select="$SEPARATOR"/><!-- Three semicolons for Excel Spacing -->
                <xsl:value-of select="$NEW_LINE"/>
                <xsl:value-of select="$SEPARATOR"/>
                <xsl:value-of select="$NEW_LINE"/>
            </xsl:if>
            <xsl:text>Questão</xsl:text><xsl:value-of select="$SEPARATOR"/><xsl:text>ID</xsl:text><xsl:value-of select="$SEPARATOR"/><xsl:text>Tipo</xsl:text><xsl:value-of select="$SEPARATOR"/><xsl:text>Respostas</xsl:text><xsl:value-of select="$SEPARATOR"/>
            <xsl:for-each select="Question"> <!-- Iterates through all Survey Question Labels -->
                <xsl:value-of select="$NEW_LINE"/>
                <xsl:value-of select="@name"/><xsl:value-of select="$SEPARATOR"/><xsl:value-of select="@id"/>;<xsl:value-of select="@type"/><!-- Selects the value of the attribute "name" -->
                <xsl:for-each select="Answer"> <!-- Iterates all "Answer" nodes -->
                    <xsl:value-of select="$NEW_LINE"/>
                    <xsl:value-of select="$SEPARATOR"/><xsl:value-of select="$SEPARATOR"/><xsl:value-of select="$SEPARATOR"/><xsl:value-of select="."/><xsl:value-of select="$SEPARATOR"/> <!-- Selects the node internal value -->
                </xsl:for-each>
            </xsl:for-each>
        </xsl:template>

        <!-- Matches Survey Reviews Suggestions -->
        <xsl:template match="Suggestions">
            <xsl:for-each select="Suggestion">
                <xsl:value-of select="$NEW_LINE"/>
                <xsl:value-of select="@value"/>
            </xsl:for-each>
        </xsl:template>
    </xsl:stylesheet>
